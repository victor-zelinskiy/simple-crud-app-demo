package ru.javarush.hikarius.testproject.crud.model;

import javax.validation.constraints.Pattern;

public class PageRestParameters {
    @Pattern(regexp = "^asc$|^desc$")
    private String sortType;
    @Pattern(regexp = "^name$|^age$|^isAdmin$|^createdDate$")
    private String sortField;

    private String nameFilter;
    private Integer ageMoreFilter;
    private Integer ageLessFilter;
    private Boolean isAdminFilter;

    public PageRestParameters() {
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }


    public String getNameFilter() {
        return nameFilter;
    }

    public void setNameFilter(String nameFilter) {
        this.nameFilter = nameFilter;
    }

    public Integer getAgeMoreFilter() {
        return ageMoreFilter;
    }

    public void setAgeMoreFilter(Integer ageMoreFilter) {
        this.ageMoreFilter = ageMoreFilter;
    }

    public Integer getAgeLessFilter() {
        return ageLessFilter;
    }

    public void setAgeLessFilter(Integer ageLessFilter) {
        this.ageLessFilter = ageLessFilter;
    }

    public Boolean getIsAdminFilter() {
        return isAdminFilter;
    }

    public void setIsAdminFilter(Boolean isAdminFilter) {
        this.isAdminFilter = isAdminFilter;
    }
}
