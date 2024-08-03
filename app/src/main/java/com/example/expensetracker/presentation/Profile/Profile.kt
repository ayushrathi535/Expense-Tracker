package com.example.expensetracker.presentation.Profile

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.expensetracker.R
import com.example.expensetracker.presentation.HomeScreen.MyAlertDialog
import com.example.expensetracker.presentation.HomeScreen.viewmodel.HomeViewModel
import com.example.expensetracker.presentation.HomeScreen.viewmodel.HomeViewModelFactory
import com.example.expensetracker.presentation.statsScreen.viewmodel.StatsViewModel
import com.example.expensetracker.presentation.statsScreen.viewmodel.StatsViewModelFactory
import com.example.expensetracker.ui.theme.greenColor
import com.example.expensetracker.utilities.CustomText


@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewmodel: HomeViewModel = viewModel(factory = HomeViewModelFactory(LocalContext.current)),
    modifier: Modifier = Modifier
) {


    Surface(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        ConstraintLayout(modifier = modifier.fillMaxSize()) {
            val (topBar, nameRow, profileCard, profileDetail, list) = createRefs()

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
                    text = "Profile", color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_dots),
                    contentDescription = "",
                    modifier = Modifier.align(Alignment.CenterEnd)
                )

            }

            Box(
                modifier = Modifier
                    .constrainAs(profileCard) {
                        top.linkTo(topBar.bottom)
                        bottom.linkTo(topBar.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .clip(CircleShape)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.avatar),
                    contentDescription = "profile pic",
                    modifier = Modifier
                        .size(164.dp)
                        .align(Alignment.Center)
                )
            }

            Box(
                modifier = Modifier
                    .constrainAs(profileDetail) {
                        top.linkTo(profileCard.bottom)
                        start.linkTo(profileCard.start)
                        end.linkTo(profileCard.end)
                    }

            ) {
                UserDetail()
            }

            Column(
                modifier = Modifier
                    .constrainAs(list) {
                        top.linkTo(profileDetail.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                ProfileItems(viewmodel)
            }


        }

    }
}


@Composable
private fun ProfileItems(viewmodel: HomeViewModel) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .padding(9.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF0F6F5))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.diamond), contentDescription = "",
                    modifier = Modifier
                        .padding(6.dp)
                        .size(26.dp)
                )
            }

            CustomText(
                text = "Invite Friends", fontWeight = FontWeight.Medium,
                fontSize = 16.sp, color = Color.Black
            )
        }
        Spacer(
            modifier = Modifier
                .padding(vertical = 6.dp, horizontal = 16.dp)
                .height(1.dp)
                .fillMaxWidth()
                .background(Color.LightGray)

        )
        ProfileMenuItem(
            Icons.Filled.Favorite, "Set Currency",
        ) {

        }
        Spacer(modifier = Modifier.height(6.dp))
        ProfileMenuItem(Icons.Filled.AccountCircle, "Personal Profile") {

        }
        Spacer(modifier = Modifier.height(6.dp))
        ProfileMenuItem(Icons.Filled.Email, "Message Center") {

        }
        Spacer(modifier = Modifier.height(6.dp))
        ProfileMenuItem(Icons.Filled.Build, "Login And Security") {

        }
        Spacer(modifier = Modifier.height(6.dp))

        val shouldShowDialog = remember { mutableStateOf(false) } // 1
        val context = LocalContext.current
        if (shouldShowDialog.value) {
            MyAlertDialog(shouldShowDialog = shouldShowDialog) {
                if (viewmodel.deleteDatabase()) {
                    Toast.makeText(context, "Data erased", Toast.LENGTH_SHORT).show()
                }
            }
        }
        ProfileMenuItem(
            Icons.Filled.Refresh,
            "Erase Data",
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(Color.Red),
            color = Color.White
        ) {
            shouldShowDialog.value = true
        }
        Spacer(modifier = Modifier.height(6.dp))
    }
}

@Composable
private fun ProfileMenuItem(
    imageVector: ImageVector? = Icons.Filled.Settings,
    menuTitle: String = "Settings",
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    onClick: () -> Unit
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clickable {
                onClick()
            },
        horizontalArrangement = Arrangement.spacedBy(18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = imageVector!!, contentDescription = "",
            tint = color
        )

        CustomText(
            text = menuTitle, fontWeight = FontWeight.Medium,
            fontSize = 16.sp, color = color
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun UserDetail(
    name: String = "Ayush Rathi",
    email: String = "ayush123@gmail.com",
    modifier: Modifier = Modifier
) {

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomText(
            text = "Ayush Rathi", fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        CustomText(
            text = "ayush123@gmail.com", fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = greenColor
        )
    }
}


@Composable
fun Wallet(modifier: Modifier = Modifier) {

    Column(Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        CustomText(text = "In Progress")
    }
}