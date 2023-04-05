package com.example.kamili.ui.booking.components

import androidx.compose.foundation.background
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
fun FutureNavigator(fontName: FontFamily) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .padding(start = 10.dp, end = 10.dp)
            .clip(RoundedCornerShape(30))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .background(
                    Color(0XFFFFF394)
                )
                .fillMaxHeight()
                .weight(1f)
        ) {
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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .background(
                    Color(0xffE5EEE0)
                )
                .fillMaxHeight()
                .weight(1f)
        ) {
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