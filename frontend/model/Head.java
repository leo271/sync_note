package model;

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

    // 以下のメソッドは、Headのプロパティを操作しながら、自分自身を返すようにしている
    // これは、メソッドチェーンを可能にするためです
    // https://magazine.techacademy.jp/magazine/31905
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
