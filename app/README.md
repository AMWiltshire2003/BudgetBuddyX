# BudgetBuddyX Android App - README

## Overview
BudgetBuddyX is a personal finance and budgeting Android app designed to help users manage their income, expenses, and preferences effectively. The app is built entirely using **Activities (not Fragments)** in **Kotlin**, and it leverages **Room Database** for local data persistence.

---

## Features Implemented

###  Splash Screen
- Brief introductory screen shown at app launch.
- Automatically navigates to the login screen after a delay.

###  User Authentication
- **Registration Screen**: Allows users to sign up with a username, email, and password.
- **Login Screen**: Authenticates users and navigates to the dashboard.
- Passwords are stored securely in the local Room database.

###  Room Database
- Used for storing user information locally.
- Includes an `AppDatabase`, `User` data model, and `UserDao` for database operations.

###  Dashboard (Create Category Activity)
- Acts as the main landing page after successful login.
- Includes navigation to the settings screen.
- Intended to manage budgeting categories (future scope).

###  Settings Screen
- Allows users to:
    - Change preferred **currency**.
    - Change **language**.
    - Toggle **notification** preferences.
    - Update **username** and **password**.
- All settings are planned to be stored locally (future enhancement: use DataStore or SharedPreferences).

---

## Navigation Structure
All navigation in the app is done using **Activities** and `Intent` objects. No fragments or Jetpack Navigation Component is used.

### Navigation Flow:
```
SplashActivity → LoginActivity → RegisterActivity (optional) → CreateCategoryActivity/MainActivity → AddExpenseActivity → ExpenseListActivity → SettingsActivity
```

---

## Tech Stack
- **Language**: Kotlin
- **Architecture**: MVVM (planned), currently Activity-based
- **Database**: Room Database
- **UI**: XML layouts
- **Git & GitHub

---

##  Contributors

<!-- Table showing who worked on what part -->
| Feature            | Developer        |
|--------------------|------------------|
| Designs            | Adrian Wiltshire |
| Splash Page        | Adrian Wiltshire |
| Login/Register Page| Adrian Wiltshire |
| CreateCategory Page| Adrian Wiltshire |
| AddExpense Screen  | Adrian Wiltshire |
| ExpenseList Page   | Milani Tshetu    |
| Settings Page      | Adrian Wiltshire |
| GitHub Setup & Final Integration | Adrian Wiltshire  |

---

## Directory Structure (Simplified)
```
- data/
  - User.kt (Entity)
  - UserDao.kt (Data Access Object)
  - AppDatabase.kt (Room Database instance)
  - Expense.kt
  - ExpenseDao
  - ExpenseDatabase.kt
  - ExpenseViewModel

- ui/
  - SplashActivity.kt
  - LoginActivity.kt
  - RegisterActivity.kt
  - CreateCategoryActivity.kt
  - SettingsActivity.kt
  - MainActivity.kt
  - AddExpenseActivity.kt
  - CategoryAdapter.kt
  - ExpenseListActivity.kt

- layout/
  - activity_splash.xml
  - activity_login.xml
  - activity_register.xml
  - activity_create_category.xml
  - activity_settings.xml
  - activity_expense_list.xml
  - item_expense.xml
```

---

## To Do / Future Enhancements
- Add category creation logic in `CreateCategoryActivity`
- Store settings changes using SharedPreferences or DataStore
- Implement password encryption
- Add budget tracking features
- Enable Google/Apple sign-in
- Create a summary dashboard with charts and reports

---

## Developer Notes
- The app is currently in the MVP (Minimum Viable Product) stage.
- No external dependencies have been added yet for simplicity.
- Focus was placed on creating a functional base with Activities and Room.

---

## License
This project is for educational and portfolio purposes. No commercial license applied yet.

---

## Author
BudgetBuddyX was created as a hands-on Android project using best practices with a beginner-friendly approach.

---

## GitHub Repository
[View the project on GitHub](https://github.com/AMWiltshire2003/BudgetBuddyX)

---

## Feedback
Suggestions or feedback are welcome to improve this project further.

---
