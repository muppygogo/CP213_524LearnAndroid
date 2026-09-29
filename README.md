# CP213 - Learn Android

> Android application development coursework and practice projects using Kotlin.

This repository contains Android development exercises, Kotlin practice, lab assignments, and application projects created while learning mobile application development.

---

## Repository Overview

The repository is organized into three main sections:

```text
CP213_524LearnAndroid/
│
├── Lab/
│   └── Android and Kotlin lab exercises
│
├── QuizKotlin/
│   └── Kotlin programming exercises and quizzes
│
└── RandomApp/
    └── Android randomizer application
```

---

## Tech Stack

| Category | Technology |
|---|---|
| Platform | Android |
| Language | Kotlin |
| IDE | Android Studio |
| UI | Jetpack Compose |
| Architecture | MVVM |
| Navigation | Android Activity / startActivity |
| Database | SQLite / Room |
| Version Control | Git, GitHub |

---

## Projects

### Lab

A collection of Android development exercises created to practice fundamental concepts such as:

- Kotlin programming
- Android project structure
- Activities
- UI development
- User interaction
- Android application logic

---

### QuizKotlin

Kotlin programming exercises and quizzes used to strengthen understanding of the language before and during Android development.

Topics include fundamental Kotlin syntax, logic, functions, and programming concepts used in Android applications.

---

## Random App

**Random App** is an Android application designed to help users make quick decisions by randomly selecting values from different categories.

The application supports:

- Random Food
- Random Name
- Random Number

Users can select the type of randomization they want, provide the required information, and instantly receive a randomized result.

### Main Features

#### Random Food

Randomly selects a food item from a predefined list.

#### Random Name

Allows users to enter multiple names and randomly selects one name from the list.

#### Random Number

Allows users to define a numeric range and generates a random number within that range.

---

## Random App Flow

```text
Open Application
      │
      ▼
   Main Menu
      │
      ├── Random Food
      │
      ├── Random Name
      │
      └── Random Number
      │
      ▼
Enter Input / Select Option
      │
      ▼
Press Random Button
      │
      ▼
Display Result
```

---

## Android Concepts Applied

Throughout the repository, I practiced several Android development concepts, including:

- Kotlin programming
- Android Studio
- Jetpack Compose
- Android Activity lifecycle
- Multi-Activity applications
- Navigation with `startActivity()`
- Basic MVVM architecture
- UI state and user interaction
- SQLite / Room database concepts
- Offline application functionality

---

## Random App Architecture

The Random App applies a basic **MVVM (Model-View-ViewModel)** structure.

```text
Model
  │
  ▼
Data

ViewModel
  │
  ▼
Application Logic

View
  │
  ▼
Jetpack Compose UI
```

The application also uses multiple Activities such as:

```text
MainActivity
FoodActivity
NameActivity
NumberActivity
```

Navigation between screens is handled using Android `startActivity()`.

---

## Future Improvements

Possible improvements for the Random App include:

- Randomization history
- Favorite items
- Food images
- Randomization animations
- Sound effects
- Spin Wheel randomizer
- Improved UI and user experience

---

## Learning Outcomes

Through these projects and exercises, I gained practical experience with:

- Developing Android applications with Kotlin
- Building interfaces with Jetpack Compose
- Designing application navigation
- Organizing Android projects
- Managing application logic
- Applying basic software architecture
- Testing applications with Android Emulator
- Using Git and GitHub for version control

---

## Featured Project

### Random App

An Android decision-support application that helps users quickly choose between options using randomization.

**Platform:** Android  
**Language:** Kotlin  
**UI:** Jetpack Compose  
**Architecture:** MVVM  

---

## Author

**Praewpaphatsorn**

Computer Science Student  
Srinakharinwirot University

GitHub: https://github.com/muppygogo
