plugins {
    `kotlin-spring`()

    `spring-boot`()
    `spring-dependency-management`()

    `jooq-gradle-plugin`()
}

dependencies {
    implementation(project(":domain"))

    implementation(`spring-boot-starter-web`)
    implementation(`spring-boot-starter-thymeleaf`)
    implementation(`spring-boot-configuration-processor`)

    implementation(`spring-boot-starter-springdoc-webmvc-ui`())

    implementation(`spring-boot-starter-security`)

    implementation(`jackson-module-kotlin`)
    runtimeOnly(`kotlin-logging`())

    implementation(`jjwt-api`())
    runtimeOnly(`jjwt-impl`())
    runtimeOnly(`jjwt-jackson`())

    implementation(`locationtech-jts-core`())
    implementation(`spring-boot-starter-jooq`)

    runtimeOnly(`postgres-connector`())

    implementation(`aws-sdk-ses`())
    implementation(`aws-sdk-s3`())
    implementation(`aws-sdk-s3-transfer-manager`())
    implementation(`aws-crt`())
    implementation(`aws-secret-manager-jdbc`())
}