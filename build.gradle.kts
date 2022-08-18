
plugins {
    kotlin("jvm") version "1.7.10"
    kotlin("plugin.spring") version "1.7.10"
    kotlin("kapt") version "1.7.10"
    `maven-publish`

}

group = "org.malv.graphql-utils"
version = "0.1"


java.targetCompatibility = JavaVersion.VERSION_11
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("https://repo.spring.io/release") }

}

kotlin {
    explicitApi()
}


tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}


dependencies {
    //Spring
    implementation("org.springframework.boot:spring-boot-starter-web:2.7.2")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.7.2")
    implementation("org.testng:testng:7.6.1")


    //Kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.7.10")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.7.10")


    //Graphql
    val graphqlVersion = "12.0.0"
    implementation("com.graphql-java-kickstart", "graphql-spring-boot-starter", graphqlVersion)


    //QueryDSL
    val queryDslVersion = "5.0.0"
    implementation ("com.querydsl", "querydsl-apt", queryDslVersion)
    implementation("com.querydsl", "querydsl-jpa", queryDslVersion)
    implementation ("com.querydsl:querydsl-apt:$queryDslVersion:jpa")
    kapt("com.querydsl:querydsl-apt:5.0.0:jpa")



    //Validation
    implementation("javax.validation", "validation-api", "2.0.1.Final")


    implementation("com.github.MiguelAngelLV:khronos:15b000c09c")




}


publishing {
    publications {
        create("maven_public", MavenPublication::class) {
            groupId = "org.gradle.sample"
            artifactId = "library"
            version = "1.1"
            from(components.getByName("java"))
        }
    }
}
