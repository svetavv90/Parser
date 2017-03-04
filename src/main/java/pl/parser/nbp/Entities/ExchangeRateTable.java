package pl.parser.nbp.Entities;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by valkos on 04.03.17.
 */
@XmlRootElement(name = "tabela_kursow")
@XmlAccessorType(XmlAccessType.NONE)
public class ExchangeRateTable {

    @XmlElement(name = "pozycja")
    private List<Currency> currencies;

    public List<Currency> getCurrencies() {
        return currencies;
    }
}
