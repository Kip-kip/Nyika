package com.nyika.kamili.ui.signup


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import com.nyika.kamili.ui.theme.KamiliDark
import com.nyika.kamili.utils.KamiliUiEvents
import com.nyika.kamili.viewmodel.signup_vm.SignupViewModel
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
fun Signup(onAlreadyRegistered: () -> Unit,onSuccessfulSignin:()->Unit, viewModel: SignupViewModel = hiltViewModel()) {
    val fontName = FontFamily(Font(R.font.raleway_regular))
    val context = LocalContext.current
    val visibility = viewModel.showPassword

    val fnameFocusRequestor = remember {
        FocusRequester()
    }
    val snameFocusRequestor = remember {
        FocusRequester()
    }

    val emailFocusRequestor = remember {
        FocusRequester()
    }
    val passwordFocusRequestor = remember {
        FocusRequester()
    }

    val cpasswordFocusRequestor = remember {
        FocusRequester()
    }


    LaunchedEffect(key1 = true) {
        viewModel.uiEvents.receiveAsFlow().collect() {
            when (it) {
                is KamiliUiEvents.ShowSnackbar -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                is KamiliUiEvents.OnSucessfulSignin -> {
                    onSuccessfulSignin()
                }
                is KamiliUiEvents.OnFnameFocusRequest ->{
                    fnameFocusRequestor.requestFocus()
                }
                is KamiliUiEvents.OnSnameFocusRequest ->{
                    snameFocusRequestor.requestFocus()
                }
                is KamiliUiEvents.OnEmailFocusRequest ->{
                    emailFocusRequestor.requestFocus()
                }
                is KamiliUiEvents.OnPasswordFocusRequest ->{
                    passwordFocusRequestor.requestFocus()
                }
                is KamiliUiEvents.OnCPasswordFocusRequest ->{
                    cpasswordFocusRequestor.requestFocus()
                }
            }
        }
    }

    Column() {

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
                focusedIndicatorColor = viewModel.fnameFocusColor.value
            ),
            textStyle = TextStyle.Default.copy(fontFamily = fontName),
            value = viewModel.fname.value,
            placeholder = {
                Text(
                    text = "First Name", style = TextStyle(
                        fontFamily = fontName
                    )
                )
            },
            onValueChange = {
                viewModel.updateFnameTextField(it)

            },
            modifier = Modifier
                .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                .fillMaxWidth()
                .height(50.dp)
                .focusRequester(fnameFocusRequestor),
            maxLines = 1
        )

        OutlinedTextField(
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = KamiliDark,
                focusedIndicatorColor = viewModel.snameFocusColor.value
            ),
            textStyle = TextStyle.Default.copy(fontFamily = fontName),
            placeholder = {
                Text(
                    text = "Second Name", style = TextStyle(
                        fontFamily = fontName
                    )
                )
            },
            value = viewModel.sname.value,
            onValueChange = {
                viewModel.updateSnameTextField(it)

            },
            modifier = Modifier
                .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                .fillMaxWidth()
                .height(50.dp)
                .focusRequester(snameFocusRequestor),
            maxLines = 1
        )

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

        OutlinedTextField(
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = KamiliDark,
                focusedIndicatorColor = viewModel.cpasswordFocusColor.value
            ),
            textStyle = TextStyle.Default.copy(fontFamily = fontName),
            value = viewModel.cpassword.value,
            placeholder = {
                Text(
                    text = "Confirm Password", style = TextStyle(
                        fontFamily = fontName
                    )
                )
            },
            onValueChange = {
                viewModel.updateCPasswordTextField(it)
            },
            modifier = Modifier
                .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                .fillMaxWidth()
                .height(50.dp)
                .focusRequester(cpasswordFocusRequestor),
            maxLines = 1,
            visualTransformation = if (visibility.value) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                if (visibility.value)
                    IconButton(onClick = {
                        viewModel.updateCPasswordVisibility(false)
                    }) {
                        Icon(Icons.Rounded.VisibilityOff, contentDescription = "")
                    }
                else
                    IconButton(onClick = {
                        viewModel.updateCPasswordVisibility(true)
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
                "Already registered? Login",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W900,
                    fontFamily = fontName
                ),
                color = KamiliDark,
                modifier = Modifier
                    .padding(start = 20.dp, top = 20.dp, end = 20.dp)
                    .clickable {
                        onAlreadyRegistered()
                    },

                )
        }

        Button(
            onClick = {
                viewModel.processSignIn()
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
                "Sign Up",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W700,
                    fontFamily = fontName
                ),
                color = Color.White,
            )
        }


    }


}