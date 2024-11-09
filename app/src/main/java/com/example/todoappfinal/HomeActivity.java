package com.example.todoappfinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton fabAddTask;
    DatabaseHelper dbHelper;
    TaskAdapter adapter;
    List<Task> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Set up the Toolbar as the ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);  // This makes the toolbar act as the action bar

        // Initialize RecyclerView and other UI components
        recyclerView = findViewById(R.id.recyclerView);
        fabAddTask = findViewById(R.id.fab_add_task);

        dbHelper = new DatabaseHelper(this);
        taskList = dbHelper.getIncompleteTasks();

        // Pass 'false' for isHistoryView because this is not the history view
        adapter = new TaskAdapter(taskList, dbHelper, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        fabAddTask.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, AddTaskActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        taskList.clear();
        taskList.addAll(dbHelper.getIncompleteTasks());
        adapter.notifyDataSetChanged();
    }

    // Inflate the menu from the res/menu folder
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu); // Inflate the menu from menu_home.xml
        return true;
    }

    // Handle item selection for the menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_history) {
            // Open HistoryActivity when the "History" menu item is clicked
            Intent intent = new Intent(HomeActivity.this, HistoryActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
