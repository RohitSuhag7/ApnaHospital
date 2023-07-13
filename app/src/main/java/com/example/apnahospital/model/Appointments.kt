package com.example.apnahospital.model

data class Appointments(
    var key: String ?= "",
    val image: String ?= "",
    val name: String ?= "",
    val pnumber: String ?= "",
    val relation: String ?= "",
    val specialities: String ?= "",
    val doctor: String ?= "",
    val gender: String ?= "",
    val age: String ?= "",
)
