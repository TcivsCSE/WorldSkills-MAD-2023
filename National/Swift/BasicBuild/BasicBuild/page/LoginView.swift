//
//  LoginView.swift
//  BasicBuild
//
//  Created by NightFeather on 2023/6/5.
//

import SwiftUI

struct LoginView: View {
	@State private var navToHome: Bool = false
	@State private var navToSignup: Bool = false
	
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
						iconName: "envelope",
						inputValue: $emailInputValue,
						placeholder: "電子郵箱",
						isSecure: false,
						keyboardType: .emailAddress
					)
					.padding(.top, 60)
					
					IconTextField(
						iconName: "lock",
						inputValue: $passwordInputValue,
						placeholder: "密碼",
						isSecure: true,
						keyboardType: nil
					)
					
					Button {
						if emailInputValue.isEmpty || passwordInputValue.isEmpty {
							dialogMessage = "輸入框不得為空"
							dialogIconName = "xmark"
							withAnimation {
								showDialog.toggle()
							}
							return
						}
						
						let accounts = UserDefaults.standard.dictionary(forKey: "userAccounts")
						
						if !accounts!.keys.contains(emailInputValue) {
							dialogMessage = "帳號不存在"
							dialogIconName = "xmark"
							withAnimation {
								showDialog.toggle()
							}
							return
						}
						
						if (accounts![emailInputValue] as! Dictionary<String, Any?>)["password"] as! String != passwordInputValue {
							dialogMessage = "密碼錯誤"
							dialogIconName = "xmark"
							withAnimation {
								showDialog.toggle()
							}
							return
						}
						
						UserDefaults.standard.set(accounts![emailInputValue], forKey: "userData")
						
						emailInputValue = ""
						passwordInputValue = ""
						
						navToHome.toggle()
					} label: {
						Text("登入")
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
							"沒有帳號嗎？"
						)
						Text(
							"前往註冊"
						)
						.foregroundColor(.blue)
						.onTapGesture {
							navToSignup.toggle()
						}
						Text(
							"或"
						)
						Text(
							"以訪客身份繼續"
						)
						.foregroundColor(.blue)
						.onTapGesture {
							navToHome.toggle()
						}
					}
					
					NavigationLink(
						destination: SignupView(),
						isActive: $navToSignup
					) {
						EmptyView()
					}
					.hidden()
					
					NavigationLink(
						destination: HomeView(),
						isActive: $navToHome
					) {
						EmptyView()
					}
					.hidden()
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
		.onAppear {
			navToHome = UserDefaults.standard.dictionary(forKey: "userData") != nil
		}
    }
}
