plugins {
    id("com.vanniktech.maven.publish")
}

afterEvaluate {
    mavenPublishing {
        publishToMavenCentral()
        signAllPublications()

        coordinates(
            groupId = project.group.toString(),
            artifactId = project.name,
            version = project.version.toString(),
        )

        pom {
            name.set("Qualifio Services Gradle Plugin")
            description.set("Gradle plug-in to configure Android applications with an qualifio-services.json file.")
            url.set("https://github.com/qualifio/qualifio-services-gradle-plugin/")

            licenses {
                license {
                    name.set("MIT")
                    url.set("https://opensource.org/license/mit")
                    distribution.set("https://opensource.org/license/mit")
                }
            }

            developers {
                developer {
                    id.set("Qualifio")
                    name.set("Qualifio")
                    url.set("https://github.com/qualifio/")
                }
            }

            scm {
                url.set("https://github.com/qualifio/qualifio-services-gradle-plugin/")
                connection.set("scm:git:git://github.com/qualifio/qualifio-services-gradle-plugin.git")
                developerConnection.set("scm:git:ssh://git@github.com/qualifio/qualifio-services-gradle-plugin.git")
            }
        }
    }
}
