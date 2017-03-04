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
    private static final String DIRECTORY_NAMES = "http://www.nbp.pl/kursy/xml/dir.txt";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.BASIC_ISO_DATE;
    private static final String UTF8_BOM = "\uFEFF"; //it is possible to keep the result with BOM prefix only manually

    private LocalDate dateFrom;
    private LocalDate dateTo;

    XmlExtractor(LocalDate dateFrom, LocalDate dateTo) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public List<String> extractXmlNames() {
        Map<LocalDate, String> files = getAllFiles();
        List<String> chosenFiles = new ArrayList<>(365);
        files.forEach( (key, value) -> {
            if ( key.isEqual(dateFrom) ||(key.isAfter(dateFrom) && key.isBefore(dateTo)) || key.isEqual(dateTo)) {
                chosenFiles.add(value);
            }
        });
        return chosenFiles;
    }

    private Map<LocalDate, String> getAllFiles() {
        final String modifier = "c";
        Map<LocalDate, String> files = new TreeMap<>();
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(new URL(DIRECTORY_NAMES).openStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.startsWith(modifier)) {
                    adaptedFields(files, inputLine);
                } else if (inputLine.startsWith(UTF8_BOM + modifier)) {
                    adaptedFields(files, inputLine.substring(1));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return files;
    }

    private Map<LocalDate, String> adaptedFields(Map<LocalDate, String> files, String inputLine) {
        final Year currentYear = Year.now();
        LocalDate date = LocalDate.parse(currentYear + inputLine.substring(7), FORMATTER); // skipping cxxxzYY symbols
        files.put(date, inputLine);
        return files;
    }

}
