package pl.chiqvito.sowieso.db.service;

public interface PropertiesService {

    String getSessionId();

    void saveSessionId(String sessionId);

    void removeSessionId();

}
