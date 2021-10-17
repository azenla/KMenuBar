package org.kmenubar

fun MenuBar.configure() = item {
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
      submenu("Level 3") {
        configureLevel3()
      }
    }
  }
}

fun MenuBarMenu.configureLevel3() {
  item("Hello World") {
    action {
      println("HELLO WORLD")
    }
  }

  item("Goodbye World") {
    action {
      println("GOODBYE WORLD")
    }
  }
}
