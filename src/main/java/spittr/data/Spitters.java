package spittr.data;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Component;

import spittr.Spitter;

@Component
public class Spitters implements SpitterRepository 
{	
	private NamedParameterJdbcTemplate jdbcOperations;
	
	private Map<String, Object> params;
	
	private static final String SEARCH_BY_USERNAME_SQL =
			"SELECT * FROM users WHERE username = :username";
	
	private static final String INSERT_SQL = 
			"INSERT INTO users (username, password, firstName, lastName, email) "
			+"VALUES (:username,:password,:firstName,:lastName,:email)";
	
	private static final String UPDATE_SQL = 
			"UPDATE users SET username = :username, password = :password, "
			+"firstName = :firstName, lastName = :lastName, email = :email WHERE id = :id";
	
	private static final String PRIVATE_ENCODE_KEY = "1234";

	@Autowired
	public Spitters(NamedParameterJdbcOperations jdbcOps)
	{
		this.jdbcOperations = (NamedParameterJdbcTemplate) jdbcOps;
		this.params = new HashMap<>();
	}

	@Override
	public Spitter findByUsername(String username)
	{
		this.params.clear();
		this.params.put("username", username);
		
		try {
			Spitter spitter = this.jdbcOperations.queryForObject(
					SEARCH_BY_USERNAME_SQL,
					this.params,
					new SpitterRowMapper());
			return spitter;
		} catch (DataAccessException e) 
		{
			return null;
		}
	}
	
	public int addSpitter(Spitter spitter)
	{
		params.clear();
		params.put("username", spitter.getUsername());
		params.put("password", this.encodePassword(spitter.getPassword()));
		params.put("firstName", spitter.getFirstName());
		params.put("lastName", spitter.getLastName());
		params.put("email", spitter.getEmail());
		
		return this.jdbcOperations.update(INSERT_SQL, params);
	}
	
	public int updateSpitter(Spitter spitter, Long id)
	{
		params.clear();
		params.put("id", id);
		params.put("username", spitter.getUsername());
		params.put("password", this.encodePassword(spitter.getPassword()));
		params.put("firstName", spitter.getFirstName());
		params.put("lastName", spitter.getLastName());
		params.put("email", spitter.getEmail());
		
		return this.jdbcOperations.update(UPDATE_SQL, params);
	}
	
	@Override
	public Spitter save(Spitter spitter)
	{
		Spitter existingSpitter = this.findByUsername(spitter.getUsername());
		if (null == existingSpitter)
		{
			this.addSpitter(spitter);
		}
		else
		{
			this.updateSpitter(spitter, existingSpitter.getId());
		}
			
		Spitter newSpitter = this.findByUsername(spitter.getUsername());
		if (null != newSpitter) {
			return newSpitter;
		}
		return null;
	}
	
	private String encodePassword(String password)
	{
		return new StandardPasswordEncoder(PRIVATE_ENCODE_KEY)
				.encode(password);
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
