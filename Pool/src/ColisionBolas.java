import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class ColisionBolas extends JPanel implements ActionListener {
    private int x1 = 40;
    private int y1 = 40;
    private int x2 = 200;
    private int y2 = 200;
    private int x1Speed = 0;
    private int y1Speed = 0;
    private int x2Speed = -1;
    private int y2Speed = -2;
    private int mouseX;
    private int mouseY;
    private Timer timer;

    public ColisionBolas() {
        timer = new Timer(10, this);
        timer.start();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                calculateSpeed(e.getX(), e.getY());
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
                repaint();
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }

    public void calculateSpeed(int targetX, int targetY) {
        // Calcula las diferencias en las coordenadas entre el centro de la pelota y el punto de clic del mouse
        int diffX = targetX - x1;
        int diffY = targetY - y1;
        
        // Calcula la distancia entre el centro de la pelota y el punto de clic del mouse
        double distance = Math.sqrt(diffX * diffX + diffY * diffY);
        
        // Calcula la dirección del impacto normalizando las diferencias
        double impactDirectionX = diffX / distance;
        double impactDirectionY = diffY / distance;
        
        // Aplica la fuerza al movimiento de la pelota basándose en la dirección del impacto
        double force = 0.1 * distance; // 0.1 es un factor de escala para ajustar la fuerza
        x1Speed -= (int) (impactDirectionX * force);
        y1Speed -= (int) (impactDirectionY * force);
        
    }


    public void move() {
        x1 += x1Speed;
        y1 += y1Speed;
        x2 += x2Speed;
        y2 += y2Speed;

        // Colisión con las paredes
        if (x1 <= 0 || x1 >= getWidth()) {
            x1Speed = -x1Speed;
        }
        if (y1 <= 0 || y1 >= getHeight()) {
            y1Speed = -y1Speed;
        }
        if (x2 <= 0 || x2 >= getWidth()) {
            x2Speed = -x2Speed;
        }
        if (y2 <= 0 || y2 >= getHeight()) {
            y2Speed = -y2Speed;
        }

        // Colisión entre las bolas
        if (Math.abs(x1 - x2) <= 20 && Math.abs(y1 - y2) <= 20) {
            int tempX1Speed = x1Speed;
            int tempY1Speed = y1Speed;
            x1Speed = x2Speed;
            y1Speed = y2Speed;
            x2Speed = tempX1Speed;
            y2Speed = tempY1Speed;
        }

        // Disminuir velocidad por rozamiento
        x1Speed *= 0.99;
        y1Speed *= 0.99;
        x2Speed *= 0.99;
        y2Speed *= 0.99;
    }

    @Override
    protected void paintComponent(Graphics g) {
    	
    	
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillOval(x1, y1, 20, 20);
        g.setColor(Color.BLUE);
        g.fillOval(x2, y2, 20, 20);

        // Dibujar línea de guía
        g.setColor(Color.GREEN);
        g.drawLine(x1+10,y1+10 , mouseX, mouseY);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Colisión de Bolas");
        ColisionBolas panel = new ColisionBolas();
        frame.add(panel);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
