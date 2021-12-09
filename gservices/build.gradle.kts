plugins {
    val kotlinVersion = "1.6.0"
    kotlin("jvm") version kotlinVersion apply true
    kotlin("plugin.spring") version kotlinVersion apply false
    kotlin("plugin.jpa") version kotlinVersion apply false

    id("org.jlleitschuh.gradle.ktlint") version "10.2.0" apply true
    id("org.springframework.boot") version "2.6.1" apply false
    id("io.spring.dependency-management") version "1.0.11.RELEASE" apply true
}

allprojects {

    group = "net.medrag"
    version = project.property("$name.version") ?: "undefined"

    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")

    tasks {
        withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
            sourceCompatibility = JavaVersion.VERSION_11.toString()
            targetCompatibility = JavaVersion.VERSION_11.toString()

            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = "11"
            }
        }

        withType<Test> {
            useJUnitPlatform()
        }

        withType<Wrapper> {
            gradleVersion = "7.3.1"
        }
    }

    dependencyManagement {
        dependencies {
            dependency("org.springframework.boot:spring-boot-starter:${project.property("spring.libs.version")}")
            dependency("org.springframework.boot:spring-boot-starter-web:${project.property("spring.libs.version")}")
            dependency("org.springframework.boot:spring-boot-starter-data-jpa:${project.property("spring.libs.version")}")
            dependency("org.springframework.boot:spring-boot-starter-actuator:${project.property("spring.libs.version")}")

            dependency("org.springframework.boot:spring-boot-starter-integration:${project.property("spring.libs.version")}")
            dependency("org.springframework.integration:spring-integration-zookeeper:${project.property("spring.integration.version")}")

            dependency("org.springframework.cloud:spring-cloud-starter-zookeeper-config:${project.property("spring.zookeeper.config.version")}")

            dependency("io.github.microutils:kotlin-logging-jvm:${project.property("kotlin.logging.version")}")
            dependency("ch.qos.logback:logback-classic:${project.property("logback.version")}")

            dependency("org.apache.curator:curator-framework:${project.property("curator.version")}")
            dependency("org.apache.curator:curator-x-async:${project.property("curator.version")}")
            dependency("org.apache.curator:curator-recipes:${project.property("curator.version")}")

//            required to correct handling of kotlin-specific edge cases (like data class properties setting)
            dependency("org.jetbrains.kotlin:kotlin-reflect:${project.property("kotlin.version")}")
        }
    }

    repositories {
        maven(url = "https://repo.maven.apache.org/maven2")
//        maven {
//            url = uri("https://repo.maven.apache.org/maven2/releases/")
//            credentials {
//                // Данные параметры необходимо указать в gradle.properties
//                username = project.property("corpRepoUser").toString()
//                password = project.property("corpRepoPassword").toString()
//            }
//        }
//        maven {
//            url = uri("https://repo.maven.apache.org/maven2/snapshots/")
//            credentials {
//                // Данные параметры необходимо указать в gradle.properties
//                username = project.property("corpRepoUser").toString()
//                password = project.property("corpRepoPassword").toString()
//            }
//        }
        mavenCentral()
        mavenLocal() // development only
    }

    ktlint {
        version.set("0.42.0")
        ignoreFailures.set((project.findProperty("ignoreKtlintErrors") as String?)?.toBoolean() ?: true)
        reporters {
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN)
        }
    }
}
