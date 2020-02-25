package com.example.todolist1.RecyclerView;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist1.Entities.Action;
import com.example.todolist1.Entities.Project;
import com.example.todolist1.Entities.Status;
import com.example.todolist1.R;
import com.example.todolist1.ViewModel.ProjectViewModel;
import com.example.todolist1.ViewModel.StatusViewModel;

import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private OnItemClickListener listener;
    public static final String DATE_FORMAT_2 = "dd-MMM-yyyy";

    private List<Action> actions = new ArrayList<>();
    //private List<Status>statuses = new ArrayList<>();
    private Context context;
    private StatusViewModel statusViewModel;
    private ProjectViewModel projectViewModel;
    private final LayoutInflater layoutInflater;

    public RecyclerViewAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View row = layoutInflater.inflate(R.layout.singel_row, parent, false);
        return new ViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.actiontitle.setText(actions.get(position).getTitle());
        holder.actiondescription.setText(actions.get(position).getDescription());
        int t = actions.get(position).getIdstatus();
        int t1 = actions.get(position).getIdproject();
        Status s = null;
        String ss = null;
        Project p = null;
        String sp = null;
        try {
            s = statusViewModel.getStatusById(t);
            ss = s.getTitle();
            p = projectViewModel.getProjectById(t1);
            sp = p.getTitlet();
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.statustitle.setText(ss);
        holder.project.setText(sp);

        String S = getCurrentDate().toString();

        holder.TextView.setText(S);

    }

    @Override
    public int getItemCount() {
        return actions.size();
    }

    public void setList(List<Action> actions) {
        this.actions = actions;
        notifyDataSetChanged();
    }

    public Action getAction(int position) {
        return actions.get(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView actiontitle, actiondescription, statustitle,project,TextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            statustitle = itemView.findViewById(R.id.sid);
            actiontitle = itemView.findViewById(R.id.actiontitle);
            actiondescription = itemView.findViewById(R.id.actiondescription);
            project = itemView.findViewById(R.id.project);
            TextView = itemView.findViewById(R.id.date);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(actions.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Action action);

    }

    public void setOnItemClickListener(OnItemClickListener listener) { this.listener=listener;}

    public static String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_2);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date today = Calendar.getInstance().getTime();
        return dateFormat.format(today);
    }
}
