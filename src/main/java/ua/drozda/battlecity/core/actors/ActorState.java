package ua.drozda.battlecity.core.actors;

/**
 * Created by GFH on 16.05.2015.
 */
public enum ActorState {
    STATE_SPAWNING, STATE_ALIVE, STATE_EXPLODING, STATE_DEAD;

    public static Long getTimeInState(ActorState state) {
        switch (state) {
            case STATE_SPAWNING:
                return 1500000000L;
            case STATE_ALIVE:
                return 0L;
            case STATE_EXPLODING:
                return 1L;
            case STATE_DEAD:
                return 1L;
            default:
                return 0L;
        }
    }

    public static ActorState nextState(ActorState state) {
        switch (state) {
            case STATE_SPAWNING:
                return STATE_ALIVE;
            case STATE_ALIVE:
                return STATE_EXPLODING;
            case STATE_EXPLODING:
                return STATE_DEAD;
            default:
                return STATE_DEAD;
        }
    }
}
