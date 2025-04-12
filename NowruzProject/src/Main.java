import db.Database;
import db.Entity;
import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;
import todo.entity.Task;
import todo.entity.Step;
import todo.service.TaskService;
import todo.service.StepService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String command;

        System.out.println("Welcome to the Task Management System!");
        System.out.println("Enter your commands (type 'exit' to quit):");

        while (true) {
            System.out.print("> ");
            command = scanner.nextLine().trim();

            if (command.equalsIgnoreCase("exit")) {
                System.out.println("Exiting the program. Goodbye!");
                break;
            }

            try {
                switch (command.toLowerCase()) {
                    case "add task":
                        handleAddTask(scanner);
                        break;
                    case "add step":
                        handleAddStep(scanner);
                        break;
                    case "delete":
                        handleDelete(scanner);
                        break;
                    case "update task":
                        handleUpdateTask(scanner);
                        break;
                    case "update step":
                        handleUpdateStep(scanner);
                        break;
                    case "get task-by-id":
                        handleGetTaskById(scanner);
                        break;
                    case "get all-tasks":
                        handleGetAllTasks();
                        break;
                    case "get incomplete-tasks":
                        handleGetIncompleteTasks();
                        break;
                    default:
                        System.out.println("Invalid command. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred.");
                System.out.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
    }

    private static void handleAddTask(Scanner scanner) {
        try {
            System.out.print("Title: ");
            String title = scanner.nextLine();

            System.out.print("Description: ");
            String description = scanner.nextLine();

            System.out.print("Due date (yyyy-MM-dd): ");
            String dueDate = scanner.nextLine();

            TaskService.saveTask(title, description, dueDate);
            Task task = (Task) Database.get(Database.currentEntityID - 1); // Get the last added task
            System.out.println("Task saved successfully.");
            System.out.println("ID: " + task.id);
        } catch (InvalidEntityException e) {
            System.out.println("Cannot save task.");
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void handleAddStep(Scanner scanner) {
        try {
            System.out.print("TaskID: ");
            int taskId = Integer.parseInt(scanner.nextLine());

            System.out.print("Title: ");
            String title = scanner.nextLine();

            StepService.saveStep(taskId, title);
            Step step = (Step) Database.get(Database.currentEntityID - 1); // Get the last added step
            System.out.println("Step saved successfully.");
            System.out.println("ID: " + step.id);
        } catch (InvalidEntityException | EntityNotFoundException e) {
            System.out.println("Cannot save step.");
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void handleDelete(Scanner scanner) {
        System.out.print("ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        try {
            ArrayList<Step> taskStepsList = Database.getTasksSteps(id);

            Database.delete(id);
            for (Step taskStep : taskStepsList)
                Database.delete(taskStep.id);
            System.out.println("Entity with ID=" + id + " successfully deleted.");
        } catch (EntityNotFoundException e) {
            System.out.println("Cannot delete entity with ID=" + id + ".");
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void handleUpdateTask(Scanner scanner) {
        try {
            System.out.print("ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            System.out.print("Field: ");
            String field = scanner.nextLine();

            System.out.print("New Value: ");
            String newValue = scanner.nextLine();

            updateTask(field, id, newValue);
        } catch (InvalidEntityException | EntityNotFoundException e) {
            System.out.println("Cannot update task.");
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void handleUpdateStep(Scanner scanner) {
        try {
            System.out.print("ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            System.out.print("Field: ");
            String field = scanner.nextLine();

            System.out.print("New Value: ");
            String newValue = scanner.nextLine();

            updateStep(field, id, newValue);
        } catch (InvalidEntityException | EntityNotFoundException e) {
            System.out.println("Cannot update step.");
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void handleGetTaskById(Scanner scanner) {
        System.out.print("ID: ");
        int taskID = Integer.parseInt(scanner.nextLine());

        try {
            Task task = TaskService.getTaskById(taskID);

            SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd");
            String taskDueDate = formatter.format(task.dueDate);
            Task.Status taskStatus = task.status;

            System.out.println("Title: " + task.title);
            System.out.println("Due Date: " + taskDueDate);
            switch (taskStatus) {
                case Task.Status.NOT_STARTED -> System.out.println("Status: NotStarted");
                case Task.Status.IN_PROGRESS -> System.out.println("Status: InProgress");
                case Task.Status.COMPLETED -> System.out.println("Status: Completed");
            }

            ArrayList<Step> tasksStepsList = Database.getTasksSteps(taskID);

            for (Step step : tasksStepsList) {
                System.out.println("\t+ " + step.title + ":");
                System.out.println("\t\tID: " + step.id);
                System.out.print("\t\tstatus: ");
                switch (taskStatus) {
                    case Task.Status.NOT_STARTED -> System.out.println("Status: NotStarted");
                    case Task.Status.IN_PROGRESS -> System.out.println("Status: InProgress");
                    case Task.Status.COMPLETED -> System.out.println("Status: Completed");
                }
            }
        } catch (EntityNotFoundException e) {
            System.out.println("Cannot find task with ID=" + taskID + ".");
        }
    }

    private static void handleGetAllTasks() {
        ArrayList<Entity> entityList = Database.getAll(Task.TASK_ENTITY_CODE);

        if (entityList.isEmpty() || entityList == null) {
            System.out.println("No tasks available at the moment. Start by adding a new task!");
            return;
        }
        // Cast Entity to Task
        // and sort by due date
        ArrayList<Task> taskList = new ArrayList<>();
        for (Entity entity : entityList)
            taskList.add((Task) entity);

        taskList.sort(Comparator.comparing(Task -> Task.dueDate));

        for (Task task : taskList) {
            SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd");
            String taskDueDate = formatter.format(task.dueDate);
            Task.Status taskStatus = task.status;

            System.out.println("ID: " + task.id);
            System.out.println("Title: " + task.title);
            System.out.println("Due Date: " + taskDueDate);
            switch (taskStatus) {
                case Task.Status.NOT_STARTED -> System.out.println("Status: NotStarted");
                case Task.Status.IN_PROGRESS -> System.out.println("Status: InProgress");
                case Task.Status.COMPLETED -> System.out.println("Status: Completed");
            }

            ArrayList<Step> tasksStepsList = Database.getTasksSteps(task.id);
            for (Step step : tasksStepsList) {
                System.out.println("Steps:");
                Step.Status stepStatus = step.status;
                System.out.println("\t+ " + step.title + ":");
                System.out.println("\t\tID: " + step.id);
                System.out.print("\t\tstatus: ");
                switch (stepStatus) {
                    case Step.Status.NOT_STARTED -> System.out.println("Status: NotStarted");
                    case Step.Status.COMPLETED -> System.out.println("Status: Completed");
                }
            }
            System.out.println();
        }
    }

    private static void handleGetIncompleteTasks() {
        ArrayList<Task> incompleteTasksList = TaskService.getIncompleteTasks();

        if (incompleteTasksList.isEmpty()) {
            System.out.println("No incomplete tasks available at the moment.");
            return;
        }

        for (Task task : incompleteTasksList) {
            System.out.println("ID: " + task.id);
            System.out.println("Title: " + task.title);
            System.out.println("Due Date: " + task.dueDate);
            Task.Status taskStatus = task.status;
            switch (taskStatus) {
                case Task.Status.NOT_STARTED -> System.out.println("Status: NotStarted");
                case Task.Status.IN_PROGRESS -> System.out.println("Status: InProgress");
                case Task.Status.COMPLETED -> System.out.println("Status: Completed");
            }

            ArrayList<Step> tasksStepsList = Database.getTasksSteps(task.id);
            for (Step step : tasksStepsList) {
                System.out.println("Steps:");
                Step.Status stepStatus = step.status;
                System.out.println("\t+ " + step.title + ":");
                System.out.println("\t\tID: " + step.id);
                System.out.print("\t\tstatus: ");
                switch (stepStatus) {
                    case Step.Status.NOT_STARTED -> System.out.println("Status: NotStarted");
                    case Step.Status.COMPLETED -> System.out.println("Status: Completed");
                }
            }
            System.out.println();
        }
    }

    private static void updateStep(String field, int stepID, String newValue) throws InvalidEntityException {
        if (field == null)
            throw new IllegalArgumentException("Field cannot be null");

        switch (field.trim().toLowerCase()) {
            case "title":
                StepService.updateTitle(stepID, newValue);
                break;
            case "status":
                updateStepStatus(stepID, newValue);
                break;
            default:
                throw new IllegalArgumentException("Invalid field name: " + field);
        }
    }

    private static void updateStepStatus(int stepID, String newStatus) throws InvalidEntityException {
        if (newStatus == null)
            throw new IllegalArgumentException("Status cannot be null");

        switch (newStatus.trim().toLowerCase()) {
            case "Not started":
                StepService.setAsNotStarted(stepID);
                break;
            case "Completed":
                StepService.setAsCompleted(stepID);
                break;
            default:
                throw new IllegalArgumentException("invalid status: " + newStatus);
        }

    }

    private static void updateTask(String field, int taskID, String newValue) throws InvalidEntityException {
        if (field == null)
            throw new IllegalArgumentException("Field cannot be null");

        switch (field.trim().toLowerCase()) {
            case "title":
                TaskService.updateTitle(taskID, newValue);
                break;
            case "description":
                TaskService.updateDescription(taskID, newValue);
                break;
            case "due date":
                TaskService.updateDueDate(taskID, newValue);
                break;
            case "status":
                updateTaskStatus(taskID, newValue);
                break;
            default:
                throw new IllegalArgumentException("Invalid field name: " + field);
        }
    }

    private static void updateTaskStatus(int taskID, String newStatus) throws InvalidEntityException {
        if (newStatus == null)
            throw new IllegalArgumentException("Status cannot be null");
  
        switch (newStatus.trim().toLowerCase()) {
            case "Not Started":
                TaskService.setAsNotStarted(taskID);
                break;
            case "In Progress":
                TaskService.setAsInProgress(taskID);
                break;
            case "Completed":
                TaskService.setAsCompleted(taskID);
                break;
            default:
                throw new IllegalArgumentException("invalid status: " + newStatus);
        }
    }
}