//
//  IconTextField.swift
//  BasicBuild
//
//  Created by NightFeather on 2023/6/6.
//

import SwiftUI

struct IconTextField: View {
	private var iconName: String
	private var inputValue: Binding<String>
	private var placeholder: String
	private var isSecure: Bool
	private var keyboardType: UIKeyboardType = UIKeyboardType.default
	
	init(iconName: String, inputValue: Binding<String>, placeholder: String, isSecure: Bool, keyboardType: UIKeyboardType?) {
		self.iconName = iconName
		self.inputValue = inputValue
		self.placeholder = placeholder
		self.isSecure = isSecure
		self.keyboardType = keyboardType ?? UIKeyboardType.default
	}
	
	var body: some View {
		HStack {
			Image(systemName: iconName)
				.scaleEffect(1.6)
				.frame(width: 40, height: 50)
				
			VStack {
				Divider()
					.hidden()
				if isSecure {
					SecureField(
						placeholder,
						text: inputValue
					)
					.keyboardType(keyboardType)
				} else {
					TextField(
						placeholder,
						text: inputValue
					)
					.keyboardType(keyboardType)
				}
				Divider()
			}
		}
	}
}
