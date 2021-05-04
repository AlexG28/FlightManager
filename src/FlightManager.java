import java.io.*;
import java.util.*;
/* Flight reservation project 
 * By: Aleksandr Gyumushyan
 * ID: 501018761
 * Date: 03-25-2021
 * 
 */
import java.util.TreeMap;
/* Flight reservation project 
 * By: Aleksandr Gyumushyan
 * ID: 501018761
 * Date: 04-16-2021
 * 
 */

public class FlightManager
{
  // Contains list of Flights departing from Toronto in a single day
	
	TreeMap<String, Flight> flights = new TreeMap<String, Flight>();
  
  
  // changed cities to a tree map with (name of city) -> (flight duration)
  TreeMap<String, Integer> cities = new TreeMap<String, Integer>();
  
  
  // Contains list of available airplane types and their seat capacity
  ArrayList<Aircraft> airplanes = new ArrayList<Aircraft>();  
  
  
  Random random = new Random(); 
  
  
  public FlightManager()
  {  	
  	// Create some aircraft types with max seat capacities
  	airplanes.add(new Aircraft(85, "Boeing 737"));
  	airplanes.add(new Aircraft(180,"Airbus 320"));
  	airplanes.add(new Aircraft(37, "Dash-8 100"));
  	airplanes.add(new Aircraft(12, "Bombardier 5000"));
  	airplanes.add(new Aircraft(592, 14, "Boeing 747"));
  	
  	// fill the cities map with the actual cites and durations
  	cities.put("Dallas", 3);
  	cities.put("New_York", 1);
  	cities.put("London", 7);
  	cities.put("Paris", 8);
  	cities.put("Tokyo", 16);
  		
   	File file;
   	Scanner fileScanner = null;
  	try {
  		
  	    file = new File("flights.txt"); // open file 
  		fileScanner = new Scanner(file);
  		
  		while (fileScanner.hasNextLine()) {
  			CreateFlight(fileScanner.nextLine()); // for each line create a flight
  		}
  		
  		
  	} catch (FileNotFoundException e) {
  		System.out.println("File not found"); // print error
  	} finally {
  		fileScanner.close();
  	}
  	
  }
  
  private void CreateFlight(String line) {
	  String[] flight = line.split(" "); // split the line by space
	  String airline = flight[0];	 // each array element represents different datapoitns
	  
	  airline = airline.replace('_', ' '); // replace _ with ' ' 
	  String destination = flight[1];
	  String departureTime = flight[2];
	  int capacity = Integer.parseInt(flight[3]); // convert capacity into an int
	  String flightNum = generateFlightNumber(airline);
	  
	  int smallestSeatDelta = 10000;
	  int airplaneIndex = 0;
	  int currentAirplaneSeats = 0;
	  
	  // goes through all airplanes and finds the airplane with the least excess capacity for this flight
	  for (int i = 0; i < airplanes.size(); i++) {
		  currentAirplaneSeats = airplanes.get(i).getTotalSeats();		  
		  if (currentAirplaneSeats > capacity) {
			  if (smallestSeatDelta > (currentAirplaneSeats - capacity)) {
				  smallestSeatDelta = currentAirplaneSeats - capacity;
				  airplaneIndex = i; // represents the index of the best airplane for this flight
			  }
		  }
	  }
	  
	  if (destination.equals("Tokyo")) { // if it is tokyo, it has to be longhaul (index = 4)
		  Flight newfl = new LongHaulFlight(flightNum, airline, destination, departureTime, cities.get(destination), airplanes.get(4));
		  flights.put(flightNum, newfl);
	  }else { // if its not tokyo, use airplaneIndex
		  
		  Flight newfl = new Flight(flightNum, airline, destination, departureTime, cities.get(destination), airplanes.get(airplaneIndex));
		  flights.put(flightNum, newfl);
	  }	  
  }
  
  
  private String generateFlightNumber(String airline)
  {
	  // generate a random number from 100 to 300 which will represent the flight number
	  int flightNum = random.nextInt(300 - 100) + 100;
	  
	  // flightString will store the the letters of the airline
      String flightString = "";
      
      // switch statement will set the first two letters for each airline
      switch(airline) {
      case "United Airlines":
        flightString = "UA";
        break;
      case "Air Canada":
    	  flightString = "AC";
        break;
      case "Porter Airlines":
    	  flightString = "PA";
        break;   
      default:
    	  flightString = "";
    }

      // concatenate the two into one string 
    String output = flightString +  Integer.toString(flightNum);   
    // return the string 
  	return output; // delete this when you finish the code
  }

  // Prints all flights in flights array list (see class Flight toString() method) 
  // This one is done for you!
  public void printAllFlights()
  { 	  	
  	for (Flight fl : flights.values()) { // go through each flight and print its toString()
  		System.out.println(fl.toString());
  	}
  }
  
  // prints all the passengers on a specific flight 
  public void printPassenger(String flightnum) 
  throws InvalidFlightNum
  {	  
	  
	  checkForFlightNum(flightnum);
	  //print the passengers if the flightnum is valid 
	  flights.get(flightnum).printPassengerManifest();
  }
  
  public void printSeats(String flightnum) 
  throws InvalidFlightNum
  {
	  checkForFlightNum(flightnum);
	  //prints the seat array if the flightnum is valid
	  flights.get(flightnum).printSeats();
  }
  
    
  // creates and returns a reservation
  // inputs are a flightnumber and seat type, and output is a reservation
  public Reservation reserveSeatOnFlight(String flightNum, String seatType, String name, String passportNumber, String seat) 
		  throws Flight.DuplicatePassenger, Flight.SeatOccupied, InvalidFlightNum
  {  	  	
	    this.checkForFlightNum(flightNum);
	  
	  //create new passenger  
		Passenger newPassenger = new Passenger(name, passportNumber, seat, seatType);		  		 		
		// reserve a seat with the new passenger	
		flights.get(flightNum).reserveSeat(newPassenger, seat);
			
		Reservation res = new Reservation(flightNum, name, passportNumber, seat);
			
		if (seatType.equals(LongHaulFlight.firstClass)) {
			res.setFirstClass(); // set res to first class 
		}
		PrintFlightString(flightNum); //returns the reservation and prints the information
		return res;			
  	  	
  }
  
  //checks the flights to make sure that this flightnum exsists, and if it doens't, throws exception 
  public void checkForFlightNum(String flightNum) 
  throws InvalidFlightNum
  {
	  if (flights.containsKey(flightNum) == false) {
		  throw new InvalidFlightNum("Flight " + flightNum + " doesn't exist");
	  }
  }
  
  
  public void cancelReservation(Reservation res, String name, String passport)
  throws Flight.PassengerNotInManifest
  {  
	  //gets passenger with the right name and passport, and then cancels him
	  String flightNum = res.flightNum;
	  Passenger p = flights.get(flightNum).getPassenger(name, passport);	  
	  
	  flights.get(flightNum).cancelSeat(p);
  }
  
  // prints the tostring of a flight based on the flight number
  public void PrintFlightString(String flightNum) {	  	  
	  System.out.println(flights.get(flightNum).toString());
  }  
  
  //exception for if the flightnum is wrong 
  class InvalidFlightNum extends Exception
  {
    public InvalidFlightNum(String message)
    {
      super(message);
    }
  }
  
}









