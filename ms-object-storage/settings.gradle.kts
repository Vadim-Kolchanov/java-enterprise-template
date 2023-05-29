rootProject.name = "ms-object-storage"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../libs.versions.toml"))
        }
    }
}

include("ms-object-storage-api", "ms-object-storage-core")
