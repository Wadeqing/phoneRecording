package com.sinocall.phonerecordera.dao;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by mac on 2017/12/9.
 */
@Entity
public class FileDownloadBean {

    @Id
    private long fid;
    private String urlPath;
    private String filePath;
    private String fileName;
    private long fileSize;
    private String createTime;
    @Generated(hash = 1732408057)
    public FileDownloadBean(long fid, String urlPath, String filePath,
            String fileName, long fileSize, String createTime) {
        this.fid = fid;
        this.urlPath = urlPath;
        this.filePath = filePath;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.createTime = createTime;
    }
    @Generated(hash = 1082129065)
    public FileDownloadBean() {
    }
    public long getFid() {
        return this.fid;
    }
    public void setFid(long fid) {
        this.fid = fid;
    }
    public String getUrlPath() {
        return this.urlPath;
    }
    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }
    public String getFilePath() {
        return this.filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
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
   

}
