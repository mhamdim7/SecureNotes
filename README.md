# SecureNotes: Encrypted Note-Taking App

### 📢 Hexagonal Architecture Branch ⚠️  

Embracing Hexagonal Architecture, SecureNotes is an Android app that prioritizes security and modern development practices, utilizing Jetpack Compose, Room, Hilt, MVVM, and Navigation Component to deliver a seamless note-taking experience.

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

SecureNotes is built on Hexagonal Architecture, mainly organized into:

- **Domain Layer**: Defines core business logic and models, establishing the application's fundamental operations.
- **Application Layer**: Contains the use cases that dictate the application's functionality, bridging the domain with the outer world.
- **Adapter Layer**: Connects the application to external services and interfaces, including UI, database access, and framework-specific features.

## Getting Started

1. Clone the repository to your local environment.
2. Open the project with Android Studio.
3. Compile and execute the app on an Android device or emulator.

## Contribution

Contributions to SecureNotes are warmly welcomed. Feel free to report issues, suggest features, or open pull requests.
