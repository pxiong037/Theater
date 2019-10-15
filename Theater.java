package GroupProject1_Iteration2;
/**
 * @author Prechar Xiong, Christopher Corcoran, Thomas Wohlever
 * Date: 07/24/2019
 * ICS-372-01 Object Oriented Design and Implementation
 * The Theater class uses a Singleton design.  
 * The Methods included carry out the functionality of a Theater  
 */
import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.*;

public class Theater implements Serializable {
	private static final long serialVersionUID = 1L;
	private static Theater theater;
	private Catalog catalog;
	public static final int REGULAR = 1;
	public static final int ADVANCE = 2;
	public static final int STUDENT_ADVANCE = 3;

	private Theater() {
		catalog = Catalog.instance();
	}

	public static Theater instance() {
		if (theater == null) {
			Catalog.instance();
			IdServer.instance();
			TicketIdServer.instance();
			return (theater = new Theater());
		} else {
			return theater;
		}
	}

	public Catalog getCatalog() {
		return catalog;
	}

	public void setCatalog(Catalog catalog) {
		this.catalog = catalog;
	}

	/**
	 * Method searches each customer for a matching credit card number
	 * @param cardNumber is a 16 digit number associated with a credit card
	 * @return true if the cardNumber entered is a match
	 */
	public boolean searchCreditCard(String cardNumber) {
		for (Iterator<ParticipantItem> iterator = catalog.iterator(); iterator.hasNext();) {
			ParticipantItem participant = iterator.next();

			if (participant instanceof Customer) {
				CreditCard card = ((Customer) participant).searchCard(cardNumber);
				if (card != null && card.getCardNumber().equals(cardNumber)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Method searches for a credit card number of a specific customer	
	 * @param customerId is a unique id that represents a customer
	 * @param cardNumber is a 16 digit number associated with a credit card
	 * @return true if the customerId has a cardNumber that matches
	 */
	public boolean searchCustomerCreditCard(String customerId, String cardNumber) {
		Customer customer = (Customer) catalog.search(customerId);
		if (customer != null) {
			if(customer.searchCard(cardNumber) != null) {
				return true;
			} else { 
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Method to add a client to the catalog
	 * @param name is the client's name stored as a String
	 * @param address is the client's address stored as a String
	 * @param phoneNumber is the client's phoneNumber stored as a string
	 * @return the Client object that was added to the catalog otherwise null
	 */
	public Client addClient(String name, String address, String phoneNumber) {
		Client client = new Client(name, address, phoneNumber);
		if (catalog.insertParticipantItem(client)) {
			return (client);
		}
		return null;
	}

	/**
	 * Method to remove a client from the catalog
	 * @param clientId is a unique ID for a client
	 */
	public void removeClient(String clientId) {
		Client client = (Client) catalog.search(clientId);
		if (client != null) {
			try {
				Calendar calendar = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				String formatted = sdf.format(calendar.getTime());
				Date currentDate = calendar.getTime();
				if(client.getShows().size() >= 1) {
					for(Show show : client.getShows()) {
						String formatBeginDate = sdf.format(show.getBeginDate().getTime());
						String formatEndDate = sdf.format(show.getEndDate().getTime());
						currentDate = sdf.parse(formatted);
						Date dateBegin = sdf.parse(formatBeginDate);
						Date dateEnd = sdf.parse(formatEndDate);
						if(dateBegin.before(currentDate) && dateEnd.before(currentDate)) {
							catalog.removeParticipantItem(clientId);
							System.out.println("The client " + clientId + " was removed");
						} else {
							System.out.println("The client " + clientId + " cannot be removed because it's scheduled for a current or future show." );
							break;
						}
					}
				} else {
					catalog.removeParticipantItem(clientId);
					System.out.println("The client " + clientId + " was removed");
				}
			} catch(Exception e) {
				System.out.println("Could not remove client " + clientId);
			}
		} else {
			System.out.println(clientId + " was not found.");
		}
	}

	/**
	 * Method returns the clients current balance
	 * @param clientId is a unique ID for a client
	 * @return float value that is the client's balance
	 */
	public float getClientBalance(String clientId) {
		Client client = (Client) catalog.search(clientId);
		return client.getBalance();

	}

	/**
	 * Method to pay the client a specific amount	
	 * @param clientId is a unique ID for a client
	 * @param amountToBePaid float amount that is to be paid to the client
	 */
	public void payClient(String clientId, float amountToBePaid) {
		Client client = (Client) catalog.search(clientId);
		client.updateBalance(amountToBePaid * -1);

	}

	/**
	 * Method to list all client to the console
	 * @return String object formatted of all clients
	 */
	public String listAllClients() {
		String s = "";
		for (Iterator<ParticipantItem> iterator = catalog.iterator(); iterator.hasNext();) {
			ParticipantItem participant = iterator.next();

			if (participant instanceof Client) {
				s = s + "\n" + (((Client) participant));
			}
		}
		return s;
	}

	/**
	 * A helper method for checking if a show's dates conflict with another show
	 * @param aShow is a show object
	 * @return true if there is a conflict with the dates of the show that was passed to the method
	 */
	boolean isWithinRange(Show aShow) {
		boolean conflictOfDates = false;
		for (Iterator<ParticipantItem> iterator = catalog.iterator(); iterator.hasNext();) {
			ParticipantItem participant = iterator.next();

			if(participant instanceof Client) {
				for (Show show : ((Client) participant).getShows()) {
					if (!(aShow.getBeginDate().before(show.getBeginDate())
							|| aShow.getBeginDate().after(show.getEndDate()))) {
						conflictOfDates = true;
						break;
					} else if (!(aShow.getEndDate().before(show.getBeginDate())
							|| aShow.getEndDate().after(show.getEndDate()))) {
						conflictOfDates = true;
						break;
					}
				}
			}
		}
		return conflictOfDates;
	}

	/**
	 * Method to search the catalog for a client
	 * @param clientId is a unique ID for a client
	 * @return true if the clientID is located in the catalog
	 */
	public boolean searchClient(String clientId) {
		Client client = (Client) catalog.search(clientId);
		if (client != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Method to add a customer to the catalog
	 * @param name is the customer's name stored as a String
	 * @param address is the customer's address stored as a String
	 * @param phoneNumber is the customer's phoneNumber stored as a string
	 * @return the Customer object that was added to the catalog otherwise null
	 */
	public Customer addCustomer(String name, String address, String phoneNumber) {
		Customer customer = new Customer(name, address, phoneNumber);
		if (catalog.insertParticipantItem(customer)) {
			return (customer);
		}
		return null;
	}

	/**
	 * Method to remove a customer from the catalog
	 * @param customerId is a unique ID for a customer
	 */
	public void removeCustomer(String customerId) {
		Customer customer = (Customer) catalog.search(customerId);
		if (customer != null) {
			catalog.removeParticipantItem(customerId);
			System.out.println(customer + "\n" + customerId + " was removed");
		} else {
			System.out.println(customerId + " was not found.");
		}
	}

	/**
	 * Method to add a credit card to a Customer object
	 * @param customerId is a unique ID for a customer
	 * @param cardNumber is a 16 digit string representing a credit card number
	 * @param expirationDate is a YearMonth object for the expiration of the card
	 * @return the CreditCard object if successful otherwise return null
	 */
	public CreditCard addCreditCard(String customerId, String cardNumber, YearMonth expirationDate) {
		CreditCard creditCard = new CreditCard(customerId, cardNumber, expirationDate);
		Customer customer = (Customer) catalog.search(customerId);
		if (customer != null) {
			customer.addCreditCard(creditCard);
			return (creditCard);
		}
		return null;
	}

	/**
	 * Method to remove a credit card
	 * @param customerId is a unique ID for a customer
	 * @param cardNumber is a 16 digit string representing a credit card number
	 */
	public void removeCreditCard(String customerId, String cardNumber) {
		Customer customer = (Customer) catalog.search(customerId);
		customer.removeCreditCard(cardNumber);
	}

	/**
	 * Method to list all customers to the console
	 * @return String object formatted with all customers
	 */
	public String listAllCustomers() {
		String s = "";
		for (Iterator<ParticipantItem> iterator = catalog.iterator(); iterator.hasNext();) {
			ParticipantItem participant = iterator.next();

			if (participant instanceof Customer) {
				s = s + "\n" + (((Customer) participant));
			}
		}
		return s;
	}

	/**
	 * Method to search the catalog for a customer
	 * @param customerId is a unique ID for a customer
	 * @return true if the customerID is located in the catalog
	 */
	public boolean searchCustomer(String customerId) {
		Customer customer = (Customer) catalog.search(customerId);
		if (customer != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Method to add a show to a Client object
	 * @param showName is a string representing the name of a show
	 * @param clientId is a unique ID for a client
	 * @param beginDate is a Calendar date for the start of the show
	 * @param endDate is a Calendar date for the end of the show
	 * @param ticketPrice float value representing the price of a ticket
	 * @return Show object the was added to the client, otherwise null
	 */
	public Show addShow(String showName, String clientId, Calendar beginDate, Calendar endDate, float ticketPrice) {		
		Show show = new Show(showName, clientId, beginDate, endDate, ticketPrice);
		Client client = (Client) catalog.search(clientId);
		if(client != null) {
			if(isWithinRange(show)) {
				System.out.println("The begin and end dates are in conflict with another show.");
				return null;
			} else {
				client.addShow(show);
				return(show);
			}
		} else {
			return null;
		}
	}

	/**
	 * Method to remove a show from a Client object
	 * @param beginDate is a Calendar date for the start of the show
	 * @param endDate is a Calendar date for the end of the show
	 */
	public void removeShow(Calendar beginDate, Calendar endDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String formatBeginDate = sdf.format(beginDate.getTime());
		String formatEndDate = sdf.format(endDate.getTime());
		for (Iterator<ParticipantItem> iterator = catalog.iterator(); iterator.hasNext();) {
			ParticipantItem participant = iterator.next();
			if(participant instanceof Client) {
				for (Show show : ((Client) participant).getShows()) {
					show = ((Client) participant).search(beginDate, endDate);
					if (!iterator.hasNext() && show == null) { 
						System.out.println("The show entered does not exist and could not be removed.");
					} else if (show != null) {
						((Client) participant).removeShow(show);
						System.out.println("The show: '" + show.getShowName() + "' from " + formatBeginDate + " to "
								+ formatEndDate + " was removed");
						break;
					}
				}
			}
		}
	}

	/**
	 * Method to list all shows to the console
	 * @return String object formatted with all shows
	 */
	public String listAllShows() {
		String s = "";
		for (Iterator<ParticipantItem> iterator = catalog.iterator(); iterator.hasNext();) {
			ParticipantItem participant = iterator.next();

			if (participant instanceof Client) {
				s = s + "\n" + (((Client) participant).listAllShows());
			}
		}
		return s;
	}

	/**
	 * Method to search for a show on a specific date	
	 * @param date Calendar object for a date
	 * @return Show object if the date matches a show, otherwise null
	 */
	public Show searchShow(Calendar date) {
		for (Iterator<ParticipantItem> iterator = catalog.iterator(); iterator.hasNext();) {
			ParticipantItem participant = iterator.next();

			if (participant instanceof Client) {
				Show show = ((Client) participant).search(date);
				if (show != null) {
					return show;
				}
			}
		}
		System.out.println("There's no current show playing on that date");
		return null;
	}

	/**
	 * Method to sell tickets uses TicketFactory to create a ticket
	 * It updates the Client balance and adds the ticket to the Customer
	 * @param type integer representing a specific type of ticket
	 * @param quantity integer representing a quantity of tickets
	 * @param customerId unique Id to identify a customer
	 * @param cardNumber 16 digit string representing a credit card number
	 * @param dateOfShow Calendar date for the requested date of the ticket
	 */
	public void sellTickets(int type, int quantity, String customerId, String cardNumber, Calendar dateOfShow) {
		Show show = searchShow(dateOfShow);
		Client client = (Client) catalog.search(show.getClientId());
		Customer customer = (Customer) catalog.search(customerId);
		for(int i = 0; quantity > i; i++) {
			Ticket ticket = TicketFactory.instance().createTicket(
					type, dateOfShow, show.getTicketPrice());
			client.updateBalance((float)(.5 * ticket.getTicketPrice()));
			customer.addTicket(ticket);
			System.out.println(ticket);
		}
		if(type == Theater.STUDENT_ADVANCE) {
			System.out.println("Must show valid student id at entrance");
		}
	}

	/**
	 * Method to print all tickets to the console for a specific date
	 * @param date Calendar date for a specific day all tickets are to be printed for
	 */
	public void printAllTickets(Calendar date) {
		ArrayList<Ticket> tickets = new ArrayList<Ticket>();
		Customer customer;
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String formatDate = sdf.format(date.getTime());
		for (Iterator<ParticipantItem> iterator = catalog.iterator(); iterator.hasNext();) {
			ParticipantItem participant = iterator.next();
			if (participant instanceof Customer) {
				customer = ((Customer) participant);
				if(!iterator.hasNext() && customer.searchTickets(date).isEmpty()) {
					System.out.println("There are no purchased tickets for the date " + formatDate);
				} else {
					tickets = customer.searchTickets(date);
					for(Ticket ticket : tickets) {
						System.out.println(ticket);
					}
				}
			}
		}
	}

	/**
	 * Method to get the TheaterData from a serialized file 
	 * @return Theater object, otherwise null
	 */
	public static Theater retrieve() {
		try {
			FileInputStream file = new FileInputStream("TheaterData");
			ObjectInputStream input = new ObjectInputStream(file);
			input.readObject();
			IdServer.retrieve(input);
			TicketIdServer.retrieve(input);
			return theater;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return null;
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
			return null;
		}
	}

	/**
	 * Serializes the Library object
	 * @return true iff the data could be saved
	 */
	public static boolean save() {
		try {
			FileOutputStream file = new FileOutputStream("TheaterData");
			ObjectOutputStream output = new ObjectOutputStream(file);
			output.writeObject(theater);
			output.writeObject(IdServer.instance());
			output.writeObject(TicketIdServer.instance());
			output.close();
			return true;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return false;
		}
	}

	/**
	 * Writes the object to the output stream 
	 * @param output the stream to be written to
	 */
	private void writeObject(java.io.ObjectOutputStream output) {
		try {
			output.defaultWriteObject();
			output.writeObject(theater);
		} catch (IOException ioe) {
			System.out.println(ioe);
		}
	}

	/**
	 * Reads the object from a given stream
	 * @param input the stream to be read
	 */
	private void readObject(java.io.ObjectInputStream input) {
		try {
			input.defaultReadObject();
			if (theater == null) {
				theater = (Theater) input.readObject();
			} else {
				input.readObject();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * String form of the theater
	 */
	@Override
	public String toString() {
		return listAllClients() + "\n\n" + listAllCustomers();
	}
}
