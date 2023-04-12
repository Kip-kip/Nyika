package com.nyika.kamili.ui.booking

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nyika.kamili.data.HouseSizeData
import com.nyika.kamili.ui.theme.KamiliDark
import com.nyika.kamili.ui.theme.KamiliLighter

@Composable
fun HouseSizes(fontName: FontFamily,currentHouseSize: Int,houseSizes:List<HouseSizeData>,onHouseSizeClicked:(pickedHouse: Int)->Unit) {


    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, start = 20.dp)
    ) {
        items(houseSizes) { house ->


            Card(
                elevation = 1.dp,
                backgroundColor = if (currentHouseSize == house.id) KamiliDark else KamiliLighter,
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .height(40.dp)
                    .padding(top = 0.dp, start = 0.dp, end = 10.dp),


                ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.clickable {
                        house.id?.let { onHouseSizeClicked(it) }
                    }
                ) {


                    house.title?.let {
                        Text(
                            it,
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontWeight = FontWeight.W700,
                                fontFamily = fontName
                            ),
                            color = if (currentHouseSize == house.id) Color.White else KamiliDark,
                            modifier = Modifier.padding(start = 10.dp, top = 5.dp, end = 10.dp),

                            )
                    }

                }


            }


        }
    }
}