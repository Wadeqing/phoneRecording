package com.sinocall.phonerecordera.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.sinocall.phonerecordera.dao.ReCordingBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "RE_CORDING_BEAN".
*/
public class ReCordingBeanDao extends AbstractDao<ReCordingBean, Long> {

    public static final String TABLENAME = "RE_CORDING_BEAN";

    /**
     * Properties of entity ReCordingBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, long.class, "id", true, "_id");
        public final static Property FileName = new Property(1, String.class, "fileName", false, "FILE_NAME");
        public final static Property FileSize = new Property(2, long.class, "fileSize", false, "FILE_SIZE");
        public final static Property CreateTime = new Property(3, String.class, "createTime", false, "CREATE_TIME");
        public final static Property ExpireTime = new Property(4, String.class, "expireTime", false, "EXPIRE_TIME");
        public final static Property DescTel = new Property(5, String.class, "descTel", false, "DESC_TEL");
        public final static Property SrcTel = new Property(6, String.class, "srcTel", false, "SRC_TEL");
        public final static Property FileExt = new Property(7, String.class, "fileExt", false, "FILE_EXT");
    }


    public ReCordingBeanDao(DaoConfig config) {
        super(config);
    }
    
    public ReCordingBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"RE_CORDING_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY NOT NULL ," + // 0: id
                "\"FILE_NAME\" TEXT," + // 1: fileName
                "\"FILE_SIZE\" INTEGER NOT NULL ," + // 2: fileSize
                "\"CREATE_TIME\" TEXT," + // 3: createTime
                "\"EXPIRE_TIME\" TEXT," + // 4: expireTime
                "\"DESC_TEL\" TEXT," + // 5: descTel
                "\"SRC_TEL\" TEXT," + // 6: srcTel
                "\"FILE_EXT\" TEXT);"); // 7: fileExt
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"RE_CORDING_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ReCordingBean entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String fileName = entity.getFileName();
        if (fileName != null) {
            stmt.bindString(2, fileName);
        }
        stmt.bindLong(3, entity.getFileSize());
 
        String createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindString(4, createTime);
        }
 
        String expireTime = entity.getExpireTime();
        if (expireTime != null) {
            stmt.bindString(5, expireTime);
        }
 
        String descTel = entity.getDescTel();
        if (descTel != null) {
            stmt.bindString(6, descTel);
        }
 
        String srcTel = entity.getSrcTel();
        if (srcTel != null) {
            stmt.bindString(7, srcTel);
        }
 
        String fileExt = entity.getFileExt();
        if (fileExt != null) {
            stmt.bindString(8, fileExt);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ReCordingBean entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String fileName = entity.getFileName();
        if (fileName != null) {
            stmt.bindString(2, fileName);
        }
        stmt.bindLong(3, entity.getFileSize());
 
        String createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindString(4, createTime);
        }
 
        String expireTime = entity.getExpireTime();
        if (expireTime != null) {
            stmt.bindString(5, expireTime);
        }
 
        String descTel = entity.getDescTel();
        if (descTel != null) {
            stmt.bindString(6, descTel);
        }
 
        String srcTel = entity.getSrcTel();
        if (srcTel != null) {
            stmt.bindString(7, srcTel);
        }
 
        String fileExt = entity.getFileExt();
        if (fileExt != null) {
            stmt.bindString(8, fileExt);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    @Override
    public ReCordingBean readEntity(Cursor cursor, int offset) {
        ReCordingBean entity = new ReCordingBean( //
            cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // fileName
            cursor.getLong(offset + 2), // fileSize
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // createTime
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // expireTime
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // descTel
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // srcTel
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7) // fileExt
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ReCordingBean entity, int offset) {
        entity.setId(cursor.getLong(offset + 0));
        entity.setFileName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setFileSize(cursor.getLong(offset + 2));
        entity.setCreateTime(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setExpireTime(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setDescTel(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setSrcTel(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setFileExt(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(ReCordingBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(ReCordingBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ReCordingBean entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}