dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // Curator dependencies
    implementation("org.apache.curator:curator-framework")
    implementation("org.apache.curator:curator-x-async")
    implementation("org.apache.curator:curator-recipes")

    implementation("io.github.microutils:kotlin-logging-jvm")
    implementation("ch.qos.logback:logback-classic")

    // Spring Integration dependencies
    implementation("org.springframework.boot:spring-boot-starter-integration")
    implementation("org.springframework.integration:spring-integration-zookeeper")

    // Zookeeper Config
    implementation("org.springframework.cloud:spring-cloud-starter-zookeeper-config")
}

tasks.register("prepareKotlinBuildScriptModel") {}
