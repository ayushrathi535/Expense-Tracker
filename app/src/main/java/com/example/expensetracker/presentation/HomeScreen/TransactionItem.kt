package com.example.expensetracker.presentation.HomeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensetracker.R
import com.example.expensetracker.ui.theme.interFamily
import com.example.expensetracker.utilities.CustomText


@Composable
fun TransactionItem(
    image: Int= R.drawable.paypal,
    title: String,
    amount: String,
    date: String,
    color: Color = Color.Red,
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Row(
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .padding(8.dp)
                    .background(Color.LightGray)
            )

            Spacer(modifier = Modifier.width(8.dp))
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                CustomText(
                    text = title, fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = interFamily, color = Color.Black
                )
                CustomText(
                    text = date, fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = interFamily, color = Color.Gray
                )
            }
        }
        CustomText(
            text = amount, fontSize = 17.sp, color = color,
            fontFamily = interFamily,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.CenterEnd)
        )

    }
}

