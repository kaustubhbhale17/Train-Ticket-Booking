package com.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.train.Train;

public class TrainDAO {
	

	
	public static Train findTrain(int train) {
		Train t = null;
		EstablishConnection ec = new EstablishConnection();
		try {
			
	        Connection connection = ec.getConnection();
	        System.out.println("Database Connected successfully ! \n");
	        String query = "select * from TRAINS where TRAIN_NO = ?";
	        PreparedStatement stm = connection.prepareStatement(query);
	        stm.setInt(1, train);
	        
	        ResultSet rs =stm.executeQuery();

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
