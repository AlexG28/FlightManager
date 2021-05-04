import java.util.ArrayList;
import java.util.Scanner;

/* Flight reservation project 
 * By: Aleksandr Gyumushyan
 * ID: 501018761
 * Date: 04-16-2021
 * 
 */
public class FlightReservationSystem
{
	public static void main(String[] args)
	{
		// Create a FlightManager object
		FlightManager manager = new FlightManager();

		// List of reservations that have been made
		ArrayList<Reservation> myReservations = new ArrayList<Reservation>();	// my flight reservations

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");

		while (scanner.hasNextLine())
		{
			String inputLine = scanner.nextLine();
			if (inputLine == null || inputLine.equals("")) continue;

			// The command line is a scanner that scans the inputLine string
			// For example: list AC201
			Scanner commandLine = new Scanner(inputLine);
			
			// The action string is the command to be performed (e.g. list, cancel etc)
			String action = commandLine.next();

			if (action == null || action.equals("")) continue;

			if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
				return;
			
			// List all flights
			else if (action.equalsIgnoreCase("LIST"))
			{
				manager.printAllFlights(); 
			}
			
			// reserve a flight for passenger with (name), (passport), and (seat)
			// example: res UA267 Alex DD1234 7B
			else if (action.equalsIgnoreCase("RES"))
			{								
				if (commandLine.hasNext()) {
					String flight = commandLine.next();
					String name = commandLine.next();
					String passport = commandLine.next();
					String seat = commandLine.next();
					String seatType = "";
					
					
					//if the last char is a +, then its a first class
					if (seat.endsWith("+")) {
						seatType = LongHaulFlight.firstClass;						
					} else {
						seatType = LongHaulFlight.economy;
					}
					
					try {
						//make a reservation
						Reservation res = manager.reserveSeatOnFlight(flight, seatType, name, passport, seat); 
						if (res != null) {	//if it was successful, add it to myReservations					
							myReservations.add(res);
						}	
						
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}										
				}
			}	
			
			else if (action.equalsIgnoreCase("CANCEL")) // cancel a seat 
			{							
				
				if (commandLine.hasNext() == true) {
					String flightNum = commandLine.next();
					String name = commandLine.next();
					String passport = commandLine.next();					
					try {
						manager.checkForFlightNum(flightNum);
						
						for(int i = 0; i < myReservations.size(); i++) { // loop through all flights to find the right flight
							
							if (myReservations.get(i).flightNum.equals(flightNum)) {
								manager.cancelReservation(myReservations.get(i), name, passport);
								myReservations.remove(i);
								break;							
							}
						}
							
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
					
					
					 					
				}
			}
						
			// print information about all passengers 
			else if (action.equalsIgnoreCase("PASMAN")) { 
				
				try {
					
					if (commandLine.hasNext() == true) {
						manager.printPassenger(commandLine.next());	
					} 					
					
				}catch(Exception e) {
					System.out.println(e.getMessage());
				}
				
				
			}
			// print out seats on a flight
			else if (action.equalsIgnoreCase("SEATS"))
			{
				try {
					if (commandLine.hasNext())
					{		
						manager.printSeats(commandLine.next());
					}
					
					
				} catch(Exception e) {
					System.out.println(e.getMessage());
				}

			}
			// cancel reservation with (passenger name) and (passport)
			
           // Print all the reservations 
			else if (action.equalsIgnoreCase("MYRES"))
			{ // loops through all reservations and prints all reservations 
				if (myReservations.isEmpty()) {
					System.out.println("You don't have any reservations");
				} else {
					for (int i = 0; i < myReservations.size(); i++) {
						myReservations.get(i).print(); // this prints the flight info
						
						manager.PrintFlightString(myReservations.get(i).flightNum);	//this prints general information about the flight			
					}
				}
			}											  
	  
			System.out.print("\n>");
		}
	}
	
	public static void clearScreen() {  
	    //System.out.print("\033[H\033[2J");  
	    System.out.flush();  
	}  

	
}

