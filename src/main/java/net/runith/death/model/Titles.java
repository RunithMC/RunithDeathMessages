package net.runith.death.model;

import lombok.Data;
import org.github.paperspigot.Title;

@Data
public final class Titles {

    private Title[] titles;
    private int next = 0;
    private boolean enable;

    public Title next() {
        if (titles.length == next+1) {
            next = 0;
            return titles[next];
        }
        return titles[next++];
    }
}