package chessGame.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Timer wrapping
 */
public class Timer {
    /**
     * Value of the timer
     */
    Integer value = 0;

    /**
     * Timer enable indicator
     */
    Boolean enabled = false;

    /**
     * timer
     */
    javax.swing.Timer timer;

    /**
     * Constructor
     * @param initialValue
     */
    public Timer(Integer initialValue) {
        this.value = initialValue;

        timer = new javax.swing.Timer(1000, null);
        timer.setRepeats(true);
        timer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                value -= 1;
            }
        });
    }

    /**
     * Return enabled status
     * @return
     */
    public Boolean isEnabled() {
        return enabled;
    }

    /**
     * Set to enable
     */
    public void enable() {
        this.enabled = true;
        timer.start();
    }

    /**
     * Set to disable
     */
    public void disable() {
        this.enabled = false;
        timer.stop();
    }

    /**
     * Get a human readable string of time
     * @return
     */
    public String getString() {
        return String.valueOf(value / 60) + ":" + String.valueOf(value % 60);
    }
}
