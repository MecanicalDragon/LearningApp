//import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
//
//plugins {
////    java
//    kotlin("jvm") version "1.3.71"
//}
//
//group = "net.medrag"
//version = "1.0"
//
//java {
//    sourceCompatibility = JavaVersion.VERSION_1_8
//    targetCompatibility = JavaVersion.VERSION_1_8
//}
//
//sourceSets {
//    main {
//        java.srcDir("src/main/kotlin")
//        resources {
//            srcDir("src/main/resources")
//        }
//    }
//}
//
//repositories {
//    mavenCentral()
//}
//
//dependencies {
//    implementation("ch.qos.logback:logback-classic:1.2.3")
//    implementation(kotlin("stdlib-jdk8"))   //  kotlin-compile only
////    implementation(files("lib/ojdbc7.jar"))
////    testImplementation("junit:junit:4.12")
//}
//
//tasks {
//    jar {
//        manifest {
////            attributes(Pair("Main-Class", "net.medrag.GradleApp"))
//            attributes(Pair("Main-Class", "net.medrag.GradleAppKt"))
//        }
//        from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
//    }
//}
//
////  kotlin-compile only
//val compileKotlin: KotlinCompile by tasks
//compileKotlin.kotlinOptions {
//    jvmTarget = "1.8"
//}
//val compileTestKotlin: KotlinCompile by tasks
//compileTestKotlin.kotlinOptions {
//    jvmTarget = "1.8"
//}