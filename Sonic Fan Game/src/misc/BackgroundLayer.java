package misc;

import java.nio.ByteBuffer;

import datatypes.Tileset;
import main.Loader;
import main.Window;
import rendering.Camera;
import rendering.Image;
import rendering.Renderer;

public class BackgroundLayer{
	
	private final int NONE = 0;
	private final int COLOR = 1;
	private final int TILE = 2;
	private int index;
	private int size;
	private int tileSize;
	private int scale;
	private float[] colorTween0;
	private float[] colorTween1;
	private int[] tileTween0;
	private int[] tileTween1;
	private int tweenType0;
	private int tweenType1;
	private Tileset tileset;
	private Image image;
	
	public BackgroundLayer(ByteBuffer image, int index, int size, int scale, int tileSize){
		this.index = index;
		this.tileSize = tileSize;
		this.size = size;
		this.scale = scale;
		tweenType0 = NONE;
		tweenType1 = NONE;
		tileset = new Tileset(image, tileSize, tileSize);
		this.image = new Image(image);
	}
	
	public void setTween(int tween, float[] color){
		if(tween == 0){
			tweenType0 = COLOR;
			colorTween0 = color;
		}
		if(tween == 1){
			tweenType1 = COLOR;
			colorTween1 = color;
		}
	}
	
	public void setTween(int tween, int x, int y){
		if(tween == 0){
			tweenType0 = TILE;
			tileTween0 = new int[]{x, y};
		}
		if(tween == 1){
			tweenType1 = TILE;
			tileTween1 = new int[]{x, y};
		}
	}
	
	public void draw(int yStart2, int scrollSpeed, Camera camera, Renderer r){
		int width = tileset.image.getWidth();
		int yStart = yStart2;
		int screenWidth = Window.getWidth() / (width * scale / 2 * Loader.scale) + 1;
		int screenHeight = (Window.getInitHeight() * 2 - Window.getHeight());
		float xCam = camera.position.x;
		float yCam = camera.position.y + (Window.getInitHeight() - Window.getHeight());
		double xOffset = (xCam / scrollSpeed) % (width * scale / 2 * Loader.scale);
		for(int i = -1; i < screenWidth + 1; i++){
			image.setPositions(xCam - xOffset + i * (width * scale / 2 * Loader.scale), yCam + yStart * scale / 2 * Loader.scale, scale / 2 * Loader.scale, scale / 2 * Loader.scale);
			r.add(image);
		}
	}
	
	private float[] setPositions(double x, double y, double width, double height, double xScale, double yScale){
		float[] vertexArray = new float[12];
		vertexArray[0] = (float)x + (float)(width * xScale);
		vertexArray[1] = (float)y;
		vertexArray[2] = 0f;
		vertexArray[3] = (float)x;
		vertexArray[4] = (float)y + (float)(height * -yScale);
		vertexArray[5] = 0f;
		vertexArray[6] = (float)x + (float)(width * xScale);
		vertexArray[7] = (float)y + (float)(height * -yScale);
		vertexArray[8] = 0f;
		vertexArray[9] = (float)x;
		vertexArray[10] = (float)y;
		vertexArray[11] = 0f;
		vertexArray[1] += (float)(height * yScale);
		vertexArray[4] += (float)(height * yScale);
		vertexArray[7] += (float)(height * yScale);
		vertexArray[10] += (float)(height * yScale);
		if(xScale < 0){
			vertexArray[0] -= (float)(width * xScale);
			vertexArray[3] -= (float)(width * xScale);
			vertexArray[6] -= (float)(width * xScale);
			vertexArray[9] -= (float)(width * xScale);
		}
		if(yScale < 0){
			vertexArray[1] -= (float)(height * yScale);
			vertexArray[4] -= (float)(height * yScale);
			vertexArray[7] -= (float)(height * yScale);
			vertexArray[10] -= (float)(height * yScale);
		}
		return vertexArray;
	}
	
	private float[] getColors(float[] color){
		return new float[]{color[0], color[1], color[2], color[3], color[0], color[1], color[2], color[3], color[0], color[1], color[2], color[3], color[0], color[1], color[2], color[3]};
	}
	
	private float[] getUVs(float[] uv){ return new float[]{uv[0], uv[1], uv[0], uv[1], uv[0], uv[1], uv[0], uv[1]}; }
	
	private float[] getRegularUVs(){ return new float[]{1f, 1f, 0f, 0f, 1f, 0f, 0f, 1f}; }
	
}
