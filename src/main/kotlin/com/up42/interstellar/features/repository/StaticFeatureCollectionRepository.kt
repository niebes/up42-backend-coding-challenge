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

    private val features by lazy {
        readFromFile(path, object : TypeReference<List<FeatureCollection>>() {})
                .flatMap { it.features }
                .associateBy { it.properties.id }
    }

    override fun getFeatures(): List<Feature> = features.values.toList()

    override fun getFeature(id: UUID): Feature? = features[id]

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
