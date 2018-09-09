package sounds;

import sun.applet.AppletAudioClip;

public class TimerMusic {

    private AppletAudioClip timerSound;

    public TimerMusic() {
        timerSound = new AppletAudioClip(getClass().getResource("countdown.wav"));
    }

    public void startMusic() {
        timerSound.play();
    }

    public void stopMusic() {
        timerSound.stop();
    }

}
