package com.nyika.kamili.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter


@Composable
fun ProfileSection(fontName: FontFamily,user:Map<String,String>) {
    Column(
        modifier = Modifier
            .background(
                Color(0XFF143740),
                shape = RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp)
            )
            .height(170.dp)
            .fillMaxWidth()

    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(start = 10.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(2f)
            ) {
                Text(
                    "Hello ${user["firstName"]} ${user["secondName"]} ",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W700,
                        fontFamily = fontName
                    ),
                    color = Color.White,
                    modifier = Modifier.padding(start = 10.dp, top = 10.dp)
                )
                Text(
                    "Let's get your spaces happy",
                    style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.W600),
                    color = Color(0XFF798B92),
                    modifier = Modifier.padding(start = 10.dp, top = 10.dp),
                    fontFamily = fontName
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, end = 20.dp)
                    .weight(1f), horizontalAlignment = Alignment.End
            ) {
                val picUrl = user["profilePic"].toString()
                Image(
                    painter = rememberAsyncImagePainter(picUrl),
                    contentDescription = "avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(
                            CircleShape
                        )
                        .size(50.dp)
                        .padding(top = 0.dp)
                )

            }

        }


    }


    fun saveUserSharedPreferences(){



    }

}