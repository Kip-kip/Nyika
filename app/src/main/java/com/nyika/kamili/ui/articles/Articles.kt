package com.nyika.kamili.ui.articles

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.hilt.navigation.compose.hiltViewModel
import com.nyika.kamili.R
import com.nyika.kamili.ui.articles.components.ArticleCard
import com.nyika.kamili.ui.theme.KamiliWhite
import com.nyika.kamili.utils.ui.SimpleAppBar
import com.nyika.kamili.viewmodel.articles_vm.ArticlesViewModel

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