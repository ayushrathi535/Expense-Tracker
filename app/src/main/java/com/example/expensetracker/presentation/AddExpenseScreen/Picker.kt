package com.example.expensetracker.presentation.AddExpenseScreen

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import com.example.expensetracker.utilities.CustomText
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerComposable(
    onDateSelected: (Date) -> Unit,
    onDismissRequest: () -> Unit
) {
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis

    DatePickerDialog(
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(onClick = {
                selectedDate?.let { onDateSelected(Date(it)) }
                onDismissRequest()
            }) {
                CustomText(text = "Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDismissRequest()
            }) {
                CustomText(text = "Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

