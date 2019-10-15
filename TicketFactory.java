package GroupProject1_Iteration2;
/**
 * @author Prechar Xiong, Christopher Corcoran, Thomas Wohlever
 * Date: 07/24/19
 * ICS-372-01 Object Oriented Design and Implementation
 * The Class TicketFactory creates tickets
 */
import java.util.Calendar;

public class TicketFactory {
	private static TicketFactory factory;

	/**
	 * Private for singleton
	 */
	private TicketFactory() {
	}

	/**
	 * Returns the only instance of the class.
	 * @return the instance
	 */
	public static TicketFactory instance() {
		if (factory == null) {
			factory = new TicketFactory();
		}
		return factory;
	}

	/**
	 * Creates a Ticket object and returns it.
	 * @param type the type of the ticket
	 * @param dateOfShow the date the show plays
	 * @param ticketPrice the price for the ticket
	 */
	public Ticket createTicket(int type, Calendar dateOfShow, float ticketPrice ) {
		switch (type) {
		case Theater.REGULAR:
			return new RegularTicket(dateOfShow, ticketPrice);
		case Theater.ADVANCE:
			return new AdvanceTicket(dateOfShow, ticketPrice);
		case Theater.STUDENT_ADVANCE:
			return new StudentAdvanceTicket(dateOfShow, ticketPrice);
		default:
			return null;
		}
	}

}

