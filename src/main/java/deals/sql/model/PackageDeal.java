package deals.sql.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by psundriyal on 2/19/18.
 */

@XmlAccessorType(XmlAccessType.FIELD)
public class PackageDeal {

    Double packageNetPrice;
    Double standalonePrice;
    Timestamp outboundDateTime;
    Timestamp inboundDateTime;
    String outboundDate;
    String inboundDate;
    String flightNo;
    String origin;
    String destination;
    String url;
    Double savings;

    public PackageDeal() {

    }

    public PackageDeal(Double packageNetPrice, Double standalonePrice, Timestamp outboundDateTime, Timestamp inboundDateTime,
                       String flightNo, String origin, String destination) {
        this.packageNetPrice = packageNetPrice;
        this.standalonePrice = standalonePrice;
        this.outboundDateTime = outboundDateTime;
        this.inboundDateTime = inboundDateTime;
        this.flightNo = flightNo;
        this.origin = origin;
        this.destination = destination;
    }

    public Double getPackageNetPrice() {
        return packageNetPrice;
    }

    public void setPackageNetPrice(Double packageNetPrice) {
        this.packageNetPrice = packageNetPrice;
    }

    public Double getStandalonePrice() {
        return standalonePrice;
    }

    public void setStandalonePrice(Double standalonePrice) {
        this.standalonePrice = standalonePrice;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Double getSavings() {
        return savings;
    }

    public void setSavings(Double savings) {
        this.savings = savings;
    }

    public Timestamp getOutboundDateTime() {
        return outboundDateTime;
    }

    public void setOutboundDateTime(Timestamp outboundDateTime) {
        this.outboundDateTime = outboundDateTime;
    }

    public Timestamp getInboundDateTime() {
        return inboundDateTime;
    }

    public void setInboundDateTime(Timestamp inboundDateTime) {
        this.inboundDateTime = inboundDateTime;
    }

    public String getOutboundDate() {
        return outboundDate;
    }

    public void setOutboundDate(String outboundDate) {
        this.outboundDate = outboundDate;
    }

    public String getInboundDate() {
        return inboundDate;
    }

    public void setInboundDate(String inboundDate) {
        this.inboundDate = inboundDate;
    }
}
