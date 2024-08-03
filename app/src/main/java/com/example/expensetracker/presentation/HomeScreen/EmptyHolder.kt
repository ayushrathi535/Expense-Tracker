package com.example.expensetracker.presentation.HomeScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.expensetracker.R
import com.example.expensetracker.utilities.CustomText
import com.example.expensetracker.utilities.spacing

@Composable
fun EmptyPlaceHolder(
    label: String =
        "No transaction has been made so far.\n Tap the '+' button to  get started",
    modifier: Modifier=Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.padding(
           horizontal = 26.dp,
            vertical = 36.dp
        )
    ) {
//        Icon(
//            painter = painterResource(R.drawable.blank_list),
//            tint = Color.White,
//            contentDescription = "no item added"
//        )

        CustomText(
            text = label,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}