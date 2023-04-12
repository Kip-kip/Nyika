package com.nyika.kamili.utils

sealed class KamiliUiEvents(){
    data class ShowSnackbar(val message: String) : KamiliUiEvents()
    object OnSucessfulLogin : KamiliUiEvents()
    object OnSucessfulSignin : KamiliUiEvents()
    object OnEmailFocusRequest : KamiliUiEvents()
    object OnPasswordFocusRequest : KamiliUiEvents()
    object OnCPasswordFocusRequest : KamiliUiEvents()
    object OnFnameFocusRequest : KamiliUiEvents()
    object OnSnameFocusRequest : KamiliUiEvents()
    object OnLogOut : KamiliUiEvents()
}
