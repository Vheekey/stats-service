package stats.service

import grails.boot.GrailsApp
import grails.boot.config.GrailsAutoConfiguration
import groovy.transform.CompileStatic
import org.springframework.context.annotation.ComponentScan
import org.springframework.kafka.annotation.EnableKafka

@ComponentScan(basePackages = ["stats.service"])
@EnableKafka
@CompileStatic
class Application extends GrailsAutoConfiguration {
    static void main(String[] args) {
        GrailsApp.run(Application, args)
    }
}
