import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class D4 {
    private static final String STRAIGHT = "MAS";
    private static final String REVERSE = "AMX";
    private static int lines;
    private static int columns;
    public static void main(String[] args) {
        String filePath = "day4/input.txt";
        //String filePath = "day4/smallInput.txt";

        try {
            String[] input = new String(Files.readAllBytes(Paths.get(filePath))).split("\n");
            lines = input.length;
            columns = input[0].length();
            long xmasFound = 0;
            xmasFound+= xmasFinder(input, 1, 0);
            xmasFound+= xmasFinder(input, 0, 1);
            xmasFound+= xmasFinder(input, 1, 1);
            xmasFound+= xmasFinder(input, 1, -1);
            System.err.println("xmas found : "+xmasFound);
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }
    private static long xmasFinder(String[] input, int xIncrement, int yIncrement){
        long xmasFound = 0;
        for (int i = 0; i < input.length; i++) {
            String current = input[i];
            for (int j = 0; j < current.length(); j++) {
                if (current.charAt(j) == 'X') {
                    xmasFound+=checkIfXmas(input, STRAIGHT, i, j, xIncrement, yIncrement);
                } else if (current.charAt(j) == 'S') {
                    xmasFound+=checkIfXmas(input, REVERSE, i, j, xIncrement, yIncrement);
                } 
            }
        }
        return xmasFound;
    }
    private static int checkIfXmas(String[] input, String target, int currentX, int currentY,int xIncrement, int yIncrement){
        if (target.length()==0)
            return 1;
        int nextX = currentX+xIncrement;
        int nextY = currentY+yIncrement;
        if ((nextX==lines)||(nextY==columns)||(nextX==-1)||(nextY==-1))
            return 0;
        if (input[nextX].charAt(nextY) == target.charAt(0))
            return checkIfXmas(input, target.substring(1), nextX, nextY, xIncrement, yIncrement);
        return 0;
    }
}
