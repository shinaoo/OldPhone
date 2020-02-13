package com.oldphone.adpts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.oldphone.R;
import com.oldphone.entity.Contacts;
import com.oldphone.evebus.BusData;
import com.oldphone.evebus.EveType;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactHolder> implements View.OnClickListener {

    private final LayoutInflater layoutInflater;
    private final Context context;
    private List<Contacts> datas;

    public ContactsAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.datas = new ArrayList<>();
    }

    public void setDatas(List<Contacts> datas) {
        this.datas = datas;
        this.notifyDataSetChanged();
    }

    @Override
    public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContactHolder(layoutInflater.inflate(R.layout.adpt_facelist, parent, false));
    }

    @Override
    public void onBindViewHolder(ContactHolder holder, int position) {
        Glide.with(context).load(datas.get(position).getAvatar()).placeholder(R.mipmap.ic_launcher).centerCrop().into(holder.iv_avatar);
        holder.tv_name.setText("名称:" + datas.get(position).getName());
        holder.tv_phone.setText("电话:" + datas.get(position).getPhone());
        holder.view.setOnClickListener(view ->{
            EventBus.getDefault().postSticky(new BusData(position, EveType.ACTION_CALL));
        });
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }


    class ContactHolder extends RecyclerView.ViewHolder {
        View view;
        ImageView iv_avatar;
        TextView tv_name;
        TextView tv_phone;

        public ContactHolder(View itemView) {
            super(itemView);
            view = itemView;
            tv_name = itemView.findViewById(R.id.tv_contacts_name);
            iv_avatar = itemView.findViewById(R.id.iv_contacts_face);
            tv_phone = itemView.findViewById(R.id.tv_contacts_phone);
        }
    }

    @Override
    public void onClick(View view) {

    }
}
