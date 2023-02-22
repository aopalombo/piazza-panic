package com.team13.piazzapanic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.team13.piazzapanic.Screens.MainMenuScreen;
import com.badlogic.gdx.Game;

public class MainGame extends Game {

	/**
	 * MainGame class is the central class of the game that creates and manages the two screens, PlayScreen and StartScreen.
	 *
	 * Class Members:
	 *     V_WIDTH (int): Width of the view.
	 *     V_HEIGHT (int): Height of the view.
	 *     TILE_SIZE (int): Size of the tile.
	 *     PPM (float): Pixels per meter.
	 *     batch (SpriteBatch): Instance of SpriteBatch.
	 *     isPlayScreen (bool): Flag indicating whether the PlayScreen is displayed or not.
	 *     playScreen (PlayScreen): Instance of PlayScreen.
	 *     startScreen (StartScreen): Instance of StartScreen.
	 *
	 * Methods:
	 *     __init__: Initializes the MainGame class.
	 *     create: Creates the instances of StartScreen and PlayScreen and initializes the SpriteBatch instance.
	 *     render: Renders the StartScreen or PlayScreen based on the value of isPlayScreen flag.
	 * 	   dispose: Releases resources used by the MainGame class.
	 */
	public static final int V_WIDTH = 160;
	public static final int V_HEIGHT = 160;
	public static final int TILE_SIZE = 16;

	public static final float PPM = 100;
	public SpriteBatch batch;
	private MainMenuScreen MainMenu;

	public MainGame(){
	}
	@Override
	public void create() {
		batch = new SpriteBatch();
		MainMenu = new MainMenuScreen(this);
		setScreen(MainMenu);
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		super.dispose();
		batch.dispose();
	}
}