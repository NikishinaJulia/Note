package ru.gb.note.data;

import java.io.Serializable;

public class Note implements Serializable {

    private Integer id;
    private String title;
    private String description;
    private String importance = "Не важно";

    public Note(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Note(String title, String description,String importance) {
        this.title = title;
        this.description = description;
        this.importance = importance;
    }

    public Note(Integer id, String title, String description, String importance) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.importance = importance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }
}
