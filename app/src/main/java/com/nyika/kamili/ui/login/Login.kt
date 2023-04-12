package com.nyika.kamili.ui.login

import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nyika.kamili.R
import com.nyika.kamili.animation.LoadingEffect
import com.nyika.kamili.ui.theme.*
import com.nyika.kamili.utils.KamiliUiEvents
import com.nyika.kamili.viewmodel.login_vm.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun Login(
    onDontHaveAccount: () -> Unit,
    onSuccessfulLogin:()-> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val fontName = FontFamily(Font(R.font.raleway_regular))
    val context = LocalContext.current
    val visibility = viewModel.showPassword
    val emailFocusRequestor = remember {
        FocusRequester()
    }
    val passwordFocusRequestor = remember {
        FocusRequester()
    }
//    val loggedInUser = FirebaseAuth.getInstance().currentUser?.email.toString()


    val token = stringResource(R.string.default_web_client_id)
    var user by remember { mutableStateOf(FirebaseAuth.getInstance().currentUser) }
    val launcher = rememberFirebaseAuthLauncher(
        onAuthComplete = { result ->
            user = result.user
        },
        onAuthError = {
            user = null
        },

    )

    LaunchedEffect(key1 = true) {
        viewModel.uiEvents.receiveAsFlow().collect() {
            when (it) {
                is KamiliUiEvents.ShowSnackbar -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                is KamiliUiEvents.OnSucessfulLogin -> {
                    onSuccessfulLogin()
                }
                is KamiliUiEvents.OnEmailFocusRequest ->{
                    emailFocusRequestor.requestFocus()
                }
                is KamiliUiEvents.OnPasswordFocusRequest ->{
                    passwordFocusRequestor.requestFocus()
                }
            }
        }
    }

    Column(
        Modifier.verticalScroll(rememberScrollState())
    ) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.nyika),
                contentDescription = "", modifier = Modifier
                    .padding(top = 50.dp)
                    .size(170.dp)
            )
            Text(
                "Welcome to Nyika",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W900,
                    fontFamily = fontName
                ),
                color = KamiliDark,
                modifier = Modifier.padding(start = 20.dp, top = 0.dp)
            )

            if(viewModel.infoTextVisibility.value){
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                ) {
                    Text(
                        viewModel.infoText.value,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.W900,
                            fontFamily = fontName
                        ),
                        color = viewModel.infoTextColor.value,
                        modifier = Modifier.padding(start = 20.dp, top = 20.dp, end = 20.dp)
                    )
                }
            }

        }


        OutlinedTextField(
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = KamiliDark,
                focusedIndicatorColor = viewModel.emailFocusColor.value
            ),
            textStyle = TextStyle.Default.copy(fontFamily = fontName),
            value = viewModel.email.value,
            placeholder = {
                Text(
                    text = "Email", style = TextStyle(
                        fontFamily = fontName
                    )
                )
            },
            onValueChange = {
                viewModel.updateEmailTextField(it)
            },
            modifier = Modifier
                .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                .fillMaxWidth()
                .height(50.dp)
                .focusRequester(emailFocusRequestor),
            maxLines = 1
        )

        OutlinedTextField(
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = KamiliDark,
                focusedIndicatorColor = viewModel.passwordFocusColor.value
            ),
            textStyle = TextStyle.Default.copy(fontFamily = fontName),
            value = viewModel.password.value,
            placeholder = {
                Text(
                    text = "Password", style = TextStyle(
                        fontFamily = fontName
                    )
                )
            },
            onValueChange = {
                viewModel.updatePasswordTextField(it)
            },
            modifier = Modifier
                .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                .fillMaxWidth()
                .height(50.dp)
                .focusRequester(passwordFocusRequestor),
            maxLines = 1,
            visualTransformation = if (visibility.value) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                if (visibility.value)
                    IconButton(onClick = {
                        viewModel.updatePasswordVisibility(false)
                    }) {
                        Icon(Icons.Rounded.VisibilityOff, contentDescription = "")
                    }
                else
                    IconButton(onClick = {
                        viewModel.updatePasswordVisibility(true)
                    }) {
                        Icon(Icons.Rounded.Visibility, contentDescription = "")
                    }
            }


        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                "Forgot Password?",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W900,
                    fontFamily = fontName
                ),
                color = KamiliDark,
                modifier = Modifier.padding(start = 20.dp, top = 20.dp, end = 20.dp)
            )
        }

        if (viewModel.loginloading.value == 0){
            Button(
                onClick = {
                    viewModel.processLogIn()
                },
                modifier = Modifier
                    .padding(start = 20.dp, top = 20.dp, end = 20.dp, bottom = 20.dp)
                    .height(50.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = KamiliDark,
                )
            ) {
                Text(
                    "Login",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W700,
                        fontFamily = fontName
                    ),
                    color = Color.White,
                )
            }
        }else{
            LoadingEffect()
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                "Or Login with",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W900,
                    fontFamily = fontName
                ),
                color = KamiliDark,
                modifier = Modifier.padding(start = 20.dp, top = 0.dp, end = 20.dp)
            )
        }



//        Button(
//            onClick = {
//
//
//            },
//            modifier = Modifier
//                .padding(start = 20.dp, top = 20.dp, end = 20.dp, bottom = 20.dp)
//                .height(50.dp)
//                .fillMaxWidth(),
//            shape = RoundedCornerShape(5.dp),
//            border = BorderStroke(1.dp, FacebookBlue),
//            colors = ButtonDefaults.buttonColors(
//                backgroundColor = FacebookBlue,
//            )
//        ) {
//
//            Image(
//                painter = painterResource(id = R.drawable.face3), contentDescription = "",
//                modifier = Modifier.size(25.dp),
//            )
//
//            Text(
//                "Login with Facebook",
//                style = TextStyle(`
//                    fontSize = 14.sp,
//                    fontWeight = FontWeight.W700,
//                    fontFamily = fontName
//                ),
//                color = Color.White,
//                modifier = Modifier.padding(start = 5.dp)
//            )
//        }
//


        if (viewModel.google_loading.value == 0) {
            Button(
                onClick = {
                    viewModel.google_loading.value = 1
                    val gso =
                        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken(token)
                            .requestEmail()
                            .build()
                    val googleSignInClient = GoogleSignIn.getClient(context, gso)
                    launcher.launch(googleSignInClient.signInIntent)
                },
                modifier = Modifier
                    .padding(start = 20.dp, top = 10.dp, end = 20.dp, bottom = 20.dp)
                    .height(50.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = GoogleRed,
                )
            ) {

                Image(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = "",
                    modifier = Modifier.size(20.dp)
                )

                Text(
                    "Login with Google",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W700,
                        fontFamily = fontName
                    ),
                    color = Color.White,
                    modifier = Modifier.padding(start = 5.dp)
                )
            }
        }
        else{
            LoadingEffect()
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                "Don't have an account? Sign up",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W900,
                    fontFamily = fontName
                ),
                color = KamiliDark,
                modifier = Modifier
                    .padding(start = 20.dp, top = 0.dp, end = 20.dp, bottom = 20.dp)
                    .clickable {
                        onDontHaveAccount()
                    },

                )
        }



    }


}
@Composable
fun rememberFirebaseAuthLauncher(
    onAuthComplete: (AuthResult) -> Unit,
    onAuthError: (ApiException) -> Unit,
    loginViewModel: LoginViewModel = hiltViewModel()
): ManagedActivityResultLauncher<Intent, ActivityResult> {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    return rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {

            val account = task.getResult(ApiException::class.java)!!
            val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
            scope.launch {

                val authResult =  FirebaseAuth.getInstance().signInWithCredential(credential).await()
                onAuthComplete(authResult)
                if(authResult.user?.email != null){

                    loginViewModel.createFirebaseUserProfile("","",authResult.user!!.email.toString())
                }

            }
        } catch (e: ApiException) {
            onAuthError(e)
        }
    }
}

