package com.example.demo;

import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserEntityRepository;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY;

@DataJpaTest
@AutoConfigureEmbeddedDatabase(provider = ZONKY)
@Sql("/init-data.sql")
public class NewTest2 {

    @Autowired
    UserEntityRepository userEntityRepository;

    @Test
    void test(){

        UserEntity userEntity = new UserEntity();
        userEntity.setName("test");
        userEntityRepository.save(userEntity);
        List<UserEntity> userEntities = userEntityRepository.findAll();
        Assertions.assertEquals(1, userEntityRepository.findAll().size());
    }
}
