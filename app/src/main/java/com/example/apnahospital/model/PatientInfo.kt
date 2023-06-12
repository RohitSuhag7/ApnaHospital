package com.example.apnahospital.model

data class PatientInfo(
    var id: String ?= "",
    var imgUrl: String ?= "",
    var name: String ?= "",
    var email: String ?= "",
    var phoneNumber: String ?= "",
    var gender: String ?= "",
    var age: String ?= "",
    var isFirstLogin: Boolean ?= false,
)
