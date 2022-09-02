package noccures.clipperms.exceptions;

public class ExceptionMessages {

    private ExceptionMessages() {
    }

    //CLIPPER EXCEPTIONS
    public static final String CLIPPER_NO_NAME = "Name cannot be empty";
    public static final String CLIPPER_GET_FAILED = "Database failed GET after post/put request";
    public static final String CLIPPER_WITH_ID_NOT_FOUND = "Clipper with given id not found. id: ";
    public static final String CLIPPER_PRESENT_AFTER_DELETE = "Clipper still found in system after delete";


    //SERIES EXCEPTIONS
    public static final String SERIES_NO_NAME = "Name cannot be empty";
    public static final String SERIES_GET_FAILED = "Database failed GET after post/put request";
    public static final String SERIES_WITH_ID_NOT_FOUND = "Series with given id not found. id: ";
    public static final String SERIES_PRESENT_AFTER_DELETE = "series still found in system after delete";
}
