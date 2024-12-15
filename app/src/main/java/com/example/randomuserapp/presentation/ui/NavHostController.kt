package com.example.randomuserapp.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.randomuserapp.data.model.User
import com.example.randomuserapp.presentation.ui.screen.UserDetailsScreen
import com.example.randomuserapp.presentation.ui.screen.UserListScreen
import com.example.randomuserapp.presentation.viewmodel.UserListViewModel
import com.google.gson.Gson

@Composable
fun NavHostController(
    modifier: Modifier = Modifier,
    homeViewModel: UserListViewModel = hiltViewModel()
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        composable("home") {
            UserListScreen (
                viewModel = homeViewModel,
                onUserSelected = { user ->
                    navController.navigate("details?user=${Gson().toJson(user)}")
                }
            )
        }
        composable(
            route = "details?user={user}",
            arguments = listOf(
                navArgument("user") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val user = Gson().fromJson(backStackEntry.arguments?.getString("user"), User::class.java)
            UserDetailsScreen(user = user)
        }
    }
}
