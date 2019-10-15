package GroupProject1_Iteration2;
/**
 * @author Prechar Xiong, Christopher Corcoran, Thomas Wohlever
 * Date: 07/24/19
 * ICS-372-01 Object Oriented Design and Implementation
 * The Class Customer stores customer attributes and and credit card information. 
 * There are methods that allow displaying customer and credit cards information.
 * A customer can only be created if there is a name, address, phone number, and at
 * least one credit card. 
 */
import java.util.*;
import java.io.*;

public class Customer extends ParticipantItem implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String CUSTOMER_STRING = "CU";
	private ArrayList<CreditCard> creditCards = new ArrayList<CreditCard>();
	private ArrayList<Ticket> tickets = new ArrayList<Ticket>();

	/**
	 * Instantiates a new customer. A customerId is assigned from the
	 * CustomerIdServer.
	 * @param name the name
	 * @param address the address
	 * @param phoneNumber the phone number
	 */
	public Customer(String name, String address, String phoneNumber) {
		super(name, address, phoneNumber);
		super.setId(CUSTOMER_STRING + (IdServer.instance().getId()));
	}
	
	/**
	 * Gets the customer id.
	 * @return the customer id
	 */
	public String getCustomerId() {
		return getId();
	}
	
	public ArrayList<CreditCard> getCreditCards(){
		return creditCards;
	}

	/**
	 * Adds the credit card.
	 * @param creditCard the credit card
	 */
	public void addCreditCard(CreditCard creditCard) {
		creditCards.add(creditCard);
	}
	
	public void addTicket(Ticket ticket) {
		tickets.add(ticket);
	}

	/**
	 * Removes the credit card. If a customer only has one credit card, it will
	 * not be removed.
	 * @param cardNumber the card number
	 */
	public void removeCreditCard(String cardNumber) {
		if (searchCard(cardNumber) == null) {
			System.out.println("The card number entered does not exist for the customer " + getCustomerId());
		} else {
			if (creditCards.size() == 1) {
				System.out.println("This is the only card associated with this account and cannot be removed. ");
			} else {
				CreditCard creditCard = searchCard(cardNumber);
				creditCards.remove(creditCard);
				System.out.println("The credit card: " + creditCard.cardNumberFormatter(creditCard.getCardNumber())
						+ " has been removed from customer " + getCustomerId());
			}
		}
	}

	/**
	 * Search for a credit card.
	 * @param cardNumber the card number
	 * @return the credit card
	 */
	public CreditCard searchCard(String cardNumber) {
		for (Iterator<CreditCard> iterator = creditCards.iterator(); iterator.hasNext();) {
			CreditCard creditCard = iterator.next();
			if ((creditCard.getCardNumber()).equals(cardNumber)) {
				return creditCard;
			}
		}
		return null;
	}
	
	public ArrayList<Ticket> searchTickets(Calendar date) {
		ArrayList<Ticket> ticketsList = new ArrayList<Ticket>();
		for (Iterator<Ticket> iterator = tickets.iterator(); iterator.hasNext();) {
			Ticket ticket = iterator.next();

			if (ticket.dateOfShow.equals(date)) {
				ticketsList.add(ticket);
			} 
		}
		return ticketsList;
	}

	/**
	 * List all cards.
	 * @return the string
	 */
	public String listAllCards() {
		String cards = "";
		for (CreditCard c : creditCards) {
			cards += c.customerCards() + "\n";
		}
		return cards;
	}
	
	public String listAllTickets() {
		String ticket = "";
		for(Ticket t : tickets) {
			ticket += t + "\n";
		}
		return ticket;
	}

	/**
	 * Displays information about the customer.
	 */
	public String toString() {
		return super.display() + "\n" + listAllCards() + 
				"Purchased tickets: \n" + listAllTickets() +
				"___________________________________________";
	}
}
