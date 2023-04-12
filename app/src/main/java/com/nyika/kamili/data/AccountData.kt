package com.nyika.kamili.data

data class AccountResponse (
    val message: List<AccountData>
        )

data class AccountData(
    val first_name: String?=null,
    val second_name: String?=null,
    val photo: String?=null,
    val gender: String?=null,
    val dob: String?=null,
    val email: String?=null
)