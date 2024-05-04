package model.data;

import java.util.UUID;

public class Document {
    private String docID;
    private String content;
    private String head;
    private int like;

    public Document(String head, String content) {
        this.docID = UUID.randomUUID().toString();
        this.content = content;
        this.head = head;
        this.like = 0;
    }

    public String getDocID() {
        return docID;
    }

    public String getContent() {
        return content;
    }

    public String getHead() {
        return head;
    }

    public int getLike() {
        return like;
    }
}
