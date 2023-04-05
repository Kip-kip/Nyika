package com.example.kamili.ui.home

import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kamili.R
import com.example.kamili.data.ReviewsData
import com.example.kamili.ui.theme.KamiliDark
import com.example.kamili.ui.theme.KamiliLight
import com.example.kamili.ui.theme.KamiliLighter
import com.example.kamili.ui.theme.KamiliMustard
import com.example.kamili.utils.KamiliUiEvents
import com.example.kamili.viewmodel.reviews_vm.ReviewsViewModel
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
fun Reviews(
    fontName: FontFamily,
    viewModel: ReviewsViewModel = hiltViewModel(),
    onReviewClicked:(reviewId:Int)->Unit
    ) {

    val reviews = viewModel.reviews.collectAsState(initial = emptyList())
    val context = LocalContext.current

    LaunchedEffect(key1 = true){
        viewModel.uiEvents.receiveAsFlow().collect(){
            when(it){
                is KamiliUiEvents.ShowSnackbar->{
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .padding(top = 10.dp, start = 20.dp)
    ) {
        items(reviews.value) { review ->
            Card(
                elevation = 5.dp,
                backgroundColor = Color.White,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .width(180.dp)
                    .height(130.dp)
                    .padding(top = 0.dp, start = 1.dp, end = 10.dp).clickable {
                        review.id?.let { onReviewClicked(it) }
                    }

            ) {
                Row(modifier = Modifier.padding(top = 10.dp, start = 10.dp)){
                    Image(
                        painter = painterResource(id = R.drawable.user),
                        contentDescription = "avatar",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(
                                CircleShape
                            )
                            .size(40.dp)
                    )

                    Column() {


                        Row(modifier = Modifier.padding(start = 10.dp)) {
                            val stars = listOf(1, 2, 3, 4, 5)
                            stars.forEachIndexed { index, i ->
                                Icon(
                                    Icons.Rounded.Star,
                                    contentDescription = "",
                                    tint = if (index >= review.score!!) KamiliLighter else
                                        KamiliMustard,
                                    modifier = Modifier.size(17.dp)
                                )
                            }
                        }

                        review.username?.let {
                            Text(
                                it,
                                style = TextStyle(
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.W700,
                                    fontFamily = fontName
                                ),
                                color = Color.Gray,
                                modifier = Modifier.padding(start = 10.dp, top = 5.dp),

                                )
                        }
                        review.comment?.let {
                            Text(
                                it,
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontFamily = fontName
                                ),
                                color = Color(0XFF0D313B),
                                modifier = Modifier.padding(start = 10.dp, top = 10.dp),
                                maxLines = 2, overflow = TextOverflow.Ellipsis
                            )
                        }

                        review.date?.let {
                            Text(
                                it,
                                style = TextStyle(
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.W700,
                                    fontFamily = fontName,
                                ),
                                color = Color.Gray,
                                modifier = Modifier.padding(start = 10.dp, top = 5.dp),

                                )
                        }
                    }


                }



            }
        }
    }
}