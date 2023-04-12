package com.nyika.kamili.viewmodel.settings_vm


import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyika.kamili.utils.KamiliUiEvents
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    @ApplicationContext private val context: Context
): ViewModel() {

    private val _uiEvents = Channel<KamiliUiEvents>()
    val uiEvents = _uiEvents

    private val auth = FirebaseAuth.getInstance()

    private val pref: SharedPreferences = context.getSharedPreferences("userProfilePref", 0) // 0 - for private mode

    val firstName = pref.getString("firstName","")
    val secondName = pref.getString("secondName","")

    fun processLogOut() {
        auth.signOut()
        sendUiEvent(KamiliUiEvents.OnLogOut)
    }



    fun sendUiEvent(event: KamiliUiEvents) {
        viewModelScope.launch {
            _uiEvents.send(event)
        }
    }


}