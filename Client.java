package GroupProject1_Iteration2;
/**
 * @author Prechar Xiong, Christopher Corcoran, Thomas Wohlever
 * Date: 07/24/19
 * ICS-372-01 Object Oriented Design and Implementation
 * The Class Client contains information about a client as
 * well as methods to manipulate its information
*/
import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class Client extends ParticipantItem implements Serializable {
	private static final long serialVersionUID = 1L;
	private float balance = 0;
	private static final String CLIENT_STRING = "CL";
	private ArrayList<Show> shows = new ArrayList<Show>();

	public Client(String name, String address, String phoneNumber) {
		super(name, address, phoneNumber);
		super.setId(CLIENT_STRING + (IdServer.instance()).getId());
	}

	/**
	 * @return each show from the clients showList
	 */
	public ArrayList<Show> getShows() {
		return shows;
	}

	/**
	 * adds a show to the Client's showList
	 * @param show
	 */
	public void addShow(Show show) {
		shows.add(show);
	}
	
	/**
	 * updates the balance of the client
	 * @param total is the amount to be added to the client's balance
	 */
	public void updateBalance(float total) {
		this.balance += total;
	}
	
	/**
	 * @return the balance of the client
	 */
	public float getBalance() {
		return balance;
	}

	/**
	 * Searches for a show using beginDate and endDate
	 * @param beginDate
	 * @param endDate
	 * @return Show that has the same begin and end date as the parameters
	 */
	public Show search(Calendar beginDate, Calendar endDate) {
		for (Iterator<Show> iterator = shows.iterator(); iterator.hasNext();) {
			Show show = (Show) iterator.next();
			if (show.getBeginDate().equals(beginDate) && show.getEndDate().equals(endDate)) {
				return show;
			}
		}
		return null;
	}
	
	/**
	 * Searches for a show that will be performing on the date passed in
	 * from the parameter.
	 * @param date to be searched
	 * @return the show found to match the searched date
	 */
	public Show search(Calendar date) {
		Show show = null;
		for(Show s : shows) {
			if(!(date.before(s.getBeginDate()) || date.after(s.getEndDate()))) {
				show = s;
				break;
			} else if(!(date.before(s.getBeginDate()) || date.after(s.getEndDate()))) {
				show = s;
				break;
			} 
		}
		return show;
	}

	/**
	 * removes a show from the Client's showList
	 * @param show
	 */
	public void removeShow(Show show) {
		shows.remove(show);
	}

	/**
	 * @return each show's toString method from the Client's showList
	 */
	public String listAllShows() {
		String clientShows = "";
		for (Show show : shows) {
			clientShows += show + "\n";
		}
		return clientShows;
	}
	
	/**
	 * @return each show from the Client's showList as a String without the
	 * clients Id.
	 */
	public String listClientShows() {
		String clientShows = "";
		for(Show show : shows) {
			clientShows += show.listClientShows() + "\n";
		}
		return clientShows;
	}
	
	/**
	 * returns a String of all of the information of a client
	 */
	public String toString() {
		DecimalFormat df = new DecimalFormat("#.##");
		return super.display() + "\n" + "Balance: $" + df.format(balance) + "\n\n" +
				"Shows performing: \n" + listClientShows() + 
				"___________________________________________";
	}
}
