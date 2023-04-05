package com.example.kamili.viewmodel.home_vm

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kamili.database.kamili_services.ServicesRepository
import com.example.kamili.network.KamiliApi
import com.example.kamili.utils.KamiliUiEvents
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val retrofitInstance: KamiliApi,
    private val servicesRepositoryImpl: ServicesRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiEvents = Channel<KamiliUiEvents>()
    val uiEvents = _uiEvents

    private val pref: SharedPreferences = context.getSharedPreferences("userProfilePref", 0) // 0 - for private mode
    private val editor: SharedPreferences.Editor = pref.edit()

    private val _firstName =  pref.getString("firstName", "")
    val firstName = _firstName
    private val _secondName =  pref.getString("secondName", "")
    val secondName = _secondName
    private val _profilePic =  pref.getString("profilePic", "")
    val profilePic = _profilePic

    val servicesRef = Firebase.firestore.collection("Services")
    val db = Firebase.firestore.collection("UserProfile")



    init {
        getFirebaseServices()
        val email = FirebaseAuth.getInstance().currentUser!!.email.toString()
        getUserProfile(email)
    }

    private val _services = servicesRepositoryImpl.getServices()
    val services = _services

    fun getUserProfile(email: String) {

        db.whereEqualTo("email", email).get().addOnSuccessListener { doc ->
//            firstName.value = doc.first().data["first_name"].toString()
//            secondName.value = doc.first().data["second_name"].toString()
//            profilePic.value = doc.first().data["profile_pic"].toString()
            if(!doc.isEmpty) {
                val refid = doc.first().reference.id
                val fname = doc.first().data["first_name"].toString()
                val sname = doc.first().data["second_name"].toString()
                val ppic = doc.first().data["profile_pic"].toString()

                saveUserProfile(fname,sname,ppic,refid)
            }

        }


    }

    fun saveUserProfile(fname: String,sname:String,ppic:String,refid:String){
        editor.putString("firstName", fname).apply()
        editor.putString("secondName", sname).apply()
        editor.putString("profilePic", ppic).apply()
        editor.putString("refId", refid).apply()
        editor.commit()

    }


    private fun getServices() {
        viewModelScope.launch {
            try {
                val remoteServices = retrofitInstance.getKamiliServices()
                remoteServices.let {
                    it
                    for (item in it.message) {
                        servicesRepositoryImpl.insertService(item)
                    }
                }

            } catch (e: IOException) {
                println("IO issue")
            } catch (e: HttpException) {
                println("HTTP issue")
            }

        }

    }

    private fun getFirebaseServices() {

        servicesRef.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            firebaseFirestoreException?.let {
                sendUiEvent(KamiliUiEvents.ShowSnackbar(message = firebaseFirestoreException.message.toString()))
            }
            querySnapshot?.let {
                for (document in it) {
                    viewModelScope.launch {
                        servicesRepositoryImpl.insertService(document.toObject())
                    }
                }
            }
        }

    }

    fun sendUiEvent(event: KamiliUiEvents) {
        viewModelScope.launch {
            _uiEvents.send(event)
        }
    }
}