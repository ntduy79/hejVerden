import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;


class TaskManager {
    private static final String TASKS_FILE = "tasks.json";
    private final Gson gson = new Gson();
    public List<Task> tasks;

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
