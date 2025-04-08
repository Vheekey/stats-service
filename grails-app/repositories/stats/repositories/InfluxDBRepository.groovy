package stats.repositories

import com.influxdb.client.WriteApiBlocking
import com.influxdb.client.domain.WritePrecision
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import stats.service.InfluxDBConnectionService
import stats.service.InfluxEntity

@Repository
class InfluxDBRepository implements DBRepository {

    @Autowired
    InfluxDBConnectionService influxDBConnectionService

    @Override
    void save(InfluxEntity entity) {
        WriteApiBlocking writeApi = influxDBConnectionService.getInfluxDbClient().getWriteApiBlocking()
        writeApi.writeMeasurement(WritePrecision.NS, entity)

    }

    @Override
    List<Map<String, Object>> query(String fluxQuery) {
        def queryApi = influxDBConnectionService.getInfluxDbClient().getQueryApi()
        return queryApi.query(fluxQuery)
                ?.collectMany { table ->
                    table.records.collect { record ->
                        record.values as Map<String, Object>
                    }
                } ?: []
    }
}
