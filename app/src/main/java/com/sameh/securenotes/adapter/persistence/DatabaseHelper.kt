package com.sameh.securenotes.adapter.persistence

import android.content.Context
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import java.security.KeyStore
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

/**
 * Provides secure access to the NoteDatabase, leveraging encrypted storage capabilities
 * to enhance data security. This class employs a multi-faceted approach to security:
 *
 * 1. Android Keystore System: Utilized for securing cryptographic keys, ensuring they are
 *    protected by the underlying hardware and OS, and are not exposed to the application layer.
 *
 * 2. EncryptedSharedPreferences: Used for securely storing sensitive information such as
 *    the database encryption passphrase and initialization vectors (IVs), with the data encrypted
 *    using the AES256-GCM encryption scheme.
 *
 * 3. SQLCipher: Integrates transparent, full-database encryption to the SQLite database used by
 *    Room. This ensures that the database file at rest is encrypted, providing a strong defense
 *    against unauthorized access.
 *
 * 4. Cipher: Facilitates the encryption and decryption operations using AES/GCM/NoPadding
 *    cipher transformation, offering both confidentiality and integrity of the stored passphrase
 *    and IV.
 *
 * 5. SecureRandom: Generates a high-entropy passphrase for database encryption, enhancing
 *    the overall security of the encryption scheme.
 *
 * The combination of these components ensures a comprehensive security model that protects
 * sensitive user data both at rest (via SQLCipher) and during access credential management
 * (via EncryptedSharedPreferences and the Android Keystore System). Error handling and logging
 * are implemented with caution to prevent sensitive information leakage while providing
 * diagnostic information in case of encryption-related failures.
 *
 * Usage:
 * The `database` property provides lazy-initialized access to the securely encrypted NoteDatabase.
 * This setup ensures that encryption keys and passphrases are securely managed and applied,
 * securing data throughout the application's lifecycle.
 */
class DatabaseHelper(private val context: Context) {
    companion object {
        private const val DATABASE_NAME = "encrypted_database.db"
        private const val KEYSTORE_ALIAS = "MyKeyAlias"
        private const val ANDROID_KEYSTORE = "AndroidKeyStore"
        private const val TRANSFORMATION = "AES/GCM/NoPadding"
        private const val PREFS_FILE = "MyAppPrefs"
        private const val PREFS_KEY_PASSPHRASE = "DbPassphrase"
        private const val PREFS_KEY_IV = "encryptionIv"

        // For a 16 character Base64 encoded string
        private const val BYTE_ARRAY_LENGTH = 12
    }

    private val sharedPreferences: SharedPreferences by lazy { createEncryptedSharedPreferences() }

    val database: NoteDatabase by lazy {
        val passphrase = getOrGeneratePassphrase()
        val factory = SupportFactory(SQLiteDatabase.getBytes(passphrase.toCharArray()))
        Room.databaseBuilder(context, NoteDatabase::class.java, DATABASE_NAME)
            .openHelperFactory(factory)
            .build()
    }

    private fun createEncryptedSharedPreferences(): SharedPreferences {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            context,
            PREFS_FILE,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    private fun getOrGeneratePassphrase(): String {
        val secretKey = getSecretKeyFromKeystore()
        return sharedPreferences.getString(PREFS_KEY_PASSPHRASE, null)?.let { encryptedPassphrase ->
            sharedPreferences.getString(PREFS_KEY_IV, null)?.let { encryptionIv ->
                decryptPassphrase(encryptedPassphrase, encryptionIv, secretKey)
            }
        } ?: run {
            val newPassphrase = generateNewPassphrase()
            encryptAndStorePassphrase(newPassphrase, secretKey)
            newPassphrase
        }
    }

    private fun getSecretKeyFromKeystore(): SecretKey {
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE).apply { load(null) }
        return if (!keyStore.containsAlias(KEYSTORE_ALIAS)) {
            val keyGenerator =
                KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEYSTORE)
            val keyGenParameterSpec = KeyGenParameterSpec.Builder(
                KEYSTORE_ALIAS,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .build()
            keyGenerator.init(keyGenParameterSpec)
            keyGenerator.generateKey()
            keyStore.getKey(KEYSTORE_ALIAS, null) as SecretKey
        } else {
            keyStore.getKey(KEYSTORE_ALIAS, null) as SecretKey
        }
    }

    private fun decryptPassphrase(
        encryptedPassphrase: String,
        encryptionIv: String,
        secretKey: SecretKey
    ): String {
        return try {
            val iv = Base64.decode(encryptionIv, Base64.DEFAULT)
            val cipher = Cipher.getInstance(TRANSFORMATION).apply {
                init(Cipher.DECRYPT_MODE, secretKey, GCMParameterSpec(128, iv))
            }
            String(
                cipher.doFinal(Base64.decode(encryptedPassphrase, Base64.DEFAULT)),
                Charsets.UTF_8
            )
        } catch (e: Exception) {
            // Log with caution, avoiding sensitive data
            // Consider fallback or error reporting mechanisms
            throw RuntimeException("Decryption error. Unable to access the database.", e)
        }
    }

    private fun generateNewPassphrase(): String {
        val secureRandom = SecureRandom()
        val bytes = ByteArray(BYTE_ARRAY_LENGTH)
        secureRandom.nextBytes(bytes)
        return Base64.encodeToString(bytes, Base64.NO_PADDING or Base64.NO_WRAP)
    }

    private fun encryptAndStorePassphrase(passphrase: String, secretKey: SecretKey) {
        try {
            val cipher =
                Cipher.getInstance(TRANSFORMATION).apply { init(Cipher.ENCRYPT_MODE, secretKey) }
            val iv = Base64.encodeToString(cipher.iv, Base64.DEFAULT)
            val encryptedBytes = cipher.doFinal(passphrase.toByteArray(Charsets.UTF_8))
            val encryptedPassphrase = Base64.encodeToString(encryptedBytes, Base64.DEFAULT)

            with(sharedPreferences.edit()) {
                putString(PREFS_KEY_PASSPHRASE, encryptedPassphrase)
                putString(PREFS_KEY_IV, iv)
                commit()
            }
        } catch (e: Exception) {
            // Log with caution, avoiding sensitive data
            // Consider fallback or error reporting mechanisms
            throw RuntimeException("Encryption error. Failed to secure the database passphrase.", e)
        }
    }
}
