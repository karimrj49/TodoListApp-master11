package com.example.todolist1.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist1.Entities.Status;
import com.example.todolist1.R;

import java.util.ArrayList;
import java.util.List;


public class FragmentRecyclerViewAdapter extends RecyclerView.Adapter<FragmentRecyclerViewAdapter.ViewHolder> {

    private List<String> arrayList = new ArrayList<>();
    Context context ;
    private final LayoutInflater layoutInflater;
    private OnFragmentListner onFragmentListner;
    public FragmentRecyclerViewAdapter(Context context,OnFragmentListner onFragmentListner) {
        this.context = context;
        this.onFragmentListner = onFragmentListner;
        layoutInflater =LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = layoutInflater.inflate(R.layout.fragment_singel_row,parent,false);
        return new ViewHolder(row,onFragmentListner);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public String getStatustitle(int posistion){
        return arrayList.get(posistion);
    }
    public void setList(List<String> list){
        this.arrayList = list;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textView ;
        OnFragmentListner onFragmentListner;
        public ViewHolder(@NonNull View itemView,OnFragmentListner fragmentListner) {
            super(itemView);
            textView = itemView.findViewById(R.id.title);
            this.onFragmentListner = fragmentListner;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
           onFragmentListner.onFragmentClick(this.getLayoutPosition());
        }
    }

    public interface OnFragmentListner{
        void onFragmentClick(int position);
    }
}
