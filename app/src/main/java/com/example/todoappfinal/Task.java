package com.example.todoappfinal;

public class Task {
    private int id;
    private String name;
    private String description;
    private String deadline;
    private boolean isCompleted;

    // Constructor
    public Task(int id, String name, String description, String deadline, boolean isCompleted) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.isCompleted = isCompleted;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDeadline() { return deadline; }
    public void setDeadline(String deadline) { this.deadline = deadline; }

    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }
}
