package pl.parser.nbp.Entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * Created by valkos on 04.03.17.
 */
@XmlRootElement(name = "pozycja")
@XmlAccessorType(XmlAccessType.NONE)
public class Currency {

    @XmlElement(name = "kod_waluty")
    private String code;

    @XmlElement(name = "kurs_kupna")
    private String sale;

    @XmlElement(name = "kurs_sprzedazy")
    private String buying;

    public String getCode() {
        return code;
    }

    public double getSale() throws ParseException {
        NumberFormat nf = NumberFormat.getInstance();
        return nf.parse(sale).doubleValue();
    }

    public double getBuying() throws ParseException {
        NumberFormat nf = NumberFormat.getInstance();
        return nf.parse(buying).doubleValue();
    }
}
