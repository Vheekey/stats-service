package spring

import stats.repositories.InfluxDBRepository
import stats.service.InfluxDBConnectionService

// Place your Spring DSL code here
beans = {
    influxDBConnectionService(InfluxDBConnectionService)
    influxDBRepository(InfluxDBRepository)
    kafkaConfig(KafkaConfig)
}