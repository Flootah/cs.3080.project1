import java.util.Scanner;
import java.io.*;

public class UserInterface {
	/*
	 * number used for state of the state machine.
	 * will determine prompts and what responses are valid.
	 */
	private int state;
	UIDictionary dictionary;
	Scanner sc;
	private int[][] matrix;
	private int	numEquations;
	
	/*
	 * constructor for the UserInterface object. Initializes state
	 * to 0 and I/O Scanner.
	 */
	public UserInterface() {
		/*
		 * initialization of state and scanner
		 */
		state = 1;
		sc = new Scanner(System.in);
		dictionary = new UIDictionary();
	}
	
	/*
	 * Begins execution and loop of the UI system.
	 */
	public void start() {
		String response = "";
		while(true) {
			switch(state) {
			case 0: // exit state
				System.out.println(dictionary.exit);
				System.exit(0);
				break;
			case 1: // main menu
				// prompt user and store their response string
				response = prompt(dictionary.mainMenu);
				switch(response) {
				case "a":
					state = 2;
					break;
				case "b":
					state = 3;
					break;
				case "c":
					state = 0;
					break;
				}
				break;
			case 2: // manual equation entry: number of equations
				response = prompt(dictionary.numEq);
				// try to parse response as Integer. 
				int intResponse = parseNumEq(response);
				if(intResponse == -1) {
					System.out.println("Number format exception! Please try again.\n");
					continue;
				}
				numEquations = intResponse;
				matrix = new int[numEquations][numEquations];
				state = 3;
				break;
			case 3: // manual equation entry: row values
				// for each equation (row)
				for(int i = 0; i < numEquations; i++) {
					response = prompt("Enter row " + (i+1) + "of the matrix"
										+ "This should be " + numEquations + " numbers seperated by a space.");
					Scanner s = new Scanner(response);
					for(int j = 0; j < numEquations; j++) {
						try {
							matrix[i][j] = s.nextInt();
						} catch(Exception e) { // if response has an exception.
							System.out.println("Number format exception! Please try again.\n");
							break;
						}
						// if response has more numbers than necessary
					}
				}
				state = 4;
				break;
			case 4: // matrix complete, do a thing.
				System.out.println("Matrix complete!");
				printMatrix();
				break;
			}
		}
	}

	/*
	 * prints matrix to the screen for debugging and other viewing stuffs.
	 */
	private void printMatrix() {
        // Loop through all rows 
        for (int i = 0; i < numEquations; i++) {
            // Loop through all elements of current row 
    		System.out.printf("["); 
            for (int j = 0; j < numEquations; j++) {
            	if(j == numEquations-1) {
            		System.out.printf("%3d", matrix[i][j]);
            	} else {
            		System.out.printf("%3d", matrix[i][j]);
            		System.out.print(" ");
            	}
            }
            System.out.println("]");
        }
        System.out.println();
	}

	/*
	 * Attempts to parse a string as an Integer for the number of equations used in our system.
	 * Allows all values between 1-10 inclusive, all ourside values or invalid formats will return -1.
	 * otherwise, the value in the string is returned as an int.
	 */
	private int parseNumEq(String s) {
		// temp int to store value
		int value;
		try {
			// try to parse int from response, store for later return
			value = Integer.parseInt(s);
			// if parsed value is > 10, return error value
			if(1 > value || value > 10) return -1; // if not between 1 and 10, return -1
		} catch (NumberFormatException e) {
			// if parsed value isn't an Integer, return error value
			return -1;
		}
		return value;
	}
	
	private String prompt(String print) {
		System.out.println(print);
		System.out.println();
		String response = sc.nextLine();
		if(response.equals("exit")) state = 0;
		return response.toLowerCase();
	}

}
