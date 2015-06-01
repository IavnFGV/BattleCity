package ua.drozda.battlecity.fx;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import ua.drozda.battlecity.core.TankType;
import ua.drozda.battlecity.core.actors.Direction;
import ua.drozda.battlecity.core.actors.Tank;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by GFH on 01.06.2015.
 */
public class FxTank implements Observer {

    private static HashMap<TankType, HashMap<Direction, Rectangle2D[][]>> spritesMap = new HashMap();
    //2d array for starcount of tank;

    static {
        //for player tank
        HashMap<Direction, Rectangle2D[][]> bufHashMap = new HashMap<>();
        //UP
        Rectangle2D[][] rectangle2Ds = new Rectangle2D[4][2];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                rectangle2Ds[i][j] = new Rectangle2D(FxWorld.firstPlayerZoneX + FxWorld.tankSize * j, FxWorld
                        .firstPlayerZoneY + FxWorld.tankSize * i, FxWorld.tankSize, FxWorld.tankSize);
            }
        }
        bufHashMap.put(Direction.UP, rectangle2Ds);
        //LEFT
        rectangle2Ds = new Rectangle2D[4][2];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                rectangle2Ds[i][j] = new Rectangle2D(FxWorld.firstPlayerZoneX + FxWorld.tankSize * 2 + FxWorld.tankSize *
                        j, FxWorld
                        .firstPlayerZoneY + FxWorld.tankSize * i, FxWorld.tankSize, FxWorld.tankSize);
            }
        }
        bufHashMap.put(Direction.LEFT, rectangle2Ds);
        //DOWN
        rectangle2Ds = new Rectangle2D[4][2];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                rectangle2Ds[i][j] = new Rectangle2D(FxWorld.firstPlayerZoneX + FxWorld.tankSize * 4 + FxWorld.tankSize *
                        j, FxWorld
                        .firstPlayerZoneY + FxWorld.tankSize * i, FxWorld.tankSize, FxWorld.tankSize);
            }
        }
        bufHashMap.put(Direction.DOWN, rectangle2Ds);
        //RIGHT
        rectangle2Ds = new Rectangle2D[4][2];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                rectangle2Ds[i][j] = new Rectangle2D(FxWorld.firstPlayerZoneX + FxWorld.tankSize * 6 + FxWorld.tankSize *
                        j, FxWorld
                        .firstPlayerZoneY + FxWorld.tankSize * i, FxWorld.tankSize, FxWorld.tankSize);
            }
        }
        bufHashMap.put(Direction.RIGHT, rectangle2Ds);
        spritesMap.put(TankType.FirstPlayer, bufHashMap);
    }

    private ImageView imageView = new ImageView(FxWorld.sprites);
    private Tank tank;
    private Rectangle2D curSprite;

    public FxTank(Tank tank) {
        this.tank = tank;
        curSprite = spritesMap.get(tank.getTankType()).get(tank.getDirection())[tank.getStarCount()][0];
        tank.addObserver(this);
        imageView.setViewport(curSprite);
        imageView.setX(tank.getPosition().getX() * FxWorld.tankSize);
        imageView.setY(tank.getPosition().getY() * FxWorld.tankSize);
    }

    public Tank getTank() {
        return tank;
    }

    public void setTank(Tank tank) {
        this.tank = tank;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Tank) {
            Tank tank = (Tank) o;
            imageView.setViewport(spritesMap.get(tank.getTankType()).get(tank.getDirection())[tank.getStarCount()
                    ][tank.getHeartState()]);

        }
    }

}
