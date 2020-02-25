package com.example.todolist1.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist1.Converter;
import com.example.todolist1.Entities.Action;
import com.example.todolist1.Entities.Project;
import com.example.todolist1.Entities.Status;
import com.example.todolist1.Entities.Tag;
import com.example.todolist1.Fragments.Inbox;
import com.example.todolist1.Fragments.State;
import com.example.todolist1.Fragments.TagF;
import com.example.todolist1.R;
import com.example.todolist1.ViewModel.ActionViewModel;
import com.example.todolist1.ViewModel.ProjectViewModel;
import com.example.todolist1.ViewModel.StatusViewModel;
import com.example.todolist1.ViewModel.TagViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final int REQUEST = 1;
    private ActionViewModel actionViewModel;
    private StatusViewModel statusViewModel;
    private ProjectViewModel projectViewModel;
    private TagViewModel tagViewModel;
    private DrawerLayout drawerLayout;
    private FloatingActionButton floatbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        floatbutton = (FloatingActionButton) findViewById(R.id.fab);
        floatbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (MainActivity.this, AddActionActivity.class);
                startActivityForResult(intent,REQUEST);
            }
        });
        drawerLayout = findViewById(R.id.drawerlayout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Inbox()).commit();
            getSupportActionBar().setTitle("Inbox");
            navigationView.setCheckedItem(R.id.inbox);
        }

        actionViewModel = ViewModelProviders.of(this).get(ActionViewModel.class);
        statusViewModel = ViewModelProviders.of(this).get(StatusViewModel.class);
        projectViewModel = ViewModelProviders.of(this).get(ProjectViewModel.class);
        tagViewModel = ViewModelProviders.of(this).get(TagViewModel.class);
        navigationView.getMenu().getItem(3).setActionView(R.layout.add_status_nav);
        View viewS = navigationView.getMenu().getItem(3).getActionView();
        viewS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View mview = getLayoutInflater().inflate(R.layout.addstatusdialog,null);
                EditText sedit = mview.findViewById(R.id.edit_status);
                Button Ssave = mview.findViewById(R.id.savestatus);
                builder.setView(mview);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                Ssave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String s = sedit.getText().toString();
                        if(s.trim().isEmpty()){
                            Toast.makeText(getApplicationContext(),"You have to write the title of the new status",Toast.LENGTH_SHORT).show();
                        }else {
                            Status S = new Status(s);
                            statusViewModel.inserts(S);
                            alertDialog.dismiss();
                        }
                    }
                });
            }
        });

        navigationView.getMenu().getItem(4).setActionView(R.layout.add_status_nav);
        View viewP = navigationView.getMenu().getItem(4).getActionView();
        viewP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View mview = getLayoutInflater().inflate(R.layout.addstatusdialog,null);
                Button Psave = mview.findViewById(R.id.savestatus);
                TextView textView = mview.findViewById(R.id.addtitle);
                textView.setText("Add Tag");
                EditText pedit = mview.findViewById(R.id.edit_status);
                builder.setView(mview);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                Psave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String p = pedit.getText().toString();
                        if(p.trim().isEmpty()){
                            Toast.makeText(getApplicationContext(),"You have to write the title of the new project",Toast.LENGTH_SHORT).show();
                        }else {
                            Project P = new Project(p);
                            projectViewModel.insert(P);
                            alertDialog.dismiss();
                        }
                    }
                });
            }
        });

        navigationView.getMenu().getItem(5).setActionView(R.layout.add_status_nav);
        View viewT = navigationView.getMenu().getItem(5).getActionView();
        viewT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View mview = getLayoutInflater().inflate(R.layout.addstatusdialog,null);
                Button Tsave = mview.findViewById(R.id.savestatus);
                TextView textView = mview.findViewById(R.id.addtitle);
                textView.setText("Add new Tag");
                EditText tedit = mview.findViewById(R.id.edit_status);
                builder.setView(mview);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                Tsave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String p = tedit.getText().toString();
                        if(p.trim().isEmpty()){
                            Toast.makeText(getApplicationContext(),"You have to write the title of the new project",Toast.LENGTH_SHORT).show();
                        }else {
                            Tag T = new Tag(p);
                            tagViewModel.insert(T);
                            alertDialog.dismiss();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST && resultCode == RESULT_OK) {

            String title = data.getStringExtra("Insert title");
            String desc = data.getStringExtra("Insert description");
            int idstatus = data.getIntExtra("IDS", -1);
            int idproject = data.getIntExtra("IDP", -1);
            int idtag = data.getIntExtra("IDT", -1);
            String Creation = data.getStringExtra("date");
            Date dateObj = new Date(data.getStringExtra("date"));
            SimpleDateFormat format1=new SimpleDateFormat("dd/MM/yyyy");
          
           // Toast.makeText(this, " la date est : " + Creation, Toast.LENGTH_SHORT).show();

            Date date = new Date();
            Action action = new Action(title, desc, idstatus, idproject, idtag, date,dateObj);


            actionViewModel.insert(action);
            String datee = action.getTododate().toString();
            Toast.makeText(this,"the date is ? "+datee,Toast.LENGTH_SHORT).show();

            //Toast.makeText(this,"A task has been added to your agenda",Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.inbox :
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Inbox()).commit();
                break;
            case R.id.state :
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new State()).commit();
                getSupportActionBar().setTitle("State");
                break;
            case R.id.project :
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new com.example.todolist1.Fragments.Project()).commit();
                getSupportActionBar().setTitle("Projects");
                break;
            case R.id.tag :
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new TagF()).commit();
                getSupportActionBar().setTitle("Tags");
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
