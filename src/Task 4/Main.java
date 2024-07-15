import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.StringTokenizer;


public class Main {

	static Scanner keyboard = new Scanner (System.in);

	public static void main(String[]args) {
		ArrayList<Cow> cows = new ArrayList<Cow>();
		cows = readCSV("src/lab4/Cows.csv");
		
		double sqr_meters=askValue("Please introduce the total square meters of your property:");
		double liters=askValue("Please introduce the least liters required:");
		
		int[] best = new int[cows.size()];

		solutionObject s=new solutionObject(cows, sqr_meters,liters);
		
		System.out.println("For the decision problem:");
		best=s.firstCase(0, 0, 0);
		showList(best);
		System.out.print(s.toString());
		
		int counter=s.allCases(0,0,0);
		if(counter!=0) {
			System.out.println("\nFor the enumeration problem there are " + counter + " solutions");
		}else {
			System.out.println("\nNo possible solution for that liters constraint...");
		}

		System.out.println("\nFor the pure optimization problem:");
		best=s.bestCase(0,0);
		showList(best);
		System.out.print(s.toString());

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

		/**********************************************************************
		* Method name: showList
		*
		* Description of the Method: The method receives via stdin the liters production required by the farmer
		*
		* Calling arguments: int[] solution, the array containing the solution to the problem
		* 
		* Return value: void
		* 
		*********************************************************************/
	
	public static void showList(int[] solution) {
		for(int i=0;i<solution.length;i++)
			System.out.print(solution[i]+" ");
	}

		/**********************************************************************
		* Method name: askValue
		*
		* Description of the Method: The method receives via stdin the liters production required by the farmer
		*
		* Calling arguments: String s, the value we ask for
		* 
		* Return value: double, a valid (numeric) value introduced by the user
		*
		* InputMismatchException: In case a different data type from int is introduced
		* IndexOutOfBoundsException: In case the value added is smaller or equal than 0 
		* 
		*********************************************************************/
	 
	public static double askValue(String s) {
		boolean result=false;
		double option = 0;
		
		System.out.println(s);
		
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
