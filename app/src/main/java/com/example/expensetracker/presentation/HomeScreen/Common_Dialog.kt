package com.example.expensetracker.presentation.HomeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensetracker.ui.theme.food_drink
import com.example.expensetracker.utilities.CustomText



@Composable
fun MyAlertDialog(shouldShowDialog: MutableState<Boolean>,onConfirm: () -> Unit) {
    if (shouldShowDialog.value) {
        AlertDialog(
            modifier = Modifier.fillMaxWidth(),
            onDismissRequest = {
                shouldShowDialog.value = false
            },

            title = { CustomText(text = "Warning!!",
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp)},
            text = { CustomText(text = "Click Confirm to delete the transaction",
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp) },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = food_drink,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.clip(RoundedCornerShape(8.dp)),
                    onClick = {
                        shouldShowDialog.value = false
                        onConfirm()
                    }
                ) {
                    CustomText(
                        text = "Confirm",
                        color = Color.White
                    )
                }
            }
        )
    }
}