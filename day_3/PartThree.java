import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class PartThree {
    static class Number {
        public Integer value;
        public boolean isPartNumber;

        public Number(Integer value, boolean isPartNumber) {
            this.value = value;
            this.isPartNumber = isPartNumber;
        }
    }

    public static void main(String[] args) {
        Integer sum = 0;

        List<String> lines = new ArrayList<String>();

        try {
            File file = new File("data.txt");
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                lines.add(line);
            }

            List<Integer> ratios = findGearRatios(lines);

            for (Integer ratio : ratios) {
                sum += ratio;
            }

            sc.close();
        } catch (Exception e) {
            System.out.println("File not found");
            e.printStackTrace();
        }

        System.out.println(sum);
    }

    public static List<Integer> findNumbers(List<String> lines) {
        List<Integer> numbers = new ArrayList<Integer>();

        for (int k = 0; k < lines.size(); k++) {
            String line = lines.get(k);

            Map<Integer, Number> map = new HashMap<Integer, Number>();
            
            for (int i = 0; i < line.length(); i++) {
                int j = i;

                if (Character.isDigit(line.charAt(i))) {
                    map.put(j, new Number(0, false));

                    do {
                        map.put(j, new Number(map.get(j).value * 10 + Character.getNumericValue(line.charAt(i)), false));
                        i++;
                    } while (i < line.length() && Character.isDigit(line.charAt(i)));
                }
            }
            
            if (k > 0) {
                String aboveLine = lines.get(k - 1);

                for (Map.Entry<Integer, Number> entry : map.entrySet()) {
                    int length = entry.getValue().value.toString().length();

                    for (int i = Math.max(0, entry.getKey() - 1); i <= Math.min(aboveLine.length() - 1, entry.getKey() + length); i++) {
                        Character ch = aboveLine.charAt(i);
                        
                        if (!Character.isDigit(ch) && ch != '.') {
                            map.put(entry.getKey(), new Number(entry.getValue().value, true));
                        }
                    }
                }
            }
            
            for (Map.Entry<Integer, Number> entry : map.entrySet()) {
                int length = entry.getValue().value.toString().length();

                Character beforeChar = line.charAt(Math.max(0, entry.getKey() - 1));
                Character afterChar = line.charAt(Math.min(line.length() - 1, entry.getKey() + length));

                if ((!Character.isDigit(beforeChar) && beforeChar != '.') || (!Character.isDigit(afterChar) && afterChar != '.')) {
                    map.put(entry.getKey(), new Number(entry.getValue().value, true));
                }
            }

            if (k < lines.size() - 1) {
                String belowLine = lines.get(k + 1);

                for (Map.Entry<Integer, Number> entry : map.entrySet()) {
                    int length = entry.getValue().value.toString().length();

                    for (int i = Math.max(0, entry.getKey() - 1); i <= Math.min(belowLine.length() - 1, entry.getKey() + length); i++) {
                        Character ch = belowLine.charAt(i);
                        
                        if (!Character.isDigit(ch) && ch != '.') {
                            map.put(entry.getKey(), new Number(entry.getValue().value, true));
                        }
                    }
                }
            }

            for (Map.Entry<Integer, Number> entry : map.entrySet()) {
                if (entry.getValue().isPartNumber) {
                    numbers.add(entry.getValue().value);
                }
            }
        }

        return numbers;
    }

    public static List<Integer> findGearRatios(List<String> lines) {
        List<Integer> gearRatios = new ArrayList<Integer>();

        for (int k = 0; k < lines.size(); k++) {
            String line = lines.get(k);

            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == '*') {
                    List<Number> numbers = new ArrayList<Number>();

                    if (k > 0) {
                        String aboveLine = lines.get(k - 1);
                        Map<Integer, Number> map = new HashMap<Integer, Number>();
                        
                        for (int ii = 0; ii < aboveLine.length(); ii++) {
                            int j = ii;

                            if (Character.isDigit(aboveLine.charAt(ii))) {
                                map.put(j, new Number(0, false));
                                do {
                                    map.put(j, new Number(map.get(j).value * 10 + Character.getNumericValue(aboveLine.charAt(ii)), false));
                                    ii++;
                                } while (ii < aboveLine.length() && Character.isDigit(aboveLine.charAt(ii)));
                            }
                        }

                        for (Map.Entry<Integer, Number> entry : map.entrySet()) {
                            int length = entry.getValue().value.toString().length();
                            
                            if (Math.max(entry.getKey() - 1, 0) <= i && i <= Math.min(entry.getKey() + length, aboveLine.length() - 1)) {
                                numbers.add(entry.getValue());
                            }
                        }
                    }
                    
                    Map<Integer, Number> map = new HashMap<Integer, Number>();
                    
                    for (int ii = 0; ii < line.length(); ii++) {
                        int j = ii;

                        if (Character.isDigit(line.charAt(ii))) {
                            map.put(j, new Number(0, false));

                            do {
                                map.put(j, new Number(map.get(j).value * 10 + Character.getNumericValue(line.charAt(ii)), false));
                                ii++;
                            } while (ii < line.length() && Character.isDigit(line.charAt(ii)));
                        }
                    }

                    for (Map.Entry<Integer, Number> entry : map.entrySet()) {
                        int length = entry.getValue().value.toString().length();
                        
                        if (Math.max(entry.getKey() - 1, 0) == i || i == Math.min(entry.getKey() + length, line.length() - 1)) {
                            numbers.add(entry.getValue());
                        }
                    }

                    if (k < lines.size() - 1) {
                        String belowLine = lines.get(k + 1);
                        map = new HashMap<Integer, Number>();
                        
                        for (int ii = 0; ii < belowLine.length(); ii++) {
                            int j = ii;

                            if (Character.isDigit(belowLine.charAt(ii))) {
                                map.put(j, new Number(0, false));

                                do {
                                    map.put(j, new Number(map.get(j).value * 10 + Character.getNumericValue(belowLine.charAt(ii)), false));
                                    ii++;
                                } while (ii < belowLine.length() && Character.isDigit(belowLine.charAt(ii)));
                            }
                        }

                        for (Map.Entry<Integer, Number> entry : map.entrySet()) {
                            int length = entry.getValue().value.toString().length();
                            
                            if (Math.max(entry.getKey() - 1, 0) <= i && i <= Math.min(entry.getKey() + length, belowLine.length() - 1)) {
                                numbers.add(entry.getValue());
                            }
                        }
                    }
                    
                    if (numbers.size() == 2) {
                        Integer gearRatio = 1;

                        for (Number number : numbers) {
                            System.out.println(number.value > 0 ? number.value : number.value + 1000);
                            gearRatio *= number.value > 0 ? number.value : number.value + 1000;
                        }
                        System.out.println("-----------------");

                        gearRatios.add(gearRatio);
                    }
                }
            }
        }

        return gearRatios;
    }
}
