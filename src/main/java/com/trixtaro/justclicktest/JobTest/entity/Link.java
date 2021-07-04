package com.trixtaro.justclicktest.JobTest.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "link")
public class Link {

    @Id
    @Column(name = "id_link", nullable = false, unique = true)
    private Integer id;

    @Column
    private String value;

    @Column
    private String url;

    @Column(name = "remaining_calls")
    private int remainingCalls;

    public Link() {
    }

    public Link(Integer id) {
        this.id = id;
    }

    public Link(String value, String url, int remainingCalls) {
        this.value = value;
        this.url = url;
        this.remainingCalls = remainingCalls;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getRemainingCalls() {
        return remainingCalls;
    }

    public void setRemainingCalls(int remainingCalls) {
        this.remainingCalls = remainingCalls;
    }
}
