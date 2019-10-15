package GroupProject1_Iteration2;
/**
 * @author Prechar Xiong, Christopher Corcoran, Thomas Wohlever
 * Date: 07/24/2019
 * ICS-372-01 Object Oriented Design and Implementation
 * The class ParticipantItem is the super class for all clients and customers
 * it contains the general information of those participants
*/
import java.io.Serializable;

/**
 * This abstract class is the super class for all customers and clients in the theater
 * It stores the general information of each participant item.
 */
public abstract class ParticipantItem implements Matchable<String>, Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String address;
	private String phoneNumber;
	private String id;

	/**
	 * Takes in the title and id stores them.
	 * @param name the name of the participant
	 * @param address the address of the participant
	 * @param phoneNumber the phone number of the participant
	 */
	public ParticipantItem(String name, String address, String phoneNumber) {
		this.address = address;
		this.name = name;
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the name of the participant
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the id of the participant
	 */
	public String getId() {
		return id;
	}

	/**
	 * sets the participants id to the param id
	 * @param id the id to replace the participants id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the address of the participant
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @return phoneNumber of the participant
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * Returns true if and only if the supplied id is the same as the id of the
	 * participant.
	 */
	@Override
	public boolean matches(String id) {
		return (this.id.equals(id));
	}

	/**
	 * Format phone number for a readable display.
	 * @param phoneNumber the phone number
	 * @return the phoneNumber as a string properly formatted
	 */
	public String formatPhoneNumber(String phoneNumber) {
		String areaCode = "";
		String number1 = "";
		String number2 = "";
		for (int i = 0; i <= 9; i++) {
			if (i <= 2) {
				areaCode += String.valueOf(phoneNumber.charAt(i));
			} else if (i <= 5) {
				number1 += String.valueOf(phoneNumber.charAt(i));
			} else {
				number2 += String.valueOf(phoneNumber.charAt(i));
			}
		}

		return "(" + areaCode + ")" + number1 + "-" + number2;
	}

	/**
	 * @return all information about the participant as a string
	 */
	public String display() {
		return "ID: " + id + "\n" + "Name: " + name + "\n" + "Address: " + address + "\n" + "Phone Number: "
				+ formatPhoneNumber(phoneNumber);
	}
}
