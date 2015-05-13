package ua.drozda.battlecity.core.tiles;

import javafx.scene.image.Image;
import ua.drozda.battlecity.core.interfaces.Updatable;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by GFH on 12.05.2015.
 */
public class Water extends Tile implements Updatable {

    private List<Image> spriteList = new LinkedList<Image>();
    private int updateInterval = 500 * 1000000; // half a second (500 000 000)
    private long lastUpdate;
    private long curIndex;

    public Water(int tileState) {
        super(tileState);
    }


    public void update(Long nanoSeconds) {
        if((nanoSeconds - lastUpdate)>=updateInterval){
            curSprite = spriteList.get((int)(++curIndex)%spriteList.size());
            lastUpdate=nanoSeconds;
        }
    }

    public void update(Object... args) {
        if (args[0] instanceof Long){
            update((Long)args[0]);
        }
    }

    public static int getMaxState() {
        return 2;
    }
}
