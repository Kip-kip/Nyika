package com.example.kamili.ui.home

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import coil.compose.rememberAsyncImagePainter
import com.example.kamili.R
import com.example.kamili.data.ArticlesData
import com.example.kamili.ui.theme.KamiliDark
import com.example.kamili.ui.theme.KamiliLight
import com.example.kamili.ui.theme.KamiliLighter
import com.example.kamili.utils.KamiliUiEvents
import com.example.kamili.viewmodel.articles_vm.ArticlesViewModel
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
fun Reads(
    fontName: FontFamily,
    articlesViewModel: ArticlesViewModel = hiltViewModel(),
    onArticleClicked:(articleId: Int)->Unit,
) {
    val articles = articlesViewModel.articles.collectAsState(initial = emptyList())

    val context = LocalContext.current

    LaunchedEffect(key1 = true){
        articlesViewModel.uiEvents.receiveAsFlow().collect(){
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
            .height(330.dp)
            .padding(top = 10.dp, start = 20.dp)
    ) {
        items(articles.value) { article ->
            Card(
                elevation = 1.dp,
                backgroundColor = Color.White,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .width(200.dp)
                    .height(250.dp)
                    .padding(top = 0.dp, start = 1.dp, end = 10.dp).clickable {
                        article.id?.let { onArticleClicked(it) }
                    }

            ) {

                Column() {
                    Image(
                        painter = rememberAsyncImagePainter(article.image) ,
                        contentDescription = "avatar",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(100.dp)
                            .fillMaxWidth()
                            .padding(top = 0.dp)
                    )
                    article.title?.let {
                        Text(
                            it,
                            style = TextStyle(
                                fontSize = 13.sp,
                                fontWeight = FontWeight.ExtraBold,
                                fontFamily = fontName
                            ),
                            color = KamiliDark,
                            modifier = Modifier.padding(start = 10.dp, top = 15.dp)
                        )
                    }
                    article.content?.let {
                        Text(
                            it,
                            style = TextStyle(
                                fontSize = 11.sp,
                                fontWeight = FontWeight.W600,
                                fontFamily = fontName
                            ),
                            color = Color(0XFF555555),
                            modifier = Modifier.padding(start = 10.dp, top = 10.dp),
                            maxLines = 3, overflow = TextOverflow.Ellipsis
                        )
                    }
                    Text(
                        "By ${article.writer}",
                        style = TextStyle(
                            fontSize = 10.sp,
                            fontWeight = FontWeight.W700,
                            fontFamily = fontName
                        ),
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 10.dp, top = 5.dp)
                    )
                    article.date?.let {
                        Text(
                            it,
                            style = TextStyle(
                                fontSize = 11.sp,
                                fontWeight = FontWeight.W700,
                                fontFamily = fontName
                            ),
                            color = Color.Gray,
                            modifier = Modifier.padding(start = 10.dp, top = 5.dp)
                        )
                    }
                }


            }
        }
    }
}