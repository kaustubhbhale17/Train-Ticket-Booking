package com.ticket;

import com.passenger.Passenger;
import com.ticket.Ticket;
import com.train.Train;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;


public class Ticket {
	
	private static int counter=100;
	private String pnr;
	private Date travelDate;
	private TreeMap<Passenger,Double> passengers;

	Train train = new Train();
	
	public Ticket(Date travelDate,Train train) {
		this.travelDate=travelDate;
		this.train=train;
		counter++;
	}
	public String generatePNR() {
		String source = train.getSource();
		String destination = train.getDestination();
		@SuppressWarnings("deprecation")
		int year= travelDate.getYear();
		@SuppressWarnings("deprecation")
		int month = travelDate.getMonth();
		@SuppressWarnings("deprecation")
		int date = travelDate.getDate();
		
		pnr = source.charAt(0)+destination.charAt(0)+"_"+year+month+date+"_"+counter;
		
		return pnr;
	}
	
	public double calculatePassengerFair(Passenger p) {
		
		if(p.getAge()<=12) {
			return train.getTicket_price()*.50;
		}
		else if(p.getAge()>=60) {
			return train.getTicket_price()*.60;
		}
		else if(p.getGender()=='F')
			return train.getTicket_price()*0.25;
		return 0;
	}
	
	public void addPassenger(String name,int age,char gender){
		Passenger passenger = new Passenger(name,age,gender);
		double fair = calculatePassengerFair(passenger);
		passengers.put(passenger, fair);
	}
	
	public double calculateTotalTicketPrice() {
		double total =0;
		for(Map.Entry<Passenger,Double> entry : passengers.entrySet()) {
			total=total+entry.getValue();
		}
		return total;
	}
	
	public StringBuilder generateTicket() {
		
		StringBuilder sb = new StringBuilder();
		
		String pnr = generatePNR();
		int trainNo=train.getTrainNo();
		String trainName=train.getTrainName();
		String from = train.getSource();
		String destination = train.getDestination();
		Date date = travelDate;
		
		sb.append(pnr+"\n"+trainNo+"\n"+trainName+"\n"+from+"\n"+destination+"\n"+date);
		
		int noOfPassengers = passengers.size();
		sb.append("\nPassenger :"+noOfPassengers);
		sb.append("Name\t"+"Age\t"+"Gender\t"+"Fair");
		for(Map.Entry<Passenger,Double> entry : passengers.entrySet()) {
			
			Passenger p = entry.getKey();
			sb.append(p.getName()+"\t"+p.getAge()+"\t"+p.getGender()+"\t"+entry.getValue()+"\n");
		}
		return sb;
		
	}
	
	public void writeTicket() {
		FileWriter f = null;
		try {
			f = new FileWriter("tickets.txt");
			StringBuilder sb = generateTicket();
			f.write(sb.toString());
		} catch (IOException e) {
			
			e.printStackTrace();
		}finally {
			try {
				f.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
	}
	
	
}
