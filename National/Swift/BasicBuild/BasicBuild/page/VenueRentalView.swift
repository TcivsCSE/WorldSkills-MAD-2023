//
//  VenueRentalView.swift
//  BasicBuild
//
//  Created by NightFeather on 2023/6/7.
//

import SwiftUI
import CoreData

struct VenueRentalView: View {
	@Environment(\.dismiss) private var popBack
	@Environment(\.managedObjectContext) private var viewContext
	
	@FetchRequest(
		sortDescriptors: [NSSortDescriptor(keyPath: \RentalRecord.datetime, ascending: true)],
		animation: .default)
	private var records: FetchedResults<RentalRecord>
	
	@State private var showDialog = false
	
	private let venueOptions: Array<String> = [
		"場館A", "場館B", "場館C", "場館D", "場館E"
	]
	@State private var venueValue = "場館A"
	@State private var personsValue = ""
	@State private var datetimeValue = Date()
	
	@State private var errorMessage = ""
	
	private let dateFormatter: DateFormatter = {
		let formatter = DateFormatter()
		formatter.dateStyle = .short
		formatter.timeStyle = .medium
		return formatter
	}()
	
    var body: some View {
		ZStack {
			VStack {
				HStack {
					Button {
						popBack()
					} label: {
						Image(systemName: "chevron.left")
							.font(.system(size: 24))
					}
					Spacer()
					Button {
						withAnimation {
							showDialog.toggle()
						}
					} label: {
						HStack {
							Text("租用場地")
								.font(.system(size: 20))
							Image(systemName: "plus")
								.font(.system(size: 24))
						}
					}
				}
				.padding(10)
				.padding(.horizontal, 6)
				Text("租用記錄")
					.bold()
					.font(.system(size: 26))
					.frame(maxWidth: .infinity, alignment: .leading)
					.padding(.horizontal, 30)
				List {
					ForEach(records.indices, id: \.self) { idx in
						HStack {
							VStack(
								alignment: .leading
							) {
								Text(records[idx].venue!)
								Text(records[idx].datetime!, formatter: dateFormatter)
							}
							Spacer()
								.frame(width: 12)
							 Text("\(records[idx].persons)人")
								.lineLimit(1)
								.truncationMode(.tail)
								.frame(maxWidth: .infinity, alignment: .center)
						}
						.listRowSeparator(.hidden)
						.listRowSeparatorTint(.white.opacity(1))
						.frame(height: 60)
						.listRowBackground(
							Color.gray.opacity(0.5).cornerRadius(10).padding(.vertical, 4)
						)
					}
				}
				.listStyle(.plain)
				.padding(.horizontal, 22)
			}
			
			if showDialog {
				Spacer()
					.frame(maxWidth: .infinity, maxHeight: .infinity)
					.background(.black.opacity(0.7))
					.onTapGesture {
						showDialog.toggle()
					}
				VStack {
					Text("預約場地")
						.bold()
						.font(.system(size: 26))
					HStack {
						Text("輸入人數：")
						TextField(
							"人數",
							text: $personsValue
						)
						.lineLimit(1)
						.multilineTextAlignment(.center)
						.onChange(of: personsValue) { newValue in
							personsValue = newValue.components(separatedBy: CharacterSet.decimalDigits.inverted).joined(separator: "")
						}
						.frame(width: 200, height: 40)
					}
					HStack {
						Text("選擇場地：")
						Picker("場地", selection: $venueValue) {
							 ForEach(venueOptions, id: \.self) { option in
								 Text(option)
							 }
						 }
						 .pickerStyle(.menu)
						 .frame(width: 200, height: 40)
					}
					HStack {
						Text("選擇時間：")
						DatePicker(selection: $datetimeValue) {
							
						}
						 .pickerStyle(.menu)
						 .frame(width: 200, height: 40)
					}
					Text(errorMessage)
						.foregroundColor(.red)
						.frame(height: 26)
					Button {
						if personsValue.isEmpty {
							errorMessage = "人數不得為空"
							return
						}
						if Int(personsValue)! > 999999 || Int(personsValue)! < 0 {
							errorMessage = "輸入的人數為無效值"
							return
						}
						if datetimeValue <= Date() {
							errorMessage = "輸入的日期為無效值"
							return
						}
						let newRecord = RentalRecord(context: viewContext)
						newRecord.persons = Int32(personsValue)!
						newRecord.datetime = datetimeValue
						newRecord.venue = venueValue
						newRecord.id = UUID()
						newRecord.userId = UUID(uuidString: UserDefaults.standard.dictionary(forKey: "userData")!["id"] as! String)
						withAnimation {
							do {
								try viewContext.save()
							} catch {
								
							}
						}
						personsValue = ""
						datetimeValue = Date()
						venueValue = venueOptions.first!
						showDialog.toggle()
					} label: {
						Text("送出")
							.frame(width: 180, height: 40)
							.background(.blue)
							.foregroundColor(.white)
							.cornerRadius(10)
					}
				}
				.padding(30)
				.keyboardType(.numberPad)
				.frame(width: 360, height: 340)
				.background(.white)
				.cornerRadius(20)
			}
		}
		.navigationBarBackButtonHidden(true)
    }
}
