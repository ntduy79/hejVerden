import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Task {
    private int id;
    private String title;
    private String status;
    private String description;

    // Constructor
    public Task(int id, String title, String status, String description) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.description = description;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // toString Method
    @Override
    public String toString() {
        return "[" + id + "] " + title + " (" + status + ") - " + description;
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

class TaskManager {
    private static final String TASKS_FILE = "tasks.json";
    private final Gson gson = new Gson();
    private List<Task> tasks;

    public TaskManager() {
        tasks = loadTasks();
    }

    private List<Task> loadTasks() {
        try (Reader reader = new FileReader(TASKS_FILE)) {
            Type listType = new TypeToken<List<Task>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void saveTasks() {
        try (Writer writer = new FileWriter(TASKS_FILE)) {
            gson.toJson(tasks, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addTask(String title, String description) {
        int id = tasks.stream().mapToInt(Task::getId).max().orElse(0) + 1;
        tasks.add(new Task(id, title, "todo", description));
        saveTasks();
    }

    public void updateTask(int id, String title, String description) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                if (title != null) task.setTitle(title);
                if (description != null) task.setDescription(description);
                saveTasks();
                System.out.println("Task updated: " + id);
                return;
            }
        }
        System.out.println("Task not found: " + id);
    }
}

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        // Example Usage
        taskManager.addTask("Write Code", "Finish the coding assignment.");
        taskManager.updateTask(1, "Write Code - Updated", null);

        // Display tasks
        taskManager.tasks.forEach(System.out::println);
    }
}
