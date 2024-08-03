package com.example.expensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.expensetracker.presentation.navigation.BottomBarWithFab
import com.example.expensetracker.presentation.navigation.NavigationGraph
import com.example.expensetracker.ui.theme.ExpenseTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExpenseTrackerTheme {
                BottomBarWithFab()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Main() {

    ExpenseTrackerTheme {
       BottomBarWithFab()
    }

}
