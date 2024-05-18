package Model.Data;

import java.util.HashSet;
import java.util.List;

public class HeadGroup {
    public String name;
    public HashSet<Head> heads;
    public HashSet<String> headGroups;

    public HeadGroup(String name) {
        this.name = name;
        this.heads = new HashSet<Head>();
        this.headGroups = new HashSet<String>();
    }

    // 以下のメソッドは、HeadGroupのプロパティを操作しながら、自分自身を返すようにしている
    // これは、メソッドチェーンを可能にするためです
    // https://magazine.techacademy.jp/magazine/31905
    public HeadGroup addHeads(Head[] head) {
        heads.addAll(List.of(head));
        return this;
    }

    public HeadGroup addHead(Head head) {
        heads.add(head);
        return this;
    }

    public HeadGroup addHeadGroups(String[] headGroup) {
        headGroups.addAll(List.of(headGroup));
        return this;
    }

    public HeadGroup addHeadGroup(String headGroup) {
        headGroups.add(headGroup);
        return this;
    }
}
