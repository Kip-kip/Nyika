package com.example.kamili.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.kamili.R
import com.example.kamili.data.ServicesData
import com.example.kamili.data.ServicesResponse
import com.example.kamili.ui.theme.KamiliDark
import com.example.kamili.ui.theme.KamiliLighter
import kotlinx.coroutines.flow.Flow

@Composable
fun ServicesSection(fontName: FontFamily, services: List<ServicesData>) {

    val kamiliFullPackage = services.filter {
        it.type == "Nyika Full Package"
    }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, start = 20.dp)
    ) {
        items(kamiliFullPackage) { service ->
            Column(
                modifier = Modifier
                    .width(80.dp)
                    .padding(start = 0.dp)
            ) {


                Box(
                    modifier = Modifier
                        .padding(top = 0.dp, end = 10.dp)
                        .height(60.dp)
                        .width(60.dp)
                        .aspectRatio(1f)
                        .background(KamiliLighter, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {

                    Image(
                        painter = rememberAsyncImagePainter(service.icon),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,

                        modifier = Modifier
                            .padding(10.dp, 10.dp)
                            .size(30.dp)

                    )

                }

                Text(
                    service.title!!,
                    style = TextStyle(
                        fontSize = 11.sp,
                        fontWeight = FontWeight.W700,
                        fontFamily = fontName
                    ),
                    color = Color(0XFF0D313B),
                    modifier = Modifier.padding(start = 0.dp, top = 10.dp)
                )

            }
        }
    }
}