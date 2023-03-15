import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

public class JPD01 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println(
			sc.hasNextDouble() ?
				new BigDecimal(Math.pow(sc.nextDouble(), 2) * Math.PI).setScale(4, RoundingMode.HALF_UP) :
				"0.0000"
		);
		sc.close();
	}
}
