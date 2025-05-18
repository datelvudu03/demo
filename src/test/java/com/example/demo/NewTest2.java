package com.example.demo;

import com.example.demo.entity.TicketEntity;
import com.example.demo.repository.TicketEntityRepository;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;


import java.util.List;

import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY;

@DataJpaTest
@AutoConfigureEmbeddedDatabase(provider = ZONKY)
@RequiredArgsConstructor
@TestPropertySource(locations = "classpath:application-test.properties")
public class NewTest2 {

    private final TicketEntityRepository ticketEntityRepository;

    @Test
    void test(){
        ticketEntityRepository.save(new TicketEntity());
        List<TicketEntity> userEntities = ticketEntityRepository.findAll();
        Assertions.assertEquals(1, ticketEntityRepository.findAll().size());
    }
}
