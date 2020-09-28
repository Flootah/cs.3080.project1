/*
 * Public dictionary to store string that are to be used by the UserInterface class.
 */
public class UIDictionary {
	public String debug;
	public String exit;
	public String mainMenu;
	public String fileEntry;
	public String numEq;
	public String enterRow;

	public UIDictionary() {
		mainMenu =		"Choose an option: \n"
							+ "a. Enter Equation\n"
							+ "b. Enter Filename\n"
							+ "c. Exit\n";
		numEq =			"Enter the number of equations in the system.\n"
							+ "You may enter any number 1-10.\n";
		debug =			"hey, you made it here!\n"
							+ "and this is a new line.\n"
							+ "now exiting...\n";
		exit = 			"Exiting...\n";
	}
}
