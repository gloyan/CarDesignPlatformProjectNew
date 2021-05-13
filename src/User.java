import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;

public class User implements Serializable,Comparable<User>
{
  private String name;
  private String surname;
  private String userName;
  private String password;
  private String email;
  private int point;
  //private LocalDateTime registeredDate;
  private String timeStamp;//registeredDate
  private String lastTrigger;//last point increment button trigger time
  public User(String name,String surname,String username,String password,String email) 
  {
	setName(name);
	setSurname(surname);
	setUserName(username);
	setPassword(password);
	setEmail(email);
	this.point=0;//baslangic icin sifir olarak ataniyor,kullanici ilk tanimlandiginda.
	//registeredDate=java.time.LocalDateTime.now();
   timeStamp = new SimpleDateFormat("dd/MM/yyyy/HH.mm.s").format(Calendar.getInstance().getTime());
   lastTrigger=new SimpleDateFormat("dd/MM/yyyy/HH.mm.s").format(Calendar.getInstance().getTime());
  }
  public String getUserName() {
	return userName;
  }
  public void setUserName(String userName) {
	this.userName = userName;
  }
  public String getPassword() {
	return password;
 }
  public void setPassword(String password) {
	this.password = password;
  }
  public int getPoint() {
	return point;
  }
  public void setPoint(int point)
  {
	this.point = point;
 }
  public String getName() {
	return name;
  } 
  public void setName(String name) {
	this.name = name;
  }
  public String getSurname() {
	return surname;
  }
  public void setSurname(String surname) {
	this.surname = surname;
  }
  public String getEmail() {
	return email;
  }
  public void setEmail(String email) {
	this.email = email;
  }
  public String getTimeStamp() {
	return timeStamp;
}
  public void setTimeStamp(String timeStamp) {
	this.timeStamp = timeStamp;
}
  public String getLastTrigger() {
		return lastTrigger;
	}
	public void setLastTrigger(String lastTrigger) {
		this.lastTrigger = lastTrigger;
	}

  @Override
	public String toString() {
		/*return "//Name:"+this.name+"//Surname:"+this.surname+"//Username:"+this.userName
		  +"//Password:"+this.password+"//Email:"+this.email+"//User's Point:"+this.point
		   +"//Registered Date and Time:"+this.timeStamp;*/
	  return this.name+"-"+this.surname+"-"+this.userName+"-"+this.password+"-"
			  +this.email+"-"+this.point+"-"+this.timeStamp+"-"+this.lastTrigger;
	}
@Override
public int compareTo(User o) {
	// TODO Auto-generated method stub
	return this.point- o.point;
}

  
}
