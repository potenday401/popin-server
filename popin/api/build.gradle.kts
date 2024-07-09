plugins {
    kotlinSpring()

    springBoot()
    springDependencyManagement()

    jooqGradle()
}

dependencies {
    implementation(project(":domain"))

    implementation(SpringBoot.webStarter())
    implementation(SpringBoot.thymeleafStarter())
    implementation(SpringBoot.configurationProcessor())

    implementation(SpringBoot.springdocStarter())

    implementation(SpringBoot.securityStarter())

    implementation(Jackson.kotlinModule())
    runtimeOnly(Logger.kotlinLogging())

    implementation(JJWT.api())
    runtimeOnly(JJWT.impl())
    runtimeOnly(JJWT.jackson())

    implementation(Java.topologySuite())
    implementation(SpringBoot.jooqStarter())

    runtimeOnly(Postgres.connector())

    implementation(AmazonWebService.simpleEmailServiceSdk())
    implementation(AmazonWebService.s3Sdk())
    implementation(AmazonWebService.s3TransferManagerSdk())
    implementation(AmazonWebService.crtSdk())
    implementation(AmazonWebService.secretManagerJdbc())
}