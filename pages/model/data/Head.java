package model.data;

import java.util.HashSet;
import java.util.List;

public class Head {
    public String name;
    public HashSet<String> docIDs;
    public Document myDoc; // デフォルトではnull

    public Head(String title) {
        this.name = title;
        this.docIDs = new HashSet<String>();
        this.myDoc = null;
    }

    public Head addDocIDs(String[] docID) {
        docIDs.addAll(List.of(docID));
        return this;
    }

    public Head addDocID(String docID) {
        docIDs.add(docID);
        return this;
    }

    public Head addMyDoc(Document doc) {
        this.myDoc = doc;
        this.docIDs.add(doc.docID);
        return this;
    }
}
