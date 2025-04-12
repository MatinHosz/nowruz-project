package todo.service;

import java.util.ArrayList;

import db.Database;
import db.exception.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;

public class StepService {
    public static void saveStep(int taskRef, String title) throws InvalidEntityException {
        Step step = new Step(title, taskRef);
        step.status = Step.Status.NOT_STARTED;
        Database.add(step);
    }
    public static void setAsNotStarted(int stepId) throws InvalidEntityException {
        Step step = (Step) Database.get(stepId);
        step.status = Step.Status.NOT_STARTED;
        Database.update(step);
    }
    public static void setAsCompleted(int stepId) throws InvalidEntityException {
        Step step = (Step) Database.get(stepId);
        step.status = Step.Status.COMPLETED;

         Task taskRefOfStep = (Task) Database.get(step.taskRef);
         if (taskRefOfStep.status == Task.Status.NOT_STARTED)
             taskRefOfStep.status = Task.Status.IN_PROGRESS;

        ArrayList<Step> refTasksSteps = Database.getTasksSteps(step.taskRef);
        boolean allStepsCompleted = true;
        for (Step tasksStep: refTasksSteps) {
            if (tasksStep.status != Step.Status.COMPLETED) {
                allStepsCompleted = false;
                break;
            }
        }
        if (allStepsCompleted)
            taskRefOfStep.status = Task.Status.COMPLETED;
        
        Database.update(step);
    }
    public static void updateTitle(int id, String title) throws InvalidEntityException {
        Step step = (Step) Database.get(id);
        step.title = title;
        Database.update(step);
    }
}
