package com.nyika.kamili.ui.booking.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BookingNavigator(fontName: FontFamily,status:String,onPendingClick:()->Unit,onCompletedClick:()->Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(43.dp)
            .padding(start = 10.dp, end = 10.dp, top = 10.dp)
            .clip(RoundedCornerShape(30))
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .background(if(status=="Pending") Color(0XFFc9b200) else Color(0XFFFFF394) )
                .fillMaxHeight()
                .weight(1f)
                .clickable {
            onPendingClick()
        }) {
            Text(
                "Pending",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W700,
                    fontFamily = fontName
                ),
                color = Color.Black,
                modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 3.dp)
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .background(if(status=="Completed") Color(0XFF699551)  else Color(0xffE5EEE0) )
                .fillMaxHeight()
                .weight(1f)
                .clickable {
            onCompletedClick()
        }) {
            Text(
                "Completed",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W700,
                    fontFamily = fontName
                ),
                color = Color.Black,
                modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 3.dp)
            )
        }
    }
}