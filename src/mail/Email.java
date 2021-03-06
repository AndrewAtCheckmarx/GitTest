package mail;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;
import com.google.appengine.api.datastore.Key;
@Entity
public class Email implements Serializable{
	private static final long serialVersionUID = 253230271311842474L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	private String content;
	private String from;
	private String to;
	private String label;
	private String date;
	private boolean unread;
	private String subject;
	private String fromName;
	private String senderAge;
	private String senderAvatar;
	@ManyToOne(fetch=FetchType.LAZY)
	private MailUser user;
	
	public void setUser(MailUser mailUser){
		user=mailUser;
	}
	
	public String getContent(){
		return content;
	}
	public String getFrom(){
		return from;
	}
	public String getTo(){
		return to;
	}
	public String getLabel(){
		return label;
	}
	public boolean getUnread(){
		return unread;
	}
	public String getSubject(){
		return subject;
	}
	public String getFromName(){
		return fromName;
	}
	public String getSenderAvatar(){
		return senderAvatar;
	}
	public String getSenderAge(){
		return senderAge;
	}
	public String getDate(){
		return date;
	}
	
	
	public void setKey(Key otherKey){
		key=otherKey;
	}
	public void setContent(String cont){
		content=cont;
	}
	public void setFrom(String sender){
		from=sender;
	}
	public void setTo(String reciever){
		to=reciever;
	}
	public void setLabel(String lab){
		label=lab;
	}
	public void setDate(String currDate){
		date=currDate;
	}
	public void setUnread(boolean read){
		unread=read;
	}
	public void setSubject(String subj){
		subject=subj;
	}
	public void setFromName(String firstName,String lastName){
		fromName=firstName+" "+lastName;
	}
	public void setAvatar(String av){
		senderAvatar=av;
	}
	public void setAge(String otherAge){
		senderAge=otherAge;
	}
	
	
	
}