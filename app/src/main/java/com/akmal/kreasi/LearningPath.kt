package com.akmal.kreasi

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LearningPath(
    val name: String,
    val description:String,
    val photo: Int
): Parcelable


@Parcelize
data class History(
    val title: String,
    val date: String
): Parcelable
