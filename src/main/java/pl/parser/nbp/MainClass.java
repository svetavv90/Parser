package pl.parser.nbp;

import pl.parser.nbp.Calculation.CalculationLogic;
import pl.parser.nbp.Parser.Parser;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static java.lang.String.format;
import static pl.parser.nbp.Messages.*;

/**
 * Created by valkos on 04.03.17.
 */

public class MainClass {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final String DATE_PATTERN = "\\d{4}-\\d{2}-\\d{2}";
    private static final DecimalFormat DOUBLE_FORMATTER = new DecimalFormat("##.####");

    public static void main(String[] args) {
        checkingArguments(args);

        String currencyCode = args[0];
        LocalDate dateFrom = null;
        LocalDate dateTo = null;
        try {
            dateFrom = LocalDate.parse(args[1], FORMATTER);
            dateTo = LocalDate.parse(args[2], FORMATTER);
        } catch (DateTimeParseException exception) {
            System.out.println(format(DATE_IN_WRONG_FORMAT, exception.getParsedString()));
            System.exit(1);
        }
        checkingEnteredParameters(currencyCode,dateFrom,dateTo);

        Parser parser = new Parser(dateFrom, dateTo, currencyCode);
        parser.parseFile();

        CalculationLogic getResult = new CalculationLogic();
        System.out.println(DOUBLE_FORMATTER.format(getResult.getSaleAverage()));
        System.out.println(DOUBLE_FORMATTER.format(getResult.getAverageDeviationForBuying()));
    }

    private static void checkingArguments(String[] args) {
        if (args == null || args.length < 3) {
            System.out.println(MALFORMED_INPUT);
            System.exit(1);
        }
        if (!args[1].matches(DATE_PATTERN) || !args[2].matches(DATE_PATTERN)) {
            System.out.println(INCORRECT_DATE_FORMAT);
            System.exit(1);
        }
    }

    private static void checkingEnteredParameters(String currencyCode, LocalDate dateFrom, LocalDate dateTo) {
        if (currencyCode.length() > 3 || currencyCode.length() < 3) {
            System.out.println(CODE_QUALITY);
        }

        if (dateFrom.isAfter(dateTo)) {
            System.out.println(DATE_FROM_GT_DATE_TO);
            System.exit(1);
        }

        if (dateTo.isAfter(LocalDate.now())) {
            System.out.println(format(DATE_TO_LATER_THAN_NOW, dateFrom, LocalDate.now()));
        }
    }
}
