package com.ticket;

import com.passenger.Passenger;
import com.ticket.Ticket;
import com.train.Train;
import com.util.EstablishConnection;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



public class Ticket {
	
	private String pnr;
	private Date travelDate;
	private static HashMap<Passenger,Double> passengers = new HashMap<>();
	private Train train;
	
	
	public Ticket(Date travelDate,Train train) {
		this.travelDate=travelDate;
		this.train=train;

	}
	
	
	@SuppressWarnings("deprecation")
	public String generatePNR() {
		
		String source = train.getSource();
		String destination = train.getDestination();
		
		int year= travelDate.getYear()+1900;
		int month = travelDate.getMonth()+1;
		int date = travelDate.getDate();
		
		//System.out.println((char)source.charAt(0)+(char)destination.charAt(0));
		char src = source.charAt(0); 
		char dest = destination.charAt(0);
		StringBuilder sb = new StringBuilder();
		
		//get the last id
		final int id=getLastId();
			
		pnr = sb.append(src).append(dest).append("_").append(year).append(month).append(date).append("_").append(id).toString();
		
		return pnr;
	}
	
	public double calculatePassengerFair(Passenger p) {
		
		if(p.getGender()=='F')
			return train.getTicket_price()*0.25;
		else if(p.getAge()<=12) {
			return train.getTicket_price()*.50;
		}
		else if(p.getAge()>=60) {
			return train.getTicket_price()*.60;
		}
		
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
		
		StringBuilder sb = new StringBuilder();
		
		String pnr = generatePNR();
		int trainNo = train.getTrainNo();
		String trainName=train.getTrainName();
		String from = train.getSource();
		String to = train.getDestination();
		
		String date= String.valueOf(travelDate.getDate());
		String month = String.valueOf(travelDate.getMonth()+1);
		String year=String.valueOf(travelDate.getYear()+1900);
		
		//for printing the date in the given format
		String newDate = date+"/"+month+"/"+year;
		
		sb.append("PNR\t\t\t\t:\t"+pnr+"\nTrainNo\t\t\t:\t"+trainNo+"\nTrain Name\t\t:\t"+trainName+"\nFrom\t\t\t:\t"+from+"\nTo\t\t\t\t:\t"+to+"\nTravel Date\t\t:\t"+newDate);
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
	
	public void insertIntoTicketTable() {
		
		EstablishConnection ec = new EstablishConnection();
		Connection connection = ec.getConnection();
		try {
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");  
		    String strDate = formatter.format(travelDate);  
			
			String query = "insert into TICKET(pnr,train_no,train_name,train_source,train_destination,travel_date,no_of_passengers,total_fair) values (?,?,?,?,?,?,?,?)";
			PreparedStatement pstm = connection.prepareStatement(query);
			
			
			
			pstm.setString(1, generatePNR());
			pstm.setInt(2, train.getTrainNo());
			pstm.setString(3, train.getTrainName());
			pstm.setString(4, train.getSource());
			pstm.setString(5, train.getDestination());
			pstm.setString(6, strDate);
			pstm.setInt(7, passengers.size());
			pstm.setDouble(8, calculateTotalTicketPrice());
			
			pstm.executeUpdate();
			
			pstm.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public int getLastId() {
		int id=0;
		
		try {
			EstablishConnection ec = new EstablishConnection();
			Connection connection = ec.getConnection();
			String query="SELECT * FROM TICKET WHERE id=(SELECT max(id) FROM TICKET)";
			PreparedStatement pstm = connection.prepareStatement(query);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()) {
				id=rs.getInt(1);
			}
			return id;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return id;
	}
	
}
