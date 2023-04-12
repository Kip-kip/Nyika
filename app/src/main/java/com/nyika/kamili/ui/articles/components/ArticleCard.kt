package com.nyika.kamili.ui.articles.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.nyika.kamili.data.ArticlesData
import com.nyika.kamili.ui.theme.KamiliDark

@Composable
fun ArticleCard(fontName: FontFamily,article: ArticlesData,onArticleClick:(articleId: Int)->Unit){

    Card(
        shape = RoundedCornerShape(10.dp), modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, end = 10.dp, start = 10.dp)
            .height(110.dp).clickable {
                article.id?.let { onArticleClick(it) }
            }

    ) {
        Row(modifier = Modifier.background(Color.White)) {

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(5.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(article.image),
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .width(100.dp)
                        .fillMaxHeight()
                        .background(Color.Green)
                        .padding(0.dp),

                )
            }

            Column(modifier = Modifier.padding(end = 10.dp)) {

                article.title?.let {
                    Text(
                        it,
                        style = TextStyle(
                            fontSize = 13.sp,
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = fontName
                        ),
                        color = KamiliDark,
                        modifier = Modifier.padding(start = 10.dp, top = 10.dp),
                        maxLines = 2, overflow = TextOverflow.Ellipsis
                    )
                }
                article.content?.let {
                    Text(
                        it,
                        style = TextStyle(
                            fontSize = 11.sp,
                            fontWeight =  FontWeight.ExtraBold,
                            fontFamily = fontName
                        ),
                        color = Color(0XFF555555),
                        modifier = Modifier.padding(start = 10.dp, top = 5.dp),
                        maxLines = 2, overflow = TextOverflow.Ellipsis

                    )
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth().padding(top = 10.dp, end = 10.dp)
                ) {


                    Text(
                        "By ${article.writer}",
                        style = TextStyle(
                            fontSize = 11.sp,
                            fontWeight = FontWeight.W700,
                            fontFamily = fontName,
                        ),
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 10.dp, top = 5.dp),

                        )
                    article.date?.let {
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