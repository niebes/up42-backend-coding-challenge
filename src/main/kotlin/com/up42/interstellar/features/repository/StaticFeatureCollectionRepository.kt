package com.up42.interstellar.features.repository

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.up42.interstellar.features.model.Feature
import com.up42.interstellar.features.model.FeatureCollection
import java.util.UUID
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component

@Component
class StaticFeatureCollectionRepository(
        private val path: String = "features/source-data.json"
) : FeatureCollectionRepository {

    private val featureCollection by lazy {
        readFromFile(path, object : TypeReference<List<FeatureCollection>>() {})
    }

    override fun getFeatures(): List<Feature> = featureCollection.flatMap(FeatureCollection::features)

    override fun getFeature(id: UUID): Feature? = featureCollection.flatMap(FeatureCollection::features)
            .firstOrNull { it.properties.id == id }

    companion object {

        private val objectMapper = ObjectMapper().registerKotlinModule()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

        private fun <T> readFromFile(path: String, clazzType: TypeReference<T>): T =
                ClassPathResource(path).apply {
                    check(exists()) { "file $path doesn't exist" }
                }.inputStream.let { inputStream ->
                    objectMapper.readValue(inputStream, clazzType)
                }
    }
}
