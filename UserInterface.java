package GroupProject1_Iteration2;
/**
 * @author Prechar Xiong, Christopher Corcoran, Thomas Wohlever
 * Date: 07/24/2019
 * ICS-372-01 Object Oriented Design and Implementation
 * This class implements the user interface for the Theater group project.
 * The commands are encoded as integers using a number of
 * static final variables. A number of utility methods exist to
 * make it easier to parse the input.
*/
import java.util.*;
import java.text.*;
import java.io.*;
import java.time.*;

public class UserInterface {
	private static UserInterface userInterface;
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static Theater theater;
	private static final int EXIT = 0;
	private static final int ADD_CLIENT = 1;
	private static final int REMOVE_CLIENT = 2;
	private static final int LIST_ALL_CLIENTS = 3;
	private static final int ADD_CUSTOMER = 4;
	private static final int REMOVE_CUSTOMER = 5;
	private static final int ADD_CREDIT_CARD = 6;
	private static final int REMOVE_CREDIT_CARD = 7;
	private static final int LIST_ALL_CUSTOMERS = 8;
	private static final int ADD_SHOW = 9;
	private static final int LIST_ALL_SHOWS = 10;
	private static final int STORE_DATA = 11;
	private static final int RETRIEVE_DATA = 12;
	private static final int SELL_REGULAR_TICKETS = 13;
	private static final int SELL_ADVANCE_TICKETS = 14;
	private static final int SELL_STUDENT_ADVANCE_TICKETS = 15;
	private static final int PAY_CLIENT = 16;
	private static final int PRINT_TICKETS_FOR_DATE = 17;
	private static final int HELP = 18;
	private int commandCounter = 0;
	private int retrieveDataCounter = 0;

	/**
	 * Made private for singleton pattern.
	 * Conditionally looks for any saved data if the use requests for it.
	 */
	private UserInterface() {
		if (yesOrNo("Look for saved data and use it?")) {
			retrieveData();
		} 
	}

	/**
	 * Supports the singleton pattern
	 * @return the singleton object
	 */
	public static UserInterface instance() {
		if (userInterface == null) {
			return userInterface = new UserInterface();
		} else {
			return userInterface;
		}
	}
	/**
	 * Gets a token after prompting
	 * @param prompt - whatever the user wants as prompt
	 * @return - the token from the keyboard
	 */
	public String getToken(String prompt) {
		do {
			try {
				System.out.println(prompt);
				String line = reader.readLine();
				StringTokenizer tokenizer = new StringTokenizer(line,"\n\r\f");
				if (tokenizer.hasMoreTokens()) {
					return tokenizer.nextToken();
				}
			} catch (IOException ioe) {
				System.exit(0);
			}
		} while (true);
	}

	/**
	 * Queries for a yes or no and returns true for yes and false for no
	 * @param prompt The string to be prepended to the yes/no prompt
	 * @return true for yes and false for no
	 */
	private boolean yesOrNo(String prompt) {
		String more = getToken(prompt + " (Y|y)[es] or anything else for no");
		if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
			return false;
		}
		return true;
	}

	/**
	 * Converts the string to a number
	 * @param prompt the string for prompting
	 * @return the integer corresponding to the string
	 */
	public int getNumber(String prompt) {
		do {
			try {
				String item = getToken(prompt);
				Integer number = Integer.valueOf(item);
				return number.intValue();
			} catch (NumberFormatException nfe) {
				System.out.println("Please input a number ");
			}
		} while (true);
	}
	
	
	/**
	 * Converts the string to a float
	 * @param prompt the string for prompting
	 * @return the float corresponding to the string
	 */
	public float getFloat(String prompt) {
		do {
			try {
				String item = getToken(prompt);
				float number = Float.valueOf(item);
				return number;
			} catch (NumberFormatException nfe) {
				System.out.println("Please input a number ");
			}
		} while (true);
	}

	/**
	 * Prompts for a valid 16 digit credit card number
	 * @param prompt the String for prompting
	 * @return return the 16 digit card number as a String
	 */
	public String getCardNumber(String prompt) {
		do {
			try {
				String item = getToken(prompt);
				if(item.length() != 16) {
					System.out.println("Please input a valid 16 digit number");
				} else {
					Long number = Long.parseLong(item);
					return item;
				}
			} catch (NumberFormatException nfe) {
				System.out.println("Please input a valid 16 digit number");
			}
		} while (true);
	}

	/**
	 * Prompts for a date and gets a date object
	 * @param prompt the prompt
	 * @return the data as a Calendar object
	 */
	public Calendar getDate(String prompt) {
		do {
			try {
				Calendar date = new GregorianCalendar();
				String item = getToken(prompt);
				DateFormat dateFormat = SimpleDateFormat.getDateInstance(DateFormat.SHORT);
				date.setTime(dateFormat.parse(item));
				return date;
			} catch (Exception fe) {
				System.out.println("Please input a date as mm/dd/yy");
			}
		} while (true);
	}

	/**
	 * Prompts for a YearMonth and ensures that the YearMonth is a date in the future.
	 * @param prompt the String for prompting
	 * @return the data as a YearMonth object
	 */
	public YearMonth getYearMonth(String prompt) {
		do {
			try {
				String item = getToken(prompt);
				YearMonth yearMonth = YearMonth.parse(item);
				Calendar c = Calendar.getInstance();
				int year = c.get(Calendar.YEAR);
				int month = c.get(Calendar.MONTH);
				YearMonth current = YearMonth.of(year, month+1);
				if(current.isAfter(yearMonth) || current.equals(yearMonth)) {
					System.out.println("The expiration date entered has already expired.");
				} else if(current.isBefore(yearMonth)) {
					return yearMonth;
				}
			} catch (Exception fe) {
				System.out.println("Please input a valid expiration date as yyyy-mm");
			}
		} while (true);
	}

	/**
	 * Prompts for a command from the keyboard. If the value of the command is RETRIVE_DATA or HELP
	 * it'll return the value otherwise if the commandCounter is 1 then it'll create 
	 * a theater object, it'll then return the value of the command.
	 * @return a valid command
	 */
	public int getCommand() {
		do {
			try {
				int value = Integer.parseInt(getToken("Enter command:" + HELP + " for help"));
				if (value >= EXIT && value <= HELP) {
					if(value == RETRIEVE_DATA || value == HELP) {
						return value;
					} else {
						commandCounter++;
						if(commandCounter == 1) {
							theater = Theater.instance();
						}
						return value;
					}
				}
			} catch (NumberFormatException nfe) {
				System.out.println("Enter a number");
			}
		} while (true);
	}

	/**
	 * Prompts for a phone number and ensures that the phone number is a valid
	 * 10 digit number.
	 * @param prompt for prompting
	 * @return phone number as a string
	 */
	public String getPhoneNumber(String prompt) {
		do {
			try {
				String item = getToken(prompt);
				if(item.length() != 10) {
					System.out.println("Please input a valid 10 digit phone number");
				} else {
					Long number = Long.parseLong(item);
					return item;
				}
			} catch (NumberFormatException nfe) {
				System.out.println("Please input a valid 10 digit phone number");
			}
		} while (true);
	}

	/**
	 * Displays the help screen
	 */
	public void help() {
		System.out.println("Enter a number between " + EXIT + " and " + HELP + " as explained below:");
		System.out.println(EXIT + " to Exit");
		System.out.println(ADD_CLIENT + " to add a client");
		System.out.println(REMOVE_CLIENT + " to remove a client");
		System.out.println(LIST_ALL_CLIENTS + " to list all clients");
		System.out.println(ADD_CUSTOMER + " to add a customer");
		System.out.println(REMOVE_CUSTOMER + " to remove a customer ");
		System.out.println(ADD_CREDIT_CARD + " to add a credit card");
		System.out.println(REMOVE_CREDIT_CARD + " to remove a credit card");
		System.out.println(LIST_ALL_CUSTOMERS + " to list all customers");
		System.out.println(ADD_SHOW + " to add a show");
		System.out.println(LIST_ALL_SHOWS + " to list all shows");
		System.out.println(STORE_DATA + " to save data");
		System.out.println(RETRIEVE_DATA + " to retrieve data");
		System.out.println(SELL_REGULAR_TICKETS + " to sell regular tickets");
		System.out.println(SELL_ADVANCE_TICKETS + " to sell advance tickets");
		System.out.println(SELL_STUDENT_ADVANCE_TICKETS + " to sell student advance tickets");
		System.out.println(PAY_CLIENT + " to pay client");
		System.out.println(PRINT_TICKETS_FOR_DATE + " to print all tickets for a specific date");
		System.out.println(HELP + " for help");
	};

	/**
	 * adds client to the theater by prompting for client name,
	 * client address, and client phone number, and using that
	 * information to create a client in the clientList of the theater.
	 */
	public void addClient() {
		String name = getToken("Enter client name");
		String address = getToken("Enter address");
		String phoneNumber = getPhoneNumber("Enter phone");
		Client result;
		result = theater.addClient(name, address, phoneNumber);
		if (result == null) {
			System.out.println("Could not add client");
		} else {
			System.out.println(result);
		}
	}

	/**
	 * removes a client by prompting for the client Id
	 * and using that to search and remove the client
	 * associated with the client Id from the clientList
	 * of the theater.
	 */
	public void removeClient() {
		String clientId = getToken("Enter client Id");
		theater.removeClient(clientId);
	}

	/**
	 * Prints to the console all of the information about
	 * each client
	 */
	public void listAllClients() {
		if(theater.listAllClients().equals("")) {
			System.out.println("There are currently no clients in the theater's system");
		} else {
			System.out.println(theater.listAllClients());
		}
	}

	/**
	 * adds a customer by prompting for customer name, customer address
	 * and customer phone number. It then uses that information to create
	 * a customer in the customerList of the theater. After adding the 
	 * customer it'll ensure that the user adds a valid credit card. It
	 * prompts for a credit card number and ensure that the credit card is
	 * a valid 16 digit and does not already exist in the system. Then it'll
	 * take the expiration date in the yyyy-mm format and ensure the expiration
	 * date is valid. Then it asks if the user wants to add more cards and repeats
	 * the add credit card process until the user says no when prompted again.
	 */
	public void addCustomer() {
		String name = getToken("Enter customer name");
		String address = getToken("Enter address");
		String phoneNumber = getPhoneNumber("Enter phone");
		Customer customer;
		customer = theater.addCustomer(name, address, phoneNumber);
		if (customer == null) {
			System.out.println("Could not add customer");
		}
		System.out.println(customer);
		String customerId = customer.getCustomerId();
		do {
			String cardNumber = getCardNumber("Enter credit card number");
			if(theater.searchCreditCard(cardNumber)) {
				System.out.println("The credit card entered already exists");
			} else {
				YearMonth expirationDate = getYearMonth("Enter credit card expiration date as yyyy-mm");
				CreditCard result;
				result = theater.addCreditCard(customerId, cardNumber, expirationDate);
				if (result == null) {
					System.out.println("Could not add creditCard");
				} else {
					System.out.println(result + " was added.");
				}

				if (!yesOrNo("Add more credit cards?")) {
					break;
				}
			}
		} while (true);
	}

	/**
	 * removes a customer by prompting for a customer Id
	 * and using that information to search for the customer Id
	 * in the customerList of the theater and removes it if it exists.
	 */
	public void removeCustomer() {
		String customerId = getToken("Enter customer Id");
		theater.removeCustomer(customerId);
	}

	/**
	 * adds a credit card by prompting for a customer Id and checking to see
	 * if the customer exists. It'll then call the getCardNumber to get a
	 * valid 16 digit credit card number and search each customer in the theater's
	 * customerList to ensure that no one else has the same 16 digit credit card
	 * number. Then it'll call the getYearMonth to get a valid expiration date for
	 * the credit card. Then it asks if the user wants to add more cards and repeats
	 * the add credit card process until the user says no when prompted again.
	 */
	public void addCreditCard() {
		String customerId = getToken("Enter customerId");
		if(theater.searchCustomer(customerId)) {
			do {
				String cardNumber = getCardNumber("Enter credit card number");
				if(theater.searchCreditCard(cardNumber)) {
					System.out.println("The credit card entered already exists");
				} else {
					YearMonth expirationDate = getYearMonth("Enter credit card expiration date as yyyy-mm");
					CreditCard result;
					result = theater.addCreditCard(customerId, cardNumber, expirationDate);
					if (result == null) {
						System.out.println("Could not add creditCard");
					} else {
						System.out.println(result + " was added.");
					}

					if (!yesOrNo("Add more credit cards?")) {
						break;
					}
				}
			} while (true);
		} else {
			System.out.println("The customer Id entered does not exist.");
		}
	}

	/**
	 * removes a credit card from a customer from the customerList of the theater.
	 * It prompts for a customer Id and uses that to search for a customer. If the
	 * customer Id exists in the customerList of the theater it'll then prompt for
	 * a credit card number and search each customers credit cardList to see if that
	 * card exists. If the card exists in the customer's credit cardList it'll remove
	 * it otherwise it'll state the credit card was not found.
	 */
	public void removeCreditCard() {
		String customerId = getToken("Enter customer Id: ");
		if(theater.searchCustomer(customerId)) {
			String cardNumber = getCardNumber("Enter credit card number: ");
			if(theater.searchCreditCard(cardNumber)) {
				theater.removeCreditCard(customerId, cardNumber);
			} else {
				System.out.println("The credit card " + cardNumber + " was not found.");
			}
		} else {
			System.out.println("The customer Id " + customerId + " was not found.");
		}
	}

	/**
	 * Prints to the console all of the information of each customer.
	 */
	public void listAllCustomers() {
		if(theater.listAllCustomers().equals("")) {
			System.out.println("There are currently no customers in the theater's system");
		} else {
			System.out.println(theater.listAllCustomers());
		}
	}

	/**
	 * adds a show to the theater by prompting for a client Id if there exists a client
	 * associated with the client Id the user can then add a show name. It then calls
	 * getDate to prompt the user for a begin date and ensure that date is valid. Then
	 * it calls the getDate to prompt the user for an end date and ensure that date is 
	 * also valid. Then the following information entered is used to create a show and
	 * add it to a clients showList of the theater's clientList.
	 */
	public void addShow() {
		String clientId = getToken("Enter clientId");
		if(theater.searchClient(clientId)) {
			String name = getToken("Enter show name");
			Calendar beginDate = getDate("Enter begin date as mm/dd/yy");
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			String formatted = sdf.format(calendar.getTime());
			Date currentDate = calendar.getTime();
			try {
				String formatBeginDate = sdf.format(beginDate.getTime());
				currentDate = sdf.parse(formatted);
				Date dateBegin = sdf.parse(formatBeginDate);
				if(currentDate.before(dateBegin)) {
					Calendar endDate = getDate("Enter end date as mm/dd/yy");
					String formatEndDate = sdf.format(endDate.getTime());
					Date dateEnd = sdf.parse(formatEndDate);
					if(dateBegin.before(dateEnd)) {
						Show result;
						float ticketPrice = getFloat("How much is a regular ticket to the show? ");
						result = theater.addShow(name, clientId, beginDate, endDate, ticketPrice);
						if (result == null) {
							System.out.println("The show was not added");
						} else {
							System.out.println(result + "was added");
						}
					} else {
						System.out.println("The begin date cannot be after the end date");
					}
				} else {
					System.out.println("The begin date has already passed.");
				}
			} catch(Exception e) {
				System.out.println("The show was not added because an error has occured.");
			}
		} else {
			System.out.println("The client " + clientId  + " was not found");
		}
	}

	/**
	 * removes a show by calling getDate to get a begin date and
	 * calling get Date to get an end date then searches for a 
	 * show from each clients showList from the theaters clientList.
	 * IF both date matches a show in a clients showList the show is
	 * then removed.
	 */
	public void removeShow() {
		Calendar beginDate = getDate("Enter begin date");
		Calendar endDate = getDate("Enter end date");
		theater.removeShow(beginDate, endDate);
	}

	/**
	 * lists all shows being performed and its information. Each 
	 * show from a clients showList from the theater's clientList
	 * will be displayed to the console.
	 */
	public void listAllShows() {
		if(theater.listAllCustomers().equals("")) {
			System.out.println("There are currently no shows in the theater's system");
		} else {
			System.out.println(theater.listAllShows());
		}
	}

	/**
	 * serializes the Theater information
	 */
	private void storeData() {
		if (Theater.save()) {
			System.out.println(" The Theater has been successfully saved in the file TheaterData \n" );
		} else {
			System.out.println(" There has been an error in saving \n" );
		}
	}

	/**
	 * serializes the Theater information then exits the application
	 */
	private void exit() {
		if (Theater.save()) {
			System.out.println(" The Theater has been successfully saved in the file TheaterData and the application has been closesd. \n" );
			System.exit(0);
		} else {
			System.out.println(" There has been an error in saving \n" );
		}
	}

	/**
	 * Method to be called for retrieving saved data.
	 * Uses the appropriate Theater method for retrieval.
	 */
	private void retrieveData() {
		if(commandCounter == 0) {
			commandCounter += 2;
			retrieveDataCounter++;
			try {
				Theater tempTheater = Theater.retrieve();
				if (tempTheater != null) {
					System.out.println(" The theater has been successfully retrieved from the file TheaterData \n" );
					theater = tempTheater;
				} else {
					System.out.println("File doesnt exist; creating new theater" );
					theater = Theater.instance();
				}
			} catch(Exception cnfe) {
				cnfe.printStackTrace();
			}
		} else if(retrieveDataCounter >= 1){
			System.out.println("Data has already been retrieved. ");
		} else {
			System.out.println("Data cannot be retrieved because data related commands have been entered previously. ");
		}
	}
	
	/**
	 * Method sells a Regular ticket
	 */
	public void sellRegularTickets() {
		sellTicket(Theater.REGULAR);
	}
	
	/**
	 * Method sells an Advance ticket
	 */
	public void sellAdvanceTickets() {
		sellTicket(Theater.ADVANCE);
	}
	
	/**
	 * Method sells a Student Advance ticket
	 */
	public void sellStudentAdvanceTickets() {
		sellTicket(Theater.STUDENT_ADVANCE);
	}
	
	/**
	 * Method is used to sell regular tickets, advance tickets 
	 * and advance student tickets. Method prompts user for
	 * quantity, customerId, cardNumber, and dateOfShow. Uses
	 * the theater's sellTickets method to create the ticket and
	 * sell it to the customer.
	 * @param type, the type of ticket to be created
	 */
	public void sellTicket(int type) {
		int quantity = getNumber("Enter quantity of tickets being purchased");
		String customerId = getToken("Enter customer Id");
		if(theater.searchCustomer(customerId)) {
			String cardNumber = getCardNumber("Enter credit card number: ");
			if(theater.searchCustomerCreditCard(customerId, cardNumber)) {
				Calendar dateOfShow = getDate("Enter the date of the show the ticket is being purchased for");
				if(theater.searchShow(dateOfShow) != null) {
					theater.sellTickets(type, quantity, customerId, cardNumber, dateOfShow);
				}
			} else {
				System.out.println("The credit card " + cardNumber + " was not found.");
			}
		} else {
			System.out.println("The customer Id " + customerId + " was not found.");
		}	
	}
	
	/**
	 * Method to be called for paying the client.
	 * Uses Theater getClientBalance and payClient methods.
	 */
	public void payClient() {
		float clientBalance;
		float amountToBePaid;
		DecimalFormat df = new DecimalFormat("#.##");
		String clientId = getToken("Enter client Id");
		if(theater.searchClient(clientId)) {
			clientBalance = theater.getClientBalance(clientId);
			System.out.println("The balance for " + clientId + " is: $" + df.format(clientBalance));
			amountToBePaid = getFloat("What is the amount to be paid to the client? ");
			if(amountToBePaid > 0 && amountToBePaid <= clientBalance) {
				theater.payClient(clientId, amountToBePaid);
			}
			else {
				System.out.println("The amount to be paid is invalid");
			}
		}
		else {
			System.out.println("The client Id " + clientId + " was not found.");
		}
	}

	/**
	 * Method prints all tickets for a date entered by the clerk. 
	 * It calls the theater's printAllTickets method.
	 */
	public void printTickets() {	
		Iterator<ParticipantItem> iterator = theater.getCatalog().iterator();
		if(!iterator.hasNext()) {
			System.out.println("There are currently no tickets in the theater's system");
		} else {
			Calendar date = getDate("Enter begin date as mm/dd/yy to return all purchased tickets for that date.");
			theater.printAllTickets(date);
		}
	}

	/**
	 * Orchestrates the whole process.
	 * Calls the appropriate method for the different functionalities.
	 */
	public void process() {
		int command;
		help();
		while (EXIT <= (command = getCommand()) || (command = getCommand()) <= HELP ) {
			switch (command) {
			case EXIT:   			    		exit();
			break;
			case ADD_CLIENT:   					addClient();
			break;
			case REMOVE_CLIENT:     			removeClient();
			break;
			case LIST_ALL_CLIENTS:  			listAllClients();
			break;
			case ADD_CUSTOMER:     				addCustomer();
			break;
			case REMOVE_CUSTOMER:   			removeCustomer();
			break;
			case ADD_CREDIT_CARD:   			addCreditCard();
			break;
			case REMOVE_CREDIT_CARD:			removeCreditCard();
			break;
			case LIST_ALL_CUSTOMERS:			listAllCustomers();
			break;
			case ADD_SHOW:      				addShow();
			break;
			case LIST_ALL_SHOWS:  				listAllShows();
			break;
			case STORE_DATA:        			storeData();
			break;
			case RETRIEVE_DATA:     			retrieveData();
			break;  
			case SELL_REGULAR_TICKETS:     		sellRegularTickets();
			break; 
			case SELL_ADVANCE_TICKETS:     		sellAdvanceTickets();
			break;
			case SELL_STUDENT_ADVANCE_TICKETS:  sellStudentAdvanceTickets();
			break;
			case PAY_CLIENT:     				payClient();
			break;
			case PRINT_TICKETS_FOR_DATE:     	printTickets();
			break;
			case HELP:              			help();
			break; 
			}
		}
	}

	/**
	 * The method to start the application. Simply calls process().
	 * @param args not used
	 */
	public static void main(String[] args) {
		UserInterface.instance().process();
	}
}