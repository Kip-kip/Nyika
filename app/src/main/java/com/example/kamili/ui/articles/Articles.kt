package com.example.kamili.ui.articles

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kamili.R
import com.example.kamili.data.ArticlesData
import com.example.kamili.ui.articles.components.ArticleCard
import com.example.kamili.ui.theme.KamiliLighter
import com.example.kamili.ui.theme.KamiliWhite
import com.example.kamili.utils.ui.Appbar
import com.example.kamili.utils.ui.SimpleAppBar
import com.example.kamili.viewmodel.articles_vm.ArticlesViewModel

@Composable
fun Articles(onPopBackStack: ()->Unit,onArticleClicked:(articleId: Int)->Unit,articlesViewModel: ArticlesViewModel = hiltViewModel()) {
    val fontName = FontFamily(Font(R.font.raleway_regular))

    val articles = articlesViewModel.articles.collectAsState(initial = emptyList())


    Column(
        modifier = Modifier
            .background(KamiliWhite)
            .fillMaxSize()
    ) {
        SimpleAppBar(R.drawable.article,Color.Black,0.1f){
            onPopBackStack()
        }

        articles.value.forEach { it
            ArticleCard(fontName,it){id->
                onArticleClicked(id)
            }
        }


    }


}