rootProject.name = "ms-notification"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../libs.versions.toml"))
        }
    }
}

include("ms-notification-api", "ms-notification-core")
