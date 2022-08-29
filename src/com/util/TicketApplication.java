package com.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import com.train.Train;
import com.ticket.Ticket;

public class TicketApplication {

	public static void main(String[] args) throws ParseException {
		
		Ticket ticket;
		Scanner scannerNumber = new Scanner(System.in);
		Scanner scannerText = new Scanner(System.in);
		
		System.out.println("Enter the Train Number");
		int trainNumber = scannerNumber.nextInt();
		Train train = TrainDAO.findTrain(trainNumber);
		if(train==null) {
			System.out.println("Train with given train number does not exist");
			System.exit(0);
		}
		
		System.out.println("Enter Travel Date ");
		String sdate = scannerText.nextLine();
		Date travelDate = new SimpleDateFormat("dd-MM-yyyy").parse(sdate); 

		if(travelDate.compareTo(new Date())<0) {
			System.out.println("Travel Date is before current date");
			System.exit(0);
		}
		
		System.out.println("Enter the number of passengers ");
		int numberOfPassengers = scannerNumber.nextInt();
		
		do {
			System.out.println("Enter Passenger Name ");
			String name = scannerText.nextLine();
			System.out.println("Enter Age ");
			int age = scannerNumber.nextInt();
			System.out.println("Enter Gender(M/F) ");
			char gender = scannerNumber.next().charAt(0);
			
			ticket = new Ticket(travelDate,train);
			ticket.addPassenger(name, age, gender);
			
			numberOfPassengers--;
			
		}while(numberOfPassengers!=0);
		
		System.out.println("Ticket Booked with PNR : "+ticket.generatePNR()+"\n");
		System.out.println(ticket.generateTicket());
		
		
		scannerNumber.close();
		scannerText.close();
	}

}
