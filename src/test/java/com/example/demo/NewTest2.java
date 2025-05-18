package com.example.demo;

import com.example.demo.entity.TicketEntity;
import com.example.demo.gen.model.Ticket;
import com.example.demo.repository.TicketEntityRepository;
import io.restassured.RestAssured;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import lombok.RequiredArgsConstructor;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;


import java.util.List;

import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DataJpaTest
@AutoConfigureEmbeddedDatabase(provider = ZONKY)
@TestPropertySource(locations = "classpath:application-test.properties")
public class NewTest2 {

    @Autowired
    private TicketEntityRepository ticketEntityRepository;

    @Test
    void test(){
        ticketEntityRepository.save(new TicketEntity());
        ticketEntityRepository.save(new TicketEntity());
        ticketEntityRepository.save(new TicketEntity());
        List<TicketEntity> userEntities = ticketEntityRepository.findAll();
        Assertions.assertEquals(3, ticketEntityRepository.findAll().size());
    }

    @Test
    void test2(){
        Ticket ticket = RestAssured.given().when().get("/tickets").then().statusCode(200).extract().as(Ticket.class);
        assertSoftly(softly -> {
            softly.assertThat(ticket).isNotNull();
        });
    }
}
