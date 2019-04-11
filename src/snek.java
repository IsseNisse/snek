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
    int vx;
    int vy;
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
        x+= vx;
        y+= vy;
    }

    public void draw (Graphics g) {
        g.setColor(new Color(0xFFFFFF));
        g.fillRect(0,0,width,height);
        g.setColor(new Color(0x000));
        g.fillRect(x, y+100 ,15, 15);
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
                vx = 1;
            } else if (keyEvent.getKeyChar()=='d') {
                if (vx == -1){

                } else {
                    vx = 1;
                    vy = 0;
                }
            } else if (keyEvent.getKeyChar()=='w') {
                if (vy == 1) {

                } else {
                    vy = -1;
                    vx=0;
                }
            } else if (keyEvent.getKeyChar()=='s') {
                if (vy == -1) {

                } else {
                    vy = 1;
                    vx=0;
                }

            } else if (keyEvent.getKeyChar()=='a') {
                if (vx == 1) {

                } else {
                    vx = -1;
                    vy = 0;
                }

            }
//            repaint();
        }


        @Override
        public void keyReleased(KeyEvent keyEvent) {

        }
    }
}
