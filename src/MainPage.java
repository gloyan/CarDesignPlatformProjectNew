import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.rmi.activation.ActivationMonitor;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import sun.net.www.content.image.jpeg;
import sun.util.logging.resources.logging_zh_CN;

public class MainPage extends JFrame implements ActionListener
{
	//static ArrayList<String>registeredUsers=new ArrayList<String>();
	static ArrayList<String>tempUsers=new ArrayList<String>();
	static ArrayList<User>loginedUsers=new ArrayList<User>();
	JButton btnRaisePoint=new JButton("Raise My Point!");
	JButton btnDesign=new JButton("Design My Car!");
	JLabel lblInfo=new JLabel("Click 'Raise My Point!' Button To Win Prize Until 12th of June!");
	//the winner(biggest point owner) will be found on specific days by admin and communicate with him/her
	//via his/her registered email to tell them he/she is the winner and will get this(phone etc.) prize.
	JPanel pnlCenter=new JPanel(new GridLayout(3,1));
	//int triggerCount=0;
	static String loginedUsername;//get the Logined user's username to increase his/her point accordingly.
	static String lastTriggeredDate;
   public MainPage() 
   {
	super("Welcome To The DESIGN YOUR CAR Platform!");
	setDefaultCloseOperation(HIDE_ON_CLOSE);
	setSize(870,500);
	//setLayout(new GridLayout(3,2));
	setLayout(new BorderLayout());
	btnDesign.setBackground(Color.BLUE);
	btnDesign.setForeground(Color.white);
	btnRaisePoint.setBackground(Color.darkGray);
	btnRaisePoint.setForeground(Color.white);	
	JPanel pnlNorth=new JPanel(new GridLayout(1,1));
	lblInfo.setSize(300,600);
	lblInfo.setForeground(Color.RED);
	lblInfo.setFont(new Font("Serif",Font.BOLD,16));
	pnlNorth.add(lblInfo);
	pnlNorth.add(new JLabel());
	//JPanel pnlCenter=new JPanel(new GridLayout(3,1));//defined at global.
	pnlCenter.add(btnDesign);
	pnlCenter.add(btnRaisePoint);	 
	add(pnlNorth,BorderLayout.NORTH);
	add(pnlCenter,BorderLayout.CENTER);
	btnDesign.addActionListener(this); 
	btnRaisePoint.addActionListener(this);
	}
 
 @Override
 public void actionPerformed(ActionEvent e)
 {
	if(loginedUsername==null)//warn user for clicking the button in main page first he/she must login.
	{
	  JOptionPane.showMessageDialog(null, "Error:You must login to the system first!"); 
	  return;
	}
	if(e.getSource()==btnDesign)
	{
	  //will connect to Car Design Page when its designed!
	}
	if(e.getSource()==btnRaisePoint)
	{
	 readUsersFromFile();//tempUsers is filled;
	 Date date1,date2;
	 long diff,diffDays;
	 String s[];
	 User newUser;
	 lastTriggeredDate = new SimpleDateFormat("dd/MM/yyyy/HH.mm.s").format(Calendar.getInstance().getTime());
	 /*if there is a copied item in the file(not deleted yet),the system will get last added one 
	    anyway(control can be provided).*/
	 for (String user : tempUsers)
	 {
	   s=user.split("-");
	   if(s[2].equals(loginedUsername))
	  {
		 try
		{
		  date1=new SimpleDateFormat("dd/MM/yyyy/HH.mm.s").parse(s[7]);
		  date2=new SimpleDateFormat("dd/MM/yyyy/HH.mm.s").parse(lastTriggeredDate);
		  diff=date2.getTime()-date1.getTime();
		  diffDays=(diff/(1000 * 60 * 60 * 24))% 365;
		  if(diffDays>=1)//point increment can be done(at least 24 hours passed for last trigger for this logined user)!
		  {
			newUser=new User(s[0],s[1],s[2],s[3],s[4]);
			newUser.setPoint(Integer.parseInt(s[5])+1);//point increased!
			newUser.setTimeStamp(s[6]);
			newUser.setLastTrigger(lastTriggeredDate);
			loginedUsers.add(newUser);
		  }
		  else
		  {JOptionPane.showMessageDialog(null, "Error:Point increment can be done once in a day!");
		   return;
		  }
		 }
		 catch (ParseException e2)
		 {
		  e2.printStackTrace();
		 }
		} 			
	  }	
	 WriteLoginedUsersToFile();
	}
	 
   }
 
 public static String findTheWinner()//winner for prize info(biggest point)which will be used by admin.
 {
	//to get accurate winner information,the file must be controlled frequently!
	readUsersFromFile();//tempUsers arraylist is filled.  
    Date date1,date2;  
	String s[],t[];
    String biggest="";
    for (int i = 0;i <tempUsers.size();i++)
   {
      if(i!=tempUsers.size()-1)//to prevent null condition(last index+1=null which is used for t[])
    {
      s=tempUsers.get(i).split("-");
      t=tempUsers.get(i+1).split("-");
	  if(Integer.parseInt(s[5])>Integer.parseInt(t[5]))//if previous one's point is bigger than the next one.
		biggest=tempUsers.get(i);
	  else if(Integer.parseInt(s[5])<Integer.parseInt(t[5]))
		biggest=tempUsers.get(i+1);
	  else //if points are equal,compare according to the registered date;
	  {
		try
		{
		  date1=new SimpleDateFormat("dd/MM/yyyy/HH.mm.s").parse(s[6]);
		  date2=new SimpleDateFormat("dd/MM/yyyy/HH.mm.s").parse(t[6]);
		  if(date1.compareTo(date2)>0)//date1 occurs after date2(date2 is older user for us)
			biggest=tempUsers.get(i+1);
		  else if(date1.compareTo(date2)<0)//date1 occurs before date2(date1 is older user for us)
			biggest=tempUsers.get(i);
		  else//if they're equal,but it has less possibility since we compare until 'second(s)' for date comparison.
		     biggest=tempUsers.get(i);	
		 } 
		catch (ParseException e)
		{
			e.printStackTrace();
		}			 	
	  }
	}
      else//if index(i)==tempUsers.size-1(last index);
    	continue;
   }
    return biggest;	 
 }

 
 public static void WriteLoginedUsersToFile()
 {
  /*write the new increased point and new 'point increment' button trigger time to the
   * file which also contains registered user's informations.If there is a copied item (since this files
   * frequently gets the registered user's informations) it can be detected from point
   * because the point is assigned as zero if there is a new user registered and this point will be increased then
   * (The point's order on the stored data is sixth item(fifth index for split)). This detected copied item
   * can be deleted by admin manually and the biggest point stored data can stay.
   * For example for the same user(same username)data,the one which contains biggest point will stay but the less one
   * is going to be deleted to prevent the confusion.
   */
	try
	{
	  FileWriter fw = new FileWriter("pointCalculationForLoginedUsers.txt",true);
	  for (User user:loginedUsers)
	  {
		fw.write(user+"\n");
	  }
	 fw.close(); //must close!
	 System.out.println("file writing finished. ");
	}
	catch(IOException e)
	{
	 System.err.println("there is a file error: " + e.getMessage());
	}
 }
 
  public static void WriteUsersToFile()
  {
	//write registered users information(from 'Register' class) to this new file too.
	try
	{
	  //String s[];//,t[];
	  FileWriter fw = new FileWriter("pointCalculationForLoginedUsers.txt",true);
	   for(String user:Register.readUsersFromFile())//write registered users information to this new file too.
	   {
		fw.write(user+"\n");	
	   } 
	 fw.close(); //must close!
	 System.out.println("file writing finished. ");
	}
	catch(IOException e)
	{
	 System.err.println("there is a file error: " + e.getMessage());
	}
  }
  
  private static void readUsersFromFile()
  {
	//add the items in the file to arraylist(tempUsers) to provide efficent control.
	try 
	{
	  Scanner sc = new Scanner(new File("pointCalculationForLoginedUsers.txt"));
	   while(sc.hasNext()) //go until the end of the file: 
	   {
		String line = sc.nextLine();
		tempUsers.add(line);
		//System.out.println(line);
		}
		sc.close();
	} 
	 catch (FileNotFoundException e) {
			System.err.println("no such file. ");
		}
  }
  
  
  private static void getUsersFromFile()//to print the items in the file.
  {
 	try 
    {
 	Scanner sc = new Scanner(new File("pointCalculationForLoginedUsers.txt"));
 	 while(sc.hasNext()) //go until the end of the file: 
     {
 	String line = sc.nextLine();
 	System.out.println(line);
 	}
 	sc.close();
    } 
    catch (FileNotFoundException e)
    {
 	System.err.println("no such file. ");
    }
  }
  
  public static void main(String[]args)
  {  
	 //WriteUsersToFile();//executed at first to create the file and also it will be uncommented when the 
	 //registered users information will be taken from the Register class.
	 getUsersFromFile();//at first comment this and run WriteUsersToFile(),then uncomment it.
	 //and comment WriteUsersToFile() on main() 
	 //to create the file first and then(for the next run calls) read it!
	 
   //System.out.println("The winner of the prize is: "+"{{{"+findTheWinner()+"}}}");//it will be used when the admin wants to learn the winner.
     new MainPage().setVisible(true);
  }
   
}
