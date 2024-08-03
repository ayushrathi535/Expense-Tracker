package com.example.expensetracker.presentation.AddExpenseScreen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.expensetracker.R
import com.example.expensetracker.data.ExpenseEntity
import com.example.expensetracker.presentation.HomeScreen.TAG
import com.example.expensetracker.presentation.navigation.Screen
import com.example.expensetracker.ui.theme.greenColor
import com.example.expensetracker.utilities.CustomText
import com.example.expensetracker.presentation.AddExpenseScreen.viewmodel.AddExpenseViewModelFactory
import com.example.expensetracker.presentation.AddExpenseScreen.viewmodel.ExpenseViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpense(modifier: Modifier = Modifier, navController: NavHostController) {
    val viewModel =
        AddExpenseViewModelFactory(LocalContext.current).create(ExpenseViewModel::class.java)

    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    val category by viewModel.category.collectAsState()

    Surface(modifier = modifier.fillMaxSize()) {

        ConstraintLayout(modifier = modifier.fillMaxSize()) {

            val (list, nameRow, card, topBar) = createRefs()

            Image(painter = painterResource(id = R.drawable.top_background),
                contentDescription = null,
                modifier.constrainAs(topBar) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )

            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp, start = 16.dp, end = 16.dp)
                .constrainAs(nameRow) {
                    top.linkTo(topBar.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)

                }) {
                Image(
                    painter = painterResource(id = R.drawable.chevron_left),
                    contentDescription = "",
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .clickable {
                            navController.popBackStack()
                        }
                )

                CustomText(
                    text = "Add Expense", color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_dots),
                    contentDescription = "",
                    modifier = Modifier.align(Alignment.CenterEnd)
                )

            }

            InputForm(
                addViewModel = viewModel,
                modifier = Modifier
                    .constrainAs(card) {
                        top.linkTo(nameRow.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)

                    }
                    .padding(top = 60.dp),
                onExpenseAdded = {
                    coroutineScope.launch {
                        Log.e("entity-->", it.toString())
                        //         if (viewModel.addExpense(it)) {
                        viewModel.addExpense(it)
                        navController.navigate(Screen.Home.route!!)
                        //}
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalUnitApi::class)
@Composable
fun InputForm(
    addViewModel: ExpenseViewModel = viewModel(),
    modifier: Modifier,
    onExpenseAdded: (ExpenseEntity) -> Unit
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    val categories by addViewModel.category.collectAsState()

    val name = remember {
        mutableStateOf("")
    }
    val amount = remember {
        mutableStateOf("")
    }
    val date = remember {
        mutableStateOf(Date())
    }
    val pickerVisibility = remember {
        mutableStateOf(false)
    }

//    val category by viewModel.category.collectAsState()

    val type = remember {
        mutableStateOf("Income")
    }



    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .shadow(16.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        if (showBottomSheet) {
            if (type.value=="Expense")
            ModalBottomSheet(
                containerColor = Color.LightGray,
                onDismissRequest = {
                    scope.launch {
                        showBottomSheet =
                            false
                    }
                },
                sheetState = sheetState,
            ) {

                CategoryChooser(viewModel = addViewModel) {
                    // showBottomSheet=false
                }

            }
        }

        CustomText(
            text = "Type", fontWeight = FontWeight.Medium,
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        val typeList = listOf("Income", "Expense")
        DropdownComposable(list = typeList, modifier = Modifier.fillMaxWidth()) {
            type.value = it
        }

        Spacer(modifier = Modifier.height(16.dp))


        CustomText(
            text = "Name", fontWeight = FontWeight.Medium,
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = name.value, onValueChange = {
                name.value = it
            },
            colors = OutlinedTextFieldDefaults.colors(
                disabledBorderColor = Color.Black,
                disabledTextColor = Color.Black
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))


        CustomText(
            text = "Amount", fontWeight = FontWeight.Medium,
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = amount.value, onValueChange = {
                amount.value = it
            },
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_wallet_outlined),
                    contentDescription = null,
                    tint = Color.Black
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                disabledBorderColor = Color.Black,
                disabledTextColor = Color.Black
            ), modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        CustomText(
            text = "Date", fontWeight = FontWeight.Medium,
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        val dateString = remember(date.value) {
            SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.getDefault()
            ).format(date.value)
        }
        OutlinedTextField(
            value = dateString,
            onValueChange = {// TODO:

            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Outlined.DateRange, "",
                    tint = Color.Black,
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    pickerVisibility.value = true
                },
            colors = OutlinedTextFieldDefaults.colors(
                disabledBorderColor = Color.Black,
                disabledTextColor = Color.Black,
                focusedBorderColor = greenColor,
                unfocusedBorderColor = Color.Black
            ), enabled = false
        )

        Spacer(modifier = Modifier.height(16.dp))
        CustomText(
            text = "Category", fontWeight = FontWeight.Medium,
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.height(4.dp))

        Button(
            onClick = {
                showBottomSheet = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
        ) {
            Text(text = categories.name)
        }


//        DropdownComposable(list = list, modifier = Modifier.fillMaxWidth()) {
//            category.value = it
//        }

        Spacer(modifier = Modifier.height(16.dp))


        CustomText(
            text = "Invoice", fontWeight = FontWeight.Medium,
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.height(4.dp))

        var buttonColor = if (type.value == "Income") Color.Green
        else Color.Red
        val context = LocalContext.current
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                .background(Color.White, RoundedCornerShape(8.dp))
                .padding(8.dp)
                .clickable {
                    val expense = ExpenseEntity(
                        title = name.value,
                        amount = amount.value.toDoubleOrNull() ?: 0.0,
                        date = date.value,
                        category = categories.name,
                        accountType = type.value
                    )
                    if (name.value.isNotEmpty() && amount.value.isNotEmpty())
                        onExpenseAdded(expense)
                    else
                        Toast
                            .makeText(context, "please fill empty fields", Toast.LENGTH_SHORT)
                            .show()
                    Log.e("on click -->", "click btn succes")
                },
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                //.background(buttonColor)

            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = "",
                    modifier = Modifier.padding(end = 8.dp),
                    tint = Color.Black
                )
                CustomText(
                    text = "Add Invoice",
                    color = Color.Black,
                )
            }
        }
//        Button(modifier = Modifier.fillMaxWidth(),
//            onClick = {
//                val expense = ExpenseEntity(
//                    title = name.value,
//                    amount = amount.value.toDoubleOrNull() ?: 0.0,
//                    date = Util.dateToReadFormat(date.value),
//                    category = category.value,
//                    type = type.value
//                )
//                onExpenseAdded(expense)
//
//            }) {
//            CustomText(text = "Add Invoice")
//        }
    }

    if (pickerVisibility.value) {
        DatePickerComposable(onDateSelected = {
            date.value = it
            Log.e("selected date-->", it.toString())
            pickerVisibility.value = false
        },
            onDismissRequest = {
                pickerVisibility.value = false
            })
    }
}


@Preview()
@Composable
private fun DropdownPreview() {

    val list = listOf("Food", "Shopping", "Travel")
    DropdownComposable(list = list) { item ->
        Log.e(TAG, "DropdownPreview: $item")
    }

}
