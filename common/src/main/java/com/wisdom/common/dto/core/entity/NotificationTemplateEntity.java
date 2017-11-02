package com.wisdom.common.dto.core.entity;


import com.wisdom.common.dto.basic.DataEntity;

/**
 * Created by zbm84 on 2017/8/16.
 */
public class NotificationTemplateEntity extends DataEntity {

    private String id;
    private String title;
    private String content;
    private String uri;
    private String create_by;
    private String create_date;
    private String update_date;
    private String del_flage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getCreate_by() {
        return create_by;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(String update_date) {
        this.update_date = update_date;
    }

    public String getDel_flage() {
        return del_flage;
    }

    public void setDel_flage(String del_flage) {
        this.del_flage = del_flage;
    }
}
