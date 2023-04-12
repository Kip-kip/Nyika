package com.nyika.kamili.viewmodel.account_vm

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyika.kamili.utils.KamiliUiEvents
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiEvents = Channel<KamiliUiEvents>()
    val uiEvents = _uiEvents

    private val pref: SharedPreferences = context.getSharedPreferences("userProfilePref", 0) // 0 - for private mode

    val fName = pref.getString("firstName","")
    val sName = pref.getString("secondName","")
    val pPic = pref.getString("profilePic","")
    val refId = pref.getString("refId","")
    val email = FirebaseAuth.getInstance().currentUser!!.email.toString()

    private val _firstName = mutableStateOf(fName)
    val firstName = _firstName
    private val _secondName = mutableStateOf(sName)
    val secondName = _secondName
    private val _useremail = mutableStateOf(email)
    val useremail = _useremail
    private val _profilePic = mutableStateOf(pPic)
    val profilePic = _profilePic
    private val _referenceId = mutableStateOf(refId)
    val referenceId = _referenceId


    val db = Firebase.firestore.collection("UserProfile")

    init {

    }


    fun receiveUIEvents(event: AccountEvents) {
        when (event) {
            is AccountEvents.updateFirstName -> {
                updateFnameTextField(event.text)
            }
            is AccountEvents.updateSecondName -> {
                updateSnameTextField(event.text)
            }
            is AccountEvents.updateEmail -> {
                sendUiEvent(KamiliUiEvents.ShowSnackbar("Email cannot be edited as it is linked to current session"))
            }
            is AccountEvents.updateUserProfile -> {
                updateUserProfile()
            }
            else -> {

            }
        }
    }

    fun getUserProfile(email: String) {

        db.whereEqualTo("email", email).get().addOnSuccessListener { doc ->
            referenceId.value = doc.first().reference.id
            firstName.value = doc.first().data["first_name"].toString()
            secondName.value = doc.first().data["second_name"].toString()
            profilePic.value = doc.first().data["profile_pic"].toString()
            useremail.value = doc.first().data["email"].toString()

        }
    }

    fun updateUserProfile() {
        sendUiEvent(KamiliUiEvents.ShowSnackbar("Profile updated successfully!"))
        db.document(referenceId.value!!).update("first_name", firstName.value)
        db.document(referenceId.value!!).update("second_name", secondName.value)
    }

    fun updateFnameTextField(text: String) {
        firstName.value = text

    }

    fun uploadImage(imageUri: Uri) {
        sendUiEvent(KamiliUiEvents.ShowSnackbar("Picture upload in progress......"))
        var file = imageUri
        val storageRef = FirebaseStorage.getInstance().reference
        val riversRef = storageRef.child("images/${referenceId.value}")
        val uploadTask = file?.let { riversRef.putFile(it) }

        uploadTask.addOnSuccessListener {
            sendUiEvent(KamiliUiEvents.ShowSnackbar("Picture uploaded successfully!"))
            val downloadUrl = riversRef.downloadUrl
            downloadUrl.addOnSuccessListener {
                db.document(referenceId.value!!).update("profile_pic", it.toString())
            }
        }
            .addOnFailureListener {
            }
    }

    fun updateSnameTextField(text: String) {
        secondName.value = text
    }

    fun sendUiEvent(event: KamiliUiEvents) {
        viewModelScope.launch {
            _uiEvents.send(event)
        }
    }

}