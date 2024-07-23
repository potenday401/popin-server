import org.apache.tools.ant.taskdefs.condition.Os
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.utils.addToStdlib.ifTrue

plugins {
	kotlinJvm()
	kotlinKapt()
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
			implementation(Jetbrains.kotlinReflect())
			implementation(Jetbrains.kotlinStdlib())

			Os.isFamily(Os.FAMILY_MAC).ifTrue {
				when {
					Os.isArch("x86_64") -> "osx-x86_64"
					Os.isArch("aarch64") -> "osx-aarch_64"
					else -> null
				}?.let { classifier ->
					runtimeOnly(Netty.nettyResolverDnsNativeMacos(classifier = classifier))
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
