public class Cow implements Comparable<Cow> {
	
	int id;
	double space;
	double food;
	double milk;	
	
	public Cow(int id, double space, double food, double milk) {
		this.id = id;
		this.space = space/100;
		this.food = food;
		this.milk = milk;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getSpace() {
		return space;
	}
	public void setSpace(double space) {
		this.space = space;
	}
	public double getFood() {
		return food;
	}
	public void setFood(double food) {
		this.food = food;
	}
	public double getMilk() {
		return milk;
	}
	public void setMilk(double milk) {
		this.milk = milk;
	}

	@Override
	public String toString() {
		return "Cow id: " + id + ", space=" + space + " m^2, food=" + food + ", milk=" + milk;
	}

	@Override
	public int compareTo(Cow c) {
		return 0;
	}
}