import java.util.ArrayList;
import java.util.Scanner;

public class JPD01 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		ArrayList<Integer> r = new ArrayList<Integer>();
		
		for (var j = 0; j < 3; j += 1) {
			if (sc.hasNextInt()) {
				r.add(sc.nextInt());
			} else {
				r.add(0);
			}
		}

		var avg = r.stream().mapToInt(Integer::intValue).forEach(null);

		System.out.print("full mark:" + );

		System.out.print("pass:" + );

		System.out.print("failed:" + );
	}
}
