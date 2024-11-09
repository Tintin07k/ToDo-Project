package com.example.todoappfinal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class EditTaskActivity extends AppCompatActivity {

    private EditText editTaskName, editTaskDescription, editTaskDeadline;
    private Button btnSave;
    private DatabaseHelper dbHelper;
    private int taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        // Initialize views
        editTaskName = findViewById(R.id.editTaskName);
        editTaskDescription = findViewById(R.id.editTaskDescription);
        editTaskDeadline = findViewById(R.id.editTaskDeadline);
        btnSave = findViewById(R.id.btnSave);

        dbHelper = new DatabaseHelper(this);

        // Retrieve task ID passed from TaskAdapter
        taskId = getIntent().getIntExtra("TASK_ID", -1);

        if (taskId != -1) {
            // Load task details if task ID is valid
            Task task = dbHelper.getTaskById(taskId);
            if (task != null) {
                editTaskName.setText(task.getName());
                editTaskDescription.setText(task.getDescription());
                editTaskDeadline.setText(task.getDeadline());
            }
        }

        // Save task changes when the button is clicked
        btnSave.setOnClickListener(view -> {
            String updatedTaskName = editTaskName.getText().toString();
            String updatedTaskDescription = editTaskDescription.getText().toString();
            String updatedTaskDeadline = editTaskDeadline.getText().toString();

            // Update the task in the database
            boolean isUpdated = dbHelper.updateTask(taskId, updatedTaskName, updatedTaskDescription, updatedTaskDeadline);

            if (isUpdated) {
                Toast.makeText(EditTaskActivity.this, "Task updated", Toast.LENGTH_SHORT).show();
                finish(); // Close the activity and go back to the previous one
            } else {
                Toast.makeText(EditTaskActivity.this, "Failed to update task", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
