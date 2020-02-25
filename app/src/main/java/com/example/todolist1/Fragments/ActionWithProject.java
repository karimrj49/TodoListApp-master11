package com.example.todolist1.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist1.Activities.AddActionActivity;
import com.example.todolist1.Activities.MainActivity;
import com.example.todolist1.Entities.Action;
import com.example.todolist1.R;
import com.example.todolist1.RecyclerView.RecyclerViewAdapter;
import com.example.todolist1.ViewModel.ActionViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class ActionWithProject extends Fragment {

    private ActionViewModel actionViewModel;
    private FloatingActionButton floatingActionButton;
    final int NO_REQUEST = 2;
    int var = -1,vars;
    String vart,vard;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.inbox_fragment,container,false);
        getActivity().findViewById(R.id.fab).setVisibility(View.VISIBLE);
        floatingActionButton = getActivity().findViewById(R.id.fab);
        Bundle bundle = getArguments();
        int p = bundle.getInt("Position");
        String title = bundle.getString("Title");
        MainActivity mainActivity = (MainActivity)getActivity();
        mainActivity.getSupportActionBar().setTitle(title);
        RecyclerView recyclerView = root.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        final RecyclerViewAdapter adapter = new RecyclerViewAdapter(getContext());
        recyclerView.setAdapter(adapter);
        actionViewModel = ViewModelProviders.of(this).get(ActionViewModel.class);

        actionViewModel.getAllActionsP(p).observe(this, new Observer<List<Action>>() {
            @Override
            public void onChanged(List<Action> actions) {
                adapter.setList(actions);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0 , ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                actionViewModel.delete(adapter.getAction(viewHolder.getAdapterPosition()));
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public synchronized void onItemClick(Action action) {
                Intent intent = new Intent(getActivity(), AddActionActivity.class);
                intent.putExtra("ID",action.getId());
                var=action.getId();
                intent.putExtra("title",action.getTitle());
                vart = action.getTitle();
                intent.putExtra("description",action.getDescription());
                vard = action.getDescription();
                intent.putExtra("IDID",action.getIdstatus());
                vars = action.getIdstatus();
                intent.putExtra("IDIDP",action.getIdproject());
                intent.putExtra("IDIDT",action.getIdtag());
                //Toast.makeText(getContext(),""+vart+vard+vars,Toast.LENGTH_SHORT).show();
                startActivityForResult(intent,NO_REQUEST);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View mview = getLayoutInflater().inflate(R.layout.add_action_alertdialoge,null);
                Button save = mview.findViewById(R.id.save);
                TextView textView = mview.findViewById(R.id.add);
                textView.setText("Add new Action");
                EditText tedit = mview.findViewById(R.id.alertTitle);
                EditText dedit = mview.findViewById(R.id.alertDesc);
                builder.setView(mview);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String t = tedit.getText().toString();
                        String d = dedit.getText().toString();
                        if(t.trim().isEmpty() && d.trim().isEmpty()){
                            Toast.makeText(getContext(),"You have to write the title and the description of the new action",Toast.LENGTH_SHORT).show();
                        }else {
                            Action action = new Action(t,d,0,p,0);
                            actionViewModel.insert(action);
                            alertDialog.dismiss();
                        }
                    }
                });
            }
        });
        return root ;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NO_REQUEST && resultCode == RESULT_OK) {

            if (var == -1) {
                Toast.makeText(getContext(), "Note can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra("Insert title");
            String desc = data.getStringExtra("Insert description");
            int idstatus = data.getIntExtra("IDS",-1);
            int idproject = data.getIntExtra("IDP",-1);
            int idtag = data.getIntExtra("IDT",-1);
            if(vart==title && vard==desc && vars==idstatus){
                Toast.makeText(getContext(),"There is nothing to update",Toast.LENGTH_SHORT).show();
                return;
            }
            Action action = new Action(title,desc,idstatus,idproject,idtag);

            action.setId(var);
            actionViewModel.update(action);

            Toast.makeText(getContext() , "Note updated", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(getContext(),"The update is canceled",Toast.LENGTH_SHORT).show();
        }
    }

}

