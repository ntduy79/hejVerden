import java.util.*;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Task Tracker CLI");
        while (true) {
            System.out.println("\nCommands: add, update, delete, status, list, exit");
            System.out.print("> ");
            String command = scanner.next().toLowerCase(Locale.ROOT);

            switch (command) {
                case "add" -> {
                    System.out.print("Title: ");
                    String title = scanner.next();
                    System.out.print("Description: ");
                    String description = scanner.next();
                    taskManager.addTask(title, description);
                }
                case "update" -> {
                    System.out.print("Task ID: ");
                    int id = scanner.nextInt();
                    System.out.print("New Title (or skip): ");
                    String title = scanner.next();
                    System.out.print("New Description (or skip): ");
                    String description = scanner.next();
                    taskManager.updateTask(id, title, description);
                }
                case "delete" -> {
                    System.out.print("Task ID: ");
                    scanner = new Scanner(System.in);
                    String s = scanner.next();
                    taskManager.deleteTask(s);
                }
                case "status" -> {
                    System.out.print("Task ID: ");
                    int id = scanner.nextInt();
                    System.out.print("New Status (todo, in-progress, done): ");
                    String status = scanner.next();
                    taskManager.changeStatus(id, status);
                }
                case "list" -> {
                    System.out.print("Status (or all): ");
                    String status = scanner.next();
                    taskManager.listTasks(status.equals("all") ? null : status);
                }
                case "exit" -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Unknown command");
            }
        }
    }
}