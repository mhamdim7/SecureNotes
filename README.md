# SecureNotes: Encrypted Note-Taking App

SecureNotes is an Android note-taking app, embodying privacy, security, and modern Android development practices. This branch showcases an implementation based on Clean Architecture principles, enhanced with Jetpack Compose, Room, Hilt, and Navigation Components to create a robust, maintainable, and user-friendly application.

## Key Technologies & Patterns

- **Jetpack Compose**: Powers the UI, offering a declarative approach to building efficient and compact user interfaces.
- **Room Database**: Manages local data storage with full SQL capabilities, wrapped in a convenient ORM for seamless integration.
- **Hilt**: Provides compile-time validated dependency injection, simplifying the architecture and promoting modularity and testability.
- **MVVM**: The architectural pattern guiding the UI layer, facilitating a clear separation of concerns and a reactive data flow.
- **Navigation Components**: Streamlines in-app navigation, ensuring a consistent and predictable user experience.

## Features

- **Encrypted Storage**: Uses SQLCipher and EncryptedSharedPreferences for secure data storage.
- **Android Keystore**: Employs the Android Keystore System for enhanced cryptographic key management.
- **Note Management**: Features adding, editing, and deleting notes, supported by an intuitive Compose-based UI.
- **Color-coded Notes**: Enhances note organization with dynamic color coding, sorting and more.

## Architecture Overview

The application is structured into three primary layers, each with distinct responsibilities:

- **Domain Layer**: The heart of the application, defining business models (`Note`) and data operations contracts (`NoteRepository`).
- **Data Layer**: Implements the data operations contracts, facilitating communication with databases.
- **Presentation Layer**: Focuses on presenting data to the user, driven by the MVVM pattern and supported by Jetpack Compose for UI composition.

## Getting Started

1. Clone this repository.
2. Open the project in Android Studio.
3. Build and run the application on your Android device or emulator.

## Contributing

Contributions to SecureNotes are warmly welcomed. Feel free to report issues, suggest features, or open pull requests.
