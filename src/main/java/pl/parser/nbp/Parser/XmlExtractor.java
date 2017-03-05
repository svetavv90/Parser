package pl.parser.nbp.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by valkos on 04.03.17.
 */
public class XmlExtractor {
    private static final String NAMES = "http://www.nbp.pl/kursy/xml/dir";
    private static final String EXTENSION = ".txt";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.BASIC_ISO_DATE;
    private static final String UTF8_BOM = "\uFEFF"; //it is possible to keep the result with BOM prefix only manually
    private Map<LocalDate, String> files = new TreeMap<>();

    private LocalDate dateFrom;
    private LocalDate dateTo;

    XmlExtractor(LocalDate dateFrom, LocalDate dateTo) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public List<String> extractXmlNames() {
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
        List<String> chosenFiles = new ArrayList<>();
        files.forEach( (key, value) -> {
            if ( key.isEqual(dateFrom) ||(key.isAfter(dateFrom) && key.isBefore(dateTo)) || key.isEqual(dateTo)) {
                chosenFiles.add(value);
            }
        });
        return chosenFiles;
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

    private Map<LocalDate, String> adaptedFields(String inputLine) {
        final String partOfYear = "20";
        LocalDate date = LocalDate.parse(partOfYear + inputLine.substring(5), FORMATTER); // skipping cxxxzYY symbols
        files.put(date, inputLine);
        return files;
    }

}
