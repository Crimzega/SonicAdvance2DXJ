package com.sulvic_myshicalatlas.sa2dx.util;

import static com.sulvic_myshicalatlas.sa2dx.util.AssetLoader.*;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import com.sulvic.engine.util.AssetLocation;
import com.sulvic.io.Endian;
import com.sulvic.io.EndianInputStream;
import com.sulvic.util.ContentBuilder;

public class ImagePalette{
	
	private List<Color> colors = ContentBuilder.newArrayList();
	
	private ImagePalette(int[] rgbs){ for(int rgb: rgbs) colors.add(new Color(rgb)); }
	
	protected static ImagePalette loadPalette(AssetLocation assetLoc) throws IOException{
		EndianInputStream stream = new EndianInputStream(getResourceStream(formatPalettePath(assetLoc)), Endian.LITTLE);
		int[] colors = new int[stream.available() / 2];
		for(int i = 0; i < colors.length; i++){
			short gbaColor = stream.readShort();
			int r = (gbaColor & 0x1F) << 3, g = ((gbaColor >> 5) & 0x1F) << 3, b = ((gbaColor >> 10) & 0x1F) << 3;
			colors[i] = ((r & 0xFF) << 16) + ((g & 0xFF) << 8) + (b & 0xFF);
		}
		stream.close();
		return new ImagePalette(colors);
	}
	
	public Color getColor(int index){ return colors.get(index); }
	
	public int getRGB(int index){ return getColor(index).getRGB(); }
	
}
