import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class JPD01 {

	public static void main(String[] args) {
		ArrayList<String> dreams = new ArrayList<String>(Arrays.asList(("There are moments in life when you miss someone so much that "
				+ "you just want to pick them from your dreams and hug them for real! Dream what "
				+ "you want to dream;go where you want to go;be what you want to be,because you have "
				+ "only one life and one chance to do all the things you want to do").split(" ")));

		Scanner sc = new Scanner(System.in);
		String search = sc.nextLine().replace(" ", "");
		sc.close();
		
		Integer first, last;
		String result;

		if (!dreams.contains(search)) {
			first = 0;
			last = 0;
			result = "";
		} else if (dreams.indexOf(search) == dreams.lastIndexOf(search)) {
			first = dreams.indexOf(search);
			last = 0;
			result = String.join(" ", dreams.subList(first, dreams.size() - 1));
			first += 1;
		} else {
			first = dreams.indexOf(search);
			last = dreams.lastIndexOf(search);
			result = String.join(" ", dreams.subList(first, last + search.length()));
			first += 1;
			last += 1;
		}

		System.out.println("first:" + first);
		System.out.println("last:" + last);
		System.out.print("capture:" + result);
	}
}
