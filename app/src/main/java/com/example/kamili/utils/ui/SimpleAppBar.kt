package com.example.kamili.utils.ui

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.kamili.R
import com.example.kamili.ui.theme.KamiliDark
import com.example.kamili.ui.theme.KamiliLighter
import com.example.kamili.ui.theme.KamiliMustard

@Composable
fun SimpleAppBar(
    passedIcon: Int,
    alphaColor: Color,
    alphaValue: Float,
    onPopBackStack: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Column(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, top = 10.dp)
                .clip(RoundedCornerShape(10.dp))
                .width(50.dp)
                .background(KamiliLighter)
//                .background(alphaColor.copy(alpha = alphaValue))
                .clickable {
                    onPopBackStack()
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Icon(
                painter = painterResource(id = R.drawable.arrowleft),
                contentDescription = null,
                tint = KamiliDark,
                modifier = Modifier
                    .height(70.dp)
                    .size(20.dp)
                    .padding(top = 5.dp, bottom = 5.dp)


            )

        }

        Column(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, top = 10.dp)
                .clip(RoundedCornerShape(10.dp))
                .width(50.dp)
                .background(KamiliLighter),
//                .background(alphaColor.copy(alpha = alphaValue)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                painter = painterResource(id = passedIcon),
                contentDescription = null,
                tint = KamiliDark,
                modifier = Modifier
                    .height(70.dp)
                    .size(20.dp)
                    .padding(top = 5.dp, bottom = 5.dp)
                    .clickable {
//                                onBackArrowClick()
                    }

            )

        }


    }
}