package com.sinocall.phonerecordera.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by qingchao on 2017/12/7.
 */
@Entity
public class CallLogBean {
    @Id
    private long id;
    private String name;
    private String phoneNum;
    @Generated(hash = 767922866)
    public CallLogBean(long id, String name, String phoneNum) {
        this.id = id;
        this.name = name;
        this.phoneNum = phoneNum;
    }
    @Generated(hash = 837774923)
    public CallLogBean() {
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
    public String getPhoneNum() {
        return this.phoneNum;
    }
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
