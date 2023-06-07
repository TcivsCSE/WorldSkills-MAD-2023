//
//  BasicBuildApp.swift
//  BasicBuild
//
//  Created by NightFeather on 2023/6/5.
//

import SwiftUI

@main
struct BasicBuildApp: App {
    let persistenceController = PersistenceController.shared

    var body: some Scene {
        WindowGroup {
            ContentView()
                .environment(\.managedObjectContext, persistenceController.container.viewContext)
        }
    }
}
