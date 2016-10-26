package mail;
import java.io.Serializable;
import java.util.*;
import javax.persistence.*;
import com.google.appengine.api.datastore.Key;
@Entity
public class MailUser implements Serializable  {
	private static final long serialVersionUID = 6970406296256642328L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private String age;
	private String avatar; 
	@OneToMany(mappedBy="user",cascade=CascadeType.ALL)
	private Vector<Email> mailbox;
	
	public void setMailBox(Vector<Email> box){
		this.mailbox=new Vector<Email>(box);
	}
	public Vector<Email> getMailBox(){
		return mailbox;
	}
	public void setKey(Key otherKey){
		key=otherKey;
	}
	public Key getKey(){
		return key;
	}
	public void setName(String first,String last ){
		firstName=first;
		lastName=last;
	}
	public String getFirstName(){
		return firstName;
	}
	public String getLastName(){
		return lastName;
	}
	public void setAge(String ages){
		age=ages;
	}
	public String getAge(){
		return age;
	}
	public void setUserName(String login){
		username=login;
	}
	public String getUsername(){
		return username;
	}
	public void setPassword(String pass){
		password=pass;
	}
	public String getPassword(){
		return password;
	}
	public void setAvatar(String av){
		avatar=av;
	}
	public String getAvatar(){
		return avatar;
	}
	
	
}
