package com.sulvic.engine.util;

import java.util.List;
import java.util.Random;

import com.sulvic.util.ContentBuilder;

public class WeightedList<E> implements Cloneable{
	
	private boolean calculated;
	private double totalWeight = 0d;
	private List<WeightedList.Item<E>> itemList = ContentBuilder.newArrayList();
	
	public E getRandomItem(Random random){
		if(!calculated){
			for(Item<E> item: itemList) totalWeight += item.getWeight();
			calculated = true;
		}
		double weight = random.nextDouble() * totalWeight;
		int index = 0;
		for(Item<E> item: itemList){
			weight -= item.getWeight();
			if(weight <= 0d){
				index = itemList.indexOf(item);
				break;
			}
		}
		return itemList.get(index).getItem();
	}
	
	public void addItem(E item, double weight){
		if(calculated){
			totalWeight = 0d;
			calculated = false;
		}
		itemList.add(new Item<>(item, weight));
	}
	
	public static class Item<E> implements Cloneable{
		
		private E theItem;
		private double itemWeight;
		
		public Item(E item, double weight){
			theItem = item;
			itemWeight = weight;
		}
		
		public boolean hasItem(E item){ return theItem.equals(item); }
		
		public double getWeight(){ return itemWeight; }
		
		public E getItem(){ return theItem; }
		
	}
	
}
