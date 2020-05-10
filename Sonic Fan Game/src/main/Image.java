package main;

import static org.lwjgl.opengl.GL33.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import rendering.Shader;
import rendering.Texture;

public class Image {
	private int vaoID;
	private int vboID;
	private int eboID;
	
	private Texture tex;
	
	private float[] vertexArray = {
		 0.0f, 0.0f, 0.0f,		1.0f, 0.0f, 0.0f, 1.0f,		1, 1,
		 0.0f, 0.0f, 0.0f,		0.0f, 1.0f, 0.0f, 1.0f,		0, 0,
		 0.0f, 0.0f, 0.0f,		0.0f, 0.0f, 1.0f, 1.0f,		1, 0,
		 0.0f, 0.0f, 0.0f,		1.0f, 1.0f, 0.0f, 1.0f,		0, 1
	};
	
	private int[] elementArray = {
		2, 1, 0,
		0, 1, 3
	};
	
	public Image(String filepath) {tex = new Texture(filepath);}
	public Image(ByteBuffer imageBuffer) {tex = new Texture(imageBuffer);}
	
	private void load(double x, double y, double xScale, double yScale) {
		vertexArray[0] = (float)(tex.width * xScale + x);
		vertexArray[1] = (float)y;
		
		vertexArray[9] = (float)x;
		vertexArray[10] = (float)(tex.height * yScale + y);
		
		vertexArray[18] = (float)(tex.width * xScale + x);
		vertexArray[19] = (float)(tex.height * yScale + y);
		
		vertexArray[27] = (float)x;
		vertexArray[28] = (float)y;
		
		vaoID = glGenVertexArrays();
		glBindVertexArray(vaoID);
		
		FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
		vertexBuffer.put(vertexArray).flip();
		
		vboID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);
		
		IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
		elementBuffer.put(elementArray).flip();
		
		eboID = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);
		
		int positionsSize = 3;
		int colorSize = 4;
		int uvSize = 2;
		int vertexSizeBytes = (positionsSize + colorSize + uvSize) * Float.BYTES;
		
		glVertexAttribPointer(0, positionsSize, GL_FLOAT, false, vertexSizeBytes, 0);
		glEnableVertexAttribArray(0);
		
		glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, positionsSize * Float.BYTES);
		glEnableVertexAttribArray(1);
		
		glVertexAttribPointer(2, uvSize, GL_FLOAT, false, vertexSizeBytes, (positionsSize + colorSize) * Float.BYTES);
		glEnableVertexAttribArray(2);
	}
	
	public void draw(double x, double y, double xScale, double yScale, Shader shader, Camera camera) {
		load(x, y + tex.height * yScale, xScale, -yScale);
		
		System.out.println((Window.getInitialHeight() - Window.getHeight()));
		
		shader.use();
		
		shader.uploadTexture("TEX_SAMPLER", 0);
		glActiveTexture(GL_TEXTURE0);
		tex.bind();
		
		shader.uploadMat4f("uProjection", camera.getProjectionMatrix());
		shader.uploadMat4f("uView", camera.getViewMatrix());
		
		glBindVertexArray(vaoID);
		
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		
		glBindVertexArray(0);
		
		shader.detach();
	}
	
	public int getWidth() {return(tex.width);}
	public int getHeight() {return(tex.height);}
}
