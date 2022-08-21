package com.example.mobileshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobileshop.R;
import com.example.mobileshop.model.Order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ListOrderAdapter extends BaseAdapter {

    Context context;
    ArrayList<Order> listOrder;

    public ListOrderAdapter(Context context, ArrayList<Order> listOrder)
    {
        this.context = context;
        this.listOrder = listOrder;

    }

    @Override
    public int getCount() {
        return listOrder.size();
    }

    @Override
    public Object getItem(int position) {
        return listOrder.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder{
        public TextView txtOrderID, txtReceiverName, txtReceiverAddress, txtReceivedDate, txtStatus;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.order_listview, null);
            viewHolder.txtOrderID = (TextView) view.findViewById(R.id.txtViewOrderID);
            viewHolder.txtReceiverName = (TextView) view.findViewById(R.id.txtViewReceicerName);
            viewHolder.txtReceiverAddress = (TextView) view.findViewById(R.id.txtViewReceiverAddress);
            viewHolder.txtReceivedDate = (TextView) view.findViewById(R.id.txtViewReceiveDate);
            viewHolder.txtStatus = (TextView) view.findViewById(R.id.txtViewStatus);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder  =(ViewHolder) view.getTag();
        }
        // gán dữ liệu
        Order order = (Order) getItem(position);
        viewHolder.txtOrderID.setText(order.getOrderID()+"");
        viewHolder.txtReceiverAddress.setText(order.getAddress());
        viewHolder.txtReceiverName.setText(order.getReceiverName());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        viewHolder.txtReceivedDate.setText(formatter.format(order.getReceivedDate()));
        if(order.getStatus()==-1)
        {
            viewHolder.txtStatus.setText("Chờ Duyệt");
        }
        else if (order.getStatus()==0)
        {
            viewHolder.txtStatus.setText("Đang Giao");
        }
        else if (order.getStatus()== 1)
        {
            viewHolder.txtStatus.setText("Đã nhận");
        }

        return view;
    }
}
