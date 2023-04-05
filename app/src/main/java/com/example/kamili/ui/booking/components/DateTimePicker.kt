package com.example.kamili.ui.booking.components

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kamili.R
import com.example.kamili.ui.theme.KamiliDark
import com.example.kamili.ui.theme.KamiliGrayish
import com.example.kamili.ui.theme.KamiliLighter
import java.util.*

@Composable
fun DateTimePicker(fontName: FontFamily,pickedDate: String,pickedTime: String,onPickDate:()->Unit,onPickTime:()->Unit){

    Row(modifier = Modifier.fillMaxWidth()){
        Column(modifier = Modifier.padding(start = 0.dp).weight(1f)){
            Text(
                "What day are you available?",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W900,
                    fontFamily = fontName
                ),
                color = KamiliGrayish,
                modifier = Modifier.padding(start = 20.dp, top = 20.dp)
            )
            Card(
                elevation = 1.dp,
                backgroundColor = KamiliLighter,
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .height(40.dp)
                    .padding(top = 10.dp, start = 20.dp, end = 10.dp).clickable {
                        onPickDate()
                    }

            ) {


                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {


                    Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier.padding(end = 10.dp)) {
                        Text(
                           if(pickedDate.isEmpty()) "Pick a date" else pickedDate,
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontWeight = FontWeight.W700,
                                fontFamily = fontName
                            ),
                            color = KamiliDark,
                            modifier = Modifier.padding(start = 10.dp, top = 0.dp, end = 10.dp),

                            )
                        Icon(
                            painter = painterResource(id = R.drawable.calendar),
                            contentDescription = "",
                            modifier = Modifier.size(15.dp),
                            tint = KamiliDark
                        )

                    }
                }



            }

        }
        Column(modifier = Modifier.padding(start = 0.dp).weight(1f)){

            Text(

                "Exact Time?",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W900,
                    fontFamily = fontName
                ),
                color = KamiliGrayish,
                modifier = Modifier.padding(start = 20.dp, top = 20.dp)
            )
            Card(
                elevation = 1.dp,
                backgroundColor = KamiliLighter,
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .height(40.dp)
                    .padding(top = 10.dp, start = 20.dp, end = 10.dp).clickable {
                        onPickTime()
                    }

            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {


                    Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier.padding(end = 10.dp)) {
                        Text(
                            if(pickedTime.isEmpty()) "Pick a time" else pickedTime,
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontWeight = FontWeight.W700,
                                fontFamily = fontName
                            ),
                            color = KamiliDark,
                            modifier = Modifier.padding(start = 10.dp, top = 0.dp, end = 10.dp),

                            )
                        Icon(
                            painter = painterResource(id = R.drawable.time),
                            contentDescription = "",
                            modifier = Modifier.size(15.dp),
                            tint = KamiliDark
                        )

                    }
                }


            }

        }
    }
}