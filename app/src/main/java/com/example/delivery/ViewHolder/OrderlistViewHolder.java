package com.example.delivery.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.delivery.Interface.ItemClickListner;
import com.example.delivery.R;

public class OrderlistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView item1,item3,item4,item5,item6,item7,item8,item9,item10,address,phone,state;


    public ItemClickListner listner;


    public OrderlistViewHolder(View itemView)
    {
        super(itemView);
        item1= itemView.findViewById(R.id.viewitem1);
        item3=itemView.findViewById(R.id.viewitem2);
        item4=itemView.findViewById(R.id.viewitem3);
        item5=itemView.findViewById(R.id.viewitem4);
        item6=itemView.findViewById(R.id.viewitem5);
        item7=itemView.findViewById(R.id.viewitem6);
        item8=itemView.findViewById(R.id.viewitem7);
        item10=itemView.findViewById(R.id.viewitem9);
        item9=itemView.findViewById(R.id.viewitem8);
        address= itemView.findViewById(R.id.address);
        phone=itemView.findViewById(R.id.phone);
        state=itemView.findViewById(R.id.state);


    }

    public void setItemClickListner(ItemClickListner listner)
    {
        this.listner = listner;
    }

    @Override
    public void onClick(View view)
    {
        listner.onClick(view, getAdapterPosition(), false);
    }
}