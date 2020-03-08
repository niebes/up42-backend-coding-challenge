package com.up42.interstellar.features.controller

import com.up42.interstellar.features.model.Acquisition
import com.up42.interstellar.features.model.Feature
import com.up42.interstellar.features.model.Properties
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import java.time.Instant
import java.util.Date
import java.util.UUID

internal class FeatureModelToFeatureDtoConverterTest {

    private val converter = FeatureModelToFeatureDtoConverter()
    @Test
    fun convert() {
        val featureId = UUID.fromString("5641c081-523b-4e7a-910f-3a50fddd7346")

        val timstamp = Date.from(Instant.ofEpochMilli(1583588672000))
        val endViewingDate = Date.from(Instant.ofEpochMilli(1583588670000))
        val beginViewingDate = Date.from(Instant.ofEpochMilli(1583588671000))

        val featureModel = Feature(
                properties = Properties(
                        id = featureId,
                        timestamp = timstamp,
                        quicklook = null,
                        acquisition = Acquisition(
                                missionName = "some-mission",
                                beginViewingDate = beginViewingDate,
                                endViewingDate = endViewingDate
                        )

                )
        )
        val expectedDto = FeatureDto(
                id = featureId,
                missionName = "some-mission",
                timestamp = timstamp,
                beginViewingDate = beginViewingDate,
                endViewingDate = endViewingDate
        )

        val actualDto = converter.convert(featureModel)
        assertThat(actualDto).isEqualTo(expectedDto)
    }
}