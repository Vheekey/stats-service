package stats.service

import grails.testing.gorm.DomainUnitTest
import grails.testing.services.ServiceUnitTest
import org.springframework.test.annotation.Rollback
import spock.lang.Specification
import stats.repositories.InfluxDBRepository

import java.time.Instant

@Rollback
class UserServiceSpec extends Specification implements ServiceUnitTest<UserService>, DomainUnitTest<UsersCreated> {

    InfluxDBRepository influxDBRepository;
    UserService service;

    def setup() {
        influxDBRepository = Mock(InfluxDBRepository)
        service = new UserService(influxDBRepository)
    }

    void "test valid records are stored in the db"() {
        given:
        UserCreatedDTO userCreatedDTO = new UserCreatedDTO(
                userId: 13,
                email: "test1@test.com",
                role: "USER",
                name: "test",
                active: true
        )
        userCreatedDTO.setDateCreated("2025-04-18T19:25:11.847187Z")

        when:
        def isSaved = service.saveUser(userCreatedDTO)

        then:
        isSaved == true
    }

    void "test exception is thrown when argument is null or invalid"() {
        given: "Some arguments are missing"
        UserCreatedDTO userCreatedDTO = new UserCreatedDTO(
                userId: 14,
                email: "test1@test.com",
                role: "USER",
                dateCreated: Instant.now().toString(),
                active: true
        )

        when:
        def isSaved = service.saveUser(userCreatedDTO)

        then: "Exception is thrown"
        thrown(IllegalArgumentException)
    }
}
