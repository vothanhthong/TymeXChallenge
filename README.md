# Currency Converter App

## Introduction

The **Currency Converter** is a mobile application built using **Kotlin** and **Jetpack Compose**, designed to provide seamless currency conversions between multiple currencies. Leveraging the **Open Exchange Rates API**, the app fetches live exchange rates and offers a user-friendly interface for currency selection and numeric input via a custom keyboard.

---

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Installation and Setup](#installation-and-setup)
  - [Prerequisites](#prerequisites)
  - [Steps](#steps)
- [Usage](#usage)
  - [Main Screen](#main-screen)
  - [Custom Keyboard](#custom-keyboard)
  - [Reload Exchange Rates](#reload-exchange-rates)
  - [Currency Selection](#currency-selection)
- [Project Structure](#project-structure)
- [Known Limitations](#known-limitations)
- [Future Enhancements](#future-enhancements)
- [License](#license)
- [Acknowledgments](#acknowledgments)

---

## Features

- Fetches live exchange rates using the **Open Exchange Rates API**.
- Displays conversion rates for multiple currencies with a fixed base currency (USD).
- Allows dynamic selection and replacement of displayed currencies.
- Custom numeric keyboard supporting:
  - Arithmetic operations: addition, subtraction, multiplication, division.
  - Special functions: percentage, clear, delete, equals.
- Modern, responsive UI.

---

## Installation and Setup

### Prerequisites

1. **Android Studio** installed on your system.
2. An API key from [FX Rates API](https://fxratesapi.com/).

### Steps

1. Clone the repository:
   ```bash
   git clone https://github.com/your-repository/currency-converter.git
   cd currency-converter
   ```
2. Open the project in Android Studio.

3. Add your API key:
   - Open (or create) the local.properties file in the project root.
   - Add the following line:
     FX_RATE_API=your_api_key_here
4. Build and run the project on an emulator or a physical Android device.

# Usage

## Main Screen

- **Base Currency**:
- **Additional Currencies**: Displays up to three additional currencies by default (e.g., VND, EUR, JPY). Tap the buttons next to each currency to replace it with a different one.

## Custom Keyboard

The app features a custom numeric keyboard that supports:

- **Numbers**: `0-9`
- **Arithmetic Operations**: `+`, `-`, `×`, `÷`
- **Special Functions**:
  - `%`: Percentage
  - `C`: Clear
  - `Delete`: Delete last input
  - `=`: Equals (calculate the result)

## Reload Exchange Rates

- Tap the **Reload** button in the toolbar to fetch the latest exchange rates and update displayed conversions.

## Currency Selection

- Tap on a currency button to open the **Currency Selection Screen**.
- Select a currency from the list to update the corresponding row on the main screen.

---

# Project Structure

```bash
src/main/
├── java/com/example/currencyconverter/
│   ├── MainActivity.kt               # Main screen of the app
│   ├── data/
│   │   ├── models/                   # Data models for API responses
│   │   ├── network/                  # Retrofit client setup
│   ├── ui/
│   │   ├── mainactivity/             # UI components for the main screen
│   │   ├── currencyselectionactivity/ # Currency selection screen
├── res/
│   ├── drawable/                     # Icons and images
│   ├── layout/                       # Layout XML files (if any)
│   ├── values/                       # Colors, strings, and themes
├── AndroidManifest.xml               # App manifest file
# Known Limitations

- **API Key Requirement**: A valid API key is necessary to fetch live exchange rates.

---

# Future Enhancements

- **Offline Mode**: Cache exchange rates for offline usage.
- **Dynamic Rows**: Enable users to add or remove currency rows dynamically.

---

# License

This project is open-source and available under the **MIT License**. You are free to use, modify, and distribute it under the terms of the license.

---

# Acknowledgments

- **Open Exchange Rates** for providing the exchange rates API.
- **Jetpack Compose** for its modern UI framework, enabling a seamless development experience.
```

## YouTube Video Demo

Watch the demo video showcasing the app's features and functionality:

[![Currency Converter App Demo](https://youtu.be/crZeOMlwSvE)](https://youtu.be/crZeOMlwSvE)

Alternatively, you can click this link: [Currency Converter App Demo](https://youtu.be/crZeOMlwSvE)
