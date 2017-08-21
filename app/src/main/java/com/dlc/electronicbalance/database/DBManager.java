package com.dlc.electronicbalance.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class DBManager {
    private final static String dbName = "weight_data_db";
    private static DBManager mInstance;
    private DaoMaster.DevOpenHelper openHelper;
    private Context context;

    public DBManager(Context context) {
        this.context = context;
        openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
    }

    /**
     * 获取单例引用
     *
     * @param context
     * @return
     */
    public static DBManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DBManager.class) {
                if (mInstance == null) {
                    mInstance = new DBManager(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        }
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db;
    }

    /**
     * 获取可写数据库
     */
    private SQLiteDatabase getWritableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        }
        SQLiteDatabase db = openHelper.getWritableDatabase();
        return db;
    }

    /**
     * 插入称重数据
     * @param bean
     */
    public void insertWeightDatas(WeighDatasBean bean) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        WeighDatasBeanDao weighDatasBeanDao = daoSession.getWeighDatasBeanDao();
        weighDatasBeanDao.insert(bean);
    }

    /**
     * 获取称重数据列表
     */
    public List<WeighDatasBean> queryWeightList(Long datatime) {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        WeighDatasBeanDao weighDatasBeanDao = daoSession.getWeighDatasBeanDao();
        QueryBuilder<WeighDatasBean> qb = weighDatasBeanDao.queryBuilder();
       // qb.where(weighDatasBeanDao.Properties.Age.gt(age)).orderAsc(UserDao.Properties.Age);
        List<WeighDatasBean> list = qb.list();
        return list;
    }


}