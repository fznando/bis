package agora.humano.seja.com.bis;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCScaleBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.actions.interval.CCSpawn;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;

import java.util.Random;

import static agora.humano.seja.com.bis.DeviceSettings.screenHeight;
import static agora.humano.seja.com.bis.DeviceSettings.screenResolution;
import static agora.humano.seja.com.bis.DeviceSettings.screenWidth;

/**
 * Created by clint on 24/06/17.
 */

public class StarDestroyer extends CCSprite {
    private StarDestroyerEngineDelegate delegate;
    private float x, y;
    private int shooted;

    public void setDelegate(StarDestroyerEngineDelegate delegate) {
        this.delegate = delegate;
    }

    public StarDestroyer(String image) {
        super(image);
        x = screenWidth() / 2;
        y = screenHeight() + 80;
    }

    public void start() {
        this.schedule("update");
    }

    public void update(float dt) {
        y -= 0.1;
        this.setPosition(screenResolution(CGPoint.ccp(x, y)));
    }

    public boolean shooted() {
        shooted++;
        if (shooted == 100) {
            this.explode();

            return true;
        }
        return false;
    }

    public void explode() {
        this.delegate.removeStarDestroyer(this);
        this.unschedule("update");
        float dt = 0.2f;
        CCScaleBy a1 = CCScaleBy.action(dt, 0.5f);
        CCFadeOut a2 = CCFadeOut.action(dt);
        CCSpawn s1 = CCSpawn.actions(a1, a2);
        CCCallFunc c1 = CCCallFunc.action(this, "removeMe");
        this.runAction(CCSequence.actions(s1, c1));

        SoundEngine.sharedEngine().playEffect(
                CCDirector.sharedDirector().getActivity(), R.raw.bang);
    }

    public void removeMe() {
        this.removeFromParentAndCleanup(true);
    }

    public float getPositionY() {
        return y;
    }
}
