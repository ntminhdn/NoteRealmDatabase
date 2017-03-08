package com.example.user.noterealmdatabase;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by User on 08/03/2017.
 */

public class MyBook extends RealmObject {

    private String title;
    private String id;

    public MyBook(String id, String title) {
        this.title = title;
        this.id = id;
    }

    public MyBook() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
