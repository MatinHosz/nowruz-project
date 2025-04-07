package todo.service;

import db.Database;
import db.Entity;
import db.exception.InvalidEntityException;
import todo.entity.Step;

import javax.xml.crypto.Data;

public class StepService {
    public static void saveStep(int taskRef, String title) throws InvalidEntityException {
        Step step = new Step(title, taskRef);
        step.status = Step.Status.NotStarted;
        Database.add(step);
    }
    public static void setAsNotStarted(int stepId) throws InvalidEntityException {
        Step step = (Step) Database.get(stepId);
        step.status = Step.Status.NotStarted;
        Database.update(step);
    }
    public static void setAsCompleted(int stepId) throws InvalidEntityException {
        Step step = (Step) Database.get(stepId);
        step.status = Step.Status.Completed;
        Database.update(step);
    }
}
