package datatypes;

import main.Loader;
import rendering.Camera;
import rendering.Image;
import rendering.Renderer;
import rendering.Shader;

public class Tilemap{
	
	private Tileset[] tilesets;
	public TiledJSON json;
	private Renderer[] layers;
	
	public Tilemap(String mapPath, String tilesetsDir){
		json = new TiledJSON(mapPath);
		tilesets = new Tileset[json.tilesets.length];
		layers = new Renderer[json.map.length];
		for(int s = 0; s < json.tilesets.length; s++) tilesets[s] = new Tileset(Loader.get().loadImage(tilesetsDir + "/" + json.tilesets[s] + ".png"), json.tileWidth, json.tileHeight);
		load();
	}
	
	public void load(){
		int scaleX = Loader.scale, scaleY = Loader.scale;
		for(int l = 0; l < json.map.length; l++){
			if(layers[l] != null) layers[l].reset();
			if(layers[l] == null) layers[l] = new Renderer();
			for(int x = 0; x < json.map[l].length; x++){
				for(int y = 0; y < json.map[l][x].length; y++){
					int s = -1, index = 0;
					for(int i = 0; i < json.offsets.length; i++) if(json.map[l][x][y] >= json.offsets[i]){
						s = i;
						index = json.map[l][x][y] - json.offsets[i];
					}
					if(s > -1){
						float[] positions = setPositions(x * json.tileWidth * scaleX, y * json.tileHeight * scaleY, json.tileWidth, json.tileHeight, scaleX, scaleY);
						Image image = new Image(tilesets[s].image.tex);
						image.setRawPositions(positions);
						image.setUVMap(tilesets[s].uvMaps[index]);
						layers[l].add(image);
					}
				}
			}
		}
	}
	
	public void draw(int layer, int scaleX, int scaleY, Shader shader, Camera camera){
		int l = layer;
		if(layers[l] != null){ layers[l].draw(shader, camera); }
	}
	
	public float[] setPositions(double x, double y, double width, double height, double xScale, double yScale){
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
}
