package ru.javarush.hikarius.testproject.crud.dao;

import ru.javarush.hikarius.testproject.crud.model.Page;
import ru.javarush.hikarius.testproject.crud.model.User;

import java.util.List;

public interface UserDao {
    List<?> getAll();
    User getById(int id);
    void saveOrUpdate(User user);
    void delete(int id);
    Page getPage(int pageNumber, int pageSize, String sortType, String sortField, String nameFilter, Integer ageMoreFilter, Integer ageLessFilter, Boolean isAdminFilter);
}