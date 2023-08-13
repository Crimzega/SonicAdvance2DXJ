package datatypes;

import java.nio.ByteBuffer;

import rendering.Image;

public class Tileset{
	
	public int tileWidth;
	public int tileHeight;
	public Image image;
	public float[][] uvMaps;
	public float[][][] uvMaps2;
	
	public Tileset(ByteBuffer imageBuffer, int tileWidth, int tileHeight){
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		image = new Image(imageBuffer);
		int tilesetWidth = image.getWidth() / tileWidth;
		int tilesetHeight = image.getHeight() / tileHeight;
		uvMaps = new float[tilesetWidth * tilesetHeight][8];
		uvMaps2 = new float[tilesetWidth][tilesetHeight][8];
		for(int x = 0; x < tilesetWidth; x++) for(int y = 0; y < tilesetHeight; y++){
			int t = x + (y * tilesetWidth);
			float fx = (float)((1f * x * tileWidth) / (1.0f * image.getWidth())), fy = (float)((1f * y * tileHeight) / (1.0f * image.getHeight())),
				fw = (float)((1f * tileWidth) / (1.0f * image.getWidth())), fh = (float)((1f * tileHeight) / (1.0f * image.getHeight()));
			float[] map = new float[]{fx + fw, fy + fh, fx, fy, fx + fw, fy, fx, fy + fh};
			uvMaps[t] = map;
			uvMaps2[x][y] = map;
		}
	}
	
}
