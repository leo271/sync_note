package model.data;

import java.util.HashSet;

public class Head {
    public String name;
    public HashSet<String> docIDs;
    public Document myDoc; // デフォルトではnull

    public Head(String title) {
        this.name = title;
        this.docIDs = new HashSet<String>();
        this.myDoc = null;
    }
}
