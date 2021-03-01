package com.john.todo;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DbHandler {

    static ArrayList<ListModel> lists = new ArrayList();

    FirebaseDatabase database;
    DatabaseReference taskRef;

    public DbHandler(String UID) {
        database = FirebaseDatabase.getInstance();
        taskRef = database.getReference(UID+"/tasks");

        taskRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lists.clear();

                for(DataSnapshot dss: dataSnapshot.getChildren()) {
                    lists.add(dss.getValue(ListModel.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }

    public void addList(String task,Date date) {
        ListModel l = new ListModel(task,date);
        taskRef.child(l.date.getTime()+"").setValue(l);
    }

}

