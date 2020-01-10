package com.example.chutingyan.event_notebook.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.chutingyan.event_notebook.DataBase.DatabaseHandler;
import com.example.chutingyan.event_notebook.Model.Event;
import com.example.chutingyan.event_notebook.R;


import java.util.List;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ViewHolder> {

    private Context context;
    private List<Event> eventList;
    private AlertDialog.Builder alertBuilder;
    private AlertDialog alertDialog;
    private LayoutInflater inflater;

    public EventListAdapter(Context context, List<Event> eventList) {
        this.context = context;
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public EventListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.manager_list_row_recyclerview, viewGroup, false);

        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull EventListAdapter.ViewHolder viewHolder, int i) {

        Event event = eventList.get(i);
        viewHolder.time.setText(event.getTime());
        viewHolder.description.setText(event.getDescription());

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView time;
        public TextView description;
        public Button edit;
        public Button delete;
        public int id;

        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);

            context = ctx;

            time = itemView.findViewById(R.id.time);
            description = itemView.findViewById(R.id.description);

            edit = itemView.findViewById(R.id.editedButton);
            delete = itemView.findViewById(R.id.deleteButton);

            edit.setOnClickListener(this);
            delete.setOnClickListener(this);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    }
            });
            }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.editedButton:
                    int position1 = getAdapterPosition();
                    Event event1 = eventList.get(position1);


                    editItem(event1);

                break;

                case R.id.deleteButton:
                    int position = getAdapterPosition();
                    Event event = eventList.get(position);
                    deleteItem(event.getId());

                break; }
            }

        public void deleteItem(final int id){

            alertBuilder = new AlertDialog.Builder(context);

            inflater = LayoutInflater.from(context);

            View view = inflater.inflate(R.layout.confirmation_dialog, null);

            Button noButton = view.findViewById(R.id.noButton);
            Button yesButton = view.findViewById(R.id.yesButton);

            alertBuilder.setView(view);
            alertDialog = alertBuilder.create();
            alertDialog.show();

            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });

            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseHandler db = new DatabaseHandler(context);
                    db.DeleteEvents(id);
                    eventList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());

                    alertDialog.dismiss();
                }
            });

        }

        public void editItem(final Event event) {

            alertBuilder = new AlertDialog.Builder(context);

            inflater = LayoutInflater.from(context);
            final View view = inflater.inflate(R.layout.event_create_alertdialog_3, null);

            final EditText time = view.findViewById(R.id.time);
            final EditText description = view.findViewById(R.id.description);

            Button saveButton = view.findViewById(R.id.save_button);


            alertBuilder.setView(view);
            alertDialog = alertBuilder.create();
            alertDialog.show();

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DatabaseHandler db = new DatabaseHandler(context);

                    //Update item
                    event.setTime(time.getText().toString());
                    event.setDescription(description.getText().toString());

                    if (!time.getText().toString().isEmpty()
                            && !description.getText().toString().isEmpty()) {
                        db.UpdateEvents(event);
                        notifyItemChanged(getAdapterPosition(),event);
                    }else {
                        Snackbar.make(view, "Add Grocery and Quantity", Snackbar.LENGTH_LONG).show();
                    }

                    alertDialog.dismiss();

                }
            });

        }
    }
}
