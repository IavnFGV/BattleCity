package ua.drozda.battlecity.core.actors;

/**
 * Created by GFH on 16.05.2015.
 */
public enum ActorState {
    STATE_SPAWNING, STATE_ALIVE, STATE_EXPLODING, STATE_DEAD, STATE_FRIENDLY_FIRE;

    public static Long getTimeInState(ActorState state) {
        switch (state) {
            case STATE_SPAWNING:
                return 500000000L;
            case STATE_ALIVE:
                return 0L;
            case STATE_EXPLODING:
                return 1L;
            case STATE_DEAD:
                return 1L;
            case STATE_FRIENDLY_FIRE:
                return 500000000L;
            default:
                return 0L;
        }
    }

    public static ActorState nextState(ActorState state) {
        switch (state) {
            case STATE_SPAWNING:
                return STATE_ALIVE;
            case STATE_ALIVE:
                return STATE_ALIVE;
            case STATE_EXPLODING:
                return STATE_DEAD;
            case STATE_FRIENDLY_FIRE:
                return STATE_ALIVE;
            default:
                return STATE_DEAD;
        }
    }
}
