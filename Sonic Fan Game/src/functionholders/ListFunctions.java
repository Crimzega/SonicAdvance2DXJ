package functionholders;

import static java.lang.Math.*;

import java.util.List;

import com.sulvic.util.ContentBuilder;
import com.sulvic.util.SulvicCollections;

import badniks.Badnik;
import datatypes.Shape;
import datatypes.Vector;
import objects.AfterImage;
import objects.BlueSpring;
import objects.DashPad;
import objects.Item;
import objects.Rail;
import objects.Ramp;
import objects.Ring;
import objects.Rotor;
import objects.Spring;
import objects.SpringPole;
import rendering.Image;
import rendering.RenderBatch;
import rendering.Texture;

public class ListFunctions{
	
	public static int[][] append(int[][] existingPoints, int[] pointToCheck){ return SulvicCollections.mergeArrays(int[].class, existingPoints, pointToCheck); }
	
	public static Shape[] append(Shape[] existingPoints, Shape pointToCheck){ return SulvicCollections.mergeArrays(Shape.class, existingPoints, pointToCheck); }
	
	public static Texture[] append(Texture[] existingPoints, Texture pointToCheck){ return SulvicCollections.mergeArrays(Texture.class, existingPoints, pointToCheck); }
	
	public static Image[] append(Image[] existingPoints, Image pointToCheck){ return SulvicCollections.mergeArrays(Image.class, existingPoints, pointToCheck); }
	
	public static boolean[] append(boolean[] existingPoints, boolean pointToCheck){ return SulvicCollections.Primitives.mergeArrays(existingPoints, existingPoints); }
	
	public static Ramp[] append(Ramp[] existingPoints, Ramp pointToCheck){ return SulvicCollections.mergeArrays(Ramp.class, existingPoints, pointToCheck); }
	
	public static Rotor[] append(Rotor[] existingPoints, Rotor pointToCheck){ return SulvicCollections.mergeArrays(Rotor.class, existingPoints, pointToCheck); }
	
	public static Spring[] append(Spring[] existingPoints, Spring pointToCheck){ return SulvicCollections.mergeArrays(Spring.class, existingPoints, pointToCheck); }
	
	public static Rail[] append(Rail[] existingPoints, Rail pointToCheck){ return SulvicCollections.mergeArrays(Rail.class, existingPoints, pointToCheck); }
	
	public static SpringPole[] append(SpringPole[] existingPoints, SpringPole pointToCheck){ return SulvicCollections.mergeArrays(SpringPole.class, existingPoints, pointToCheck); }
	
	public static RenderBatch[] append(RenderBatch[] existingPoints, RenderBatch pointToCheck){ return SulvicCollections.mergeArrays(RenderBatch.class, existingPoints, pointToCheck); }
	
	public static float[] append(float[] existingPoints, float pointToCheck){ return SulvicCollections.Primitives.mergeArrays(existingPoints, pointToCheck); }
	
	public static int[] append(int[] existingPoints, int pointToCheck){ return SulvicCollections.Primitives.mergeArrays(existingPoints, pointToCheck); }
	
	public static double[] append(double[] existingPoints, double pointToCheck){ return SulvicCollections.Primitives.mergeArrays(existingPoints, pointToCheck); }
	
	public static Badnik[] append(Badnik[] existingPoints, Badnik pointToCheck){ return SulvicCollections.mergeArrays(Badnik.class, existingPoints, pointToCheck); }
	
	public static DashPad[] append(DashPad[] existingPoints, DashPad pointToCheck){ return SulvicCollections.mergeArrays(DashPad.class, existingPoints, pointToCheck); }
	
	public static BlueSpring[] append(BlueSpring[] existingPoints, BlueSpring pointToCheck){ return SulvicCollections.mergeArrays(BlueSpring.class, existingPoints, pointToCheck); }
	
	public static AfterImage[] append(AfterImage[] existingPoints, AfterImage pointToCheck){ return SulvicCollections.mergeArrays(AfterImage.class, existingPoints, pointToCheck); }
	
	public static Item[] append(Item[] existingPoints, Item pointToCheck){ return SulvicCollections.mergeArrays(Item.class, existingPoints, pointToCheck); }
	
	public static Vector[] append(Vector[] existingPoints, Vector pointToCheck){ return SulvicCollections.mergeArrays(Vector.class, existingPoints, pointToCheck); }
	
	public static Ring[] append(Ring[] existingPoints, Ring pointToCheck){ return SulvicCollections.mergeArrays(Ring.class, existingPoints, pointToCheck); }
	
	public static Vector[][] append(Vector[][] existingPoints, Vector[] pointToCheck){ return SulvicCollections.mergeArrays(Vector[].class, existingPoints, pointToCheck); }
	
	public static Vector[] appendWithoutDupes(Vector[] existingPoints, Vector pointToCheck){
		Vector[] newArray = append(existingPoints, pointToCheck);
		for(int i = 0; i < newArray.length - 1; i++) if(newArray[i].checkEqual(pointToCheck)) newArray = SulvicCollections.remove(newArray, newArray.length - 1);
		return newArray;
	}
	
	public static Vector[] removeDupes(Vector[] a){
		Vector[] b = null;
		for(int i = 0; i < a.length; i++) b = appendWithoutDupes(b, a[i]);
		return b;
	}
	
	public static Shape[] combine(Shape[] a, Shape[] b){ return SulvicCollections.mergeArrays(Shape.class, a, b); }
	
	public static Vector[] combine(Vector[] a, Vector[] b){ return SulvicCollections.mergeArrays(Vector.class, a, b); }
	
	public static Vector[] combine(Vector[][] a){
		Vector[] array = null;
		for(int i = 0; i < a.length; i++) array = SulvicCollections.mergeArrays(Vector.class, array, a[i]);
		return array;
	}
	
	public static Vector[] combine(Vector[] a, Vector[][] b){
		Vector[] array = a;
		for(int i = 0; i < b.length; i++) array = SulvicCollections.mergeArrays(Vector.class, array, b[i]);
		return array;
	}
	
	public static Vector[] reduceDimensions(Vector[][] d){
		Vector[] array = null;
		for(int i = 0; i < d.length; i++) array = SulvicCollections.mergeArrays(Vector.class, array, d[i]);
		return array;
	}
	
	public static Vector[][] getVectorsFromShapes(Shape[] shapes){
		Vector[][] array = new Vector[shapes.length][];
		for(int i = 0; i < shapes.length; i++) array[i] = shapes[i].points;
		return array;
	}
	
	public static Vector[][] removeIndex(Vector[][] list, int index){ return SulvicCollections.remove(list, index); }
	
	public static Shape[] removeIndex(Shape[] list, int index){ return SulvicCollections.remove(list, index); }
	
	public static Ring[] removeIndex(Ring[] list, int index){ return SulvicCollections.remove(list, index); }
	
	public static Badnik[] removeIndex(Badnik[] list, int index){ return SulvicCollections.remove(list, index); }
	
	public static Item[] removeIndex(Item[] list, int index){ return SulvicCollections.remove(list, index); }
	
	public static AfterImage[] removeIndex(AfterImage[] list, int index){ return SulvicCollections.remove(list, index); }
	
	public static Shape[] applyMask(Shape[] list, boolean[] masks){
		Shape[] output = null;
		for(int i = 0; i < list.length; i++) if(masks[i]) output = append(output, list[i]);
		return output;
	}
	
	public static Vector getSmallest(Vector[] list){
		List<Vector> theList = ContentBuilder.newArrayList(list);
		theList.sort((vec, vec1) -> { return Double.compare(vec.getLength(), vec1.getLength()); });
		return theList.get(0);
	}
	
	public static Vector getLargest(Vector[] list){
		List<Vector> theList = ContentBuilder.newArrayList(list);
		theList.sort((vec, vec1) -> { return Double.compare(vec.getLength(), vec1.getLength()); });
		return theList.get(theList.size() - 1);
	}
	
	public static int[][] resize(int[][] array, int size){
		if(array == null) return new int[size][];
		else{
			int[][] output = new int[size][];
			for(int i = 0; i < min(size, array.length); i++) output[i] = array[i];
			return output;
		}
	}
	
	public static float[][] resize(float[][] array, int size){
		if(array == null) return new float[size][];
		else{
			float[][] output = new float[size][];
			for(int i = 0; i < min(size, array.length); i++) output[i] = array[i];
			return output;
		}
	}
	
}
