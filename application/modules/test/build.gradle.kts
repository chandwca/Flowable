dependencies {
    api(project(":modules-common"))
    api("org.springframework.boot:spring-boot-starter-test")
    api("org.springframework.boot:spring-boot-testcontainers")
    api("org.jetbrains.kotlin:kotlin-test-junit5")
    api("org.springframework.restdocs:spring-restdocs-mockmvc")
    api("org.testcontainers:junit-jupiter")
    api("org.testcontainers:postgresql")
    api("org.testcontainers:pulsar")
    runtimeOnly("org.junit.platform:junit-platform-launcher")
}