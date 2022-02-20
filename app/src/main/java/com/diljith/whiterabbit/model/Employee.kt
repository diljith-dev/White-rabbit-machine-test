package com.diljith.whiterabbit.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Employee(
    val address: AddressData?,
    val company: Company?,
    val email: String,
    val id: String,
    val name: String,
    val phone: String?,
    val profile_image: String?,
    val username: String?,
    val website: String?
): Parcelable