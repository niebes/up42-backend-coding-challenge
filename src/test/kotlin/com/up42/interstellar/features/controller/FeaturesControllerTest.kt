package com.up42.interstellar.features.controller

import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import com.up42.interstellar.features.FeatureFixtures.Companion.feature
import com.up42.interstellar.features.repository.FeatureCollectionRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.UUID

@WebMvcTest(FeaturesController::class)
internal class FeaturesControllerTest(
        @Autowired
        private val controller: MockMvc
) {

    @MockBean
    private lateinit var featureRepo: FeatureCollectionRepository

    @Test
    fun `should return success with empty array when repo had no hits`() {
        given(featureRepo.getFeatures()).willReturn(emptyList())

        controller.perform(
                        get(FeaturesController.featureMapping)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk)
                .andExpect(jsonPath("$").isArray)
                .andExpect(jsonPath("$.length()").value(0))

        verify(featureRepo).getFeatures()
    }

    @Test
    fun `should return success with transformed element`() {
        given(featureRepo.getFeatures()).willReturn(listOf(feature()))

        controller.perform(
                        get(FeaturesController.featureMapping)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk)
                .andExpect(jsonPath("$").isArray)
                .andExpect(jsonPath("$.length()").value(1))

        verify(featureRepo).getFeatures()
    }

    @Test
    fun `should return success when feature by id is present`() {
        val featureId = UUID.fromString("5641c081-523b-4e7a-910f-3a50fddd7346")
        given(featureRepo.getFeature(featureId)).willReturn(feature(id = featureId))
        controller.perform(
                get("${FeaturesController.featureMapping}/$featureId")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)
                .andExpect(jsonPath("$.id").value(featureId.toString()))
                .andExpect(jsonPath("$.missionName").value("some-mission"))
                .andExpect(jsonPath("$.timestamp").value("1583588672000"))
                .andExpect(jsonPath("$.beginViewingDate").value("1583588671000"))
                .andExpect(jsonPath("$.endViewingDate").value("1583588670000"))

    }

    @Test
    fun `should return not found when feature by id is absent`() {
        val featureId = UUID.fromString("5641c081-523b-4e7a-910f-3a50fddd7346")
        controller.perform(
                get("${FeaturesController.featureMapping}/$featureId")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound)
    }

    @Test
    fun `should return png for present quicklook`() {
        val featureId = UUID.fromString("5641c081-523b-4e7a-910f-3a50fddd7346")
        given(featureRepo.getFeature(featureId)).willReturn(feature())
        controller.perform(
                        get("/features/$featureId/quicklook")
                )
                .andExpect(status().isOk)
                .andExpect(content().contentType("image/png"))

        verify(featureRepo).getFeature(featureId)
    }

    @Test
    fun `should return 404 for absent quicklook`() {
        val featureId = UUID.fromString("5641c081-523b-4e7a-910f-3a50fddd7346")
        given(featureRepo.getFeature(featureId)).willReturn(feature(quicklook = null))
        controller.perform(
                        get("/features/$featureId/quicklook")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound)

        verify(featureRepo).getFeature(featureId)
    }

    @Test
    fun `should return 404 for absent feature`() {
        controller.perform(
                        get("/features/5641c081-523b-4e7a-910f-3a50fddd7346/quicklook")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound)

        verify(featureRepo).getFeature(UUID.fromString("5641c081-523b-4e7a-910f-3a50fddd7346"))
    }
}