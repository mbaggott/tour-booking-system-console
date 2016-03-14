/*
 * class TourBookingSystem
 * Provides a menu system for creating Tour and GuidedTour instances.
 * Includes helper methods to write details of instances to file,
 * and to search the array for a specified Tour ID.
 * Creates an array from any updated file written on previous 
 * operation of the program.
 * 
 */

// Import libraries for use with data input, and file writing.

import java.util.Scanner;
import java.io.*;

// Creation of the application class.

public class TourBookingSystem {
    
    //Declare global variables.

    private static final Tour[] tours = new Tour[100];
    private static int tourCount = 0;

    // Declare a global scanner.

    private static final Scanner sc = new Scanner(System.in);

    
    //Start main method, program will begin here.
    public static void main(String[] args) {
        
        /* FILE READING
         * ************
         * Initiate file reading process, an exception is thrown 
         * in the form of an error message if no file is found, and
         * the program will continue.
         */
        
        try {
            
            Tour temp = null;
            String tourRecord;
            
            //Reads from the file datafile.txt
            Scanner readFile = new Scanner(new FileReader("datafile.txt"));
            while (readFile.hasNextLine()) {
                tourRecord = readFile.nextLine();
                
                // If file entry is a 'Tour' create a Tour object.
                if (tourRecord.startsWith("Tour")) {
                    temp = new Tour();
                }
                
                // If file entry is a 'GuidedTour' create a GuidedTour object.
                else if (tourRecord.startsWith("GuidedTour")) {
                    temp = new GuidedTour();
                }
                
                // Output error if first data field cannot be read.
                // Otherwise go ahead and create the array of tours.
                
                if (temp == null) {
                    System.out.println("Error reading data field!");
                }
                else {
                    String [] recordDetails = tourRecord.split("\\|");
                    temp.extractDetails(recordDetails);
                
                    tours[tourCount] = temp;
                    tourCount++;
                }
            }
            readFile.close();
        }
        
        // Print error message and continue if file is not found.
        catch(FileNotFoundException e) {
            System.out.println("No data to read from, starting from scratch!");
        }
        
        
        //Variable Declarations
        //*********************
        
        // variables required to process user's menu selection.
        String input, tourID, tourDescription, tourDate, tourLeader;
        int numBookings = 0, groupSize;
        double tourCost, bookResult;
        char selection = '\0';
        Tour searchResult;

        // Menu
        // ****
        // keep repeating the menu until the user chooses to exit.
        do {
            
            // display menu options.
            System.out.println("Tour System Menu");
            System.out.println("----------------");
            System.out.println("A - Add New Tour");
            System.out.println("B - Display Tour Summary");
            System.out.println("C - Add Tour Booking(s)");
            System.out.println("D - Cancel Tour Booking(s)");
            System.out.println("E - Add New Guided Tour");
            System.out.println("F - Update Guided Tour Group Size");
            System.out.println("X - Exit Program");
            System.out.println();

            // prompt the user to enter their selection.
            System.out.print("Enter your selection: ");
            input = sc.nextLine();

            System.out.println();

            // check to see if the user failed to enter exactly one character
            // for their menu selection.

            if (input.length() != 1) {
                System.out.println("Error - selection must be a single " +
                        "character!");

            }
            else {
                // extract the user's menu selection as a char value and
                // convert it to upper case so that the menu becomes
                // case-insensitive.

                selection = Character.toUpperCase(input.charAt(0));

                // process the user's selection.
                switch (selection) {
                case 'A':

                    // Get new Tour details.
                    System.out.print("Add New Tour option selected!");
                    System.out.print("\n\nPlease enter the Tour ID: ");
                    tourID = sc.nextLine();
                    System.out.print("Please enter the Tour Description: ");
                    tourDescription = sc.nextLine();
                    System.out.print("Please enter the Tour Cost: ");
                    tourCost = sc.nextDouble();
                    sc.nextLine();
                    
                    //Add the new Tour to the array.
                    tours[tourCount] = new Tour(tourID, tourDescription,
                            tourCost);
                    tourCount++;
                    break;

                case 'B':

                    // Display a summary of all tours currently
                    // in the system.
                    System.out.println("Display Tour Summary option " +
                            "selected!\n");
                    for (int i = 0; i < tourCount; i++) {
                        tours[i].displayTourDetails();
                        System.out.println();
                    }
                    break;

                case 'C':

                    // Add booking(s) for a tour.
                     
                    System.out.println("Add Tour Booking(s) option selected!");
                    System.out.print("\nPlease enter a Tour ID to search for: ");
                    tourID = sc.nextLine();
                    
                    //Search the array for an existing Tour ID
                    searchResult = searchBookings(tourID);
                    if (searchResult != null) {
                        System.out.print("\nTour found. Please enter the" +
                                "number of Tourists to book: ");
                        numBookings = sc.nextInt();
                        sc.nextLine();

                        //Attempt to add the bookings. Output TourException
                        //error if unsuccessful.
                        try {
                            bookResult = searchResult.addBookings(numBookings);
                            System.out.println("\nBooking successful");
                            System.out.printf("\nTotal booking fees to be " +
                                    "charged: $%.2f\n", bookResult);
                        }

                        catch (TourException e) {
                            System.out.println();
                            System.out.println(e.getMessage());
                        }

                    }
                    
                    //output error if search for Tour ID is unsuccessful
                    else {
                        System.out.println("\nError, Tour ID not found!");
                    }
                    break;

                case 'D':

                    // Cancel booking(s).
                    System.out.println("Cancel Tour Booking(s) option selected!");
                    System.out.print("\nPlease enter a Tour ID to search for: ");
                    tourID = sc.nextLine();
                    
                    //search the array for requested tour ID
                    searchResult = searchBookings(tourID);
                    if (searchResult != null) {
                        System.out.print("\nTour found. Please enter the number" +
                                " of Tourists to CANCEL a booking for: ");
                        numBookings = sc.nextInt();
                        sc.nextLine();
                        
                        // Assign the result of cancelBookings() to a variable.
                        bookResult = searchResult.cancelBookings(numBookings);
                        
                        // Print either an error, or the total of the cancelled
                        // booking fees, depending on outcome of cancelBookings().
                        if (Double.isNaN(bookResult)) {
                            System.out.print("\nError, number of bookings to " +
                                    "cancel must be greater than 0!\n");
                        }
                        else {
                            System.out.printf("\nTotal booking fees to be " + 
                                    "refunded: $%.2f\n", bookResult);
                        }

                    }
                    
                    // print an error message if search for tour ID is unsuccessful.
                    else {
                        System.out.println("\nError, Tour ID not found!");
                    }
                    break;

                case 'E':

                    // Add a new GuidedTour.
                    
                    // Collect GuidedTour details.
                    System.out.println("Add New Guided Tour option selected!");
                    System.out.print("\n\nPlease enter the Tour ID: ");
                    tourID = sc.nextLine();
                    System.out.print("Please enter the Tour Description: ");
                    tourDescription = sc.nextLine();
                    System.out.print("Please enter the Tour Cost: ");
                    tourCost = sc.nextDouble();
                    sc.nextLine();
                    System.out.print("Please enter the Tour Date: ");
                    tourDate = sc.nextLine();
                    System.out.print("Please enter the Group Size: ");
                    groupSize = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Please enter the Tour Leader: ");
                    tourLeader = sc.nextLine();
                    
                    // Assign GuidedTour details to the tours array.
                    
                    tours[tourCount] = new GuidedTour(tourID, tourDescription,
                            tourCost, tourDate, groupSize, tourLeader);
                    tourCount++;
                    break;

                case 'F':

                    // Update a GuidedTour group size.
                    
                    System.out.println("Update Tour Group Size option selected!");
                    System.out.print("\nPlease enter a Tour ID to search for: ");
                    tourID = sc.nextLine();
                    
                    // search for the requested Tour ID.
                    searchResult = searchBookings(tourID);
                    
                    if (searchResult != null) {
                        
                        // Only update group size if GuidedTour.
                        if (searchResult instanceof GuidedTour) {
                            System.out.print("\nTour found. Please enter " + 
                                    "the updated group size: ");
                            groupSize = sc.nextInt();
                            sc.nextLine();
                            ((GuidedTour) searchResult).updateGroupSize(groupSize);
                        }
                        
                        //Output an error if not GuidedTour.
                        else {
                            System.out.print("\nError, Tour is not a Guided " + 
                                    "Tour, group size cannot be updated!\n");
                        }
                    }
                    
                    // Output error of Tour ID not found.
                    else {
                        System.out.printf("\nError, Tour ID not found!\n");
                    }
                    break;

                case 'X':

                    // Exit the menu.
                    System.out.println("Tour system shutting down – goodbye!");
                    break;

                default:

                    // default case - handles invalid selections
                    System.out.println("Error - invalid selection!");

                }
            }
            System.out.println();

        }
        
        //loop back to beginning if exit is not selected.
        
        while (selection != 'X');
        
        // Perform the file writing before exiting program.
        
        writeDetails();

    }

    // Conducts a search of the tours array for a specified tour ID.
    // Returns a tours reference if found.

    private static Tour searchBookings(String searchString) {
        Tour acc = null;
        for (int i = 0; i < tourCount; i++) {
            if (searchString.compareTo(tours[i].getTourID()) == 0) {
                acc = tours[i];
            }
        }
        return acc;
    }
    
    // Writes details of all tours to a file called datafile.txt
    
    private static void writeDetails() {
        try {
            //Open the file to write to.
            PrintWriter fileWriter = 
                    new PrintWriter(new FileWriter("datafile.txt"));
            
            // Write details to the file for each instance of the array.
            for (int x=0; x<tourCount; x++) {
                tours[x].writeDetails(fileWriter);
            }
        fileWriter.close();
        }
        
        // Throw an error message if file could not be written to, then 
        // continue with the program.
        catch (IOException e) {
            System.out.println("There was an error writing to the file!");
        }
    }

}
