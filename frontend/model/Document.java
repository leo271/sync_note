package model;

import java.util.UUID;
import model.db_enum.DB;

public class Document {
    private static final char US = '\u001f';

    public String docID;
    public String head;
    public String content;
    public int like;

    public Document(String head) {
        this.docID = UUID.randomUUID().toString();
        this.content = "";
        this.head = head;
        this.like = 0;
    }

    public Document(String docID, String head, String content, int like) {
        this.docID = docID;
        this.content = content;
        this.head = head;
        this.like = like;
    }

    public static Document fromJSON(JSON json) {
        var doc = new Document(json.get(DB.DOC_ID), json.get(DB.HEAD), json.get(DB.CONTENT),
                Integer.parseInt(json.get(DB.LIKE)));
        return doc;
    }

    public static Document fromString(String str) {
        var split = str.split(US + "");
        return new Document(split[0], split[1], split[2], Integer.parseInt(split[3]));
    }

    public JSON toJSON(boolean isMine) {
        var json = new JSON();
        json.put(DB.DOC_ID, docID);
        json.put(DB.HEAD, head);
        json.put(DB.CONTENT, content);
        json.put(DB.LIKE, Integer.toString(like));
        json.put(DB.TYPE_ML, isMine ? "M" : "L");
        return json;
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Document) {
            var doc = (Document) obj;
            return this.docID.equals(doc.docID);
        }
        return false;
    }
}
