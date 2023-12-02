import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        int sum = 0;
        int sumOfPowers = 0;

        try {
            File file = new File("data.txt");
            Scanner sc = new Scanner(file);
            
            int lineNum = 1;

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                
                if (fitsCriteria(line)) {
                    sum += lineNum;
                }

                sumOfPowers += findPower(line);

                lineNum++;
            }

            sc.close();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
            e.printStackTrace();
        }

        System.out.println(sum);
        System.out.println(sumOfPowers);
    }

    public static boolean fitsCriteria(String line) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("red", 0);
        map.put("green", 0);
        map.put("blue", 0);

        line = line.split(": ", 0)[1];

        String[] sections = line.split("; ", 0);

        for (String section: sections) {
            String[] parts = section.split(", ", 0);

            for (String part: parts) {
                Integer number = Integer.parseInt(part.split(" ", 0)[0]);
                String colour  = part.split(" ", 0)[1];
                
                map.put(colour, Math.max(map.get(colour), number));
            }
        }

        return Integer.compare(map.get("red"), 13) < 0 
            && Integer.compare(map.get("green"), 14) < 0 
            && Integer.compare(map.get("blue"), 15) < 0; 
    }
    
    public static int findPower(String line) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("red", 0);
        map.put("green", 0);
        map.put("blue", 0);

        line = line.split(": ", 0)[1];

        String[] sections = line.split("; ", 0);

        for (String section: sections) {
            String[] parts = section.split(", ", 0);

            for (String part: parts) {
                Integer number = Integer.parseInt(part.split(" ", 0)[0]);
                String colour  = part.split(" ", 0)[1];
                
                map.put(colour, Math.max(map.get(colour), number));
            }
        }

        return map.get("red") * map.get("green") * map.get("blue");
    }
}
