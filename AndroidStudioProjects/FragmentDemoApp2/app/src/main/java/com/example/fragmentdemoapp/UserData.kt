package com.example.fragmentdemoapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserData(
    val name: String = "",
    val age: Int = 0,
    val isStudent: Boolean = false,
    val email: String = ""
) : Parcelable

object DataTransferManager {
    var sharedData: String = ""
    var userData: UserData? = null
}