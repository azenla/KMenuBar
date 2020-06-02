package org.kmenubar

import kotlinx.cinterop.ObjCAction
import platform.AppKit.NSMenu
import platform.AppKit.NSMenuItem
import platform.AppKit.NSStatusBar
import platform.AppKit.NSStatusBarButton
import platform.AppKit.NSStatusItem
import platform.AppKit.NSVariableStatusItemLength
import platform.AppKit.title
import platform.Foundation.NSSelectorFromString
import platform.darwin.NSObject

class MenuBar(private val statusBar: NSStatusBar) {
  fun item(block: MenuBarStatusItem.() -> Unit) {
    val item = statusBar.statusItemWithLength(NSVariableStatusItemLength)
    MenuBarStatusItem(item).apply(block)
  }
}

class MenuBarStatusItem(private val statusItem: NSStatusItem) {
  var title: String?
    get() = statusItem.title
    set(value) {
      statusItem.title = value
    }

  fun title(title: String?) {
    statusItem.title = title
  }

  fun button(block: MenuBarRootButton.() -> Unit = {}): MenuBarRootButton {
    return MenuBarRootButton(statusItem.button!!).apply(block)
  }

  fun menu(block: MenuBarMenu.() -> Unit = {}): MenuBarMenu {
    var menu = statusItem.menu

    if (menu == null) {
      menu = NSMenu()
      statusItem.menu = menu
    }

    return MenuBarMenu(menu).apply(block)
  }

  fun item(block: MenuBarMenuItem.() -> Unit): MenuBarMenuItem {
    return menu().item(block)
  }

  fun item(title: String, block: MenuBarMenuItem.() -> Unit = {}): MenuBarMenuItem {
    return menu().item(title, block)
  }

  fun submenu(block: MenuBarMenuItem.() -> Unit): MenuBarMenuItem {
    return menu().item(block)
  }

  fun submenu(title: String, block: MenuBarMenu.() -> Unit): MenuBarMenuItem {
    return menu().submenu(title, block)
  }

  fun remove(item: MenuBarMenuItem) {
    menu().remove(item)
  }

  fun advanced(block: NSStatusItem.() -> Unit = {}): NSStatusItem {
    return statusItem.apply(block)
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

  fun advanced(block: NSStatusBarButton.() -> Unit = {}): NSStatusBarButton {
    return button.apply(block)
  }
}

class MenuBarMenu(private val menu: NSMenu) {
  fun item(block: MenuBarMenuItem.() -> Unit): MenuBarMenuItem {
    val item = NSMenuItem()
    val wrapper = MenuBarMenuItem(item).apply(block)
    menu.addItem(item)
    return wrapper
  }

  fun item(title: String, block: MenuBarMenuItem.() -> Unit = {}): MenuBarMenuItem {
    val handler = MenuBarActionHandler()
    val item = NSMenuItem(
      title,
      null,
      title
    )
    item.target = handler
    item.enabled = true
    val wrapper = MenuBarMenuItem(item).apply(block)
    menu.addItem(item)
    return wrapper
  }

  fun submenu(title: String, block: MenuBarMenu.() -> Unit = {}): MenuBarMenuItem {
    return item(title) {
      submenu(block)
    }
  }

  fun remove(item: MenuBarMenuItem) {
    menu.removeItem(item.advanced())
  }

  fun removeAllItems() {
    menu.removeAllItems()
  }

  fun advanced(block: NSMenu.() -> Unit = {}): NSMenu {
    return menu.apply(block)
  }
}

class MenuBarMenuItem(private val item: NSMenuItem) {
  var title: String
    get() = item.title
    set(value) {
      item.title = value
    }

  var shortcut: String
    get() = item.keyEquivalent
    set(value) {
      item.keyEquivalent = value
    }

  fun title(title: String) {
    item.title = title
  }

  fun shortcut(shortcut: String) {
    item.keyEquivalent = shortcut
  }

  fun action(block: () -> Unit): MenuBarActionHandler {
    val handler = item.target as MenuBarActionHandler
    handler.block = block
    item.setAction(NSSelectorFromString("call"))
    return handler
  }

  fun submenu(block: MenuBarMenu.() -> Unit): MenuBarMenu {
    if (item.submenu == null) {
      item.submenu = NSMenu()
    }

    return MenuBarMenu(item.submenu!!).apply(block)
  }

  fun advanced(block: NSMenuItem.() -> Unit = {}): NSMenuItem {
    return item.apply(block)
  }
}

class MenuBarActionHandler(var block: () -> Unit = {}) : NSObject() {
  @ObjCAction
  fun call() {
    block()
  }
}
