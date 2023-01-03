package com.restful.booker.bookinginfo;

import com.restful.booker.model.BookingPojo;
import com.restful.booker.testbase.TestBase;
import com.restful.booker.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class BookingCRUDTestWithSteps extends TestBase {

    static String firstname = "Harshid" + getRandomValue();
    static String lastname = "Prime" + getRandomValue();
    static int totalprice = Integer.parseInt(1 + getRandomValue());
    static boolean depositpaid = true;
    static String additionalneeds = "Breakfast";

    static String token;
    static int id;

    @Steps
    AuthorisationSteps authorisationSteps;
    @Steps
    BookingSteps bookingSteps;

    @Title("This method will create a Token")
    @Test
    public void test001() {
        ValidatableResponse response = authorisationSteps.getAuthToken().statusCode(200);
        token = response.extract().path("token");
    }

    @Title("This method will create a booking")
    @Test
    public void test002() {
        BookingPojo.BookingDates bookingdates = new BookingPojo.BookingDates();
        bookingdates.setCheckin("2023-01-05");
        bookingdates.setCheckout("2023-01-10");
        ValidatableResponse response = bookingSteps.createBooking(firstname, lastname, totalprice, depositpaid, bookingdates, additionalneeds).statusCode(200);
        id = response.extract().path("bookingid");
    }


    @Title("This method will verify new Booking ID creation")
    @Test
    public void test003() {
        ValidatableResponse response = bookingSteps.getBookingInfoByID();
        ArrayList<?> booking = response.extract().path("bookingid");
        Assert.assertTrue(booking.contains(id));
    }

    @Title("This method will get booking with Id")
    @org.junit.Test
    public void test004() {
        bookingSteps.getSingleBookingIDs(id).statusCode(200);
    }

    @Title("This method will updated a booking with ID")
    @Test
    public void test005() {
        additionalneeds =  "lunch";
        BookingPojo.BookingDates bookingdates = new BookingPojo.BookingDates();
        bookingdates.setCheckin("2023-01-05");
        bookingdates.setCheckout("2023-01-10");
        bookingSteps.updateBookingWithID(id, token, firstname, lastname, totalprice, depositpaid, bookingdates, additionalneeds);
        ValidatableResponse response = bookingSteps.getSingleBookingIDs(id);
        HashMap<String, ?> update = response.extract().path("");
        Assert.assertThat(update, hasValue(additionalneeds));
    }

    @Title("This method will delete a booking with ID and verify deleted booking")
    @Test
    public void test006() {
        bookingSteps.deleteABookingID(id, token).statusCode(201);
        bookingSteps.getSingleBookingIDs(id).statusCode(404);
    }

    @Title("This method will get all bookings")
    @Test
    public void test008() {
        bookingSteps.getAllBookingId();
    }

}
