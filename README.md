# SecureNotes: Encrypted Note-Taking App

### Hexagonal Architecture Branch

Embracing Hexagonal Architecture, SecureNotes is an Android app that prioritizes security and modern development practices, utilizing Jetpack Compose, Room, Hilt, MVVM, and Navigation Components to deliver a seamless note-taking experience.

## Key Technologies & Patterns

- **Jetpack Compose**: Simplifies UI development with a modern, declarative approach.
- **Room Database**: Provides robust local data storage solutions, enhanced with encrypted capabilities for security.
- **Hilt**: Facilitates dependency injection, streamlining the setup and scalability of the application architecture.
- **MVVM**: Guides the presentation layer, ensuring clean separation between the UI and business logic.
- **Navigation Components**: Manages UI navigation, offering a flexible and intuitive handling of app flows.

## Features

- **Secure Storage**: Integrates SQLCipher with EncryptedSharedPreferences for top-tier data encryption.
- **Keystore Integration**: Leverages the Android Keystore System for secure key management.
- **Intuitive UI for Notes**: Allows users to easily add, modify, and categorize notes with custom colors.
- **Jetpack Compose UI**: Ensures a responsive and aesthetically pleasing user interface.

## Architecture Overview

SecureNotes is built on Hexagonal Architecture, organized into:

- **Domain Layer**: Defines core business logic and models, establishing the application's fundamental operations.
- **Application Layer**: Contains the use cases that dictate the application's functionality, bridging the domain with the outer world.
- **Adapter Layer**: Connects the application to external services and interfaces, including UI, database access, and framework-specific features.

## Getting Started

1. Clone the repository to your local environment.
2. Open the project with Android Studio.
3. Compile and execute the app on an Android device or emulator.

## Contribution

Your input helps make SecureNotes better. Please feel free to suggest improvements, report bugs, or contribute directly through pull requests.
