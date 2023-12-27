package coding_challenges.wc_tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ReadFile {
    private String _flag;
    private String _filePath;

    public ReadFile(String flag, String fileName) {
        _flag = flag;
        _filePath = "coding_challenges/wc_tool/" + fileName;
    }

    public ReadFile(String fileName) {
        // coding_challenges/wc_tool/test.txt
        _filePath = "coding_challenges/wc_tool/" + fileName;
    }

    public void wcc() {
        File file = new File(_filePath);
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);
            int lines = 0;
            int words = 0;
            int chars = 0;
            String line = reader.readLine();
            
            while (line != null) {
                lines += 1;
                
                String[] arr = line.split(" ");
                byte[] byteArray = line.getBytes();
                chars += byteArray.length;
                for (String s : arr) {
                    if (s.length() > 0) {
                        words += 1;
                    }
                }
                line = reader.readLine();
            }
            if(_flag == null){
                System.out.println(lines+ " " + words + " " + chars);
            }
            else if(_flag.equals("-w")){
                System.out.println(words);
            }
            else if(_flag.equals("-l")){
                System.out.println(lines);
            }
            else if(_flag.equals("-c")){
                System.out.println(chars);
            }            
            reader.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
