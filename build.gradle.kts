import org.apache.tools.ant.taskdefs.condition.Os
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

			if (Os.isArch("x86_64")) {
				if (Os.isFamily(Os.FAMILY_MAC)) {
					runtimeOnly(`netty-dns-macos`(classifier = "osx-x86_64"))
				}
			} else if (Os.isArch("aarch64")) {
				if (Os.isFamily(Os.FAMILY_MAC)) {
					runtimeOnly(`netty-dns-macos`(classifier = "osx-aarch_64"))
				}
			}
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
