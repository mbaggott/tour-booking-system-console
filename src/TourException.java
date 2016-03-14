// Custom exception class for the TourException.
// Provides the new exception name TourException
// behaving in the same was as default Exceptions.

public class TourException extends Exception {

    TourException() {
        super();
    }
    
    TourException(String errorMessage) {
        super(errorMessage);
    }
    
}
