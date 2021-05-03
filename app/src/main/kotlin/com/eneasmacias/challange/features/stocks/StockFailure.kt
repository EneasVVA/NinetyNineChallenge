
package com.eneasmacias.challange.features.stocks

import com.eneasmacias.challange.core.exception.Failure.FeatureFailure

class StockFailure {
    class ListNotAvailable : FeatureFailure()
    class NonExistentStock : FeatureFailure()
}

