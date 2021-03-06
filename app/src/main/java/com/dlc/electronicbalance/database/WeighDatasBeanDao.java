package com.dlc.electronicbalance.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "WEIGH_DATAS_BEAN".
*/
public class WeighDatasBeanDao extends AbstractDao<WeighDatasBean, Long> {

    public static final String TABLENAME = "WEIGH_DATAS_BEAN";

    /**
     * Properties of entity WeighDatasBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Rq = new Property(1, String.class, "rq", false, "RQ");
        public final static Property U_account = new Property(2, String.class, "u_account", false, "U_ACCOUNT");
        public final static Property U_name = new Property(3, String.class, "u_name", false, "U_NAME");
        public final static Property Goods_id = new Property(4, String.class, "goods_id", false, "GOODS_ID");
        public final static Property Goods_name = new Property(5, String.class, "goods_name", false, "GOODS_NAME");
        public final static Property Nums = new Property(6, String.class, "nums", false, "NUMS");
        public final static Property Op_account = new Property(7, String.class, "op_account", false, "OP_ACCOUNT");
    }


    public WeighDatasBeanDao(DaoConfig config) {
        super(config);
    }
    
    public WeighDatasBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"WEIGH_DATAS_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"RQ\" TEXT," + // 1: rq
                "\"U_ACCOUNT\" TEXT," + // 2: u_account
                "\"U_NAME\" TEXT," + // 3: u_name
                "\"GOODS_ID\" TEXT," + // 4: goods_id
                "\"GOODS_NAME\" TEXT," + // 5: goods_name
                "\"NUMS\" TEXT," + // 6: nums
                "\"OP_ACCOUNT\" TEXT);"); // 7: op_account
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"WEIGH_DATAS_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, WeighDatasBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String rq = entity.getRq();
        if (rq != null) {
            stmt.bindString(2, rq);
        }
 
        String u_account = entity.getU_account();
        if (u_account != null) {
            stmt.bindString(3, u_account);
        }
 
        String u_name = entity.getU_name();
        if (u_name != null) {
            stmt.bindString(4, u_name);
        }
 
        String goods_id = entity.getGoods_id();
        if (goods_id != null) {
            stmt.bindString(5, goods_id);
        }
 
        String goods_name = entity.getGoods_name();
        if (goods_name != null) {
            stmt.bindString(6, goods_name);
        }
 
        String nums = entity.getNums();
        if (nums != null) {
            stmt.bindString(7, nums);
        }
 
        String op_account = entity.getOp_account();
        if (op_account != null) {
            stmt.bindString(8, op_account);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, WeighDatasBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String rq = entity.getRq();
        if (rq != null) {
            stmt.bindString(2, rq);
        }
 
        String u_account = entity.getU_account();
        if (u_account != null) {
            stmt.bindString(3, u_account);
        }
 
        String u_name = entity.getU_name();
        if (u_name != null) {
            stmt.bindString(4, u_name);
        }
 
        String goods_id = entity.getGoods_id();
        if (goods_id != null) {
            stmt.bindString(5, goods_id);
        }
 
        String goods_name = entity.getGoods_name();
        if (goods_name != null) {
            stmt.bindString(6, goods_name);
        }
 
        String nums = entity.getNums();
        if (nums != null) {
            stmt.bindString(7, nums);
        }
 
        String op_account = entity.getOp_account();
        if (op_account != null) {
            stmt.bindString(8, op_account);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public WeighDatasBean readEntity(Cursor cursor, int offset) {
        WeighDatasBean entity = new WeighDatasBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // rq
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // u_account
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // u_name
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // goods_id
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // goods_name
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // nums
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7) // op_account
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, WeighDatasBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setRq(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setU_account(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setU_name(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setGoods_id(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setGoods_name(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setNums(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setOp_account(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(WeighDatasBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(WeighDatasBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(WeighDatasBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
