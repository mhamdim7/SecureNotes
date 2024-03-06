# SecureNotes: Encrypted Note-Taking App

### 📢 Clean Architecture Branch ⚠️

SecureNotes is an Android note-taking app, embodying privacy, security, and modern Android development practices. This branch showcases an implementation based on Clean Architecture principles, enhanced with Jetpack Compose, Room, Hilt, and Navigation Component to create a robust, maintainable, and user-friendly application.

## Key Technologies & Patterns

- **Jetpack Compose**: Simplifies UI development with a modern, declarative approach.
- **Room Database**: Provides robust local data storage solutions, enhanced with encrypted capabilities for security.
- **Hilt**: Facilitates dependency injection, streamlining the setup and scalability of the application architecture.
- **MVVM**: Guides the presentation layer, facilitating a clear separation of concerns and a reactive data flow.
- **Navigation Component**: Manages UI navigation, offering a flexible and intuitive handling of app flows.

## Features

- **Encrypted Storage**: Uses SQLCipher and EncryptedSharedPreferences for secure data storage.
- **Android Keystore**: Employs the Android Keystore System for enhanced cryptographic key management.
- **Note Management**: Features adding, editing, and deleting notes, supported by an intuitive Compose-based UI.
- **Color-coded Notes**: Enhances note organization with dynamic color coding, sorting and more.

## ToDo

- **Add password protected lockscreen**
- **Add possibility to use Biometrics**

## Architecture Overview

SecureNotes employs Clean Architecture principles, organized around a feature-based packaging strategy:

- **Domain Layer**: The heart of the application, defining business models and data operations contracts.
- **Data Layer**: Implements the data operations contracts, facilitating communication with databases.
- **Presentation Layer**: Focuses on presenting data to the user, driven by the MVVM pattern and supported by Jetpack Compose for UI composition.

## Getting Started

1. Clone this repository.
2. Open the project in Android Studio.
3. Build and run the application on your Android device or emulator.

## Contributing

Contributions to SecureNotes are warmly welcomed. Feel free to report issues, suggest features, or open pull requests.
