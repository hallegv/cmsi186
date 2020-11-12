import java.awt.Graphics;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class RobotSoccerSimulation extends JPanel {
    private static final long serialVersionUID = -5228718339006830546L;

    private static double WIDTH = 400;
    private static double HEIGHT = 600;

    private static double PLAYER_RADIUS;
    private static double ENEMY_RADIUS;
    private static double PLAYER_SPEED;
    private static double ENEMY_SPEED;
    private static double FRICTION;

    private static volatile String endMessage;

    static class Ball {
        private double x;
        private double y;
        private double radius;
        private double speed;
        private Color color;

        Ball(double x, double y, double radius, double speed, Color color) {
            this.x = x;
            this.y = y;
            this.radius = radius;
            this.speed = speed;
            this.color = color;
        }

        void moveToward(double targetX, double targetY) {
            var dx = targetX - this.x;
            var dy = targetY - this.y;
            var v = this.speed / Math.hypot(dx, dy);
            this.x = constrain(this.x + v * dx, this.radius, WIDTH - this.radius);
            this.y = constrain(this.y + v * dy, this.radius, HEIGHT - this.radius);
        }

        public static void endSimulationIfNecessary() {
            if (balls[0].speed <= 0) {
                endMessage = "Uh oh! Game Over!";
            } else if (balls[0].inside(goal)) {
                endMessage = "GOOAALL!";
            }
        }

        public static void adjustIfCollisions() {
            for (var b1 : balls) {
                for (var b2 : balls) {
                    if (b1 != b2) {
                        var dx = b2.x - b1.x;
                        var dy = b2.y - b1.y;
                        var dist = Math.hypot(dx, dy);
                        var overlap = b1.radius + b2.radius - dist;
                        if (overlap > 0) {
                            var adjustX = (overlap / 2) * (dx / dist);
                            var adjustY = (overlap / 2) * (dy / dist);
                            b1.x -= adjustX;
                            b1.y -= adjustY;
                            b2.x += adjustX;
                            b2.y += adjustY;
                        }
                    }
                }
            }
        }

        private static double constrain(double value, double low, double high) {
            return Math.max(low, (Math.min(value, high)));
        }

        void applyFriction() {
            double inf = Double.POSITIVE_INFINITY;
            this.speed = constrain(this.speed - FRICTION, 0, inf);
        }

        boolean inside(Goal goal) {
            return (
                this.x - this.radius > goal.x - goal.w / 2 &&
                this.x + this.radius < goal.x + goal.w / 2 &&
                this.y - this.radius > goal.y - goal.h / 2 &&
                this.y + this.radius < goal.y + goal.h / 2
            );
        }
    }

    private static Ball[] balls;

    private static class Goal {
        double x = WIDTH / 2;
        double y = 0;
        double w = 100;
        double h = 100;
    }

    private static Goal goal = new Goal();

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (var ball : balls) {
            g.setColor(ball.color);
            g.fillOval((int) (ball.x - ball.radius), (int) (ball.y - ball.radius), (int) ball.radius * 2,
                    (int) ball.radius * 2);
        }
        g.setColor(new Color(255, 100, 255, 128));
        g.fillRect((int) (goal.x - goal.w / 2), (int) (goal.y - goal.h / 2), (int) goal.w, (int) goal.h);
        if (endMessage != null) {
            g.setFont(new Font("Georgia", Font.ITALIC, 50));
            g.setColor(Color.RED.darker());
            g.drawString(endMessage, 30, (int) HEIGHT / 2);
        }
    }

    private void runTheAnimation() {
        while (endMessage == null) {
            for (var i = 0; i < balls.length; i++) {
                balls[i].applyFriction();
                balls[i].moveToward(i == 0 ? goal.x : balls[0].x, i == 0 ? goal.y : balls[0].y);
            };
            Ball.adjustIfCollisions();
            Ball.endSimulationIfNecessary();
                repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
        }
    }

    public static void main(String[] args) {

        try {

            PLAYER_RADIUS = Double.parseDouble(args[0]);
            ENEMY_RADIUS = Double.parseDouble(args[1]);
            PLAYER_SPEED = Double.parseDouble(args[2]);
            ENEMY_SPEED = Double.parseDouble(args[3]);
            FRICTION = Double.parseDouble(args[4]);

            if (FRICTION < .0009) {
                throw new IllegalArgumentException("The parameter for friction needs to be a value of at least .0009");
            } else if (PLAYER_RADIUS > 25 || PLAYER_RADIUS < 5
                    || ENEMY_RADIUS > 50 || ENEMY_RADIUS < 5) {
                throw new IllegalArgumentException("Parameters for ball radii out of range.");
            } else if (PLAYER_SPEED > 100 || ENEMY_SPEED > 100) {
                throw new IllegalArgumentException("Parameter for ball speed cannot exceed 100.");
            }

        } catch (NumberFormatException e) {
            System.err.println("Sorry, parameters must be numbers! Try again.");
            return;
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return;
        }

        SwingUtilities.invokeLater(() -> {
            balls = new Ball[] {
                new Ball(0.0, HEIGHT, PLAYER_RADIUS, PLAYER_SPEED, Color.CYAN.brighter()),
                new Ball(WIDTH * 0.25, 40, ENEMY_RADIUS, ENEMY_SPEED, Color.MAGENTA.darker()),
                new Ball(WIDTH * 0.75, 40, ENEMY_RADIUS, ENEMY_SPEED, Color.MAGENTA.darker()),
                new Ball(WIDTH / 2, HEIGHT / 2, ENEMY_RADIUS, ENEMY_SPEED, Color.MAGENTA.darker())
            };

            var panel = new RobotSoccerSimulation();
            panel.setBackground(Color.CYAN.darker());
            var frame = new JFrame("Robotic Soccer");
            frame.setSize((int) WIDTH, (int) HEIGHT);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(panel, BorderLayout.CENTER);
            frame.setVisible(true);
            new Thread(() -> panel.runTheAnimation()).start();

      });
    }
}
