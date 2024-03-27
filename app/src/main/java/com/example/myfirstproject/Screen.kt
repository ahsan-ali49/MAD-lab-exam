package com.example.myfirstproject

sealed class Screen(val route: String) {

    object SplashScreen : Screen("splash_screen")
    object CitySelection : Screen("city_selection")
//    object Home : Screen("home_screen")
//    object Details : Screen("details_screen")
//    object ToDoList : Screen("todo_list_screen")
//    object Settings : Screen("settings_screen")
    object Details : Screen("details_screen/{data}") { // Here "data" is the argument name
        fun createRoute(data: String) = "details_screen/$data"
    }
    //sealed class Screen(val route: String) {
//    object SplashScreen : Screen("splash_screen")
//    object SecondScreen : Screen("second_screen")
//    // ... other screens
//}
//    object Responsive : Screen("responsive_screen")
}


//sealed class Screen(val route: String) {
//    object SplashScreen : Screen("splash_screen")
//    object SecondScreen : Screen("second_screen")
//    // ... other screens
//}