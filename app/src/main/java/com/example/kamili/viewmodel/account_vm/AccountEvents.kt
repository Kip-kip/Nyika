package com.example.kamili.viewmodel.account_vm

sealed class AccountEvents {
    data class updateFirstName(val text: String): AccountEvents()
    data class updateSecondName(val text: String): AccountEvents()
    object updateEmail: AccountEvents()
    object updateUserProfile: AccountEvents()
}