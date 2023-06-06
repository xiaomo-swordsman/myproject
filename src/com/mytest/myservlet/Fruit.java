package com.mytest.myservlet;

public enum Fruit {
	
	APPLE(1,"ɽ��",6.60),
	BANANA(2,"����",6.60),
	ORANGE(3,"�ձ�",6.60);
	
	private final Integer number;
	private final String orign;
	private final Double price;
	
	Fruit(Integer number,String orign,Double price){
		this.number = number;
		this.orign = orign;
		this.price = price;
	}
	
	public Integer getNumber() {
		return number;
	}
	public String getOrign() {
		return orign;
	}
	public Double getPrice() {
		return price;
	}
}
