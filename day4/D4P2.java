import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class D4P2 {
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
            for (int i = 0; i < input.length; i++) {
                String current = input[i];
                for (int j = 0; j < current.length(); j++) {
                    if (current.charAt(j) == 'A')
                        xmasFound+=checkIfXmas(input, i, j);
                }
            }
            System.err.println("xmas found : "+xmasFound);
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }
    private static int checkIfXmas(String[] input,int currentX, int currentY){
        if ((currentX==lines-1)||(currentY==columns-1)||(currentX==0)||(currentY==0))
            return 0;

        boolean d1 = (unitXcheck(input,currentX, currentY, 1, 1) || unitXcheck(input,currentX, currentY, -1, -1));
        boolean d2 = (unitXcheck(input,currentX, currentY, 1, -1) || unitXcheck(input,currentX, currentY, -1, 1));
        if (d1&&d2)
            return 1;
        return 0;
    }
    private static boolean unitXcheck(String[] input,int currentX, int currentY, int xInc, int yInc){
        return (input[currentX+xInc].charAt(currentY+yInc) == 'M' && input[currentX-xInc].charAt(currentY-yInc)=='S');
    }
}
