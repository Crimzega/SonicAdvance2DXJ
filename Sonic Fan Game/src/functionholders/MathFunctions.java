package functionholders;

import static java.lang.Math.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

import datatypes.Vector;

public class MathFunctions{
	
	public static double getAngleOfVector(Vector vec){
		Vector axis = vec.normalize();
		double axisAngle = acos(axis.x);
		if(axis.y > 0) axisAngle *= -1;
		axisAngle = limitAngle(axisAngle);
		return axisAngle;
	}
	
	public static double getDistanceBetweenAngles(double a0, double a1){
		double b0 = limitAngle(a0);
		double b1 = limitAngle(a1);
		return min(limitAngle(b0 - b1), limitAngle(b1 - b0));
	}
	
	public static Vector fixAxis(Vector axis0){
		Vector axis = new Vector(axis0.x, axis0.y);
		if(axis.y < 0) axis = axis.scale(-1);
		if(axis.x < 0) axis = axis.scale(-1);
		return axis;
	}
	
	public static double getProjectedValue(Vector point, Vector axis){
		Vector project = point.project(axis);
		double output = project.getLength();
		if(project.x < 0) output *= -1;
		return output;
	}
	
	public static Vector getPerVector(Vector point){ return new Vector(point.y, point.x); }
	
	public static double limitAngle(double angle){
		double temp = angle;
		while(temp < 0) temp += 2 * PI;
		while(temp >= 2 * PI) temp -= 2 * PI;
		return temp;
	}
	
	public static boolean checkAngleBetweenAngles(double a0, double a1, double a2){
		double b0 = limitAngle(a0), b1 = limitAngle(a1), b2 = limitAngle(a2);
		b0 = limitAngle(b0 - b1);
		b2 = limitAngle(b2 - b1);
		return b0 < b2 && b0 != 0;
	}
	
	public static boolean checkAngleBetweenAnglesInclusive(double a0, double a1, double a2){
		double b0 = limitAngle(a0), b1 = limitAngle(a1), b2 = limitAngle(a2);
		b0 = limitAngle(b0 - b1);
		b2 = limitAngle(b2 - b1);
		return b0 <= b2;
	}
	
	public static double round(double value, int places){
		if(places < 0) throw new IllegalArgumentException();
		BigDecimal bd = new BigDecimal(Double.toString(value));
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
	
}
