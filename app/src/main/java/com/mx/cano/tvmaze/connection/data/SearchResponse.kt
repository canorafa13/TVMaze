package com.mx.cano.tvmaze.connection.data

import com.google.gson.annotations.SerializedName

data class SearchResponse (
    @SerializedName("score" ) var score : Double? = null,
    @SerializedName("show"  ) var show  : Show?   = Show()
)