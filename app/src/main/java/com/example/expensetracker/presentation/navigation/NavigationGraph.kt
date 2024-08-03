package com.example.expensetracker.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.expensetracker.presentation.AddExpenseScreen.AddExpense
import com.example.expensetracker.presentation.HomeScreen.HomeScreen
import com.example.expensetracker.presentation.Profile.ProfileScreen
import com.example.expensetracker.presentation.statsScreen.Stats
import com.example.expensetracker.presentation.Profile.Wallet

@Composable
fun NavigationGraph(navController: NavHostController) {
//    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Home.route!!) {

        composable(route = Screen.Home.route,
        ) {
            HomeScreen(navController = navController)
        }

        composable(
            route = Screen.Profile.route!!,
        ) {
            ProfileScreen(navController)
        }
        composable(
            route = Screen.AddExpense.route!!,
        ) {
            AddExpense(navController = navController)
        }
        composable(
            route = Screen.Wallet.route!!,

        ) {
            Wallet()
        }
        composable(
            route = Screen.Stats.route!!,

        ) {
            Stats(navController)
        }
    }
}


@Preview
@Composable
private fun Nav() {
    NavigationGraph(navController = rememberNavController())
}