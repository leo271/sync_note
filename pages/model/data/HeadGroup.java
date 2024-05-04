package model.data;

import java.util.HashSet;

public class HeadGroup {
    String name;
    HashSet<String> heads;
    HashSet<String> headGroups;

    public HeadGroup(String name) {
        this.name = name;
        this.heads = new HashSet<String>();
        this.headGroups = new HashSet<String>();
    }

}
