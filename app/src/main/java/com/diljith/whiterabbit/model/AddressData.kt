package com.diljith.whiterabbit.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AddressData(
    val city: String,
    val geo: Geo,
    val street: String,
    val suite: String,
    val zipcode: String
): Parcelable