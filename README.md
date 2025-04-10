# Order Management App

## Overview

Order Management App is a comprehensive Android application built entirely with Jetpack Compose. It serves as a platform for customers to order food and for restaurant managers to manage incoming orders efficiently. The application features a dynamic user interface that adapts based on the logged-in user type (CUSTOMER or RESTAURANT_MANAGER), and also supports guest users for Browse and adding items to the cart.

## Features

### General
* **Dual User Roles:** Supports distinct interfaces and functionalities for `CUSTOMER` and `RESTAURANT_MANAGER`.
* **Guest Access:** Allows non-logged-in users to browse the menu, view campaigns, and add items to the cart.
* **Dynamic UI:** The Bottom Navigation Bar and available screens change automatically based on the user's login status and type.
* **Secure Session Management:** Uses `EncryptedSharedPreferences` (via `SecurePreferences`) and a `SessionManager` to handle JWT tokens, user details, and user types securely.
* **Push Notifications:** Leverages Firebase Cloud Messaging for real-time updates between customers and managers (e.g., order status changes, cancellations).
* **Deeplinking:** Notifications navigate the user directly to the relevant order details screen.

### Customer Role (`CUSTOMER`)
* **Home Screen:** View menus and active campaigns. Add items directly to the cart.
* **Cart Management:** View cart, adjust item quantities, or remove items. Users are prompted to log in/sign up before placing an order.
* **Order Placement:** Place food orders. Uses Geocoding API to suggest addresses based on coordinates.
* **Order History:** View past and current orders with filtering options.
* **Order Cancellation:** Cancel active orders (notifies the manager).
* **Order Confirmation:** Confirm receipt of delivered orders (notifies the manager).
* **Profile:** View basic profile information and log out.

### Restaurant Manager Role (`RESTAURANT_MANAGER`)
* **Order Dashboard (List & Map):**
    * View incoming orders in a list format. List shows addresses identified via `Geocoder`.
    * View incoming orders geographically on a Google Map.
    * Filters applied are shared between the List and Map views (using `SharedViewModel`).
    * View detailed order information, including the full address entered by the customer.
* **Order Status Updates:** Change the status of orders (e.g., mark as "On the Way," which notifies the customer).
* **Notifications:** Receive notifications for new orders, customer cancellations, and confirmations.
* **Profile:** View basic profile information and log out.

## Architecture

The application follows the **MVVM (Model-View-ViewModel)** architectural pattern and employs a multi-module structure for better separation of concerns and maintainability.

* **`:app` Module:**
    * The main application module containing all UI elements (Screens, Composables), navigation (including the dynamic Bottom Navigation Bar), and ViewModels.
    * Depends on the `:data` module for all data requirements.
* **`:data` Module:**
    * Acts as the single source of truth for the `:app` module.
    * Fetches data from the `:network` module (or potentially local sources in the future).
    * Contains its own data models and maps models from the `:network` layer.
    * Provides data wrapped in a `Result<T>` class, which includes a custom `ResultCode` (extendable beyond standard HTTP codes) for granular error handling and status reporting in the UI.
    * Includes repository logic, such as determining whether to fetch guest cart data or authenticated user cart data based on `SessionManager` status.
    * Depends on `:core` and `:network`.
* **`:network` Module:**
    * Handles all remote API interactions using **Ktor Client**.
    * Defines network-specific data models and uses a `BaseResponse<T>` wrapper for incoming data.
    * Accessible *only* by the `:data` module.
    * Depends on `:core`.
* **`:core` Module:**
    * Contains base classes, utilities, and components shared across other modules.
    * Includes `SecurePreferences` for handling `EncryptedSharedPreferences`.
    * Includes `SessionManager` for managing user sessions (JWT token, user info, user type).

## Technology Stack & Libraries

* **UI:** 100% Jetpack Compose
* **Architecture:** MVVM
* **Dependency Injection:** Koin
* **Networking:** Ktor Client
* **Asynchronous Programming:** Kotlin Coroutines
* **Image Loading:** Coil
* **Push Notifications:** Firebase Cloud Messaging (FCM)
* **Secure Local Storage:** EncryptedSharedPreferences
* **Maps:** Google Maps SDK for Android
* **Address/Coordinate Handling:** Geocoding API & Android Geocoder

## Setup

1.  Clone the repository.
2.  Build and run the application.
