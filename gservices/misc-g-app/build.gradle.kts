dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("io.github.microutils:kotlin-logging-jvm")
    implementation("ch.qos.logback:logback-classic")
    implementation("io.micrometer:micrometer-registry-prometheus")

    // Required here to use TimedAspects of micrometer
    implementation("org.springframework.boot:spring-boot-starter-aop")
}

tasks.register("prepareKotlinBuildScriptModel") {}
