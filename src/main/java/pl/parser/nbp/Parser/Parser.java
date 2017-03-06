package pl.parser.nbp.Parser;

import pl.parser.nbp.Calculation.StorageInstruments;
import pl.parser.nbp.Entities.Currency;
import pl.parser.nbp.Entities.ExchangeRateTable;

import javax.xml.bind.JAXB;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Set;

/**
 * Created by valkos on 04.03.17.
 */
public class Parser {
    private final static String XML_PATH = "http://www.nbp.pl/kursy/xml/";
    private final static String EXTENSION = ".xml";

    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String currencyCode;

    public Parser(LocalDate dateFrom, LocalDate dateTo, String currencyCode) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.currencyCode = currencyCode;
    }

    public void parseFile() {
        XmlExtractor xmlData = new XmlExtractor(dateFrom, dateTo);
        Set<String> xmlFiles = xmlData.extractXmlNames();
        if (xmlFiles != null && !xmlFiles.isEmpty()) {
            xmlFiles.forEach(file -> gettingValues(XML_PATH + file + EXTENSION));
        }
    }

    private void gettingValues(String path) {
        try {
            URL url = new URL(path);
            Currency currencyEntity = JAXB.unmarshal(url, ExchangeRateTable.class)
                                    .getCurrencies().stream()
                                    .filter(currency -> (currencyCode != null && currencyCode.equals(currency.getCode())))
                                    .findFirst().orElse(null);
            if (currencyEntity != null) {
                try {
                    StorageInstruments.putToSale(currencyEntity.getSale());
                    StorageInstruments.putToBuying(currencyEntity.getBuying());
                    StorageInstruments.incrementCounter();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

}
