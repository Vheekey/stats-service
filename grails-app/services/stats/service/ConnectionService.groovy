package stats.service;

interface ConnectionService
{
    void init();

    boolean connect();

    ConnectionService getConnectionURL()
    ConnectionService getDBUsername()
    ConnectionService getDBPassword()
    ConnectionService getDB()
    ConnectionService getConfigToken()
    ConnectionService getConfigOrg()

}