package stats.service

import com.influxdb.client.InfluxDBClient;

interface ConnectionService {
    void init();

    boolean connect();

    void close();

    InfluxDBClient getInfluxDbClient();

    ConnectionService getConnectionURL()

    ConnectionService getDBUsername()

    ConnectionService getDBPassword()

    ConnectionService getDB()

    ConnectionService getConfigToken()

    ConnectionService getConfigOrg()
}
