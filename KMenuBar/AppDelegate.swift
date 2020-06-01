//
//  AppDelegate.swift
//  KMenuBar
//
//  Created by Kenneth Endfinger on 5/31/20.
//  Copyright © 2020 Kenneth Endfinger. All rights reserved.
//

import Cocoa
import SwiftUI
import KotlinMenuBar

@NSApplicationMain
class AppDelegate: NSObject, NSApplicationDelegate {
  var kotlin: KotlinMenuBar!
  var statusItem: NSStatusItem!
  
  override init() {
    kotlin = KotlinMenuBar()
    statusItem = NSStatusBar.system.statusItem(withLength: NSStatusItem.squareLength)
  }

  func applicationDidFinishLaunching(_: Notification) {
    kotlin.setup(statusItem: statusItem)
  }

  func applicationWillTerminate(_: Notification) {
    // Insert code here to tear down your application
    print("Status Bar Title: \(statusItem!.button!.title)")
  }  
}
