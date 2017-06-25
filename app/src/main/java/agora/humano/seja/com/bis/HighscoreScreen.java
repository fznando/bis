package agora.humano.seja.com.bis;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.opengl.CCBitmapFontAtlas;
import org.cocos2d.types.CGPoint;

import static agora.humano.seja.com.bis.DeviceSettings.screenHeight;
import static agora.humano.seja.com.bis.DeviceSettings.screenResolution;
import static agora.humano.seja.com.bis.DeviceSettings.screenWidth;

/**
 * Created by clint on 23/06/17.
 */

public class HighscoreScreen extends CCLayer {

    private ScreenBackground background;
    private CCBitmapFontAtlas text;
    private Score score;

    public HighscoreScreen() {
        this.background = new ScreenBackground(Assets.BACKGROUND);
        this.background.setPosition(
                screenResolution(
                        CGPoint.ccp(
                                screenWidth() / 2.0f,
                                screenHeight() / 2.0f
                        ))
        );
        this.addChild(this.background);


        score = new Score();
        this.text = CCBitmapFontAtlas.bitmapFontAtlas(
                String.valueOf(score.getHighscore()),
                "UniSansSemiBold_Numbers_240.fnt");
        this.text.setScale((float) 120 / 240);
        this.setPosition(screenWidth() - 50, screenHeight() - 20);
        this.addChild(this.text);

        /*MenuButtons menuLayer = new MenuButtons();
        this.addChild(menuLayer);*/

    }

    public CCScene scene() {
        CCScene scene = CCScene.node();
        scene.addChild(this);
        return scene;
    }
}
