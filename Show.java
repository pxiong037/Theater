package GroupProject1_Iteration2;
/**
 * @author Prechar Xiong, Christopher Corcoran, Thomas Wohlever
 * Date: 07/24/19
 * ICS-372-01 Object Oriented Design and Implementation
 * This class contains all of the information about a show
*/
import java.util.*;
import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class Show implements Serializable {
	private static final long serialVersionUID = 1L;
	private String showName;
	private String clientId;
	private Calendar beginDate;
	private Calendar endDate;
	private float ticketPrice;
	
	public Show(String showName, String clientId, Calendar beginDate, Calendar endDate, float ticketPrice) {
		this.showName = showName;
		this.clientId = clientId;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.ticketPrice = ticketPrice;
	}
	
	/**
	 * @return String client Id of the show's associated client Id
	 */
	public String getClientId() {
		return clientId;
	}
	
	/**
	 * set the show's associated client Id to another client Id
	 * @param clientId
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	/**
	 * @return Calendar date of the show's beginDate
	 */
	public Calendar getBeginDate() {
		return beginDate;
	}
	
	/**
	 * @return Calendar date of the show's endDate
	 */
	public Calendar getEndDate() {
		return endDate;
	}
	
	/**
	 * @return String showName of the show
	 */
	public String getShowName() {
		return showName;
	}
	
	/**
	 * @return float ticketPrice of the show
	 */
	public float getTicketPrice() {
		return ticketPrice;
	}
	
	/**
	 * @return String information of the show without the client Id
	 */
	public String listClientShows() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String formatBeginDate = sdf.format(beginDate.getTime());
		String formatEndDate = sdf.format(endDate.getTime());
		DecimalFormat df = new DecimalFormat("#.##");
		return	"Show Name: " + showName + "\n" + 
				"Begin Date: " + formatBeginDate + "\n" +
				"End Date: " + formatEndDate + "\n" +
				"Regular Ticket Price: $" + df.format(ticketPrice) + "\n";
	}
	
	/**
	 * returns String information of the show.
	 */
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String formatBeginDate = sdf.format(beginDate.getTime());
		String formatEndDate = sdf.format(endDate.getTime());
		DecimalFormat df = new DecimalFormat("#.##");
		return "Client ID: " + clientId + "\n" +
				"Show Name: " + showName + "\n" + 
				"Begin Date: " + formatBeginDate + "\n" +
				"End Date: " + formatEndDate + "\n" +
				"Regular Ticket Price: $" + df.format(ticketPrice) + "\n";
	}
}
