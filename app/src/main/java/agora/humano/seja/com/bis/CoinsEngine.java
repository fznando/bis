package agora.humano.seja.com.bis;

import org.cocos2d.layers.CCLayer;

import java.util.Random;

/**
 * Created by clint on 24/06/17.
 */

public class CoinsEngine extends CCLayer {
    public CoinsEngineDelegate delegate;

    public CoinsEngine() {
        this.schedule("coinsEngine", 1.0f / 10f);
    }

    public void coinsEngine(float dt) {
        if (new Random().nextInt(50) == 0) {
            this.getDelegate().createCoin(new Coin(Assets.COIN));
        }
    }

    public CoinsEngineDelegate getDelegate() {
        return delegate;
    }

    public void setDelegate(CoinsEngineDelegate delegate) {
        this.delegate = delegate;
    }
}
