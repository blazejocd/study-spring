package spittr.data;

import java.sql.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Component;

import spittr.Spitter;

@Component
public class Spitters implements SpitterRepository 
{	
	private JdbcOperations jdbcOperations;
	
	private static final String SEARCH_BY_USERNAME_SQL =
			"SELECT * FROM users WHERE username = ?";
	
	private static final String INSERT_SQL = 
			"INSERT INTO users (username, password, firstName, lastName, email) VALUES (?,?,?,?,?)";
	
	private static final String UPDATE_SQL = 
			"UPDATE users SET username = ?, password = ?, firstName = ?, lastName = ?, email = ? WHERE id = ?";

	@Autowired
	public Spitters(JdbcOperations jdbcOps)
	{
		this.jdbcOperations = jdbcOps;
	}
	
	@Override
	public Spitter save(Spitter spitter)
	{
		Spitter existingSpitter = this.findByUsername(spitter.getUsername());
		if (null == existingSpitter)
		{
			this.jdbcOperations.update(
				INSERT_SQL,
				spitter.getUsername(),
				new StandardPasswordEncoder("1234")
					.encode(spitter.getPassword()),
				spitter.getFirstName(),
				spitter.getLastName(),
				spitter.getEmail());
		}
		else
		{
			this.jdbcOperations.update(
				UPDATE_SQL,
				spitter.getUsername(),
				new StandardPasswordEncoder("1234")
					.encode(spitter.getPassword()),
				spitter.getFirstName(),
				spitter.getLastName(),
				spitter.getEmail(),
				existingSpitter.getId());
		}
			
		Spitter newSpitter = this.findByUsername(spitter.getUsername());
		if (null != newSpitter) {
			return newSpitter;
		}
		return null;
	}

	@Override
	public Spitter findByUsername(String username)
	{
		try {
			Spitter spitter = this.jdbcOperations.queryForObject(
					SEARCH_BY_USERNAME_SQL, 
					new SpitterRowMapper(), 
					username);
			return spitter;
		} catch (DataAccessException e) 
		{
			return null;
		}
	}
}

final class SpitterRowMapper implements RowMapper<Spitter>
{
	@Override
	public Spitter mapRow(ResultSet rs, int rowNum) throws SQLException 
	{
		return new Spitter(
				rs.getLong("id"),
				rs.getString("username"),
				rs.getString("password"),
				rs.getString("firstName"),
				rs.getString("lastName"),
				rs.getString("email"));
	}
	
}
