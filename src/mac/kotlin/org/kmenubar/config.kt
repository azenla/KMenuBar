package org.kmenubar

fun MenuBarDsl.configure() {
  button {
    title = "KotlinMenuBar"
  }

  item("Hello World") {
    action {
      println("Hello World")
    }
  }
}
