plugins {
    id("java")
}

allprojects {
    apply(plugin = "java")
    dependencies {
        implementation("log4j:log4j:1.2.17")
        testImplementation(platform("org.junit:junit-bom:5.9.3"))
        testImplementation("org.junit.jupiter:junit-jupiter")
        testImplementation("org.mockito:mockito-core:5.3.1")
    }

    repositories {
        mavenCentral()
    }

    tasks.test {
        useJUnitPlatform()
    }
}