package com.up42.interstellar.features.controller

import java.util.Date
import java.util.UUID

data class FeatureDto(
        val id: UUID,
        val timestamp: Date,
        val beginViewingDate: Date,
        val endViewingDate: Date,
        val missionName: String
)
