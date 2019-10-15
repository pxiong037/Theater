package GroupProject1_Iteration2;
/**
 * @author Prechar Xiong, Christopher Corcoran, Thomas Wohlever
 * Date: 07/24/2019
 * ICS-372-01 Object Oriented Design and Implementation
 * This class stores AdvanceTicket attributes and adjusts it's ticket price accordingly.
 * It extends the abstract class Ticket
*/
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AdvanceTicket extends Ticket implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String type = "ADVANCE";
	
	public AdvanceTicket(Calendar dateOfShow, float ticketPrice) {
		super(dateOfShow, (float)(ticketPrice * .7));
		serialNumber = type + (TicketIdServer.instance().getId());
	}
	
	/**
	 * @return the String information of the AdvanceTicket
	 */
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		DecimalFormat df = new DecimalFormat("#.##");
		String formatDateOfShow = sdf.format(dateOfShow.getTime());
		return "Show Date: " + formatDateOfShow + "\nType: " + type + 
				"\nPrice: $" + df.format(ticketPrice) + "\nSerial Number: " + serialNumber + "\n";
	}
}
