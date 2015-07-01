package ua.drozda.battlecity.fx;


import javafx.scene.media.AudioClip;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Responsible for loading sound media to be played using an id or key.
 * Contains all sounds for use later.
 * </pre>
 * <pre> * User: cdea
 */
public class SoundManager {
    static public Queue<String> soundEventsQueue = new ConcurrentLinkedQueue<>();
    static ExecutorService soundPool = Executors.newFixedThreadPool(5);
    static Map<String, AudioClip> soundEffectsMap = new HashMap<>();
    static private Long soundTimer = 100000000l;
    static private Long soundTime = 0l;


    static {
        loadSoundEffects("pause", "pause.wav");
        loadSoundEffects("shieldhit", "shieldhit.wav");
        loadSoundEffects("shoot", "shoot.wav");
        loadSoundEffects("steelhit", "steelhit.wav");
        loadSoundEffects("tbonushit", "tbonushit.wav");
        loadSoundEffects("bonus", "bonus.wav");
        loadSoundEffects("brickhit", "brickhit.wav");
        loadSoundEffects("eexplosion", "eexplosion.wav");
        loadSoundEffects("fexplosion", "fexplosion.wav");
        loadSoundEffects("gameover", "gameover.wav");
        loadSoundEffects("ice", "ice.wav");
        loadSoundEffects("levelstarting", "levelstarting.wav");
        loadSoundEffects("life", "life.wav");
        loadSoundEffects("moving", "moving.wav");
        loadSoundEffects("nmoving", "nmoving.wav");
        soundEventsQueue.add("levelstarting");

    }

    public static void handleSoundQueue(Long now) {

        while (soundEventsQueue.peek() != null) {
            playSound(soundEventsQueue.poll());
        }

    }

    /**
     * Lookup a name resource to play sound based on the id.
     *
     * @param id identifier for a sound to be played.
     */
    public static void playSound(final String id) {
        Runnable soundPlay = new Runnable() {
            @Override
            public void run() {
                soundEffectsMap.get(id).play();
            }
        };
        soundPool.execute(soundPlay);
    }

    /**
     * Load a sound into a map to later be played based on the id.
     *
     * @param id       - The identifier for a sound.
     * @param filename - audiofile name
     */
    private static void loadSoundEffects(String id, String filename) {
        AudioClip sound = null;
        URL resource = SoundManager.class.getResource("../sounds/" + filename);
        System.out.println(resource.toExternalForm());
        sound = new AudioClip(resource.toString());
        soundEffectsMap.put(id, sound);
    }

    /**
     * Stop all threads and media players.
     */
    public static void shutdown() {
        soundPool.shutdown();
    }

}