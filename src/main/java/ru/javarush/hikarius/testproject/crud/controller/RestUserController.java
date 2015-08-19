package ru.javarush.hikarius.testproject.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.javarush.hikarius.testproject.crud.dao.UserDao;
import ru.javarush.hikarius.testproject.crud.model.Page;
import ru.javarush.hikarius.testproject.crud.model.PageRestParameters;
import ru.javarush.hikarius.testproject.crud.model.User;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/crud")
public class RestUserController {

    @Autowired
    private UserDao userDao;


    @RequestMapping(value = "/getPage/{pageSize}/{pageNumber}", method = RequestMethod.GET)
    public
    @ResponseBody
    Page getPage(@PathVariable("pageNumber") Integer pageNumber, @PathVariable("pageSize") Integer pageSize, @Valid @ModelAttribute PageRestParameters pageRestParameters) {
        return userDao.getPage(pageNumber, pageSize, pageRestParameters.getSortType(), pageRestParameters.getSortField(), pageRestParameters.getNameFilter(), pageRestParameters.getAgeMoreFilter(), pageRestParameters.getAgeLessFilter(), pageRestParameters.getIsAdminFilter());
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable("id") Integer id) {
        userDao.delete(id);
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public void updateItem(@RequestBody @Valid User updatedUser) {
        userDao.saveOrUpdate(updatedUser);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public
    @ResponseBody
    List<?> getAllUsers() {
        return userDao.getAll();
    }

    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    User getUserById(@PathVariable("id") Integer id) {
        return userDao.getById(id);
    }
}