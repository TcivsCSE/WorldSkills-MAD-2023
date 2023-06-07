//
//  HomeView.swift
//  BasicBuild
//
//  Created by NightFeather on 2023/6/5.
//

import SwiftUI

struct HomeView: View {
	@Environment(\.dismiss) private var popBack
	
	let persistenceController = PersistenceController.shared
	
	@State private var navToVenueRental = false
	@State private var navToVenueInfo = false
	
	@State private var showDialog = false
	
	func userIsGuest() -> Bool {
		if UserDefaults.standard.dictionary(forKey: "userData") == nil {
			return true
		}
		return false
	}
	
	private let newsData = [
		["title": "test1", "content": "123123test1123123test12312323123", "datetime": "2023/06/06 12:30 PM"],
		["title": "test1", "content": "123123test123123test123123123123", "datetime": "2023/06/06 12:30 PM"],
		["title": "test1", "content": "123123test123123test123123123123", "datetime": "2023/06/06 12:30 PM"],
		["title": "test1", "content": "123123tes123123test123123t123123", "datetime": "2023/06/06 12:30 PM"],
		["title": "test1", "content": "123123te123123test123123st123123", "datetime": "2023/06/06 12:30 PM"],
		["title": "test1", "content": "123123test123123123test123123123", "datetime": "2023/06/06 12:30 PM"],
		["title": "test1", "content": "123123test12123123teast123123123", "datetime": "2023/06/06 12:30 PM"],
		["title": "test1", "content": "123123test123123123test123123123", "datetime": "2023/06/06 12:30 PM"],
		["title": "test1", "content": "123123te123123test123123st123123", "datetime": "2023/06/06 12:30 PM"],
		["title": "test1", "content": "123123test12123123test1231233123", "datetime": "2023/06/06 12:30 PM"],
		["title": "test1", "content": "123123123123test123123test123123", "datetime": "2023/06/06 12:30 PM"],
		["title": "test1", "content": "123123test12123123test1231233123", "datetime": "2023/06/06 12:30 PM"],
		["title": "test1", "content": "123123123123test123123test123123", "datetime": "2023/06/06 12:30 PM"],
		["title": "test1", "content": "123123test1123123test12312323123", "datetime": "2023/06/06 12:30 PM"],
		["title": "test1", "content": "123123123123test123123test123123", "datetime": "2023/06/06 12:30 PM"],
		["title": "test1", "content": "12312123123test1231233test123123", "datetime": "2023/06/06 12:30 PM"],
		["title": "test1", "content": "123123test1123123test12312323123", "datetime": "2023/06/06 12:30 PM"],
		["title": "test1", "content": "123123te123123test123123st123123", "datetime": "2023/06/06 12:30 PM"],
		["title": "test1", "content": "123123test1231123123test12312323", "datetime": "2023/06/06 12:30 PM"],
		["title": "test1", "content": "123123test123123test123123123123", "datetime": "2023/06/06 12:30 PM"],
		["title": "test1", "content": "123123tes123123test123123t123123", "datetime": "2023/06/06 12:30 PM"],
		["title": "test1", "content": "123123test12123123test1231233123", "datetime": "2023/06/06 12:30 PM"],
		["title": "test1", "content": "123123tes123123test123123t123123", "datetime": "2023/06/06 12:30 PM"],
		["title": "test1", "content": "123123test123123test123123123123", "datetime": "2023/06/06 12:30 PM"],
		["title": "test1", "content": "123123te123123test123123st123123", "datetime": "2023/06/06 12:30 PM"],
		["title": "test1", "content": "123123te123123test123123st123123", "datetime": "2023/06/06 12:30 PM"],
		["title": "test1", "content": "123123test1123123test12312323123", "datetime": "2023/06/06 12:30 PM"],
	]
	
    var body: some View {
		NavigationView {
			ZStack {
				VStack {
					ViewPager(
						imageNames: [
							"vpImg0",
							"vpImg1",
							"vpImg2",
							"vpImg3",
							"vpImg4",
						]
					)
					Spacer()
						.frame(height: 30)
					Text(
						"展場消息"
					)
					.font(.system(size: 24))
					.frame(maxWidth: .infinity, alignment: .leading)
					.padding(.horizontal, 20)
					List(newsData.indices, id: \.self) { idx in
						HStack {
							VStack(
								alignment: .leading
							) {
								Text(newsData[idx]["title"]!)
								Text(newsData[idx]["datetime"]!)
							}
							Spacer()
								.frame(width: 12)
							Text(newsData[idx]["content"]!)
								.lineLimit(1)
								.truncationMode(.tail)
						}
						.listRowSeparator(.hidden)
						.listRowSeparatorTint(.white.opacity(1))
						.frame(height: 60)
						.listRowBackground(
							Color.gray.opacity(0.5).cornerRadius(10).padding(.vertical, 4)
						)
					}
					.listStyle(.plain)
					.padding(.horizontal, 20)
					.frame(height: 400)
					Spacer()
					HStack {
						Spacer()
						Button {
							navToVenueInfo.toggle()
						} label: {
							Text("場地介紹")
								.frame(width: 180, height: 100)
								.background(.blue)
								.foregroundColor(.white)
								.cornerRadius(10)
						}
						Spacer()
						Button {
							if userIsGuest() {
								withAnimation {
									showDialog.toggle()
								}
								return
							}
							navToVenueRental.toggle()
						} label: {
							Text("場地租用")
								.frame(width: 180, height: 100)
								.background(.blue)
								.foregroundColor(.white)
								.cornerRadius(10)
						}
						Spacer()
					}
					Spacer()
					Button {
						UserDefaults.standard.removeObject(forKey: "userData")
						popBack()
					} label: {
						if userIsGuest() {
							Text("前往登入")
						} else {
							Text("登出")
						}
					}
					Spacer()
				}
				
				if showDialog {
					Spacer()
						.frame(maxWidth: .infinity, maxHeight: .infinity)
						.background(.black.opacity(0.7))
						.onTapGesture {
							showDialog.toggle()
						}
					VStack {
						Image(
							systemName: "exclamationmark.triangle.fill"
						)
						.frame(width: 80, height: 80)
						.scaleEffect(3)
						Text(
							"訪客無法使用此功能"
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
				
				NavigationLink(
					destination: VenueInfoView(),
					isActive: $navToVenueInfo
				) {
					EmptyView()
				}
				.hidden()
				
				NavigationLink(
					destination: VenueRentalView().environment(\.managedObjectContext, persistenceController.container.viewContext),
					isActive: $navToVenueRental
				) {
					EmptyView()
				}
				.hidden()
			}
		}
		.navigationBarBackButtonHidden(true)
    }
}
