package com.example.apnahospital.utils

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation.findNavController
import com.google.gson.Gson

fun navigateTo(view: View?, id: Int, bundle: Bundle ?= null) {
    findNavController(view!!).navigate(id)
}

fun <T> getJson(json: String?, clazz: Class<T>?): T {
    return Gson().fromJson(json.toString(), clazz)
}
