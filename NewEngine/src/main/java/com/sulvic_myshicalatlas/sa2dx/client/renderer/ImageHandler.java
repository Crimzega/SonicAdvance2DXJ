package com.sulvic_myshicalatlas.sa2dx.client.renderer;

import static org.lwjgl.BufferUtils.*;
import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

public class ImageHandler{
	
	public static void renderImage(float x, float y, int texId){
		glBindTexture(GL_TEXTURE_2D, texId);
		glBegin(GL_QUADS);
		glTexCoord2f(0f, 0f);
		glVertex2f(-1f,	-1f);
		glTexCoord2f(1f, 0f);
		glVertex2f(1f,	-1f);
		glTexCoord2f(1f, 1f);
		glVertex2f(1f,	1f);
		glTexCoord2f(0f, 1f);
		glVertex2f(-1f,	1f);
		glEnd();
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	public static int toTexture(BufferedImage img){
		int height = img.getHeight(), width = img.getWidth();
		int[] pixels = img.getRGB(0, 0, width, height, null, 0, width);
		ByteBuffer buffer = createByteBuffer(width * height * 4);
		for(int y = 0; y < height; y++) for(int x = 0; x < width; x++){
			int px = pixels[x + y * width];
			buffer.put((byte)((px >> 16) & 0xFF));
			buffer.put((byte)((px >> 8) & 0xFF));
			buffer.put((byte)(px & 0xFF));
			buffer.put((byte)((px >> 24) & 0xFF));
		}
		buffer.flip();
		int result = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, result);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		glBindTexture(GL_TEXTURE_2D, 0);
		return result;
	}
	
}
