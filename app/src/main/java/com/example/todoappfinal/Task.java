package com.example.todoappfinal;

public class Task {
    private int id;
    private String name;
    private String description;
    private String deadline;
    private boolean completed;

    public Task(int id, String name, String description, String deadline, boolean completed) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.completed = completed;
    }

    // Getters and Setters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getDeadline() { return deadline; }
    public boolean isCompleted() { return completed; }

    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setDeadline(String deadline) { this.deadline = deadline; }
    public void setCompleted(boolean completed) { this.completed = completed; }
}
