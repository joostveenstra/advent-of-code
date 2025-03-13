plugins {
    `kotlin-conventions`
    application
}

dependencies {
    implementation(libs.cliKt)
}

application {
    mainClass.set("MainKt")
}