package GroupProject1_Iteration2;
/**
 * @author Prechar Xiong, Christopher Corcoran, Thomas Wohlever
 * Date: 07/24/19
 * ICS-372-01 Object Oriented Design and Implementation
 * The Class CreditCard creates a credit card belonging to a customer.
 * A credit card requires a customer id, card number, and expiration date.
 * There are methods for displaying credit card information
 */
import java.io.*;
import java.time.*;

public class CreditCard implements Serializable {
	private static final long serialVersionUID = 1L;
	private String customerId;
	private String cardNumber;
	private YearMonth expirationDate;
	
	/**
	 * Instantiates a new credit card.
	 * @param customerId the customer id
	 * @param cardNumber the card number
	 * @param expirationDate the expiration date
	 */
	public CreditCard(String customerId, String cardNumber, YearMonth expirationDate) {
		this.customerId = customerId;
		this.cardNumber = cardNumber;
		this.expirationDate = expirationDate;
	}
	
	/**
	 * Gets the card number.
	 * @return the card number
	 */
	public String getCardNumber() {
		return this.cardNumber;
	}
	
	/**
	 * Gets the customer id.
	 * @return the customer id
	 */
	public String getCustomerId() {
		return customerId;
	}
	
	/**
	 * Card number formatter ensures a readable card number.
	 * @param cardNumber the card number
	 * @return the string
	 */
	public String cardNumberFormatter(String cardNumber) {
		String number1 = "";
		String number2 = "";
		String number3 = "";
		String number4 = "";
		for(int i = 0; i <= 15 ; i++) {
			if(i <= 3) {
				number1 += String.valueOf(cardNumber.charAt(i));
			} else if(i <= 7) {
				number2 += String.valueOf(cardNumber.charAt(i));
			} else if(i <= 11) {
				number3 += String.valueOf(cardNumber.charAt(i));
			} else {
				number4 += String.valueOf(cardNumber.charAt(i));
			}
		}
		
		return number1 + " " + number2 + " " + number3 + " " +  number4;
	}
	
	/**
	 * @return all customer information as a string
	 */
	public String toString() {
		return "Customer ID: " + customerId + "\n" +
				"Card Number: " + cardNumberFormatter(cardNumber) + "\n" + 
				"Card Expiration Date: " + expirationDate;
	}
	
	/**
	 * @return the customer's credit cards as a string
	 */
	public String customerCards() {
		return "Card Number: " + cardNumberFormatter(cardNumber) + "\n" + 
				"Card Expiration Date: " + expirationDate;
	}
}
