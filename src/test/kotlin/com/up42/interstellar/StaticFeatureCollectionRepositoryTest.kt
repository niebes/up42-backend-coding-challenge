package com.up42.interstellar

import com.up42.interstellar.features.repository.StaticFeatureCollectionRepository
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.UUID

internal class StaticFeatureCollectionRepositoryTest {

    private val repository = StaticFeatureCollectionRepository("features/test-source-data.json")

    @Test
    fun `should load test source data`() {
        val features = repository.getFeatures()
        assertThat(features).isNotEmpty
    }

    @Test
    fun `should find feature by id when id is present`() {
        val feature = repository.getFeature(UUID.fromString("6e36156e-af05-4a70-b4cd-9e98a40c1401"))
        assertThat(feature).isNull()
    }

    @Test
    fun `should return empty feature by id when id is absent`() {
        val feature = repository.getFeature(UUID.fromString("39c2f29e-c0f8-4a39-a98b-deed547d6aea"))
        assertThat(feature).isNotNull
    }
}
