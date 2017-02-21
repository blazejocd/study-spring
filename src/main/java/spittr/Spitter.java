package spittr;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.*;
import org.apache.commons.lang3.builder.*;
import org.hibernate.Session;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GeneratorType;
import org.hibernate.tuple.ValueGenerator;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

@Entity(name = "Spitter")
@Table(name = "users")
public class Spitter 
{
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id = null;
	
	@NotNull
	@Size(min=3, max=25, message="{username.size}")
	private String username;
	
	@NotNull
	@Size(min=3, max=10)
	@GeneratorType(type = SpitterPasswordEncoder.class, when = GenerationTime.ALWAYS)
	private String password;
	
	@NotNull
	@Size(min=2, max=25, message="{firstName.size}")
	private String firstName;
	
	@NotNull
	@Size(min=2, max=25, message="{lastName.size}")
	private String lastName;
	
	@NotNull
	@Size(min=5, max=25, message="{email.size}")
	private String email;
	
	@Transient
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
	
	public boolean hasId()
	{
		if (null != this.id) {
			return true;
		}
		return false;
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

class SpitterPasswordEncoder implements ValueGenerator<String>
{

	@Override
	public String generateValue(Session session, Object owner)
	{
		Spitter spitter = (Spitter) owner;
		StandardPasswordEncoder passEncoder = new StandardPasswordEncoder("1234");
		return passEncoder.encode(spitter.getPassword());
	}
	
}
