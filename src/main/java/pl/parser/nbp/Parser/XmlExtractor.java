package pl.parser.nbp.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by valkos on 04.03.17.
 */
public class XmlExtractor {
    private static final String NAMES = "http://www.nbp.pl/kursy/xml/dir";
    private static final String EXTENSION = ".txt";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.BASIC_ISO_DATE;
    private static final String UTF8_BOM = "\uFEFF"; //it is possible to keep the result with BOM prefix only manually
    private Set<String> nameOfFile = new HashSet<>();

    private LocalDate dateFrom;
    private LocalDate dateTo;

    XmlExtractor(LocalDate dateFrom, LocalDate dateTo) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public Set<String> extractXmlNames() {
        int currentYear = Year.now().getValue();
        int yearFrom = dateFrom.getYear();
        int yearTo = dateTo.getYear();
        String readFromCurrentYear = NAMES + EXTENSION;
        if (currentYear == yearFrom && currentYear == yearTo) {
            getAllFiles(readFromCurrentYear);
        } else {
            if (currentYear == yearTo) {
                getAllFiles(readFromCurrentYear);
                readNamesForPreviuosYears(yearFrom, yearTo-1);
            } else {
                readNamesForPreviuosYears(yearFrom, yearTo);
            }
        }
        return nameOfFile;
    }

    private void readNamesForPreviuosYears(int yearFrom, int yearTo) {
        for (int value = yearFrom; value <= yearTo; value++ ) {
            getAllFiles(NAMES + value + EXTENSION);
        }
    }

    private void getAllFiles(String readFrom) {
        final String modifier = "c";
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(new URL(readFrom).openStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.startsWith(modifier)) {
                    adaptedFields(inputLine);
                } else if (inputLine.startsWith(UTF8_BOM + modifier)) {
                    adaptedFields(inputLine.substring(1));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Set<String> adaptedFields(String inputLine) {
        final String partOfYear = "20";
        LocalDate date = LocalDate.parse(partOfYear + inputLine.substring(5), FORMATTER); // skipping cxxxzYY symbols
        if ( date.isEqual(dateFrom) ||(date.isAfter(dateFrom) && date.isBefore(dateTo)) || date.isEqual(dateTo)) {
            nameOfFile.add(inputLine);
        }
        return nameOfFile;
    }

}
