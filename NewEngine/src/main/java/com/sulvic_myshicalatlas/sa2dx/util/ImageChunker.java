package com.sulvic_myshicalatlas.sa2dx.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import com.sulvic.util.ContentBuilder;

class ImageChunker{
	
	private static BufferedImage parseChunkData(ImagePalette palette, byte[] data) throws IOException{
		BufferedImage result = new BufferedImage(8, 8, BufferedImage.TYPE_INT_RGB);
		for(int i = 0; i < data.length; i += 2){
			int x = i % 8;
			int y = (int)Math.floor(i / 8);
			byte b = (byte)((data[i] >> 4) & 0xF);
			byte b1 = (byte)(data[i] & 0xF);
			result.setRGB(x, y, palette.getRGB(b));
			result.setRGB(x + 1, y, palette.getRGB(b1));
		}
		return result;
	}
	
	public static List<BufferedImage> parseImageData(int width, int height, int frameCount, ImagePalette palette, byte[][] data) throws IOException{
		List<BufferedImage> result = ContentBuilder.newArrayList();
		for(int i = 0; i < frameCount; i++){
			BufferedImage img = new BufferedImage(width * 8, height * 8, BufferedImage.TYPE_INT_RGB);
			for(int y = 0; y < width; y++) for(int x = 0; x < height; x++){
				BufferedImage img1 = parseChunkData(palette, data[i]);
				int offsetX = x * 8, offsetY = y * 8;
				for(int y1 = 0; y1 < 8; y1++) for(int x1 = 0; x1 < 8; x1++) img.setRGB(offsetX + x, offsetY + y1, img1.getRGB(x, y));
			}
			result.add(img);
		}
		return result;
	}
	
}
