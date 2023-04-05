package com.example.kamili.ui.settings

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.kamili.R
import com.example.kamili.ui.theme.Gray
import com.example.kamili.ui.theme.KamiliDark
import com.example.kamili.ui.theme.KamiliLighter
import com.example.kamili.utils.KamiliUiEvents
import com.example.kamili.utils.Routes
import com.example.kamili.utils.ui.SimpleAppBar
import com.example.kamili.viewmodel.settings_vm.SettingsViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
fun Settings(
    onAccountClicked: () -> Unit,
    onLogOut:()-> Unit,
    onPopBackStack: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val fontName = FontFamily(Font(R.font.raleway_regular))




    LaunchedEffect(key1 = true) {
        viewModel.uiEvents.receiveAsFlow().collect() {
            when (it) {
                is KamiliUiEvents.OnLogOut -> {
                   onLogOut()
                }
//                is KamiliUiEvents.ShowSnackbar->{
//                    Toast.makeText(LocalContext.current,"wfwf")
//                }
            }
        }
    }

    Column() {
        SimpleAppBar(R.drawable.settings, Color.Black, 0.1f) {
            onPopBackStack()

        }

        Column(
            Modifier
                .fillMaxSize()
                .padding(start = 30.dp, end = 30.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Text(
                "Settings",
                style = TextStyle(
                    KamiliDark,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = fontName
                ),
                modifier = Modifier
                    .padding(top = 50.dp)

            )
            Text(
                "Account",
                style = TextStyle(
                    KamiliDark,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W700,
                    fontFamily = fontName
                ),
                modifier = Modifier
                    .padding(top = 30.dp)

            )

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp), verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Box(
                    modifier = Modifier
                        .padding(top = 0.dp, end = 10.dp)
                        .height(60.dp)
                        .width(50.dp)
                        .aspectRatio(1f)
                        .background(KamiliLighter, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        painter = painterResource(id = R.drawable.account),
                        contentDescription = "",
                        modifier = Modifier.size(20.dp),
                        tint = KamiliDark,
                    )


                }

                Column() {
                    Text(
                        "${viewModel.firstName.toString()} ${viewModel.secondName.toString()}",
                        style = TextStyle(
                            KamiliDark,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W700,
                            fontFamily = fontName
                        ),
                        modifier = Modifier
                            .padding(start = 20.dp, end = 20.dp, top = 0.dp)

                    )

                    Text(
                        "Personal Info",
                        style = TextStyle(
                            Gray,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.W500,
                            fontFamily = fontName
                        ),
                        modifier = Modifier
                            .padding(start = 20.dp, end = 20.dp, top = 10.dp)

                    )

                }


                Column(
                    modifier = Modifier
                        .padding()
                        .clip(RoundedCornerShape(10.dp))
                        .width(50.dp)
                        .background(KamiliLighter)
                        .clickable {
                            onAccountClicked()
                        },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,

                    ) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrowright),
                        contentDescription = null,
                        tint = KamiliDark,
                        modifier = Modifier
                            .height(40.dp)
                            .size(20.dp)
                            .padding(top = 5.dp, bottom = 5.dp)


                    )

                }


            }

            Text(
                "Settings",
                style = TextStyle(
                    KamiliDark,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W700,
                    fontFamily = fontName
                ),
                modifier = Modifier
                    .padding(top = 30.dp)

            )

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp), verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Box(
                    modifier = Modifier
                        .padding(top = 0.dp, end = 10.dp)
                        .height(60.dp)
                        .width(50.dp)
                        .aspectRatio(1f)
                        .background(Color(0XFFFEF0E6), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        painter = painterResource(id = R.drawable.notification),
                        contentDescription = "",
                        modifier = Modifier.size(20.dp),
                        tint = Color(0xffFC6B22),
                    )


                }

                Column() {
                    Text(
                        "Notifications",
                        style = TextStyle(
                            KamiliDark,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W700,
                            fontFamily = fontName
                        ),
                        modifier = Modifier
                            .padding(start = 20.dp, end = 20.dp, top = 0.dp)

                    )


                }


                Column(
                    modifier = Modifier
                        .padding()
                        .clip(RoundedCornerShape(10.dp))
                        .width(50.dp)
                        .background(KamiliLighter),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrowright),
                        contentDescription = null,
                        tint = KamiliDark,
                        modifier = Modifier
                            .height(40.dp)
                            .size(20.dp)
                            .padding(top = 5.dp, bottom = 5.dp)
                            .clickable {
//                                onBackArrowClick()
                            }

                    )

                }


            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp), verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Box(
                    modifier = Modifier
                        .padding(top = 0.dp, end = 10.dp)
                        .height(60.dp)
                        .width(50.dp)
                        .aspectRatio(1f)
                        .background(Color(0XFFECEBFF), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        painter = painterResource(id = R.drawable.help),
                        contentDescription = "",
                        modifier = Modifier.size(20.dp),
                        tint = Color(0xff5731FC),

                        )


                }

                Column() {
                    Text(
                        "Help",
                        style = TextStyle(
                            KamiliDark,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W700,
                            fontFamily = fontName
                        ),
                        modifier = Modifier
                            .padding(start = 20.dp, end = 20.dp, top = 0.dp)

                    )


                }





                Column(
                    modifier = Modifier
                        .padding()
                        .clip(RoundedCornerShape(10.dp))
                        .width(50.dp)
                        .background(KamiliLighter),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrowright),
                        contentDescription = null,
                        tint = KamiliDark,
                        modifier = Modifier
                            .height(40.dp)
                            .size(20.dp)
                            .padding(top = 5.dp, bottom = 5.dp)
                            .clickable {
//                                onBackArrowClick()
                            }

                    )

                }


            }


            Text(
                "Log Out",
                style = TextStyle(
                    KamiliDark,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W700,
                    fontFamily = fontName
                ),
                modifier = Modifier
                    .padding(top = 30.dp)

            )

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp), verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Box(
                    modifier = Modifier
                        .padding(top = 0.dp, end = 10.dp)
                        .height(60.dp)
                        .width(50.dp)
                        .aspectRatio(1f)
                        .clickable {
                            viewModel.processLogOut()
                        }
                        .background(Color(0XFFf6dcd9), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        painter = painterResource(id = R.drawable.arrowleft),
                        contentDescription = "",
                        modifier = Modifier.size(20.dp),
                        tint = Color(0xFFD14735),

                        )


                }


            }


        }

    }
}