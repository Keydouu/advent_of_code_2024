import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class day6 {
    private static int xDirection = 0;// 1 Right -1 Left
    private static int yDirection = 1;// 1 UP  -1 Down
    public static void main(String[] args) {
        String filePath = "day6/input.txt";
        try {
            String[] input = new String(Files.readAllBytes(Paths.get(filePath))).split("\n");
            int lines = input.length;
            int columns = input[0].length();
            int[] guardLoc = findGard(input);
            int totalVisited = 0;
            while(guardLoc[0]-yDirection>=0 && guardLoc[1]+xDirection>=0 && guardLoc[0]-yDirection<lines && guardLoc[1]+xDirection<columns){
                if(input[guardLoc[0]-yDirection].charAt(guardLoc[1]+xDirection)!='#'){
                    totalVisited+=putMark(input, guardLoc);
                    guardLoc[0]-=yDirection;
                    guardLoc[1]+=xDirection;
                }else
                    turnRight();
            }
            totalVisited+=putMark(input, guardLoc);
            //for(String l : input){
            //    System.err.println(l);
            //}
            System.err.println("Total Visited : "+totalVisited);
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }
    private static void turnRight(){
        if(yDirection!=0){
            xDirection = yDirection;
            yDirection = 0;
        } else{
            yDirection -= xDirection;
            xDirection = 0;
        }
    }

    public static int putMark(String[] input, int[] guardLoc){
        String currentLine = input[guardLoc[0]];
        if(currentLine.charAt(guardLoc[1])!='X'){
            input[guardLoc[0]] = currentLine.substring(0, guardLoc[1]) + 'X' + currentLine.substring(guardLoc[1] + 1);
            return 1;
        }
        return 0;
    }

    public static int[] findGard(String[] input) {
        for (int i = 0; i < input.length; i++) {
            String current = input[i];
            int j = current.indexOf('^');
            if (j != -1) {
                return new int[]{i, j};
            }
        }
        throw new IllegalArgumentException("guard not found.");
    }
}
