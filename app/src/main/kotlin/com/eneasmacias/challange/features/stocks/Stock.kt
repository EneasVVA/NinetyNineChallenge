
package com.eneasmacias.challange.features.stocks

import com.eneasmacias.challange.core.extension.empty

data class Stock(internal val id: String) {

    companion object {
        val empty = Stock(String.empty())
    }
}
