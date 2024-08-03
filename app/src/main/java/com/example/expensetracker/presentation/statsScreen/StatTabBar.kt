package com.example.expensetracker.presentation.statsScreen

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.expensetracker.ui.theme.GreenAlpha700
import com.example.expensetracker.ui.theme.Red500
import com.example.expensetracker.utilities.TransactionType
import com.example.expensetracker.presentation.statsScreen.viewmodel.StatsViewModel
@Composable
fun StatTabBar(
    cornerRadius: Dp = 24.dp,
    statsViewModel: StatsViewModel = viewModel()
) {
    val selectedType by statsViewModel.selectedType.collectAsState()
    Surface(
        color = Color.DarkGray.copy(alpha = 0.1f),
        shape = RoundedCornerShape(cornerRadius)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 8.dp,
                    end = 8.dp
                ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            InsightBar(
                modifier = Modifier.weight(1f),
                cornerRadius = cornerRadius,
                onTabClick = {
                    statsViewModel.updateType(TransactionType.INCOME)
                },
                title = "Income",
                backgroundColor = animateColorAsState(
                    targetValue = if (selectedType == TransactionType.INCOME)
                        GreenAlpha700 else Color.Transparent
                ).value,
                textColor = if (selectedType == TransactionType.INCOME)
                    Color.White else Color.Black
            )
            InsightBar(
                modifier = Modifier.weight(1f),
                cornerRadius = cornerRadius,
                onTabClick = {
                    statsViewModel.updateType(TransactionType.EXPENSE)
                },
                title = "Expense",
                backgroundColor = animateColorAsState(
                    targetValue = if (selectedType == TransactionType.EXPENSE)
                        Red500 else Color.Transparent
                ).value,
                textColor = if (selectedType == TransactionType.EXPENSE)
                    Color.White else Color.Black
            )
        }
    }
}

@Composable
fun InsightBar(
    modifier: Modifier,
    cornerRadius: Dp,
    onTabClick: () -> Unit,
    title: String,
    backgroundColor: Color,
    textColor: Color
) {
    TextButton(
        onClick = onTabClick,
        modifier = modifier
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(cornerRadius),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = backgroundColor,
            contentColor = textColor
        )
    ) {
        Text(
            text = title,
            color = textColor,
            modifier = Modifier
                .padding(
                    horizontal = 6.dp,
                    vertical = 4.dp
                )
                .align(Alignment.CenterVertically)
        )
    }
}