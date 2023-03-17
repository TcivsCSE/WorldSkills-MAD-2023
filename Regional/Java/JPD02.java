// Q201

// import java.util.Scanner;
// import java.util.stream.Collector;
// import java.util.stream.Collectors;
// import java.util.Arrays;
// import java.util.List;

// public class JPD02 {
// 	public static void main(String[] args) {
// 		Scanner sc = new Scanner(System.in);
// 		String[] inputList = {sc.nextLine(), sc.nextLine()};
// 		List<Number> result = Arrays.stream(inputList)
// 			.map(s -> {
// 				try {
// 					return Integer.parseInt(s);
// 				} catch(Exception e) {
// 					try {
// 						return Double.parseDouble(s);
// 					} catch(Exception ee) {
// 						return 0;
// 					}
// 				}
// 			})
// 			.collect(Collectors.toList());

// 		if (result.get(0).doubleValue() > result.get(1).doubleValue()) {
// 			System.out.println(result.get(0) + ">" + result.get(1));
// 		} else if (result.get(0).doubleValue() < result.get(1).doubleValue()) {
// 			System.out.println(result.get(0) + "<" + result.get(1));
// 		} else if (result.get(0).doubleValue() == result.get(1).doubleValue()) {
// 			System.out.println(result.get(0) + "=" + result.get(1));
// 		}

// 		sc.close();
// 	}
// }



// Q202

// import java.util.Scanner;

// public class JPD02 {
// 	public static void main(String[] args) {
// 		try {
// 			Scanner sc = new Scanner(System.in);
// 			int i = sc.nextInt();
// 			if (i % 2 == 0) {
// 				System.out.print(i + " is an even number.");
// 			} else {
// 				System.out.print(i + " is an odd number.");
// 			}
// 		} catch (Exception e) {
// 			System.out.println("error");
// 			return;
// 		}
// 	}
// }



// Q203

// import java.util.Arrays;
// import java.util.Scanner;

// public class JPD02 {
// 	public static void main(String[] args) {
// 		Scanner sc = new Scanner(System.in);
// 		String[] inputList = {sc.nextLine(), sc.nextLine(), sc.nextLine()};

// 		Arrays.stream(inputList)
// 			.map(s -> {
// 				try {
// 					return Integer.parseInt(s);
// 				} catch(Exception e) {
// 					return 0;
// 				}
// 			})
// 			.filter(i -> i <= 100)
// 			.map(i -> {
// 				if (i >= 85 && i <= 100) {
// 					return "A";
// 				} else if (i >= 60 && i < 85) {
// 					return "B";
// 				} else {
// 					return "C";
// 				}
// 			})
// 			.forEach(s -> System.out.println(s));
// 	}
// }



// Q204

// TODO



// Q205

// import java.util.Scanner;
// import java.util.stream.IntStream;

// public class JPD02 {
// 	public static void main(String[] args) {
// 		try {
// 			Scanner sc = new Scanner(System.in);
// 			int a = sc.nextInt();
// 			int b = sc.nextInt();
// 			int min = IntStream.of(new int[] {a, b}).min().getAsInt();
// 			int max = IntStream.of(new int[] {a, b}).max().getAsInt();
// 			System.out.println(
// 				IntStream.range(2, min)
// 				.filter(i -> max % i == 0 && min % i == 0)
// 				.max()
// 				.getAsInt()
// 			);
// 		} catch (Exception e) {
// 			System.out.print("error");
// 			return;
// 		}
// 	}
// }



// Q206

// import java.util.Scanner;

// public class JPD02 {
// 	public static void main(String[] args) {
// 		final String dreams = "There are moments in life when you miss someone so much that "
// 				+ "you just want to pick them from your dreams and hug them for real! Dream what "
// 				+ "you want to dream; go where you want to go; be what you want to be, because you have "
// 				+ "only one life and one chance to do all the things you want to do "; // Add space at end of string to make split work like we want

// 		Scanner sc = new Scanner(System.in);
// 		String search = sc.nextLine();
// 		System.out.println(dreams.split(search).length - 1);
// 		sc.close();
// 	}
// }



// Q207

// import java.util.Arrays;
// import java.util.Scanner;

// public class JPD02 {
// 	public static void main(String[] args) {
// 		Scanner sc = new Scanner(System.in);
// 		Arrays.stream(sc.nextLine().split(""))
// 			.forEach(c -> System.out.println("ASCII code for '" + c + "' is " + (int) c.charAt(0)));
// 		sc.close();
// 	}
// }



// Q208

// import java.text.DecimalFormat;
// import java.util.Comparator;
// import java.util.Scanner;
// import java.util.stream.IntStream;

// public class JPD02 {
// 	public static void main(String args[]) {
// 		try {
// 			Scanner sc = new Scanner(System.in);
// 			int i = sc.nextInt();
// 			if (i > 9 || i < 1) {
// 				System.out.print("error");
// 				return;
// 			}
// 			DecimalFormat format = new DecimalFormat("00");

// 			IntStream.range(1, i + 1)
// 				.forEach(j -> {
// 					IntStream.range(1, j + 1)
// 						.boxed()
// 						.sorted(Comparator.reverseOrder())
// 						.forEach(k -> {
// 							System.out.print(j + "*" + k + "=" + format.format(j * k) + "  ");
// 						});
// 					System.out.println();
// 				});
// 		} catch (Exception e) {
// 			System.out.print("error");
// 			return;
// 		}
// 	}
// }



// Q209

// import java.util.Arrays;
// import java.util.List;
// import java.util.Scanner;
// import java.util.stream.Collector;
// import java.util.stream.Collectors;

// public class JPD02 {
// 	public static void main(String[] args) {
// 		try {
// 			Scanner sc = new Scanner(System.in);
// 			String[] inputList = {sc.nextLine(), sc.nextLine()};
// 			List<Number> points = Arrays.stream(inputList)
// 				.map(s -> {
// 					try {
// 						return Integer.parseInt(s);
// 					} catch(Exception e) {
// 						try {
// 							return Double.parseDouble(s);
// 						} catch(Exception ee) {
// 							System.out.print("error");
// 							System.exit(0);
// 							return 0;
// 						}
// 					}
// 				})
// 				.collect(Collectors.toList());

// 			if (points.get(0).doubleValue() == 0.0f && points.get(1).doubleValue() == 0.0f) {
// 				System.out.print("(" + points.get(0) + "," + points.get(1) + ")" + " origin");
// 			} else if (points.get(0).doubleValue() == 0.0f) {
// 				System.out.print("(" + points.get(0) + "," + points.get(1) + ")" + " Y");
// 			} else if (points.get(1).doubleValue() == 0.0f) {
// 				System.out.print("(" + points.get(0) + "," + points.get(1) + ")" + " X");
// 			} else {
// 				if (points.get(0).doubleValue() > 0.0f && points.get(1).doubleValue() > 0.0f) {
// 					System.out.print("(" + points.get(0) + "," + points.get(1) + ")" + " 1");
// 				} else if (points.get(0).doubleValue() < 0.0f && points.get(1).doubleValue() > 0.0f) {
// 					System.out.print("(" + points.get(0) + "," + points.get(1) + ")" + " 2");
// 				} else if (points.get(0).doubleValue() < 0.0f && points.get(1).doubleValue() < 0.0f) {
// 					System.out.print("(" + points.get(0) + "," + points.get(1) + ")" + " 3");
// 				} else if (points.get(0).doubleValue() > 0.0f && points.get(1).doubleValue() < 0.0f) {
// 					System.out.print("(" + points.get(0) + "," + points.get(1) + ")" + " 4");
// 				}
// 			}

// 		} catch (Exception e) {
// 			System.out.print("error");
// 			return;
// 		}
// 	}
// }



// Q210

// import java.util.Scanner;

// public class JPD02 {
// 	public static void main(String[] args) {
// 		try {
// 			Scanner sc = new Scanner(System.in);
// 			int num1 = 25;
// 			int num2 = sc.nextInt();
// 			if (num2 > 25) {
// 				System.out.print("error");
// 				return;
// 			}
// 			try {
// 				System.out.print(num1 / num2);
// 			} catch(Exception e) {
// 				System.out.print("error:DivideByZeroException");
// 			}
// 		} catch (Exception e) {
// 			System.out.print("error");
// 			return;
// 		}
// 	}
// }