# BudgetBuddyX Android App - README

## Overview
BudgetBuddyX is a personal finance and budgeting Android app designed to help users manage income, expenses, and preferences.
The app is built entirely using Activities (not Fragments) in Kotlin and uses Room Database for local storage and data persistence.

---

## Features Implemented

###  Splash Screen
- Displays a splash screen at app launch.
- Navigates to Login after a short delay.

###  User Authentication
- RegisterActivity: User signs up with username, email, and password.- **Login Screen**: Authenticates users and navigates to the dashboard.
- LoginActivity: Users log in with credentials and are redirected to the main dashboard.
- Successful login stores user session using SharedPreferences.

###  Category Creation (MainActivity)
- Acts as the Dashboard after login.
- Users can create budgeting categories like "Food", "Transport", etc.
- Each category can be tapped to add expenses.

### Add Expense (AddExpenseActivity)
- Opens when a user taps on a category.
- Allows adding expenses with amount, note, and date.
- Saved expenses are linked to the selected category.

### Expense List (ExpenseListActivity)
- Displays a list of all expenses for a selected category using RecyclerView.
- Dynamically updates as new expenses are added.

### Budget Graphs (BudgetGraphActivity)
- Visualizes:
-  Daily spending (last 30 days) using a Bar Chart.
-  Category breakdown within a selected date range.
- Charts implemented using MPAndroidChart.
- Users can filter data by selecting a start and end date.

### Gamification & Rewards (GamificationActivity & BudgetKeeperActivity)
- Adds motivation to budgeting by awarding badges.
- Badges are displayed using a RecyclerView.
- Each badge is stored in a Room database table and shown dynamically.
- Entry point is the Gamification page where users can view or unlock badges and track budget streaks.

### Settings Page (SettingsActivity)
- Users can:
-  Change currency preference (e.g. ZAR, USD).
-  Select language (e.g. English).
-  Toggle notification settings.
-  Update their username and password.
- Preference data planned to be stored using SharedPreferences (or DataStore in future).

### Navigation Structure
- All navigation is implemented using Activities + Intents.
- No fragments or navigation components used.

### Flow:

SplashActivity
↓
LoginActivity
→ RegisterActivity (if user doesn’t have an account)
↓
MainActivity (Create Category Dashboard)
↓
AddExpenseActivity (per category)
↓
ExpenseListActivity
↓
BudgetGraphActivity
↓
GamificationActivity → BudgetKeeperActivity (Your Badges)
↓
SettingsActivity

---

### SharedPreferences
- Used to:
-  Store user session after login.
-  Skip login if already logged in.

### Tech Stack
- Language: Kotlin
- Architecture: Activity-based + Room + ViewModel (for Expense List)
- Database: Room
- UI: XML + MPAndroidChart + RecyclerView
- Dependency Tools: Gradle
- Version Control: Git + GitHub

---

##  Contributors

| Feature               | Developer                      |
|-----------------------|--------------------------------|
| App Design            | Adrian Wiltshire               |
| Splash/Login/Register | Adrian Wiltshire               |
| Create Category       | Adrian Wiltshire               |
| Add Expense           | Adrian Wiltshire               |
| Expense List          | Milani Tshetu                  |
| Graphs & Charts       | Adrian Wiltshire/Milani Tshetu |
| Settings Page         | Adrian Wiltshire               |
| Gamification/Badges   | Adrian Wiltshire               |
| GitHub Integration    | Adrian Wiltshire               |
| Youtube Video         | Adrian Wiltshire/Milani Tshetu |

---

## Directory Structure (Simplified)
```
- data/
  - User.kt
  - UserDao.kt
  - AppDatabase.kt
  - Expense.kt
  - ExpenseDao.kt
  - Badge.kt
  - BadgeDao.kt

- viewmodel/
  - ExpenseViewModel.kt

- adapter/
  - CategoryAdapter.kt
  - ExpenseAdapter.kt
  - BadgeAdapter.kt

- ui/
  - SplashActivity.kt
  - LoginActivity.kt
  - RegisterActivity.kt
  - MainActivity.kt (Category creation)
  - AddExpenseActivity.kt
  - ExpenseListActivity.kt
  - BudgetGraphActivity.kt
  - GamificationActivity.kt
  - BudgetKeeperActivity.kt
  - SettingsActivity.kt

- res/layout/
  - activity_splash.xml
  - activity_login.xml
  - activity_register.xml
  - activity_create_category.xml
  - activity_add_expense.xml
  - activity_expense_list.xml
  - activity_budget_graph.xml
  - activity_gamification.xml
  - activity_budget_keeper.xml
  - activity_settings.xml
  - item_expense.xml
  - item_badge.xml
  - item_category.xml

```

---

## To Do / Future Enhancements
- Store user preferences using SharedPreferences or DataStore.
- Add password encryption using hashing (e.g. BCrypt).
- Add PieCharts for income/spending ratio.
- Implement Google or Apple sign-in.
- Add daily reminders using Notifications.
- Add export functionality (PDF/CSV).
---

## Developer Notes
- Project is developed using Android Studio Dolphin & Kotlin.
- Focused on real-world app structure with MVP-ready features.
- Designed for both learning and portfolio presentation.
- No backend required—runs completely offline using Room.

---

## GitHub Repository
[View the project on GitHub](https://github.com/AMWiltshire2003/BudgetBuddyX)

## Feedback
Feel free to open an issue or fork the project if you have suggestions or want to contribute.

---

## License
This project is for educational purposes only. Licensing will be added if used commercially.

## Video Presentation link
https://youtu.be/0dpNkErQeyE
---

## Author
BudgetBuddyX was created as a hands-on Android project using best practices with a beginner-friendly approach.

---




