import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

public class snek extends Canvas implements Runnable {

    int width = 800;
    int height = 600;
    BufferStrategy bs;
    int x = 100;
    int y = 100;
    private Thread thread;
    private boolean running = false;

    public snek() {
        setSize(width,height);
        JFrame frame = new JFrame("Snek");
        frame.add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.addKeyListener(new KL());
    }

    public void  render() {
        bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(1);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        draw(g);
        g.dispose();
        bs.show();
    }

    private void update () {
        x++;

    }

    public void draw (Graphics g) {
        g.setColor(new Color(0xFFFFFF));
        g.fillRect(0,0,width,height);
        g.setColor(new Color(0x000));
        g.fillRect(x, y+100 ,20, 15);
    }

    public synchronized void start() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        double ns = 1000000000.0 / 60.0;
        double delta = 0;
        long lastTime = System.nanoTime();

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while(delta >= 1) {
                // Uppdatera koordinaterna
                update();
                // Rita ut bilden med updaterad data
                render();
                delta--;
            }
        }
        stop();
    }

    public static void main(String[] args) {
        snek minGrafik = new snek();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                minGrafik.setVisible(true);
            }
        });
        minGrafik.start();
    }

    private class KL implements KeyListener {
        @Override
        public void keyTyped(KeyEvent keyEvent) {

        }

        @Override
        public void keyPressed(KeyEvent keyEvent) {
            if (keyEvent.getKeyChar()=='\n') {


            } else if (keyEvent.getKeyChar()=='d') {

            } else if (keyEvent.getKeyChar()=='w') {

            } else if (keyEvent.getKeyChar()=='s') {

            } else if (keyEvent.getKeyChar()=='a') {

            }
//            repaint();
        }


        @Override
        public void keyReleased(KeyEvent keyEvent) {

        }
    }
}
