
plugins {
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.spring") version "1.6.10"
    kotlin("kapt") version "1.6.10"
    `maven-publish`

}

group = "org.malv.graphql-utils"
version = "0.1"


java.targetCompatibility = JavaVersion.VERSION_11
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }

}

kotlin {
    explicitApi()
}


dependencies {
    //Spring
    implementation("org.springframework.boot:spring-boot-starter-web:2.6.1")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.6.1")
    implementation("org.testng:testng:7.4.0")


    //Kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.6.0")


    //Graphql
    val graphqlVersion = "11.1.0"
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
