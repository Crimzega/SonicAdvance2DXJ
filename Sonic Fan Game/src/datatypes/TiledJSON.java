package datatypes;

import java.io.InputStreamReader;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class TiledJSON{
	
	public int width;
	public int height;
	public int length;
	public int tileWidth;
	public int tileHeight;
	public int[][][] map;
	public int[] offsets;
	public String[] tilesets;
	public String[] layers;
	
	public TiledJSON(String jsonPath){
		Gson gson = new GsonBuilder().registerTypeAdapter(TileData.class, new DataAdapter()).create();
		TileData data = gson.fromJson(new InputStreamReader(getClass().getResourceAsStream(jsonPath)), TileData.class);
		tileWidth = data.tileWidth;
		tileHeight = data.tileHeight;
		tilesets = data.tilesets;
		offsets = data.tilesetsOffsets;
		length = data.mapLength;
		width = data.mapWidth;
		height = data.mapHeight;
		layers = data.mapLayers;
		map = data.mapData;
	}
	
	private static class TileData{
		
		private int[][][] mapData;
		private int[] tilesetsOffsets;
		private String[] tilesets, mapLayers;
		private int mapLength, mapHeight, mapWidth, tileHeight, tileWidth;
		
		public TileData(int width, int height){
			tileWidth = width;
			tileHeight = height;
		}
		
		public TileData setTilesets(String[] sets, int[] offsets){
			tilesets = sets;
			tilesetsOffsets = offsets;
			return this;
		}
		
		public TileData setMapInfo(String[] layers, int[][][] map, int length, int width, int height){
			mapLayers = layers;
			mapData = map;
			mapLength = length;
			mapWidth = width;
			mapHeight = height;
			return this;
		}
		
	}
	
	private static class DataAdapter implements JsonDeserializer<TileData>{
		
		public TileData deserialize(JsonElement jsonElem, Type type, JsonDeserializationContext ctx) throws JsonParseException{
			JsonObject jsonObj = jsonElem.getAsJsonObject();
			TileData result = new TileData(jsonObj.get("tilewidth").getAsInt(), jsonObj.get("tileheight").getAsInt());
			JsonArray tilesetsArr = jsonObj.get("tilesets").getAsJsonArray();
			int[] offsets = new int[tilesetsArr.size()];
			String[] tilesets = new String[tilesetsArr.size()];
			for(int i = 0; i < tilesetsArr.size(); i++){
				JsonObject tilesetObj = tilesetsArr.get(i).getAsJsonObject();
				offsets[i] = tilesetObj.get("firstgid").getAsInt();
				String tileset = tilesetObj.get("source").getAsString();
				if(tileset.endsWith(".tsx") || tileset.endsWith(".xml")) tileset = tileset.substring(0, tileset.length() - 4);
				else if(tileset.endsWith(".json")) tilesets[i] = tileset.substring(0, tileset.length() - 5);
				tilesets[i] = tileset;
			}
			JsonArray layersArr = jsonObj.get("layers").getAsJsonArray();
			int length = layersArr.size();
			String[] layers = new String[length];
			JsonObject layerObj = layersArr.get(0).getAsJsonObject();
			int startX = layerObj.get("startx").getAsInt();
			int startY = layerObj.get("starty").getAsInt();
			int endX = startX + layerObj.get("width").getAsInt();
			int endY = startY + layerObj.get("height").getAsInt();
			for(int i = 1; i < length; i++){
				JsonObject layerObj1 = layersArr.get(i).getAsJsonObject();
				int x = layerObj1.get("startx").getAsInt();
				int y = layerObj1.get("starty").getAsInt();
				int x1 = x + layerObj1.get("width").getAsInt();
				int y1 = y + layerObj1.get("height").getAsInt();
				startX = Math.min(startX, x);
				startY = Math.min(startY, y);
				endX = Math.max(endX, x1);
				endY = Math.max(endY, y1);
			}
			int width = endX - startX;
			int height = endY - startY;
			int[][][] map = new int[length][width][height];
			for(int i = 0; i < length; i++){
				JsonObject layerObj1 = layersArr.get(i).getAsJsonObject();
				layers[i] = layerObj1.get("name").getAsString();
				int w = layerObj1.get("width").getAsInt();
				int h = layerObj1.get("height").getAsInt();
				if(w * h > 0){
					JsonArray chunksArr = layerObj1.get("chunks").getAsJsonArray();
					for(int j = 0; j < chunksArr.size(); j++){
						JsonObject chunkObj = chunksArr.get(j).getAsJsonObject();
						JsonArray dataArr = chunkObj.get("data").getAsJsonArray();
						int x1 = chunkObj.get("x").getAsInt();
						int y1 = chunkObj.get("y").getAsInt();
						int w1 = chunkObj.get("width").getAsInt();
						int h1 = chunkObj.get("height").getAsInt();
						for(int x = 0; x < w1; x++) for(int y = 0; y < h1; y++) map[i][x + x1 - startX][y + y1 - startY] = dataArr.get(x + (y * w1)).getAsInt();
					}
				}
			}
			return result.setTilesets(tilesets, offsets).setMapInfo(layers, map, length, width, height);
		}
		
	}
	
}
