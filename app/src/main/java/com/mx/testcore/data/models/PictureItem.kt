package com.mx.testcore.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class PhotoItem (
    var id: String? = null,
    var url: String? = null,
    var timestamp: Long? = null,
    var description: String? = null
): Parcelable