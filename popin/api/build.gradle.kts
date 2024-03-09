plugins {
    `kotlin-spring`()

    `spring-boot`()
    `spring-dependency-management`()

    `jooq-gradle-plugin`()
}

dependencies {
    implementation(project(":domain"))

    implementation(`spring-boot-starter-web`)
    implementation(`spring-boot-configuration-processor`)

    implementation(`spring-boot-starter-security`)

    implementation(`jackson-module-kotlin`)

    implementation(`jjwt-api`())
    runtimeOnly(`jjwt-impl`())
    runtimeOnly(`jjwt-jackson`())

    implementation(`spring-boot-starter-jooq`)

    runtimeOnly(`postgres-connector`())
}