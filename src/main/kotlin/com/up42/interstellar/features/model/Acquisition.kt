package com.up42.interstellar.features.model

import java.util.Date

data class Acquisition(
        val endViewingDate: Date,
        val beginViewingDate: Date,
        val missionName: String
)
