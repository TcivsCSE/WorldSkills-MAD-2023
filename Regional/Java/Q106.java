import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class JPD01 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		ArrayList<Integer> r = new ArrayList<Integer>();

		for (var j = 0; j < 4; j += 1) {

			if (sc.hasNextInt()) {
				var i = sc.nextInt();
				if (i >= 0) {
					r.add(i);
				} else {
					r.add(0);
				}
			} else {
				r.add(0);
				sc.nextLine();
			}
		}

		r.sort(null);
		sc.close();
		System.out.println("smallest:" + r.get(0));
		System.out.print("largest:" + r.get(r.size() - 1));
	}

}
