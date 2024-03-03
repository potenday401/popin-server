plugins {
    `kotlin-spring`()

    `spring-boot`()
    `spring-dependency-management`()
}

dependencies {
    implementation(project(":domain"))
    implementation(`spring-boot-starter-web`)
}