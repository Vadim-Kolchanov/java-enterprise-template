rootProject.name = "ms-gateway"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../libs.versions.toml"))
        }
    }
}

include("ms-gateway-core")
