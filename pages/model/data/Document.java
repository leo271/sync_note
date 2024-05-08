package model.data;

import java.util.UUID;

public class Document {
    public String docID;
    public String content;
    public String head;
    public int like;

    public Document(String head, String content) {
        this.docID = UUID.randomUUID().toString();
        this.content = content;
        this.head = head;
        this.like = 0;
    }
}
