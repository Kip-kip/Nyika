package com.example.kamili.ui.home

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.kamili.R
import com.example.kamili.ui.theme.KamiliDark
import com.example.kamili.utils.KamiliUiEvents
import com.example.kamili.viewmodel.home_vm.HomeViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
fun HomeScreen(
    onPopBackStack: ()->Unit,
    onBookingInitiated: () -> Unit,
    onReviewClicked: (reviewId:Int) -> Unit,
    onSeeAll: () -> Unit,
    onArticleClicked:(articleId: Int)->Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val fontName = FontFamily(Font(R.font.raleway_regular))
    val context = LocalContext.current
    val services = viewModel.services.collectAsState(initial = emptyList())


    LaunchedEffect(key1 = true){
        viewModel.uiEvents.receiveAsFlow().collect(){
            when(it){
                is KamiliUiEvents.ShowSnackbar->{
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .background(Color(0XFFFFFFFF))
            .fillMaxSize()
    ) {

        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {


            Box(
                modifier = Modifier
                    .background(Color(0XFFFFFFFF))
            ) {

                val user = mapOf(
                    "firstName" to viewModel.firstName,
                    "secondName" to viewModel.secondName,
                    "profilePic" to viewModel.profilePic,
                )
                ProfileSection(fontName = fontName, user as Map<String, String>)
                IntroductoryCard(fontName = fontName) {
                    onBookingInitiated()
                }
            }

            Text(
                "Nyika Full Package",
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.W900,
                    fontFamily = fontName
                ),
                color = KamiliDark,
                modifier = Modifier.padding(start = 20.dp, top = 10.dp)
            )


            ServicesSection(fontName = fontName, services = services.value)

            Text(
                "Client Reviews",
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.W900,
                    fontFamily = fontName
                ),
                color = KamiliDark,
                modifier = Modifier.padding(start = 20.dp, top = 20.dp)
            )
            Reviews(fontName) {
                onReviewClicked(it)
            }


            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Top Reads",
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.W900,
                        fontFamily = fontName
                    ),
                    color = KamiliDark,
                    modifier = Modifier.padding(start = 20.dp, top = 20.dp)
                )
                Text(
                    "See all...",
                    style = TextStyle(
                        fontSize = 13.sp,
                        fontWeight = FontWeight.W900,
                        fontFamily = fontName
                    ),
                    color = KamiliDark,
                    modifier = Modifier
                        .padding(top = 20.dp, end = 10.dp)
                        .clickable {
                            onSeeAll()
                        }
                )
            }


            Reads(fontName){
                onArticleClicked(it)
            }


        }


    }
}
