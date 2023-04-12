package com.nyika.kamili.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nyika.kamili.R
import com.nyika.kamili.data.BottomNavData
import com.nyika.kamili.ui.theme.KamiliMustard
import com.nyika.kamili.utils.Routes

@Composable
fun BottomNav(
    navController: NavController,
    fontName: FontFamily, modifier: Modifier
) {
    var selectedIndex by remember {
        mutableStateOf(0)
    }

    val items = listOf(
        BottomNavData(title = "Home", icon = R.drawable.house),
        BottomNavData(title = "My Bookings", icon = R.drawable.calendar),
        BottomNavData(title = "Articles", icon = R.drawable.article),
        BottomNavData(title = "Settings", icon = R.drawable.settings),
    )


    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,

        ) {
        items.forEachIndexed { index, item ->
            BotomNavItem(item, (index == selectedIndex), fontName) {
                selectedIndex = index
                when(selectedIndex){
                    0->{
                        navController.navigate(Routes.HOME_SCREEN)
                    }
                    1->{
                        navController.navigate(Routes.MY_BOOKINGS)
                    }
                    2->{
                        navController.navigate(Routes.ARTICLES_SCREEN)
                    }
                    3->{
                        navController.navigate(Routes.SETTINGS)
                    }

                }


            }
        }

    }

}

@Composable
fun BotomNavItem(
    itemName: BottomNavData,
    isSelected: Boolean,
    fontName: FontFamily,
    onItemClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(IntrinsicSize.Max)
            .clickable {
                onItemClick()
            }, horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Box(contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(0.dp)
                .fillMaxWidth()
                .height(30.dp)
                .background(
                    if (isSelected) Color(0xff1e5360) else Color.Transparent,
                    shape = RoundedCornerShape(9.dp)
                )
                .clickable {
                    onItemClick()
                }) {

            Icon(
                painter = painterResource(id = itemName.icon),
                contentDescription = null,
                tint = if (isSelected) KamiliMustard else Color.White,
                modifier = Modifier.size(20.dp)
                // decorative element
            )
        }
        Text(
            text = itemName.title, color = if (isSelected) KamiliMustard else Color.White,
            style = androidx.compose.ui.text.TextStyle(fontFamily = fontName, fontSize = 12.sp)
        )

    }
}