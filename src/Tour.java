/*
 * Superclass Tour.
 * 
 * Provides two constructors, one with arguments, one without.
 * Provides accessors for all private instance variables.
 * Provides mutators/operations methods for adding and canceling bookings.
 * Provides helper methods to calculate booking fees and output tour details.
 * Contains methods for file writing of Tour instances.
 * 
 */



// Import library for file writing

import java.io.*;


// Create the class and define instance variables

public class Tour {
    
    // Define instance variables required for a Tour
    private String tourID;
    private String tourDescription;
    private double tourFee;
    private int totalBookings;

    // Define constructors
    // *******************
    
    // Define a constructor that sets up a new Tour
    public Tour(String tourID, String tourDescription, double tourFee) {
        this.tourID = tourID;
        this.tourDescription = tourDescription;
        this.tourFee = tourFee;
    }
    
    // Define a blank constructor for use with file reading in the application
    // class.
    public Tour() {
        
    }

    
    // Define accessors for each instance variable
    // *******************************************
    
    public String getTourID() {
        return tourID;
    }

    public String getTourDescription() {
        return tourDescription;
    }

    public double getTourFee() {
        return tourFee;
    }

    public int getTotalBookings() {
        return totalBookings;
    }
    

    // Define operations that can be performed on a Tour
    // *************************************************
    // addBookings()
    //
    // Attempts to add the specified number of tourists to the
    // total booking count for this Tour and return the booking
    // fees charged.
    //
    // Throws a TourException when the number of tourists
    // specified is not a positive value.
    //
    public double addBookings(int numberOfTourists) throws TourException {
        
        // check for an invalid number of tourists
        if (numberOfTourists <= 0) {
            throw new TourException(
                    "Error, number of tourists must be greater than zero.");
        }
        else {
            
            // update the total number of bookings for this Tour
            totalBookings += numberOfTourists;
            return this.calculateBookingFees(numberOfTourists);
        }
    }

    
    // cancelBookings()
    //
    // Attempts to subtract the specified number of tourists from the
    // total booking count for this Tour and return the booking
    // fees to be refunded accordingly.
    //
    // Returns a signal of Double.NaN when the number of tourists
    // specified is not a positive value OR is greater than the current
    // total number of bookings for this Tour.
    //
    public double cancelBookings(int numberOfTourists) {
        if (numberOfTourists <= 0 || numberOfTourists > totalBookings) {
            return Double.NaN;
        }
        else {
            // update the total number of bookings for this Tour
            totalBookings -= numberOfTourists;

            // calculate the total booking fees to be refunded
            double refundedFees = this.calculateBookingFees(numberOfTourists);

            // return the total booking fees amount to the caller
            return refundedFees;
        }
    }

    // Define helper methods that we may need
    //***************************************
    
    // calculatBookingFees()
    //
    // Helper method which calculates the total booking fees that
    // apply based on the specified number of tourists
    //
    private double calculateBookingFees(int numberOfTourists) {
        return numberOfTourists * tourFee;
    }

    // displayTourDetails()
    //
    // Helper method which displays the basic details for this Tour,
    // as well as the total fees received for this Tour.
    public void displayTourDetails() {
        System.out.printf("%s %s\n", "Tour ID:", tourID);
        System.out.printf("%s %s\n", "Description:", tourDescription);
        System.out.printf("%s $%.2f\n", "Tour Fee:", tourFee);
        System.out.printf("%s %s\n", "Total Bookings:", totalBookings);

        // call the calculateBookingFees() method to help figure out the
        // total fees
        System.out.printf("%s $%.2f\n", "Total Booking Fees Received:",
                this.calculateBookingFees(totalBookings));
    }
    
    // Define helper methods for use in file writing
    // *********************************************
    
    // Extracts tour details and assigns them to elements of the 
    // array that is passed in.
    
    public void extractDetails(String details[]) {
        this.tourID = details[1];
        this.tourDescription = details[2];
        this.tourFee = Double.parseDouble(details[3]);
        this.totalBookings = Integer.parseInt(details[4]);
    }

    // Writes values of instance variables in correct format to the 
    // file reference passed in.
    
    public void writeAttributes(PrintWriter fileWriter) {
        String attributes = tourID + "|" + tourDescription + "|" + tourFee + 
                "|" + totalBookings;
        fileWriter.print(attributes);
    }
    
    
    // Adds the Tour class identifier to the end of the instance variable values
    // already written, then adds a new line to signal the end of the file entry
    // for this instance.
    
    public void writeDetails(PrintWriter fileWriter) {
        fileWriter.print("Tour|");
        writeAttributes(fileWriter);
        fileWriter.println();
    }

}
