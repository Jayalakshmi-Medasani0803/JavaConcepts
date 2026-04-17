plugins {
    id("java")
}

group = "com.java.concept"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:3.4.1")
}

tasks.test {
    useJUnitPlatform()
}