package com.example.todoappfinal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList;
    private DatabaseHelper dbHelper;
    private boolean isHistoryView;

    public TaskAdapter(List<Task> taskList, DatabaseHelper dbHelper, boolean isHistoryView) {
        this.taskList = taskList;
        this.dbHelper = dbHelper;
        this.isHistoryView = isHistoryView;
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

        // Hide buttons if it's in history view
        if (isHistoryView) {
            holder.btnComplete.setVisibility(View.GONE);
            holder.btnDelete.setVisibility(View.GONE);
        } else {
            holder.btnComplete.setVisibility(View.VISIBLE);
            holder.btnDelete.setVisibility(View.VISIBLE);

            holder.btnComplete.setOnClickListener(v -> {
                dbHelper.markTaskAsCompleted(task.getId());
                taskList.remove(position);
                notifyItemRemoved(position);
            });

            holder.btnDelete.setOnClickListener(v -> {
                dbHelper.deleteTask(task.getId());
                taskList.remove(position);
                notifyItemRemoved(position);
            });
        }
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView tvTaskName, tvTaskDeadline;
        Button btnComplete, btnDelete;

        TaskViewHolder(View itemView) {
            super(itemView);
            tvTaskName = itemView.findViewById(R.id.tv_task_name);
            tvTaskDeadline = itemView.findViewById(R.id.tv_task_deadline);
            btnComplete = itemView.findViewById(R.id.btn_complete);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
