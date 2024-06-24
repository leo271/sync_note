package model;

import java.util.UUID;
import model.db_enum.DB;

public class Document {
    private static final char US = '\u001f';

    public String docID;
    public String content;
    public String head;
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
        var doc = new Document(json.get(DB.DOC_ID.name), json.get(DB.HEAD.name),
                json.get(DB.CONTENT.name), Integer.parseInt(json.get(DB.LIKE.name)));
        return doc;
    }

    public static Document fromString(String str) {
        var split = str.split(US + "");
        return new Document(split[0], split[1], split[2], Integer.parseInt(split[3]));
    }

    public JSON toJSON() {
        var json = new JSON();
        json.put(DB.DOC_ID.name, docID);
        json.put(DB.HEAD.name, head);
        json.put(DB.CONTENT.name, content);
        json.put(DB.LIKE.name, Integer.toString(like));
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
}
