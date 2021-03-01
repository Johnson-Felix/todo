package com.john.todo;

import java.util.Date;

public class ListModel {
    String task;
    boolean isCompleted;
    Date date;
    Date deadLineDate;

    public ListModel() {

    }

    public ListModel(String task,Date deadLineDate) {
        this.task = task;
        this.deadLineDate = deadLineDate;
        isCompleted = false;
        date = new Date();
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDeadLineDate() {
        return deadLineDate;
    }

    public void setDeadLineDate(Date deadLineDate) {
        this.deadLineDate = deadLineDate;
    }
}
