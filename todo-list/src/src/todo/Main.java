package todo;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner=new Scanner(System.in);
    private static final TaskManager taskManager=new TaskManager();
    private static final FileStorage fileStorage=new FileStorage("tasks.txt");

    public static void main(String[] args){
        loadTasks();
        boolean running=true;
        while (running) {
            printMenu();
            int choice=readInt("Choose: ");
            switch (choice) {
                case 1:
                    addTask();
                    break;
                case 2:
                    showTasks();
                    break;
                case 3:
                    changeStatus();
                    break;
                case 4:
                    deleteTask();
                    break;
                case 5:
                    saveTasks();
                    break;
                case 6:
                    saveTasks();
                    running=false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
        scanner.close();
    }
    private static void printMenu(){
        System.out.println();
        System.out.println("____TODO LIST____");
        System.out.println("1. Add task");
        System.out.println("2. Show all tasks");
        System.out.println("3. Change task status");
        System.out.println("4. Delete task");
        System.out.println("5. Save tasks");
        System.out.println("6. Exit");
        System.out.println();
    }

    private static void addTask(){
        System.out.print("Enter task title: ");
        String title=scanner.nextLine();

        if (title.trim().isEmpty()){
            System.out.println("Title cannot be empty.");
            return;
        }

        taskManager.addTask(title);
        System.out.println("Task added.");
    }

    private static void showTasks(){
        List<Task> tasks=taskManager.getAllTasks();

        if (tasks.isEmpty()){
            System.out.println("No tasks.");
            return;
        }

        System.out.println();
        for (Task task:tasks){
            System.out.println(task);
        }
    }

    private static void changeStatus(){
        int id=readInt("Enter task ID: ");
        Task task=taskManager.findById(id);
        if (task==null){
            System.out.println("Task not found.");
            return;
        }

        System.out.println("Current status: "+task.getStatus());
        System.out.println("1. NEW");
        System.out.println("2. IN_PROGRESS");
        System.out.println("3. DONE");

        int choice=readInt("Choose new status: ");

        TaskStatus newStatus;

        switch (choice){
            case 1:
                newStatus=TaskStatus.NEW;
                break;
            case 2:
                newStatus=TaskStatus.IN_PROGRESS;
                break;
            case 3:
                newStatus=TaskStatus.DONE;
                break;
            default:
                System.out.println("Invalid status.");
                return;
        }
        taskManager.changeStatus(id,newStatus);
        System.out.println("Status changed.");
    }

    private static void deleteTask(){
        int id=readInt("Enter task ID: ");
        boolean deleted=taskManager.deleteTask(id);
        if (deleted){
            System.out.println("Task deleted.");
        } else{
            System.out.println("Task not found.");
        }
    }

    private static void saveTasks(){
        try{
            fileStorage.save(taskManager.getAllTasks());
            System.out.println("Tasks saved.");
        } catch (IOException e){
            System.out.println("Error while saving tasks: "+e.getMessage());
        }
    }

    private static void loadTasks(){
        try{
            List<Task> tasks=fileStorage.load();
            taskManager.loadTasks(tasks);
            System.out.println(tasks.size()+" task(s) loaded.");
        }catch (IOException | IllegalArgumentException e) {
            System.out.println("Error while loading tasks: "+e.getMessage());
        }
    }

    private static int readInt(String message){
        while (true){
            System.out.print(message);
            try{
                return Integer.parseInt(scanner.nextLine());
            }catch (NumberFormatException e) {
                System.out.println("Enter a valid number.");
            }
        }
    }
}