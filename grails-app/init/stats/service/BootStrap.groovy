package stats.service

class BootStrap {

    def init = { servletContext ->
        // Create logs directory if it doesn't exist
        new File('logs').mkdirs()
    }
    def destroy = {
    }
}