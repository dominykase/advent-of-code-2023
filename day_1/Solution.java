import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        int sum = 0;

        try {
            File myObj = new File("p1data.txt");
            Scanner myReader = new Scanner(myObj);
        
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                sum += findSum(data);      
            }
            
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        System.out.println(sum);
    }
    
    public static int findSum(String str) {
        int sum = 0;
        Map<String, Integer> map = new HashMap<String, Integer>();

        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);
        map.put("four", 4);
        map.put("five", 5);
        map.put("six", 6);
        map.put("seven", 7);
        map.put("eight", 8);
        map.put("nine", 9);
        map.put("ten", 10);
        
        int firstPos = str.length();
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                firstPos = i;
                break;
            }
        }
        
        int lastPos = -1;
        for (int i = str.length() - 1; i >= 0 ; i--) {
            if (Character.isDigit(str.charAt(i))) {
                lastPos = i;
                break;
            }
        }
        
        String firstWord = "";
        String lastWord = "";
        int firstWordPos = str.length();
        int lastWordPos = -1;
        for (Map.Entry<String, Integer> entry: map.entrySet()) {
            String[] list = str.split(entry.getKey(), -1);
            if (list.length >= 1) {
                if (firstWordPos > list[0].length()) {
                    firstWordPos = list[0].length();
                    firstWord = entry.getKey();
                }
                if (lastWordPos < str.length() - list[list.length - 1].length() - entry.getKey().length()) {
                    lastWordPos = str.length() - list[list.length - 1].length() - entry.getKey().length();
                    lastWord = entry.getKey();
                }
            }
        }

        int firstDigit = 0;

        if (firstPos < firstWordPos) {
            firstDigit = Character.getNumericValue(str.charAt(firstPos));
        } else {
            firstDigit = map.get(firstWord).intValue();
        }

        int lastDigit = 0;

        if (lastPos < lastWordPos) {
            lastDigit = map.get(lastWord).intValue();
        } else {
            lastDigit = Character.getNumericValue(str.charAt(lastPos));
        }

        return 10 * firstDigit + lastDigit;
    }
}
