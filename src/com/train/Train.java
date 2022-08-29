package com.train;

public class Train {
	
	private int trainNo;
	private String trainName;
	private String source;
	private String destination;
	private double ticket_price;
	
	public Train() {
		
	}
	
	public Train(int trainNo, String trainName, String source, String destination, double ticket_price) {
		
		this.trainNo = trainNo;
		this.trainName = trainName;
		this.source = source;
		this.destination = destination;
		this.ticket_price = ticket_price;
	}
	
	public int getTrainNo() {
		return trainNo;
	}
	
	public void setTrainNo(int trainNo) {
		this.trainNo = trainNo;
	}
	
	public String getTrainName() {
		return trainName;
	}
	
	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
	public String getDestination() {
		return destination;
	}
	
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	public double getTicket_price() {
		return ticket_price;
	}
	
	public void setTicket_price(double ticket_price) {
		this.ticket_price = ticket_price;
	}
	
	
	
}
