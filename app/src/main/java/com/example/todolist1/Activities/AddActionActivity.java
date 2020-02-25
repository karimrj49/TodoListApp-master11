package com.example.todolist1.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todolist1.Entities.Action;
import com.example.todolist1.Entities.Project;
import com.example.todolist1.Entities.Status;
import com.example.todolist1.Entities.Tag;
import com.example.todolist1.Fragments.DatePickerFragement;
import com.example.todolist1.R;
import com.example.todolist1.RecyclerView.StatusRecyclerViewAdapter;
import com.example.todolist1.ViewModel.ProjectViewModel;
import com.example.todolist1.ViewModel.StatusViewModel;
import com.example.todolist1.ViewModel.TagViewModel;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AddActionActivity extends AppCompatActivity implements StatusRecyclerViewAdapter.OnStatusListener, DatePickerDialog.OnDateSetListener {

    private EditText edititle , editdesc , project_title , tag_title;
    private String date;
    private StatusViewModel statusViewModel;
    private ProjectViewModel projectViewModel;
    private TagViewModel tagViewModel;
    private RecyclerView recyclerView;
    private StatusRecyclerViewAdapter adapter;
    private RecyclerView.ViewHolder viewHolder;
    private int rpos=0;
    private int sn=0;
    private int p=0;
    private int t = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_action);
        //AddActionActivity.this.setTitle(R.string.add_task);
        getSupportActionBar().setTitle(R.string.add_task);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        adapter = new StatusRecyclerViewAdapter(this,this);
        recyclerView.setAdapter(adapter);
        statusViewModel = ViewModelProviders.of(this).get(StatusViewModel.class);

        statusViewModel.getAllStatus().observe(this, new Observer<List<Status>>() {
            @Override
            public void onChanged(List<Status> status) {
                // Update RecyclerView
                adapter.setList(status);
            }
        });
        edititle = (EditText) findViewById(R.id.action_title);
        editdesc = (EditText) findViewById(R.id.action_description);
        project_title = (EditText) findViewById(R.id.project);
        tag_title = (EditText) findViewById(R.id.tag);
        Intent intent = getIntent();
        if (intent.hasExtra("ID")) {
            getSupportActionBar().setTitle("Update Task");
            edititle.setText(intent.getStringExtra("title"));
            editdesc.setText(intent.getStringExtra("description"));
            int idp = intent.getIntExtra("IDIDP",-1);
            int idt = intent.getIntExtra("IDIDT",-1);
            Project idP = null;
            Tag idT = null;
            try {
                idP = projectViewModel.getProjectById(idp);
                idT = tagViewModel.getTagById(idt);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            sn = intent.getIntExtra("IDID",-1);
            rpos=intent.getIntExtra("IDID",-1);

            String s=null;
            Status S = null;
            try {
                S = statusViewModel.getStatusById(sn);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

//            tag_title.setText(idT.getTitle());
//            project_title.setText(idP.getTitlet());
//            Toast.makeText(getApplicationContext(),"The selected state previously is "+s,Toast.LENGTH_SHORT).show();
            if(S==null && idP==null && idT==null){
                return;
            }
            else if(idP==null && idT==null){
                s = S.getTitle();
                Toast.makeText(getApplicationContext(),"The selected state previously is "+s,Toast.LENGTH_SHORT).show();
                return;
                //Toast.makeText(getApplicationContext(),"rpos and sn "+sn+rpos,Toast.LENGTH_SHORT).show();
            } else if(idP==null && S==null){
                tag_title.setText(idT.getTitle());
                return;
            }else if(idT==null && S==null){
                project_title.setText(idP.getTitlet());
                return;
            }else if(idP==null){
                s = S.getTitle();
                Toast.makeText(getApplicationContext(),"The selected state previously is "+s,Toast.LENGTH_SHORT).show();
                tag_title.setText(idT.getTitle());
                return;
            }else if(idT==null){
                s = S.getTitle();
                Toast.makeText(getApplicationContext(),"The selected state previously is "+s,Toast.LENGTH_SHORT).show();
                project_title.setText(idP.getTitlet());
                return;
            }else if(S==null){
                tag_title.setText(idT.getTitle());
                project_title.setText(idP.getTitlet());
                return;
            }else if(S!=null && idP!=null && idT!=null){
                s = S.getTitle();
                Toast.makeText(getApplicationContext(),"The selected state previously is "+s,Toast.LENGTH_SHORT).show();
                project_title.setText(idP.getTitlet());
                tag_title.setText(idT.getTitle());
                return;
            }


        } else {
            getSupportActionBar().setTitle(R.string.add_task);
        }
        Button button = (Button) findViewById(R.id.Cal);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragement();
                datePicker.show(getSupportFragmentManager(),"date picker ");
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addaction,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.add :
                try {
                    add();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void add () throws ExecutionException, InterruptedException {

        edititle = (EditText) findViewById(R.id.action_title);
        editdesc = (EditText) findViewById(R.id.action_description);
        project_title = (EditText) findViewById(R.id.project);
        tag_title = (EditText) findViewById(R.id.tag);
        String title = edititle.getText().toString();
        String desc = editdesc.getText().toString();
        String pt = project_title.getText().toString();
        String tt = tag_title.getText().toString();
        p = projectViewModel.getIdByTitle(pt);
        t = tagViewModel.getIdByTitle(tt);
        Intent data = new Intent();
        if(title.trim().isEmpty() || desc.trim().isEmpty()){
            setResult(RESULT_CANCELED,data);
            Toast.makeText(getApplicationContext(),"Please insert a title and description",Toast.LENGTH_SHORT).show();
        }
        else {
            data.putExtra("Insert title", title);
            data.putExtra("Insert description", desc);
            data.putExtra("IDP",p);
            data.putExtra("IDT",t);
            data.putExtra("date",date);
            if(rpos==sn){
                data.putExtra("IDS",sn);
            }
            else {
                data.putExtra("IDS",rpos);
            }
            setResult(RESULT_OK, data);
            finish();
        }
    }
    @Override
    public void onStatusClick(int position) {
        //viewHolder.itemView.setBackgroundColor(000);
        String title = adapter.getStatusTitle(position);
        rpos = position+1;
        Toast.makeText(this,"You have selected the status"+title+" ",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        }
        else {
            super.onBackPressed();
        }

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        String currentDates = DateFormat.getDateInstance().format(c.getTime());
        date = currentDates;
        Toast.makeText(this,""+date,Toast.LENGTH_SHORT).show();

    }
}
