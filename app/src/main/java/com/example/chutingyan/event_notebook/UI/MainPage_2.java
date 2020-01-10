package com.example.chutingyan.event_notebook.UI;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.chutingyan.event_notebook.Adapter.EventListAdapter;
import com.example.chutingyan.event_notebook.DataBase.DatabaseHandler;
import com.example.chutingyan.event_notebook.Model.Event;
import com.example.chutingyan.event_notebook.R;


import java.util.ArrayList;
import java.util.List;

public class MainPage_2 extends AppCompatActivity {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog alertDialog;
    private EditText time;
    private EditText description;
    private Button saveButton;

    private DatabaseHandler db;

    private RecyclerView recyclerView;
    private EventListAdapter adapter;
    private List<Event> eventList;
    private List<Event> eventItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page_2);

        db = new DatabaseHandler(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                create_eventList();
            }
        });

        db = new DatabaseHandler(this);
        recyclerView = findViewById(R.id.managerEventListView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        eventList = new ArrayList<>();
        eventItems = new ArrayList<>();


        // get all events
        eventList = db.GetAllEvents();

        for (Event e : eventList){
            Event event = new Event();
            event.setId(e.getId());
            event.setTime(e.getTime());
            event.setDescription(e.getDescription());

            eventItems.add(event);
        }

        adapter = new EventListAdapter(this, eventItems);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private void create_eventList(){

        dialogBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.event_create_alertdialog_3, null);
        time = view.findViewById(R.id.time);
        description = view.findViewById(R.id.description);
        saveButton = view.findViewById(R.id.save_button);

        dialogBuilder.setView(view);
        alertDialog = dialogBuilder.create();
        alertDialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveEventToDatabase(view);
            }
        });


    }

    private void saveEventToDatabase(View v){

        Event event = new Event();

        String newTime = time.getText().toString();
        String newDescription = description.getText().toString();

        event.setTime(newTime);
        event.setDescription(newDescription);

        db.AddEvent(event);

        Snackbar.make(v, "event Saved!", Snackbar.LENGTH_LONG).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                alertDialog.dismiss();

                //start a new activity
                startActivity(new Intent(MainPage_2.this, MainPage_2.class));
            }
        }, 1000); //1 sec

    }

}