package com.sulvic_myshicalatlas.sa2dx.util;

import static com.sulvic.util.StringHelper.*;
import static org.lwjgl.BufferUtils.*;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;

import org.lwjgl.glfw.GLFWImage;

import com.sulvic.engine.util.AssetLocation;
import com.sulvic.io.*;
import com.sulvic_myshicalatlas.sa2dx.client.texture.CharacterImage;

public class AssetLoader{
	
	private static final AssetLocation ICON = new AssetLocation("sa2dx", "icon");
	
	private static GLFWImage getIconImage(){
		GLFWImage img = null;
		try{
			BufferedImage buffImg = ImageIO.read(getResourceStream(formatTexturePath(ICON)));
			int height = buffImg.getHeight(), width = buffImg.getWidth();
			ByteBuffer buffer = createByteBuffer(width * height * 4);
			for(int y = 0; y < height; y++) for(int x = 0; x < width; x++){
				int color = buffImg.getRGB(x, y);
				buffer.put((byte)((color << 8) >> 24));
				buffer.put((byte)((color << 16) >> 24));
				buffer.put((byte)((color << 24) >> 24));
				buffer.put((byte)(color >> 24));
			}
			buffer.flip();
			img = GLFWImage.malloc();
			img.set(width, height, buffer);
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
		return img;
	}
	
	private static String formatCharacterImagePath(AssetLocation assetLoc, String animName){ return format("/assets/{}/data/{}/{}.cbd", assetLoc.getDomain(), assetLoc.getPath(), animName); }
	
	private static String formatSoundPath(AssetLocation assetLoc){ return format("/assets/{}/sounds/{}.wav", assetLoc.getDomain(), assetLoc.getPath()); }
	
	private static String formatTexturePath(AssetLocation assetLoc){ return format("/assets/{}/textures/{}.png", assetLoc.getDomain(), assetLoc.getPath()); }
	
	protected static InputStream getResourceStream(String pathName){ return AssetLoader.class.getResourceAsStream(pathName); }
	
	protected static String formatPalettePath(AssetLocation assetLoc){ return format("/assets/{}/palette/{}.pal", assetLoc.getDomain(), assetLoc.getPath()); }
	
	public static CharacterImage getCharacterImage(AssetLocation paletteLoc, String animName){
		try{
			ImagePalette palette = ImagePalette.loadPalette(paletteLoc);
			EndianInputStream stream = new EndianInputStream(getResourceStream(formatCharacterImagePath(paletteLoc, animName)), Endian.LITTLE);
			int width = stream.readInt();
			int height = stream.readInt();
			int frames = stream.readInt();
			int[] frameTimeouts = new int[frames];
			byte[][] data = new byte[frames][64];
			for(int i = 0; i < frames; i++){
				frameTimeouts[i] = stream.readInt();
				stream.read(data[i]);
			}
			CharacterImage charImage = CharacterImage.newCharacterImage(ImageChunker.parseImageData(width, height, frames, palette, data), frameTimeouts);
			if(stream.available() == 4) charImage.setRepeatFrame(stream.readInt());
			stream.close();
			return charImage;
		}
		catch(IOException ex){
			ex.printStackTrace();
			return (CharacterImage)null;
		}
	}
	
	public static GLFWImage.Buffer getIcon(){
		GLFWImage.Buffer buffer = GLFWImage.malloc(1);
		buffer.put(0, getIconImage());
		return buffer;
	}
	
	public static Clip getSound(AssetLocation assetLoc, float amp){
		try{
			Clip sound = AudioSystem.getClip();
			sound.open(AudioSystem.getAudioInputStream(new BufferedInputStream(getResourceStream(formatSoundPath(assetLoc)))));
			FloatControl gainControl = (FloatControl)sound.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(amp);
			return sound;
		}
		catch(Exception ex){
			System.err.println(format("Failed to load sound '{}'", ex));
			ex.printStackTrace();
			return (Clip)null;
		}
	}
	
}
