import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class snek extends Canvas implements Runnable {

    Random R = new Random();
    int width = 800;
    int height = 600;
    BufferStrategy bs;
    int x = 100;
    int y = 100;
    int x1;
    int y1;
    int vx;
    int vy;
    Rectangle target;
    Rectangle striker;
    private Thread thread;
    private boolean running = false;


    //Ritar ut fönstret
    public snek() {
        setSize(width,height);
        JFrame frame = new JFrame("Snek");
        frame.add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.addKeyListener(new KL());

        target = new Rectangle(R.nextInt(width-11), R.nextInt(height-11), 10,10);
        striker = new Rectangle(width-16, height-16,15,15);
    }

    //Fixar buffret
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

    //Gör så att ormen rör sig
    private void update () {
        striker.x+= vx;
        striker.y+= vy;
        if (striker.intersects(target)) {
            target.x = R.nextInt(width-11);
            target.y = R.nextInt(height-11);
        }
    }

    //Ritar ut det grafiska i fönstret t.ex ormen
    public void draw (Graphics g) {
        g.setColor(new Color(0xFFFFFF));
        g.fillRect(0,0,width,height);
        frukt(g);
        ormen(g);
    }

    private void frukt(Graphics g) {
        g.setColor(new Color(0x9990000));
        g.fillOval(target.x, target.y, 10, 10);
    }

    private void ormen(Graphics g) {
        g.setColor(new Color(0x000));
        g.fillRect(striker.x, striker.y ,15, 15);
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


    //FPS
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
             if (keyEvent.getKeyChar()=='d') {
                if (vx == -2){

                } else {
                    vx = 2;
                    vy = 0;
                }
            } else if (keyEvent.getKeyChar()=='w') {
                if (vy == 2) {

                } else {
                    vy = -2;
                    vx=0;
                }
            } else if (keyEvent.getKeyChar()=='s') {
                if (vy == -2) {

                } else {
                    vy = 2;
                    vx=0;
                }

            } else if (keyEvent.getKeyChar()=='a') {
                if (vx == 2) {

                } else {
                    vx = -2;
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
