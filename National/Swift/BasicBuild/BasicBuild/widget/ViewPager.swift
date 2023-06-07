//
//  ViewPager.swift
//  BasicBuild
//
//  Created by NightFeather on 2023/6/6.
//

import SwiftUI

struct ViewPager: View {
	@State private var selectedIndex = 0
	private var imageNames: Array<String> = []
	private var timer = Timer.publish(every: 3, on: .main, in: .common).autoconnect()
	
	init(imageNames: Array<String>) {
		self.imageNames = imageNames
	}
	
    var body: some View {
		ZStack(
			alignment: .center
		) {
			TabView(selection: $selectedIndex) {
				ForEach(imageNames.indices, id: \.self) { idx in
					ViewPagerItem(imageName: imageNames[idx])
				}
			}
			.onReceive(timer) { _ in
				selectedIndex = (selectedIndex + 1) % imageNames.count
			}
			HStack {
				Button {
					if selectedIndex - 1 == -1 {
						selectedIndex = imageNames.count - 1
					} else {
						selectedIndex = (selectedIndex - 1) % imageNames.count
					}
				} label: {
					Image(systemName: "chevron.left")
						.foregroundColor(.white)
						.font(.system(size: 28))
						.frame(width: 42)
						.frame(maxHeight: 200)
						.background(.black.opacity(0.4))
						.clipped()
				}
				Spacer()
				Button {
					selectedIndex = (selectedIndex + 1) % imageNames.count
				} label: {
					Image(systemName: "chevron.right")
						.foregroundColor(.white)
						.font(.system(size: 28))
						.frame(width: 42)
						.frame(maxHeight: 200)
						.background(.black.opacity(0.4))
						.clipped()
				}
			}
			VStack {
				Spacer()
				HStack {
					ForEach(imageNames.indices, id: \.self) { idx in
						Spacer()
							.frame(width: 6, height: 6)
							.background(idx == selectedIndex ? .white : .black.opacity(0.4))
							.cornerRadius(100)
							.padding(.vertical, 6)
							.padding(.horizontal, 1)
					}
				}
			}
		}
		.frame(height: 200)
    }
}

struct ViewPagerItem: View {
	private var imageName = ""
	
	init(imageName: String) {
		self.imageName = imageName
	}
	
	var body: some View {
		GeometryReader { geo in
			Image(imageName)
				.resizable()
				.scaledToFill()
				.clipped()
		}
	}
}
