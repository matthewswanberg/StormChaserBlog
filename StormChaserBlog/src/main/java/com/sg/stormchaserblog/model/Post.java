/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.stormchaserblog.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author ShellyAnneIsaacs
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class Post {

    private int id;
    private LocalDateTime date;
    private String title;
    private String text;
    private boolean isPublished;
    private boolean isApproved;
    private boolean isStatic;
    private List<Author> authors;
    private List<Tag> tags;
    private List<Category> categories;
    private Photo photo;
    
    public void setDate(LocalDateTime date) {
        String ldt = date.format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        date = LocalDateTime.parse(ldt, ISO_LOCAL_DATE_TIME);
        this.date = date;
    }

    public String getStatus() {
        if(this.isPublished) return "Published";
        if(this.isApproved) return "Awaiting Publication";
        else return "Awaiting Approval";
    }
    
    
    
}
