import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

public class JPD01 {

	public static void main(String[] args) {

		try {
			Scanner sc = new Scanner(System.in);
			var a = sc.nextLine().split(" ");
			var b = sc.nextLine().split(" ");

			System.out.println(
				new BigDecimal(
					Math.sqrt(
						Math.pow(Double.parseDouble(a[0]) - Double.parseDouble(b[0]), 2) +
						Math.pow(Double.parseDouble(a[1]) - Double.parseDouble(b[1]), 2)
					)
				).setScale(4, RoundingMode.HALF_UP)
			);
			sc.close();
			// TO DO

		} catch (Exception e) {
			System.out.print("error");
            System.exit(0);
		}
	}
}
