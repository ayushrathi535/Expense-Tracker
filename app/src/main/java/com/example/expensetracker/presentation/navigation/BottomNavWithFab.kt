package com.example.expensetracker.presentation.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.expensetracker.ui.theme.greenColor

@Composable
fun BottomBarWithFab() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BackHandler(enabled = currentRoute != Screen.Home.route) {
        navController.navigate(Screen.Home.route!!) {
            // Pop up to the home screen
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    Scaffold(
        bottomBar = {
            Box {
//                AnimatedVisibility(
//                    visible = true,
//                    enter = slideInVertically(initialOffsetY = { it }),
//                    exit = slideOutVertically(targetOffsetY = { it })
//                ) {
                    BottomNavigationBar(navController)
               // }
                if (currentRoute == Screen.Home.route) {
                    Box(
                        modifier = Modifier
                            .size(78.dp)
                            .offset(y = -48.dp)
                            .background(
                                color = Color(0xFFFFFFFF),
                                shape = CircleShape
                            )
                            .align(Alignment.Center)

                    ) {
                        FloatingActionButton(
                            shape = CircleShape,
                            containerColor = greenColor,
                            contentColor = Color.White,
                            onClick = {
                                Screen.AddExpense.route?.let {
                                    navController.navigate(it) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
                            modifier = Modifier
                                .size(65.dp)
                                .align(Alignment.Center)

                        ) {
                            Icon(
                                painter = painterResource(id = Screen.AddExpense.selectedIcon!!),
                                contentDescription = "Add icon",
                                modifier = Modifier.size(34.dp),
                            )
                        }
                    }
                }
            }

        }
    ) {
        Box(Modifier.padding(it)) {
            NavigationGraph(navController)
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val items = listOf(
        Screen.Home,
        Screen.Stats,
        Screen.Wallet,
        Screen.Profile
    )

    NavigationBar(
        modifier = Modifier
            .shadow(8.dp)
            .fillMaxWidth(),
        containerColor = Color.White,
        contentColor = Color.Gray,
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route
            val color by animateColorAsState(
                targetValue = if (isSelected) greenColor else Color.Gray,
                animationSpec = tween(durationMillis = 300)
            )
            val iconSize by animateDpAsState(
                targetValue = if (isSelected) 32.dp else 30.dp,
                animationSpec = tween(durationMillis = 300)
            )


            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.White,
                    selectedTextColor = color,
                    selectedIconColor = color,
                    unselectedTextColor = color,
                    unselectedIconColor = color,
                    disabledIconColor = Color.Black,
                ),
                alwaysShowLabel = false,
                icon = {
                    val icon = if (isSelected) item.selectedIcon else item.unselectedIcon
                    Icon(
                        painter = painterResource(id = icon!!), contentDescription = "item.title",
                        modifier = Modifier.size(iconSize)
                    )
                },
//                label = { item.title?.let { Text(it) } },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route!!) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }
}

