package com.example.kamili.ui.booking.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.kamili.R
import com.example.kamili.data.ArticlesData
import com.example.kamili.data.BookingData
import com.example.kamili.ui.theme.KamiliDark
import com.example.kamili.ui.theme.KamiliLighter
import com.example.kamili.ui.theme.KamiliMustard
import com.example.kamili.viewmodel.book_vm.BookViewModelEvents
import java.nio.file.Files.size

@Composable
fun BookingCard(fontName: FontFamily, booking: BookingData, onBookingClicked: () -> Unit) {
//    Text(booking.toString())
    Card(
        shape = RoundedCornerShape(10.dp), modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, end = 10.dp, start = 10.dp)
            .height(110.dp)
            .clickable {
                onBookingClicked()
            }, elevation = 1.dp

    ) {
        Column() {
            Row() {

                Row(
                    Modifier
                        .padding(top = 0.dp)
                        .weight(2.5f)
                ) {
                    booking.services.forEach { it ->
                        Column() {

                            Box(
                                modifier = Modifier
                                    .padding(start = 10.dp, top = 10.dp, end = 10.dp)
                                    .height(40.dp)
                                    .width(40.dp)
                                    .aspectRatio(1f)
                                    .background(KamiliLighter, shape = CircleShape),
                                contentAlignment = Alignment.Center
                            ) {

                                Image(
                                    painter = rememberAsyncImagePainter(it.icon),
                                    contentDescription = "",
                                    contentScale = ContentScale.Crop,

                                    modifier = Modifier
                                        .padding(10.dp, 10.dp)
                                        .size(30.dp)

                                )

//

                            }

                            Text(

                                if(booking.services.size > 2) it.title!!.take(9) + "..." else it.title!!,
                                style = TextStyle(
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.W700,
                                    fontFamily = fontName
                                ),
                                color = KamiliDark,
                                modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp)
                            )
                        }

                    }
                }

                if(booking.status=="Pending"){
                Row(
                    Modifier
                        .padding(top = 20.dp, end = 0.dp)
                ) {
                    Text(

                        "Kshs: ${booking.price}",
                        style = TextStyle(
                            fontSize = 11.sp,
                            fontWeight = FontWeight.W700,
                            fontFamily = fontName
                        ),
                        color = KamiliDark,
                        modifier = Modifier.padding(end = 20.dp)
                    )
                }

            }
                else {
                    Row(
                        Modifier
                            .padding(top = 20.dp, end = 10.dp)
                            .weight(1f)
                    ) {

                        val stars = listOf(1, 2, 3, 4, 5)
                        stars.forEachIndexed { index, i ->
                            if (booking.review_score == 0) {

                            } else {
                                Icon(
                                    Icons.Rounded.Star,
                                    contentDescription = "",
                                    tint = if (index >= booking.review_score!!) KamiliLighter else
                                        KamiliMustard,
                                    modifier = Modifier.size(17.dp)
                                )
                            }
                        }
                    }

                }
            }



            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

                Card(
                    shape = RoundedCornerShape(10.dp), modifier = Modifier
                        .padding(top = 10.dp, end = 10.dp, start = 10.dp)
                        .height(20.dp), elevation = 1.dp,
                    backgroundColor = if (booking.status == "Completed") Color(0xffE5EEE0) else Color(
                        0XFFFFF394
                    )

                ) {
                    Text(
                        booking.status!!,
                        style = TextStyle(
                            fontSize = 11.sp,
                            fontWeight = FontWeight.W700,
                            fontFamily = fontName
                        ),
                        color = Color.Black,
                        modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 3.dp)
                    )
                }
                Column() {

                }
                Column(verticalArrangement = Arrangement.Bottom) {

                    Text(
                        booking.time!! + " " + booking.date!!,
                        style = TextStyle(
                            fontSize = 11.sp,
                            fontWeight = FontWeight.W700,
                            fontFamily = fontName
                        ),
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 20.dp, top = 10.dp, end = 20.dp),
                        maxLines = 3, overflow = TextOverflow.Ellipsis

                    )
                }

            }


        }


    }
}