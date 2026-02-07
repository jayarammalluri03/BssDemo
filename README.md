# Face Tagging Sample App

## Overview

This is a sample Android application that detects human faces from images selected from the device gallery, draws bounding boxes around detected faces, allows the user to assign tags (names) to each face, and saves the tagged results locally using Room Database.

The project is built using **Clean Architecture**, **MVVM**, and **Jetpack Compose**, demonstrating a scalable and maintainable Android application structure.

---

## Features

* Fetch images from the device gallery
* Automatic face detection using MediaPipe Face Detection
* Draw bounding boxes over detected faces
* Tap a face to assign or edit a tag (name)
* Persist detected faces and tags locally using **Room Database**
* Observe saved results using reactive **Flow**
* Fully built with **Jetpack Compose UI**

---

## Architecture

The application follows **Clean Architecture** with clear separation of layers:

### Presentation Layer

* Jetpack Compose UI screens
* ViewModels (MVVM pattern)
* StateFlow for reactive UI updates

### Domain Layer

* UseCases for business logic
* Domain models independent of frameworks

### Data Layer

* Repository implementations
* Room database (local persistence)
* Entity ↔ Domain mappers

---

## Tech Stack

* Kotlin
* Jetpack Compose
* MVVM Architecture
* Clean Architecture
* Hilt Dependency Injection
* Room Database
* Kotlin Coroutines & Flow
* MediaPipe Face Detection

---

## App Flow

1. Fetches images from the gallery.
2. The app detects faces in each image.
3. Bounding boxes are drawn around detected faces.
4. User taps a face to add a tag (name).
5. Tagged results are saved locally using Room.
6. Saved images and tags can be viewed later.

---

## Purpose

This project is created as a **learning and demonstration project** to showcase:

* Face detection integration in Android
* Clean architecture implementation
* Compose-based UI development
* Local persistence using Room
* Scalable MVVM project structure



## Author

Sample project developed for learning advanced Android concepts including **Face Detection, Compose UI, Clean Architecture, and Room persistence**.
