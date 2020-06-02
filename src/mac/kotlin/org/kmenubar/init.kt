package org.kmenubar

import platform.AppKit.NSStatusBar

@UsedBySwift
class KotlinMenuBar {
  @UsedBySwift
  fun launch() {
    MenuBar(NSStatusBar.systemStatusBar).configure()
  }

  @UsedBySwift
  fun terminate() {}
}
