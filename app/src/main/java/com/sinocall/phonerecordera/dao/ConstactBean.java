package com.sinocall.phonerecordera.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by qingchao on 2017/12/5.
 *
 */
@Entity
public class ConstactBean {
    @Id
    private long id;
    private String name;
    private String pinyin;
    private String jianpin;
    private String sortKey;
    @NotNull
    private String mobile;
    @Generated(hash = 1604891875)
    public ConstactBean(long id, String name, String pinyin, String jianpin,
            String sortKey, @NotNull String mobile) {
        this.id = id;
        this.name = name;
        this.pinyin = pinyin;
        this.jianpin = jianpin;
        this.sortKey = sortKey;
        this.mobile = mobile;
    }
    @Generated(hash = 985095275)
    public ConstactBean() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPinyin() {
        return this.pinyin;
    }
    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
    public String getJianpin() {
        return this.jianpin;
    }
    public void setJianpin(String jianpin) {
        this.jianpin = jianpin;
    }
    public String getSortKey() {
        return this.sortKey;
    }
    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }
    public String getMobile() {
        return this.mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
