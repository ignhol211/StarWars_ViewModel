package com.example.ejemplointernet

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class PlanetaResponse (
    val count: Long,
    val next: String,
    val results: List<Planeta>
) : Parcelable