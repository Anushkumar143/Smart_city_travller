package anu;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
public class Roni extends JFrame 
{
	static class user
	{
		String name1,name2,dob,phone,email,gender,pan,location;
		
		user(String name1,String name2,String dob,String phone,String email,String gender,String pan,String location)
		{
			this.name1=name1;
			this.name2=name2;
			this.dob=dob;
			this.phone=phone;
			this.email=email;
			this.gender=gender;
			this.pan=pan;
			this.location=location;
		}
	}
	private static user currentuser;
	private static final long serialVersionUID = 1L;
	private static JPanel mainPanel,sidebarpanel;
	private static CardLayout  card;
	private static JTextField name1,name2,dob,phone,email,pan;
	private static JTextArea location;
	private static ButtonGroup genderGroup;
	private static boolean sidebarvisible=true;
	private static final String user_file="d:\\anush\\users.csv";
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
					Roni f = new Roni();
					f.setVisible(true);
				}
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}
	public Roni() 
	{
		setTitle("Smart City Travellers");
        setSize(800,600);
        setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		card = new CardLayout();
		mainPanel = new JPanel(card);
		mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
		mainPanel.setBackground(new Color(240,245,250));
	    
		mainPanel.add(dashboardScreen(), "dashboard");
		mainPanel.add(loginScreen(), "login");
		mainPanel.add(signScreen(), "signin");
        mainPanel.add(createuser(),"users");
        
		 getContentPane().setLayout(new BorderLayout());
		 getContentPane().add(mainPanel, BorderLayout.CENTER);
	}
	static JPanel loginScreen()
	{
		JPanel main=new JPanel(new BorderLayout());
	
		main.setBackground(new Color(240, 245, 255));
		
		main.add(createHeader("SMART CITY TRAVELS"), BorderLayout.NORTH);
		main.add(createFooter("© 2025 Smart City Travellers"), BorderLayout.SOUTH);

		JPanel center=new JPanel();
		center.setLayout(new BoxLayout(center,BoxLayout.Y_AXIS));
		center.setBackground(new Color(240,248,255));
		
		center.add(Box.createVerticalGlue());
		
		JPanel form=new JPanel();
		form.setLayout(new BoxLayout(form,BoxLayout.Y_AXIS));
		form.setBackground(Color.white);
		form.setBorder(BorderFactory.createCompoundBorder
			(BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
				new EmptyBorder(30, 30, 30, 50)));
		form.setMaximumSize(new Dimension(400, 300));
		form.setAlignmentX(Component.CENTER_ALIGNMENT);

		JPanel user=new JPanel();
		user.setBackground(Color.WHITE);
		JLabel name=new JLabel("username :");
		JTextField namefield=new JTextField(20);
		user.add(name);
		user.add(namefield);

		JPanel pass=new JPanel();
		pass.setBackground(Color.WHITE);
		
		JLabel password=new JLabel("password :");
		JPasswordField passwordfield = showandhide(20);
		pass.add(password);
		pass.add(passwordfield);
	    
		JButton button=new JButton("login");
		stylebutton(button,new Color(0, 123, 255));
		button.addActionListener(e-> {
			String username=namefield.getText().trim();
			String userpassword=new String(passwordfield.getPassword());
			if(username.isEmpty()&&userpassword.isEmpty())
			{
				JOptionPane.showMessageDialog(null,"please enter user and password");
				return;
			}
			if(validatelogin(username,userpassword))
			{
				JOptionPane.showMessageDialog(null,"login sucessfull");
				card.show(mainPanel, "dashboard");
				namefield.setText(null);
				passwordfield.setText(null);
			}
			else
			{
					JOptionPane.showMessageDialog(null,"invalid username and password");
			}
										});
				
		JPanel signup=new JPanel();
		signup.setBackground(Color.WHITE);
		JLabel details=new JLabel("don't have an account?");
		JLabel signinLabel=new JLabel("<html><u>sign up</u></html>");
		signinLabel.setForeground(new Color(0, 123, 255));
		signinLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		signup.add(details);
		signup.add(signinLabel);
	    
		signinLabel.addMouseListener(new MouseAdapter() 
		{
			public void mouseClicked(MouseEvent e)
			{
				card.show(mainPanel,"signin");
				namefield.setText(null);
				passwordfield.setText(null);
				
				
			}
			public void mouseEntered(MouseEvent e)
			{
				signinLabel.setForeground(Color.blue);
			}
			public void mouseExited(MouseEvent e)
			{
				signinLabel.setForeground(Color.red);
			}
		});
	
		form.add(user);
		form.add(Box.createVerticalStrut(15));
		form.add(pass);
		form.add(Box.createVerticalStrut(20));
		form.add(button);
		form.add(Box.createVerticalStrut(15));
		form.add(signup);
		
		center.add(form);
		center.add(Box.createVerticalGlue());

		main.add(center);

	return main;
	}
	static JPanel signScreen()
	{
		JPanel main =new JPanel(new BorderLayout());
		
		main.setBackground(new Color(240, 245, 255));
	 
		
		main.add(createHeader("REGISTERATION FORM"),BorderLayout.NORTH);
		main.add(createFooter("© 2025 Smart City Travellers"),BorderLayout.SOUTH);
		
		JPanel center=new JPanel();
		center.setLayout(new BoxLayout(center,BoxLayout.Y_AXIS));
		center.setBackground(Color.white);
		center.setBorder(new EmptyBorder(20,30,20,30));
		
		JPanel personaldetails=new JPanel(new GridLayout(0,2,10,7) );
		personaldetails.setBackground(Color.white);
		personaldetails.setBorder(BorderFactory.createTitledBorder("personal details"));
		
		personaldetails.add(new JLabel("first name :"));
		name1=new JTextField(15);
		personaldetails.add(name1);
		personaldetails.add(new JLabel("Last name :"));
		name2=new JTextField(15);
		personaldetails.add(name2);
		personaldetails.add(new JLabel("date of birth:"));
		dob=new JTextField(15);
		personaldetails.add(dob);
		personaldetails.add(new JLabel("phone :"));
		phone=new JTextField(15);
		personaldetails.add(phone);
		personaldetails.add(new JLabel("email id :"));
		email=new JTextField(15);
		personaldetails.add(email);
		
		JPanel sex=new JPanel(new FlowLayout(FlowLayout.LEFT,5,5));
		sex.setBackground(Color.WHITE);
		JLabel gender=new JLabel("gender :");
		JRadioButton genderm=new JRadioButton("male");
		JRadioButton genderf=new JRadioButton("female");
		genderGroup=new ButtonGroup();
		genderGroup.add(genderf);
		genderGroup.add(genderm);
		genderm.setOpaque(false);
		genderf.setOpaque(false);
		sex.add(gender);
		sex.add(genderm);
		sex.add(genderf);
		
		JPanel form=new JPanel();
		form.setBackground(Color.WHITE);
		form.setLayout(new BoxLayout(form,BoxLayout.Y_AXIS));
		form.setBorder(BorderFactory.createTitledBorder("additional details"));
		JPanel aadhar=new JPanel(new FlowLayout(FlowLayout.LEFT,10,5));
		aadhar.setBackground(Color.WHITE);
		aadhar.add(new JLabel("aadhar/pan no :"));
		pan=new JTextField(15);
		aadhar.add(pan);
		form.add(aadhar);
		
		JPanel address=new JPanel(new BorderLayout(10,5));
		address.setBackground(Color.WHITE);
		address.add(new JLabel("address :"),BorderLayout.NORTH);
		location=new JTextArea(3,20);
		location.setLineWrap(true);
		address.add(new JScrollPane(location));
		form.add(address);
		
		center.add(personaldetails);
		center.add(sex);
		center.add(form);
		
		JPanel button=new JPanel();
		button.setLayout(new FlowLayout(FlowLayout.CENTER,20,10));
		button.setBackground(Color.WHITE);
		
		JButton submit=new JButton("submit");
		stylebutton(submit,new Color(40, 167, 69));
		button.add(submit);
		
		//((JButton)buttonPanel.getComponent(0)).addActionListener(e -> card.show(mainPanel,"dashboard"));
		submit.addActionListener(e->{
			if(name1.getText().isEmpty() || name2.getText().isEmpty() ||
				dob.getText().isEmpty() || phone.getText().isEmpty() ||
					email.getText().isEmpty() || pan.getText().isEmpty() || 
						location.getText().isEmpty() || (!genderf.isSelected() && !genderm.isSelected()))
						{
							JOptionPane.showMessageDialog(null,"data is missing");
							return;
						}	
		String g=genderm.isSelected()?"male":"female";
				
		currentuser = new user(name1.getText(), name2.getText(), dob.getText(), phone.getText(),
                email.getText(), g, pan.getText(), location.getText());
		
        submit.setText("Processing...");
        submit.setEnabled(false);
		        
        Timer timer = new Timer(2000, evt ->{
        	card.show(mainPanel,"users");
        	submit.setText("Submit");
        	submit.setEnabled(true);
		       								});
		timer.setRepeats(false);
		timer.start();
									});
		
		JButton clear=new JButton("clear");
		stylebutton(clear,new Color(108, 117, 125));
		button.add(clear);
		
		clear.addActionListener(e->{
			name1.setText(null);
			name2.setText(null);
			dob.setText(null);
			phone.setText(null);
			email.setText(null);
			pan.setText(null);
			location.setText(null);
			genderm.setSelected(false);
			genderf.setSelected(false);
		});
		JButton back = new JButton("Back");
		stylebutton(back,new Color(255, 193, 7));
		back.addActionListener(e -> card.show(mainPanel, "login"));
		button.add(back);
		
		center.add(button);
		
		main.add(center);
		
		JScrollPane scrollPane=new JScrollPane(center);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
		main.add(scrollPane,BorderLayout.CENTER);

		return main;
	}
	static JPanel createuser()
	{
		JPanel main=new JPanel();
		
		main.setLayout(new BorderLayout());
		main.setBackground(new Color(240,245,250));

		main.add(createHeader("CREATE ACCOUNT"),BorderLayout.NORTH);
		main.add(createFooter("CREATE ACCOUNT"),BorderLayout.SOUTH);
		
		JPanel center=new JPanel();
		center.setLayout(new BoxLayout(center,BoxLayout.Y_AXIS));
		center.setBackground(new Color(240,248,255));
		
		center.add(Box.createVerticalGlue());
		
		JPanel form=new JPanel();
		form.setLayout(new BoxLayout(form,BoxLayout.Y_AXIS));
		form.setBorder(new EmptyBorder(30,40,30,40));
		form.setMaximumSize(new Dimension(400,250));
		form.setAlignmentX(Component.CENTER_ALIGNMENT);
		form.setBorder(BorderFactory.createCompoundBorder
				(BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
					new EmptyBorder(25, 25, 20,25)));	
		form.setBackground(Color.WHITE);
	
		JPanel user=new JPanel();
		user.setBackground(Color.WHITE);
		user.add(new JLabel("username :"));
		JTextField name=new JTextField(20);
		user.add(name);
		
		JPanel pass=new JPanel();
		pass.setBackground(Color.WHITE);
		pass.add(new JLabel("password :"));
		JPasswordField password=showandhide(20);
		pass.add(password);
		
		JPanel button=new JPanel(new FlowLayout(FlowLayout.CENTER,20,10));
		button.setBackground(Color.white);
		
		JButton submit=new JButton("submit");
		stylebutton(submit,new Color(40,167,69));
		button.add(submit);
		
		submit.addActionListener(e->{
			
			String username=name.getText().trim();
			String passwordcheck=new String(password.getPassword());
			
			if(username.isEmpty()||passwordcheck.isEmpty()) 
			{
				JOptionPane.showMessageDialog(null,"please enter username and password");
				return;
			}
			if(nameusescheck(username))
			{
				JOptionPane.showMessageDialog(null,"username is already exists");
				return;
			}
			else
			{
				if(saveusercredentials(username, passwordcheck ,currentuser)) 
				{
				JOptionPane.showMessageDialog(null,"data submitted");
				card.show(mainPanel,"login");
				
				name.setText(null);
				password.setText(null);
				cleardatafield();
				}
				else {
				JOptionPane.showMessageDialog(null,"error");
				}
			}
									});
		
		JButton back=new JButton("back");
		stylebutton(back,new Color(108, 117, 125));
		button.add(back);
		back.addActionListener(e->card.show(mainPanel,"signin"));
		
		
		form.add(Box.createVerticalStrut(20));
		form.add(user);
		form.add(Box.createVerticalStrut(15));
		form.add(pass);
		form.add(Box.createVerticalStrut(20));
		form.add(button);
		
		center.add(form,BorderLayout.CENTER);
		
		center.add(Box.createVerticalGlue());
		
		main.add(center);
	
		return main; 
	}
	public static JPanel dashboardScreen()
	{
		JPanel dashboard=new JPanel(new BorderLayout());
		
		JPanel topnav=topmainheader();
		dashboard.add(topnav,BorderLayout.NORTH);
		
		JPanel sidebar=new JPanel(new BorderLayout()); 
		sidebarpanel=sidenavigationbar();
		sidebar.add(sidebarpanel,BorderLayout.WEST);
		
		JPanel center=new JPanel(new BorderLayout());
		
		JPanel centernav=centerheader();
		center.add(centernav,BorderLayout.NORTH);
		
		JPanel details=centeralldetails();
		center.add(details,BorderLayout.CENTER);
		
		sidebar.add(center,BorderLayout.CENTER);
		dashboard.add(sidebar);

		return dashboard;
	}
	
	
	
	public static JPanel topmainheader()
	{
		JPanel top=new JPanel(new BorderLayout());
		top.setPreferredSize(new Dimension(1200,60));
		top.setBackground(Color.WHITE);
		top.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.LIGHT_GRAY));
		
		JPanel leftPanel=new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));
		leftPanel.setBackground(Color.WHITE);
		
		JButton sidebarbtn=new JButton("☰");
		sidebarbtn.setFont(new Font("Arial", Font.BOLD, 18));
		sidebarbtn.setPreferredSize(new Dimension(40, 40));
		sidebarbtn.setFocusPainted(false);
		sidebarbtn.setBackground(Color.white);
		sidebarbtn.setForeground(new Color(0, 102, 204));
		sidebarbtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		sidebarbtn.setBorderPainted(false);
		sidebarbtn.setToolTipText("sidebarbutton");
		sidebarbtn.addActionListener(e->{	
										sidebarvisible=!sidebarvisible;
										if(sidebarvisible)
										{
											sidebarpanel.setVisible(true);
											sidebarpanel.setPreferredSize(new Dimension(200,600));
										}
										else
										{
											sidebarpanel.setVisible(false);
											sidebarpanel.setPreferredSize(new Dimension(0,0));
										}
										sidebarpanel.revalidate();
										sidebarpanel.repaint();
										sidebarbtn.setToolTipText(sidebarvisible?"hide":"show");										});
		leftPanel.add(sidebarbtn);
				
		JLabel logo=new JLabel("SMARTCITYTRAVELLERS");
		logo.setFont(new Font("Arial",Font.BOLD,24));
		logo.setForeground(new Color(0, 102, 204));
		leftPanel.add(logo);
				
		JPanel rightPanel=new JPanel(new FlowLayout(FlowLayout.RIGHT,15,20));
		rightPanel.setBackground(Color.WHITE);
		rightPanel.setBorder(new EmptyBorder(0,0,0,20));
		
		String [] topmenus= {"app","FAQs","usd","customer support","find booking"};
		for(String items:topmenus)
		{
			JButton menubtn=new JButton(items);
		    menubtn.setFont(new Font("Arial", Font.PLAIN, 14));
		    menubtn.setBackground(Color.white);
		    menubtn.setForeground(Color.black);
		    menubtn.setFocusPainted(false);
		    menubtn.setBorderPainted(false);
			menubtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			
			menubtn.addMouseListener(new MouseAdapter() 
									{
										public void mouseEntered(MouseEvent e)
										{
											menubtn.setForeground(new Color(0, 102, 204));
										}	
										public void mouseExited(MouseEvent e)
										{
											menubtn.setForeground(Color.black);
										}
									});
			
			rightPanel.add(menubtn);
		}
		
		JButton logout=new JButton("LOGOUT");
		stylebutton(logout,new Color(0, 102, 204));
		
		logout.addActionListener(e->{
									int confirm=JOptionPane.showConfirmDialog(null,
										"are you sure you want to logout?",
											"logoutconfirmation",
												JOptionPane.YES_NO_OPTION);
									if(confirm==JOptionPane.YES_OPTION)
									card.show(mainPanel,"login");
									});
		
		rightPanel.add(logout);
		
		top.add(leftPanel,BorderLayout.WEST);
		top.add(rightPanel,BorderLayout.EAST);
		
		return top;
	}
	public static JPanel sidenavigationbar()
	{
		JPanel main=new JPanel(new BorderLayout());
		
		JPanel sidebar=new JPanel();
		sidebar.setLayout(new BoxLayout(sidebar,BoxLayout.Y_AXIS));
		sidebar.setBackground(Color.WHITE);
		sidebar.setBorder(BorderFactory.createMatteBorder(0,0,0,1,new Color(200,200,200)));
		
		String[] name= {"Trains","Flights","Hostel & Homes","Bus & Travelling","Attraction & tours"
						,"Cars & Cab Booking","Cruises","Insurance","Private Tours"
						,"Group Tours","Gifts","Trip Planner","Travel Inspiration",
						"Map","Deals"};
		
		for(String btn:name)
		{
			sidebarbuttonstyle(sidebar,btn);
		}	
		JScrollPane scroll=new JScrollPane();
	    scroll.setViewportView(sidebar);
	    scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	    scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	    scroll.setBorder(BorderFactory.createEmptyBorder());
	    scroll.setPreferredSize(new Dimension(200, 600));
	    
	    main.add(scroll,BorderLayout.CENTER);
		return main;
	}
	public static JPanel centerheader()
	{
		 JPanel titlePanel = new JPanel();
		 titlePanel.setBackground(new Color(0, 102, 204));
		 titlePanel.setBorder(new EmptyBorder(15, 0, 15, 0));
		 
		 JLabel title = new JLabel("Your Trip Starts Here");
		 title.setFont(new Font("Arial", Font.BOLD, 20));
		 title.setForeground(Color.WHITE);
		 titlePanel.add(title);
		 
		return titlePanel;
	}
	public static JPanel centeralldetails()
	{
	    JPanel main = new JPanel(new BorderLayout());
	    main.setBackground(Color.WHITE);
	    
	    JPanel gap = new JPanel(new BorderLayout());
	    gap.setBackground(Color.WHITE);
	    gap.setBorder(new EmptyBorder(15, 10, 25, 10));
			
	    JPanel buttonspanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
	    buttonspanel.setBackground(Color.WHITE);
			
	    JPanel singleline = new JPanel();
	    singleline.setLayout(new BoxLayout(singleline, BoxLayout.X_AXIS));
	    singleline.setBackground(Color.WHITE);
			
	    String[] names = {"Flights","Trains","Cars & Cab","Hotels & Homes","Attraction"};
			
	    CardLayout card = new CardLayout();
	    JPanel form = new JPanel(card);
	    // ✅ same as your old code: fields look good inside this width/height
	    form.setPreferredSize(new Dimension(700, 230));
	    
	    for(String btns : names)
	    {
	        JButton tab = new JButton(btns);
	        tab.setPreferredSize(new Dimension(130, 30));
	        tab.setMinimumSize(new Dimension(130, 30));
	        tab.setMaximumSize(new Dimension(130, 30));
	        singleline.add(tab);
	        
	        if(btns.equals("Flights"))
	        {
	            tab.setBackground(new Color(0, 102, 204));
	            tab.setForeground(Color.WHITE);
	        }
	        else 
	        {
	            tab.setBackground(Color.WHITE);
	            tab.setForeground(Color.BLACK);	
	        }
	        
	        JPanel formtab = customerdetailstab(btns); // 👈 each Tab's content (fields + search)
	        form.add(formtab, btns);
	        
	        tab.addActionListener(e -> {
	            for(Component c : singleline.getComponents())
	            {
	                if(c instanceof JButton)
	                {
	                    JButton otherTab = (JButton) c;
	                    otherTab.setBackground(Color.WHITE);
	                    otherTab.setForeground(Color.BLACK);
	                }
	            }
	            tab.setBackground(new Color(0, 102, 204));
	            tab.setForeground(Color.WHITE);
	            card.show(form, btns);
	        });
	    }
	    
	    buttonspanel.add(singleline);
	    gap.add(buttonspanel, BorderLayout.CENTER);
			
	    main.add(gap, BorderLayout.NORTH);
	    main.add(form, BorderLayout.CENTER);  // ✅ no wrapper, same as original
	    return main;
	}
	public static JPanel customerdetailstab(String t)
	{
	    // WRAPPER panel - this prevents the Search button from drifting to center
	    JPanel wrapper = new JPanel(new BorderLayout());
	    wrapper.setBackground(Color.WHITE);
	    wrapper.setBorder(new EmptyBorder(20, 20, 20, 20));

	    // ====== FIELD ROW (same as your old code) ======
	    JPanel form = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
	    form.setBackground(Color.WHITE);

	    if (t.equals("Flights")) {
	        String[] title = {"From","To","Departure","Passengers","Class"};
	        String[] holder = {"Departure city","Arrival city","Select date","1 adult","Economy"};
	        for (int i = 0; i < title.length; i++)
	            form.add(createField(title[i], holder[i]));
	    }
	    else if (t.equals("Trains")) {
	        String[] title = {"From","To","Departure","Passengers","Class"};
	        String[] holder = {"Departure city","Arrival city","Select date","1 adult","Sleeper"};
	        for (int i = 0; i < title.length; i++)
	            form.add(createField(title[i], holder[i]));
	    }
	    else if (t.equals("Hotels & Homes")) {
	        String[] title = {"Destination","Check-in","Check-out","Guests","Rooms"};
	        String[] holder = {"Enter city or hotel","Select date","Select date","2 guests","1 room"};
	        for (int i = 0; i < title.length; i++)
	            form.add(createField(title[i], holder[i]));
	    }
	    else if (t.equals("Attraction")) {
	        String[] title = {"Destination","Date","Travelers","Tour Type"};
	        String[] holder = {"City or attraction","Select date","2 adults","Any"};
	        for (int i = 0; i < title.length; i++)
	            form.add(createField(title[i], holder[i]));
	    }
	    else {
	        String[] title = {"Pick-up Location","Pick-up Date","Drop-off Date","Passengers"};
	        String[] holder = {"Airport,city","Select date","Select date","1 adult"};
	        for (int i = 0; i < title.length; i++)
	            form.add(createField(title[i], holder[i]));
	    }

	    // Add fields to TOP of wrapper
	    wrapper.add(form, BorderLayout.NORTH);

	    // ====== SEARCH BUTTON (CENTERED BELOW FIELDS) ======
	    JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
	    searchPanel.setBackground(Color.WHITE);

	    JButton searchBtn = new JButton("SEARCH " + t.toUpperCase());
	    stylebutton(searchBtn, new Color(255, 87, 34));
	    searchPanel.add(searchBtn);

	    // Add search panel BELOW form
	    wrapper.add(searchPanel, BorderLayout.SOUTH);

	    return wrapper;
	}

	public static JPanel createField(String c,String placeholder)
	{
		JPanel form=new JPanel(new BorderLayout());
		form.setPreferredSize(new Dimension(150, 60));
		form.setBackground(Color.white);
		
		JLabel name=new JLabel(c);
	    name.setFont(new Font("Arial", Font.BOLD, 12));
	    name.setForeground(new Color(0, 102, 204)); // Blue color
	    name.setBorder(new EmptyBorder(0, 0, 5, 0));
		form.add(name,BorderLayout.NORTH);
		
		String currentplaceholder=placeholder;
		
		JTextField names=new JTextField(currentplaceholder,12);
		names.setFont(new Font("Arial", Font.PLAIN, 12));
	    names.setPreferredSize(new Dimension(150, 32));
	    names.setBackground(new Color(248, 248, 248)); 
	    names.setBorder(BorderFactory.createCompoundBorder(
		        BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
		        BorderFactory.createEmptyBorder(5, 8, 5, 8)
		    ));
	    
	    names.addFocusListener(new FocusAdapter() {
	        public void focusGained(FocusEvent e) {
	            names.setBackground(Color.WHITE);
	            names.setBorder(BorderFactory.createCompoundBorder(
	                BorderFactory.createLineBorder(new Color(0, 102, 204), 2),
	                BorderFactory.createEmptyBorder(4, 7, 4, 7)
	            ));
	            if (names.getText().equals(currentplaceholder)) {
	                names.setText("");
	                names.setForeground(Color.BLACK);
	            }
	        }
	        public void focusLost(FocusEvent e) {
	            names.setBackground(new Color(248, 248, 248));
	            names.setBorder(BorderFactory.createCompoundBorder(
	                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
	                BorderFactory.createEmptyBorder(5, 8, 5, 8)
	            ));
	            if (names.getText().isEmpty()) {
	                names.setText(currentplaceholder);
	                names.setForeground(Color.GRAY);
	            }
	        }
	    });
	    
	    names.setForeground(new Color(100, 100, 100));

	    
		form.add(names,BorderLayout.CENTER);
		
		return form;
	}
	public static void sidebarbuttonstyle(JPanel sidebar,String text)
	{
	    JButton btn = new JButton(text);
	    btn.setFont(new Font("Arial", Font.PLAIN, 14));
	    btn.setFocusPainted(false);
	    btn.setHorizontalAlignment(SwingConstants.LEFT);
	    btn.setBorder(new EmptyBorder(12, 20, 12, 10));
	    btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	    btn.setContentAreaFilled(false);
	    btn.setOpaque(true);
	    btn.setBackground(Color.WHITE);
	    btn.setForeground(Color.BLACK);
	    btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
	    btn.setAlignmentX(Component.LEFT_ALIGNMENT);
	    btn.addMouseListener(new MouseAdapter() {
	        public void mouseEntered(MouseEvent evt) {
	            btn.setBackground(new Color(240, 240, 240)); 
	        }
	        public void mouseExited(MouseEvent evt) {
	            btn.setBackground(Color.WHITE); 
	        }
	    });
	    btn.addActionListener(e -> {
	        if (text.contains("Flights")) {
	            JOptionPane.showMessageDialog(null, "Flights feature coming soon!");
	        } else if (text.contains("Trip Planner")) {
	            JOptionPane.showMessageDialog(null, "Trip Planner feature coming soon!");
	        }
	    });

	    sidebar.add(btn);
	    sidebar.add(Box.createVerticalStrut(2));
	}
	public static void stylebutton(JButton b,Color c)
	{
		b.setFont(new Font("ARIAL",Font.BOLD,12));
		b.setBackground(c);
		b.setForeground(Color.WHITE);
		b.setBorder(new EmptyBorder(10, 20, 10, 20));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
	}
	public static boolean nameusescheck(String username)
	{
		File f=new File(user_file);
		if(!f.exists())
		return false;
		try(BufferedReader b=new BufferedReader(new FileReader(f)))
		{
			String l;
			while((l=b.readLine())!=null)
			{
				if(l.trim().isEmpty()||l.startsWith("username,"))
				continue;
				String [] part=l.split(",",-1);
				if(part.length>=2)
				{
					if(part[0].equals(username))return true;
				}
			}
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return false;
	}
	private static boolean saveusercredentials(String username, String password, user detail) {
	    try {
	        File file = new File(user_file);
	        file.getParentFile().mkdirs();
	        
	        boolean f = !file.exists();
	        
	        FileWriter write = new FileWriter(file, true);
	        if (f) {
	            write.write("username,password,firstname,lastname,dob,phone,email,gender,aadhar/pan,address\n");
	        }
	        
	        write.write(username + "," + password + "," + detail.name1 + "," + detail.name2 + ","
	                    + detail.dob + "," + detail.phone + "," + detail.email + ","
	                    + detail.gender + "," + detail.pan + "," + detail.location + "\n");
	        
	        write.close();
	        return true;
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	public static boolean validatelogin(String user,String pass)
	{
		File f=new File(user_file);
		if(!f.exists())return false;
		try (BufferedReader b=new BufferedReader(new FileReader(f)))
		{
			String l;
			while((l=b.readLine())!=null)
			{
				if(l.trim().isEmpty()||l.startsWith("username,"))continue;
				String []part=l.split(",",-1);
				if(part.length>=2)
				{
					String username=part[0];
					String password=part[1];
					if(username.equals(user)&&password.equals(pass))
					{
						return true;
					}
				}
			}
			
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		return false;
	}
	public static JPasswordField showandhide(int c)
	{
		JPasswordField passwordField=new JPasswordField(c);
		passwordField.setEchoChar('*');
		
		JToggleButton show=new JToggleButton("show");
		show.setFont(new Font("Arial", Font.PLAIN, 12));
        show.setForeground(new Color(0, 123, 255));
        show.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        show.setBorderPainted(false);
        show.setContentAreaFilled(false);
        show.setFocusPainted(false);
        show.setMargin(new Insets(0,0,0,0));
		show.setToolTipText("show password");
	    show.addMouseListener(new MouseAdapter() 
	    {
	    	public void mouseClicked(MouseEvent e)
	    	{
	    		if(passwordField.getEchoChar()=='*')
	    		{
	    			passwordField.setEchoChar((char)0);
	    			show.setText("hide");
	    			show.setToolTipText("hide password");
	    		}
	    		else 
	    		{
	    			passwordField.setEchoChar('*');
	    			show.setText("show");
	    			show.setToolTipText("show password");
				}
	    	}
	    	public void mouseEntered(MouseEvent e)
	    	{
	    		show.setForeground(new Color(0, 123, 255));
	    	}
	    	public void mouseExited(MouseEvent e)
	    	{
	    		show.setForeground(Color.BLACK);
	    	}
	    }	
	    					);
	    
		passwordField.setLayout(new BorderLayout());
		passwordField.add(show,BorderLayout.EAST);
		return passwordField;
	}
	public static JPanel createHeader(String text) 
	{
	    JPanel header = new JPanel();
	    header.setBackground(new Color(0, 82, 165)); // dark blue
	    header.setBorder(new EmptyBorder(15, 10, 15, 10));

	    JLabel title = new JLabel(text);
	    title.setFont(new Font("Arial", Font.BOLD, 22));
	    title.setForeground(Color.WHITE);
	    title.setAlignmentX(Component.CENTER_ALIGNMENT);
	    
	    header.add(title);
	    return header;		
  	}

	public static JPanel createFooter(String t) 
	{
	    JPanel footer = new JPanel();
	    footer.setBackground(new Color(240, 240, 240));
	    footer.setBorder(new EmptyBorder(8, 10, 8, 10));

	    JLabel info = new JLabel(t);
	    info.setFont(new Font("Arial", Font.ITALIC, 12));
	    info.setForeground(Color.DARK_GRAY);

	    footer.add(info);
	    return footer;
	}
	public static void cleardatafield()
	{
		if(name1!=null)name1.setText(null);
		if(name2!=null)name2.setText(null);
		if(dob!=null)dob.setText(null);
		if(phone!=null)phone.setText(null);
		if(email!=null)email.setText(null);
		if(genderGroup!=null)genderGroup.clearSelection();
		if(pan!=null)pan.setText(null);
		if(location!=null)location.setText(null);
	}
}