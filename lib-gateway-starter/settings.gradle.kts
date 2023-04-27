rootProject.name = "lib-gateway-starter"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../libs.versions.toml"))
        }
    }
}
