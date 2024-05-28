package model;

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

    // 以下のメソッドは、Documentのプロパティを操作しながら、自分自身を返すようにしている
    // これは、メソッドチェーンを可能にするためです
    // https://magazine.techacademy.jp/magazine/31905
    public Document like() {
        this.like++;
        return this;
    }

    public Document unlike() {
        this.like--;
        return this;
    }

    public Document setContent(String content) {
        this.content = content;
        return this;
    }

    public Document setHead(String head) {
        this.head = head;
        return this;
    }
}
