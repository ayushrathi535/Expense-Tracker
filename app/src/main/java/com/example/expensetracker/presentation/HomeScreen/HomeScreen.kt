package com.example.expensetracker.presentation.HomeScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.expensetracker.R
import com.example.expensetracker.data.ExpenseEntity
import com.example.expensetracker.ui.theme.greenColor
import com.example.expensetracker.ui.theme.interFamily
import com.example.expensetracker.utilities.CustomText
import com.example.expensetracker.utilities.Util
import com.example.expensetracker.presentation.HomeScreen.viewmodel.HomeViewModel
import com.example.expensetracker.presentation.HomeScreen.viewmodel.HomeViewModelFactory
import com.example.expensetracker.ui.theme.GreenAlpha700
import com.example.expensetracker.utilities.getCategoryIcon


const val TAG = "HomeScreen"

@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavHostController) {

    val homeViewModel: HomeViewModel =
        HomeViewModelFactory(LocalContext.current).create(HomeViewModel::class.java)

    Surface(modifier = modifier
        .fillMaxSize()
       ) {

        ConstraintLayout(modifier = modifier.fillMaxSize()) {

            val (list, nameRow,cardShadow, card, topBar, addButton) = createRefs()

            Image(painter = painterResource(id = R.drawable.top_background),
                contentDescription = null,
                modifier
                    .constrainAs(topBar) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxWidth()
            )

            Box(modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(
                    top = 40.dp, start = 16.dp, end = 16.dp,
                    bottom = 16.dp
                )
                .constrainAs(nameRow) {
                    top.linkTo(topBar.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }) {
                Column {
                    CustomText(
                        text = "Good Afternoon",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                    CustomText(
                        text = "Ayush Rathi",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )

                }
                Image(
                    painter = painterResource(id = R.drawable.ic_bell),
                    contentDescription = null,
                    modifier.align(Alignment.CenterEnd)
                )

            }

            val state = homeViewModel.expense.collectAsState(initial = emptyList())
            val income = homeViewModel.getTotalIncome(state.value).toString()
            val expense = homeViewModel.getTotalExpense(state.value).toString()
            val balance = homeViewModel.getBalance(state.value).toString()


            CardShadow(modifier.constrainAs(cardShadow) {
                top.linkTo(nameRow.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })
            CardItem(
                modifier.constrainAs(card) {
                    top.linkTo(nameRow.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                income, expense, balance
            )


               Column(modifier.constrainAs(list) {
                    top.linkTo(card.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }) {
                    if (state.value.isNotEmpty())
                    TransactionList(list = state.value)
                    else
                    EmptyPlaceHolder()
                }
//            Image(imageVector = Icons.Default.Add, contentDescription ="add button",
//                modifier = modifier.constrainAs(addButton){
//                    bottom.linkTo(parent.bottom)
//                    end.linkTo(parent.end)
//                }
//                    .size(50.dp)
//                    .clickable {
//                    navController.navigate("/addExpense")
//                }
//                    .padding(bottom = 24.dp, end = 24.dp)
//            )


        }
    }
}




@Composable
fun CardShadow(modifier: Modifier = Modifier,) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(16.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(GreenAlpha700)
            .padding(16.dp)
    ) {

    }

}



@Composable
fun CardItem(modifier: Modifier = Modifier, income: String, expense: String, balance: String) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(horizontal = 16.dp, vertical = 24.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(greenColor)
            .padding(16.dp)
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f)

        ) {
            Column(
                modifier = modifier.align(Alignment.CenterStart)
            ) {
                CustomText(
                    text = "Total Balance",
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = interFamily, fontSize = 16.sp, color = Color.White
                )
                CustomText(
                    text = balance, fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = interFamily,
                    color = Color.White
                )
            }

            Image(
                painter = painterResource(id = R.drawable.ic_dots),
                contentDescription = null,
                Modifier.align(Alignment.CenterEnd)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            CardRowItem(
                modifier = Modifier.align(Alignment.CenterStart),
                image = R.drawable.ic_down_arrow,
                text = "Income",
                number = income
            )

            CardRowItem(
                modifier = Modifier.align(Alignment.CenterEnd),
                image = R.drawable.ic_up_arrow,
                text = "Expense",
                number = expense
            )
        }
    }
}


@Composable
fun CardRowItem(modifier: Modifier, image: Int, text: String, number: String) {

    Column(
        modifier = modifier
    ) {
        Row {
            Image(
                painter = painterResource(id = image),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(8.dp))
            CustomText(
                text = text, fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = interFamily, color = Color.White
            )
        }
        CustomText(
            text = number, fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = interFamily,
            color = Color.White
        )
    }


}
@Composable
fun TransactionList(modifier: Modifier = Modifier, list: List<ExpenseEntity>) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                CustomText(
                    text = "Transactions History", fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
                CustomText(
                    text = "See All", fontSize = 16.sp,
                    fontWeight = FontWeight.Normal, color = Color.Black,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
        }

        items(list) { item ->
            val image = getCategoryIcon(item.category)
            Log.e(TAG, "TransactionList: $item")
            TransactionItem(
                image = image,
                title = item.title,
                amount = item.amount.toString(),
                date = Util.dateToReadFormat(item.date),
                color = if (item.accountType == "Income") Color.Green else Color.Red
            )
        }
    }
}


@Preview
@Composable
private fun HomeScreenPreview() {

    HomeScreen(navController = rememberNavController())
}