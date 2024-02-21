import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	`kotlin-jvm`()
	`kotlin-kapt`()
}

allprojects {
	repositories {
		mavenCentral()
	}
}

subprojects {
	if (project.buildFile.exists()) {
		apply {
			plugin("org.jetbrains.kotlin.jvm")
		}

		group = "kr.co"
		version = "0.0.1-SNAPSHOT"

		java.sourceCompatibility = JavaVersion.VERSION_21

		dependencies {
			implementation(`kotlin-reflect`)
			implementation(`kotlin-stdlib`)
		}

		tasks {
			withType<KotlinCompile> {
				kotlinOptions {
					freeCompilerArgs += "-Xjsr305=strict"
					jvmTarget = "21"
				}
			}

			withType<Test> {
				useJUnitPlatform()
			}
		}
	}
}
