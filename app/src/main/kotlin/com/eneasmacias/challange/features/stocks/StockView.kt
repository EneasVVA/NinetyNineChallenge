
package com.eneasmacias.challange.features.stocks

import android.os.Parcel
import com.eneasmacias.challange.core.platform.KParcelable
import com.eneasmacias.challange.core.platform.parcelableCreator

data class StockView(val id: String) : KParcelable {
    companion object {
        @JvmField
        val CREATOR = parcelableCreator(::StockView)
    }

    constructor(parcel: Parcel) : this(parcel.readString()!!)

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeString(id)
        }
    }
}
