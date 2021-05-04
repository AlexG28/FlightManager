/* Flight reservation project 
 * By: Aleksandr Gyumushyan
 * ID: 501018761
 * Date: 04-16-2021
 * 
 */

public class LongHaulFlight extends Flight
{
	int numFirstClassPassengers;
	String seatType;
	
	// Possible seat types
	public static final String firstClass = "First Class Seat";
	public static final String economy 		= "Economy Seat";  
	

	public LongHaulFlight(String flightNum, String airline, String dest, String departure, int flightDuration, Aircraft aircraft)
	{
		// use the super() call to initialize all inherited variables
		// also initialize the new instance variables 
		super(flightNum, airline, dest, departure, flightDuration, aircraft);
		// not sure about this
		this.seatType = economy; 
		this.numFirstClassPassengers = 0;
	}

	public LongHaulFlight()
	{
     // default constructor
	}
	
	public FlightType getFlightType() {
		return FlightType.LONGHAUL;
	}
	
	// reserves a  seat for a longhaul flight
	public void reserveSeat(Passenger p, String seat, String seatType)
	throws Flight.DuplicatePassenger, Flight.SeatOccupied
	{
		// if the seat is economy, use parent class function instead
		if (seatType.equals(LongHaulFlight.economy)) {			
			super.reserveSeat(p, seat);			
		} // if its first class 
		else if(seatType.equals(LongHaulFlight.firstClass)) {
			// check if there are enough seats left 
			if (this.aircraft.getNumFirstClassSeats() > this.numFirstClassPassengers) {
				this.numFirstClassPassengers++; // increase seat count by one and return true
				super.reserveSeat(p, seat);
			} 
		}					
	}
	
	// Cancel a seat if type is not specified	
	public void cancelSeat(Passenger p)	
	{
	  // override the inherited cancelSeat method and call the cancelSeat method below with an economy seatType
		// use the constants defined at the top
		cancelSeat(p, LongHaulFlight.economy);
		
	}
	// cancels a seat if a seat type is specified 
	public void cancelSeat(Passenger p, String seatType)	
	{
		// if its economy, reduce passengers by one
		if(seatType.equals(LongHaulFlight.economy)) {
			this.passengers--;
		} else if (seatType.equals(LongHaulFlight.firstClass)) {
			// if its first class, check if there is enough room, and if there is reduce passenger count
			if (this.numFirstClassPassengers > 0) {
				this.numFirstClassPassengers--;
			}
		}	
		
		super.cancelSeat(p);
	}
	
	// return the total passenger count of economy passengers *and* first class passengers
	// use instance variable at top and inherited method that returns economy passenger count
	public int getPassengerCount()
	{
		return this.passengers + this.numFirstClassPassengers;
	}
	
	public String toString()
	{
		// use parent class function and add 'longhaul' at the end 
		return super.toString() + " LongHaul";			
	}		
}
