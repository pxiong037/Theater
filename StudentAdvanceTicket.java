package GroupProject1_Iteration2;
/**
 * @author Prechar Xiong, Christopher Corcoran, Thomas Wohlever
 * Date: 07/24/2019
 * ICS-372-01 Object Oriented Design and Implementation
 * This class stores StudentAdvanceTicket attributes and adjusts it's ticket price accordingly. 
 * It extends the abstract class Ticket
*/
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class StudentAdvanceTicket extends Ticket implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String type = "STUDENT_ADVANCE";
	
	public StudentAdvanceTicket(Calendar dateOfShow, float ticketPrice) {
		super(dateOfShow, (float)(ticketPrice * .7 * .5));
		serialNumber = type + (TicketIdServer.instance().getId());
	}
	
	/**
	 * @return the all of the studentAdvanceTicket information as a string
	 */
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String formatDateOfShow = sdf.format(dateOfShow.getTime());
		DecimalFormat df = new DecimalFormat("#.##");
		return "Show Date: " + formatDateOfShow + "\nType: " + type + 
				"\nPrice: $" + df.format(ticketPrice) + "\nSerial Number: " + serialNumber + "\n";
	}
}
