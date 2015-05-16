package ua.drozda.battlecity.core.collisions;


import javafx.geometry.Point2D;
import ua.drozda.battlecity.core.actors.Actor;

/**
 * Created by GFH on 14.05.2015.
 */
public class CollisionManager {

    //    public static Tile handleCollision(Tile curTile,Bullet bullet){
//        if(curTile instanceof Brick){
//
//        }
//    }
    public Boolean canMove(Actor actor, Point2D newPosition) {
        return true;//TODO Check for collision in new position
    }
}
