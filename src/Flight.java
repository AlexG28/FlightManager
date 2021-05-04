import java.util.*;
/* Flight reservation project 
 * By: Aleksandr Gyumushyan
 * ID: 501018761
 * Date: 04-16-2021
 * 
 */
public class Flight
{
	public enum Status {DELAYED, ONTIME, ARRIVED, INFLIGHT};
	public static enum FlightType {SHORTHAUL, MEDIUMHAUL, LONGHAUL};
	
	String flightNum;
	String airline;
	String origin, dest;
	String departureTime;
	Status status; // see enum Status above. google this to see how to use it
	FlightType type;
	int flightDuration;
	Aircraft aircraft;
	protected int passengers; // count of (economy) passengers on this flight - initially 0
	protected ArrayList<Passenger> manifest = new ArrayList<Passenger>();
	protected TreeMap<String, Passenger> seatMap = new TreeMap<String, Passenger>();
	Random random = new Random();
	protected int columnLength = 0;

	
	public Flight()
	{
		// write code to initialize instance variables to default values
	}
	
	public Flight(String flightNum, String airline, String dest, String departure, int flightDuration, Aircraft aircraft)
	{
		this.flightNum = flightNum;
		this.airline = airline;
		this.dest = dest;
		this.origin = "Toronto";
		this.departureTime = departure;
		this.flightDuration = flightDuration;
		this.aircraft = aircraft;
		passengers = 0;
		status = Status.ONTIME;
		this.type = FlightType.MEDIUMHAUL; // default value
		
		this.dest = dest.replace('_', ' ');
		
	}
	public void setFlightType(FlightType type)
	{
		this.type = type;
	}
	public String getFlightNum()
	{
		return flightNum;
	}
	public void setFlightNum(String flightNum)
	{
		this.flightNum = flightNum;
	}
	public String getAirline()
	{
		return airline;
	}
	public void setAirline(String airline)
	{
		this.airline = airline;
	}
	public String getOrigin()
	{
		return origin;
	}
	public void setOrigin(String origin)
	{
		this.origin = origin;
	}
	public String getDest()
	{
		return dest;
	}
	public void setDest(String dest)
	{
		this.dest = dest;
	}
	public String getDepartureTime()
	{
		return departureTime;
	}
	public void setDepartureTime(String departureTime)
	{
		this.departureTime = departureTime;
	}
	
	public Status getStatus()
	{
		return status;
	}
	public void setStatus(Status status)
	{
		this.status = status;
	}
	public int getFlightDuration()
	{
		return flightDuration;
	}
	public void setFlightDuration(int dur)
	{
		this.flightDuration = dur;
	}
	
	public int getPassengers()
	{
		return passengers;
	}
	public void setPassengers(int passengers)
	{
		this.passengers = passengers;
	}
	
	//loop through all passengers, and if it finds a duplicate, throw DuplicatePassenger Exception
	public void checkForDuplicate(Passenger p) 
	throws DuplicatePassenger
	{
		for (int i = 0; i < manifest.size(); i++) {
			if (manifest.get(i).compareTo(p) == true) {
				throw new DuplicatePassenger("Duplicate passenger " + p.getName() + " " + p.getPassportNumber());
			}
		}		
	}
	
	public Passenger getPassenger(String name, String passport) 
	throws PassengerNotInManifest
	{
		for (int i = 0; i < manifest.size(); i++) {
			if (manifest.get(i).getName().equals(name) && manifest.get(i).getPassportNumber().equals(passport)) {
				return manifest.get(i);
			}
		}
		throw new PassengerNotInManifest("This passenger is not on this flight ");
	}
	
	public void reserveSeat(Passenger p, String seat) 
		throws DuplicatePassenger, SeatOccupied
		{
		
		checkForDuplicate(p);
		//checks to make sure this seat actually exists
		if (this.aircraft.checkForValidSeat(seat) == false) {
			throw new SeatOccupied("Invalid seat");
		}
		
		//checks if this seat is occupied, and if it is, throws exception
		if (this.seatMap.containsKey(seat)) {
			throw new SeatOccupied("Seat " + seat + " is already occupied");
		}
		
		
		// if no exceptions, then increase passenger count and add this passenger to the respective lists 
		this.passengers++;
		this.manifest.add(p);
		this.seatMap.put(seat, p);	
	}
	
	
	public void cancelSeat(Passenger p) 
	{		
		// removes passenger 
		String seat = p.getSeatNumber();		
		this.seatMap.remove(seat);				
		this.manifest.remove(p);		
	}
	

	// returns the flightType of this flight
	public FlightType getFlightType() {
		return this.type;
	}
	

	
	public String toString() // return this flights data 
	{
		 return airline + "\t Flight:  " + flightNum + "\t Dest: " + dest + "\t Departing: " + departureTime + "\t Duration: " + flightDuration + "\t Status: " + status;
		
	}
	// returns a string of all the passengers on this flight 
	public void printPassengerManifest() {
		
		if (this.manifest.size() == 0) { // if there are no passengers, print the respective message
			System.out.println("There are no passengers on this flight");
		} else {
			String output = "";
			// for each passenger, add their toString to output
			for (Passenger p : this.manifest) {
				output += p.toString();
			}
			
			System.out.println(output);
		}
	}
	
	//prints the array of seats 
	public void printSeats() {
		//stores the array and the string for the output
		String output = "";
		String[][] seats = this.aircraft.getSeatLayout();
		
		// nested loop to loop through the matrix 
		for (int i = 0; i < seats.length; i++) {
			for (int j = 0; j < seats[0].length; j++) {
				
				if (seatMap.keySet().contains(seats[i][j])) {
					output += "XX" + " "; // if the seat is already taken, add XX
				} else {
					// if the seat is not taken, add the actual seat 
					output += seats[i][j] + " ";
				}
				
			} // go down by one line
			output += "\n";
		}
		
		output += "\n";
		output += "'XX' = Occupied           '+' = First Class";
		System.out.println(output);
	}
	
	//exception classes 
	static class DuplicatePassenger extends Exception
	  {
	    public DuplicatePassenger(String message)
	    {
	      super(message);
	    }
	  }
	  
	static class SeatOccupied extends Exception
	  {
	    public SeatOccupied(String message)
	    {
	      super(message);
	    }
	  } 
	
	static class PassengerNotInManifest extends Exception
	  {
	    public PassengerNotInManifest(String message)
	    {
	      super(message);
	    }
	  }
  
}
