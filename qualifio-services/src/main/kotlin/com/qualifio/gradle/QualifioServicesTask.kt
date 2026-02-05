package com.qualifio.gradle

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import java.io.File

private typealias ResValuesMap = Map<String, Any>
private typealias MutableResValuesMap = MutableMap<String, Any>

@CacheableTask
abstract class QualifioServicesTask : DefaultTask() {
    @get:OutputDirectory
    abstract val outputDirectory: DirectoryProperty

    @get:Internal
    val intermediateDir: File
        get() = outputDirectory.asFile.get()

    @get:PathSensitive(PathSensitivity.RELATIVE)
    @get:InputFiles
    abstract val qualifioServicesJsonFiles: Property<Collection<File>>

    @Throws(GradleException::class)
    @TaskAction
    fun action() {
        val searchedLocations = qualifioServicesJsonFiles.get()
        val jsonFiles = searchedLocations.filter { it.isFile }

        if (jsonFiles.isEmpty()) {
            val message = """
                File ${QualifioServicesGradlePlugin.JSON_FILE_NAME} is missing.
                The Qualifio Services Gradle Plugin cannot function without it.
                Searched locations: ${searchedLocations.joinToString { it.absolutePath }}
            """.trimIndent()

            throw GradleException(message)
        }

        val servicesFile = jsonFiles.first()
        logger.warn("Parsing json file: $servicesFile")

        // Clean up previous directories.
        intermediateDir.deleteRecursively()

        if (!intermediateDir.mkdirs()) {
            throw GradleException("Failed to create folder: $intermediateDir")
        }

        val values = File(intermediateDir, "values")
        if (!values.exists() && !values.mkdirs()) {
            throw GradleException("Failed to create folder: $values")
        }

        val resValues = parseJsonFileContents(servicesFile)
        File(values, "values.xml").writeText(getValuesContent(resValues), Charsets.UTF_8)
    }

    private fun parseJsonFileContents(file: File): ResValuesMap {
        val resValues: MutableResValuesMap = LinkedHashMap()

        val root = JsonParser.parseReader(file.bufferedReader())
        if (!root.isJsonObject) {
            throw GradleException("Malformed root json at ${file.absolutePath}")
        }

        val domain = root.asJsonObject.getAsJsonPrimitive("domain")?.asString
            ?: throw GradleException("Missing 'domain' attribute.")

        resValues["qualifio_services_domain"] = domain

        return resValues
    }

    private fun getValuesContent(values: ResValuesMap): String {
        val sb = StringBuilder(256)

        sb.append(
            """
                <?xml version="1.0" encoding="utf-8"?>
                <resources>

            """.trimIndent()
        )

        for ((name, value) in values) {
            when (value) {
                is String -> sb.append("    <string name=\"$name\" translatable=\"false\">$value</string>\n")
                is Boolean -> sb.append("    <bool name=\"$name\">$value</bool>\n")
                else -> {
                    logger.warn("Unsupported property '$name' with type '${value.javaClass.simpleName}'.")
                    continue
                }
            }
        }

        sb.append("</resources>\n")

        return sb.toString()
    }
}
