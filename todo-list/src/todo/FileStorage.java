package todo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileStorage {
    private final Path filePath;

    public FileStorage(String fileName) {
        this.filePath=Paths.get(fileName);
    }

    public void save(List<Task> tasks) throws IOException {
        List<String> lines=new ArrayList<>();

        for (Task task:tasks) {
            String line=task.getId()+"|"+task.getTitle()+"|"+task.getStatus();
            lines.add(line);
        }
        Files.write(filePath,lines,StandardCharsets.UTF_8);
    }

    public List<Task> load() throws IOException {
        List<Task> tasks=new ArrayList<>();
        if (!Files.exists(filePath)) {
            return tasks;
        }
        List<String> lines=Files.readAllLines(
                filePath,
                StandardCharsets.UTF_8
        );
        for (String line:lines) {
            if (line.trim().isEmpty()) {
                continue;
            }
            String[] parts=line.split("\\|", 3);
            if (parts.length!=3) {
                continue;
            }
            int id=Integer.parseInt(parts[0]);
            String title=parts[1];
            TaskStatus status=TaskStatus.valueOf(parts[2]);
            tasks.add(new Task(id,title,status));
        }

        return tasks;
    }
}