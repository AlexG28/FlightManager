/* Flight reservation project 
 * By: Aleksandr Gyumushyan
 * ID: 501018761
 * Date: 04-16-2021
 * 
 */


public class Passenger {

	// basic variables such as name, passport and seat number
	private String name;
	private String passport;
	private String seat;
	private String seatType;
	
	// constructor class that uses name and passport number (seat is generated elsewhere)
	public Passenger (String name, String passportNumber, String seat, String seatType) {
		this.name = name;
		this.passport = passportNumber;
		this.seatType = seatType;
		this.seat = seat;
		
	}
	
	// compare two passengers based on name and passport number
	public boolean compareTo(Passenger other) {
		if (this.name.equals(other.name) && this.passport.equals(other.passport)) {
			return true; // return true if they are the same 
		} else {
			return false; // return false if they are different 
		}
	}
	
	// sets the seat number 
	public void setSeatNumber(String seatNumber) {
		this.seat = seatNumber;
	}
	// returns the name 
	public String getName() {
		return this.name;
	}
	
	//returns passport number
	public String getPassportNumber() {
		return this.passport;
	}
	
	// returns seat number
	public String getSeatNumber() {
		return this.seat;
	}
	
	public String getSeatType() {
		return this.seatType;
	}
	
	// returns a string that contains all of this passengers data 
	public String toString() {
		return "Name: " + this.name + " Passport Number: " + this.passport + " Seat: " + this.seat + "\n";
	}
}
