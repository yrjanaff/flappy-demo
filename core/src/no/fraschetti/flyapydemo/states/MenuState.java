package no.fraschetti.flyapydemo.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import no.fraschetti.flyapydemo.FlappyDemo;

public class MenuState extends State{

    private Texture background;
    private Texture playBtn;
    private boolean gameOver = false;

    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter titleParameter;
    private FreeTypeFontGenerator.FreeTypeFontParameter gameOverParameter;
    private BitmapFont titleFont;
    private BitmapFont gameOverFont;

    private static int TITLE_FONT_SZE = 140;
    private static int GAME_OVERT_FONT_SIZE = 92;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        setUp();
    }

    public MenuState(GameStateManager gsm, boolean gameOver) {
        super(gsm);
        setUp();
        this.gameOver = gameOver;
    }

    private void setUp() {
        setUpFonts();
        cam.setToOrtho(false, FlappyDemo.WIDTH, FlappyDemo.HEIGHT);
        background = new Texture("bg.png");
        playBtn = new Texture("playbtn.png");
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()) {
            gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, cam.position.x - cam.viewportWidth, 0);
        sb.draw(background, 0,0, FlappyDemo.WIDTH, FlappyDemo.HEIGHT);
        sb.draw(playBtn, (FlappyDemo.WIDTH / 2) - (playBtn.getWidth() / 2), FlappyDemo.HEIGHT / 2);

        titleFont.draw(sb, "Flappy Demo", (FlappyDemo.WIDTH / 2) - (GAME_OVERT_FONT_SIZE + 125), FlappyDemo.HEIGHT - 50);
        if (gameOver) {
            gameOverFont.draw(sb, "Game Over", (FlappyDemo.WIDTH / 2) - (GAME_OVERT_FONT_SIZE + 35), FlappyDemo.HEIGHT - 275);
        }

        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
        gameOverFont.dispose();
        titleFont.dispose();
        generator.dispose();
        System.out.println("MenyState disposed");
    }

    private void setUpFonts() {
        generator = new FreeTypeFontGenerator(Gdx.files.internal("font/FlappyBirdy.ttf"));

        titleParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        titleParameter.size = TITLE_FONT_SZE;
        titleFont = generator.generateFont(titleParameter);

        gameOverParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        gameOverParameter.size = GAME_OVERT_FONT_SIZE;
        gameOverFont = generator.generateFont(gameOverParameter);
    }

}
