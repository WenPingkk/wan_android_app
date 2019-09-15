package com.sean.module.main.db;

import android.database.sqlite.SQLiteDatabase;

import com.sean.base.library.base.BaseApplication;
import com.sean.module.main.bean.db.DaoMaster;
import com.sean.module.main.bean.db.DaoSession;
import com.sean.module.main.bean.db.SearchHistoryDao;

/**
 * Author WenPing
 * CreateTime 2019/9/14.
 * Description:
 * 搜索数据管理者 DbManager
 *
 */
public class DbManager {

    private static DbManager instance;
    private final SearchHistoryDao searchHistoryDao;

    public DbManager() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(BaseApplication.getApplication(), "wandroid");
        SQLiteDatabase database = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        DaoSession daoSession = daoMaster.newSession();
        searchHistoryDao = daoSession.getSearchHistoryDao();
    }

    public static DbManager getInstance() {
        if (instance == null) {
            synchronized (DbManager.class) {
                if (instance == null) {
                    instance = new DbManager();
                }
            }
        }
        return instance;
    }

    public SearchHistoryDao getSearchHistoryDao() {
        return searchHistoryDao;
    }
}

