package com.nyika.kamili.viewmodel.login_vm

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyika.kamili.network.KamiliApi
import com.nyika.kamili.ui.theme.ForestGreen
import com.nyika.kamili.ui.theme.GoogleRed
import com.nyika.kamili.ui.theme.KamiliLight
import com.nyika.kamili.utils.KamiliUiEvents
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val retrofitInstance: KamiliApi,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiEvents = Channel<KamiliUiEvents>()
    val uiEvents = _uiEvents

    private val _infoText = mutableStateOf("")
    val infoText = _infoText

    val infoTextColor = mutableStateOf(Color.Black)
    val infoTextVisibility = mutableStateOf(false)

    private val _email = mutableStateOf("")
    val email = _email
    private val _password = mutableStateOf("")
    val password = _password
    private val _showPassword = mutableStateOf(false)
    val showPassword = _showPassword

    private val _emailFocusColor = mutableStateOf(KamiliLight)
    val emailFocusColor = _emailFocusColor

    private val _passwordFocusColor = mutableStateOf(KamiliLight)
    val passwordFocusColor = _passwordFocusColor

    val loginloading = mutableStateOf(0)
    val google_loading = mutableStateOf(0)


    //    GOOGLE SIGN IN
    private lateinit var oneTapClient: SignInClient
    private lateinit var signUpRequest: BeginSignInRequest


    fun updateEmailTextField(text: String) {
        email.value = text
        emailFocusColor.value = KamiliLight
        infoTextVisibility.value = false
        sendUiEvent(KamiliUiEvents.OnEmailFocusRequest)
    }

    fun updatePasswordTextField(text: String) {
        password.value = text
        passwordFocusColor.value = KamiliLight
        infoTextVisibility.value = false
        sendUiEvent(KamiliUiEvents.OnPasswordFocusRequest)
    }

    fun updatePasswordVisibility(vis: Boolean) {
        showPassword.value = vis
    }

    fun processLogIn() {
        if (email.value.isEmpty()) {
            infoText.value = "Please enter an email"
            infoTextColor.value = GoogleRed
            emailFocusColor.value = GoogleRed
            infoTextVisibility.value = true
            sendUiEvent(KamiliUiEvents.OnEmailFocusRequest)
        } else if (password.value.isEmpty()) {
            infoText.value = "Please enter a password"
            passwordFocusColor.value = GoogleRed
            infoTextColor.value = GoogleRed
            infoTextVisibility.value = true
            sendUiEvent(KamiliUiEvents.OnPasswordFocusRequest)
        } else {
            loginloading.value = 1
            viewModelScope.launch {
                try {
                    val firebaseAuth = FirebaseAuth.getInstance()
                    firebaseAuth.signInWithEmailAndPassword(email.value, password.value)
                        .addOnCompleteListener {

                            if (it.isSuccessful) {

                                infoText.value = "Login Successful!"
                                infoTextColor.value = ForestGreen
                                infoTextVisibility.value = true
                                sendUiEvent(KamiliUiEvents.OnSucessfulLogin)


                            } else {
                                loginloading.value = 0
                                infoText.value = it.exception!!.message.toString()
                                infoTextColor.value = GoogleRed
                                infoTextVisibility.value = true
                            }


                        }
//                    val res = retrofitInstance.processLogin(
//                        email = email.value,
//                        password = password.value
//                    )
//                    if (res.message == "true") {
//                        infoText.value = "Login Successful!"
//                        infoTextColor.value = ForestGreen
//                        infoTextVisibility.value = true
//                        sendUiEvent(KamiliUiEvents.OnSucessfulLogin)
//                    } else {
//                        infoText.value =
//                            "You have entered the wrong email or password. Please check and try again"
//                        infoTextColor.value = GoogleRed
//                        infoTextVisibility.value = true
//                    }

                } catch (e: IOException) {
                    print("IO Issue")
                } catch (e: HttpException) {
                    print("Net Issue")
                }
            }
        }

    }

    fun createFirebaseUserProfile(firstName: String, secondName: String, email: String) {
        val db = Firebase.firestore.collection("UserProfile")
        db.whereEqualTo("email", email).get().addOnSuccessListener { doc ->
            if (doc.isEmpty) {
                val profile = hashMapOf(
                    "first_name" to firstName,
                    "second_name" to secondName,
                    "email" to email
                )
                db.add(profile).addOnSuccessListener {
                    google_loading.value = 0
                    sendUiEvent(KamiliUiEvents.OnSucessfulLogin)
                }.addOnFailureListener {

                }
            }
            else{
                google_loading.value = 0
                sendUiEvent(KamiliUiEvents.OnSucessfulLogin)
                println("Profile already exists")
            }
        }


    }


    fun sendUiEvent(event: KamiliUiEvents) {
        viewModelScope.launch {
            _uiEvents.send(event)
        }
    }


}