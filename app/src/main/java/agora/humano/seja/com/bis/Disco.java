package agora.humano.seja.com.bis;

import android.util.Log;

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

public class Disco extends CCSprite {
    private DiscoEngineDelegate delegate;
    private float x, y;
    private int shooted;

    public void setDelegate(DiscoEngineDelegate delegate) {
        this.delegate = delegate;
    }

    public Disco(String image) {
        super(image);
        x = new Random().nextInt(Math.round(screenWidth()));
        y = screenHeight();
    }

    public void start() {
        this.schedule("update");
    }

    public void update(float dt) {
        y -= 0.5;
        this.setPosition(screenResolution(CGPoint.ccp(x, y)));

        if (y < -11) {
            this.delegate.removeDisco(this);
            removeMe();
        }

    }

    public boolean shooted() {
        shooted++;
        if (shooted == 3) {
            this.explode();

            return true;
        }
        return false;
    }

    public void explode() {
        this.delegate.removeDisco(this);
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
