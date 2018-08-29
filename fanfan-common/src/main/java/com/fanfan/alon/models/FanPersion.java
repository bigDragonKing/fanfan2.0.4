package com.fanfan.alon.models;

import java.util.Date;

public class FanPersion {
    private Long id;
    private String pName;
    private String pAuthor;
    private Date createDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpAuthor() {
        return pAuthor;
    }

    public void setpAuthor(String pAuthor) {
        this.pAuthor = pAuthor;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public FanPersion() {
        super();
    }

    public FanPersion(Long id, String pName, String pAuthor) {
        this.id = id;
        this.pName = pName;
        this.pAuthor = pAuthor;
    }
}
