package com.example.todoappfinal;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    RecyclerView recyclerViewHistory;
    DatabaseHelper dbHelper;
    TaskAdapter adapter;
    List<Task> completedTaskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerViewHistory = findViewById(R.id.recyclerViewHistory);
        dbHelper = new DatabaseHelper(this);

        // Retrieve completed tasks from the database
        completedTaskList = dbHelper.getCompletedTasks();

        // Initialize adapter with isHistoryView set to true
        adapter = new TaskAdapter(completedTaskList, dbHelper, true);
        recyclerViewHistory.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewHistory.setAdapter(adapter);
    }
}
