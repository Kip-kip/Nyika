package com.nyika.kamili.ui.booking

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.nyika.kamili.R
import com.nyika.kamili.ui.theme.KamiliDark
import com.nyika.kamili.ui.theme.KamiliLighter
import com.nyika.kamili.ui.theme.KamiliMustard
import com.nyika.kamili.utils.KamiliUiEvents
import com.nyika.kamili.utils.ui.SimpleAppBar
import com.nyika.kamili.viewmodel.book_vm.BookDetailsViewModel
import com.nyika.kamili.viewmodel.book_vm.BookViewModelEvents

@Composable
fun BookingDetails(
    onPopBackStack: () -> Unit,
    viewModel: BookDetailsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val fontName = FontFamily(Font(R.font.raleway_regular))
    val services = viewModel.bookingById.services
    val booking = viewModel.bookingById

    Column() {
        SimpleAppBar(R.drawable.star, Color.Black, 0.1f) {
            onPopBackStack()
        }

        LaunchedEffect(key1 = true ){
            viewModel.uiFromVm.collect(){
                when(it){
                    is KamiliUiEvents.ShowSnackbar -> {
                        Toast.makeText(context,it.message,Toast.LENGTH_LONG).show()
                    }
                    else -> {

                    }
                }

            }
        }

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(start = 20.dp, top = 20.dp, end = 20.dp)
        ) {

            Text(
                "Services",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = fontName
                ),
                color = KamiliDark,
                modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp)
            )

            Row(
                Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .horizontalScroll(rememberScrollState())
            ) {


                services.let {
                    it.forEach { it ->
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

                            }

                            it.title?.let { it1 ->
                                Text(
                                    it1,
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
                }
            }

            Text(
                "House Size",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = fontName
                ),
                color = KamiliDark,
                modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp)
            )

            booking.house_size?.let {
                Text(
                    it,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = fontName
                    ),
                    color = Color.Black,
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp)
                )
            }

            Text(
                "Price",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = fontName
                ),
                color = KamiliDark,
                modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 15.dp)
            )

            booking.price?.let {
                Text(
                    "Kshs: "+it.toString(),
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = fontName
                    ),
                    color = Color.Black,
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp)
                )
            }

            Text(
                "Location",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = fontName
                ),
                color = KamiliDark,
                modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 15.dp)
            )
            booking.location?.let {
                Text(
                    it,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = fontName
                    ),
                    color = Color.Black,
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp)
                )
            }
            Text(
                "Additional Info",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = fontName
                ),
                color = KamiliDark,
                modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 15.dp)
            )

            booking.additional_info?.let {
                Text(
                    it,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = fontName
                    ),
                    color = Color.Black,
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp)
                )
            }

            Text(
                "Status",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = fontName
                ),
                color = KamiliDark,
                modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 15.dp)
            )

            Card(
                shape = RoundedCornerShape(10.dp), modifier = Modifier
                    .padding(top = 10.dp, end = 10.dp, start = 10.dp)
                    .height(20.dp), elevation = 1.dp,
                backgroundColor = if (booking.status == "Completed") Color(0xffE5EEE0) else Color(
                    0XFFFFF394
                )

            ) {
                booking.status?.let {
                    Text(
                        it,
                        style = TextStyle(
                            fontSize = 11.sp,
                            fontWeight = FontWeight.W700,
                            fontFamily = fontName
                        ),
                        color = Color.Black,
                        modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 3.dp)
                    )
                }
            }

            Text(
                "Review",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = fontName
                ),
                color = KamiliDark,
                modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 15.dp)
            )
            booking.review_score?.let {
                Row(modifier = Modifier.padding(start = 5.dp, top = 10.dp)) {
                    val stars = listOf(1, 2, 3, 4, 5)
                    stars.forEachIndexed { index, i ->
                        Icon(
                            Icons.Rounded.Star,
                            contentDescription = "",
                            tint = if (index >= it) KamiliLighter else
                                KamiliMustard,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            booking.review_comment?.let {
                Text(
                    it,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = fontName
                    ),
                    color = Color.Black,
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 15.dp)
                )
            }

            Text(
                "Add/Update Review(Updating will override the current review)",
                style = TextStyle(
                    fontSize = 13.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = fontName
                ),
                color = KamiliDark,
                modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 20.dp)
            )

            Row(Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .padding(start = 10.dp, top = 10.dp, end = 10.dp)
                        .height(40.dp)
                        .width(40.dp)
                        .aspectRatio(1f)
                        .background(if (viewModel.selectedReviewScore.value==1) KamiliDark else KamiliLighter , shape = CircleShape)
                        .clickable {
                            viewModel.receiveUIEvents(BookViewModelEvents.onReviewScoreSelected(1))
                        },
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        "1",
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = fontName
                        ),
                        color = if (viewModel.selectedReviewScore.value==1) Color.White else KamiliDark,
//                        modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 15.dp)
                    )

                }

                Box(
                    modifier = Modifier
                        .padding(start = 10.dp, top = 10.dp, end = 10.dp)
                        .height(40.dp)
                        .width(40.dp)
                        .aspectRatio(1f)
                        .background(if (viewModel.selectedReviewScore.value==2) KamiliDark else KamiliLighter, shape = CircleShape)
                        .clickable {
                            viewModel.receiveUIEvents(BookViewModelEvents.onReviewScoreSelected(2))
                        },
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        "2",
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = fontName
                        ),
                        color = if (viewModel.selectedReviewScore.value==2) Color.White else KamiliDark,
//                        modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 15.dp)
                    )

                }

                Box(
                    modifier = Modifier
                        .padding(start = 10.dp, top = 10.dp, end = 10.dp)
                        .height(40.dp)
                        .width(40.dp)
                        .aspectRatio(1f)
                        .background(if (viewModel.selectedReviewScore.value==3) KamiliDark else KamiliLighter, shape = CircleShape)
                        .clickable {
                            viewModel.receiveUIEvents(BookViewModelEvents.onReviewScoreSelected(3))
                        },
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        "3",
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = fontName
                        ),
                        color = if (viewModel.selectedReviewScore.value==3) Color.White else KamiliDark,
//                        modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 15.dp)
                    )

                }

                Box(
                    modifier = Modifier
                        .padding(start = 10.dp, top = 10.dp, end = 10.dp)
                        .height(40.dp)
                        .width(40.dp)
                        .aspectRatio(1f)
                        .background(if (viewModel.selectedReviewScore.value==4) KamiliDark else KamiliLighter, shape = CircleShape)
                        .clickable {
                            viewModel.receiveUIEvents(BookViewModelEvents.onReviewScoreSelected(4))
                        },
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        "4",
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = fontName
                        ),
                        color = if (viewModel.selectedReviewScore.value==4) Color.White else KamiliDark,
//                        modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 15.dp)
                    )

                }
                Box(
                    modifier = Modifier
                        .padding(start = 10.dp, top = 10.dp, end = 10.dp)
                        .height(40.dp)
                        .width(40.dp)
                        .aspectRatio(1f)
                        .background(if (viewModel.selectedReviewScore.value==5) KamiliDark else KamiliLighter, shape = CircleShape)
                        .clickable {
                            viewModel.receiveUIEvents(BookViewModelEvents.onReviewScoreSelected(5))
                        },
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        "5",
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = fontName
                        ),
                        color = if (viewModel.selectedReviewScore.value==5) Color.White else KamiliDark,
//                        modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 15.dp)
                    )

                }

            }

            Text(
                "Describe your experience",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = fontName
                ),
                color = KamiliDark,
                modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 15.dp)
            )

            OutlinedTextField(
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = KamiliDark,
                    focusedIndicatorColor = KamiliDark
                ),
                textStyle = TextStyle.Default.copy(fontFamily = fontName),
                value = viewModel.reviewComment.value,
                onValueChange = {
                    viewModel.receiveUIEvents(BookViewModelEvents.onReviewComment(it))

                },
                modifier = Modifier
                    .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                    .fillMaxWidth()
                    .height(100.dp),
                maxLines = 4
            )


            Button(
                onClick = {
                    viewModel.receiveUIEvents(BookViewModelEvents.onReviewSubmitted)

                },
                modifier = Modifier
                    .padding(start = 10.dp, top = 10.dp, end = 20.dp, bottom = 70.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = KamiliDark,
                )
            ) {
                Text(
                    "Submit Review",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W700,
                        fontFamily = fontName
                    ),
                    color = Color.White,
                )
            }

        }
    }
}