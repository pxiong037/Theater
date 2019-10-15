package GroupProject1_Iteration2;
/**
 * @author Prechar Xiong, Christopher Corcoran, Thomas Wohlever
 * Date: 07/24/19
 * ICS-372-01 Object Oriented Design and Implementation
 * The Class IdServer stores the unique Id for each participant Item
 */
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class IdServer implements Serializable {
	private static final long serialVersionUID = 1L;
	private  int idCounter;
	private static IdServer server;

	/**
	 * Private constructor for singleton pattern
	 */
	private IdServer() {
		idCounter = 1;
	}

	/**
	 * Supports the singleton pattern
	 * @return the singleton object
	 */
	public static IdServer instance() {
		if (server == null) {
			return (server = new IdServer());
		} else {
			return server;
		}
	}

	/**
	 * Getter for id
	 * @return id of the member
	 */
	public int getId() {
		return idCounter++;
	}

	/** 
	 * String form of the collection
	 */
	@Override
	public String toString() {
		return ("IdServer" + idCounter);
	}

	/**
	 * Retrieves the server object
	 * @param input inputstream for deserialization
	 */
	public static void retrieve(ObjectInputStream input) {
		try {
			server = (IdServer) input.readObject();
		} catch(IOException ioe) {
			ioe.printStackTrace();
		} catch(Exception cnfe) {
			cnfe.printStackTrace();
		}
	}

	/**
	 * Supports serialization
	 * @param output the stream to be written to
	 */
	private void writeObject(java.io.ObjectOutputStream output) throws IOException {
		try {
			output.defaultWriteObject();
			output.writeObject(server);
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}

	/**
	 * Supports serialization
	 * @param input the stream to be read from
	 */
	private void readObject(java.io.ObjectInputStream input) throws IOException, ClassNotFoundException {
		try {
			input.defaultReadObject();
			if (server == null) {
				server = (IdServer) input.readObject();
			} else {
				input.readObject();
			}
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
