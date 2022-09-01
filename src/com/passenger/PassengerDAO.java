package com.passenger;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.util.EstablishConnection;

public class PassengerDAO{
	private EstablishConnection ec = new EstablishConnection();
	
	public void insertIntoPassengers(Passenger p) {
		Connection connection = ec.getConnection();
		
		try {

				String query = "insert into PASSENGERS values (?,?,?)";
				PreparedStatement pstm = connection.prepareStatement(query);
				pstm.setString(1,p.getName());
				pstm.setInt(2, p.getAge());
				pstm.setString(3, String.valueOf(p.getGender()));
				
				pstm.executeUpdate();
	
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally{
			try {
				connection.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
		}
		
	}
	
	
}
