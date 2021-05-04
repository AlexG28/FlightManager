/* Flight reservation project 
 * By: Aleksandr Gyumushyan
 * ID: 501018761
 * Date: 04-16-2021
 * 
 */
public class Aircraft implements Comparable<Aircraft>  
{
  int numEconomySeats;
  int numFirstClassSeats;
  
  // seat layout based on [rows][columns]
  String[][] seatLayout;
  
  String model;
  
  public Aircraft(int seats, String model)
  {
  	this.numEconomySeats = seats;
  	this.numFirstClassSeats = 0;
  	this.makeLayout(seats, 0);
  	this.model = model;
  }

  public Aircraft(int economy, int firstClass, String model)
  {
  	this.numEconomySeats = economy;
  	this.numFirstClassSeats = firstClass;
  	this.makeLayout(economy, firstClass);
  	this.model = model;
  }
  
  //nested loop through the seatLayout matrix
  // if the seat exists then return true, if not return false 
  public boolean checkForValidSeat(String seat) {
	  for (int i = 0; i < seatLayout.length; i++) {
		  for (int j = 0; j < seatLayout[0].length; j++) {
			  if (seatLayout[i][j].equals(seat)) {
				  return true;
			  }
		  }
		  
	  }
	  return false;
  }
 
  private void makeLayout(int economy, int firstClass) {
	  int rows = 4; // each plane has 4 rows
	  int columns = (economy + firstClass) / 4; // sets the column length
	  this.seatLayout = new String[rows][columns]; // creates the matrix
	  String seat = "";
	  if (firstClass == 0) {
		  // if no first class seats 
		  for (int i = 0; i < seatLayout.length; i++) { // I representds A B C or D 
			  for (int j = 0; j < seatLayout[0].length; j++) { // j represents the length of each isle
				  switch (i) { // based on i, add the right letter to the current isle number
					case 0:
						seat = Integer.toString(j+1) + "A";
						break;
					case 1:
						seat = Integer.toString(j+1) + "B";
						break;
					case 2:
						seat = Integer.toString(j+1) + "C";
						break;
					case 3:
						seat = Integer.toString(j+1) + "D";
						break;
					}
				  // store the seat in the matrix
				  seatLayout[i][j] = seat;
			  }
		  }
		  
	  } else {
		  // if there is a first class 
		  int firstClassLength = firstClass / 4; // how many seats the first class occupies in each isle
		  for (int i = 0; i < seatLayout.length; i++) {
			  for (int j = 0; j < seatLayout[0].length; j++) {
				  switch (i) {
					case 0:
						seat = Integer.toString(j+1) + "A";
						break;
					case 1:
						seat = Integer.toString(j+1) + "B";
						break;
					case 2:
						seat = Integer.toString(j+1) + "C";
						break;
					case 3:
						seat = Integer.toString(j+1) + "D";
						break;
					}
				  if (j < firstClassLength) { // add the first class seats to the front 
					  seat = seat + "+";
				  }
				  
				  seatLayout[i][j] = seat;
			  }
		  }
	  }
   }
  
    //returns the seat layout
    public String[][] getSeatLayout(){
	    return this.seatLayout;
    }
	
	public int getNumSeats()
	{
		return numEconomySeats;
	}
	
	public int getTotalSeats()
	{
		return numEconomySeats + numFirstClassSeats;
	}
	
	public int getNumFirstClassSeats()
	{
		return numFirstClassSeats;
	}

	public String getModel()
	{
		return model;
	}

	public void setModel(String model)
	{
		this.model = model;
	}
	
	public void print()
	{
		System.out.println("Model: " + model + "\t Economy Seats: " + numEconomySeats + "\t First Class Seats: " + numFirstClassSeats);
	}

	/*
	 * Write a compareTo method that is part of the Comparable interface
	 */
	//@Override
	public int compareTo(Aircraft other) {
		// returns the difference between the number of economy seats 
		return this.numEconomySeats - other.numEconomySeats;
	}
}
  
