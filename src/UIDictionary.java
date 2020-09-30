/*
 * Public dictionary to store strings that are to be used by the UserInterface class.
 */
public class UIDictionary {

	public String mainMenu;
	public String fileEntry;
	public String numEq;
	public String numberEntryError;
	public String debug;
	public String exit;
	public String[] variables;
	public String badState;

	public UIDictionary() {
		mainMenu =			"Choose an option: \n"
								+ "a: 	Enter an equation manually.\n"
								+ "b: 	Enter matrix file. \n"
								+ "exit: 	Exit Program\n";
		
		numEq =				"Enter the number of equations in the system.\n"
								+ "You may enter any number 1-10.\n"
								+ "Type 'back' to return to main menu";
		
		numberEntryError =	"Number format exception!";
		
		fileEntry =			"Enter the name of the file containing the matrix.\n"
								+ "Please include the extension.\n"
								+ "Type 'back' to return to equation quantity input.";
		
		debug =				"hey, you made it here!\n"
								+ "and this is a new line.\n"
								+ "now exiting...\n";
		
		exit = 				"Exiting...\n";
		variables = new String[] {"x", "y", "z", "a", "b", "c", "d", "e", "f", "g", "h", 
								"i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", 
								"t", "u", "v", "w"
								};
		badState = 			"Illegal state accessed! This is definietly a bug within the UI loop.\n"
							+ "The program will now restart. Press Enter to contine...";
	}
}
