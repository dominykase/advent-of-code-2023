import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Solution {

    public static void main(String[] args) {
        List<Long> preliminarySeedNumbers = new ArrayList<Long>();
        String contents = "";

        try {
            File file = new File("data.txt");
            Scanner scanner = new Scanner(file);
            
            int lineNum = 1;

            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();

                if (lineNum == 1) {
                    String[] numbersStr = (data.split(": ", 0)[1]).split(" ", 0);
                    for (String numberStr : numbersStr) {
                        preliminarySeedNumbers.add(Long.parseLong(numberStr));
                    }
                } else if (lineNum != 2) {
                    contents += data + "\n";
                }

                lineNum++;
            }

            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        List<List<List<Long>>> data = parseData(contents);

        Long min = Long.MAX_VALUE;

        for (int k = 0; k < preliminarySeedNumbers.size(); k += 2) {
            Long seedRangeStart = preliminarySeedNumbers.get(k);
            Long seedRangeLength = preliminarySeedNumbers.get(k+1);
            
            for (Long kk = seedRangeStart; kk < seedRangeStart + seedRangeLength; kk++) {
                Long finalNum = kk;

                for (int i = 0; i < data.size(); i++) {
                    List<List<Long>> sectionData = data.get(i);
                    
                    Long startingNum = finalNum;
                    finalNum = -1L;

                    for (int j = 0; j < sectionData.size(); j++) {
                        List<Long> row = sectionData.get(j);
                        
                        Long diff = startingNum - row.get(1); 
                        
                        if (Long.compare(diff, 0L) >= 0 && Long.compare(diff, row.get(2)) < 0) {
                            finalNum = row.get(0) + diff;
                        }
                    }

                    if (Long.compare(finalNum, 0L) < 0) {
                        finalNum = startingNum;
                    }
                }

                min = Long.compare(finalNum, min) < 0 ? finalNum : min;
            }
        }
        
        System.out.println(min);
    }

    private static List<List<List<Long>>> parseData(String contents) {
        String[] sections = contents.split("\n\n", 0);
        List<List<List<Long>>> data = new ArrayList<List<List<Long>>>();

        for (String section : sections) {
            String dataLines = section.split(":\n", 0)[1];
            String[] lines = dataLines.split("\n", 0);

            List<List<Long>> sectionData = new ArrayList<List<Long>>();

            for (String line : lines) {
                List<Long> lineData = new ArrayList<Long>();
                String[] numbersStr = line.split(" ", 0);

                for (String numberStr : numbersStr) {
                    lineData.add(Long.parseLong(numberStr));
                }

                sectionData.add(lineData);
            }

            data.add(sectionData);
        }

        return data;
    }
}
