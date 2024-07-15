
public class Car implements Comparable<Car> {
	private String model;
	private String fuel_type;
	private String transmission_type;
	private double  n_seats;
	private double tank_capacity; 
	private double average_consumption; // in liters of fuel per 100km driven
	private boolean capable;
	private double consumption;
	
	public Car(String model, String fuel_type, double n_seats, String transmission_type, double tank_capacity, double average_consumption) {
		this.model = model;
		this.fuel_type = fuel_type;
		this.transmission_type = transmission_type;
		this.n_seats = n_seats;
		this.tank_capacity = tank_capacity;
		this.average_consumption = average_consumption;
		capable = true;
		consumption = tank_capacity * 100 / average_consumption;
	}

	public double getConsumption() {
		return consumption;
	}

	public void setConsumption(double c) {
		consumption = c;
	}
	
	public boolean getCapable() {
		return capable;
	}

	public void setCapable(boolean c) {
		capable = c;
	}
	
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getFuel_type() {
		return fuel_type;
	}

	public void setFuel_type(String fuel_type) {
		this.fuel_type = fuel_type;
	}

	public String getTransmission_type() {
		return transmission_type;
	}

	public void setTransmission_type(String transmission_type) {
		this.transmission_type = transmission_type;
	}

	public double getN_seats() {
		return n_seats;
	}

	public void setN_seats(double m_seats) {
		this.n_seats = m_seats;
	}

	public double getTank_capacity() {
		return tank_capacity;
	}

	public void setTank_capacity(double tank_capacity) {
		this.tank_capacity = tank_capacity;
	}

	public double getAverage_consumption() {
		return average_consumption;
	}

	public void setAverage_consumption(double average_consumption) {
		this.average_consumption = average_consumption;
	}
	
	@Override
	public String toString() {
		return "Car model=" + model + ", fuel_type=" + fuel_type + ", transmission_type=" + transmission_type
				+ ", m_seats=" + n_seats + ", tank_capacity=" + tank_capacity + ", average_consumption="
				+ average_consumption + '\n';
	}

	/************************
	* Method name: compareTo
	*
	* Description of the Method: The method subtracts to the total tank capacity
	* 							 the liters that remains in the tank, obtaining the total liters consumed in the trip 
	*
	* Calling arguments: Car c, a car to which the instance is going to be compared
	* 
	* Return value: 1 if c has consumed more liters, 0 if both have consumed the same quantity and -1 if c has consumed less liters
	* 
	***********************/
	
	public int compareTo(Car c) {
		int comp=0;
		if ((c.getTank_capacity()-c.getConsumption()*c.getAverage_consumption() / 100) > (this.getTank_capacity()-this.getConsumption()*this.getAverage_consumption() / 100))
	     	comp=-1;
		if ((c.getTank_capacity()-c.getConsumption()*c.getAverage_consumption() / 100) < (this.getTank_capacity()-this.getConsumption()*this.getAverage_consumption() / 100))
			comp=1;
	     return comp;
	}
}
