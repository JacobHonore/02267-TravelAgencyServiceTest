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
public class TestRestful {
    @Test
    public void testP1() {
        CreateItinerary();
        GetFlights();
        AddFlight("SKY-654");
        GetHotels();
        AddHotel("211500-1");
        AddFlight("SKY-655");
        AddFlight("SKY-656");
        AddHotel("3850-2");
        //Ask for the itinerary with getItinerary
        Client client = Client.create();
        WebResource r = client.resource("http://localhost:8080/TravelAgencyService/webresources/travel/getItinerary");
        Builder builder = r.getRequestBuilder();
        for (NewCookie cookie : cookies) {
            builder.cookie(cookie);
        }
        String result = builder.get(String.class);
        assertEquals("The following flights has been planned:<br>"
                + "Booking number: SKY-654 Booking status: UNCONFIRMED<br>"
                + "Booking number: SKY-655 Booking status: UNCONFIRMED<br>"
                + "Booking number: SKY-656 Booking status: UNCONFIRMED<br>"
                + "<br>The following hotels has been planned:<br>"
                + "Booking number: 211500-1 Booking status: UNCONFIRMED<br>"
                + "Booking number: 3850-2 Booking status: UNCONFIRMED<br>",result);
        // Book the flights and hotels with book
        client = Client.create();
        r = client.resource("http://localhost:8080/TravelAgencyService/webresources/travel/book?name=Donovan%20Jasper&number=50408818&month=6&year=9");
        builder = r.getRequestBuilder();
        for (NewCookie cookie : cookies) {
            builder.cookie(cookie);
        }
        result = builder.post(String.class);
        assertEquals(
                "SKY-654 has been CONFIRMED<br>"
                + "SKY-655 has been CONFIRMED<br>"
                + "SKY-656 has been CONFIRMED<br>"
                + "211500-1 has been CONFIRMED<br>"
                + "3850-2 has been CONFIRMED<br>",result);
        
        //Ask for the itinerary with getItinerary
        client = Client.create();
        r = client.resource("http://localhost:8080/TravelAgencyService/webresources/travel/getItinerary");
        builder = r.getRequestBuilder();
        for (NewCookie cookie : cookies) {
            builder.cookie(cookie);
        }
        result = builder.get(String.class);
        assertEquals("The following flights has been planned:<br>"
                + "Booking number: SKY-654 Booking status: CONFIRMED<br>"
                + "Booking number: SKY-655 Booking status: CONFIRMED<br>"
                + "Booking number: SKY-656 Booking status: CONFIRMED<br>"
                + "<br>The following hotels has been planned:<br>"
                + "Booking number: 211500-1 Booking status: CONFIRMED<br>"
                + "Booking number: 3850-2 Booking status: CONFIRMED<br>",result);
    }
    @Test
    public void testP2() {
        CreateItinerary();
        GetFlights();
        AddFlight("SKY-655");
        //Cancel the itinerary with cancelItinerary
        Client client = Client.create();
        WebResource r = client.resource("http://localhost:8080/TravelAgencyService/webresources/travel/cancelItinerary");
        Builder builder = r.getRequestBuilder();
        for (NewCookie cookie : cookies) {
            builder.cookie(cookie);
        }
        String result = builder.delete(String.class);
        assertEquals("SKY-655 cancelled<br>",result);
    }
    
    @Test
    public void testB() {
        CreateItinerary();
        AddFlight("PLO-367");
        AddFlight("AHD-856");
        AddHotel("750-4");
        //Ask for the itinerary with getItinerary
        Client client = Client.create();
        WebResource r = client.resource("http://localhost:8080/TravelAgencyService/webresources/travel/getItinerary");
        Builder builder = r.getRequestBuilder();
        for (NewCookie cookie : cookies) {
            builder.cookie(cookie);
        }
        String result = builder.get(String.class);
        assertEquals("The following flights has been planned:<br>"
                + "Booking number: PLO-367 Booking status: UNCONFIRMED<br>"
                + "Booking number: AHD-856 Booking status: UNCONFIRMED<br>"
                + "<br>The following hotels has been planned:<br>"
                + "Booking number: 750-4 Booking status: UNCONFIRMED<br>",result);
        // Book the flights and hotels with book
        client = Client.create();
        r = client.resource("http://localhost:8080/TravelAgencyService/webresources/travel/book?name=Tick%20Joachim&number=50408824&month=2&year=11");
        builder = r.getRequestBuilder();
        for (NewCookie cookie : cookies) {
            builder.cookie(cookie);
        }
        result = builder.post(String.class);
        assertEquals(
                "PLO-367 has been CONFIRMED<br>"
                + "AHD-856 has been CONFIRMED<br>"
                + "750-4 has been CONFIRMED<br>",result);
    }
    
    
    
    static List<NewCookie> cookies = new ArrayList<NewCookie>();
    public void CreateItinerary() {
        Client client = Client.create();
        WebResource r = client.resource("http://localhost:8080/TravelAgencyService/webresources/travel/createItinerary");
        ClientResponse result = r.put(ClientResponse.class);
        cookies = result.getCookies();
        assertEquals("New itinerary created",result.getEntity(String.class));
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
    
    public void AddFlight(String bookingNumber) {
        Client client = Client.create();
        WebResource r = client.resource("http://localhost:8080/TravelAgencyService/webresources/travel/addFlight?flightnumber="+bookingNumber);
        Builder builder = r.getRequestBuilder();
        for (NewCookie cookie : cookies) {
            builder.cookie(cookie);
        }
        String result = builder.post(String.class);
        assertEquals("Flight with booking number "+bookingNumber+" has been booked and added to list of flights with status UNCONFIRMED.",result);
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
    public void AddHotel(String bookingNumber) {
        Client client = Client.create();
        WebResource r = client.resource("http://localhost:8080/TravelAgencyService/webresources/travel/addHotel?hotelnumber="+bookingNumber);
        Builder builder = r.getRequestBuilder();
        for (NewCookie cookie : cookies) {
            builder.cookie(cookie);
        }
        String result = builder.post(String.class);
        assertEquals("Hotel with booking number "+bookingNumber+" has been booked and added to list of hotels with status UNCONFIRMED.",result);
    }
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
    public void GetItinerary() {
        Client client = Client.create();
        WebResource r = client.resource("http://localhost:8080/TravelAgencyService/webresources/travel/getItinerary");
        Builder builder = r.getRequestBuilder();
        for (NewCookie cookie : cookies) {
            builder.cookie(cookie);
        }
        String result = builder.get(String.class);
        assertEquals("The following flights has been booked: IKR-104 SKY-654 The following hotels has been booked: 211500-1 ",result);
    }
    @Test
    public void CancelItinerary() {
        Client client = Client.create();
        WebResource r = client.resource("http://localhost:8080/TravelAgencyService/webresources/travel/cancelItinerary");
        Builder builder = r.getRequestBuilder();
        for (NewCookie cookie : cookies) {
            builder.cookie(cookie);
        }
        String result = builder.delete(String.class);
        assertEquals("IKR-104 cancelled SKY-654 cancelled 211500-1 cancelled ",result);
    }
}