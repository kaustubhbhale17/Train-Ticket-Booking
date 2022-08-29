package com.tickets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.train.Train;

public class TrainDAO {
	
	private static String url= "jdbc:mysql://localhost:3306/coreJavaCaseStudy";
	private static String user ="root";
	private static String password = "root";
	
	
	public static Train findTrain(int train) {
		Train t = null;
		try {
			
	        Connection connection = DriverManager.getConnection(url, user, password);
	        System.out.println("Connected successfully ! ");
	        String query = "select * from TRAINS where TRAIN_NO = ?";
	        PreparedStatement stm = connection.prepareStatement(query);
	        stm.setInt(1, train);
	        
	        ResultSet rs =stm.executeQuery();
	        //System.out.println(rs);
//	        if(rs.getFetchSize()<=0) {
//	        	System.out.println("Train with given train number does not exist");
//	        	System.exit(0);
//	        }
	        while(rs.next()) {
	        	int trainNo = rs.getInt("TRAIN_NO");
	        	String trainName = rs.getString("TRAIN_NAME");
	        	String source = rs.getString("SOURCE");
	        	String destination = rs.getString("DESTINATION");
	        	double price = rs.getDouble("TICKET_PRICE");
	        	
	        	t = new Train(trainNo,trainName,source,destination,price);
	        	return t;
	        	
	        }
	       
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return t;
	}
	
	
}
