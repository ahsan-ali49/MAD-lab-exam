package com.example.myfirstproject

//import androidx.compose.foundation.layout.ColumnScopeInstance.weight
//import androidx.compose.foundation.layout.FlowColumnScopeInstance.weight
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.delay
import android.util.Xml
import org.xmlpull.v1.XmlPullParser


class PreferenceManager(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)

    fun saveString(key: String, value: String) {
        with(sharedPreferences.edit()) {
            putString(key, value)
            apply()
        }
    }

    fun getString(key: String, defaultValue: String): String =
        sharedPreferences.getString(key, defaultValue) ?: defaultValue
}


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val preferenceManager = PreferenceManager(this)
            MyApp(preferenceManager)
        }
    }
}

@Composable
fun MyApp(preferenceManager: PreferenceManager) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {
        composable(Screen.SplashScreen.route) {
            SplashScreen(navController)
        }
        composable(Screen.CitySelection.route) {
            CityScreen(navController) // Composable function for your second screen
        }
        composable(
            route = Screen.Details.route,
            arguments = listOf(navArgument("data") { type = NavType.StringType })
    ) { backStackEntry ->
            // Get the argument passed from the previous screen
            val data = backStackEntry.arguments?.getString("data") ?: ""
            DetailsScreen(navController, data)
    }
        // ... other composables
    }
//    val navController = rememberNavController()
//    NavHost(navController = navController, startDestination = Screen.Home.route) {
//        composable(Screen.Home.route) {
//            HomeScreen(navController)
//        }
//        composable(Screen.Details.route) {
//            DetailsScreen(navController)
//        }
//        composable(Screen.ToDoList.route){
//            ToDoListScreen(navController)
//        }
//        composable(Screen.Settings.route){
//            SettingsScreen(preferenceManager, navController)
//        }
//
//        composable(Screen.Responsive.route) {
//            ResponsiveLayoutScreen(navController)
//        }
//        // Define more screens here if necessary
//    }
}

@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(key1 = "splash") {
        delay(3000) // Delay for 3 seconds
        navController.navigate(Screen.CitySelection.route) {
            // Remove the splash screen from the back stack
            popUpTo(Screen.SplashScreen.route) { inclusive = true }
        }
    }

    // Display content for your splash screen here
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Sky Sight")
    }
}

@Composable
fun CityScreen(navController: NavController) {
    var text by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Enter some text to save") }
        )
        Button(
        onClick = {
            // Assume 'text' is a string that you want to pass to the Details screen
            val route = Screen.Details.createRoute(text)
            navController.navigate(route)
        }
        ) {
            Text("Show Weather")
        }
    }


}

@Composable
fun DetailsScreen(navController: NavController, data: String) {
//    val data = backStackEntry.arguments?.getString("data") ?: return
    Text(text = "City Name: $data")
}




//@Composable
//fun DetailsScreen(navController: NavController, data: String) {
//    // Use 'data' as needed in your DetailsScreen
//    // ...
//}



//Button(onClick = { navController.navigate(Screen.Details.createRoute(dataToSend)) }) {
//        Text("Go to Details")
//    }


//@Composable
//fun CityScreen(preferenceManager: PreferenceManager, navController: NavController) {
//    var text by remember { mutableStateOf("") }
//    var savedText by remember { mutableStateOf(preferenceManager.getString("saved_text", "")) }
//
//    Column {
//        TextField(
//            value = text,
//            onValueChange = { text = it },
//            label = { Text("Enter some text to save") }
//        )
//        Button(
//            onClick = {
//                preferenceManager.saveString("saved_text", text)
//                savedText = text // Update the state to show the saved text
//            }
//        ) {
//            Text("Save")
//        }
//        Text(text = "Saved text is: $savedText")
//        Button(onClick = { navController.navigate(Screen.Responsive.route) }) {
//            Text(text = "Check Layout")
//        }
//    }
//}


//@Composable
//fun HomeScreen(navController: NavController) {
//    Button(onClick = { navController.navigate(Screen.Details.route) }) {
//        Text("Go to Details")
//    }
//}

//@Composable
//fun DetailsScreen(navController: NavController) {
//    Button(onClick = { navController.navigate(Screen.ToDoList.route) }) {
//        Text(text = "ToDo List Screen")
//    }
//}

//@Composable
//fun ToDoListScreen(navController: NavController) {
//    // This will hold the list of to-do items and observe changes in state
//    val toDoItems = remember { mutableStateListOf<String>() }
//    var text by remember { mutableStateOf("") }
//
//    Column {
//        OutlinedTextField(
//            value = text,
//            onValueChange = { text = it },
//            label = { Text("Add a task") },
//            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
//            singleLine = true
//        )
//        Button(
//            onClick = {
//                if (text.isNotBlank()) {
//                    toDoItems.add(text)
//                    text = "" // Clear the input field
//                }
//            }
//        ) {
//            Text("Add")
//        }
//
//        // Display the list of tasks
//        toDoItems.forEach { task ->
//            Text(text = task)
//        }
//        Button(onClick = { navController.navigate(Screen.Settings.route) }) {
//            Text(text = "Data Storage Screen")
//        }
//    }
//}


//@Composable
//fun ResponsiveLayoutScreen(navController: NavController) {
//    val configuration = LocalConfiguration.current
//    val screenWidth = configuration.screenWidthDp.dp
//    val screenHeight = configuration.screenHeightDp.dp
//
//    // Determine if we're in portrait or landscape orientation
//    val isLandscape = screenWidth > screenHeight
//
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        if (isLandscape) {
//            // Landscape layout
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                SideContent(navController)
//                MainContent()
//            }
//        } else {
//            // Portrait layout
//            Column(
//                modifier = Modifier
//                    .fillMaxHeight()
//                    .padding(16.dp),
//                verticalArrangement = Arrangement.SpaceBetween
//            ) {
//                MainContent()
//                SideContent(navController)
//            }
//        }
//    }
//}

//@Composable
//fun MainContent() {
//    // Main content that takes more space
//    Column(
//        modifier = Modifier
//            .padding(8.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(text = "Main Content", style = MaterialTheme.typography.titleLarge)
//        // You can add more main content here
//    }
//}





//@Composable
//fun MyApp() {
//    val navController = rememberNavController()
//    NavHost(navController = navController, startDestination = Screen.Home.route) {
//        composable(Screen.Home.route) {
//            HomeScreen(navController)
//        }
//        composable(
//            route = Screen.Details.route,
//            arguments = listOf(navArgument("data") { type = NavType.StringType })
//        ) { backStackEntry ->
//            DetailsScreen(navController, backStackEntry)
//        }
//        // ... other composables
//    }
//}

//dependencies {
//    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0' // Use the latest version
//    // ... other dependencies ...
//}









//Code for Splash Screen:
//
//LaunchedEffect(Unit) {
//
//    delay(3000) // Wait for 3 seconds
//
//    // code to be called after 3 seconds
//
//}
//
//
//
//Get Dynamic Data from Strings
//
//val weatherInfo: List<String> = run {
//
//    val resourceId = resources.getIdentifier("weather_info_${cityName.lowercase()}", "array", context.packageName)
//
//    if (resourceId != 0) resources.getStringArray(resourceId).toList() else listOf("Info not available")
//
//}



data class Item(val name: String, val description: String)

fun parseXmlData(context: Context): List<Item> {
    val items = mutableListOf<Item>()
    val parser: XmlPullParser = context.resources.getXml(R.xml.strings)
    var eventType: Int = parser.eventType
    var name: String? = null
    var description: String? = null

    while (eventType != XmlPullParser.END_DOCUMENT) {
        when (eventType) {
            XmlPullParser.START_TAG -> {
                when (parser.name) {
                    "name" -> name = readText(parser)
                    "description" -> description = readText(parser)
                }
            }
            XmlPullParser.END_TAG -> {
                if (parser.name == "item" && name != null && description != null) {
                    items.add(Item(name, description))
                    name = null
                    description = null
                }
            }
        }
        eventType = parser.next()
    }
    return items
}

private fun readText(parser: XmlPullParser): String {
    var result = ""
    if (parser.next() == XmlPullParser.TEXT) {
        result = parser.text
        parser.nextTag()
    }
    return result
}
