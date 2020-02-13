package com.oldphone;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.oldphone.acts.BaseActivity;
import com.oldphone.acts.ContactAddAct;
import com.oldphone.adpts.ContactsAdapter;
import com.oldphone.dao.ContactsDao;
import com.oldphone.entity.Contacts;
import com.oldphone.evebus.BusData;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.rv_main_contacts)
    RecyclerView rv_contacts;

    private List<Contacts> contacts = new ArrayList<>();
    private ContactsAdapter contactsAdapter;
    private ContactsDao contactsDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.fb_main_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fb_main_add:
                startActivity(new Intent(MainActivity.this, ContactAddAct.class));
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BusData data) {
        switch (data.getType()) {
            case CONTACT_FRESH:
                freshContacts();
                break;
            case ACTION_CALL:
                int position = (int) data.getData();
                callContact(position);
                break;
        }
    }

    private void init() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        rv_contacts.setItemAnimator(null);
        rv_contacts.setLayoutManager(layoutManager);

        contactsAdapter = new ContactsAdapter(this);
        contactsAdapter.setDatas(contacts);
        rv_contacts.setAdapter(contactsAdapter);

        contactsDao = new ContactsDao(this);
        freshContacts();
    }

    private void freshContacts() {
        this.contacts = contactsDao.queryAllContacts();
        contactsAdapter.setDatas(this.contacts);
    }

    private void callContact(int position) {
        String phoneNum = this.contacts.get(position).getPhone();
        call("tel:"+phoneNum);
    }

}
