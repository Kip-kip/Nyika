package com.example.kamili

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.kamili.ui.Book
import com.example.kamili.ui.account.Account
import com.example.kamili.ui.articles.ArticleInfo
import com.example.kamili.ui.articles.Articles
import com.example.kamili.ui.booking.BookingDetails
import com.example.kamili.ui.booking.MyBookings
import com.example.kamili.ui.home.BottomNav
import com.example.kamili.ui.home.HomeScreen
import com.example.kamili.ui.login.Login
import com.example.kamili.ui.reviews.ReviewDetails
import com.example.kamili.ui.settings.Settings
import com.example.kamili.ui.signup.Signup
import com.example.kamili.ui.theme.KamiliDark
import com.example.kamili.ui.theme.KamiliTheme
import com.example.kamili.utils.Routes
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KamiliTheme {

                val navController = rememberNavController()

                val loggedInUser = FirebaseAuth.getInstance().currentUser
                var showBottomBar by rememberSaveable { mutableStateOf(true) }
                val navBackStackEntry by navController.currentBackStackEntryAsState()

                showBottomBar = when (navBackStackEntry?.destination?.route) {
                    Routes.LOGIN -> false
                    Routes.SIGNUP -> false
                    else -> true
                }

                Scaffold(
                    bottomBar = {
                        if (showBottomBar) {
                            BottomNav(
                                navController = navController,
                                fontName = FontFamily(Font(R.font.raleway_regular)),
                                modifier = Modifier
                                    .background(KamiliDark)
                                    .fillMaxWidth()
                                    .padding(5.dp)
                            )
                        }

                    }
                ) {


                    NavHost(
                        navController = navController,
                        startDestination =  if(loggedInUser!=null) Routes.HOME_SCREEN else Routes.LOGIN
                    ) {
                        composable(Routes.HOME_SCREEN) {
                            HomeScreen(
                                onPopBackStack = {
                                    navController.navigate(Routes.HOME_SCREEN) {
                                        popUpTo(Routes.SIGNUP) {
                                            inclusive = true
                                        }
                                    }
                                    navController.popBackStack()
                                    navController.navigate(Routes.HOME_SCREEN)
                                },
                                onBookingInitiated = {
                                navController.navigate(Routes.BOOKING_SCREEN)
                            }, onReviewClicked = {
                                navController.navigate(Routes.REVIEW_DETAILS + "/$it")
                            }, onSeeAll = {
                                navController.navigate(Routes.ARTICLES_SCREEN)
                            }, onArticleClicked = {
                                navController.navigate(Routes.ARTICLE_INFO + "/$it")
                            }
                            )
                        }
                        composable(Routes.BOOKING_SCREEN) {
                            Book(onPopBackStack = {
                                navController.popBackStack()
                            })
                        }
                        composable(Routes.ARTICLES_SCREEN) {
                            Articles(onPopBackStack = {
                                navController.popBackStack()
                            }, onArticleClicked = {
                                navController.navigate(Routes.ARTICLE_INFO + "/$it")
                            }
                            )
                        }
                        composable(Routes.SETTINGS) {
                            Settings(onAccountClicked = {
                                navController.navigate(Routes.ACCOUNT)
                            },
                                onLogOut = {
                                    navController.navigate(Routes.LOGIN)
                                },
                                onPopBackStack = {
                                navController.popBackStack()
                            })
                        }
                        composable(Routes.ACCOUNT) {
                            Account(onPopBackStack = {
                                navController.popBackStack()
                            })
                        }
                        composable(Routes.LOGIN) {
                            Login(onDontHaveAccount = {
                                navController.navigate(Routes.SIGNUP)
                            }, onSuccessfulLogin = {
                                navController.navigate(Routes.HOME_SCREEN)
                            }
                            )
                        }
                        composable(Routes.SIGNUP) {
                            Signup(onAlreadyRegistered = {
                                navController.navigate(Routes.LOGIN)
                            },
                            onSuccessfulSignin = {
                                navController.navigate(Routes.LOGIN)
                            })
                        }
                        composable(Routes.MY_BOOKINGS) {
                            MyBookings(onPopBackStack = {
                                navController.popBackStack()
                            }, onBookingClicked = {
                                navController.navigate(Routes.BOOKING_DETAILS + "/$it")
                            })
                        }
                        composable(
                            Routes.BOOKING_DETAILS + "/{bookingId}",
                            arguments = listOf(
                                navArgument(name = "bookingId") {
                                    type = NavType.StringType
                                    defaultValue = ""
                                }
                            )) {
                            BookingDetails(onPopBackStack = {
                                navController.popBackStack()
                            })
                        }
                        composable(Routes.ARTICLE_INFO + "/{articleId}",
                            arguments = listOf(
                                navArgument(name = "articleId") {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            ArticleInfo(onPopBackStack = {
                                navController.popBackStack()
                            })
                        }
                        composable(Routes.REVIEW_DETAILS + "/{reviewId}",
                            arguments = listOf(
                                navArgument(name = "reviewId") {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            ReviewDetails(onPopBackStack = {
                                navController.popBackStack()
                            })
                        }
                    }


                }


            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    KamiliTheme {

    }
}
