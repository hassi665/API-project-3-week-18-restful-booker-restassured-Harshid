package com.restful.booker.bookinginfo;

import com.restful.booker.constants.EndPoints;
import com.restful.booker.model.BookingPojo;
import com.restful.booker.model.PatchBookingPojo;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

public class BookingSteps {

    @Step("Creating new booking wiht firstName : {0}, lastName: {1}, totalprice: {2} depositpaid: {3}, bookingdates: {4} and additonalneeds: {5}")
    public ValidatableResponse createBooking(String firstname, String lastname, int totalprice, boolean depositpaid, BookingPojo.BookingDates bookingdates, String additionalneeds) {

        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setFirstname(firstname);
        bookingPojo.setLastname(lastname);
        bookingPojo.setTotalprice(totalprice);
        bookingPojo.setDepositpaid(depositpaid);
        bookingPojo.setBookingdates(bookingdates);
        bookingPojo.setAdditionalneeds(additionalneeds);
        return SerenityRest.given()
                .header("Content-Type", "application/json")
                .when()
                .body(bookingPojo)
                .post()
                .then().log().all();

    }

    @Step("Getting all Booking ")
    public ValidatableResponse getAllBookingId() {

        return SerenityRest.given().log().all()
                .when()
                .get()
                .then().log().all().statusCode(200);
    }

    @Step("Getting exisiting single booking with id: {0}")
    public ValidatableResponse getSingleBookingIDs(int id) {
        return SerenityRest.given()
                .pathParam("id", id)
                .when()
                .get(EndPoints.GET_SINGLE_BOOKING_BY_ID)
                .then().log().all();
    }

    @Step("Updating record with id:{0}, token{1}, firstName: {2}, lastName: {3}, totalprice: {4} depositpaid: {5}, bookingdates: {6} and additonalneeds: {7} ")
    public ValidatableResponse updateBookingWithID(int id, String token, String firstname, String lastname, int totalprice, boolean depositpaid, BookingPojo.BookingDates bookingdates, String additionalneeds) {

        BookingPojo bookingPojo = new BookingPojo();
        PatchBookingPojo patchBookingPojo = new PatchBookingPojo();
        bookingPojo.setFirstname(firstname);
        bookingPojo.setLastname(lastname);
        bookingPojo.setTotalprice(totalprice);
        bookingPojo.setDepositpaid(depositpaid);
        bookingPojo.setBookingdates(bookingdates);
        bookingPojo.setAdditionalneeds(additionalneeds);
        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Cookie", "token=" + token)
                .pathParam("id", id)
                .body(bookingPojo)
                .when().put(EndPoints.UPDATE_BOOKING_BY_ID)
                .then().log().all().statusCode(200);
    }

    @Step("Deleting existing booking with id: {0} and token: {1}")
    public ValidatableResponse deleteABookingID(int id, String token) {
        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .header("Cookie", "token=" + token)
                .pathParam("id", id)
                .when().delete(EndPoints.DELETE_BOOKING_BY_ID)
                .then().log().all();

    }

    @Step("Getting booking info by ID")
    public ValidatableResponse getBookingInfoByID() {
        return SerenityRest.given()
                .when()
                .get()
                .then().statusCode(200);
    }


}
