package pl.chiqvito.sowieso.db.service;

import android.util.Log;

import pl.chiqvito.sowieso.db.DBConstatants;
import pl.chiqvito.sowieso.db.DbManager;
import pl.chiqvito.sowieso.db.dao.PropertiesDao;
import pl.chiqvito.sowieso.db.model.PropertyEntity;

public class PropertiesServiceImpl implements PropertiesService {

    private static final String TAG = PropertiesServiceImpl.class.getName();

    private final PropertiesDao dao;

    public PropertiesServiceImpl(DbManager db) {
        dao = new PropertiesDao(db);
    }

    @Override
    public String getSessionId() {
        PropertyEntity p = dao.get(DBConstatants.DB_TABLE_PROPERTIES_A__NAME_SID);
        if (p != null) {
            return p.getValue();
        }
        return null;
    }

    @Override
    public void saveSessionId(String sessionId) {
        String id = DBConstatants.DB_TABLE_PROPERTIES_A__NAME_SID;
        save(id, sessionId);
    }

    @Override
    public void removeSessionId() {
        PropertyEntity p = new PropertyEntity();
        p.setName(DBConstatants.DB_TABLE_PROPERTIES_A__NAME_SID);
        dao.delete(p);
    }

    private void save(String id, String value) {
        PropertyEntity p = dao.get(id);
        if (p == null) {
            p = new PropertyEntity();
            p.setName(id);
            p.setValue(value);
            Log.d(TAG, "insert " + p);
            dao.insert(p);
        } else {
            p.setValue(value);
            Log.d(TAG, "update " + p);
            dao.update(p);
        }
    }

}
