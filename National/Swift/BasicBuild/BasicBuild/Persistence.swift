//
//  Persistence.swift
//  BasicBuild
//
//  Created by NightFeather on 2023/6/5.
//

import CoreData

struct PersistenceController {
    static let shared = PersistenceController()

    let container: NSPersistentContainer

    init() {
        container = NSPersistentContainer(name: "BasicBuild")
        container.loadPersistentStores { storeDescription, error in
			if let error = error as NSError? {
			   fatalError("Unresolved error \(error), \(error.userInfo)")
		   }
		}
        container.viewContext.automaticallyMergesChangesFromParent = true
    }
}
