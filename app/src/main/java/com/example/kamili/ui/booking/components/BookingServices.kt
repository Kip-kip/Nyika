package com.example.kamili.ui.booking

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
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
import com.example.kamili.data.ServicesData
import com.example.kamili.ui.theme.KamiliDark
import com.example.kamili.ui.theme.KamiliLight
import com.example.kamili.ui.theme.KamiliLighter
import com.example.kamili.ui.theme.KamiliMustard
import com.squareup.picasso.Picasso

@Composable
fun BookingServices(fontName: FontFamily,services: List<Int>,theServices: List<ServicesData>,onServiceClicked :(title : Int)->Unit) {

    val extraServices = theServices.filter {
        it.type == "Extras"
    }



    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, start = 20.dp)
    ) {
        items(extraServices) { bs ->

            Card(
                elevation = 1.dp,
                backgroundColor = if(services.contains(bs.id)) KamiliMustard else KamiliLighter,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .width(130.dp)
                    .height(130.dp)
                    .padding(top = 0.dp, start = 0.dp, end = 10.dp).clickable {
                        onServiceClicked(bs.id!!)
                    }

            ) {

                Column(horizontalAlignment = Alignment.CenterHorizontally,verticalArrangement = Arrangement.Center) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.arrowright),
//                        contentDescription = "",
//                        modifier = Modifier.size(40.dp),
//                        tint = KamiliDark
//                    )
                    Image(
                        painter = rememberAsyncImagePainter(bs.icon),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,

                        modifier = Modifier
                            .padding(10.dp, 10.dp)
                            .size(40.dp)

                    )

                    Text(
                        bs.title!!,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.W700,
                            fontFamily = fontName
                        ),
                        color = KamiliDark,
                        modifier = Modifier.padding(start = 10.dp, top = 5.dp),

                        )

                }


            }


        }
    }
}