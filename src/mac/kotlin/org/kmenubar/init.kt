package org.kmenubar

import kotlinx.cinterop.staticCFunction
import platform.AppKit.NSApplication
import platform.AppKit.NSImage
import platform.AppKit.NSMenu
import platform.AppKit.NSMenuItem
import platform.AppKit.NSStatusItem
import platform.AppKit.highlightMode
import platform.CoreGraphics.CGSizeMake

fun action() {
  println("Hello World")
}

@UsedBySwift
class KotlinMenuBar {
  @UsedBySwift
  fun setup(statusItem: NSStatusItem) {
    statusItem.highlightMode = true

    val icon = NSImage.imageNamed("AppIcon")!!
    icon.size = CGSizeMake(16.0, 16.0)
    val button = statusItem.button!!
    button.image = icon
    button.title = "Hello World"
    button.target = staticCFunction(::action)

    val menu = NSMenu()
    menu.addItemWithTitle(
      "Hello World",
      staticCFunction(::action),
      ""
    )
    statusItem.menu = menu
  }
}
