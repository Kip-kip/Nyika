package com.nyika.kamili.viewmodel.signup_vm


import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyika.kamili.network.KamiliApi
import com.nyika.kamili.ui.theme.GoogleRed
import com.nyika.kamili.ui.theme.KamiliLight
import com.nyika.kamili.utils.KamiliUiEvents
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val retrofitInstance: KamiliApi
) : ViewModel() {

    private val _uiEvents = Channel<KamiliUiEvents>()
    val uiEvents = _uiEvents

    private val _fname = mutableStateOf("")
    val fname = _fname

    private val _sname = mutableStateOf("")
    val sname = _sname

    private val _email = mutableStateOf("")
    val email = _email

    private val _password = mutableStateOf("")
    val password = _password

    private val _cpassword = mutableStateOf("")
    val cpassword = _cpassword


    private val _showPassword = mutableStateOf(false)
    val showPassword = _showPassword

    private val _showCPassword = mutableStateOf(false)
    val showCPassword = _showCPassword

    private val _fnameFocusColor = mutableStateOf(KamiliLight)
    val fnameFocusColor = _fnameFocusColor

    private val _snameFocusColor = mutableStateOf(KamiliLight)
    val snameFocusColor = _snameFocusColor

    private val _emailFocusColor = mutableStateOf(KamiliLight)
    val emailFocusColor = _emailFocusColor

    private val _passwordFocusColor = mutableStateOf(KamiliLight)
    val passwordFocusColor = _passwordFocusColor

    private val _cpasswordFocusColor = mutableStateOf(KamiliLight)
    val cpasswordFocusColor = _cpasswordFocusColor

    private val _infoText = mutableStateOf("")
    val infoText = _infoText

    val infoTextColor = mutableStateOf(Color.Black)
    val infoTextVisibility = mutableStateOf(false)

    val db = Firebase.firestore.collection("UserProfile")

    fun updateFnameTextField(text: String) {
        fname.value = text
        fnameFocusColor.value = KamiliLight
        sendUiEvent(KamiliUiEvents.OnFnameFocusRequest)
    }

    fun updateSnameTextField(text: String) {
        sname.value = text
        snameFocusColor.value = KamiliLight
        sendUiEvent(KamiliUiEvents.OnSnameFocusRequest)
    }

    fun updateEmailTextField(text: String) {
        email.value = text
        emailFocusColor.value = KamiliLight
        sendUiEvent(KamiliUiEvents.OnEmailFocusRequest)
    }

    fun updatePasswordTextField(text: String) {
        password.value = text
        passwordFocusColor.value = KamiliLight
        sendUiEvent(KamiliUiEvents.OnPasswordFocusRequest)
    }

    fun updateCPasswordTextField(text: String) {
        cpassword.value = text
        cpasswordFocusColor.value = KamiliLight
        sendUiEvent(KamiliUiEvents.OnCPasswordFocusRequest)
    }

    fun updatePasswordVisibility(vis: Boolean) {
        showPassword.value = vis
    }

    fun updateCPasswordVisibility(vis: Boolean) {
        showCPassword.value = vis
    }

    fun processSignIn() {
        if (fname.value.isEmpty()) {
            infoText.value = "Please enter first name"
            infoTextColor.value = GoogleRed
            fnameFocusColor.value = GoogleRed
            infoTextVisibility.value = true
            sendUiEvent(KamiliUiEvents.OnFnameFocusRequest)

        } else if (sname.value.isEmpty()) {
            infoText.value = "Please enter second name"
            infoTextColor.value = GoogleRed
            snameFocusColor.value = GoogleRed
            infoTextVisibility.value = true
            sendUiEvent(KamiliUiEvents.OnSnameFocusRequest)

        } else if (email.value.isEmpty()) {

            infoText.value = "Please enter email"
            infoTextColor.value = GoogleRed
            emailFocusColor.value = GoogleRed
            infoTextVisibility.value = true
            sendUiEvent(KamiliUiEvents.OnEmailFocusRequest)

        } else if (password.value.isEmpty()) {

            infoText.value = "Please enter a password"
            infoTextColor.value = GoogleRed
            passwordFocusColor.value = GoogleRed
            infoTextVisibility.value = true
            sendUiEvent(KamiliUiEvents.OnPasswordFocusRequest)


        } else if (cpassword.value.isEmpty()) {

            infoText.value = "Please confirm password"
            infoTextColor.value = GoogleRed
            cpasswordFocusColor.value = GoogleRed
            infoTextVisibility.value = true
            sendUiEvent(KamiliUiEvents.OnCPasswordFocusRequest)

        } else if (password.value != cpassword.value) {

            infoText.value = "Please ensure your passwords match"
            infoTextVisibility.value = true
            infoTextColor.value = GoogleRed

        } else {
            infoText.value = ""
            viewModelScope.launch {
                try {
                    val firebaseAuth = FirebaseAuth.getInstance()
                    firebaseAuth.createUserWithEmailAndPassword(email.value, password.value)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                sendUiEvent(KamiliUiEvents.ShowSnackbar("Registration Successful!"))
                                createFirebaseUserProfile(fname.value, sname.value, email.value)
                                sendUiEvent(KamiliUiEvents.OnSucessfulSignin)
                            } else {
                                sendUiEvent(KamiliUiEvents.ShowSnackbar(it.exception!!.message.toString()))
                            }
                        }


//                    val res = retrofitInstance.processSignin(fname=fname.value,sname=sname.value,email = email.value, password = password.value)
//                    if (res.message == "true"){
//                        sendUiEvent(KamiliUiEvents.ShowSnackbar("Signin Successful"))
//                        sendUiEvent(KamiliUiEvents.OnSucessfulLogin)
//                    }
//                    else{
//                        sendUiEvent(KamiliUiEvents.ShowSnackbar(res.message.toString()))
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

        db.whereEqualTo("email", email).get().addOnSuccessListener { doc ->
            if (doc.isEmpty) {
                val profile = hashMapOf(
                    "first_name" to firstName,
                    "second_name" to secondName,
                    "email" to email
                )
                db.add(profile).addOnSuccessListener {
                    sendUiEvent(KamiliUiEvents.OnSucessfulSignin)
                }.addOnFailureListener {

                }
            }
            else{
                println("Account already exists")
            }
        }


    }


    fun sendUiEvent(event: KamiliUiEvents) {
        viewModelScope.launch {
            _uiEvents.send(event)
        }
    }


}