import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginPage extends JFrame implements ActionListener,FocusListener
{
	
  //ArrayList<User>registeredUsers=new ArrayList<User>();
   ArrayList<String>registeredUsers=new ArrayList<String>();
  JButton btnRegister=new JButton("Register");//if the user want to register first!
  JButton btnLogin=new JButton("Login");
  JTextField txtUsername=new JTextField();
  JTextField txtPassword=new JTextField();
  JLabel lblUsername=new JLabel();
  JLabel lblPassword=new JLabel();

  public LoginPage() 
  {
	 super("Login");
	 setDefaultCloseOperation(HIDE_ON_CLOSE);
	 setLocationRelativeTo(null);//open the screen at the middle of the page;
	 setSize(800,450);
	 setLayout(new BorderLayout());
	 btnRegister.setBackground(Color.BLUE);
	 btnRegister.setForeground(Color.white);
     btnLogin.setBackground(Color.darkGray);
     btnLogin.setForeground(Color.white);
     
	 JPanel pnlCenter=new JPanel(new GridLayout(3,3));
	 pnlCenter.add(new JLabel("Username (Use Just Words,Not Symbols):"));
	 pnlCenter.add(txtUsername);
	 pnlCenter.add(lblUsername);
	 pnlCenter.add(new JLabel("Password: (Use Just Words,Not Symbols):"));
	 pnlCenter.add(txtPassword);
	 pnlCenter.add(lblPassword);
	 pnlCenter.add(btnRegister);
	 pnlCenter.add(btnLogin);	 
	 add(pnlCenter,BorderLayout.CENTER);

	 txtUsername.addFocusListener(this);
	 txtPassword.addFocusListener(this);
	 btnRegister.addActionListener(this); 
	 btnLogin.addActionListener(this);
  }

  @Override
  public void actionPerformed(ActionEvent e)
 {
	for (String user : Register.readUsersFromFile())
	//with using the registered users information from the permanent data stored file,
    //we're doing login control here!
	{
		registeredUsers.add(user);
	}
	
	//boolean flag=false;
	if(e.getSource()==btnRegister)
	{
		new Register().setVisible(true);//register/signup page will be opened.
	}
	
	if(e.getSource()==btnLogin)
	{
	  if(lblUsername.getText()=="Acceptable!"&&lblPassword.getText()=="Acceptable!")		 
	 {

      boolean control=false;	
      for(String newUser:registeredUsers)
      {
        String s[]=newUser.split("-");
      if(s[2].equals(txtUsername.getText())&&s[3].equals(txtPassword.getText()))
      //if this user is registered and stored at the file which keeps registered users data.;
       {
    	control=true;
       }        
      }
	  if(control==true)
	  {JOptionPane.showMessageDialog(null, "SUCCESSFULLY LOGINED!");//main page will be opened,if the user logined succesfully.
	  MainPage.loginedUsername=txtUsername.getText();//the main page will keep the logined user info to increase his/her point etc.
	  new MainPage().setVisible(true);
	  }
	  else
	  JOptionPane.showMessageDialog(null, "ERROR:PLEASE FIRST REGISTER WITH USING REGISTER BUTTON!");
     }
	  else
	   JOptionPane.showMessageDialog(null, "Error:Please enter all inputs valid as it shown to login!");
	}
  
 }
    
  @Override
  public void focusGained(FocusEvent e)
  {
	if(e.getSource()==txtUsername)
	   lblUsername.setText("");
	else if(e.getSource()==txtPassword)
	   lblPassword.setText("");
  }

  @Override
  public void focusLost(FocusEvent e)
  {
	if(e.getSource()==txtUsername)//can be letter,number,_ and .(some characters) and cannot be passed with space or empty.
	   setItems(txtUsername,"[\\w]+[\\S]",lblUsername);//[a-zA-Z0-9_]+
	else if(e.getSource()==txtPassword)//[a-zA-Z0-9_]+ ==>same rules with txtUsername is applied here.
	   setItems(txtPassword,"[\\w]+[\\S]",lblPassword);
	
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
	 new LoginPage().setVisible(true);
  }
}
