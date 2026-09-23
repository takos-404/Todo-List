package todo;

import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private final List<Task> tasks;
    private int nextId;

    public TaskManager() {
        tasks=new ArrayList<>();
        nextId=1;
    }

    public void addTask(String title) {
        Task task=new Task(nextId, title, TaskStatus.NEW);
        tasks.add(task);
        nextId++;
    }

    public List<Task> getAllTasks() {
        return tasks;
    }

    public boolean changeStatus(int id, TaskStatus newStatus) {
        Task task=findById(id);
        if (task==null) {
            return false;
        }
        task.setStatus(newStatus);
        return true;
    }

    public boolean deleteTask(int id) {
        Task task=findById(id);
        if (task==null) {
            return false;
        }
        tasks.remove(task);
        return true;
    }

    public Task findById(int id) {
        for (Task task:tasks) {
            if (task.getId()==id) {
                return task;
            }
        }
        return null;
    }

    public void loadTasks(List<Task> loadedTasks) {
        tasks.clear();
        tasks.addAll(loadedTasks);

        for (Task task:tasks) {
            if (task.getId()>=nextId) {
                nextId=task.getId() + 1;
            }
        }
    }
}