import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Register extends JFrame implements ActionListener,FocusListener
{
  //private static LinkedList users=new LinkedList();
  public static LinkedList<User>users=new LinkedList<User>();
  //public static ArrayList<User>deserializedUsers=new ArrayList<User>();
  public static ArrayList<String>deserializedUsers=new ArrayList<String>();
  public static ArrayList<String>dsrlzUsers;
  String name,surname,username,password,email;
  //private User user;
  JTextField txtName=new JTextField();
  JTextField txtSurname=new JTextField();
  JTextField txtUsername=new JTextField();
  JTextField txtPassword=new JTextField();
  JTextField txtEmail=new JTextField();
  JLabel lblName=new JLabel();
  JLabel lblSurname=new JLabel();
  JLabel lblUsername=new JLabel();
  JLabel lblPassword=new JLabel();
  JLabel lblEmail=new JLabel();
  JButton btnRegister=new JButton("Register");
  public Register() 
  {
	  super("Register/Sign Up");
	  setDefaultCloseOperation(HIDE_ON_CLOSE);
	  setSize(800,500);
	  setLayout(new GridLayout(8,3));
	  btnRegister.setBackground(Color.blue);
	  btnRegister.setForeground(Color.white);	
	  add(new JLabel("Name (Use Just Letters):"));
	  add(txtName);
	  add(lblName);
	  add(new JLabel("Surname (Use Just Letters):"));
	  add(txtSurname);
	  add(lblSurname);
      add(new JLabel("Username (Use Just Words,Not Symbols):"));//. and _ acceptable.
	  add(txtUsername);
	  add(lblUsername);
	  add(new JLabel("Password (Use Just Words,Not Symbols):"));//. and _ acceptable.
	  add(txtPassword);
	  add(lblPassword);
	  add(new JLabel("Email (name.surname_1990@gmail.com):"));
	  add(txtEmail);
	  add(lblEmail);
	  add(new JLabel(""));
	  add(btnRegister);	
	  //user=new User(txtName.getText(),txtSurname.getText(),txtUsername.getText(),
			 //txtPassword.getText(),txtEmail.getText());
	  
	  //email=txtEmail.getText();
	  //user=new User(name,surname,username,password,email);
	  //users.addUser(new User(txtName.getText(),txtSurname.getText(),txtUsername.getText(),
			  //txtPassword.getText(),txtEmail.getText()));	  
	  txtName.addFocusListener(this);
	  txtSurname.addFocusListener(this);
	  txtUsername.addFocusListener(this);
	  txtPassword.addFocusListener(this);
	  txtEmail.addFocusListener(this);
	  btnRegister.addActionListener(this);
    }
  
   @Override
   public void actionPerformed(ActionEvent e) 
   { 
	if (e.getSource()==btnRegister)
	{ 
	  if(lblName.getText()=="Acceptable!"&&lblSurname.getText()=="Acceptable!"&&
	     lblUsername.getText()=="Acceptable!"&&lblPassword.getText()=="Acceptable!"&&
	     lblEmail.getText()=="Acceptable!")		 
      {
	   RegisterUser();
      }
	  else
	   JOptionPane.showMessageDialog(null, "Error:Please enter all inputs valid as it shown to register!");
    }
   }
   private void RegisterUser()
   {
	 boolean isUniqueUser=true;
	 deserializedUsers=readUsersFromFile();
	 String s[];
	 for (String dsrlzUser : deserializedUsers)
	 {  
		s=dsrlzUser.split("-");
		if(s[2].equals(txtUsername.getText())||s[4].equals(txtEmail.getText()))
		//cannot be registered with this username or email if one of them(or both)used before!
		{
		  isUniqueUser=false;
		}
	 }
	 
	 if(isUniqueUser==false)
	 {
	  JOptionPane.showMessageDialog(null, "Error:this username has already taken by another user!"); 
	 }
	 else
	 {
	   users.add(new User(txtName.getText(),txtSurname.getText(),txtUsername.getText(),txtPassword.getText(),txtEmail.getText()));
	   System.out.println("The registered users are:");
	   for(User user:users)
	   {
		System.out.println(user.toString()); 
	   }	
	   writeUsersToFile();//save registered users permanently to the file;
	   new LoginPage().setVisible(true);
	 }
	}

   private static void writeUsersToFile()//writing registered users to file(for permanent data storage);
   {
	   try
		{
		    dsrlzUsers=readUsersFromFile();
		    String s[];
            FileWriter fw = new FileWriter("registeredUserRecords.txt",true);
			/*for (User newUser:users) 
			{
			   if(dsrlzUsers.contains(newUser.getUserName()))
			   {
				 users.
			   }
			    fw.write(newUser+"\n");
			}*/
            if(!dsrlzUsers.isEmpty())
			{ general:for(int i=0;i<users.size();i++)//to prevent copied items!;
			  {
			   for (int j = 0; j <dsrlzUsers.size(); j++) 
			   {
				 s=dsrlzUsers.get(j).split("-");
				 if(s[2].equals(users.get(i).getUserName()))
				  continue general;
			   }			   
				fw.write(users.get(i)+"\n");
			  }
			  //oos.close();
			  fw.close();
			  System.out.println("file writing finished. ");
		   }
            else //if dsrlzUsers(the file) is empty;==>it will be executed on first call(when the file is empty);
            {
              for (User newUser :users)
              {
            	fw.write(newUser+"\n");
			  }	
              fw.close();
			  System.out.println("file writing finished. ");
            }
		}
	   
		/*catch (NotSerializableException e) 
		{
			System.err.println("the class is not serializable: " + e.getMessage());
		}*/
	   
		catch(IOException e)
		{
			System.err.println("there is a file error: " + e.getMessage());
			e.printStackTrace();
		}	   
   }
   
   public static ArrayList<String>readUsersFromFile()
   {
	   try
	   {
	   Scanner sc = new Scanner(new File("registeredUserRecords.txt"));
	   while(sc.hasNext()) //go until the end of the file: 
		{
		   String line = sc.nextLine();
		   deserializedUsers.add(line);
		}
	   sc.close();
	 } 
     catch (FileNotFoundException e) {
		System.err.println("no such file. ");
	}
	   return deserializedUsers;
   }
   
  
   
   private static void getUsersFromFile()
   {
	   try 
		{
		  /*ObjectInputStream ois = new ObjectInputStream
			(new FileInputStream("PermanentData2.txt"));
          while(true)
		  {
			try
		    {
			User user = (User)ois.readObject();		
			System.out.println(user);
		    }
		    catch (ClassNotFoundException e) 
			{
			 // TODO Auto-generated catch block
			 e.printStackTrace();
			}
			catch (EOFException e) 
			{
			 //e.printStackTrace();
			 break;
			 }
			}			
			ois.close();*/
		   Scanner sc = new Scanner(new File("registeredUserRecords.txt"));
		   while(sc.hasNext()) //go until the end of the file: 
			{
				String line = sc.nextLine();
				System.out.println(line);
			}
		   sc.close();
		} 
	   catch (FileNotFoundException e) {
			System.err.println("no such file. ");
		}
		/*catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   */  
   }
   
  @Override
  public void focusGained(FocusEvent e)
  {
     if(e.getSource()==txtName)
    	lblName.setText("");
     else if(e.getSource()==txtSurname)
	    lblSurname.setText("");
     else if(e.getSource()==txtUsername)
    	lblUsername.setText("");
     else if(e.getSource()==txtPassword)
    	lblPassword.setText("");
	  else if(e.getSource() == txtEmail)
		lblEmail.setText(""); 
  }
  
  @Override
  public void focusLost(FocusEvent e) 
  {
	//'+' means at least one character must be entered.
    if(e.getSource()==txtName)//e.g,onur(with lowercase) and cannot be passed with space or empty.
      setItems(txtName,"[A-z]+[\\S]",lblName);
    else if(e.getSource()==txtSurname)//e.g,ozkinaci(with lowercase)and cannot be passed with space or empty.
      setItems(txtSurname,"[A-z]+[\\S]",lblSurname);
    else if(e.getSource()==txtUsername)//can be letter,number,_ and .(some characters) and cannot be passed with space or empty.
      setItems(txtUsername,"[\\w]+[\\S]",lblUsername);//[a-zA-Z0-9_]+
    else if(e.getSource()==txtPassword)//[a-zA-Z0-9_]+ ==>same rules with txtUsername is applied here.
      setItems(txtPassword,"[\\w]+[\\S]",lblPassword);
    else if(e.getSource()==txtEmail)//@gmail.com
     setItems(txtEmail,"[a-z]{1,}[\\w]+[@][\\w]+[.][a-z]{3}",lblEmail);
  }
  
  public void setItems(JTextField txt, String regex, JLabel lbl)
  {
	 if(txt.getText().matches(regex))
	 {
	   lbl.setText("Acceptable!");
	   lbl.setForeground(Color.green);
	 }
	else
	{
	 lbl.setText("Wrong Format!");
	 lbl.setForeground(Color.red);
	}  
  }
  
  public static void main(String[]args)
  {
	 getUsersFromFile();//at first if there is no file,it must be created then it can be read.Therefore,
	 //at first comment 'getUsersFromFile()' and run the program and for the next run calls,
	 //you can cancel the comment since the file is created. The datas in it can be read now.
	 new Register().setVisible(true);
  }
}
