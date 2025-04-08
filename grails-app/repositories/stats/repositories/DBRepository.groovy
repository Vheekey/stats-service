package stats.repositories

import stats.service.InfluxEntity

interface DBRepository {
    void save(InfluxEntity entity)

    List<Map<String, Object>> query(String fluxQuery)
}