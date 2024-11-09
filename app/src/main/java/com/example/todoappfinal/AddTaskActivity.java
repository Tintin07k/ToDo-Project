package com.example.todoappfinal;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddTaskActivity extends AppCompatActivity {
    EditText etTaskName, etTaskDescription;
    Button btnSelectDeadline, btnSubmit;
    DatabaseHelper dbHelper;
    Calendar deadlineCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        // Initialize views
        etTaskName = findViewById(R.id.et_task_name);
        etTaskDescription = findViewById(R.id.et_task_description);
        btnSelectDeadline = findViewById(R.id.btn_select_deadline);
        btnSubmit = findViewById(R.id.btn_submit);

        dbHelper = new DatabaseHelper(this);

        // Set up deadline picker
        btnSelectDeadline.setOnClickListener(view -> showDateTimePicker());

        btnSubmit.setOnClickListener(view -> {
            String name = etTaskName.getText().toString();
            String description = etTaskDescription.getText().toString();
            String deadline = btnSelectDeadline.getText().toString();

            if (name.isEmpty() || deadline.equals("Select Deadline")) {
                Toast.makeText(this, "Name and Deadline are required", Toast.LENGTH_SHORT).show();
                return;
            }

            long result = dbHelper.addTask(name, description, deadline);
            if (result != -1) {
                Toast.makeText(this, "Task added successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to add task", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDateTimePicker() {
        // First, show the DatePickerDialog
        new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            deadlineCalendar.set(Calendar.YEAR, year);
            deadlineCalendar.set(Calendar.MONTH, month);
            deadlineCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            // After selecting the date, show the TimePickerDialog
            new TimePickerDialog(this, (timeView, hourOfDay, minute) -> {
                deadlineCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                deadlineCalendar.set(Calendar.MINUTE, minute);

                // Format the date and time, then display it in the Button's text
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                btnSelectDeadline.setText(dateFormat.format(deadlineCalendar.getTime()));
            }, deadlineCalendar.get(Calendar.HOUR_OF_DAY), deadlineCalendar.get(Calendar.MINUTE), false).show();
        }, deadlineCalendar.get(Calendar.YEAR), deadlineCalendar.get(Calendar.MONTH), deadlineCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}
