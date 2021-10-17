plugins {
  kotlin("multiplatform") version "1.5.31"

  id("com.diffplug.spotless") version "5.17.0"
}

repositories {
  mavenCentral()
}

val kotlinCoroutinesVersion = "1.5.2"

fun kotlinx(name: String, version: String): String = "org.jetbrains.kotlinx:kotlinx-$name:$version"

kotlin {
  sourceSets {
    commonMain {
      dependencies {
        api(kotlin("stdlib-common"))
      }
    }
  }

  macosX64 {
    binaries {
      framework {
        baseName = "KotlinMenuBar"
      }
    }

    compilations["main"].defaultSourceSet {
      kotlin.srcDir("src/mac/kotlin")
    }

    compilations["main"].apply {
      dependencies {
        api(kotlinx("coroutines-core", kotlinCoroutinesVersion))
      }
    }
  }
}

spotless {
  format("misc") {
    target("**/*.md", "**/.gitignore")

    trimTrailingWhitespace()
    endWithNewline()
  }

  kotlin {
    target(
      fileTree("src") {
        include("**/*.kt")
      }
    )

    ktlint().userData(
      mapOf(
        "indent_size" to "2",
        "continuation_indent_size" to "2"
      )
    )
    endWithNewline()
  }

  kotlinGradle {
    ktlint().userData(
      mapOf(
        "indent_size" to "2",
        "continuation_indent_size" to "2"
      )
    )
    endWithNewline()
  }
}

tasks.withType<Wrapper> {
  gradleVersion = "7.2"
  distributionType = Wrapper.DistributionType.ALL
}
