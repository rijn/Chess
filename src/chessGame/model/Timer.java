package chessGame.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Timer {
    Integer value = 0;

    Boolean enabled = false;

    javax.swing.Timer timer;

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

    public Boolean isEnabled() {
        return enabled;
    }

    public void enable() {
        this.enabled = true;
        timer.start();
    }

    public void disable() {
        this.enabled = false;
        timer.stop();
    }

    public String getString() {
        return String.valueOf(value / 60) + ":" + String.valueOf(value % 60);
    }
}
