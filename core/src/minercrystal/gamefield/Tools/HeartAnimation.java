package minercrystal.gamefield.Tools;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;


public class HeartAnimation {
    private Texture heart_texture;
    private Rectangle heart_rect;
    private final static float beatingTimer = 0.1f, beatingZoom = 4;
    private float timer = 0, zoom;
    private boolean isBeatingAnimation = false;
    public HeartAnimation(Rectangle heart_rect) {
        this.heart_rect = heart_rect;
        heart_texture = new Texture("5heart.png");
    }

    public void render(SpriteBatch spriteBatch, float delta) {
        spriteBatch.draw(heart_texture, heart_rect.x-zoom/2, heart_rect.y-zoom/2, heart_rect.width+zoom, heart_rect.height+zoom);
        if(isBeatingAnimation){
            timer+=delta;
            if(timer > beatingTimer){
                zoom = 0;
                isBeatingAnimation = false;
                timer = 0;
            }
        }
    }

    public void dispose() {
        heart_texture.dispose();
    }

    public void changePosition(float X, float Y) {
        heart_rect.y= Y;
    }

    public Rectangle getPosition() {
        return new Rectangle(heart_rect.x, heart_rect.y, heart_rect.width, heart_rect.height);
    }
    public void startBeatingAnimation(){
        isBeatingAnimation = true;
        zoom = beatingZoom;
    }
}
