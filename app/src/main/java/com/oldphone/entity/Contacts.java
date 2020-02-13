package com.oldphone.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Contacts {
    @Id(autoincrement = true)
    private Long _id;
    private String name;
    private String phone;
    private String avatar;
    @Generated(hash = 1079599923)
    public Contacts(Long _id, String name, String phone, String avatar) {
        this._id = _id;
        this.name = name;
        this.phone = phone;
        this.avatar = avatar;
    }
    @Generated(hash = 1804918957)
    public Contacts() {
    }
    public Long get_id() {
        return this._id;
    }
    public void set_id(Long _id) {
        this._id = _id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getAvatar() {
        return this.avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


}
