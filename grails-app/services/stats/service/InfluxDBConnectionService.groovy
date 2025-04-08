package stats.service

import com.influxdb.client.InfluxDBClient
import com.influxdb.client.InfluxDBClientFactory;
import grails.core.GrailsApplication
import grails.gorm.services.Service
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import javax.annotation.PostConstruct

@Slf4j
@Service
class InfluxDBConnectionService implements ConnectionService {
    @Autowired
    GrailsApplication grailsApplication;

    String url
    String token
    String org
    String databaseName
    String databaseUser
    String databasePassword
    InfluxDBClient influxDBClient

    ConnectionService getConnectionURL() {
        url = grailsApplication.config.getProperty('influxdb.url')
        this;
    }

    ConnectionService getDBUsername() {
        databaseUser = grailsApplication.config.getProperty('influxdb.username')
        this;
    }

    ConnectionService getDBPassword() {
        databasePassword = grailsApplication.config.getProperty('influxdb.password')
        this;
    }

    ConnectionService getDB() {
        databaseName = grailsApplication.config.getProperty('influxdb.bucket')
        this;
    }

    ConnectionService getConfigToken() {
        token = grailsApplication.config.getProperty('influxdb.token')
        this;
    }

    ConnectionService getConfigOrg() {
        org = grailsApplication.config.getProperty('influxdb.org')
        this;
    }

    InfluxDBClient getInfluxDbClient() {
        influxDBClient = InfluxDBClientFactory.create(url, token.toCharArray(), org, databaseName)
        return influxDBClient;
    }


    @PostConstruct
    @Override
    void init() {
        getConnectionURL()
        if (!url) {
            throw new IllegalStateException('Connection url is not set')
        }

        getConfigToken()
        if (!token) {
            throw new IllegalStateException('Config token is not set')
        }

        getDBUsername()
        if (!databaseUser) {
            throw new IllegalStateException('Database user is not set')
        }

        getDBPassword()
        if (!databasePassword) {
            throw new IllegalStateException('Database password is not set')
        }

        getConfigOrg()
        if (!org) {
            throw new IllegalStateException('Org is not set')
        }

        getDB()
        if (!databaseName) {
            throw new IllegalStateException('Database name is not set')
        }

        getInfluxDbClient()
        if (!influxDBClient) {
            throw new IllegalStateException('InfluxDB client is not set')
        }
    }

    @Override
    boolean connect() {
        boolean connected = false;
        try {
            connected = influxDBClient.ping()

            if (!connected) {
                log.error("Connection failed")
            }

            log.info("Connected to influxdb")
        } catch (Exception e) {
            log.error(e.getMessage())
        }

        return connected;
    }

    void close() {
        influxDBClient?.close()
        log.info("Connection closed")
    }
}
