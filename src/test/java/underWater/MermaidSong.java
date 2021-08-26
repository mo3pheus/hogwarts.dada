package underWater;

import javax.crypto.spec.IvParameterSpec;
import java.io.Serializable;

public class MermaidSong implements Serializable {
    private int depth;
    private String name;
    boolean hostile;
    private String song;

    @Override
    public String toString() {
        return "From the base of the hill we dive.\n" + depth + " feet underwater we live.\n"
                + "My name is = " + name + "\n"
                + "Listen to my song = " + song;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHostile() {
        return hostile;
    }

    public void setHostile(boolean hostile) {
        this.hostile = hostile;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }
}
