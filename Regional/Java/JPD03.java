// Q301

// import java.util.Arrays;
// import java.util.List;
// import java.util.Scanner;
// import java.util.stream.Collector;
// import java.util.stream.Collectors;

// public class JPD03 {
// 	public static void main(String[] args) {
// 		Scanner sc = new Scanner(System.in);
// 		String[] inputList = {sc.nextLine(), sc.nextLine(), sc.nextLine(), sc.nextLine(), sc.nextLine()};
// 		List<Integer> r = Arrays.stream(inputList)
// 			.map(s -> {
// 				try {
// 					return Integer.parseInt(s);
// 				} catch(Exception e) {
// 					try {
// 						return (int) Math.floor(Double.parseDouble(s));
// 					} catch(Exception ee) {
// 						return 0;
// 					}
// 				}
// 			})
// 			.filter(i -> i > 50)
// 			.collect(Collectors.toList());

// 		int sum = r.stream()
// 			.mapToInt(Integer::intValue)
// 			.sum();

// 		System.out.print(sum + "\n" + (int) Math.floor(sum / r.size()));
// 		sc.close();
// 	}
// }



// Q302

// import java.util.Scanner;

// public class JPD03 {

// 	public static void main(String[] args) {

// 		try {
// 			Scanner sc = new Scanner(System.in);
// 			String[] weekday = {"Mon", "Tus", "Wed", "Thr", "Fri", "Sat", "Sun"};
// 			System.out.print(weekday[sc.nextInt()]);
// 		} catch (Exception e) {
// 			System.out.print("error");
// 			return;
// 		}
// 	}
// }



// Q303

// import java.util.Scanner;
// import java.util.stream.Collector;
// import java.util.stream.Collectors;
// import java.util.Arrays;
// import java.util.Comparator;

// public class JPD03 {
// 	public static void main(String[] args) {
// 		int[] numbers = new int[5];
// 		Scanner sc = new Scanner(System.in);
// 		String[] inputList = {sc.nextLine(), sc.nextLine(), sc.nextLine(), sc.nextLine(), sc.nextLine()};
// 		String r = Arrays.stream(inputList)
// 			.map(s -> {
// 				try {
// 					return Integer.parseInt(s);
// 				} catch(Exception e) {
// 					return 0;
// 				}
// 			})
// 			.sorted()
// 			.map(i -> i.toString())
// 			.collect(Collectors.joining(" "));
// 		System.out.println(r + " ");
// 	}
// }



// Q304

// import java.math.BigDecimal;
// import java.math.RoundingMode;
// import java.util.Arrays;
// import java.util.List;
// import java.util.Scanner;
// import java.util.stream.Collectors;

// public class JPD03 {
// 	public static void main(String[] args) {
// 		int[] scores = { 100, 100, 95, 95, 92, 91, 90, 100, 88, 88, 87, 87, 90, 91, 85, 80, 81, 82, 82, 89 };
// 		List<Integer> filtered;
// 		Scanner sc = new Scanner(System.in);
// 		try {
// 			int remove = sc.nextInt();
// 			filtered = Arrays.stream(scores)
// 				.filter(i -> i != remove)
// 				.boxed()
// 				.collect(Collectors.toList());
// 		} catch(Exception e) {
// 			filtered = Arrays.stream(scores)
// 				.filter(i -> i != 0)
// 				.boxed()
// 				.collect(Collectors.toList());
// 		}
// 		System.out.print(
// 			new BigDecimal(
// 				(double) filtered.stream().mapToInt(Integer::intValue).sum() / (double) filtered.size()
// 			).setScale(2, RoundingMode.HALF_UP)
// 		);
// 	}
// }



// Q305

// import java.lang.reflect.Array;
// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.List;
// import java.util.Scanner;

// public class JPD03 {

// 	public static void main(String[] args) {
// 		Scanner sc = new Scanner(System.in);
// 		List<Long> n = new ArrayList<Long>();
// 		n.add((long) 0);
// 		n.add((long) 1);
// 		n = calc(n);
// 		try {
// 			int i = sc.nextInt();
// 			System.out.print((i + 1) + ":" + n.get(i));
// 		} catch(Exception e) {
// 			System.out.print("error");
// 		}
// 	}

// 	public static List<Long> calc(List<Long> n) {
// 		if (n.size() == 50) return n;
// 		n.add(n.get(n.size() - 2) + n.get(n.size() - 1));
// 		return calc(n);
// 	}
// }



// Q306

// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.List;
// import java.util.Scanner;
// import java.util.stream.Collector;
// import java.util.stream.Collectors;
// import java.util.stream.IntStream;

// public class JPD03 {
// 	public static void main(String args[]) {
// 		List<Integer> a = new ArrayList<Integer>();
// 		a.add(1); a.add(2); a.add(3); a.add(4); a.add(5); a.add(6);
// 		Scanner sc = new Scanner(System.in);

// 		try {
// 			List<Integer> b = Arrays.stream(sc.nextLine().split(" "))
// 				.map(s -> Integer.parseInt(s))
// 				.filter(i -> i > 0 && i < 101)
// 				.collect(Collectors.toList());

// 			IntStream.range(0, 3).forEach(i -> {
// 				System.out.printf("%4d", a.subList(0, 3).get(i) + b.subList(0, 3).get(i));
// 			});
// 			System.out.println();
// 			IntStream.range(0, 3).forEach(i -> {
// 				System.out.printf("%4d", a.subList(3, 6).get(i) + b.subList(3, 6).get(i));
// 			});
// 		} catch(Exception e) {
// 			System.out.print("error");
// 		}
// 	}
// }



// Q307

// import java.util.ArrayList;
// import java.util.Comparator;
// import java.util.List;
// import java.util.Scanner;
// import java.util.stream.Collector;
// import java.util.stream.Collectors;
// import java.util.stream.IntStream;

// public class JPD03 {

// 	public static void main(String[] argv) {
// 		Scanner sc = new Scanner(System.in);
// 		List<String> inputList = new ArrayList<String>();
// 		while (sc.hasNextLine()) {
// 			String l = sc.nextLine();
// 			if (l.compareTo("999") == 0) break;
// 			inputList.add(l);
// 		}
// 		System.out.println("before:" + inputList.stream().collect(Collectors.joining(" ")));
// 		System.out.print("after:" + IntStream.range(0, inputList.size())
// 			.boxed()
// 			.sorted(Comparator.reverseOrder())
// 			.map(i -> inputList.get(i))
// 			.collect(Collectors.joining(" "))
// 		);
// 	}
// }



// Q308

// import java.util.Arrays;
// import java.util.Scanner;

// public class JPD03 {
// 	public static void main(String[] args) {
// 		int[] scores = { 100, 100, 95, 95, 92, 91, 90, 100, 88, 88, 87, 87, 90, 91, 85, 80, 81, 82, 82, 89 };
// 		try {
// 			Scanner sc = new Scanner(System.in);
// 			int s = sc.nextInt();
// 			System.out.print(
// 				Arrays.stream(scores)
// 					.filter(i -> i == s)
// 					.count()
// 			);
// 		} catch (Exception e) {
// 			System.out.print("error");
// 			return;
// 		}
// 	}
// }



// Q309

// import java.util.Scanner;

// public class JPD03 {
// 	public static void main(String args[]) {

// 		try {
// 			Scanner sc = new Scanner(System.in);
// 			int n = sc.nextInt();
// 			if (n < 1 || n > 20) {
// 				System.out.print("error");
// 				return;
// 			}
// 			System.out.print(f(n));
// 		} catch (Exception e) {
// 			System.out.print("error");
// 			return;
// 		}
// 	}

// 	public static int f(int n) {
// 		if (n == 0) return 1;
// 		return n * f(n - 1);
// 	}
// }



// Q310

// import java.util.Scanner;

// public class JPD03 {
// 	public static void main(String[] args) {
// 		Scanner sc = new Scanner(System.in);
// 		try {
// 			int n = Math.abs(sc.nextInt());
// 			if (n == 0) {
// 				System.out.print("error");
// 				return;
// 			}
// 			System.out.print(compute(n));
// 		} catch (Exception e) {
// 			System.out.print("error");
// 			return;
// 		}
// 	}

// 	public static int compute(int n) {
// 		if (n == 1) return 2;
// 		return compute(n - 1) + 3 * n;
// 	}
// }