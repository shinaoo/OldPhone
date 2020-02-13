package com.oldphone.acts;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.oldphone.R;
import com.oldphone.dao.ContactsDao;
import com.oldphone.entity.Contacts;
import com.oldphone.evebus.BusData;
import com.oldphone.evebus.EveType;
import com.oldphone.utils.Constants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactAddAct extends AppCompatActivity {

    @BindView(R.id.iv_contactadd_avatar)
    ImageView iv_avatar;
    @BindView(R.id.et_contactadd_name)
    EditText et_name;
    @BindView(R.id.et_contactadd_phone)
    EditText et_phone;

    private ContactsDao contactsDao;
    private String avatarPath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_contactadd);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.tv_contactadd_back, R.id.tv_contactadd_add, R.id.iv_contactadd_avatar})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_contactadd_back:
                finish();
                break;
            case R.id.tv_contactadd_add:
                addContact();
                finish();
                break;
            case R.id.iv_contactadd_avatar:
                openAlbum();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constants.REQUEST_OPEN_ALBUM:
                    final String filePath = getFilePathFromContentUri(data.getData(), this);
                    if (filePath != null && !filePath.equalsIgnoreCase("")) {
                        String temp = filePath.trim().toLowerCase();
                        if (temp.endsWith(Constants.IMAGE_FILE_TYPE_JPG)
                                || temp.endsWith(Constants.IMAGE_FILE_TYPE_PNG)
                                || temp.endsWith(Constants.IMAGE_FILE_TYPE_JPEG)
                                || temp.endsWith(Constants.IMAGE_FILE_TYPE_BMP)) {
                            this.avatarPath = filePath;
                            Log.e("MyTag","set avatarPath:"+filePath);
                            Glide.with(this).load(this.avatarPath).placeholder(R.mipmap.ic_launcher).centerCrop().into(iv_avatar);
                        } else {
                            Toast.makeText(this, "文件格式不支持", Toast.LENGTH_SHORT).show();
                        }
                    }

                    break;
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BusData data) {

    }

    private void init() {
        contactsDao = new ContactsDao(this);
        Glide.with(this).load(this.avatarPath).placeholder(R.mipmap.ic_launcher).into(iv_avatar);
    }

    private void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, Constants.REQUEST_OPEN_ALBUM);
        } else {
            Toast.makeText(this, "打开相册失败", Toast.LENGTH_SHORT).show();
        }
    }

    public static String getFilePathFromContentUri(Uri uri, Context context) {
        String filePath;
        String[] filePathColumn = {MediaStore.MediaColumns.DATA};
        Cursor cursor = context.getContentResolver().query(uri, filePathColumn, null, null, null);
        if (cursor == null) {
            filePath = uri.getPath();
        } else {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            filePath = cursor.getString(columnIndex);
            cursor.close();
        }
        return filePath;
    }

    private void addContact() {
        Contacts contacts = new Contacts();
        contacts.setAvatar(this.avatarPath);
        contacts.setName(et_name.getText().toString());
        contacts.setPhone(et_phone.getText().toString());
        contactsDao.insertContact(contacts);
        EventBus.getDefault().postSticky(new BusData("", EveType.CONTACT_FRESH));
    }

}
