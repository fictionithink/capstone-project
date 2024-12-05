package entities;

import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LaserBeam {
    private float startX, startY; // Starting position of the laser
    private float endX, endY;     // End position of the laser
    private float length;         // Dynamic length of the laser
    private BufferedImage laserSprite;
    private int alpha;
    private long creationTime;

    public LaserBeam(float startX, float startY, float angle) {
        this.startX = startX;
        this.startY = startY;
        this.alpha = 255;

        calculateDynamicLength(angle);

        loadLaserSprite();

        this.creationTime = System.currentTimeMillis();
    }

    private void loadLaserSprite() {
        laserSprite = LoadSave.getSpriteAtlas(LoadSave.LASER_BEAM_SPRITE); // Retrieves the laser sprite
    }

    private void calculateDynamicLength(float angle) {
        float maxDistance = (float) Math.hypot(Game.GAME_WIDTH, Game.GAME_HEIGHT);

        for (float t = 0; t <= maxDistance; t += 1.0f) {
            float currentX = startX + (float) Math.cos(angle) * t;
            float currentY = startY + (float) Math.sin(angle) * t;

            // If the laser exceeds the panel's boundaries, stop
            if (currentX < 0 || currentX > Game.GAME_WIDTH || currentY < 0 || currentY > Game.GAME_HEIGHT) {
                this.endX = currentX;
                this.endY = currentY;
                this.length = (float) Math.hypot(currentX - startX, currentY - startY);
                break;
            }
        }
    }

    public void update() {
        long elapsedTime = System.currentTimeMillis() - creationTime;
        if (elapsedTime >= 50) { // After 0.5 seconds, start fading
            alpha = Math.max(0, alpha - 5); // Decrease alpha, but ensure it doesn't go below 0
        }
    }

    public boolean isFaded() {
        return alpha <= 0; // Check if the laser is fully invisible
    }

    public void draw(Graphics g) {
        // Calculate the angle of rotation in degrees
        double rotationAngle = Math.toDegrees(Math.atan2(endY - startY, endX - startX));
        
        Graphics2D g2d = (Graphics2D) g;
        var originalTransform = g2d.getTransform();
        g2d.translate(startX, startY);
        g2d.rotate(Math.toRadians(rotationAngle));

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha / 255f));

        g2d.drawImage(
                laserSprite, 0,
                -(int) (laserSprite.getHeight() / 2 * Game.SCALE),
                (int) length,
                (int) (laserSprite.getHeight() * Game.SCALE),
                null
        );

        g2d.setTransform(originalTransform);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}
