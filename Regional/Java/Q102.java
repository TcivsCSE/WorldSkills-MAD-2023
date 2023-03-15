import java.util.Scanner;

public class JPD01 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		var r = 0;

		if (sc.hasNextInt()) {
			var i = sc.nextInt();
			if (i % 2 == 0) {
				r += i;
			}
		} else {
			sc.nextLine();
		}

		if (sc.hasNextInt()) {
			var i = sc.nextInt();
			if (i % 2 == 0) {
				r += i;
			}
		} else {
			sc.nextLine();
		}
		
		System.out.println(r);

		sc.close();
	}
}
