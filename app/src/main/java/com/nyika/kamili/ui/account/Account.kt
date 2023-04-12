package com.nyika.kamili.ui.account

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.nyika.kamili.ui.theme.KamiliDark
import com.nyika.kamili.utils.KamiliUiEvents
import com.nyika.kamili.utils.ui.SimpleAppBar
import com.nyika.kamili.viewmodel.account_vm.AccountEvents
import com.nyika.kamili.viewmodel.account_vm.AccountViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.nyika.kamili.R
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
fun Account(onPopBackStack: () -> Unit, vm: AccountViewModel = hiltViewModel()) {
    val fontName = FontFamily(Font(R.font.raleway_regular))
    val context = LocalContext.current

    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val bitmap = remember { mutableStateOf<Bitmap?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }
    imageUri?.let {
        if (Build.VERSION.SDK_INT < 28) {
            bitmap.value = MediaStore.Images
                .Media.getBitmap(context.contentResolver, it)

        } else {
            val source = ImageDecoder
                .createSource(context.contentResolver, it)
            bitmap.value = ImageDecoder.decodeBitmap(source)
        }


    }

    val systemUiController = rememberSystemUiController()
//    systemUiController.isStatusBarVisible = false // Status bar

    Column(Modifier.fillMaxSize()) {


        LaunchedEffect(key1 = true) {
            vm.uiEvents.receiveAsFlow().collect() {
                when (it) {
                    is KamiliUiEvents.ShowSnackbar -> {
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        SimpleAppBar(R.drawable.settings, Color.Black, 0.1f) {
            onPopBackStack()
        }

        Column(
            Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                "Account",
                style = TextStyle(
                    KamiliDark,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = fontName
                ),
                modifier = Modifier
                    .padding(top = 50.dp, start = 20.dp)


            )


            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp),

                ) {

                Text(
                    "Photo:",
                    style = TextStyle(
                        KamiliDark,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W700,
                        fontFamily = fontName
                    ),
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp, top = 0.dp)

                )

                Column(verticalArrangement = Arrangement.Center) {
                    Row() {
                        Box(
                            modifier = Modifier
                                .padding(start = 50.dp, top = 50.dp, end = 10.dp)
                                .height(0.dp)
                                .width(80.dp)
                                .aspectRatio(1f)
                                .background(Color(0XFFFEF0E6), shape = CircleShape),
                            contentAlignment = Alignment.Center
                        ) {


                            bitmap.value?.let { btm ->
                                Image(
                                    bitmap = btm.asImageBitmap(),
                                    contentDescription = "avatar",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .clip(
                                            CircleShape
                                        )
                                        .size(80.dp)
                                )
                            }
                            if (bitmap.value == null) {
                                Image(
                                    painter = rememberAsyncImagePainter(vm.profilePic.value),
                                    contentDescription = "avatar",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .clip(
                                            CircleShape
                                        )
                                        .size(80.dp)
                                )


                            }
                        }

                        bitmap.value?.let { btm ->
                            Column(modifier = Modifier.clickable {
                                imageUri?.let { vm.uploadImage(it) }

                            }) {
                                Image(
                                    painter = painterResource(id = R.drawable.upload),
                                    contentDescription = "avatar",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .padding(start = 10.dp, end = 20.dp, top = 45.dp)
                                        .size(30.dp)
                                )
                                Text(
                                    "Upload",
                                    style = TextStyle(
                                        KamiliDark,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.W700,
                                        fontFamily = fontName
                                    ),
                                    modifier = Modifier
                                        .padding(start = 0.dp, end = 20.dp, top = 0.dp)

                                )

                            }
                        }

                    }





                    if (bitmap.value == null) {
                        Text(
                            "Click to upload/change photo",
                            style = TextStyle(
                                KamiliDark,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.W700,
                                fontFamily = fontName
                            ),
                            modifier = Modifier
                                .padding(
                                    start = 40.dp,
                                    end = 20.dp,
                                    top = if (bitmap.value != null) 20.dp else 50.dp
                                )
                                .clickable {
                                    launcher.launch("image/*")

                                }

                        )


                    }
                }

            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp), verticalAlignment = Alignment.CenterVertically

            ) {

                Text(
                    "First Name: ",
                    style = TextStyle(
                        KamiliDark,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W700,
                        fontFamily = fontName
                    ),
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp, top = 0.dp)

                )

                Column(verticalArrangement = Arrangement.Center) {

                    TextField(
                        value = vm.firstName.value!!,
                        onValueChange = {
                            vm.receiveUIEvents(AccountEvents.updateFirstName(it))
                        },
                        textStyle = TextStyle.Default.copy(fontSize = 14.sp, fontFamily = fontName),

                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            focusedIndicatorColor = KamiliDark,
                            cursorColor = KamiliDark
                        ),
                        modifier = Modifier
                            .padding(start = 20.dp, end = 20.dp)
                            .fillMaxWidth()
                            .scale(scaleY = 0.9F, scaleX = 1F),

                        )

                }


            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 0.dp), verticalAlignment = Alignment.CenterVertically

            ) {

                Text(
                    "Second Name: ",
                    style = TextStyle(
                        KamiliDark,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W700,
                        fontFamily = fontName
                    ),
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp, top = 0.dp)

                )

                Column(verticalArrangement = Arrangement.Center) {

                    TextField(
                        value = vm.secondName.value!!,
                        onValueChange = {
                            vm.receiveUIEvents(AccountEvents.updateSecondName(it))
                        },
                        textStyle = TextStyle.Default.copy(fontSize = 14.sp, fontFamily = fontName),


                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            focusedIndicatorColor = KamiliDark,
                            cursorColor = KamiliDark
                        ),
                        modifier = Modifier
                            .padding(start = 20.dp, end = 20.dp)
                            .fillMaxWidth()
                            .scale(scaleY = 0.9F, scaleX = 1F),

                        )

                }


            }



            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp),

                ) {

                Text(
                    "Gender: ",
                    style = TextStyle(
                        KamiliDark,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W700,
                        fontFamily = fontName
                    ),
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp, top = 0.dp)

                )

                Row() {

                    Box(
                        modifier = Modifier
                            .padding(start = 20.dp, top = 10.dp, end = 10.dp)
                            .height(0.dp)
                            .width(40.dp)
                            .aspectRatio(1f)
                            .background(Color(0XFFffecf3), shape = CircleShape),
                        contentAlignment = Alignment.Center
                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.female),
                            contentDescription = "avatar",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .padding(10.dp, 10.dp)
                                .size(30.dp)
                        )


                    }

                    Box(
                        modifier = Modifier
                            .padding(start = 20.dp, top = 10.dp, end = 10.dp)
                            .height(0.dp)
                            .width(40.dp)
                            .aspectRatio(1f)
                            .background(Color(0XFFdae5f2), shape = CircleShape),
                        contentAlignment = Alignment.Center
                    ) {


                        Image(
                            painter = painterResource(id = R.drawable.male),
                            contentDescription = "avatar",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .padding(10.dp, 10.dp)
                                .size(20.dp)
                        )


                    }

                }


            }


            Row(
                Modifier
                    .fillMaxWidth()
//                .background(Color.Blue)
                    .padding(top = 10.dp), verticalAlignment = Alignment.CenterVertically

            ) {

                Text(
                    "Email: ",
                    style = TextStyle(
                        KamiliDark,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W700,
                        fontFamily = fontName
                    ),
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp, top = 0.dp)

                )

                Column(verticalArrangement = Arrangement.Center) {

                    TextField(
                        value = vm.useremail.value,
                        onValueChange = {
                            vm.receiveUIEvents(AccountEvents.updateEmail)
                        },
                        textStyle = TextStyle.Default.copy(fontSize = 14.sp, fontFamily = fontName),


                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            focusedIndicatorColor = KamiliDark,
                            cursorColor = KamiliDark
                        ),
                        modifier = Modifier
                            .padding(start = 20.dp, end = 20.dp)
                            .fillMaxWidth()
                            .scale(scaleY = 0.9F, scaleX = 1F),

                        )

                }


            }


            Row(modifier = Modifier.padding(bottom = 50.dp)) {

                Button(
                    onClick = {
                        vm.updateUserProfile()
                    },
                    modifier = Modifier
                        .padding(start = 20.dp, top = 40.dp, end = 20.dp, bottom = 20.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = KamiliDark,
                    )
                ) {
                    Text(
                        "Update Details",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W700,
                            fontFamily = fontName
                        ),
                        color = Color.White,
                    )
                }
            }


        }

    }
}