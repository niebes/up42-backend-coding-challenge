package com.up42.interstellar.features.model

import java.util.Date
import java.util.UUID

data class Properties(
        val id: UUID,
        val timestamp: Date,
        val quicklook: String?,
        val acquisition: Acquisition
)
