import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Day6Part2 {
    private static int xDirection = 0;// 1 Right -1 Left
    private static int yDirection = 1;// 1 UP  -1 Down
    private static int[] guardLoc;

    public static int lines, columns, xObstacle =-1, yObstacle =-1;

    public static void main(String[] args) {
        String filePath = "day6/input.sql";
        try {
            String[] input = new String(Files.readAllBytes(Paths.get(filePath))).split("\n");
            lines = input.length;
            columns = input[0].length();

            ArrayList<String> workingObstaclePos = new ArrayList<>();//Array of correct answers

            ArrayList<String> pastPosAndDirections = new ArrayList<>();//True history

            ArrayList<String> badObstaclePos = new ArrayList<>();//obstacles can't be here

            ArrayList<String> theoricPosAndDirections=new ArrayList<>();

            boolean isLooping=false;

            guardLoc = findGard(input);
            badObstaclePos.add(getCurrentPos());
            while(isWithinTheMap()){
                //while not out
                if (!isObstacleUsed()){
                    theoricPosAndDirections.clear();
                    if (input[guardLoc[0]-yDirection].charAt(guardLoc[1]+xDirection)!='#'){//if next step isn't blocked
                        String nextPos = posToString( guardLoc[1]+xDirection, guardLoc[0]-yDirection);
                        if(!(workingObstaclePos.contains(nextPos)||badObstaclePos.contains(nextPos))){
                            //if you can try putting the obstacke ahead, do it
                            isLooping=false;
                            setObstacle(guardLoc[1]+xDirection, guardLoc[0]-yDirection);
                        }
                        else{//if you can't, moveNormal
                            guardLoc[0] -= yDirection;
                            guardLoc[1] += xDirection;
                            badObstaclePos.add(getCurrentPos());
                        }
                    }else
                        turnRight();
                    pastPosAndDirections.add(getFullStatus());//if you go back to one of thoses, then you're in a loop
                }
                if (isObstacleUsed()){
                    int[] callBackData = {guardLoc[0], guardLoc[1], xDirection, yDirection};
                    //what if scenario
                    while(isWithinTheMap()&&(!isLooping)){
                        // first
                        if((input[guardLoc[0]-yDirection].charAt(guardLoc[1]+xDirection)!='#')&&
                                ((guardLoc[0]-yDirection!=yObstacle) || (guardLoc[1]+xDirection!=xObstacle))){//if next step isn't blocked
                            guardLoc[0]-=yDirection;
                            guardLoc[1]+=xDirection;
                        }else{
                            turnRight();
                        }
                        //Now we check if we're in a loop
                        if(theoricPosAndDirections.contains(getFullStatus())||pastPosAndDirections.contains(getFullStatus()))
                            isLooping=true;
                        else
                            theoricPosAndDirections.add(getFullStatus());
                    }

                    // maybe I can save tracks that are garanteed not to loop ?
                    // this was, as long the new obstacle doesn't block them, I'll be able
                    // to predict loop / not loop way earlier
                    // but this will also add too many loops and if checks, which may slow down the global process
                    if (isLooping)
                        workingObstaclePos.add(posToString(xObstacle, yObstacle));
                    else
                        badObstaclePos.add(posToString(xObstacle, yObstacle));
                    resetObstacle();
                    guardLoc[0]=callBackData[0];//return to previous guardLoc
                    guardLoc[1]=callBackData[1];
                    xDirection=callBackData[2];
                    yDirection=callBackData[3];
                }
            }
            //for(String l : input){
            //    System.err.println(l);
            //}
            System.err.println("Total working Obstacles : "+workingObstaclePos.size());
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

    public static String getFullStatus(){
        return getCurrentPos()+String.valueOf(xDirection)+yDirection;
    }
    public static String getCurrentPos(){
        return guardLoc[1]+"|"+guardLoc[0];
    }
    public static String posToString(int x, int y){
        return x+"|"+y;
    }

    public static void resetObstacle(){
        xObstacle=-1;
        yObstacle=-1;
    }
    public static void setObstacle(int x,int y){
        xObstacle=x;
        yObstacle=y;
    }
    public static boolean isObstacleUsed(){
        return (xObstacle!=-1);
    }
    public static boolean isWithinTheMap(){
        return (guardLoc[0]-yDirection>=0 && guardLoc[1]+xDirection>=0 && guardLoc[0]-yDirection<lines && guardLoc[1]+xDirection<columns);
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
