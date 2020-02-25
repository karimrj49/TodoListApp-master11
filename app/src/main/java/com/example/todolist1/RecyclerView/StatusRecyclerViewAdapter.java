package com.example.todolist1.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist1.Entities.Status;
import com.example.todolist1.R;

import java.util.ArrayList;
import java.util.List;

public class StatusRecyclerViewAdapter extends RecyclerView.Adapter<StatusRecyclerViewAdapter.ViewHolder> {

    private static List<Status> status = new ArrayList<>();
    private Context context;
    private final LayoutInflater layoutInflater;
    private OnStatusListener onStatusListener;
    int row_index=-1;
    public StatusRecyclerViewAdapter(Context context , OnStatusListener onStatusListener) {
        this.context = context;
        layoutInflater =LayoutInflater.from(context);
        this.onStatusListener = onStatusListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = layoutInflater.inflate(R.layout.status_row,parent,false);
        return new ViewHolder(row,onStatusListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

         holder.textView.setText(status.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return status.size();
    }

    public void setList(List<Status>status){
        this.status = status;
        notifyDataSetChanged();
    }

    public String getStatusTitle(int position){
        return status.get(position).getTitle();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textView;
        OnStatusListener onStatusListener;

        public ViewHolder(@NonNull View itemView , OnStatusListener onStatusListener) {
            super(itemView);
            textView = itemView.findViewById(R.id.statustitle);
            this.onStatusListener = onStatusListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onStatusListener.onStatusClick(this.getLayoutPosition());
        }

    }

    public interface OnStatusListener{
        void onStatusClick(int position);
    }
}
