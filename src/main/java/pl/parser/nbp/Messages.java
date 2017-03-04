package pl.parser.nbp;

/**
 * Created by valkos on 05.03.17.
 */
public final class Messages {
    final static String DATE_IN_WRONG_FORMAT = "Wrong parameter %s, specify in format: yyyy-mm-dd.";
    final static String MALFORMED_INPUT = "Malformed input, expected - 3 parameters";
    final static String INCORRECT_DATE_FORMAT = "Please, enter date in the correct format: yyyy-mm-dd. Cancelling...";
    final static String CODE_QUALITY = "Most possible you entered the wrong code";
    final static String DATE_FROM_GT_DATE_TO = "\"Date from\" has to be earlier than date till which you want to receive the result";
    final static String DATE_TO_LATER_THAN_NOW = "You specified date (till which you want to get the result) later than now. " +
            "Program will calculate from: %s to %s";

}
