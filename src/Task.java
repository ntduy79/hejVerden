import java.util.Objects;

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
