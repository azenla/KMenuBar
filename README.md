# KMenuBar

Kotlin macOS Menu Bar Application

## Example Code

This is an example of a Menu Bar application created with the Kotlin DSL.
This code is available at src/mac/kotlin/org/kmenubar/config.kt in this repository.

```kotlin
fun MenuBar.configure() {
  item {
    title = "KotlinMenuBar"

    item("Hello World") {
      action {
        println("Hello World")
      }
    }

    submenu("Letters") {
      item("A")
      item("B")
      item("C")
      item("D")
    }

    submenu("Level 1") {
      submenu("Level 2") {
        submenu("Level 3")
      }
    }
  }
}
```