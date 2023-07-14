package com.example.apnahospital.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Appointments(
    var key: String? = "",
    val image: String? = "",
    val name: String? = "",
    val pnumber: String? = "",
    val relation: String? = "",
    val specialities: String? = "",
    val doctor: String? = "",
    val gender: String? = "",
    val age: String? = "",
) : Parcelable
