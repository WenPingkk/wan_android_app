package com.sean.module.main.bean.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Author WenPing
 * CreateTime 2019/9/14.
 * Description:
 */
@Entity(nameInDb = "SearchHistory")
public class SearchHistory {
    @Id(autoincrement = true)
    private Long id;
    private String keyword;
    private Long time;

    public SearchHistory(String keyword, Long time) {
        this.keyword = keyword;
        this.time = time;
    }

    @Generated(hash = 238310654)
    public SearchHistory(Long id, String keyword, Long time) {
        this.id = id;
        this.keyword = keyword;
        this.time = time;
    }

    @Generated(hash = 1905904755)
    public SearchHistory() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "SearchHistory{" +
                "id=" + id +
                ", keyword='" + keyword + '\'' +
                ", time=" + time +
                '}';
    }
}
