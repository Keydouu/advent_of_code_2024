import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class D3P2{
    private static final String REGEX = "mul\\((\\d+),(\\d+)\\)";
    public static void main(String[] args) {
        String filePath = "day3/input.txt";

        try {
            String input = new String(Files.readAllBytes(Paths.get(filePath)));
            long sum =0;

            for(String doPart : input.split("do\\(\\)")){
                sum+=parseString(doPart.split("don't\\(\\)")[0]);
            }
            System.out.println("Total sum of all mult(X,Y) products: " + sum);

        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }
    private static long parseString(String input){
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(input);
        long sum = 0;
        while (matcher.find()) {
            String xStr = matcher.group(1);
            String yStr = matcher.group(2);
            int x = Integer.parseInt(xStr);
            int y = Integer.parseInt(yStr);
            sum += x * y;
        }
        return sum;
    }
}
