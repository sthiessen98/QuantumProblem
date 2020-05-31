
import java.io.*;

//****************
//START: READ ONLY
//****************
public class Quantum {
//****************
//END: READ ONLY
//****************

// YOU CAN DEFINE YOUR OWN FUNCTIONS HERE IF YOU REALLY NEED ONE

//****************
//START: READ ONLY
//****************
		
	
    /**     
	 * @param X: The number of buses
     * @return The cost of minimum crossing configuration with X buses
     */
    public static int cost(int X) {
//****************
//END: READ ONLY
//****************
        //Spencer Thiessen
		//WRITE YOUR NSID: SDT747
        //11229984
		int[][] ArrayofArray = new int[X][2];
        int finalCost = placeLine(ArrayofArray, 0, 0, X, 0);
        System.out.println(finalCost);

		return 0;
        //end: write your code here 
	 
		 
		
    }

    /*
    Return the cost a single line has on the overall system
     */
    private static int lineCost(int[][] array, int level){
        if(level < 2){
            return 0;
        }

        int currLevel = level-2;
        int totalCost = 0;
        //Try to draw best bus to each line
        while(currLevel >= 0){
            int min_between_lines = Math.max(array[level][0], array[currLevel][0]);
            int max_between_lines = Math.min(array[level][1], array[currLevel][1]);
            int levelCost = 1000;
            //Try drawing bus between currLevel and Level for each position
            for(int currPosition = min_between_lines; currPosition <= max_between_lines; currPosition++){
                int tempLevelCost = 0;

                //See if lines in between currLevel and level intersect currPosition
                for(int i = currLevel + 1; i < level; i++){
                    if(array[i][0] <= currPosition && array[i][1] >= currPosition){
                        tempLevelCost++;
                    }
                }

                if(tempLevelCost < levelCost){
                    levelCost = tempLevelCost;
                }

            }

            totalCost += levelCost;
            currLevel--;
        }

        return totalCost;
    }

    /*
    Create every possible legal combination of horizontal lines
     */
    private static int placeLine(int[][] someArray, int level, int local_min, int local_max, int total_cost) {

        //If first line, will always be (1, MAX), so put that in and continue with our day
        if( level == 0) {
            someArray[level][0] = 1;
            someArray[level][1] = someArray.length;
            total_cost = placeLine(someArray, level + 1, local_min, local_max, total_cost);

         //If all lines have been created, calculate their cost and return
        }else if(level == someArray.length){

            int limit = someArray.length;
            int total = 0;

            for(int i=0; i < limit; i++){
                total += lineCost(someArray, i);
            }

            return total;

            //Recursive case, add all possible lines
        }else{
            int best_cost = 99999;
            //Length of start of line can be 0 -> local_max
            for(int lineStart = 1; lineStart <= local_max; lineStart++){
                //Length of end of line can be max(local_min, lineStart) -> someArray.length -1
                for(int lineEnd = Math.max(local_min, lineStart + 1); lineEnd <= someArray.length;lineEnd++){
                    someArray[level][0] = lineStart;
                    someArray[level][1] = lineEnd;

                    //Change min/max
                    int new_local_min = Math.max(local_min, lineStart);
                    int new_local_max = Math.min(local_max, lineEnd);

                    total_cost = placeLine(someArray, level + 1, new_local_min, new_local_max, total_cost);

                    //If returned cost is lower than best seen so far, keep it.
                    if(total_cost < best_cost){
                        best_cost = total_cost;
                    }
                }
            }
            return best_cost;
        }
        //Must be full array if makes here

        return total_cost;
    }


//****************
//START: READ ONLY
//****************
    /**
     * Main Function.
     */
    public static void main(String[] args) {

        BufferedReader reader;
        File file = new File("output.txt");
		int X = 0; 
		String line;
        try {
            reader = new BufferedReader(new FileReader("Quantum.txt"));
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));            
            while(true){ 
				line = reader.readLine();
				if(line == null) break;				
				X = Integer.parseInt(line); 
                writer.write(cost(X) + "\n");
				writer.flush();
            } 

            reader.close();
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("Could not locate input file.");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
