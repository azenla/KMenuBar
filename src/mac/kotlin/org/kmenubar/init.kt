package org.kmenubar

import platform.AppKit.NSStatusBar
import platform.AppKit.NSVariableStatusItemLength
import platform.AppKit.enabled

@UsedBySwift
class KotlinMenuBar {
  @UsedBySwift
  fun launch() {
    val statusItem = NSStatusBar.systemStatusBar.statusItemWithLength(
      NSVariableStatusItemLength
    )
    statusItem.enabled = true
    MenuBarDsl(statusItem).configure()
  }

  @UsedBySwift
  fun terminate() {}
}
