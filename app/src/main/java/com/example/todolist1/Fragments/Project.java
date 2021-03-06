package com.example.todolist1.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist1.Activities.MainActivity;
import com.example.todolist1.R;
import com.example.todolist1.RecyclerView.FragmentRecyclerViewAdapter;
import com.example.todolist1.ViewModel.ProjectViewModel;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Project extends Fragment implements FragmentRecyclerViewAdapter.OnFragmentListner {

    private ProjectViewModel projectViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.state_fragment,container,false);
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.getSupportActionBar().setTitle("Projects");
        getActivity().findViewById(R.id.fab).setVisibility(View.INVISIBLE);
        RecyclerView recyclerView = root.findViewById(R.id.recyclerchoice);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        final FragmentRecyclerViewAdapter fragmentRecyclerViewAdapter = new FragmentRecyclerViewAdapter(getContext(),this);
        recyclerView.setAdapter(fragmentRecyclerViewAdapter);

        projectViewModel = ViewModelProviders.of(this).get(ProjectViewModel.class);
        projectViewModel.getAllProjecttitle().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> list) {
                // Update RecyclerView
                fragmentRecyclerViewAdapter.setList(list);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0 , ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int p = viewHolder.getAdapterPosition();

                String t = fragmentRecyclerViewAdapter.getStatustitle(viewHolder.getAdapterPosition());
                com.example.todolist1.Entities.Project P = null;
                try {
                    P = projectViewModel.getProjectById(viewHolder.getAdapterPosition()+1);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                projectViewModel.delete(P);
                fragmentRecyclerViewAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                Toast.makeText(getContext(),"The task "+t+" is done",Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(recyclerView);
        return root;
    }

    @Override
    public void onFragmentClick(int position) {
        int rpos=position+1;
        Toast.makeText(getContext(),"selected"+rpos,Toast.LENGTH_SHORT).show();
        String title = null;
        try {
            com.example.todolist1.Entities.Project P = projectViewModel.getProjectById(rpos);
            title = P.getTitlet();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Bundle bundle = new Bundle();
        bundle.putInt("Position",rpos);
        bundle.putString("Title",title);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        ActionWithProject actionWithProject = new ActionWithProject();
        actionWithProject.setArguments(bundle);

        fragmentTransaction.replace(R.id.fragment_container,actionWithProject,"tag");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
