/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.NewCookie;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jacobhonore
 */
public class TestTravelResource {
    static List<NewCookie> cookies = new ArrayList<NewCookie>();
    @Test
    public void CreateItinery() {
        Client client = Client.create();
        WebResource r = client.resource("http://localhost:8080/TravelAgencyService/webresources/travel/createItinery");
        ClientResponse result = r.put(ClientResponse.class);
        cookies = result.getCookies();
        assertEquals("New itinery created",result.getEntity(String.class));
    }
    @Test
    public void SetCreditCardInfo() {
        Client client = Client.create();
        WebResource r = client.resource("http://localhost:8080/TravelAgencyService/webresources/travel/setCreditCardInfo?name=Donovan%20Jasper&number=50408818&month=6&year=9");
        Builder builder = r.getRequestBuilder();
        for (NewCookie cookie : cookies) {
            builder.cookie(cookie);
        }
        String result = builder.post(String.class);
        assertEquals("Creditcard information has been stored.",result);
    }
    @Test
    public void AddFlight1() {
        Client client = Client.create();
        WebResource r = client.resource("http://localhost:8080/TravelAgencyService/webresources/travel/addFlight?flightnumber=IKR-104");
        Builder builder = r.getRequestBuilder();
        for (NewCookie cookie : cookies) {
            builder.cookie(cookie);
        }
        String result = builder.post(String.class);
        assertEquals("Flight has been booked and added to list of flights.",result);
    }
    @Test
    public void AddFlight2() {
        Client client = Client.create();
        WebResource r = client.resource("http://localhost:8080/TravelAgencyService/webresources/travel/addFlight?flightnumber=SKY-654");
        Builder builder = r.getRequestBuilder();
        for (NewCookie cookie : cookies) {
            builder.cookie(cookie);
        }
        String result = builder.post(String.class);
        assertEquals("Flight has been booked and added to list of flights.",result);
    }
    @Test
    public void AddHotel() {
        Client client = Client.create();
        WebResource r = client.resource("http://localhost:8080/TravelAgencyService/webresources/travel/addHotel?hotelnumber=211500-1");
        Builder builder = r.getRequestBuilder();
        for (NewCookie cookie : cookies) {
            builder.cookie(cookie);
        }
        String result = builder.post(String.class);
        assertEquals("Hotel has been booked and added to list of hotels.",result);
    }
    @Test
    public void GetHotels() {
        Client client = Client.create();
        WebResource r = client.resource("http://localhost:8080/TravelAgencyService/webresources/travel/getHotels?city=Sacramento&arrivaldate=2014-08-03%2022:12&departuredate=2014-12-23%2012:47");
        Builder builder = r.getRequestBuilder();
        for (NewCookie cookie : cookies) {
            builder.cookie(cookie);
        }
        String result = builder.get(String.class);
        assertEquals("<list>\n" +
"  <hotelObjects.HotelBooking>\n" +
"    <hotelName>Hotel California</hotelName>\n" +
"    <address>\n" +
"      <address>Eagles Street 445</address>\n" +
"      <city>Sacramento</city>\n" +
"      <zipCode>94273</zipCode>\n" +
"      <country>United States of America</country>\n" +
"    </address>\n" +
"    <bookingNumber>211500-1</bookingNumber>\n" +
"    <creditCardGuarentee>true</creditCardGuarentee>\n" +
"    <priceForStay>211500</priceForStay>\n" +
"    <bookingService>NiceView</bookingService>\n" +
"  </hotelObjects.HotelBooking>\n" +
"</list>",result);
    }
    @Test
    public void GetFlights() {
        Client client = Client.create();
        WebResource r = client.resource("http://localhost:8080/TravelAgencyService/webresources/travel/getFlights?startairport=Heathrow&destairport=Shanghai&liftoffdate=2014-02-23%2012:47");
        Builder builder = r.getRequestBuilder();
        for (NewCookie cookie : cookies) {
            builder.cookie(cookie);
        }
        String result = builder.get(String.class);
        assertEquals("<list>\n" +
"  <flightObjects.FlightListData>\n" +
"    <bookingNumber>SKY-654</bookingNumber>\n" +
"    <airlineService>LameDuck</airlineService>\n" +
"    <flight>\n" +
"      <startAirport>Heathrow</startAirport>\n" +
"      <destAirport>Shanghai</destAirport>\n" +
"      <liftoffDate>2014-02-23 12:47</liftoffDate>\n" +
"      <arrivalDate>2014-02-24 19:52</arrivalDate>\n" +
"      <carrier>Travel Up</carrier>\n" +
"    </flight>\n" +
"    <price>3000</price>\n" +
"  </flightObjects.FlightListData>\n" +
"  <flightObjects.FlightListData>\n" +
"    <bookingNumber>SKY-655</bookingNumber>\n" +
"    <airlineService>LameDuck</airlineService>\n" +
"    <flight reference=\"../../flightObjects.FlightListData/flight\"/>\n" +
"    <price>3500</price>\n" +
"  </flightObjects.FlightListData>\n" +
"  <flightObjects.FlightListData>\n" +
"    <bookingNumber>SKY-656</bookingNumber>\n" +
"    <airlineService>LameDuck</airlineService>\n" +
"    <flight reference=\"../../flightObjects.FlightListData/flight\"/>\n" +
"    <price>3750</price>\n" +
"  </flightObjects.FlightListData>\n" +
"</list>",result);
    }
    @Test
    public void GetItinery() {
        Client client = Client.create();
        WebResource r = client.resource("http://localhost:8080/TravelAgencyService/webresources/travel/getItinery");
        Builder builder = r.getRequestBuilder();
        for (NewCookie cookie : cookies) {
            builder.cookie(cookie);
        }
        String result = builder.get(String.class);
        assertEquals("The following flights has been booked: IKR-104 SKY-654 The following hotels has been booked: 211500-1 ",result);
    }
    @Test
    public void CancelItinery() {
        Client client = Client.create();
        WebResource r = client.resource("http://localhost:8080/TravelAgencyService/webresources/travel/cancelItinery");
        Builder builder = r.getRequestBuilder();
        for (NewCookie cookie : cookies) {
            builder.cookie(cookie);
        }
        String result = builder.delete(String.class);
        assertEquals("IKR-104 cancelled SKY-654 cancelled 211500-1 cancelled ",result);
    }
}