package GroupProject1_Iteration2;
/**
 * @author Prechar Xiong, Christopher Corcoran, Thomas Wohlever
 * Date: 07/24/2019
 * ICS-372-01 Object Oriented Design and Implementation
 * This abstract class stores Ticket attributes and is the super class
 * of all AdvanceTickets, RegularTickets, and StudentAdvanceTickets
*/
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public abstract class Ticket implements Serializable {
	private static final long serialVersionUID = 1L;
	protected Calendar dateOfShow;
	protected float ticketPrice;
	protected String serialNumber;
	protected String type;
	
	public Ticket(Calendar dateOfShow, float ticketPrice) {
		this.dateOfShow = dateOfShow;
		this.ticketPrice = ticketPrice;
	}

	/**
	 * @return the ticket's serial number
	 */
	public String getSerialNumber() {
		return serialNumber;
	}

	/**
	 * @return the type of the ticket
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * @return Calendar dateOfShow
	 */
	public Calendar getDateOfShow() {
		return dateOfShow;
	}
	
	/**
	 * @return ticket price of the show
	 */
	public float getTicketPrice() {
		return ticketPrice;
	}
	
	/**
	 * return all information of the ticket as a string
	 */
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String formatDateOfShow = sdf.format(dateOfShow.getTime());
		DecimalFormat df = new DecimalFormat("#.##");
		return "Show Date: " + formatDateOfShow + "\nType: " + type + 
				"\nPrice: $" + df.format(ticketPrice) + "\nSerial Number: " + serialNumber + "\n";
	}
}
