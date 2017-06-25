package agora.humano.seja.com.bis;

import org.cocos2d.layers.CCLayer;

import java.util.Random;

/**
 * Created by clint on 24/06/17.
 */

public class DiscoEngine extends CCLayer {
    public DiscoEngineDelegate delegate;

    public DiscoEngine() {
        this.schedule("discoEngine", 1.0f / 10f);
    }

    public void discoEngine(float dt) {
        if (new Random().nextInt(50) == 0) {
            this.getDelegate().createDisco(new Disco(Assets.DISCO));
        }
    }

    public DiscoEngineDelegate getDelegate() {
        return delegate;
    }

    public void setDelegate(DiscoEngineDelegate delegate) {
        this.delegate = delegate;
    }
}
