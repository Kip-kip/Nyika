package com.example.kamili.utils.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kamili.ui.theme.KamiliDark
import com.example.kamili.ui.theme.KamiliLighter

@Composable
fun Appbar(title: String, fontName: FontFamily){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(KamiliDark)
            .height(56.dp)
            .fillMaxWidth()
    ) {
        Icon(
            Icons.Rounded.ArrowBack,
            contentDescription = "",
            tint = Color.White,
            modifier = Modifier.padding(start = 10.dp)
        )
        Text(
            title,
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.W900,
                fontFamily = fontName
            ),
            color = Color.White,
            modifier = Modifier.padding(start = 0.dp, top = 0.dp)
        )
        Icon(
            Icons.Rounded.Notifications,
            contentDescription = "",
            tint = Color.White,
            modifier = Modifier.padding(end = 10.dp)
        )
    }

}