// Q301

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class JPD03 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String[] inputList = {sc.nextLine(), sc.nextLine(), sc.nextLine(), sc.nextLine(), sc.nextLine()};
		var sum = Arrays.stream(inputList)
			.map(s -> {
				try {
					return Integer.parseInt(s);
				} catch(Exception e) {
					try {
						return (int) Math.floor(Double.parseDouble(s));
					} catch(Exception ee) {
						return 0;
					}
				}
			})
			.filter(i -> i > 50)
			.mapToInt(Integer::intValue);

		System.out.print(r.sum() + "\n" + (int) Math.floor(r.sum() / r.count()));
		sc.close();
	}
}



// Q302

// TODO



// Q303

// TODO



// Q304

// TODO



// Q305

// TODO



// Q306

// TODO



// Q307

// TODO



// Q308

// TODO



// Q309

// TODO



// Q310

// TODO