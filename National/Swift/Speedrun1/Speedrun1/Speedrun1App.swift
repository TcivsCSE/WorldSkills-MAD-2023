//
//  Speedrun1App.swift
//  Speedrun1
//
//  Created by NightFeather on 2023/6/7.
//

import SwiftUI

@main
struct Speedrun1App: App {
    let persistenceController = PersistenceController.shared

    var body: some Scene {
        WindowGroup {
            ContentView()
                .environment(\.managedObjectContext, persistenceController.container.viewContext)
        }
    }
}
