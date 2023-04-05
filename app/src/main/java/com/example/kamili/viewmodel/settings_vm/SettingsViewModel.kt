package com.example.kamili.viewmodel.settings_vm


import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kamili.network.KamiliApi
import com.example.kamili.ui.theme.GoogleRed
import com.example.kamili.ui.theme.KamiliLight
import com.example.kamili.utils.KamiliUiEvents
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
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