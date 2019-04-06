package com.example.projectmeter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.MyViewHolder> {

    private Context mContext;
    private List<Bill> billList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView cost, date, is_paid;

        public MyViewHolder(View view) {
            super(view);
            cost = view.findViewById(R.id.cost);
            date = view.findViewById(R.id.date);
            is_paid = view.findViewById(R.id.paid);
        }
    }


    public BillAdapter(Context mContext, List<Bill> billList) {
        this.mContext = mContext;
        this.billList = billList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bill_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Bill bill = billList.get(position);
        holder.cost.setText(String.valueOf(bill.getCost()));
        holder.date.setText(bill.getTime());
        if(bill.getIs_paid())
            holder.is_paid.setText("Paid");
        else
            holder.is_paid.setText("Pending");
    }



    @Override
    public int getItemCount() {
        return billList.size();
    }
}
