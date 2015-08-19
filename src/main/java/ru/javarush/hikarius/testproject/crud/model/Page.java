package ru.javarush.hikarius.testproject.crud.model;

import java.util.List;

public class Page {
    private int totalUsersNumber;
    private List<?> data;

    public Page() {
    }

    public Page(int totalUsersNumber, List<?> data) {
        this.totalUsersNumber = totalUsersNumber;
        this.data = data;
    }

    public int getTotalUsersNumber() {
        return totalUsersNumber;
    }

    public void setTotalUsersNumber(int totalUsersNumber) {
        this.totalUsersNumber = totalUsersNumber;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
