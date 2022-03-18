//
// Name: Just, Kyle
// Project: #5
// Due: 05/13/2021
// Course: cs-2400-03-s21
//
// Description:
// This is the main method that will run my program, and load the data from the
// files into my data structures.
//
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AirportApp {
	
	//////////////////////////////////////////////////////////////////////////////
	//				Static Fields for Main and Helper Methods				   //
	////////////////////////////////////////////////////////////////////////////
	//Will stop program if set to false
	static boolean running;
	//This is to represent the connections of all the airports
	private static GraphInterface<String> graph = new DirectedGraph<>();
	//This will be used to store the actual names of all the abbreviations
	private static DictionaryInterface<String, String> fullNames = new LinkedDictionary<>();
	//Will collect user input
	private static Scanner input = new Scanner(System.in);
	
	//////////////////////////////////////////////////////////////////////////////
	//								MAIN									   //
	////////////////////////////////////////////////////////////////////////////
	public static void main(String[] args){
		//Will be used to hold data from the .split()
		String[] loadingArray;
		//This will read data from the given file
		Scanner fileReader;
		
		
		
		/*
		 * File Loading section for airports.csv, will thow exeption if file is not found
		 */
		try {
			fileReader = new Scanner(new File("airports.csv"));

			while(fileReader.hasNext()) {
				loadingArray = fileReader.nextLine().split(",");
				if(loadingArray.length > 2)
					throw new RuntimeException("Initial input data is not formatted correctly");
				graph.addVertex(loadingArray[0]);
				fullNames.add(loadingArray[0], loadingArray[1]);
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		/*
		 * File Loading section for distances.csv, will thow exeption if file is not found,
		 * if the data is formatted incorrectly
		 */
		try {
			fileReader = new Scanner(new File("distances.csv"));
			
			while(fileReader.hasNext()) {
				loadingArray = fileReader.next().split(",");
				if(loadingArray.length > 3)
					throw new RuntimeException("Initial input data is not formatted correctly");
				graph.addEdge(loadingArray[0], loadingArray[1], Double.parseDouble(loadingArray[2]));
			}
		}catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}catch (RuntimeException e) {
			System.out.println(e.getMessage());
		}
		
		
		/*
		 * This is what will be running the collection of user input
		 */
		System.out.println("Airports by K. Just \n");
		running = true;
		printMenu();//Initial menu printing
		while(running) {
			System.out.println("Command?");
			menuOptions(input.nextLine());//See method
		}
		input.close();
	}
	
	/**
	 * Will be used to decide which menu option to run based on a users input
	 * @param option
	 * 	selected letter representing a function
	 */
	private static void menuOptions(String option) {
		
		String[] getter; //This will collectet the parsed data from the user input
		
		switch(option.toUpperCase()) {
		//////////////////////////////////////////////////////////////////////////////
		//							Query										   //
		////////////////////////////////////////////////////////////////////////////
		case "Q":
			//Query the airport information by entering the airport code.
			System.out.print("Airport code: ");
			/*
			 * This will use the fullNames Dictionary to get the full name of the airport
			 * I also include the .toUpperCase() to circumvent any capitalization issues
			 */
			try {
				System.out.println(fullNames.getValue(input.nextLine().toUpperCase()));
			}
			catch(Exception e) {
				System.out.println("Please enter a valid airport code");
				menuOptions("Q");
				}
			break;
		//////////////////////////////////////////////////////////////////////////////
		//							Distance									   //
		////////////////////////////////////////////////////////////////////////////
		case "D":
			//Find Minimum Distance between two airports
			System.out.println("Airport codes: ");
			StackInterface<String> stack = new LinkedStack<>(); //This stack will be printed later to show shortest path
			getter = input.nextLine().toUpperCase().split(" "); //getter is loaded with the
			if(getter.length == 2) //If above or under two, then it was not formatted correctly
				try {
					System.out.println("The minimum distance between \"" + fullNames.getValue(getter[0]) + "\" and \""
										+ fullNames.getValue(getter[1]) + "\" is "
										+ graph.getCheapestPath(getter[0], getter[1], stack)
										+ " through the route: ");
					while(!stack.isEmpty())
						System.out.println(fullNames.getValue(stack.pop())); //This will print out the stack from getCheapestPath()
				}catch(Exception e) {
					System.out.println(e.getMessage() + "\nPlease try again"); //If any of the methods called throw an exception then I'll know which one had the issue
					menuOptions("D");

				}
			else {
				System.out.println("Please enter two airport codes");
				menuOptions("D"); //will recall this part of the switch statement for the user to re-input the codes
			}
			break;
		//////////////////////////////////////////////////////////////////////////////
		//							Insert										   //
		////////////////////////////////////////////////////////////////////////////
		case "I":
			//Insert a connection between two airports
			System.out.println("Airport codes and distance: ");
			getter = input.nextLine().toUpperCase().split(" ");
			
			if (getter.length == 3) { //enures there is 3 arguments
				try {
					if (graph.addEdge(getter[0], getter[1], Double.parseDouble(getter[2]))) { //If this command is succesful then the following will print
						System.out.println("You have inserted a connection from \""
											+ fullNames.getValue(getter[0])
											+ "\" to \""
											+ fullNames.getValue(getter[1])
											+ "\" with a distance of "
											+ getter[2]);
					}
				} catch(NumberFormatException n) { //If user inputs a non number in the weight section
					System.out.println("The entry \"" + getter[2] + "\" is not a valid weight \nPlease try again");
					menuOptions("I");
				} catch (RuntimeException e) { //If any called methods throw an exception
					System.out.println(e.getMessage());
				}
			}else {
				System.out.println("Please enter two airport codes and the distance between them \nPlease try again");
				menuOptions("I");
			}
			break;
		//////////////////////////////////////////////////////////////////////////////
		//							Remove										   //
		////////////////////////////////////////////////////////////////////////////
		case "R":
			//Remove an existing connection between two airports
			System.out.println("Airport codes: ");
			getter = input.nextLine().toUpperCase().split(" ");
			if(getter.length == 2) {
				
				try {
					if(graph.removeEdge(getter[0], getter[1])) {
						System.out.println("The connection from \""
										  + fullNames.getValue(getter[0])
										  + "\" to \""
										  + fullNames.getValue(getter[1])
										  + "\" has been removed");
					}else
						System.out.println("The removal could not be completed");
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				
			}else {
				System.out.println("Please enter two airport codes \nPlease try again");
				menuOptions("R");
			}
			break;
		//////////////////////////////////////////////////////////////////////////////
		//							Help										   //
		////////////////////////////////////////////////////////////////////////////
		case "H":
			//Display this message (re-display the menu options). 
			printMenu();
			break;
		//////////////////////////////////////////////////////////////////////////////
		//							Exit										   //
		////////////////////////////////////////////////////////////////////////////
		case "E":
			running = false;
			break;
		default:
			System.out.println(option.toUpperCase() + " is not a valid command. "
					+ "Use \"H\" for a list of valid commands");
			break;
		}
	}
	
	/**
	 * This method will print all of the options for menuOptions() that are valid in a menu format
	 */
	private static void printMenu() {
		System.out.println("Q Query the airport information by entering the airport code.");
		System.out.println("D Find the minimum distance between two airports.");
		System.out.println("I Insert a connection by entering two airport codes and distance.");
		System.out.println("R Remove an existing connection by entering two airport codes.");
		System.out.println("H Display this message.");
		System.out.println("E Exit.");
	}

}
