/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javakpi.uitest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import sofia_kp.KPICore;
import sofia_kp.SIBResponse;
import org.fruct.oss.*;

/**
 *
 * @author kulakov
 */
public class JavakpiUitest extends Panel implements ActionListener {

    // SmartSpaces
    KPICore kp = null;

    // панель параметров соединения
    JTextField tfIP, tfPort, tfSSN;
    JPanel jpConnection;
    JLabel lConnectionStatus;
    JButton bConnect;

    // панель профиля пользователя
    JTextField tfUserID, tfUserName, tfUserSurname, tfUserGeoLat, tfUserGeoLon;
    JTextArea tfUserPreferences;
    User user = null;
    JPanel jpUser;
    JButton bUserGenerate, bUserGet, bUserUpdate, bUserRemove;

    /**
     * генерация UI
     */
    public JavakpiUitest() {

        JPanel jpMain = new JPanel(false);
        jpMain.setLayout(new BoxLayout(jpMain, BoxLayout.Y_AXIS));
        JPanel line1, line2, line3, line4, line5, line6;

        //=============================================
        // панель соединения с сервером
        tfIP = new JTextField();
        tfPort = new JTextField();
        tfSSN = new JTextField();

        tfIP.setColumns(10);
        tfPort.setColumns(5);
        tfSSN.setColumns(5);

        lConnectionStatus = new JLabel(" (disconnected)");

        bConnect = new JButton("Connect");
        bConnect.addActionListener(this);

        jpConnection = new JPanel(false);
        jpConnection.setBorder(BorderFactory.createLineBorder(Color.black));
        line1 = new JPanel(false);
        line1.add(new JLabel("IP:"));
        line1.add(tfIP);
        line1.add(new JLabel("Port:"));
        line1.add(tfPort);
        line1.add(new JLabel("SSN:"));
        line1.add(tfSSN);
        line1.add(bConnect);
        line1.add(lConnectionStatus);
        jpConnection.add(line1);
        jpMain.add(jpConnection);

        //=============================================
        // панель пользователя
        tfUserID = new JTextField();
        tfUserID.setColumns(20);
        tfUserName = new JTextField();
        tfUserName.setColumns(10);
        tfUserSurname = new JTextField();
        tfUserSurname.setColumns(10);
        tfUserGeoLat = new JTextField();
        tfUserGeoLat.setColumns(10);
        tfUserGeoLon = new JTextField();
        tfUserGeoLon.setColumns(10);
        tfUserPreferences = new JTextArea();
        tfUserPreferences.setColumns(20);
        tfUserPreferences.setRows(10);

        bUserGenerate = new JButton("Generate ID");
        bUserGenerate.addActionListener(this);
        bUserGet = new JButton("Load from SIB");
        bUserGet.addActionListener(this);
        bUserUpdate = new JButton("Update");
        bUserUpdate.addActionListener(this);
        bUserRemove = new JButton("Delete");
        bUserRemove.addActionListener(this);

        jpUser = new JPanel();
        jpUser.setBorder(BorderFactory.createLineBorder(Color.green));
        jpUser.setLayout(new BoxLayout(jpUser, BoxLayout.Y_AXIS));

        line1 = new JPanel();
        line1.add(new JLabel("USER INFORMATION"));
        jpUser.add(line1);

        line5 = new JPanel();
        line5.add(new JLabel("ID:"));
        line5.add(tfUserID);
        line5.add(bUserGenerate);
        jpUser.add(line5);

        line2 = new JPanel();
        line2.add(new JLabel("Name:"));
        line2.add(tfUserName);
        line2.add(new JLabel("Surname:"));
        line2.add(tfUserSurname);
        jpUser.add(line2);

        line3 = new JPanel();
        line3.add(new JLabel("Latitude:"));
        line3.add(tfUserGeoLat);
        line3.add(new JLabel("Longitude:"));
        line3.add(tfUserGeoLon);
        jpUser.add(line3);

        line4 = new JPanel();
        line4.add(tfUserPreferences);
        jpUser.add(line4);

        line6 = new JPanel();
        line6.add(bUserGet);
        line6.add(bUserUpdate);
        line6.add(bUserRemove);
        jpUser.add(line6);

        jpMain.add(jpUser);

        /////////////////////////////////////////////////////
        // отображение панели
        this.add(jpMain, BorderLayout.SOUTH);
    }

    /**
     * Создание окна, начальные параметры и т.п.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // создаем форму
        JavakpiUitest gui = new JavakpiUitest();
        Frame f = new Frame("JavaKPI Test UI");
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        f.add("Center", gui);
        f.setPreferredSize(new Dimension(1000, 600));
        f.pack();
        f.setVisible(true);
        f.setLocationRelativeTo(null);

        // начальные параметры
        gui.tfIP.setText("127.0.0.1");
        gui.tfPort.setText("10010");
        gui.tfSSN.setText("X");
    }

    /**
     * Обработка нажатия кнопок
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JComponent c = (JComponent) e.getSource();

        if (c == bConnect) {
            if (this.kp == null) {
                // подключаемся
                this.kp = new KPICore(tfIP.getText(), Integer.parseInt(tfPort.getText()), tfSSN.getText());
                if (this.kp == null) {
                    System.out.println("Can't create connection to SIB");
                    lConnectionStatus.setText("(Can't create connection to SIB)");
                    return;
                }
                this.kp.enable_debug_message();
                this.kp.enable_error_message();

                SIBResponse ret;
                ret = kp.join();
                if (!ret.isConfirmed()) {
                    JOptionPane.showMessageDialog(null, "Can't connect to SIB");
                    lConnectionStatus.setText("(Can't connect to SIB)");
                    this.kp = null;
                    return;
                }

                bConnect.setText("Disconnect");
                lConnectionStatus.setText("(Connected: " + this.kp.toString() + ")");
            } else {
                // отключаемся
                SIBResponse ret;
                ret = this.kp.unsubscribe();
                if (!ret.isConfirmed()) {
                    JOptionPane.showMessageDialog(null, "Error in unsubscribe");
                }
                ret = this.kp.leave();
                if (!ret.isConfirmed()) {
                    JOptionPane.showMessageDialog(null, "Error leaving the SIB");
                }

                this.kp = null;
                bConnect.setText("Connect");
                lConnectionStatus.setText("(Disconnected)");
            }

        } // c == bConnect
        else if (c == bUserGenerate) {
            tfUserID.setText(BaseRDF.generateID("user"));
        } // c == bUserGenerate
        else if (c == bUserGet) {
            if (this.kp == null) {
                JOptionPane.showMessageDialog(null, "First connect to SIB!");
                return;
            }
            user = new User(this.kp, tfUserID.getText());
            user.load();

            ArrayList<String> tt = user.name();
            if (!tt.isEmpty()) {
                tfUserName.setText(tt.get(0));
            }

            tt = user.surname();
            if (!tt.isEmpty()) {
                tfUserSurname.setText(tt.get(0));
            }

            ArrayList<Location> loclist = user.hasLocation();
            if (!loclist.isEmpty()) {
                Location loc;
                loc = loclist.get(0);

                tt = loc.lat();
                if (!tt.isEmpty()) {
                    tfUserGeoLat.setText(tt.get(0));
                }

                tt = loc.lon();
                if (!tt.isEmpty()) {
                    tfUserGeoLon.setText(tt.get(0));
                }
            }
            
            tt = user.preferences();
            StringBuilder sb = new StringBuilder();
            for (String s: tt) {
                sb.append(s + "\n");
            }
            tfUserPreferences.setText(sb.toString());
        } // c == bUserGet
        else if (c == bUserUpdate) {
            if (this.kp == null) {
                JOptionPane.showMessageDialog(null, "First connect to SIB!");
                return;
            }
            if (user == null || !user.getID().equals(tfUserID.getText())) {
                user = new User(this.kp, tfUserID.getText());
            }
            if (!tfUserName.getText().isEmpty())
                user.name(tfUserName.getText());
            if (!tfUserSurname.getText().isEmpty())
                user.surname(tfUserSurname.getText());
            
            String[] prefs = tfUserPreferences.getText().split("\n");
            if (prefs.length > 0) {
                user.preferences(new ArrayList<String>(Arrays.asList(prefs)));
            }
            if (!tfUserGeoLat.getText().isEmpty() && !tfUserGeoLon.getText().isEmpty()) {
                ArrayList<Location> loclist = user.hasLocation();
                Location loc;
                if (loclist.isEmpty()) {
                    loc = new Location(this.kp);
                    user.hasLocation(loc);
                } else {
                    loc = loclist.get(0);
                }
                if (!tfUserGeoLat.getText().isEmpty())
                    loc.lat(tfUserGeoLat.getText());
                if (!tfUserGeoLon.getText().isEmpty())
                    loc.lon(tfUserGeoLon.getText());
                loc.update();
            }
            user.update();
        } else {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

}
