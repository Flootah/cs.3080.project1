import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.*;
import java.text.DecimalFormat;

public class UserInterface {
	/*
	 * number used for state of the state machine.
	 * will determine prompts and what responses are valid.
	 */
	private int state;
	UIDictionary dictionary;
	Calculator calculator;
	Scanner sc;
	private int[][] matrix;
	private int	numEquations;
	private String[] variables;
	private boolean inputMethod;	// 1 = manual, 0 = file entry
	DecimalFormat df;
	
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
		variables = dictionary.variables;
		df = new DecimalFormat("##.##########");
	}
	
	/*
	 * Begins execution and loop of the UI system.
	 */
	public void start() {
		String response = "";
		while(true) {
			switch(state) {
			/*
			 * State 0: Exit state
			 * exits the program after printing an exit dialogue.
			 * exit code returns as exiting normally.
			 */
			case 0:
				System.out.println(dictionary.exit);
				System.exit(0);
				break;
			/*
			 * State 1: Main Menu
			 * User will choose between two options:
			 * a: Manually entering a matrix
			 * b: Choosing a matrix file
			 * Upon completion, goes to state 2.
			 */
			case 1: // main menu
				// prompt user and store their response string
				response = prompt(dictionary.mainMenu);
				switch(response) {
				case "a":
					inputMethod = true;
					state = 2;
					break;
				case "b":
					inputMethod = false;
					state = 2;
					break;
				}
				break;
			/*
			 * State 2: Equation Number Entry
			 * User will enter the number of equations that will be in the matrix.
			 * Only integer values between 1-10 will be accepted.
			 * On completion, goes to state 3.
			 */
			case 2: // manual equation entry: number of equations
				response = prompt(dictionary.numEq);
				if(response.equals("back")) {
					state = 1;
					break;
				}
				// try to parse response as Integer. 
				int intResponse = parseNumEq(response);
				if(intResponse == -1) {
					System.out.println(dictionary.numberEntryError);
					continue;
				}
				numEquations = intResponse;
				matrix = new int[numEquations][numEquations+1];
				if(inputMethod) {
					state = 3;
				}
				else {
					state = 5;
				}
				break;
			/*
			 * State 3: Matrix Value Entry
			 * User will input the values of the matrix, row by row.
			 * Only integer values will be accepted, separated by a space.
			 * Row entries with too few or too many values are not accepted.
			 * On completion, goes to state 4.
			 */
			case 3: // manual equation entry: row values
				// for each equation (row)
				for(int i = 0; i < numEquations; i++) {
					response = prompt("Enter row " + (i+1) + " of the matrix. \n"
										+ "This should be " + (numEquations+1) + " numbers seperated by a space.");
					if(response.equals("exit")) {
						state = 0;
						break;
					}
					Scanner s = new Scanner(response);
					int[] a = new int[numEquations+1];	// int array to hold row values.
					int k = 0;
					try {								// iterate through input, placing into array.
						while(s.hasNextInt()) {
							a[k] = s.nextInt();
							k++;
						}
					} catch (ArrayIndexOutOfBoundsException e) {	// catches all inputs > than needed
						System.out.println(dictionary.numberEntryError);
						System.out.println("Too many values inputted.");
						i--;
						continue;
					}
					if(k < numEquations+1) {
						System.out.println(dictionary.numberEntryError);
						System.out.println("Too few values inputted.");
						i--;
						continue;
					}
					
					for(int j = 0; j < numEquations + 1; j++) {
						try {
							matrix[i][j] = a[j];
						} catch(Exception e) { // if response has an exception.
							System.out.println(dictionary.numberEntryError);
							break;
						}
					}
					s.close();
					state = 4;
				}
				break;
			/*
			 * State 4: Complete Matrix printing and storage.
			 * Prints @matrix now that it is complete.
			 * Upon completion, goes to state 0.
			 */
			case 4: // matrix complete, do a thing.
				System.out.println("Matrix input:");
				calculator = new Calculator(matrix);
				printMatrix(matrix);
				double[] answers = calculator.calculate();
				System.out.println();
				System.out.println();
				for(int i = 0; i < answers.length; i++) {
					System.out.println(variables[i] + ": " + smartRound(answers[i]));
				}
				System.out.println();
				state = 0;
				break;
			/*
			 * State 5: Filename entry
			 * User will enter a filename for a text file with a complete matrix.
			 * If file isn't found, a new one is asked for.
			 * Upon completion, goes to state 6.
			 */
			case 5: // filename entry
				response = prompt(dictionary.fileEntry);
				if(response.equals("back")) {
					state = 2;
					break;
				}
			    try {
			        File file = new File(response);
			        Scanner s = new Scanner(file);
			        int rcount = 0;
			        int ccount = 0;
			        while(s.hasNextLine()) {
			        	String line = s.nextLine();
			        	Scanner line_s = new Scanner(line);
			        	while(line_s.hasNext()) {
				        	matrix[ccount][rcount] = line_s.nextInt();
				        	rcount++;
			        	}
			        	line_s.close();
			        	if(rcount < numEquations+1) {
				        	System.out.println(dictionary.numberEntryError);
							System.out.println("too few items in row " + (ccount+1));
				        	break;
			        	}
			        	rcount = 0;
			        	ccount++;
			        }
			        if(ccount < numEquations) {
			        	System.out.println(dictionary.numberEntryError);
						System.out.println("too few columns, make sure there are " + (numEquations+1) + ".");
			        	break;
			        }
			        s.close();
			    } catch (FileNotFoundException e) {
			        System.out.println("File not found! Please try again.");
					break;

			    } catch (ArrayIndexOutOfBoundsException e) {
					System.out.println(dictionary.numberEntryError);
					System.out.println("too many items. check your matrix file");
					break;
			    } catch (NumberFormatException e) {
					// if parsed value isn't an Integer, return error value
					System.out.println(dictionary.numberEntryError);
					System.out.println("Non-Integer value entered.");
				} catch (InputMismatchException e) {
					// if parsed value isn't an Integer, return error value
					System.out.println(dictionary.numberEntryError);
					System.out.println("Non-Integer value entered.");
				}
				state = 4;
				break;
			default:
				prompt(dictionary.badState);
			}
		}
	}

	/*
	 * prints matrix to the screen for debugging and other viewing stuffs.
	 */
	private void printMatrix(int[][] x) {
        // Loop through all rows 
        for (int i = 0; i < numEquations; i++) {
            // Loop through all elements of current row 
    		System.out.printf("%6s", "["); 
            for (int j = 0; j < numEquations + 1; j++) {
            	System.out.printf("%4d", x[i][j]);
            	if(j != numEquations) System.out.print(" ");
            }
            System.out.println(" ]");
        }
        System.out.println();
	}
	
	public int[][] getMatrix() {
		return matrix;
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
		System.out.println(dictionary.divisor);
		System.out.println(print);
		String response = sc.nextLine();
		if(response.equals("exit")) state = 0;
		return response.toLowerCase();
	}
	
	/*
	 * takes in a double and returns a string with a possible rounded number.
	 * 
	 * if the number is very close to an integer with a precision of 9 places
	 * (ex. 2.999999999), return a rounded string to that nearest int.
	 * if the number is not close to a whole number, return the input double
	 * as a string.
	 */
	private String smartRound(double x) {
		try {
			return Integer.toString(Integer.parseInt(df.format(x)));
		} catch (NumberFormatException e) {
			return Double.toString(x);
		}
	}

}
