package minercrystal.gamefield.Buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class CloseBtn {
    private boolean deploying = false, isDeployed = false, isReverse = false;
    private int INDEX = 0;
    private Array<Texture> buttonTexture;
    private Rectangle buttonRect;
    private float accumulator = 0;
    private static final float speed = 0.025f;

    public CloseBtn(Rectangle buttonRect) {
        this.buttonRect = buttonRect;
        buttonTexture = new Array<>();
        for(int i = 12; i >= 1; i--){
            buttonTexture.add(new Texture("Buttons/closeBtn/" + i+".png"));
        }

    }
    public void render(SpriteBatch batch, float delta){
      if(isDeployed || deploying){
          if(deploying){
              accumulator+=delta;
              if(!isReverse) {
                  if (accumulator > speed) {
                      accumulator = 0;
                      INDEX++;
                      if (INDEX >= 11) {
                          INDEX = 11;
                          isDeployed = true;
                          deploying = false;
                      }
                  }
              }else{
                  if (accumulator > speed) {
                      accumulator = 0;
                      INDEX--;
                      if (INDEX == 0) {
                          isDeployed = false;
                          deploying = false;
                          isReverse = false;
                      }
                  }
              }
          }
          batch.draw(buttonTexture.get(INDEX), buttonRect.getX(), buttonRect.getY(), buttonRect.getWidth(), buttonRect.getHeight());

      }
    }
    public boolean touchDown(float X, float Y){
        if(buttonRect.contains(X, Y)){
            isDeployed = false;
            deploying = true;
            isReverse = true;
            return true;
        }
        return false;
    }
    public void setDistruct(){
        isDeployed = false;
        deploying = true;
        isReverse = true;
    }
    public void setDeploying(boolean deploying) {
        this.deploying = deploying;
    }

    public void dispose(){
        for(int i = 12; i > 1; i--){
            buttonTexture.get(i).dispose();
        }
    }
}
