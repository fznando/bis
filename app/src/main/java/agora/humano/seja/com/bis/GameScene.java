package agora.humano.seja.com.bis;


import android.util.Log;

import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static agora.humano.seja.com.bis.DeviceSettings.screenHeight;
import static agora.humano.seja.com.bis.DeviceSettings.screenResolution;
import static agora.humano.seja.com.bis.DeviceSettings.screenWidth;

public class GameScene extends CCLayer implements MeteorsEngineDelegate, ShootEngineDelegate, CoinsEngineDelegate, DiscoEngineDelegate, StarDestroyerEngineDelegate {
    private ScreenBackground background;

    private MeteorsEngine meteorsEngine;
    private CCLayer meteorsLayer;
    private List meteorsArray;

    private DiscoEngine discoEngine;
    private CCLayer discoLayer;
    private List discoArray;

    private StarDestroyerEngine starDestroyerEngine;
    private CCLayer starDestroyerLayer;
    private List starDestroyerArray;


    private CCLayer playerLayer;
    private Player player;

    private GameButtons gameButtonsLayer;

    private CCLayer shootsLayer;
    private ArrayList shootsArray;

    private List playersArray;

    private CCLayer scoreLayer;
    private Score score;

    private CoinsEngine coinsEngine;
    private CCLayer coinsLayer;
    private List coinsArray;


    private GameScene() {
        this.background = new ScreenBackground(Assets.BACKGROUND);
        this.background.setPosition(
                screenResolution(
                        CGPoint.ccp(screenWidth() / 2.0f, screenHeight() / 2.0f)));
        this.addChild(this.background);

        this.meteorsLayer = CCLayer.node();
        this.addChild(this.meteorsLayer);

        this.discoLayer = CCLayer.node();
        this.addChild(this.discoLayer);

        this.starDestroyerLayer = CCLayer.node();
        this.addChild(this.starDestroyerLayer);

        this.coinsLayer = CCLayer.node();
        this.addChild(this.coinsLayer);

        this.playerLayer = CCLayer.node();
        this.addChild(this.playerLayer);

        gameButtonsLayer = new GameButtons();
        gameButtonsLayer.setDelegate(this);
        this.addChild(this.gameButtonsLayer);

        this.shootsLayer = CCLayer.node();
        this.addChild(this.shootsLayer);

        this.scoreLayer = CCLayer.node();
        this.addChild(this.scoreLayer);

        this.setIsTouchEnabled(true);

        this.addGameObjects();
    }

    @Override
    public void onEnter() {
        super.onEnter();
        this.schedule("checkHits");
        this.schedule("checkPositionEnemys");
        this.startEngines();
    }

    private void startEngines() {
        this.addChild(this.meteorsEngine);
        this.meteorsEngine.setDelegate(this);

        this.addChild(this.coinsEngine);
        this.coinsEngine.setDelegate(this);

        this.addChild(this.starDestroyerEngine);
        this.starDestroyerEngine.setDelegate(this);

        this.addChild(this.discoEngine);
        this.discoEngine.setDelegate(this);
    }

    public static CCScene createGame() {
        CCScene scene = CCScene.node();
        GameScene layer = new GameScene();
        scene.addChild(layer);
        return scene;
    }

    @Override
    public void createMeteor(Meteor meteor) {
        meteor.setDelegate(this);
        this.meteorsLayer.addChild(meteor);
        meteor.start();
        this.meteorsArray.add(meteor);
    }

    @Override
    public void removeMeteor(Meteor meteor) {
        this.meteorsArray.remove(meteor);
    }

    @Override
    public void createCoin(Coin coin) {
        if (this.score.getScore() > 3) {
            coin.setDelegate(this);
            this.coinsLayer.addChild(coin);
            coin.start();
            this.coinsArray.add(coin);
        }
    }

    @Override
    public void removeCoin(Coin coin) {
        this.coinsArray.remove(coin);
    }

    @Override
    public void createDisco(Disco disco) {
        if (this.score.getScore() > 5) {
            disco.setDelegate(this);
            this.discoLayer.addChild(disco);
            disco.start();
            this.discoArray.add(disco);
        }
    }

    @Override
    public void removeDisco(Disco disco) {
        this.discoArray.remove(disco);
    }

    @Override
    public void createStarDestroyer(StarDestroyer starDestroyer) {
        if (this.starDestroyerArray.size() < 1 && this.score.getScore() >= 100) {
            starDestroyer.setDelegate(this);
            this.starDestroyerLayer.addChild(starDestroyer);
            starDestroyer.start();
            this.starDestroyerArray.add(starDestroyer);
        }
    }

    @Override
    public void removeStarDestroyer(StarDestroyer starDestroyer) {
        this.starDestroyerArray.remove(starDestroyer);
    }

    @Override
    public void removeShoot(Shoot shoot) {
        this.shootsArray.remove(shoot);
    }

    @Override
    public void createShoot(Shoot shoot) {
        this.shootsLayer.addChild(shoot);
        shoot.setDelegate(this);
        shoot.start();
        this.shootsArray.add(shoot);
    }

    private void addGameObjects() {
        this.meteorsArray = new ArrayList();
        this.meteorsEngine = new MeteorsEngine();

        this.discoArray = new ArrayList();
        this.discoEngine = new DiscoEngine();

        this.starDestroyerArray = new ArrayList();
        this.starDestroyerEngine = new StarDestroyerEngine();

        this.player = new Player();
        this.playerLayer.addChild(this.player);

        this.shootsArray = new ArrayList();
        this.player.setDelegate(this);

        this.playersArray = new ArrayList();
        this.playersArray.add(this.player);

        this.coinsArray = new ArrayList();
        this.coinsEngine = new CoinsEngine();

        // placar
        this.score = new Score();
        this.scoreLayer.addChild(this.score);
    }

    public boolean shoot() {
        player.shoot();
        return true;
    }

    public void moveLeft() {
        player.moveLeft();
    }

    public void moveRight() {
        player.moveRight();
    }

    public CGRect getBoarders(CCSprite object) {
        CGRect rect = object.getBoundingBox();
      //  Log.e("JOGO", "rect x: " + rect.origin.x);
       // Log.e("JOGO", "rect y: " + rect.origin.y);
      //  Log.e("JOGO", "rect width: " + rect.size.width);
     //   Log.e("JOGO", "rect height: " + rect.size.height);
        //CGPoint GLpoint = rect.origin;
        //CGRect GLrect = CGRect.make(GLpoint.x, GLpoint.y, rect.size.width, rect.size.height);
       // Log.e("JOGO", "GLrect x: " + GLrect.origin.x);
       // Log.e("JOGO", "GLrect y: " + GLrect.origin.y);
       // Log.e("JOGO", "GLrect width: " + GLrect.size.width);
       // Log.e("JOGO", "GLrect height: " + GLrect.size.height);
        return rect;
    }

    private boolean checkRadiusHitsOfArray(List<? extends CCSprite> array1,
                                           List<? extends CCSprite> array2,
                                           GameScene gameScene,
                                           String hit) {
        boolean result = false;
        for (int i = 0; i < array1.size(); i++) {
            // Pega objeto do primeiro array
            CGRect rect1 = getBoarders(array1.get(i));
            for (int j = 0; j < array2.size(); j++) {
                // Pega objeto do segundo array
                CGRect rect2 = getBoarders(array2.get(j));
                // Verifica colisão
                if (CGRect.intersects(rect1, rect2)) {
                    System.out.println("Colision Detected: " + hit);
                    result = true;

                    Method method;
                    try {
                        method = GameScene.class.getMethod(hit, CCSprite.class, CCSprite.class);
                        method.invoke(gameScene, array1.get(i), array2.get(j));
                    } catch (SecurityException e1) {
                        e1.printStackTrace();
                    } catch (NoSuchMethodException e1) {
                        e1.printStackTrace();
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }

    public void checkHits(float dt) {
        this.checkRadiusHitsOfArray(this.meteorsArray,
                this.shootsArray, this, "meteoroHit");
        this.checkRadiusHitsOfArray(this.discoArray,
                this.shootsArray, this, "discoHit");
        this.checkRadiusHitsOfArray(this.starDestroyerArray,
                this.shootsArray, this, "starDestroyerHit");
        this.checkRadiusHitsOfArray(this.meteorsArray,
                this.playersArray, this, "playerMeteorHit");
        this.checkRadiusHitsOfArray(this.discoArray,
                this.playersArray, this, "playerDiscoHit");
        this.checkRadiusHitsOfArray(this.starDestroyerArray,
                this.playersArray, this, "playerStarDestroyerHit");
        this.checkRadiusHitsOfArray(this.coinsArray,
                this.playersArray, this, "coinHit");
    }

    public void meteoroHit(CCSprite meteor, CCSprite shoot) {
        ((Meteor) meteor).shooted();
        ((Shoot) shoot).explode();
        this.score.increase(1);
    }

    public void discoHit(CCSprite disco, CCSprite shoot) {
        if (((Disco) disco).shooted()) {
            this.score.increase(3);
        }
        ((Shoot) shoot).explode();
    }

    public void starDestroyerHit(CCSprite starDestroyer, CCSprite shoot) {
        if (((StarDestroyer) starDestroyer).shooted()) {
            this.finalEnd();
        }
        ((Shoot) shoot).explode();
    }

    public void playerMeteorHit(CCSprite meteor, CCSprite player) {
        ((Meteor) meteor).shooted();
        ((Player) player).explode();

        this.gameOver();
    }

    public void playerDiscoHit(CCSprite disco, CCSprite player) {
        ((Disco) disco).explode();
        ((Player) player).explode();

        this.gameOver();
    }

    public void playerStarDestroyerHit(CCSprite starDestroyer, CCSprite player) {
        ((StarDestroyer) starDestroyer).explode();
        ((Player) player).explode();

        this.gameOver();
    }

    public void coinHit(CCSprite coin, CCSprite player) {
        ((Coin) coin).captured();
        this.score.increase(10);
    }

    public void checkPositionEnemys(float dt) {
        this.checkPositionMeteor(this.meteorsArray);
        this.checkPositionDisco(this.discoArray);

        if (this.score.getScore() < 0) {
            this.gameOver();
        }
    }

    public void checkPositionMeteor(List<? extends CCSprite> meteors) {
        for (CCSprite meteor: meteors) {
            if (((Meteor) meteor).getPositionY() == -10) {
                this.score.decrease(1);
            }
        }
    }

    public void checkPositionDisco(List<? extends CCSprite> discos) {
        for (CCSprite disco: discos) {
            if (((Disco) disco).getPositionY() == -10) {
                this.score.decrease(3);
            }
        }
    }

    public void gameOver() {
        CCDirector.sharedDirector().replaceScene(
                CCFadeTransition.transition(1.0f, new GameOverScreen().scene()));
    }

    public void finalEnd() {
        CCDirector.sharedDirector().replaceScene(
                CCFadeTransition.transition(1.0f, new WinScreen().scene()));
    }
}
