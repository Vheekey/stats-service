package stats.service

import grails.gorm.transactions.Transactional
import stats.repositories.InfluxDBRepository
import java.time.Instant


@Transactional
class UserService {

    protected InfluxDBRepository influxDBRepository;

    UserService(InfluxDBRepository influxDBRepository) {
        this.influxDBRepository = influxDBRepository;
    }

    def saveUser(UserCreatedDTO userCreatedDTO) {
        Instant dateCreated = Instant.parse(userCreatedDTO.getDateCreated())

        UsersCreated usersCreated = new UsersCreated(
                userId: userCreatedDTO.getUserId(),
                email: userCreatedDTO.getEmail(),
                active: userCreatedDTO.isActive(),
                name: userCreatedDTO.getName()
        )
        usersCreated.setDateCreated(dateCreated)

        if (!usersCreated.validate()) {
            throw new IllegalArgumentException("Missing entity arguments on save" + usersCreated.properties)
        }

        this.influxDBRepository.save(usersCreated)

        return true
    }
}
