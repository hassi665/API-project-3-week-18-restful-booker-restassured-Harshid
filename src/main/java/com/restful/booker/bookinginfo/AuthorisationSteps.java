package com.restful.booker.bookinginfo;

import com.restful.booker.model.AuthorisationPojo;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

public class AuthorisationSteps {

    @Step("Getting Access Tokken")
    public ValidatableResponse getAuthToken() {
        AuthorisationPojo authorisationPojo = new AuthorisationPojo();
        authorisationPojo.setUsername("admin");
        authorisationPojo.setPassword("password123");

        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .when()
                .body(authorisationPojo)
                .post("https://restful-booker.herokuapp.com/auth")
                .then().log().all();

    }
}
