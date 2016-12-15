package spittr;

import javax.validation.constraints.*;
import org.apache.commons.lang3.builder.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class Spitter 
{
	private Long id;
	
	@NotNull
	@Size(min=2, max=5, message="{username.size}")
	private String username;
	

	private String password;
	
	@NotNull
	@Size(min=2, max=5, message="{firstName.size}")
	private String firstName;
	
	@NotNull
	@Size(min=2, max=5, message="{lastName.size}")
	private String lastName;
	
	@NotNull
	private String email;
	
	private MultipartFile profilePicture;
	
	public Spitter() {}
	
	public Spitter(String username, String password, String firstName, String lastName, String email)
	{
		this.id = null;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	
	public Spitter(Long id, String username, String password, String firstName, String lastName, String email)
	{
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return this.id;
	}
	
	public void setUsername(String u) {
		this.username = u;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
	    return password;
	  }

	  public void setPassword(String password) {
	    this.password = password;
	  }

	  public String getFirstName() {
	    return firstName;
	  }

	  public void setFirstName(String firstName) {
	    this.firstName = firstName;
	  }

	  public String getLastName() {
	    return lastName;
	  }

	  public void setLastName(String lastName) {
	    this.lastName = lastName;
	  }
	  
	  public String getEmail() {
	    return email;
	  }
	  
	  public void setEmail(String email) {
	    this.email = email;
	  }
	  
	  public MultipartFile getProfilePicture()
	  {
		  return this.profilePicture;
	  }
	  
	  public void setProfilePicture(MultipartFile profilePicture)
	  {
		  this.profilePicture = profilePicture;
	  }
	  
	  @Override
	  public boolean equals(Object that)
	  {
		  return EqualsBuilder.reflectionEquals(this, that,
				  "username","password","firstName","lastName","email");
	  }
	  
	  @Override
	  public int hashCode()
	  {
		  return HashCodeBuilder.reflectionHashCode(this,
				  "username","password","firstName","lastName","email");
	  }
}
