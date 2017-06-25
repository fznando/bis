package agora.humano.seja.com.bis;

import org.cocos2d.layers.CCLayer;

import java.util.Random;

/**
 * Created by clint on 24/06/17.
 */

public class StarDestroyerEngine extends CCLayer {
    public StarDestroyerEngineDelegate delegate;

    public StarDestroyerEngine() {
        this.schedule("starDestroyerEngine", 1.0f / 10f);
    }

    public void starDestroyerEngine(float dt) {
        if (new Random().nextInt(1) == 0) {
            this.getDelegate().createStarDestroyer(new StarDestroyer(Assets.STARDESTROYER));
        }
    }

    public StarDestroyerEngineDelegate getDelegate() {
        return delegate;
    }

    public void setDelegate(StarDestroyerEngineDelegate delegate) {
        this.delegate = delegate;
    }
}
