import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;
import javax.swing.*;

class Surface extends JPanel implements ActionListener {

    private Timer timer;
    int x = 0, y = 0;
    boolean durum = true;


    public Surface() {

        initTimer();
    }

    private void initTimer() {

        int DELAY = 150;
        timer = new Timer(DELAY, this);
        timer.start();

    }

    public Timer getTimer() {
        return timer;
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        Random rand = new Random();
        int r, gr, b;
        r = rand.nextInt(256);
        gr = rand.nextInt(256);
        b = rand.nextInt(256);
        Color c = new Color(r, gr, b);

        float[] dash1 = {2f, 0f, 2f};
        BasicStroke bs1 = new BasicStroke(2, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_ROUND, 1.0f, dash1, 2f);


        g2d.setStroke(bs1);

        g2d.setPaint(Color.black);
        g2d.drawRect(x + 1, y + 1, ((getWidth() / 10) - 1), ((getHeight() / 8) - 1));
        g2d.setPaint(c);
        g2d.fillRect(x + 2, y + 2, ((getWidth() / 10) - 2), ((getHeight() / 8) - 2));


    }

    @Override
    public void paintComponent(Graphics g) {

        doDrawing(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (durum) {

            if (x >= getWidth() - getWidth() / 10) {
                y += getHeight() / 8;
                durum = false;


            } else {
                x += (getWidth() / 10);

            }



        } else {
            if (x > 0) {
                x -= (getWidth() / 10);

            } else {
                y += getHeight() / 8;

                if (y >= getHeight() - (getHeight() / 8)) {
                    timer.stop();

                } else
                    durum = true;

            }
        }


        repaint();
    }
}

public class Main extends JFrame {

    public Main() {

        initUI();
    }

    private void initUI() {


        String genislik, yukseklik;
        int genislikInt,yukseklikInt;

        while (true) {
            genislik = JOptionPane.showInputDialog("Genislik degerini giriniz: ");
            if (genislik.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Genislik degeri bos birakilamaz!");
            } else{
                try {
                     genislikInt = Integer.parseInt(genislik);
                    break;
                }catch (Exception e){
                    JOptionPane.showMessageDialog(null, "Genislik degeri sayı olmalıdır!");
                }
            }



        }
        while (true) {
            yukseklik = JOptionPane.showInputDialog("Yukseklik degerini giriniz: ");

            if (yukseklik.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Yukseklik degeri bos birakilamaz!");
            } else{
                try {
                    yukseklikInt = Integer.parseInt(yukseklik);
                    break;

                }catch (Exception e){
                    JOptionPane.showMessageDialog(null, "Yukseklik degeri sayı olmalıdır!");
                }
            }



        }


        if (genislikInt % 10 != 0) {
            genislikInt -= genislikInt % 10;
        }
        if (yukseklikInt % 8 != 0) {
            yukseklikInt -= yukseklikInt % 8;
        }

        genislikInt += 16;
        yukseklikInt += 39;

        setTitle("Kare");
        setSize(genislikInt, yukseklikInt);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        final Surface surface = new Surface();
        add(surface);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Timer timer = surface.getTimer();
                timer.stop();
            }
        });


    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

                Main pencere = new Main();
                pencere.setVisible(true);
            }
        });
    }
}