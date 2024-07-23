plugins {
    kotlinSpring()

    springBoot()
    springDependencyManagement()

    jooqGradle()
}

dependencies {
    implementation(project(":domain"))

    implementation(Springframework.springBootStarterWeb())
    implementation(Springframework.springBootStarterThymeleaf())
    implementation(Springframework.springBootConfigurationProcessor())

    implementation(Springframework.springdocOpenapiStarterWebmvcUi())

    implementation(Springframework.springBootStarterSecurity())

    implementation(Fasterxml.jacksonModuleKotlin())
    runtimeOnly(Microutils.kotlinLoggingJvm())

    implementation(Jsonwebtoken.jjwtApi())
    runtimeOnly(Jsonwebtoken.jjwtImpl())
    runtimeOnly(Jsonwebtoken.jjwtJackson())

    implementation(Locationtech.jtsCore())
    implementation(Springframework.springBootStarterJooq())

    runtimeOnly(Postgresql.postgresql())

    implementation(Amazonaws.awsJavaSdkSes())
    implementation(Amazonaws.s3())
    implementation(Amazonaws.s3TransferManager())
    implementation(Amazonaws.awsCrt())
    implementation(Amazonaws.awsSecretsManagerJdbc())
}