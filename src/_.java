import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    public void deleteTask(String input) {
        try {
            if ("all".equalsIgnoreCase(input)) {
                tasks.clear();
                System.out.println("All tasks deleted.");
            } else {
                int id = Integer.parseInt(input);
                tasks.removeIf(task -> task.getId() == id);
                System.out.println("Task deleted: " + id);
            }
            saveTasks();
        } catch (NumberFormatException e) {
            System.err.println("Invalid input. Please enter a valid task ID or 'all'.");
        } catch (Exception e) {
            System.err.println("An error occurred while deleting tasks.");
            e.printStackTrace();
        }
    }

    public void changeStatus(int id, String status) {
        if (!status.equals("todo") && !status.equals("in-progress") && !status.equals("done")) {
            System.out.println("Invalid status " + status);
            return;
        }
        for (Task task : tasks) {
            if (task.getId() == id) {
                task.setStatus(status);
                saveTasks();
                System.out.println(("Task status updated: " + id));
                return;
            }
        }
        System.out.print("Task not found: " + id);
    }

    public void listTasks(String status) {
        tasks.stream()
                .filter(task -> status == null || task.getStatus().equals(status))
                .forEach((System.out::println));
    }
}
