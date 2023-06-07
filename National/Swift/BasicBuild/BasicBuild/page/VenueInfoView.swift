//
//  VenueInfoView.swift
//  BasicBuild
//
//  Created by NightFeather on 2023/6/7.
//

import SwiftUI

struct VenueData: Identifiable {
	let id = UUID()
	let name: String
	let thumbnailName: String
	let personLimit: Int
}

struct VenueInfoView: View {
	@Environment(\.dismiss) private var popBack
	
	private let venueDatas: Array<VenueData> = [
		VenueData(name: "場館A", thumbnailName: "venueTb0", personLimit: 100),
		VenueData(name: "場館B", thumbnailName: "venueTb1", personLimit: 114514),
		VenueData(name: "場館C", thumbnailName: "venueTb2", personLimit: 2100),
		VenueData(name: "場館D", thumbnailName: "venueTb3", personLimit: 1200),
		VenueData(name: "場館E", thumbnailName: "venueTb4", personLimit: 52300)
	]
	
	@State private var nameFilter = ""
	@State private var personLimitFilter = ""
	
    var body: some View {
		VStack {
			HStack {
				Button {
					popBack()
				} label: {
					Image(systemName: "chevron.left")
						.font(.system(size: 24))
				}
				Spacer()
			}
			.padding(10)
			.padding(.horizontal, 6)
			TextField(
				"場地名稱",
				text: $nameFilter
			)
			.padding(.horizontal, 10)
			.frame(maxWidth: .infinity)
			.frame(height: 40)
			.background(.gray.opacity(0.5))
			.cornerRadius(6)
			.padding(.horizontal, 20)
			TextField(
				"最小人數",
				text: $personLimitFilter
			)
			.padding(.horizontal, 10)
			.frame(maxWidth: .infinity)
			.frame(height: 40)
			.background(.gray.opacity(0.5))
			.cornerRadius(6)
			.padding(.horizontal, 20)
			.onChange(of: personLimitFilter) { newValue in
				personLimitFilter = newValue.components(separatedBy: .decimalDigits.inverted).joined(separator: "")
			}
			List {
				ForEach(
					venueDatas.filter({
						if nameFilter.isEmpty && personLimitFilter.isEmpty {
							return true
						} else if nameFilter.isEmpty {
							return $0.personLimit >= Int(personLimitFilter)!
						} else if personLimitFilter.isEmpty {
							return $0.name.contains(nameFilter)
						}
						return $0.name.contains(nameFilter) && $0.personLimit >= Int(personLimitFilter)!
					})
				) { venueData in
					HStack {
						Text(venueData.name)
							.foregroundColor(.white)
							.bold()
							.font(.system(size: 20))
						Spacer()
							.frame(width: 12)
						Text("\(venueData.personLimit)人")
							.foregroundColor(.white)
							.bold()
							.font(.system(size: 20))
							.lineLimit(1)
							.truncationMode(.tail)
							.frame(maxWidth: .infinity, alignment: .center)
					}
					.listRowSeparator(.hidden)
					.listRowSeparatorTint(.white.opacity(1))
					.frame(height: 60)
					.listRowBackground(
						Image(venueData.thumbnailName)
							.resizable()
							.scaledToFill()
							.clipped()
							.opacity(0.7)
							.background(.black)
					)
				}
			}
			.scrollContentBackground(.hidden)
		}
		.navigationBarBackButtonHidden(true)
    }
}
