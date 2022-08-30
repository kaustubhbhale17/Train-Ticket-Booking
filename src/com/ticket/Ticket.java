package com.ticket;

import com.passenger.Passenger;
import com.ticket.Ticket;
import com.train.Train;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



public class Ticket {
	
	private static int counter=99;
	private String pnr;
	private Date travelDate;
	private static HashMap<Passenger,Double> passengers = new HashMap<>();
	private Train train;

	
	public Ticket(Date travelDate,Train train) {
		this.travelDate=travelDate;
		this.train=train;
		counter++;
	}
	
	
	@SuppressWarnings("deprecation")
	public String generatePNR() {
		String source = train.getSource();
		String destination = train.getDestination();
		
		int year= travelDate.getYear();
		int month = travelDate.getMonth();
		int date = travelDate.getDate();
		
		//System.out.println((char)source.charAt(0)+(char)destination.charAt(0));
		char src = source.charAt(0); 
		char dest = destination.charAt(0);
		StringBuilder sb = new StringBuilder();
		pnr = sb.append(src).append(dest).append("_").append(year).append(month).append(date).append("_").append(counter).toString();
		
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
		return train.getTicket_price();
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
	
	@SuppressWarnings("deprecation")
	public StringBuilder generateTicket() {
		//generate a file with name PNR no and copy below contents inside file.
		StringBuilder sb = new StringBuilder();
		
		String pnr = generatePNR();
		int trainNo = train.getTrainNo();
		String trainName=train.getTrainName();
		String from = train.getSource();
		String to = train.getDestination();
		
		String date= String.valueOf(travelDate.getDate());
		String month = String.valueOf(travelDate.getMonth()+1);
		String year=String.valueOf(travelDate.getYear()+1900);
		String newDate = date+"/"+month+"/"+year;
		
		sb.append("\nPNR\t\t\t\t:\t"+pnr+"\nTrainNo\t\t\t:\t"+trainNo+"\nTrain Name\t\t:\t"+trainName+"\nFrom\t\t\t:\t"+from+"\nTo\t\t\t\t:\t"+to+"\nTravel Date\t\t:\t"+newDate);
		sb.append("\n\nPassengers:\n");
		sb.append("---------------------------------------------------------\n");
		sb.append("Name\t\tAge\t\tGender\t\tFair\n");
		sb.append("---------------------------------------------------------\n");
		for(Map.Entry<Passenger,Double> entry : passengers.entrySet()) {
			Passenger p = entry.getKey();
			sb.append(p.getName()+"\t\t"+p.getAge()+"\t\t"+p.getGender()+"\t\t"+entry.getValue()+"\n");
		}
		
		sb.append("\nTotal Price: "+calculateTotalTicketPrice());
		return sb;
		
	}
	
	public void writeTicket() {
		//create a file with PNR number 
		StringBuilder s = generateTicket();
		try {
			FileWriter writer = new FileWriter(generatePNR()+".txt");
			writer.write(s.toString());
			writer.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		} 
		
	}
	
	
}
