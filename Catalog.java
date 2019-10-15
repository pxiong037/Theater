package GroupProject1_Iteration2;
/**
 * @author Prechar Xiong, Christopher Corcoran, Thomas Wohlever
 * Date: 07/24/2019
 * ICS-372-01 Object Oriented Design and Implementation
 * This class is the collection class for all ParticipantItem objects
*/
import java.io.IOException;

public class Catalog extends ItemList<ParticipantItem, String> {
	private static final long serialVersionUID = 1L;
	private static Catalog catalog;

	/**
	 * Private constructor for singleton pattern
	 */
	private Catalog() {
	}

	/**
	 * Supports the singleton pattern
	 * @return the singleton object
	 */
	public static Catalog instance() {
		if (catalog == null) {
			return (catalog = new Catalog());
		} else {
			return catalog;
		}
	}

	/**
	 * Removes a participant from the catalog
	 * @param participantItemId participant Id
	 * @return true iff participant could be removed
	 */
	public boolean removeParticipantItem(String participantItemId) {
		ParticipantItem participantItem = search(participantItemId);
		if (participantItem == null) {
			return false;
		} else {
			return super.remove(participantItem);
		}
	}

	/**
	 * Inserts a participant into the catalog
	 * @param participant the participant to be inserted
	 * @return true iff the participant could be inserted. Currently always true
	 */
	public boolean insertParticipantItem(ParticipantItem participantItem) {
		return super.add(participantItem);
	}

	/**
	 * Supports serialization
	 * @param output the stream to be written to
	 */
	private void writeObject(java.io.ObjectOutputStream output) throws IOException {
		output.defaultWriteObject();
		output.writeObject(catalog);
	}

	/**
	 * Supports deserialization
	 * @param input the stream to be read from
	 */
	private void readObject(java.io.ObjectInputStream input) throws IOException, ClassNotFoundException {
		input.defaultReadObject();
		if (catalog == null) {
			catalog = (Catalog) input.readObject();
		} else {
			input.readObject();
		}
	}
}
