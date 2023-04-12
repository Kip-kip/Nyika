package com.nyika.kamili.ui.reviews

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nyika.kamili.R
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nyika.kamili.ui.theme.KamiliLighter
import com.nyika.kamili.ui.theme.KamiliMustard
import com.nyika.kamili.utils.ui.SimpleAppBar
import com.nyika.kamili.viewmodel.reviews_vm.ReviewsDetailsViewModel

@Composable
fun ReviewDetails(
    onPopBackStack: () -> Unit,
    viewModel: ReviewsDetailsViewModel = hiltViewModel()
) {
    val fontName = FontFamily(Font(R.font.raleway_regular))

    val review = viewModel.reviewById

    Column() {
        SimpleAppBar(R.drawable.star, Color.Black, 0.1f) {
            onPopBackStack()
        }

        Column(
            Modifier
                .fillMaxSize()
                .padding(start = 20.dp, top = 20.dp, end = 20.dp)) {
            Row(modifier = Modifier.padding(top = 10.dp, start = 10.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.user),
                    contentDescription = "avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(
                            CircleShape
                        )
                        .size(50.dp)
                )

                Column() {

                    review.score?.let {
                        Row(modifier = Modifier.padding(start = 10.dp)) {
                            val stars = listOf(1, 2, 3, 4, 5)
                            stars.forEachIndexed { index, i ->
                                Icon(
                                    Icons.Rounded.Star,
                                    contentDescription = "",
                                    tint = if (index >= it) KamiliLighter else
                                        KamiliMustard,
                                    modifier = Modifier.size(17.dp)
                                )
                            }
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