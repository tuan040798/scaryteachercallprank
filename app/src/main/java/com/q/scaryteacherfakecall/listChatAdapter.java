package com.q.scaryteacherfakecall;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class listChatAdapter extends BaseAdapter {

    //Dữ liệu liên kết bởi Adapter là một mảng các sản phẩm
    final ArrayList<class_chat> listChat;
    private TextView userName,textTitle;
    public listChatAdapter(ArrayList<class_chat> listChat) {
        this.listChat = listChat;
    }

    @Override
    public int getCount() {
        //Trả về tổng số phần tử, nó được gọi bởi ListView
        return listChat.size();
    }

    @Override
    public Object getItem(int position) {
        //Trả về dữ liệu ở vị trí position của Adapter, tương ứng là phần tử
        //có chỉ số position trong listProduct
        return listChat.get(position);
    }

    @Override
    public long getItemId(int position) {
        //Trả về một ID của phần
        return listChat.size();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //convertView là View của phần tử ListView, nếu convertView != null nghĩa là
        //View này được sử dụng lại, chỉ việc cập nhật nội dung mới
        //Nếu null cần tạo mới

        View viewProduct;
        if (convertView == null) {
            viewProduct = View.inflate(parent.getContext(), R.layout.itemlistchat, null);
        } else viewProduct = convertView;

        userName = viewProduct.findViewById(R.id.userName);
        textTitle = viewProduct.findViewById(R.id.textTitle);
        //Bind sữ liệu phần tử vào View
        if(position % 2 == 0){
            userName.setTextColor(Color.parseColor("#02a823"));
        }
        else {

            userName.setTextColor(Color.parseColor("#fc0303"));
        }

        userName.setText(listChat.get(position).getName());
        textTitle.setText(listChat.get(position).getTitle());


        return viewProduct;
    }
}