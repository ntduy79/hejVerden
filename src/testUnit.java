import com.google.gson.JsonObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class testUnit {
    private static final String TEST_FILE = "test_tasks.json";
    private TaskManager taskManager;

    @BeforeEach
    void setup() {
        taskManager = new TaskManager() {
            private String getTasksFilePath() {
                return TEST_FILE;
            }
        };
        try (Writer writer = new FileWriter(TEST_FILE)) {
            writer.write("[]");
        } catch (IOException e) {
            fail("can't test brother");
        }
    }

    @AfterEachvoid
    void teardown() {
        File file = new File(TEST_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testAddTask() {
        taskManager.addTask("Test track", "Test description");
        List<Task> tasks = taskManager.getTask();

        assertEquals(1, tasks.size());
        assertEquals("Test track", tasks.get(0).getTitle());
        assertEquals("todo", tasks.get(0).getStatus());
    }

    private void assertEqual(String todo, String status) {
    }

    private void assertEqual(int i, int size) {
    }
}