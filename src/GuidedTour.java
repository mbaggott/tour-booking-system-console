/*
 * Subclass GuidedTour.
 * 
 * Provides two constructors, one with arguments, one without.
 * Provides accessors for all private instance variables.
 * Provides an overidden method for adding bookings.
 * Provides an overidden method to output tour details.
 * Contains overidden methods for file writing of GuidedTour instances.
 * 
 */

// Import library for use with file writing methods

import java.io.*;

// Class creation and declaration of instance variables.

public class GuidedTour extends Tour {

    private String tourDate;
    private int groupSize;
    private String tourLeader;
    
    // Define constructors
    // *******************
    
 // Define a constructor that sets up a new GuidedTour
    
    public GuidedTour(String tourID, String tourDescription, double tourFee, 
            String tourDate, int groupSize, String tourLeader) {
        super(tourID, tourDescription, tourFee);
        this.tourDate = tourDate;
        this.groupSize = groupSize;
        this.tourLeader = tourLeader;
    }
    
    // Define a blank constructor for use with file reading in the application
    // class.
    
    public GuidedTour() {
        
    }
    
    
    // Define accessors for each instance variable
    // *******************************************
    
    public String getTourDate() {
        return tourDate;
    }
    
    public int getGroupSize() {
        return groupSize;
    }
    
    public String getTourLeader() {
        return tourLeader;
    }
    
    // Define mutators
    // ***************
    
    public void updateGroupSize(int size) {
        this.groupSize = size;
    }
    
    // Define operations that can be performed on a Tour
    // *************************************************
    // addBookings() overrides the superclass method of the same name.
    //
    // Attempts to add the specified number of tourists to the
    // total booking count for this Tour and return the booking
    // fees charged.
    //
    // Throws a TourException when the number of tourists
    // plus the total current bookings exceeds the group size.
    //
    // Adds bookings through the superclass method, which will
    // also throw an exception if the value is not a positive number.
    
    @Override
    public double addBookings(int numberOfTourists) throws TourException {
        if (numberOfTourists + super.getTotalBookings() > groupSize) {
            throw new TourException("Error, tourist bookings exceeds " + 
                    "group size.");
        }
        
        else {
            return super.addBookings(numberOfTourists);
        }
    }
    
    // displayTourDetails()
    // Provides an overidden display of the GuidedTour instance details
    
    
    @Override
    public void displayTourDetails() {
        System.out.println("**GUIDED TOUR GROUP**");
        super.displayTourDetails();
        System.out.println("Tour Date: " + tourDate);
        System.out.println("Group Size: " + groupSize);
        System.out.println("Tour Leader: " + tourLeader);
    }
    
    // Define helper methods for use in file writing
    // *********************************************
    
    // Extracts tour details and assigns them to elements of the 
    // array that is passed in.
    
    @Override
    public void extractDetails(String details[]) {
        super.extractDetails(details);
        this.tourDate = details[5];
        this.groupSize = Integer.parseInt(details[6]);
        this.tourLeader = details[7];
        
    }
    
    // Writes values of instance variables in correct format to the 
    // file reference passed in.
    
    @Override
    public void writeAttributes(PrintWriter fileWriter) {
        super.writeAttributes(fileWriter);
        String extraInfo = "|" + tourDate + "|" + groupSize + "|" + tourLeader;
        fileWriter.print(extraInfo);
    }
    
    
    // Adds the Tour class identifier to the end of the instance variable values
    // already written, then adds a new line to signal the end of the file entry
    // for this instance.
    
    @Override
    public void writeDetails(PrintWriter fileWriter) {
        fileWriter.print("GuidedTour|");
        this.writeAttributes(fileWriter);
        fileWriter.println();
    }
}
