package com.sulvic.util;

import static java.lang.Math.*;

import java.awt.Color;

/** This class is a java rewrite of jamieowen's glsl-blend library<br/><a href="https://github.com/jamieowen/glsl-blend">Library</a>*/
public class ColorBlender{
	
	public static int blendColors(int baseRGB, int blendRGB, EnumMode mode){ return mode.getModeColor(new Color(baseRGB), new Color(blendRGB)).getRGB(); }
	
	public static int blendColors(int baseRGB, int blendRGB, float opacity, EnumMode mode){ return mode.getModeColor(new Color(baseRGB), new Color(blendRGB), opacity).getRGB(); }
	
	public static enum EnumMode{
		
		ADD,
		AVERAGE,
		COLOR_BURN,
		COLOR_DODGE,
		DARKEN,
		DIFFERENCE,
		EXCLUSION,
		GLOW,
		HARD_LIGHT,
		HARD_MIX,
		LIGHTEN,
		LINEAR_BURN,
		LINEAR_DODGE,
		LINEAR_LIGHT,
		MULTIPLY,
		NEGATION,
		NORMAL,
		OVERLAY,
		PHOENIX,
		PIN_LIGHT,
		REFLECT,
		SCREEN,
		SOFT_LIGHT,
		SUBTRACT,
		VIVID_LIGHT;
		
		private float getModeValue(float base, float blend){
			switch(this){
				case ADD: return min(base + blend, 1f);
				case AVERAGE: return (base + blend) / 2f;
				case COLOR_BURN: return (blend == 0f)? blend: max(1f - (1f - base) / blend, 0f);
				case COLOR_DODGE: return (blend == 1f)? blend: min(base / (1f - blend), 1f);
				case DARKEN: return min(base, blend);
				case DIFFERENCE: return abs(base - blend);
				case EXCLUSION: return base + blend - 2f * base * blend;
				case GLOW: return REFLECT.getModeValue(base, blend);
				case HARD_LIGHT: return OVERLAY.getModeValue(base, blend);
				case HARD_MIX: return VIVID_LIGHT.getModeValue(base, blend);
				case LIGHTEN: return max(base, blend);
				case LINEAR_BURN: return max(base + blend - 1f, 0f);
				case LINEAR_DODGE: return min(base + blend, 1f);
				case LINEAR_LIGHT: return blend < 0.5f? LINEAR_BURN.getModeValue(base, 2F * blend): LINEAR_DODGE.getModeValue(base, 2f * (blend - 0.5f));
				case MULTIPLY: return base * blend;
				case NEGATION: return 1f - abs(1f - base - blend);
				case OVERLAY: return base < 0.5f? 2f * base * blend: 1f - 2f * (1f - base) * (1f - blend);
				case PHOENIX: return min(base, blend) - max(base, blend) + 1f;
				case PIN_LIGHT: return blend < 0.5f? DARKEN.getModeValue(base, 2F * blend): LIGHTEN.getModeValue(base, 2f * (blend - 0.5f));
				case REFLECT: return blend == 1f? blend: min(base * base / (1f - blend), 1f);
				case SCREEN: return 1f - ((1f - base) * (1f - blend));
				case SOFT_LIGHT: return blend < 0.5f? 2f * base * blend + base * base * (1f - 2f * blend): (float)(sqrt(base) * (2f * blend - 1f)) + 2f * base * (1f - blend);
				case SUBTRACT: return max(base + blend - 1f, 0f);
				case VIVID_LIGHT: return blend < 0.5f? COLOR_BURN.getModeValue(base, 2f * blend): COLOR_DODGE.getModeValue(base, 2f * (blend - 0.5f));
				default: return blend;
			}
		}
		
		private float getModeValue(float base, float blend, float opacity){ return getModeValue(base, blend) * opacity + base * (1f - opacity); }
		
		private float toRange(int value){ return value / 255f; }
		
		protected Color getModeColor(Color base, Color blend){
			float r = getModeValue(toRange(base.getRed()), toRange(blend.getRed())),
			g = getModeValue(toRange(base.getGreen()), toRange(blend.getGreen())),
			b = getModeValue(toRange(base.getBlue()), toRange(blend.getBlue()));
			return new Color(r, g, b);
		}
		
		protected Color getModeColor(Color base, Color blend, float opacity){
			float r = getModeValue(toRange(base.getRed()), toRange(blend.getRed()), opacity),
			g = getModeValue(toRange(base.getGreen()), toRange(blend.getGreen()), opacity),
			b = getModeValue(toRange(base.getBlue()), toRange(blend.getBlue()), opacity);
			return new Color(r, g, b);
		}
		
	}
	
}
