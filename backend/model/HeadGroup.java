package backend.model;

import java.util.List;
import java.util.ArrayList;

public class HeadGroup {
    public String name;
    public List<String> heads;
    public List<String> headGroups;

    public HeadGroup(String name, List<String> heads, List<String> headGroups) {
        this.name = name;
        this.heads = new ArrayList<String>(heads);
        this.headGroups = new ArrayList<String>(headGroups);
    }

    public String toJSON() {
        return "{\"name\":\"" + name + "\",\"heads\":" + heads + ",\"headGroups\":" + headGroups
                + "}";
    }
}
