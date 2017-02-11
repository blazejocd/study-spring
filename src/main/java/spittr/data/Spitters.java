package spittr.data;

import java.sql.*;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Component;

import spittr.Spitter;

@Component
public class Spitters implements SpitterRepository 
{
	@Autowired
	private DataSource dataSource;
	
	private Connection conn;
	
	private PreparedStatement stmt;
	
	private static final String SEARCH_SQL =
			"SELECT * FROM users WHERE username = ?";
	
	private static final String INSERT_SQL = 
			"INSERT INTO users (username, password, firstName, lastName, email) VALUES (?,?,?,?,?)";
	
	private static final String UPDATE_SQL = 
			"UPDATE users SET username = ?, password = ?, firstName = ?, lastName = ?, email = ? WHERE id = ?";

	
	public Spitters()
	{
		this.conn = null;
		this.stmt = null;
	}
	
	@Override
	public Spitter save(Spitter spitter)
	{
		Spitter existingSpitter = this.findByUsername(spitter.getUsername());
		
		try
		{
			conn = this.dataSource.getConnection();
			
			if (null == existingSpitter) {
				stmt = conn.prepareStatement(INSERT_SQL);
				this.bindParameters(spitter, null);
			}
			else
			{
				stmt = conn.prepareStatement(UPDATE_SQL);
				this.bindParameters(spitter, existingSpitter);
			}
			stmt.execute();
			
			Spitter newSpitter = this.findByUsername(spitter.getUsername());
			if (null != newSpitter) {
				return newSpitter;
			}
			
		} catch (SQLException e) {
		}
		finally {
			try {
				if (null != stmt) {
					stmt.close();
				}
				if (null != conn) {
					conn.close();
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		return null;
	}

	@Override
	public Spitter findByUsername(String username)
	{
		Spitter spitter = new Spitter();
		
		try
		{
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(SEARCH_SQL);
			this.bindParameters(username);
			
			ResultSet rs = stmt.executeQuery();
			if (false == rs.first()) {
				return null;
			}
			else 
			{
				spitter.setId(rs.getLong("id"));
				spitter.setUsername(rs.getString("username"));
				spitter.setPassword("IT IS SECRET");
				spitter.setFirstName(rs.getString("firstName"));
				spitter.setLastName(rs.getString("lastName"));
				spitter.setEmail(rs.getString("email"));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally 
		{
			try {
				if (null != stmt) {
					stmt.close();
				}
				if (null != conn) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if (spitter.hasId()) {
			return spitter;
		}
		
		return null;
	}
	
	private void bindParameters(Spitter newSpitter, Spitter existingSpitter)
	{
		try
		{
			stmt.setString(1, newSpitter.getUsername());
			stmt.setString(2,
				new StandardPasswordEncoder("1234").encode(newSpitter.getPassword()));
			stmt.setString(3, newSpitter.getFirstName());
			stmt.setString(4, newSpitter.getLastName());
			stmt.setString(5, newSpitter.getEmail());
			
			if (null != existingSpitter) {
				stmt.setLong(6, existingSpitter.getId());
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void bindParameters(String username)
	{
		try {
			stmt.setString(1, username);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
