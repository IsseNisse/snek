import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

public class snek extends Canvas {

    int width = 800;
    int height = 600;
    BufferStrategy bs;

    public snek() {
        setSize(width,height);
        JFrame frame = new JFrame("Snek");
        frame.add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.addKeyListener(new KL());
    }

    public void  paint(Graphics g) {
        bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(1);
            return;
        }
        draw(g);
        g.dispose();
        bs.show();
        repaint();
    }

    public void draw (Graphics g) {
        g.setColor(new Color(0x000));
        g.fillRect(100, 200 ,20, 15);
    }

    public static void main(String[] args) {
        snek minGrafik = new snek();
    }

    private class KL implements KeyListener {
        @Override
        public void keyTyped(KeyEvent keyEvent) {

        }

        @Override
        public void keyPressed(KeyEvent keyEvent) {
            if (keyEvent.getKeyChar()=='a') {

            } else if (keyEvent.getKeyChar()=='d') {

            } else if (keyEvent.getKeyChar()=='w') {

            } else if (keyEvent.getKeyChar()=='s') {

            }
            repaint();
        }


        @Override
        public void keyReleased(KeyEvent keyEvent) {

        }
    }
}