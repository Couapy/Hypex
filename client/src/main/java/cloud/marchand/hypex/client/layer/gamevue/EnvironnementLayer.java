package cloud.marchand.hypex.client.layer.gamevue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import cloud.marchand.hypex.client.Pov;
import cloud.marchand.hypex.client.interfaces.CanvasLayer;
import cloud.marchand.hypex.client.interfaces.Vue;
import cloud.marchand.hypex.core.models.Map;

/**
 * Draw Sky and ground textures
 */
public class EnvironnementLayer extends CanvasLayer {

    /**
     * Sky color.
     */
    private static final Color SKY_COLOR = Color.BLUE;

    /**
     * Ground color.
     */
    private static final Color GROUND_COLOR = Color.BLACK;

    /**
     * Current vue.
     */
    private Vue vue;

    /**
     * EnvironnementLayer from a vue.
     */
    public EnvironnementLayer(Vue vue) {
        this.vue = vue;
    }

    /**
     * Paint environnement (sky + ground).
     */
    @Override
    public void draw(Graphics graphics) {
        int width = vue.getWidth();
        int height = vue.getHeight();

        // Draw sky
        graphics.setColor(SKY_COLOR);
        graphics.fillRect(0, 0, width, height / 2);

        // Draw ground
        graphics.setColor(GROUND_COLOR);
        graphics.fillRect(0, height / 2, width, height);
    }
    
}
