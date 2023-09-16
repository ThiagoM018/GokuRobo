package Goku_Robo;
import robocode.*;
import java.awt.Color;

public class Goku extends Robot {
    private double enemyX;
    private double enemyY;

    public void run() {
        setColors(Color.yellow, Color.yellow, Color.yellow);
        goToTopLeftCorner();

        while (true) {
            turnGunLeft(360); // Gire o canhão
        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        double absoluteBearing = getHeading() + e.getBearing();
        enemyX = getX() + e.getDistance() * Math.sin(Math.toRadians(absoluteBearing));
        enemyY = getY() + e.getDistance() * Math.cos(Math.toRadians(absoluteBearing));

        // Calcule  para mirar 
        double angleToEnemy = Math.toDegrees(Math.atan2(enemyX - getX(), enemyY - getY()));
        double gunTurn = normalizeBearing(angleToEnemy - getGunHeading());

        // Atualize a direção do canhão
        turnGunRight(gunTurn);

        // Mirar e atirar
        turnGunRight(getHeading() - getGunHeading() + e.getBearing());
        fire(4); // poder de fogo 
    }

    public void onHitByBullet(HitByBulletEvent e) {
        back(20);
    }

    public void onHitWall(HitWallEvent e) {
        // para evitar ficar preso na parede
        turnRight(90);
        ahead(100);
    }

    // Mover o robô para o canto esquerdo
    private void goToTopLeftCorner() {
        // direção ao canto superior esquerdo
        turnLeft(getHeading() % 90);
        ahead(getY() - 50); 
        turnLeft(90);
        ahead(getX() - 50); 
    }

    // Função para normalizar o ângulo
    private double normalizeBearing(double angle) {
        while (angle > 180) {
            angle -= 360;
        }
        while (angle < -180) {
            angle += 360;
        }
        return angle;
    }
}

