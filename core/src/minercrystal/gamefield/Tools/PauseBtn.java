package minercrystal.gamefield.Tools;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;


public class PauseBtn {
    private boolean isPause = false, isWinDeploy = false, isWinReverse = false, isWinDeployed = false, isDeployed = false, isReverse = false, isDeploy = false, isActive = false, isGoToMenu = false;
    private float accumulator = 0;
    private final static float actDelta = 0.02f;
    private Array<Texture> btnPauseTexture;
    private Texture pauseTexture1, pauseTexture2;
    private int INDEX = 15;
    private Rectangle btnRect;
    public PauseBtn() {
        btnPauseTexture = new Array<>();
        for(int i = 1; i <=16; i++)
            btnPauseTexture.add(new Texture("Buttons/pauseBtn/" + i + ".png"));
        pauseTexture2 = new Texture("Buttons/pauseBtn/pa.png");
        pauseTexture1 = new Texture("Buttons/pauseBtn/pl.png");

    }
    public void render(SpriteBatch spriteBatch, float delta){
        if(INDEX !=15)
        spriteBatch.draw(btnPauseTexture.get(INDEX), btnRect.x, btnRect.y, btnRect.width, btnRect.height);
      if(isDeploy){
       accumulator += delta;
       if(accumulator > actDelta){
        accumulator = 0;
        if(!isReverse) {
            INDEX--;
            if (INDEX == 0) {
                isDeploy = false;
                isDeployed = true;
            }
        }else {
            INDEX++;
            if (INDEX == 15) {
                isDeployed = true;
                isDeploy = false;
            }
        }
       }
      }else if(INDEX == 0 && !isWinDeploy && !isWinDeployed){
          if(isPause){
              spriteBatch.draw(pauseTexture1, btnRect.x, btnRect.y, btnRect.width, btnRect.height);
          }else{
              spriteBatch.draw(pauseTexture2, btnRect.x, btnRect.y, btnRect.width, btnRect.height);
          }
      }
      if(isWinDeploy){
          accumulator += delta;
          if(accumulator > actDelta){

              accumulator = 0;
              if(!isWinReverse) {
                  INDEX--;
                  if (INDEX <= 0) {
                      isWinDeployed = true;
                      isWinDeploy = false;
                      INDEX = 0;
                  }
              }else{
                  INDEX++;
                  if (INDEX >= 15) {
                      isGoToMenu = true;
                      isWinDeploy = false;
                      INDEX = 15;
                  }
              }
          }
      }
    }
    public void setReverse(){
       isReverse = true;
       isDeployed = false;
       isDeploy = true;
    }

    public boolean isPause() {
        return isPause;
    }

    public boolean isDeploy() {
        return isDeployed;
    }

    public boolean isReverse() {
        return isReverse;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isGoToMenu() {
        return isGoToMenu;
    }

    public boolean touchDown(float x, float y){
        if(!isWinDeployed && !isWinDeploy) {
            if (btnRect.contains(x, y)) {
                if (!isPause)
                    isActive = true;
                return true;
            } else {
                return false;
            }
        }
        if(isWinDeployed){
            if (btnRect.contains(x, y)) {
                isWinReverse = true;
                isWinDeploy = true;

                return true;
            } else {
                return false;
            }
        }
        return false;
    }
    public void winDeploy(Rectangle btnRect){
        this.btnRect = btnRect;
        isWinDeploy = true;
        accumulator = 0;
        isDeploy = false;
    }
    public void setPToNull(){
        INDEX = 15;
        isReverse = false;
    }
    public void deploy(boolean isPause, Rectangle btnRect){
        this.isPause = isPause;
        this.btnRect = btnRect;
        isDeploy = true;
        isDeployed = false;
        isReverse = false;
        if(!isPause)
            isActive = false;
    }
}
