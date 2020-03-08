package com.up42.interstellar.features.controller

import com.up42.interstellar.features.repository.FeatureCollectionRepository
import java.util.UUID
import org.springframework.http.ResponseEntity
import org.springframework.util.Base64Utils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(FeaturesController.featureMapping)
class FeaturesController(
        private val repository: FeatureCollectionRepository,
        private val featureConverter: FeatureModelToFeatureDtoConverter
) {

    companion object {
        const val featureMapping = "/features"
    }

    /**
     * FIXME https://opensource.zalando.com/restful-api-guidelines/#110
     * this satisfies the requirements, but is not compliant towards best practice. return an object with "items"
     * instead
     */
    @GetMapping
    fun getFeatures(): List<FeatureDto> =
            repository.getFeatures()
                    .map(featureConverter::convert)

    @GetMapping("/{id}")
    fun getFeatures(
            @PathVariable("id") id: UUID
    ): ResponseEntity<FeatureDto> =
            repository.getFeature(id)
                    ?.let(featureConverter::convert)?.let {
                        ResponseEntity.ok(it)
                    } ?: ResponseEntity.notFound().build()

    @GetMapping("/{id}/quicklook", produces = ["image/png"])
    fun getFeatureQuicklook(
            @PathVariable("id") id: UUID
    ): ResponseEntity<ByteArray> =
            repository.getFeature(id)?.properties?.quicklook?.let {
                Base64Utils.decode(it.toByteArray())
            }?.let {
                ResponseEntity.ok(it)
            } ?: ResponseEntity.notFound().build()
}
