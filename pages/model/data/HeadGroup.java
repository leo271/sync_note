package model.data;

import java.util.HashSet;

public class HeadGroup {
    public String name;
    public HashSet<Head> heads;
    public HashSet<String> headGroups;

    public HeadGroup(String name) {
        this.name = name;
        this.heads = new HashSet<Head>();
        this.headGroups = new HashSet<String>();
    }

    public void addHead(Head head) {
        heads.add(head);
    }
}
