package GroupProject1_Iteration2;
/**
 * @author Prechar Xiong, Christopher Corcoran, Thomas Wohlever
 * Date: 07/24/2019
 * ICS-372-01 Object Oriented Design and Implementation
 * This class stores RegularTicket attributes and extends the abstract class
 * Ticket
*/
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RegularTicket extends Ticket implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String type = "REGULAR";
	
	public RegularTicket(Calendar dateOfShow, float ticketPrice) {
		super(dateOfShow, ticketPrice);
		serialNumber = type + (TicketIdServer.instance().getId());
	}

	/**
	 * returns the ticket's serial number as a String
	 */
	public String getSerialNumber() {
		return serialNumber;
	}

	/**
	 * set the tickets serialNumber to the param serialNumber
	 * @param serialNumber the serialNumber to replace the tickets serialNumber
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	/**
	 * returns the type of ticket as a String
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * returns the date of the show the ticket is associated with
	 */
	public Calendar getDateOfShow() {
		return dateOfShow;
	}
	
	/**
	 * returns the ticket price as a float
	 */
	public float getTicketPrice() {
		return ticketPrice;
	}
	
	/**
	 * returns all of the ticket's information as a String
	 */
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String formatDateOfShow = sdf.format(dateOfShow.getTime());
		DecimalFormat df = new DecimalFormat("#.##");
		return "Show Date: " + formatDateOfShow + "\nType: " + type + 
				"\nPrice: $" + df.format(ticketPrice) + "\nSerial Number: " + serialNumber + "\n";
	}
}
