/* Flight reservation project 
 * By: Aleksandr Gyumushyan
 * ID: 501018761
 * Date: 04-16-2021
 * 
 */
public class Reservation
{
	String flightNum;
	String flightInfo;
	boolean firstClass;
	
	String passengerName;
	String passengerPassport;
	String seat;
	
	
	public Reservation(String flightNum, String name, String passport, String seat)
	{
		this.flightNum = flightNum;		
		this.firstClass = false;
		
		this.passengerName = name;
		this.passengerPassport = passport;
		this.seat = seat;
	}
	
	public boolean isFirstClass()
	{
		return firstClass;
	}

	public void setFirstClass()
	{
		this.firstClass = true;
	}

	public String getFlightNum()
	{
		return flightNum;
	}
	
	public String getFlightInfo()
	{
		return flightInfo;
	}

	public void setFlightInfo(String flightInfo)
	{
		this.flightInfo = flightInfo;
	}

	// prints the data bout this reservation
	public void print()
	{		
		String output = "Name: " + this.passengerName + " Passport: " + this.passengerPassport + " Seat: " + this.seat;
		// if its a first class seat, add the appropriate label 
		if (this.firstClass == true) {
			output += " First class";
		}
		
		System.out.println(output);
	}
	
	public String getPassengerName() {
		return this.passengerName;
	}
	
	public String getPassport() {
		return this.passengerPassport;
	}
	
	// checks if this reservation is equal to another reservation based on name and flightnum
	public boolean equals(Reservation other) {
		if (this.flightNum == other.getFlightNum() && this.passengerName == other.getPassengerName()
				&& this.passengerPassport == other.getPassport()) {
			return true;
		} else {
			return false;
		}
	}
}
