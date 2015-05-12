package ua.drozda.battlecity.core.tiles;

import javafx.scene.image.Image;
import ua.drozda.battlecity.core.Updatable;

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

    public Water(Image curSprite) {
        super(curSprite);
    }

    public void update(Long nanoSeconds) {
        if((nanoSeconds - lastUpdate)>=updateInterval){
            curSprite = spriteList.get((int)(++curIndex)%spriteList.size());
            lastUpdate=nanoSeconds;
        }
    }
}
