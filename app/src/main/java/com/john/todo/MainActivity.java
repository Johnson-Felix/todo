package com.john.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupMenu;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    TabLayout tabView;
    ViewPager viewPager;
    String UID;
    DbHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() == null) {
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            finish();
        }
        else {

            UID = auth.getCurrentUser().getUid();
            db = new DbHandler(UID);

            setContentView(R.layout.activity_main);

            tabView = (TabLayout) findViewById(R.id.tab_view);
            viewPager = (ViewPager) findViewById(R.id.view_pager);

            initFragments(viewPager);
            tabView.setupWithViewPager(viewPager);
        }

    }

    private void initFragments(ViewPager viewPager) {
        PageViewerAdapter viewerAdapter = new PageViewerAdapter(getSupportFragmentManager());

        ArrayList<TabModel> tabs = new ArrayList<>();

        tabs.add(new TabModel("All",new AllFragment()));
        tabs.add(new TabModel("Remaining",new CompletedFragment()));
        tabs.add(new TabModel("Completed",new RemaningFragment()));

        for(int i=0; i<tabs.size(); i++){
            Bundle bundle = new Bundle();
            bundle.putString("UID", UID);
            tabs.get(i).fragment.setArguments(bundle);
            viewerAdapter.addFragment(tabs.get(i));
        }

        viewPager.setAdapter(viewerAdapter);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.more_button:
                showMore(v);
                break;
            case R.id.fab:
                addTask();
                break;
        }
    }

    private void showMore(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        // This activity implements OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.log_out:
                        signOut();
                        break;
                }
                return false;
            }
        });
        popup.inflate(R.menu.dot_menu);
        popup.show();
    }

    private void signOut() {
        auth.signOut();
        goToLoginPage();

    }

    private void goToLoginPage() {
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
        finish();
    }

    private void addTask() {
        Log.i("fab","main activity");
        //db.addList("tasks",new Date());
        showAddTaskDialog();
    }

    private void showAddTaskDialog() {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.add_task_dialog);

        EditText task = (EditText)dialog.findViewById(R.id.task_view);
        EditText dateView = (EditText)dialog.findViewById(R.id.task_date);
        Button addTask = (Button)dialog.findViewById(R.id.add_task_button);

        Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                dateView.setText(dayOfMonth+"/"+monthOfYear+"/"+year);
            }

        };

        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(MainActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(task.getText().toString().trim() != null && dateView.getText().toString().trim() != null) {
                    db.addList(task.getText().toString().trim(),new Date(dateView.getText().toString().trim()));
                    dialog.hide();
                }
            }
        });

        dialog.show();
    }


}