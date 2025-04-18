package stats.service

import grails.testing.gorm.DomainUnitTest
import org.springframework.test.annotation.Rollback
import spock.lang.Specification
import java.time.Instant

@Rollback
class UsersCreatedSpec extends Specification implements DomainUnitTest<UsersCreated> {

    void "test should create valid UsersCreated entity"() {
        given:
        def dateCreated = Instant.now();
        def userId = 13;
        def email = "test@test.com";
        def active = true;
        def name = "test";

        UsersCreated usersCreated = new UsersCreated(
                userId: userId,
                email: email,
                active: active,
                name: name
        )
        usersCreated.setDateCreated(dateCreated)


        expect: "Entity values are valid"
        usersCreated.getMeasurementName() == UsersCreated.MEASUREMENT_NAME
        usersCreated.getName() == name
        usersCreated.getEmail() == email
        usersCreated.getUserId() == userId
        usersCreated.getActive() == active
        usersCreated.getDateCreated() != null
        usersCreated.getDateCreated() == dateCreated
    }

    void "test domain constraints apply to UsersCreated entity"() {
        given:
        UsersCreated usersCreated = new UsersCreated(
                userId: userId,
                name: name,
                email: email,
                active: active
        )
        usersCreated.setDateCreated(dateCreated)

        when:
        def valid = usersCreated.validate()

        then:
        valid == expectedValidity

        where:
        userId | dateCreated   | name    | email           | active || expectedValidity
        null   | Instant.now() | "test1" | "test@test.com" | true   || false
        13     | Instant.now() | ""      | "test@test.com" | true   || false
        14     | Instant.now() | "test2" | "test@test.com" | true   || true
    }
}
