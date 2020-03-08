package com.up42.interstellar.features.repository

import com.up42.interstellar.features.model.Feature
import java.util.UUID

interface FeatureCollectionRepository {

    fun getFeatures(): List<Feature>
    fun getFeature(id: UUID): Feature?
}
