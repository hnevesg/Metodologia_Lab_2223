import java.util.ArrayList;

public class solutionObject {
    private int[] optimalSol; 
    private double optimalLiters=0;
    private ArrayList<Cow> cows; 
    private double maxMeters;
    private double minLiters;
    private int[] currentSol;
    boolean b;
    int counter=0;
    
    public solutionObject(ArrayList<Cow> cows, double maxMeters, double minLiters) {
        optimalSol = new int[cows.size()];
        this.optimalLiters = 0;
        this.cows=cows;
        this.maxMeters = maxMeters;
        this.minLiters = minLiters;
        currentSol=new int[cows.size()];
        for (int i = 0; i < cows.size(); i++)
        	currentSol[i] = 0;
        this.b=true;
    }
    
    /**********************************************************************
	* Method name: firstCase
	*
	* Description of the Method: The method receives the list of cows and when it finds a solution, it returns it.
	*
	* Calling arguments: int level, the current level of the space-state tree;
	* 					 double total_liters, the current liters accumulated by all cows that are part of the solution;
	* 					 double total_space, the current space occupied by all cows that are part of the solution
	* 
	* Return value: int[], the first solution found
	* 
	*********************************************************************/
    
    public int[] firstCase(int level, double total_liters, double total_space){
    	if (level == cows.size()) { // Completion condition
	    	if(total_liters>=minLiters & b) {
	    		optimalLiters=total_liters;
	    		for(int i=0; i<cows.size();i++) { 
					 optimalSol[i] = currentSol[i];
			    }
	    		b=false;
	    	}
	    }
	    else{
	      if (level < cows.size()) {  
	    		if (total_space + cows.get(level).space <= maxMeters){ // Feasibility condition
	    			currentSol[level] = 1;
	    			firstCase(level + 1, total_liters+cows.get(level).getMilk(),total_space + cows.get(level).space);
	    		}
	    		currentSol[level] = 0;
	    		firstCase(level + 1, total_liters,total_space);
	    		
	      }
	    }
		return optimalSol;
	  }
    
    /**********************************************************************
	* Method name: allCases
	*
	* Description of the Method: The method receives the list of cows and when it finds a solution, increases the counter.
	*
	* Calling arguments: int level, the current level of the space-state tree;
	* 					 double total_liters, the current liters accumulated by all cows that are part of the solution;
	* 					 double total_space, the current space occupied by all cows that are part of the solution
	* 
	* Return value: int, the total number of solutions found
	* 
	*********************************************************************/
    
	public int allCases(int level, double total_liters, double total_space){
		if (level == cows.size()) { // Completion condition
	    	if(total_liters>=minLiters) {
	    		  counter++; 
	    	}
	    }
	    else{
	      if (level < cows.size()) { //check 
	    		if (total_space + cows.get(level).space <= maxMeters){   // Feasibility condition
	    			currentSol[level]= 1;
	    			allCases(level + 1, total_liters+cows.get(level).getMilk(),total_space + cows.get(level).space);
	    		}
   			currentSol[level]= 0;
   			allCases(level + 1, total_liters,total_space);
   				      }
	    }
		return counter;
	  }
    
	/**********************************************************************
	* Method name: bestCase
	*
	* Description of the Method: The method receives the list of cows and finds all solutions.
	* 							 For each solution, it checks if the found solution is the best, 
	* 							 and if so, is stored in optimalSol, along with the liters produced.
	*
	* Calling arguments: int level, the current level of the space-state tree;
	* 					 double current_space, the current space occupied by all cows that are part of the solution
	* 
	* Return value: int[], the best solution found
	* 
	*********************************************************************/
	
    public int[] bestCase(int level, double current_space){
		if (level == cows.size()) { // Completion condition
			double currentLiters=0;
			for(int i=0; i<cows.size();i++) { //profit of a specific solution
				currentLiters += currentSol[i]*cows.get(i).getMilk();
	    	}
	    	if (currentLiters>optimalLiters){
	    		optimalLiters=currentLiters;
				for(int i=0; i<cows.size();i++) { //if profit of a specific solution is better than the previous
					 optimalSol[i] = currentSol[i];
			    }
	    	}
	    	
	    }else{
	    	if (level < cows.size()) { 
	    		if (current_space + cows.get(level).space <= maxMeters){   // Feasibility condition
	    			currentSol[level] = 1;
	    			bestCase(level + 1, current_space + cows.get(level).space);
	    		}
    			currentSol[level] = 0;
	    		bestCase(level + 1, current_space);
   			}
	    }
		return optimalSol;
	}
    
    @Override
    public String toString() {
        return ", Total Liters: " + optimalLiters + "\n";
    }
}

