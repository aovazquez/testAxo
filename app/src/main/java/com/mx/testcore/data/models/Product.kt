package com.mx.testcore.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product (
    @SerializedName("id") val id: Int? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("price") val price: Double? = null,
    @SerializedName("thumbnail") val thumbnail: String? = null,
    @SerializedName("images") val image: List<String>? = null
): Parcelable

@Parcelize
data class MainProduct (
    @SerializedName("products") val products: List<Product>
): Parcelable