package org.kmenubar

import kotlinx.cinterop.ObjCAction
import platform.AppKit.NSMenu
import platform.AppKit.NSMenuItem
import platform.AppKit.NSStatusBarButton
import platform.AppKit.NSStatusItem
import platform.AppKit.enabled
import platform.Foundation.NSSelectorFromString
import platform.darwin.NSObject

class MenuBarDsl(private val statusItem: NSStatusItem) {
  fun button(block: MenuBarRootButton.() -> Unit) {
    MenuBarRootButton(statusItem.button!!).apply(block)
  }

  fun menu(block: MenuBarMenu.() -> Unit) {
    var menu = statusItem.menu

    if (menu == null) {
      menu = NSMenu()
      statusItem.menu = menu
    }

    MenuBarMenu(menu).apply(block)
  }

  fun item(block: MenuBarMenuItem.() -> Unit) {
    menu { item(block) }
  }

  fun item(title: String, block: MenuBarMenuItem.() -> Unit = {}) {
    menu {
      item(title, block)
    }
  }

  fun advanced(block: NSStatusItem.() -> Unit) {
    statusItem.apply(block)
  }
}

class MenuBarRootButton(private val button: NSStatusBarButton) {
  var title: String
    get() = button.title
    set(value) {
      button.title = value
    }

  fun title(title: String) {
    button.title = title
  }

  fun advanced(block: NSStatusBarButton.() -> Unit) {
    button.apply(block)
  }
}

class MenuBarMenu(private val menu: NSMenu) {
  fun item(block: MenuBarMenuItem.() -> Unit) {
    val item = NSMenuItem()
    MenuBarMenuItem(item).apply(block)
  }

  fun item(title: String, block: MenuBarMenuItem.() -> Unit = {}) {
    val handler = MenuBarActionHandler()
    val item = NSMenuItem(
      title,
      null,
      title
    )
    item.target = handler
    item.enabled = true
    MenuBarMenuItem(item).apply(block)
    menu.addItem(item)
  }

  fun advanced(block: NSMenu.() -> Unit) {
    menu.apply(block)
  }
}

class MenuBarMenuItem(private val item: NSMenuItem) {
  var title: String
    get() = item.title
    set(value) {
      item.title = value
    }

  fun title(title: String) {
    item.title = title
  }

  fun action(block: () -> Unit) {
    val handler = item.target as MenuBarActionHandler
    handler.block = block
    item.setAction(NSSelectorFromString("call"))
  }

  fun advanced(block: NSMenuItem.() -> Unit) {
    item.apply(block)
  }
}

class MenuBarActionHandler(var block: () -> Unit = {}) : NSObject() {
  @ObjCAction
  fun call() {
    block()
  }
}
