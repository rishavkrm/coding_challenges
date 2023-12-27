package coding_challenges.wc_tool;

public class TestDrive {
    public static void main(String[] args) {
        boolean exit = false;
        while (!exit) {
            String userInput = UserInput.userInput();

            if (userInput == "exit") {
                exit = true;
                continue;
            }
            String[] userInputArray = userInput.split(" ");

            if (userInputArray[0].equals("ccwc")) {
                ReadFile rf;
                if (userInputArray.length == 2) {
                    String fileName = userInputArray[1];
                    rf = new ReadFile(fileName);
                    rf.wcc();

                } else {
                    
                    String flag = userInputArray[1];
                    String fileName = userInputArray[2];

                    if (flag.equals("-m") || flag.equals("-l") || flag.equals("-c")) {
                        
                        rf = new ReadFile(flag, fileName);
                        rf.wcc();
                    }
                    
                }
                ;
            }
        }

    }
}
