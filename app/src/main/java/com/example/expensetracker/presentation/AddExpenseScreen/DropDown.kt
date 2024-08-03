package com.example.expensetracker.presentation.AddExpenseScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.expensetracker.utilities.CustomText


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownComposable(
    list: List<String>,
    modifier: Modifier = Modifier,
    onItemSelected: (String) -> Unit,

    ) {

    val expanded = remember {
        mutableStateOf(false)
    }
    val selectedItem = remember {
        mutableStateOf(list[0])
    }

    ExposedDropdownMenuBox(
        expanded = expanded.value,
        onExpandedChange = {
            expanded.value = it
        },
        // modifier = modifier
    ) {
        TextField(
            value = selectedItem.value,
            onValueChange = {},
            readOnly = true,
            modifier = modifier
                // .fillMaxWidth()
                .menuAnchor()
                .border(1.dp, Color.Black, RoundedCornerShape(8.dp)),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value)
            },
            colors = TextFieldDefaults.colors(
                disabledContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                focusedTextColor = Color.Black,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            )
        )
        ExposedDropdownMenu(
            expanded = expanded.value,
            onDismissRequest = {
                expanded.value = false
            },

            modifier = Modifier.background(Color.White)
        ) {
            list.forEach {
                DropdownMenuItem(text = {
                    CustomText(text = it)
                }, onClick = {
                    selectedItem.value = it
                    onItemSelected(selectedItem.value)
                    expanded.value = false
                })

            }
        }

    }

}

