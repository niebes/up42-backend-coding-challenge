package com.up42.interstellar.features.controller

import com.up42.interstellar.features.model.Feature
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class FeatureModelToFeatureDtoConverter : Converter<Feature, FeatureDto> {
    override fun convert(source: Feature) = FeatureDto(
            id = source.properties.id,
            timestamp = source.properties.timestamp,
            beginViewingDate = source.properties.acquisition.beginViewingDate,
            endViewingDate = source.properties.acquisition.endViewingDate,
            missionName = source.properties.acquisition.missionName
    )
}
