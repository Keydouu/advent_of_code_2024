import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class D5 {
    public static void main(String[] args) {
        String filePath = "day5/input.txt";

        try {
            String[] input = new String(Files.readAllBytes(Paths.get(filePath))).split("\n");
            boolean readingRules = true;
            ArrayList<String[]> rules = new ArrayList<>();
            ArrayList<String> correctUpdates = new ArrayList<>();
            ArrayList<String> incorrectUpdates = new ArrayList<>();
            for (String current : input) {
                if (readingRules){
                    if (!current.contains("|")){
                        readingRules = false;
                        continue;
                    }
                    rules.add(current.split("\\|"));
                }
                else{
                    boolean allGood = true;
                    for(int i=0; i<rules.size(); i++){
                        String[] rule=rules.get(i);
                        if(current.contains(rule[0])){
                            String[] splited = current.split(rule[0]);
                            if (splited[0].contains(rule[1])) {
                                allGood=false;
                                current = splited[0].replace(rule[1]+',', "")+rule[0]+','+rule[1];
                                if (splited.length>1)
                                    current+=splited[1];
                                i=-1;
                            }
                        }
                    }
                    if (allGood)
                        correctUpdates.add(current);
                    else
                        incorrectUpdates.add(current);
                }
            }
            int output=0;
            for(String update : correctUpdates){
                String[] content = update.split(",");
                output += Integer.parseInt(content[(content.length-1)/2]);
            }
            System.err.println("Part 1 Output : "+output);
            output=0;
            for(String update : incorrectUpdates){
                String[] content = update.split(",");
                output += Integer.parseInt(content[(content.length-1)/2]);
            }//4525 too high
            System.err.println("Part 2 Output : "+output);
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }  
}
