package com.oldphone.dao;

import android.content.Context;

import com.oldphone.entity.Contacts;

import java.util.List;

public class ContactsDao {
    private DaoManager daoManager;

    public ContactsDao(Context context) {
        daoManager = DaoManager.getInstance();
        daoManager.init(context);
    }

    public boolean insertContact(Contacts contacts) {
        return daoManager.getDaoSession().getContactsDao().insert(contacts) == -1 ? false : true;
    }

    public boolean updateContact(Contacts contacts) {
        boolean ret = false;
        try {
            daoManager.getDaoSession().update(contacts);
            ret = true;
        } catch (Exception e) {
            ret = false;
        }
        return ret;
    }

    public boolean deleteContact(Contacts contacts) {
        boolean ret = false;
        try {
            daoManager.getDaoSession().delete(contacts);
            ret = true;
        } catch (Exception e) {
            ret = false;
        }
        return ret;
    }

    public List<Contacts> queryAllContacts() {
        return daoManager.getDaoSession().loadAll(Contacts.class);
    }

    public List<Contacts> queryByNativeSql(String sql,String[] conditions){
        return daoManager.getDaoSession().queryRaw(Contacts.class,sql,conditions);
    }

}

