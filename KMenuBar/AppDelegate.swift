//
//  AppDelegate.swift
//  KMenuBar
//
//  Created by Kenneth Endfinger on 6/1/20.
//  Copyright © 2020 Kenneth Endfinger. All rights reserved.
//

import Cocoa
import KotlinMenuBar

@NSApplicationMain
class AppDelegate: NSObject, NSApplicationDelegate {
  let kotlin: KotlinMenuBar

  override init() {
    kotlin = KotlinMenuBar()
  }

  func applicationDidFinishLaunching(_: Notification) {
    kotlin.launch()
  }

  func applicationWillTerminate(_: Notification) {
    kotlin.terminate()
  }
}
