package com.example.todoappfinal;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList;
    private DatabaseHelper dbHelper;
    private boolean isHistoryView;
    private Context context;

    public TaskAdapter(List<Task> taskList, DatabaseHelper dbHelper, boolean isHistoryView, Context context) {
        this.taskList = taskList;
        this.dbHelper = dbHelper;
        this.isHistoryView = isHistoryView;
        this.context = context;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.tvTaskName.setText(task.getName());
        holder.tvTaskDeadline.setText(task.getDeadline());

        if (isHistoryView) {
            holder.btnComplete.setVisibility(View.GONE);
            holder.btnDelete.setVisibility(View.GONE);
            holder.btnEdit.setVisibility(View.GONE);
        } else {
            holder.btnComplete.setVisibility(View.VISIBLE);
            holder.btnDelete.setVisibility(View.VISIBLE);
            holder.btnEdit.setVisibility(View.VISIBLE);

            holder.btnComplete.setOnClickListener(v -> {
                dbHelper.markTaskAsCompleted(task.getId());
                taskList.remove(position);
                notifyItemRemoved(position);
                Snackbar.make(v, "Task marked as complete", Snackbar.LENGTH_SHORT).show();
            });

            holder.btnDelete.setOnClickListener(v -> {
                dbHelper.deleteTask(task.getId());
                taskList.remove(position);
                notifyItemRemoved(position);
                Snackbar.make(v, "Task deleted", Snackbar.LENGTH_SHORT).show();
            });

            holder.btnEdit.setOnClickListener(v -> {
                Intent intent = new Intent(context, EditTaskActivity.class);
                intent.putExtra("TASK_ID", task.getId());
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView tvTaskName, tvTaskDeadline;
        Button btnComplete, btnDelete, btnEdit;

        TaskViewHolder(View itemView) {
            super(itemView);
            tvTaskName = itemView.findViewById(R.id.tv_task_name);
            tvTaskDeadline = itemView.findViewById(R.id.tv_task_deadline);
            btnComplete = itemView.findViewById(R.id.btn_complete);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            btnEdit = itemView.findViewById(R.id.btn_edit);  // Ensure your XML layout has btn_edit
        }
    }
}
