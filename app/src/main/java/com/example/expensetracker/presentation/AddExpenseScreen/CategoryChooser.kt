package com.example.expensetracker.presentation.AddExpenseScreen


import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.expensetracker.R
import com.example.expensetracker.utilities.Category
import com.example.expensetracker.utilities.CustomText
import com.example.expensetracker.presentation.AddExpenseScreen.viewmodel.ExpenseViewModel


@OptIn(ExperimentalLayoutApi::class)
@ExperimentalUnitApi
@Composable
fun CategoryChooser(
    expenseItems: Array<Category> = Category.values(),
    viewModel: ExpenseViewModel,
    onClick :()->Unit
) {
    FlowRow(
        maxItemsInEachRow = 4,
        modifier = Modifier.padding(
            start = 8.dp,
            top = 8.dp,
            bottom = 8.dp,
        ),
    ) {
        expenseItems.forEach {
            CategoryTag(category = it,
                viewModel=viewModel){
                onClick()
            }
        }
    }
}

@ExperimentalUnitApi
@Composable
fun CategoryTag(category: Category, viewModel: ExpenseViewModel = viewModel(),
                onClick: () -> Unit) {
    val selected by viewModel.category.collectAsState()
    var isSelected = selected.title == category.title
    TextButton(
        modifier = Modifier.padding(8.dp),
        onClick = {
            viewModel.selectCategory(category)
            isSelected = selected.title == category.title
            onClick()
        },
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(
            horizontal = 10.dp,
            vertical = 6.dp
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) {
                category.bgRes.copy(alpha = 0.95f)
            } else Color.White,
            contentColor = if (isSelected) {
                category.colorRes
            } else Color.Black,

        ),
    ) {
        Icon(
            painter = if (!isSelected) {
                painterResource(id = R.drawable.ic_add)
            } else painterResource(id = category.iconRes),
            contentDescription = category.title,
        )
        Spacer(modifier = Modifier.width(6.dp))
        CustomText(
            text = category.title,

        )
    }
}
