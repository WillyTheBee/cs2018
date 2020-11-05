package de.examination;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Controller implements ChangeListener {

    View view = null;

    public void initalise (View view){
        this.view = view;

    }

    @Override
    public void stateChanged(ChangeEvent e) {

        int currentCounter = (Integer) view.counter.getValue();
        int clocksCount = this.view.clocks.getComponentCount();
        int differenz  = currentCounter - clocksCount;
        
        if (differenz < 0 ) {
            for (int i = 0; i < -differenz; i++) {
                // dadurch wird immer das letzte element gelöscht
                this.view.clocks.remove(clocksCount - i - 1);
            }
        } else if (differenz > 0){
            for (int i = 0; i < differenz; i++) {
                this.view.clocks.add(new Display());
            }
        }

        this.view.pack();
        this.view.paint(this.view.getGraphics());

        //     this.view.repaint();
    }
}
