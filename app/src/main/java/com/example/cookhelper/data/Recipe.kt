package com.example.cookhelper.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class Recipe(
    val imageResId: Int,
    val name: String,
    val ingredients: String,
    val instructions: String
)