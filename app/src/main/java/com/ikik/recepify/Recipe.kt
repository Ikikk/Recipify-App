package com.ikik.recepify

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(
    val name: String,
    val description: String,
    val photo: Int,
    val ingredient: String = "",
    val step: String = ""
) : Parcelable
