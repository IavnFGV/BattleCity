package ua.drozda.battlecity.core;

import javafx.geometry.Point2D;
import ua.drozda.battlecity.core.actors.ActorCommand;
import ua.drozda.battlecity.core.actors.MoveStrategy;
import ua.drozda.battlecity.core.actors.PlayerTank;
import ua.drozda.battlecity.core.actors.Tank;

/**
 * Created by GFH on 31.05.2015.
 */
public class Player {
    ActorCommand command = new ActorCommand();
    World world;
    PlayerTank playerTank;
    private Point2D respawnPosition = new Point2D(10 * world.getCellWidth(), 25 * world.getCellHeight());

    public Player(World world) {
        this.world = world;
        playerTank = new PlayerTank(world.getCollisionManager());
        command.addObserver(playerTank);
        playerTank.setMoveStrategy(new MoveStrategy<Tank>(playerTank));
    }

    public ActorCommand getCommand() {
        return command;
    }

    public void setCommand(ActorCommand command) {
        this.command = command;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }
}
