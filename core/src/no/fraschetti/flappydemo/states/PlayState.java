package no.fraschetti.flappydemo.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import no.fraschetti.flappydemo.FlappyDemo;
import no.fraschetti.flappydemo.sprites.Bird;
import no.fraschetti.flappydemo.sprites.Tube;

public class PlayState extends State {

    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COINT = 4;
    private static final int GROUND_Y_OFFSET = -50;

    private no.fraschetti.flappydemo.sprites.Bird bird;
    private Texture bg;
    private Texture ground;
    private Vector2 groundPos1, groundPos2;

    private Array<no.fraschetti.flappydemo.sprites.Tube> tubes;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        bird = new Bird(50, 300);
        cam.setToOrtho(false, no.fraschetti.flappydemo.FlappyDemo.WIDTH / 2, FlappyDemo.HEIGHT / 2);
        bg = new Texture("bg.png");
        ground = new Texture("ground.png");
        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth / 2, GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + ground.getWidth(), GROUND_Y_OFFSET);

        tubes = new Array<no.fraschetti.flappydemo.sprites.Tube>();

        for(int i = 1; i <= TUBE_COINT; i++) {
            tubes.add(new no.fraschetti.flappydemo.sprites.Tube(i * (TUBE_SPACING + no.fraschetti.flappydemo.sprites.Tube.TUBE_WIDTH)));
        }
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()) {
            bird.jump();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        updateGround();
        bird.update(dt);
        cam.position.x = bird.getPosition().x + 80;

        for(no.fraschetti.flappydemo.sprites.Tube tube : tubes) {
            if(cam.position.x - (cam.viewportWidth / 2) > tube.getPosTopTube().x + tube.getTopTube().getWidth()) {
                tube.reposition(tube.getPosTopTube().x + ((no.fraschetti.flappydemo.sprites.Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COINT));
            }
            if(tube.collides(bird.getBounds())) {
                gameOver();//gsm.set(new PlayState(gsm));
                break;
            }
        }
        if (bird.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET) {
            gameOver();//gsm.set(new PlayState(gsm));
        }

        cam.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, cam.position.x -(cam.viewportWidth / 2), 0);
        sb.draw(bird.getTexture(), bird.getPosition().x ,bird.getPosition().y);
        for(no.fraschetti.flappydemo.sprites.Tube tube : tubes) {
            sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }
        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);
        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        bird.dispose();
        for (Tube tube : tubes) {
            tube.dispose();
        }
        ground.dispose();
        System.out.println("PlayState disposed.");
    }

    private void updateGround() {
        if (cam.position.x - (cam.viewportWidth / 2) > groundPos1.x + ground.getWidth()) {
            groundPos1.add(ground.getWidth() * 2,0);
        }
        if (cam.position.x - (cam.viewportWidth / 2) > groundPos2.x + ground.getWidth()) {
            groundPos2.add(ground.getWidth() * 2,0);
        }
    }

    private void gameOver() {
        gsm.set(new MenuState(gsm, true));
    }
}
