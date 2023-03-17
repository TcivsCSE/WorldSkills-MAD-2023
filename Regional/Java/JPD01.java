// Q101

// import java.util.Scanner;

// public class JPD01 {
// 	public static void main(String[] args) {
// 		Scanner sc = new Scanner(System.in);
// 		System.out.println(sc.nextLine() + " " + sc.nextLine());
// 		sc.close();
// 	}
// }



// Q102

// import java.util.Arrays;
// import java.util.Scanner;

// public class JPD01 {
// 	public static void main(String[] args) {
// 		Scanner sc = new Scanner(System.in);
// 		String[] inputList = {sc.nextLine(), sc.nextLine()};

// 		System.out.println(
// 			Arrays.stream(inputList)
// 				.map(s -> {
// 					try {
// 						return Integer.parseInt(s);
// 					} catch(Exception e) {
// 						return 0;
// 					}
// 				})
// 				.filter(i -> i % 2 == 0)
// 				.mapToInt(Integer::intValue)
// 				.sum()
// 		);

// 		sc.close();
// 	}
// }



// Q103

// import java.math.BigDecimal;
// import java.math.RoundingMode;
// import java.util.Scanner;

// public class JPD01 {
// 	public static void main(String[] args) {
// 		Scanner sc = new Scanner(System.in);
// 		System.out.println(
// 			sc.hasNextDouble() ?
// 				new BigDecimal(Math.pow(sc.nextDouble(), 2) * Math.PI).setScale(4, RoundingMode.HALF_UP) :
// 				"0.0000"
// 		);
// 		sc.close();
// 	}
// }



// Q104

// import java.math.BigDecimal;
// import java.math.RoundingMode;
// import java.util.Scanner;

// public class JPD01 {

// 	public static void main(String[] args) {
// 		Scanner sc = new Scanner(System.in);
// 		var a = sc.nextLine().split(" ");
// 		var b = sc.nextLine().split(" ");

// 		System.out.println(
// 			new BigDecimal(
// 				Math.sqrt(
// 					Math.pow(Double.parseDouble(a[0]) - Double.parseDouble(b[0]), 2) +
// 					Math.pow(Double.parseDouble(a[1]) - Double.parseDouble(b[1]), 2)
// 				)
// 			).setScale(4, RoundingMode.HALF_UP)
// 		);
		
// 		sc.close();
// 	}
// }



// Q105

// TODO



// Q106

// import java.util.List;
// import java.util.Arrays;
// import java.util.Comparator;
// import java.util.Scanner;
// import java.util.stream.Collector;
// import java.util.stream.Collectors;

// public class JPD01 {
// 	public static void main(String[] args) {
// 		Scanner sc = new Scanner(System.in);

// 		String[] inputList = {sc.nextLine(), sc.nextLine(), sc.nextLine(), sc.nextLine()};
// 		List<Integer> r = Arrays.stream(inputList)
// 			.map(s -> {
// 				try {
// 					return Integer.parseInt(s);
// 				} catch(Exception e) {
// 					return 0;
// 				}
// 			})
// 			.map(i -> i < 0 ? 0 : i)
// 			.sorted()
// 			.collect(Collectors.toList());

// 		sc.close();
// 		System.out.println("smallest:" + r.get(0));
// 		System.out.print("largest:" + r.get(r.size() - 1));
// 	}
// }



// Q107

// import java.util.Arrays;
// import java.util.Scanner;

// public class JPD01 {
// 	public static void main(String[] args) {
// 		Scanner sc = new Scanner(System.in);
// 		String[] inputList = {sc.nextLine(), sc.nextLine(), sc.nextLine()};

// 		int score = (int) Math.ceil(Arrays.stream(inputList)
// 			.map(s -> {
// 				try {
// 					return Integer.parseInt(s);
// 				} catch(Exception e) {
// 					return 0;
// 				}
// 			})
// 			.map(i -> {
// 				if (i < 0) {
// 					return 0;
// 				} else if (i > 100) {
// 					return 100;
// 				} else {
// 					return i;
// 				}
// 			})
// 			.mapToInt(Integer::intValue)
// 			.sum() / 3.0f);

// 		if (score < 60) {
// 			System.out.print("failed:" + score);
// 		} else if (score == 100) {
// 			System.out.print("full mark:" + score);
// 		} else {
// 			System.out.print("pass:" + score);
// 		}
// 	}
// }



// Q108

// import java.math.BigDecimal;
// import java.math.RoundingMode;
// import java.util.Locale;
// import java.util.Scanner;

// public class JPD01 {
// 	public static void main(String[] args) {
// 		try {
// 			Scanner sc = new Scanner(System.in);
// 			double x = sc.nextDouble();

// 			System.out.println(
// 				new BigDecimal((2.0f * Math.pow(x, 3.0f)) + (3.0f * x) - 1.0f)
// 					.setScale(4, RoundingMode.DOWN)
// 			);
// 		} catch (Exception e) {
// 			System.out.print("error");
// 		}
// 	}
// }



// Q109

// TODO



// Q110

// import java.text.DecimalFormat;
// import java.util.Arrays;
// import java.util.Scanner;
// import java.util.stream.Collector;
// import java.util.stream.Collectors;
// import java.util.List;

// public class JPD01 {

// 	public static void main(String[] args) {
// 		Scanner sc = new Scanner(System.in);
// 		String[] inputList = {sc.nextLine(), sc.nextLine(), sc.nextLine()};
// 		List<Integer> moneys = Arrays.stream(inputList)
// 			.map(s -> {
// 				try {
// 					return Integer.parseInt(s);
// 				} catch(Exception e) {
// 					return 0;
// 				}
// 			})
// 			.map(i -> i < 0 ? 0 : i)
// 			.collect(Collectors.toList());
// 		int total = moneys.get(0) + (moneys.get(1) * 5) + (moneys.get(2) * 10);
// 		System.out.println(new DecimalFormat("#,###").format(total));
// 		sc.close();
// 	}
}
