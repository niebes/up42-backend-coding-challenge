import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

object Versions {
	const val JACKSON = "2.11.1"
	const val MOCKITO_KOTLIN = "2.2.0"
}

plugins {
	id("org.springframework.boot") version "2.3.1.RELEASE"
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
	kotlin("jvm") version "1.3.70"
	kotlin("plugin.spring") version "1.3.70"
}

group = "com.up42"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation(kotlin("stdlib"))
	implementation(kotlin("stdlib-jdk8"))
	implementation("org.springframework.boot:spring-boot-starter")
	implementation(group = "org.springframework.boot", name = "spring-boot-starter-web")
	implementation(group = "org.springframework.boot", name = "spring-boot-starter-actuator")
	implementation(group = "com.fasterxml.jackson.module", name = "jackson-module-kotlin", version = Versions.JACKSON)
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
	implementation(group = "com.fasterxml.jackson.core", name = "jackson-databind", version = Versions.JACKSON)
	implementation(group = "com.fasterxml.jackson.module", name = "jackson-module-kotlin", version = Versions.JACKSON)

	testImplementation(group = "com.nhaarman.mockitokotlin2", name = "mockito-kotlin", version = Versions.MOCKITO_KOTLIN)
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}
