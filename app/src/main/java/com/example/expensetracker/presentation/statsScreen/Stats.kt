package com.example.expensetracker.presentation.statsScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.expensetracker.R
import com.example.expensetracker.presentation.AddExpenseScreen.DatePickerComposable
import com.example.expensetracker.presentation.HomeScreen.EmptyPlaceHolder
import com.example.expensetracker.presentation.HomeScreen.TransactionItem
import com.example.expensetracker.ui.theme.greenColor
import com.example.expensetracker.ui.theme.interFamily
import com.example.expensetracker.utilities.Category
import com.example.expensetracker.utilities.CustomText
import com.example.expensetracker.utilities.FilterType
import com.example.expensetracker.utilities.SortOrder
import com.example.expensetracker.utilities.Util
import com.example.expensetracker.presentation.statsScreen.viewmodel.StatsViewModel
import com.example.expensetracker.presentation.statsScreen.viewmodel.StatsViewModelFactory
import com.example.expensetracker.utilities.getCategoryIcon
import java.util.Date

@Composable
fun Stats(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewmodel: StatsViewModel = viewModel(factory = StatsViewModelFactory(LocalContext.current))
) {
    //val viewmodel: StatsViewModel =
    //   StatsViewModelFactory(LocalContext.current).create(StatsViewModel::class.java)
    val filterTransaction by viewmodel.filteredTransaction.collectAsState()
    val groupData = filterTransaction.groupBy { it.category }
    val filteredCategories = mutableListOf<Category>()

    groupData.forEach { data ->
        Category.values().forEach cat@{
            Log.e("Donut-->", "data key:${data.key}\n" + "title: ${it.name}")
            if (data.key == it.name) {
                filteredCategories.add(it)
                return@cat
            }
        }
    }
    val amountList = groupData.map {
        it.value.sumOf { trx ->
            trx.amount
        }
    }
    val totalTrx = amountList.map { it.toFloat() }.sum()
    val percentProgress = amountList.map {
        it.toFloat() * 100 / totalTrx
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        val pickerVisibility = remember {
            mutableStateOf(false)
        }
        val dates = remember {
            mutableStateOf(Date())
        }
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)

        ) {
            val (topBar, filterRow, typeFilter, graph, date, transactionList) = createRefs()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp, start = 16.dp, end = 16.dp)
                    .constrainAs(topBar) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.chevron_left),
                    contentDescription = "",
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .clickable {
                            navController.popBackStack()
                        },
                    colorFilter = ColorFilter.tint(Color.Black)
                )
                CustomText(
                    text = "Statistics", color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center),
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_download),
                    contentDescription = "",
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
            Column(
                modifier = Modifier
                    .constrainAs(filterRow) {
                        top.linkTo(topBar.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(top = 20.dp)
            ) {
                FilterRow(viewmodel)
            }
            Box(
                modifier = Modifier
                    .constrainAs(typeFilter) {
                        top.linkTo(filterRow.bottom)
                        end.linkTo(parent.end)
                    }
                    .padding(top = 26.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
            ) {
                StatTabBar(statsViewModel = viewmodel)
            }
            Column(
                Modifier
                    .constrainAs(graph) {
                        top.linkTo(typeFilter.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
              //      .height(300.dp)
            ) {
               if (filterTransaction.isEmpty())
                    EmptyPlaceHolder("No transaction.\n Tap the '+' button on the home menu to get started.",
                        Modifier.wrapContentHeight())
                Column(
                    Modifier.padding(bottom = 16.dp)
                ) {
                    if (filterTransaction.isNotEmpty())
                        DonutChart(filteredCategories, percentProgress)
                }
            }

            val selectedFilter by viewmodel.selectedFilter.collectAsState()
            Column(
                modifier = Modifier.constrainAs(date){
                    top.linkTo(graph.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            ) {
                if(selectedFilter==FilterType.DAY)
                Row(Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center) {
                    TextButton(onClick = {
                        pickerVisibility.value=true
                    }) {
                        val dateValue=Util.dateToReadFormat(dates.value)
                        CustomText(text = "Select date ${dateValue}")
                    }
                }
            }

            Column(
                modifier = Modifier.constrainAs(transactionList){
                    top.linkTo(date.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            ) {
                if (filterTransaction.isNotEmpty())
                    LazyColumn(
                        Modifier
                            .padding(horizontal = 12.dp)
                    ) {
                        item {
                            ListTitle(viewmodel)
                        }

                        items(filterTransaction) { item ->
                            val image = getCategoryIcon(item.category)
                            TransactionItem(
                                image = image,
                                title = item.title,
                                amount = item.amount.toString(),
                                date = Util.dateToReadFormat(item.date),
                            )
                        }
                    }
            }

        }


        if (pickerVisibility.value) {
            DatePickerComposable(onDateSelected = {
               // date.value = it
                Log.e("selected date-->", it.toString())
                pickerVisibility.value = false
                viewmodel.currentDate(it)
            },
                onDismissRequest = {
                    pickerVisibility.value = false
                })
        }
    }


}

@Composable
private fun FilterRow(viewmodel: StatsViewModel = viewModel()) {

    val selectedFilter by viewmodel.selectedFilter.collectAsState()

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(FilterType.entries.toTypedArray()) { filterType ->
            FilterCard(
                text = filterType.name,
                isSelected = filterType == selectedFilter,
                onClick = {
                    viewmodel.updateFilter(filterType)
                }
            )
        }
    }
}

@Composable
private fun FilterCard(
    text: String?,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .wrapContentWidth()
            .clickable {
                onClick()
            },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) greenColor else Color.White,
            contentColor = if (isSelected) Color.White else Color.Black
        )
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 26.dp, vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            CustomText(
                text = text!!,
                fontWeight = FontWeight.Normal,
                fontSize = 13.sp
            )
        }
    }
}


@Composable
fun ListTitle(viewModel: StatsViewModel = viewModel()) {
    val sortOrder by viewModel.sortOrder.collectAsState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 22.dp, horizontal = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Top Spending",
            fontFamily = interFamily,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
        Row {
            Text(
                text = if (sortOrder == SortOrder.LOW_TO_HIGH) "Low to High" else "High to Low",
                fontFamily = interFamily,
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier
                    .padding(end = 8.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_filter_expense),
                contentDescription = "",
                modifier = Modifier
                    .clickable {
                        viewModel.updateSortOrder(
                            if (sortOrder == SortOrder.LOW_TO_HIGH) SortOrder.HIGH_TO_LOW else SortOrder.LOW_TO_HIGH
                        )
                    }
            )
        }
    }
}
