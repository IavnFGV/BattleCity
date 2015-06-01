package ua.drozda.battlecity.core.actors;

import ua.drozda.battlecity.core.World;

/**
 * Created by GFH on 16.05.2015.
 */
public enum ActorState {
    STATE_SPAWNING("STATE_SPAWNING", "STATE_SPAWNING", "STATE_ALIVE_0", World.TIME_IN_SPAWNING),
    STATE_SPAWNING_X("STATE_SPAWNING_X", "STATE_SPAWNING_X", "STATE_ALIVE_X", World.TIME_IN_SPAWNING),
    STATE_SPAWNING_ARMOR("STATE_SPAWNING_ARMOR", "STATE_SPAWNING_ARMOR", "STATE_ALIVE_3", World.TIME_IN_SPAWNING),
    STATE_SPAWNING_ARMOR_X("STATE_SPAWNING_ARMOR_X", "STATE_SPAWNING_ARMOR_X", "STATE_ALIVE_3_X", World.TIME_IN_SPAWNING),
    STATE_ALIVE_0("STATE_DEAD", "STATE_FRIENDLY_FIRE", "STATE_ALIVE_0", 0l),
    STATE_ALIVE_X("STATE_DEAD", "STATE_FRIENDLY_FIRE", "STATE_ALIVE_X", 0l),
    STATE_ALIVE_1("STATE_ALIVE_0", "STATE_FRIENDLY_FIRE", "STATE_ALIVE_1", 0l),
    STATE_ALIVE_2("STATE_ALIVE_1", "STATE_FRIENDLY_FIRE", "STATE_ALIVE_2", 0l),
    STATE_ALIVE_3("STATE_ALIVE_2", "STATE_FRIENDLY_FIRE", "STATE_ALIVE_3", 0l),
    STATE_ALIVE_3_X("STATE_ALIVE_2", "STATE_FRIENDLY_FIRE", "STATE_ALIVE_3_X", 0l),
    STATE_EXPLODING("STATE_EXPLODING", "STATE_EXPLODING", "STATE_DEAD", 1l),
    STATE_DEAD("STATE_DEAD", "STATE_DEAD", "STATE_DEAD", 0l),
    STATE_FRIENDLY_FIRE("STATE_EXPLODING", "STATE_FRIENDLY_FIRE", "STATE_ALIVE_0", World.TIME_IN_FRIENDLY_FIRE);
    // only for player


    private String nextBulletState;
    private String nextFriendBulletState;
    private String nextUpdateState;
    private Long timeInState;

    ActorState(String nextBulletState, String nextFriendBulletState, String nextUpdateState, Long timeInState) {
        this.nextBulletState = nextBulletState;
        this.nextFriendBulletState = nextFriendBulletState;
        this.nextUpdateState = nextUpdateState;
        this.timeInState = timeInState;
    }

    public void nextState(ActorStateHelper helper) {
        ActorState curState = helper.getNextState();
        if (helper.isBullet()) { //todo on null???
            if (helper.isFriendBullet()) {
                helper.setNextState(this.getNextFriendBulletState());
            } else {
                helper.setNextState(this.getNextBulletState());
            }
            helper.setRemainTime(this.getTimeInState());
            return;
        }
        if (curState.getTimeInState() != 0) {
            if (helper.getRemainTime() >= curState.getTimeInState()) {
                helper.setNextState(curState.getNextUpdateState());
            }
        }
    }

    public ActorState getNextFriendBulletState() {
        return ActorState.valueOf(nextFriendBulletState);
    }

    public ActorState getNextBulletState() {
        return ActorState.valueOf(nextBulletState);
    }

    public void setNextBulletState(ActorState nextBulletState) {
        this.nextBulletState = nextBulletState.name();
    }

    public Long getTimeInState() {
        return timeInState;
    }

    public ActorState getNextUpdateState() {
        return ActorState.valueOf(nextUpdateState);
    }

    public void setNextUpdateState(ActorState nextUpdateState) {
        this.nextUpdateState = nextUpdateState.name();
    }

    public void setTimeInState(Long timeInState) {
        this.timeInState = timeInState;
    }

    public void setNextFriendBulletState(ActorState nextFriendBulletState) {
        this.nextFriendBulletState = nextFriendBulletState.name();
    }

    public static class ActorStateHelper {
        private Long remainTime;
        private ActorState nextState;
        private Boolean bullet;
        private Boolean friendBullet;

        public Long getRemainTime() {
            return remainTime;
        }

        public void setRemainTime(Long remainTime) {
            this.remainTime = remainTime;
        }

        public ActorState getNextState() {
            return nextState;
        }

        public void setNextState(ActorState nextState) {
            this.nextState = nextState;
        }

        public Boolean isBullet() {
            return bullet;
        }

        public void setBullet(Boolean bullet) {
            this.bullet = bullet;
        }

        public Boolean isFriendBullet() {
            return friendBullet;
        }

        public void setFriendBullet(Boolean friendBullet) {
            this.friendBullet = friendBullet;
        }
    }


}
