package model;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import model.db_enum.DB;

public class HeadGroup {
    private static final char US = '\u001f';

    public String name;
    public HashSet<String> heads;
    public HashSet<String> headGroups;
    public int like;

    public HeadGroup(String name) {
        this.name = name;
        this.heads = new HashSet<String>();
        this.headGroups = new HashSet<String>();
        this.like = 0;
    }

    public HeadGroup(String name, HashSet<String> heads, HashSet<String> headGroups, int like) {
        this.name = name;
        this.heads = heads;
        this.headGroups = headGroups;
        this.like = like;
    }

    public static HeadGroup fromJSONL(ArrayList<JSON> jsons) {
        var heads = new HashSet<String>();
        var headGroups = new HashSet<String>();
        var like = 0;
        var name = jsons.get(0).get(DB.GROUP_NAME.name);
        for (var json : jsons) {
            var type = json.get(DB.TYPE_HG.name);
            var child = json.get(DB.NAME.name);
            if (type.equals("H")) {
                heads.add(child);
            } else {
                headGroups.add(child);
            }
            like = Math.max(like, Integer.parseInt(json.get(DB.LIKE.name)));
        }

        return new HeadGroup(name, heads, headGroups, like);
    }

    public static HeadGroup fronLines(ArrayList<String> lines) {
        var heads = new HashSet<String>();
        var headGroups = new HashSet<String>();
        var like = 0;
        var name = lines.get(0).split(US + "")[1];
        for (var line : lines) {
            var split = line.split(US + "");
            var type = split[4];
            var child = split[3];
            if (type.equals("H")) {
                heads.add(child);
            } else {
                headGroups.add(child);
            }
            like = Math.max(like, Integer.parseInt(split[2]));
        }
        return new HeadGroup(name, heads, headGroups, like);
    }

    public ArrayList<JSON> toJSONL() {
        var jsons = new ArrayList<JSON>();
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (md5 == null) {
            return jsons;
        }
        for (var head : heads) {
            var json = new JSON();
            json.put(DB.GROUP_NAME.name, name);
            json.put(DB.TYPE_HG.name, "H");
            json.put(DB.LIKE.name, Integer.toString(like));
            json.put(DB.NAME.name, head);
            json.put(DB.RECORD_ID.name, md5.digest((head + name).getBytes()).toString());
            jsons.add(json);
        }
        for (var headGroup : headGroups) {
            var json = new JSON();
            json.put(DB.GROUP_NAME.name, name);
            json.put(DB.TYPE_HG.name, "G");
            json.put(DB.NAME.name, headGroup);
            json.put(DB.LIKE.name, Integer.toString(like));
            json.put(DB.RECORD_ID.name, md5.digest((headGroup + name).getBytes()).toString());
            jsons.add(json);
        }
        return jsons;
    }

    // 以下のメソッドは、HeadGroupのプロパティを操作しながら、自分自身を返すようにしている
    // これは、メソッドチェーンを可能にするためです
    // https://magazine.techacademy.jp/magazine/31905
    public HeadGroup addHeads(String[] head) {
        heads.addAll(List.of(head));
        return this;
    }

    public HeadGroup addHead(String head) {
        heads.add(head);
        return this;
    }

    public HeadGroup like() {
        this.like++;
        return this;
    }

    public HeadGroup unlike() {
        this.like--;
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof HeadGroup) {
            var headGroup = (HeadGroup) obj;
            return this.name.equals(headGroup.name) && this.heads.equals(headGroup.heads)
                    && this.headGroups.equals(headGroup.headGroups) && this.like == headGroup.like;
        }
        return false;
    }
}
