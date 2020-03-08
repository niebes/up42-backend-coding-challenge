package com.up42.interstellar.features

import com.up42.interstellar.features.model.Acquisition
import com.up42.interstellar.features.model.Feature
import com.up42.interstellar.features.model.Properties
import java.time.Instant
import java.util.Date
import java.util.UUID

class FeatureFixtures {
    companion object {
        fun feature(
                id: UUID = UUID.fromString("60f0ccf8-d366-4fb0-b0f4-b285cf12716e"),
                quicklook: String? = blackPixelPng
        ) = Feature(
                properties = Properties(
                        id = id,
                        timestamp = Date.from(Instant.ofEpochMilli(1583588672000)),
                        quicklook = quicklook,
                        acquisition = Acquisition(
                                missionName = "some-mission",
                                beginViewingDate = Date.from(Instant.ofEpochMilli(1583588671000)),
                                endViewingDate = Date.from(Instant.ofEpochMilli(1583588670000))
                        )

                )
        )

        private const val blackPixelPng = "R0lGODlhAQABAIAAAAUEBAAAACwAAAAAAQABAAACAkQBADs="
    }
}