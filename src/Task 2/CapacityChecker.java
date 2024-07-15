

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

public class CapacityChecker{
	
	final static Scanner keyboard = new Scanner(System.in);
	
	public static void main(String[]args) throws IOException {
		String file = "cars_dataset.csv";
		List<Car> cars;
		int points;

		cars = readF(file);
		System.out.println("Please introduce the number of points of interest (10 at most):");
		points=readPoints();
		double[] total_distance = generateKMs(points);
		quicksort(total_distance);
		afterSort(total_distance,cars);
	}
	
	/************************
	* Method name: readF
	*
	* Description of the Method: The method reads the content of the file with all Cars and separates 
	* 							 the attributes to store them. Being these: model,fuel_type,n_seats,transmission_type,tank_capacity,average_consumption
	*
	* Calling arguments: String file, it is the address where the file is located in the pc
	*
	* Return value: List with all Cars
	*
	* Required Files: The method expects a file to be read
	*
	* FileNotFoundException: In case the file does not exist or it is not in that location
	* IOException: In case there is an error during an input-output operation (when opening the file)
	* 
	***********************/

	public static List<Car> readF(String file) throws IOException {
		File f = new File(file);
		BufferedReader br;
		String str;
		List<Car> l = new Vector<Car>();

		try {
			
			 br = new BufferedReader(new FileReader(f));
			 str = br.readLine();
			//Keeps repeating until there is no more information left in the file to read
			while((str=br.readLine())!=null) {
				//For saving all attributes:
				String [] array = str.split(",",6);
				String model = array[0].toLowerCase();
				String fuel_type = array[1].toLowerCase();
				double n_seats= Double.valueOf(array[2]);
				String transmission_type = array[3].toLowerCase();
				double tank_capacity = Double.valueOf(array[4]);
				double average_consumption = Double.valueOf(array[5]);
				l.add(new Car(model,fuel_type,n_seats,transmission_type,tank_capacity,average_consumption));
			}
			br.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("Error. The file wasn't found");
		}
		return l;	
	}
	
	/************************
	* Method name: readPoints
	*
	* Description of the Method: The method receives via stdin a certain number of POI's
	*
	* Calling arguments: none
	* 
	* Return value: Number of POI's (Points Of Interest)
	*
	* InputMismatchException: In case a different data type is added
	* IndexOutOfBoundsException: In case the value added is smaller than 0 
	* 
	***********************/

	public static int readPoints() {
			boolean result=false;
			int option=0;
			do {
			try {
					option=keyboard.nextInt();
					if(option<0) throw new IndexOutOfBoundsException();
					result=false;
				}
				catch(InputMismatchException y){ 
					System.out.println("Error, letters and symbols are not allowed. Try again:");
					keyboard.next();
					result=true;
				}
				catch(IndexOutOfBoundsException z) {
					System.out.println("Error, the number must be 0 or positive. Try again:");
					result=true;
				}
			}while(result);
			return option;
	}
	
	/************************
	* Method name: printNotPossible
	*
	* Description of the Method: The method prints the cars that cannot complete the trip
	* 
	* Calling arguments: List<Car> cars, the list of cars
	* 
	* Return value: none
	***********************/
	
	public static void printNotPossible(List<Car> list) {
		if(list!=null) {
			for(int i=0;i<list.size();i++) {
				System.out.println("The car " + list.get(i).getModel() + " will not complete the trip. Tank capacity: " + list.get(i).getTank_capacity());
			}
		}
	}
		
	/************************
	* Method name: generateKMs
	*
	* Description of the Method: The method randomly generates a certain set of values
	* 							 that are the km's in between one POI and another
	*
	* Calling arguments: number of POI's
	* 
	* Return value: array of distances
	*
	* IndexOutOfBoundsException: In case the value entered is bigger than 10 
	*
	***********************/
	
	public static double[] generateKMs(int POIs) {
		Random rd = new Random();
		double total[]=new double[10];
		
		System.out.println("Distance in kms of the route");
		
		try {
		for(int i=0;i<POIs;i++) {
			double actual = Math.abs(rd.nextInt(150));
			total[i]=actual;
			System.out.println("["+ i +" --> " + ++i +"]: " + actual);
			--i;
		}
		}catch(IndexOutOfBoundsException e) {
			System.out.println("Too many POIs, just 10 taken into account");
		}
		return total;
	}
	
	/************************
	* Method name: afterSort
	*
	* Description of the Method: The method checks if the cars can complete the whole route after the array of distances has already been sorted. 
	* 							 And if so, prints them in increasing order of fuel consumption.
	* 							  
	* Calling arguments: double[] array, the array of distances; 
	* 					 List<Car> cars, the list of cars
	* 
	* Return value: none
	* 
	***********************/
	
	public static void afterSort(double[] POIs, List<Car> cars){
		int size=POIs.length;
		List<Car> not_enough_fuel = new Vector<Car>();
		double consumption=0;
		
		for(int i=0; i<size;i++) {			
			for(int j=0;j<cars.size();j++) {
				consumption = cars.get(j).getConsumption(); // calculates the kms each car can do with the tank full
				if(consumption > POIs[i]) { 
					cars.get(j).setConsumption(consumption-POIs[i]); 
					//if the car can do this part of the route, the consumption (in kms) is the current capacity - kms of this part of route					
				}else{
					not_enough_fuel.add(cars.remove(j));
				}
			}
		}

		Collections.sort(cars); //sorts the list based on the compareTo method from Car class
		
		for(int n=0;n<cars.size();n++) {
		 System.out.printf("Total consumption for the car " + cars.get(n).getModel() + ": " + "%.2f (Tank capacity: " 
			+ cars.get(n).getTank_capacity() + ")\n", (cars.get(n).getTank_capacity() - (cars.get(n).getConsumption() * cars.get(n).getAverage_consumption() / 100)));
		 	//Tank capacity - remaining fuel = fuel consumed in the trip
		}
		
		printNotPossible(not_enough_fuel);

	}

	/************************
	* Method name:  quicksort
	*
	* Description of the Method: The method calls the recursive implementation of the quicksort algorithm
	* 							 for sorting distances from lower to higher
	* 
	*  Calling arguments: double[] array, the array of distances
	* 
	* Return value: none
	* 
	***********************/
	
	public static void quicksort(double[] array) {
		quicksortRec(array, 0, array.length-1);
	}

	/************************
	* Method name: quicksortRec
	*
	* Description of the Method: The method reads the content of the file with all Cars and separates 
	* 							 the attributes to store them. Being these: model,fuel_type,n_seats,transmission_type,tank_capacity,average_consumption
	*
	* Calling arguments: double[] array, the array of distances; 
	* 					 int left, the first element of the subarray;
	* 					 int right, the last element of the subarray
	*
	*
	* Return value: none
	* 
	***********************/
	
	public static void quicksortRec(double[] array, int left, int right) {
		if (left < right) {
			int p = partition(array, left, right);
			quicksortRec (array, left, p-1);
			quicksortRec (array, p + 1, right);
		}
	}
	
	/************************
	* Method name: partition
	*
	* Description of the Method: The method calculates a pivot, and sorts the array based on it
	*
	* Calling arguments: double[] a, the array of distances; 
	* 					 int left, the first element of the subarray;
	* 					 int right, the last element of the subarray
	*
	* Return value: The position of the pivot
	* 
	***********************/
	
	public	static int partition (double[] a, int left, int right) {
		int l = left, r = right;
		double p=a[left], aux;
		while (l < r) {
			while ((l < right && a[++l] < p) && (r > left && p < a[r]));
				if (l < r) {
					aux= a[l]; a[l] = a[r]; a[r] = aux;
				}
		}
		a[left] = a[r];
		a[r] = p;
		return r;
	}

}
