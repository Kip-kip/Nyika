package com.example.kamili.ui.articles


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.kamili.R
import com.example.kamili.data.ArticlesData
import com.example.kamili.ui.theme.KamiliDark
import com.example.kamili.ui.theme.KamiliLight
import com.example.kamili.ui.theme.KamiliLighter
import com.example.kamili.utils.ui.SimpleAppBar
import com.example.kamili.viewmodel.articles_vm.ArticlesDetailsViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun ArticleInfo(
    onPopBackStack: ()->Unit,
viewModel: ArticlesDetailsViewModel = hiltViewModel()
) {

    val article = viewModel.articleById

    val fontName = FontFamily(Font(R.font.raleway_regular))


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            Modifier
                .height(420.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp))
                    .background(Color.Black.copy(alpha = 0.4f))
                    .height(420.dp)
            ) {

                Image(
                    painter = rememberAsyncImagePainter(article.image),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxHeight().fillMaxWidth()
                )


                SimpleAppBar(R.drawable.reading,Color.White,0.6f){
                    onPopBackStack()
                }

                Column(
                    Modifier
                        .height(420.dp)
                        .fillMaxWidth()
                        .background(Color.Black.copy(alpha = 0.4f))
                ) {

                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Column(modifier = Modifier.padding(bottom = 70.dp)) {
            article.date?.let {
                Text(
                    it,
                    style = TextStyle(
                        Color(0xff7E7E7E),
                        fontSize = 12.sp,
                        fontFamily =  fontName,
                        fontWeight = FontWeight.W500
                    ),
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp)

                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            article.title?.let {
                Text(
                    it,
                    style = TextStyle(
                        KamiliDark,
                        fontSize = 18.sp,
                        fontFamily =  fontName,
                        fontWeight = FontWeight.W500
                    ),
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp)

                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            article.content?.let {
                Text(
                    it,
                    style = TextStyle(
                        Color(0XFF555555),
                        fontWeight = FontWeight.W700,
                        fontFamily =  fontName,
                        fontSize = 13.sp,
                    ),
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp)

                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            Text(
                "By ${article.writer}",
                style = TextStyle(
                    Color(0xff7E7E7E),
                    fontSize = 12.sp,
                    fontFamily = fontName,
                    fontWeight = FontWeight.W500
                ),
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp)

            )

        }


    }
}
