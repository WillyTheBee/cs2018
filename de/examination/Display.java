package de.examination;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

import static javax.swing.JOptionPane.ERROR_MESSAGE;


public class Display extends JPanel implements Serializable, ActionListener {

    // mit transient verhindert man ein Serialisieren

    transient DateTimeFormatter formatter;
    transient SimpleDateFormat Simpleformatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    String dateString;
    JLabel label;
    transient Timer timer;

    String timeZone;
    Date datum;

    JComboBox<String> comboBox = new JComboBox<>();

    public Display() {
        this.formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

        label = new JLabel();
        label.setForeground(Color.RED);

        label.setFont(new Font("serifless", Font.BOLD, 48));

        // try mit ressources damit ich streams nicht mehr selber schließen muss
        //alles get Class gibt mir die Klasse des aktuellen Objektes
        // dann schau dort wo deine klasse ist und gib mir die ressource as stream sucht nach der Datei an dem ort wo meine Classe ist
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("java-timezones.txt")))) {

            bufferedReader.lines().forEach(comboBox::addItem);

        } catch (IOException e) {

            e.printStackTrace();

            Arrays.asList("Asia/Bangkok", "Asia/Beirut", "Asia/Damascus", "Asia/Istanbul", "Asia/Jakarta",
                    "Asia/Shanghai", "Asia/Tokyo", "Europe/Berlin", "Europe/Lisbon", "Europe/Moscow"
            ).forEach(comboBox::addItem);
        }


        comboBox.setSelectedItem("Europe/Berlin");

        //this.dateString = Instant.now().atZone(ZoneId.of((String) comboBox.getSelectedItem())).format(this.formatter);//Fehlt im text
        //label.setText(dateString);

        this.setPreferredSize(new Dimension(600, 120));
        this.setBackground(Color.LIGHT_GRAY);
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createLineBorder(Color.GREEN, 5));

        this.add(comboBox, BorderLayout.SOUTH);
        this.add(label, BorderLayout.NORTH);

        timer = new Timer(1000, this);
        comboBox.addActionListener(this);

        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object actionSource = e.getSource();

        if (actionSource instanceof Timer) {
            Date datumsobjekt = new Date();
            setDatum(datumsobjekt);

        } else if (actionSource instanceof JComboBox) {
            JComboBox<String> comboaction = (JComboBox<String>) actionSource;
            String aktullerEintrag = (String) comboaction.getSelectedItem();
            setTimeZone(aktullerEintrag);
        } else {
            JOptionPane.showMessageDialog(null, "unbekannte Qulle", "FEHLER", ERROR_MESSAGE);
        }
    }


    public String getTimeZone() {
        //TimeZone timeZone = TimeZone.getTimeZone((String) comboBox.getSelectedItem());
        return Simpleformatter.getTimeZone().getDisplayName();
    }

    public void setTimeZone(String timeZone) {
        TimeZone timeZone1 = TimeZone.getTimeZone(timeZone);
        Simpleformatter.setTimeZone(timeZone1);
    }

    public Date getDatum() {
        DateFormat dateFormat = DateFormat.getInstance();
        try {
            datum = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return datum;
    }

    public void setDatum(Date datum) {
        dateString = Simpleformatter.format(datum);
        label.setText(dateString);
        this.datum = datum;
    }
}
