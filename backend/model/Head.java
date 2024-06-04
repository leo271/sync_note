package backend.model;

import java.util.ArrayList;
import java.util.List;

public class Head {
    public String name;
    public ArrayList<String> docIDs;

    public Head(String title) {
        this.name = title;
        this.docIDs = new ArrayList<String>();
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
}
