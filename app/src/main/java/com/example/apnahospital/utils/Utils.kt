package com.example.apnahospital.utils

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation.findNavController

fun navigateTo(view: View?, id: Int, bundle: Bundle ?= null) {
    findNavController(view!!).navigate(id)
}
