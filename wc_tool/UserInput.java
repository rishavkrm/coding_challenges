package coding_challenges.wc_tool;
import java.util.Scanner;
public class UserInput {
    static String userInput() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        // scanner.close();
        return input;
    }
}
