//
//  SignupView.swift
//  BasicBuild
//
//  Created by NightFeather on 2023/6/6.
//

import SwiftUI

struct SignupView: View {
	@Environment(\.dismiss) private var popBack
	
	@State private var alreadyLogin: Bool = false
	
	@State private var nameInputValue: String = ""
	@State private var emailInputValue: String = ""
	@State private var passwordInputValue: String = ""
	
	@State private var showDialog: Bool = false
	@State private var dialogMessage: String = "註冊成功"
	@State private var dialogIconName: String = "checkmark"
	
	init() {
	}
	
	var body: some View {
		NavigationView {
			ZStack {
				VStack() {
					Text(
						"南港展覽館"
					)
					.bold()
					.font(.system(size: 28))
					Text(
						"場地租借"
					)
					.font(.system(size: 24))
					
					IconTextField(
						iconName: "person",
						inputValue: $nameInputValue,
						placeholder: "姓名",
						isSecure: false,
						keyboardType: nil
					)
					.padding(.top, 60)
					
					IconTextField(
						iconName: "envelope",
						inputValue: $emailInputValue,
						placeholder: "電子郵箱",
						isSecure: false,
						keyboardType: .emailAddress
					)
					
					IconTextField(
						iconName: "lock",
						inputValue: $passwordInputValue,
						placeholder: "密碼",
						isSecure: true,
						keyboardType: nil
					)
					
					Button {
						if nameInputValue.isEmpty || emailInputValue.isEmpty || passwordInputValue.isEmpty {
							dialogMessage = "輸入框不得為空"
							dialogIconName = "xmark"
							withAnimation {
								showDialog.toggle()
							}
							return
						}
						
						var accounts = UserDefaults.standard.dictionary(forKey: "userAccounts")
						
						if accounts!.keys.contains(emailInputValue) {
							dialogMessage = "電子郵箱已被使用"
							dialogIconName = "xmark"
							withAnimation {
								showDialog.toggle()
							}
							return
						}
						
						accounts![emailInputValue] = [
							"id": UUID().uuidString,
							"name": nameInputValue,
							"email": emailInputValue,
							"password": passwordInputValue
						]
						
						nameInputValue = ""
						emailInputValue = ""
						passwordInputValue = ""
						
						UserDefaults.standard.setValue(accounts, forKey: "userAccounts")
						
						dialogMessage = "註冊成功"
						dialogIconName = "checkmark"
						withAnimation {
							showDialog.toggle()
						}
					} label: {
						Text("註冊")
							.font(.system(size: 22))
							.frame(width: 240, height: 60)
							.background(.blue)
							.foregroundColor(.white)
							.cornerRadius(10)
					}
					.padding(.top, 40)
					
					HStack(
						alignment: .center,
						spacing: 0
					) {
						Text(
							"已經有帳號了？"
						)
						Text(
							"登入"
						)
						.foregroundColor(.blue)
						.onTapGesture {
							popBack()
						}
					}
				}
				.padding(.horizontal, 40)
				
				if showDialog {
					Spacer()
					.frame(maxWidth: .infinity, maxHeight: .infinity)
					.background(.black.opacity(0.7))
					.onTapGesture {
						showDialog.toggle()
					}
					VStack {
						Image(
							systemName: dialogIconName
						)
						.frame(width: 80, height: 80)
						.scaleEffect(3)
						Text(
							dialogMessage
						)
						.font(.system(size: 22))
						Button {
							showDialog.toggle()
						} label: {
							Text(
								"確定"
							)
							.frame(width: 160, height: 50)
							.background(.blue)
							.foregroundColor(.white)
							.cornerRadius(10)
						}
					}
					.frame(width: 300, height: 300)
					.background(.white)
					.cornerRadius(20)
				}
			}
		}
		.navigationBarBackButtonHidden(true)
	}
}
