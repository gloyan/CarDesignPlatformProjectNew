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
import java.util.Collections;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.sun.org.apache.bcel.internal.generic.IUSHR;

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
	  new LoginPage().setVisible(true);
	  return;
	}
	if(e.getSource()==btnDesign)
	{
	  //will connect to Car Design Page when its designed!
	}
	if(e.getSource()==btnRaisePoint)
	{
	 readUsersFromFile();//tempUsers is filled;
	 deleteContextOfFile();//it will be filled again with updated and remained points acc.to the logined user.
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
		  else //eski bilgileri ile dosyaya ekleyerek degisiklik yapmamis oluyorsun bu kullanici icin.
		  {
		   newUser= new User(s[0],s[1],s[2],s[3],s[4]);
		   newUser.setPoint(Integer.parseInt(s[5]));
		   newUser.setTimeStamp(s[6]);
		   newUser.setLastTrigger(s[7]);
		   loginedUsers.add(newUser);		   
		   JOptionPane.showMessageDialog(null, "Error:Point increment can be done once in a day!");
		   continue;
		  }
		 }
		 catch (ParseException e2)
		 {
		  e2.printStackTrace();
		 }
		}
	    else //baglanan kullanici olmadigi icin update edilmeden,eski haliyle yuklenecek.
	    {
		   newUser= new User(s[0],s[1],s[2],s[3],s[4]);
		   newUser.setPoint(Integer.parseInt(s[5]));
		   newUser.setTimeStamp(s[6]);
		   newUser.setLastTrigger(s[7]);
		   loginedUsers.add(newUser);
	    }
	  }	
	 WriteLoginedUsersToFile();
	}
	 
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
  
  public static void deleteContextOfFile()//to prevent copied items and to initialize the points again.
  {
	  try 
	  {
		FileWriter fw = new FileWriter("pointCalculationForLoginedUsers.txt",false);
		PrintWriter pwOb = new PrintWriter(fw,false);
		pwOb.flush();
		pwOb.close();
		System.out.println("file content is deleted.");
	  } 
	  catch (IOException e1) 
	  {
		e1.printStackTrace();
	  }	
  }
  
  
    public static String findTheWinner()//winner for prize info(biggest point)which will be used by admin.
   {
    	readUsersFromFile();//tempUsers is filled.
    	String t[];
    	String s[]=tempUsers.get(0).split("-");    	
        int max = Integer.parseInt(s[5]);
        String maxPointOwner="";
        // store the length of the ArrayList in variable n
        int n = tempUsers.size(); 
        // loop to find maximum from ArrayList
        for (int i = 1; i < n; i++) 
        {
          t=tempUsers.get(i).split("-");
            if (Integer.parseInt(t[5])>max) 
           {
              maxPointOwner=tempUsers.get(i);
              max =Integer.parseInt(t[5]);
           }
        }
        System.out.println("Maximum is : " + max);
        return maxPointOwner;
   }
  
  public static void EqualizePoints()
  {
 	//to be more fair,the point and last trigger time will be equalized by admin after the prize is given via
	//deleting the context first and then get again the registered users' informations.
	  
 	/*readUsersFromFile();//tempUsers is filled.
 	deleteContextOfFile();
 	User newUser;
 	String s[];
 	lastTriggeredDate = new SimpleDateFormat("dd/MM/yyyy/HH.mm.s").format(Calendar.getInstance().getTime());
 	for (String user : tempUsers)
 	{
 	 s=user.split("-");
 	 newUser=new User(s[0],s[1],s[2],s[3],s[4]);
 	 newUser.setPoint(0);
 	 newUser.setLastTrigger(lastTriggeredDate);//current date and time;
 	 loginedUsers.add(newUser);
 	}
 	WriteLoginedUsersToFile();*/
	  
	deleteContextOfFile();
	WriteUsersToFile();
  }
  
  public static void main(String[]args)
  {  
	 //WriteUsersToFile();//executed at first to create the file and also it will be uncommented when the 
	 //registered users information will be taken from the Register class.
	  
	 getUsersFromFile();//at first comment this and run WriteUsersToFile(),then uncomment it.
	 //and comment WriteUsersToFile() on main() 
	 //to create the file first and then(for the next run calls) read it!
	 
	 //EqualizePoints();//used to initialize the points and equalize last trigger 
	 //time to be more fair about calculation for each prize period.
	 
   //System.out.println("The winner of the prize is: "+"{{{"+findTheWinner()+"}}}");//it will be used when the admin wants to learn the winner.
     new MainPage().setVisible(true);
  }
   
}
