//
//  ContentView.swift
//  BasicBuild
//
//  Created by NightFeather on 2023/6/5.
//

import SwiftUI

struct ContentView: View {
	let persistenceController = PersistenceController.shared
	
	init() {
		if UserDefaults.standard.dictionary(forKey: "userAccounts") == nil {
			UserDefaults.standard.set(Dictionary<String, Any?>(), forKey: "userAccounts")
		}
	}
	
    var body: some View {
        LoginView()
    }
}
