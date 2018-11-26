package com.sinocall.phonerecordera.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by qingchao on 2017/12/7.
 */
@Entity
public class ReCordingBean {
    @Id
    private long id;
    private String fileName;
    private long fileSize;
    private String createTime;
    private String expireTime;
    private String descTel;
    private String srcTel;
    private String fileExt;
    @Generated(hash = 972000715)
    public ReCordingBean(long id, String fileName, long fileSize, String createTime,
            String expireTime, String descTel, String srcTel, String fileExt) {
        this.id = id;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.createTime = createTime;
        this.expireTime = expireTime;
        this.descTel = descTel;
        this.srcTel = srcTel;
        this.fileExt = fileExt;
    }
    @Generated(hash = 2068451551)
    public ReCordingBean() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getFileName() {
        return this.fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public long getFileSize() {
        return this.fileSize;
    }
    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }
    public String getCreateTime() {
        return this.createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public String getExpireTime() {
        return this.expireTime;
    }
    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }
    public String getDescTel() {
        return this.descTel;
    }
    public void setDescTel(String descTel) {
        this.descTel = descTel;
    }
    public String getSrcTel() {
        return this.srcTel;
    }
    public void setSrcTel(String srcTel) {
        this.srcTel = srcTel;
    }
    public String getFileExt() {
        return this.fileExt;
    }
    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }


}
