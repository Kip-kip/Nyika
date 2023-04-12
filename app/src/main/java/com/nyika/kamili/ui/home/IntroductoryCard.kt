package com.nyika.kamili.ui.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import com.nyika.kamili.R
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun IntroductoryCard(fontName: FontFamily,onBookInitiated: ()->Unit){
    Card(
        elevation = 1.dp,
        backgroundColor = Color(0XFFC5E2DF),
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(top = 80.dp, start = 20.dp, end = 20.dp)

    ) {

        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 15.dp)
            ) {
                Text(
                    "We make your HOME as GOOD as NEW",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W700,
                        fontFamily = fontName
                    ),
                    color = Color(0XFF0D313B),
                    modifier = Modifier.padding(top = 15.dp)
                )
                Button(
                    onClick = {
                              onBookInitiated()

                              },
                    modifier = Modifier.padding(top = 10.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0XFF143740),
                    )
                ) {
                    Text(
                        "Book Now",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.W700,
                            fontFamily = fontName
                        ),
                        color = Color.White,
                    )
                }

            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .weight(1f),
                horizontalAlignment = Alignment.End
            ) {

                Image(
                    painter = painterResource(id = R.drawable.cleaner),
                    contentDescription = "avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(top = 0.dp)
                )


            }

        }


        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            drawCircle(
                color = Color(0XFFECB9B9),
                center = Offset(x = canvasWidth / 9, y = canvasHeight / 1),
                radius = size.minDimension / 4
            )
        }
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            drawCircle(
                color = Color(0XFF50AD62),
                center = Offset(x = canvasWidth / 1, y = canvasHeight / 9),
                radius = size.minDimension / 4
            )
        }


    }
}