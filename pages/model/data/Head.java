package model.data;

import java.util.HashSet;

public class Head {
    private String title;
    private HashSet<String> docIDs;
    private Document myDoc; // デフォルトではnull

    public Head(String title) {
        this.title = title;
        this.docIDs = new HashSet<String>();
        this.myDoc = null;
    }

    public String getTitle() {
        return title;
    }

    public HashSet<String> getDocIDs() {
        return docIDs;
    }

    public Document getMyDoc() {
        return myDoc;
    }
}
