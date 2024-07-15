import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.StringTokenizer;


public class Main {
	static Scanner keyboard = new Scanner (System.in);

	public static void main(String[]args) {
		ArrayList<Cow> cows = new ArrayList<Cow>();
		ArrayList<Cow> milk = new ArrayList<Cow>();
		ArrayList<Cow> food = new ArrayList<Cow>();
		
		cows = readCSV("src/lab3/Cows.csv");
		double sqr_meters=askMeters();
		
		milk = maximizeMilk(cows, sqr_meters);
		showResult(milk, "first", "by milk production");

		milk = bySpace(cows, sqr_meters);
		showResult(milk, "first", "by space occupation");

		food = minimizeFeed(cows, sqr_meters);
		showResult(food, "second", "by food consumption");
	}	
	
	/**********************************************************************
	* Method name: readCSV
	*
	* Description of the Method: The method reads the content of the file with all cows and separates 
	* 							 the id, space, food, and milk, allowing to store these values for objects' attributes
	*
	* Calling arguments: String file, it is the address where the file is located in the pc
	*
	* Return value: ArrayList<Cow>, the data of the file stored in a list
	*
	* Required Files: The method expects a csv file to be read
	*
	* FileNotFoundException: In case the file does not exist or it is not in that location
	* IOException: In case there is an error during an input-output operation (when opening the file)
	* 
	*********************************************************************/
	
	public static ArrayList<Cow> readCSV(String file) {
			File f = new File(file);
			BufferedReader br;
			String next_line;
			ArrayList<Cow> cows_list = new ArrayList<Cow>();
			
			try {
				
				 br = new BufferedReader(new FileReader(f));
				//Keeps repeating until there is no more information left in the file to read
				while((next_line=br.readLine())!=null) {
					//For saving all attributes
			StringTokenizer tokenizer = new StringTokenizer(next_line,";"); 
					//String [] array = next_line.split(";",4); 
					int id = Integer.valueOf(tokenizer.nextToken());
					double space = Double.valueOf(tokenizer.nextToken());
					double food = Double.valueOf(tokenizer.nextToken());
					double milk = Double.valueOf(tokenizer.nextToken());

					cows_list.add(new Cow(id,space,food,milk));
				}
				br.close();
				
			} catch (FileNotFoundException e) {
				System.out.println("Error. The file wasn't found");
			} catch (IOException io) {
				System.out.println("Error opening the file");
			}
		return cows_list;
	}

	/************************
	* Method name: maximizeMilk
	*
	* Description of the Method: The method sorts the cows by milk production in decreasing order
	* 							 and checks how many cows fit on the farmer's property
	*
	* Calling arguments: ArrayList<Cow> cows, the initial list of all cows;
	* 					 double sqr_meters, the space in the farmer's property
	* 
	* Return value: ArrayList<Cow>, the solution 
	* 				(a list with all cows that produce more milk that fit in the farmer's property)
	*
	***********************/
	
	public static ArrayList<Cow> maximizeMilk(ArrayList<Cow> cows, double sqr_meters) { 
		ArrayList<Cow> solution = new ArrayList<Cow>();
		double total_space = 0;
		
		Collections.sort(cows, new Comparator<Cow>() {
				public int compare(Cow o1, Cow o2) {
					return  (int) (o2.getMilk()-o1.getMilk());
					
				}
		    }); 
		
		for(int i=0; i < cows.size(); i++) {
			if(sqr_meters >=  total_space + cows.get(i).getSpace()) {
				total_space+=cows.get(i).getSpace();
				solution.add(cows.get(i));
			}
			if(total_space == sqr_meters) { break;} 
		}
		 
		 return solution;
	}
	
	/*********************
	* Method name: bySpace
	*
	* Description of the Method: The method sorts the cows by space in increasing order
	* 							 and checks how many cows fit on the farmer's property
	*
	* Calling arguments: ArrayList<Cow> cows, the initial list of all cows;
	* 					 double sqr_meters, the space in the farmer's property
	* 
	* Return value: ArrayList<Cow>, the solution 
	*
	***********************/
	
	public static ArrayList<Cow> bySpace(ArrayList<Cow> cows, double sqr_meters) { 
		ArrayList<Cow> solution = new ArrayList<Cow>();
		double total_space = 0;
		
		Collections.sort(cows, new Comparator<Cow>() {
				public int compare(Cow o1, Cow o2) {
					return  (int) (o1.getSpace()-o2.getSpace());
					
				}
		    }); 
		
		for(int i=0; i < cows.size(); i++) {
			if(sqr_meters >=  total_space + cows.get(i).getSpace()) {
				total_space+=cows.get(i).getSpace();
				solution.add(cows.get(i));
			}
			if(total_space == sqr_meters) { break;} 
		}
		 
		 return solution;
	}
	
	/************************
	* Method name: minimizeFeed
	*
	* Description of the Method: The method sorts the cows by food consumption in increasing order
	* 							 and checks how many cows fit on the farmer's property
	*
	* Calling arguments: ArrayList<Cow> cows, the initial list of all cows;
	* 					 double sqr_meters, the space in the farmer's property
	* 
	* Return value: ArrayList<Cow>, the solution 
	* 				(a list with all cows that consume less food that fit in the farmer's property)
	*
	***********************/
	
	public static ArrayList<Cow> minimizeFeed(ArrayList<Cow> cows, double sqr_meters) {
		ArrayList<Cow> solution = new ArrayList<Cow>();
		double total_space = 0;

		Collections.sort(cows, new Comparator<Cow>() {
			public int compare(Cow o1, Cow o2) {
				return  (int) (o1.getFood()-o2.getFood());
				
			}
	    }); 
		
		for(int i=0; i < cows.size(); i++) {
			if(sqr_meters >=  total_space + cows.get(i).getSpace()) {
				total_space+=cows.get(i).getSpace();
				solution.add(cows.get(i));
			}
			if(total_space == sqr_meters) { break;} 
		}
		 
		 return solution;
	}

	/************************
	* Method name: showResult
	*
	* Description of the Method: The method shows by screen the cows that fit on the farmer's property
	*
	* Calling arguments: ArrayList<Cow> solution, the list with all possible cows maximizing milk production
	* 
	* Return value: void
	*
	***********************/
	
	public static void showResult(ArrayList<Cow> solution, String n, String s){
		System.out.println("For the " + n +" case (" + s + "):");
		for(int i = 0; i < solution.size(); i++) {
			System.out.println(solution.get(i).toString());
		}
		System.out.println();
	}
	
	/************************
	* Method name: askMeters
	*
	* Description of the Method: The method receives via stdin the square meters of the farmer's property
	*
	* Calling arguments: none
	* 
	* Return value: double, a valid (numeric) value introduced by the user
	*
	* InputMismatchException: In case a different data type from int is introduced
	* IndexOutOfBoundsException: In case the value added is smaller or equal than 0 
	* 
	***********************/

	public static double askMeters() {
		boolean result=false;
		double option = 0;
		
		System.out.println("Please introduce the total square meters of your property:");
		
		do {
			try {
				option=keyboard.nextDouble();
				if(option<=0) throw new IndexOutOfBoundsException();
				result=false;
			}
			catch(InputMismatchException y){ 
				System.out.println("Error, letters and symbols are not allowed. Try again:");
				keyboard.next();
				result=true;
			}
			catch(IndexOutOfBoundsException z) {
				System.out.println("Error, the number must be positive. Try again:");
				result=true;
			}
		}while(result);
		
	return option;
	}
}
