package com.qualifio.gradle

import kotlin.test.Test
import kotlin.test.assertContains

class QualifioServicesGradlePluginTest {

    @Test
    fun `search locations - no flavor`() {
        val locations = QualifioServicesGradlePlugin.getJsonLocations("release", emptyList())
        assertContains(locations, "src/release/qualifio-services.json")
    }

    @Test
    fun `search locations - single flavor`() {
        val locations = QualifioServicesGradlePlugin.getJsonLocations("release", listOf("foo"))
        val expected = listOf(
            "src/fooRelease/qualifio-services.json",
            "src/foo/release/qualifio-services.json",
            "src/foo/qualifio-services.json",
            "src/release/foo/qualifio-services.json",
            "src/release/qualifio-services.json",
            "qualifio-services.json",
        )

        for (value in expected) {
            assertContains(locations, value)
        }
    }

    @Test
    fun `search locations - multiple flavors`() {
        val locations = QualifioServicesGradlePlugin.getJsonLocations("release", listOf("foo", "bar"))
        val expected = listOf(
            "src/foo/bar/release/qualifio-services.json",
            "src/fooBar/release/qualifio-services.json",
            "src/release/fooBar/qualifio-services.json",
            "src/foo/release/qualifio-services.json",
            "src/foo/bar/qualifio-services.json",
            "src/foo/barRelease/qualifio-services.json",
            "src/release/qualifio-services.json",
            "src/fooBar/qualifio-services.json",
            "src/fooBarRelease/qualifio-services.json",
            "src/foo/qualifio-services.json",
            "src/fooRelease/qualifio-services.json",
            "qualifio-services.json"
        )

        for (value in expected) {
            assertContains(locations, value)
        }
    }
}
