package com.example.demo;

import com.example.demo.entity.TicketEntity;
import com.example.demo.gen.model.Ticket;
import com.example.demo.repository.TicketEntityRepository;
import io.restassured.RestAssured;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY;

@AutoConfigureEmbeddedDatabase(provider = ZONKY)
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TicketControllerTest {

    @Autowired
    private TicketEntityRepository ticketEntityRepository;

    private static final String BASE_URI = "http://localhost";
    private static final String PARAM_POSITION = "position";

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUpBcContractTest() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
    }

    @AfterEach
    void tearDownBcContractTest() {
        ticketEntityRepository.deleteAll();
    }

    @Test
    void createTicketTest() {
        createTickets(3);
        List<TicketEntity> tickets = ticketEntityRepository.findAll(Sort.by(Sort.Direction.ASC, PARAM_POSITION));
        Assertions.assertEquals(3, tickets.size());
        TicketEntity ticket = tickets.get(2);
        Assertions.assertNotNull(ticket);
        Assertions.assertEquals(2, ticket.getPosition());
    }

    @Test
    void getCurrentTicketTest() {
        createTickets(1);
        RestAssured.given()
                .when()
                .get("/tickets/current")
                .then()
                .statusCode(200)
                .extract()
                .as(Ticket.class);
    }

    @Test
    void removeCurrentTicketTest() {
        createTickets(2);
        List<TicketEntity> tickets = ticketEntityRepository.findAll(Sort.by(Sort.Direction.ASC, PARAM_POSITION));

        Assertions.assertEquals(2, tickets.size());

        RestAssured.given().when().delete("/tickets/current").then().statusCode(204);
        tickets = ticketEntityRepository.findAll(Sort.by(Sort.Direction.ASC, PARAM_POSITION));

        Assertions.assertEquals(1, tickets.size());
        Assertions.assertEquals(0, tickets.get(0).getPosition());

        RestAssured.given().when().delete("/tickets/current").then().statusCode(204);
        tickets = ticketEntityRepository.findAll(Sort.by(Sort.Direction.ASC, PARAM_POSITION));

        Assertions.assertEquals(0, tickets.size());

    }

    private void createTickets(int count) {
        for (int i = 0; i < count; i++) {
            RestAssured.given().when().post("/tickets").then().statusCode(200).extract().as(Ticket.class);
        }
    }
}
