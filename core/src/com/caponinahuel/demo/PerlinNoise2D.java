package com.caponinahuel.demo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import space.earlygrey.shapedrawer.ShapeDrawer;
import com.caponinahuel.demo.framework.core.math.FastMath;
import com.caponinahuel.demo.framework.core.math.random.Random;
import com.caponinahuel.demo.framework.graphics.Colors;
import com.caponinahuel.demo.framework.graphics.ShapeDrawerFactory;

public class PerlinNoise2D extends ApplicationAdapter {
	protected Color backgroundColor = new Color(Colors.CyanDark);

	protected Batch batch;
	protected ShapeDrawer drawer;

	protected float speed = 1f;
	protected float timeOff = 0;

	protected int height;
	protected int width;


	float noiseGranularity = 0.05f;
	int squareSize = 5;
	float timeOffX = 0;
	float timeOffY = 0;

	@Override
	public void create () {
		batch = new SpriteBatch();
		drawer = ShapeDrawerFactory.getNew(batch);
		height = Gdx.graphics.getHeight(); //Obtain graphics data
		width = Gdx.graphics.getWidth();
	}

	@Override
	public void render () {
		//Clean screen
		ScreenUtils.clear(backgroundColor);

		batch.begin();
		drawer.setDefaultLineWidth(2);
		draw();
		batch.end();
	}
	protected int getLineHeight(float x) {
		int y = height/2;

		int n = FastMath.map(Random.noise(x), 0, 1, -height/2+100, height/2 - 100);
		y+= n;


		return y;
	}
	protected void draw() {
		float noiseOffX = timeOffX;
		for (int x = 0; x <= width; x += squareSize) {
			float noiseOffY = timeOffY;
			for (int y = 0; y < height; y += squareSize) {
				float color = Random.noise(noiseOffX, noiseOffY);


				drawer.setColor(color, color, color, 1);
				drawer.filledRectangle(x, y, squareSize, squareSize);
				noiseOffY += noiseGranularity;
			}
			noiseOffX += noiseGranularity;
		}
		timeOffX += noiseGranularity;
		timeOffY += noiseGranularity * (Random.noise(timeOffX)-0.5)*5 ;
	}
	@Override
	public void dispose () {
		batch.dispose();
	}
}
