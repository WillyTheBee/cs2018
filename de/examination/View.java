package de.examination;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;


public class View extends JFrame {

    public JSpinner counter;
    public JPanel clocks;
    JPanel p;

    public void initialise (Controller controller) {

        setTitle("Weltzeituhr");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(1007,800));

        JLabel label = new JLabel(new ImageIcon(getClass().getResource("urania-weltzeituhr_alexanderplatz_berlin.jpeg")));
        add(label, BorderLayout.WEST);

        p = new JPanel();
        p.setLayout(new BorderLayout());

        clocks = new JPanel();
        clocks.setLayout(new BoxLayout(clocks, BoxLayout.Y_AXIS));


        counter = new JSpinner(new SpinnerNumberModel(0,0,5,1));
        counter.setPreferredSize(new Dimension(400,100));
        counter.setFont(new Font("serifless", Font.BOLD, 64));
        counter.setToolTipText("Erzeugt oder Löscht eine Uhr");
        counter.addChangeListener(controller);

        p.add(counter , BorderLayout.SOUTH);
        p.add(clocks, BorderLayout.NORTH);

        add(p, BorderLayout.EAST);

        // pack macht das der platz im JPanel komplett ausgenutzt wird, bzw. sich sachen nicht überlappen
        pack();
        setVisible(true);
    }


}
