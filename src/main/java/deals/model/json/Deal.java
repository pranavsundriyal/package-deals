package deals.model.json;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by psundriyal on 2/19/18.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "pack_price",
        "sa_price",
        "outbound_airport",
        "inbound_airport",
        "outbound_departure_time",
        "inbound_arrival_time",
        "fare_type",
        "fare_type1",
        "marketing_flights",
        "cabin_class",
        "cabin_class1"
})
public class Deal {

    @JsonProperty("pack_price")
    private String packPrice;
    @JsonProperty("sa_price")
    private String saPrice;
    @JsonProperty("outbound_airport")
    private String outboundAirport;
    @JsonProperty("inbound_airport")
    private String inboundAirport;
    @JsonProperty("outbound_departure_time")
    private String outboundDepartureTime;
    @JsonProperty("inbound_arrival_time")
    private String inboundArrivalTime;
    @JsonProperty("fare_type")
    private String fareType;
    @JsonProperty("fare_type1")
    private String fareType1;
    @JsonProperty("marketing_flights")
    private String marketingFlights;
    @JsonProperty("cabin_class")
    private String cabinClass;
    @JsonProperty("cabin_class1")
    private String cabinClass1;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("pack_price")
    public String getPackPrice() {
        return packPrice;
    }

    @JsonProperty("pack_price")
    public void setPackPrice(String packPrice) {
        this.packPrice = packPrice;
    }

    public Deal withPackPrice(String packPrice) {
        this.packPrice = packPrice;
        return this;
    }

    @JsonProperty("sa_price")
    public String getSaPrice() {
        return saPrice;
    }

    @JsonProperty("sa_price")
    public void setSaPrice(String saPrice) {
        this.saPrice = saPrice;
    }

    public Deal withSaPrice(String saPrice) {
        this.saPrice = saPrice;
        return this;
    }

    @JsonProperty("outbound_airport")
    public String getOutboundAirport() {
        return outboundAirport;
    }

    @JsonProperty("outbound_airport")
    public void setOutboundAirport(String outboundAirport) {
        this.outboundAirport = outboundAirport;
    }

    public Deal withOutboundAirport(String outboundAirport) {
        this.outboundAirport = outboundAirport;
        return this;
    }

    @JsonProperty("inbound_airport")
    public String getInboundAirport() {
        return inboundAirport;
    }

    @JsonProperty("inbound_airport")
    public void setInboundAirport(String inboundAirport) {
        this.inboundAirport = inboundAirport;
    }

    public Deal withInboundAirport(String inboundAirport) {
        this.inboundAirport = inboundAirport;
        return this;
    }

    @JsonProperty("outbound_departure_time")
    public String getOutboundDepartureTime() {
        return outboundDepartureTime;
    }

    @JsonProperty("outbound_departure_time")
    public void setOutboundDepartureTime(String outboundDepartureTime) {
        this.outboundDepartureTime = outboundDepartureTime;
    }

    public Deal withOutboundDepartureTime(String outboundDepartureTime) {
        this.outboundDepartureTime = outboundDepartureTime;
        return this;
    }

    @JsonProperty("inbound_arrival_time")
    public String getInboundArrivalTime() {
        return inboundArrivalTime;
    }

    @JsonProperty("inbound_arrival_time")
    public void setInboundArrivalTime(String inboundArrivalTime) {
        this.inboundArrivalTime = inboundArrivalTime;
    }

    public Deal withInboundArrivalTime(String inboundArrivalTime) {
        this.inboundArrivalTime = inboundArrivalTime;
        return this;
    }

    @JsonProperty("fare_type")
    public String getFareType() {
        return fareType;
    }

    @JsonProperty("fare_type")
    public void setFareType(String fareType) {
        this.fareType = fareType;
    }

    public Deal withFareType(String fareType) {
        this.fareType = fareType;
        return this;
    }

    @JsonProperty("fare_type1")
    public String getFareType1() {
        return fareType1;
    }

    @JsonProperty("fare_type1")
    public void setFareType1(String fareType1) {
        this.fareType1 = fareType1;
    }

    public Deal withFareType1(String fareType1) {
        this.fareType1 = fareType1;
        return this;
    }

    @JsonProperty("marketing_flights")
    public String getMarketingFlights() {
        return marketingFlights;
    }

    @JsonProperty("marketing_flights")
    public void setMarketingFlights(String marketingFlights) {
        this.marketingFlights = marketingFlights;
    }

    public Deal withMarketingFlights(String marketingFlights) {
        this.marketingFlights = marketingFlights;
        return this;
    }

    @JsonProperty("cabin_class")
    public String getCabinClass() {
        return cabinClass;
    }

    @JsonProperty("cabin_class")
    public void setCabinClass(String cabinClass) {
        this.cabinClass = cabinClass;
    }

    public Deal withCabinClass(String cabinClass) {
        this.cabinClass = cabinClass;
        return this;
    }

    @JsonProperty("cabin_class1")
    public String getCabinClass1() {
        return cabinClass1;
    }

    @JsonProperty("cabin_class1")
    public void setCabinClass1(String cabinClass1) {
        this.cabinClass1 = cabinClass1;
    }

    public Deal withCabinClass1(String cabinClass1) {
        this.cabinClass1 = cabinClass1;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Deal withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}