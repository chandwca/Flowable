package com.caritrainc.backend.testcontainers

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.testcontainers.containers.BindMode
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName
import org.testcontainers.utility.MountableFile
import java.io.File

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TestContainerInitializer {

    fun seed() {
        val result = appDBTestContainer.execInContainer(
            "psql",
            "-U",
            POSTGRESQL_USERNAME,
            "-d",
            POSTGRESQL_DATABASE,
            "-f",
            "/docker-entrypoint-initdb.d/dump/caritra.sql"
        )
        println("DB Reset error: ${result.stderr}")
        println("DB Reset output: ${result.stdout}")
        info()
    }

    fun reset() {
        if (!initialized) {
            appDBTestContainer.start()
            initialized = true
        }
        seed()
    }

    fun stop() {
        appDBTestContainer.stop()
        initialized = false
    }

    fun info() {
        println("------- TEST CONTAINERS INFO -------")
        println("App JDBC Url: ${appDBTestContainer.jdbcUrl} | User: ${appDBTestContainer.getUsername()}, Password: ${appDBTestContainer.getPassword()}")
        println("------- TEST CONTAINERS INFO -------")
    }

    companion object {
        const val POSTGRESQL_DOCKER_IMAGE = "postgres:latest"
        const val POSTGRESQL_USERNAME = "postgres"
        const val POSTGRESQL_PASSWORD = "Caritra"
        const val POSTGRESQL_DATABASE = "Caritra"
        const val PORT = 5431
        var initialized: Boolean = false

        fun findSeedPath(path: String): String {
            if (File("$path/seed").exists()) {
                return "$path/seed"
            }
            return findSeedPath(File(path).parentFile.absolutePath)
        }

        val appDBTestContainer: PostgreSQLContainer<*> =
            PostgreSQLContainer(DockerImageName.parse(POSTGRESQL_DOCKER_IMAGE)).apply {
                withDatabaseName(POSTGRESQL_DATABASE)
                withUsername(POSTGRESQL_USERNAME)
                withPassword(POSTGRESQL_PASSWORD)
                withExposedPorts(5432)
                portBindings = listOf("$PORT:5432")

                withFileSystemBind(
                    MountableFile.forHostPath(
                        "${findSeedPath(MountableFile.forHostPath("").filesystemPath)}/dump/sql", 755
                    ).filesystemPath, "/docker-entrypoint-initdb.d/dump/", BindMode.READ_ONLY
                )
            }

    }
}