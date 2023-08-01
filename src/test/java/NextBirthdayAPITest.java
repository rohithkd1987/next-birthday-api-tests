import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesRegex;

public class NextBirthdayAPITest {

    private static final String BASE_URL = "https://lx8ssktxx9.execute-api.eu-west-1.amazonaws.com/Prod/next-birthday";
    private String dateOfBirth = "1987-08-20";

    @Test
    public void testValidHourUnit() {
        given()
                .queryParam("dateofbirth", dateOfBirth)
                .queryParam("unit", "hour")
                .when()
                .get(BASE_URL)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("message", matchesRegex("\\d+ hours left"));
    }

    @Test
    public void testValidDayUnit() {
        given()
                .queryParam("dateofbirth", dateOfBirth)
                .queryParam("unit", "day")
                .when()
                .get(BASE_URL)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("message", matchesRegex("\\d+ days left"));
    }

    @Test
    public void testValidWeekUnit() {
        given()
                .queryParam("dateofbirth", dateOfBirth)
                .queryParam("unit", "week")
                .when()
                .get(BASE_URL)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("message", matchesRegex("\\d+ weeks left"));
    }

    @Test
    public void testValidMonthUnit() {
        given()
                .queryParam("dateofbirth", dateOfBirth)
                .queryParam("unit", "month")
                .when()
                .get(BASE_URL)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("message", matchesRegex("\\d+ months left"));
    }

    @Test
    public void testInvalidDateFormat() {
        given()
                .queryParam("dateofbirth", "1990/10/30")
                .queryParam("unit", "day")
                .when()
                .get(BASE_URL)
                .then()
                .statusCode(400)
                .contentType(ContentType.JSON)
                .body("error", equalTo("Invalid date format. Please use YYYY-MM-DD."));
    }

    @Test
    public void testInvalidDate() {
        given()
                .queryParam("dateofbirth", "2023-02-30") // 2023-02-30 is an invalid date
                .queryParam("unit", "day")
                .when()
                .get(BASE_URL)
                .then()
                .statusCode(400)
                .contentType(ContentType.JSON)
                .body("error", equalTo("Invalid date."));
    }

    @Test
    public void testInvalidUnit() {
        given()
                .queryParam("dateofbirth", dateOfBirth)
                .queryParam("unit", "invalid")
                .when()
                .get(BASE_URL)
                .then()
                .statusCode(400)
                .contentType(ContentType.JSON)
                .body("error", equalTo("Invalid unit. Available options: hour/day/week/month."));
    }
}
