package com.example.apnahospital.model

data class Appointments(
    val id: String ?= "",
    val image: String ?= "",
    val name: String ?= "",
    val pNumber: String ?= "",
    val relation: String ?= "",
    val specialities: String ?= "",
    val doctor: String ?= "",
    val gender: String ?= "",
    val age: String ?= "",
)
