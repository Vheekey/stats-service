package stats.service
import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration

import geb.spock.*

/**
 * See https://www.gebish.org/manual/current/ for more instructions
 */
@Integration
@Rollback
class StatsServiceSpec extends GebSpec {

    void "test something"() {
        when:"The home page is visited"
            go '/'

        then:"The title is correct"
            title == "This is the statistics service"
    }

}
