package BDD;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

public class Insertion extends JFrame implements ActionListener
{
	
	//déclaration des éléments graphiques utilisés
	private JPanel top, bottom;
	private JComboBox box;
	private final String[] state = { "has been shown", "is being shown", "will be shown"};
	private JButton insert;
	private JButton clear;
	private JTextField text1,text2,text3,text4,text5,text6;
	
	//constructeur pour établir deux panneaux
	public Insertion(String arg0)
	{
		super(arg0);
	    setResizable(false);
	    Container pane=getContentPane();
	    pane.setLayout(new GridLayout(2,0));// Layout : une grille de deux lignes égales pour top et bottom
	    top=new JPanel();
	    top.setBackground(new Color(0xFF9933));
	    bottom=new JPanel();
	    bottom.setBackground(new Color(0xFFB266));
	    pane.add(top);
	    pane.add(bottom);
	}
	
	//dimensionnement de la fenêtre
	public Dimension getPreferredSize() 
	{
		return new Dimension(900, 400);
	}
	 
	//complétion du panneau top 
	public void completeTop()
	{
	 	top.setLayout(new GridLayout(3,2));//division du panneau top
		JPanel first=new JPanel();//création d'un sous-panneau
		first.setLayout(new FlowLayout(FlowLayout.LEADING,18,20));//préciser la position des composants
		first.add(new JLabel("Programming ID:"));//préciser le label et l'ajouter dans le panneau
		text1=new JTextField("to be completed");//préciser la zone de texte
		text1.setColumns(20);//préciser la longueur de la zone de texte
		first.add(text1);//ajouter la zone de texte dans le panneau
			
		
		JPanel second=new JPanel();
		second.setBackground(new Color(0x6666FF));
		second.setLayout(new FlowLayout(FlowLayout.LEADING,60,20));
		second.add(new JLabel("Start Date:"));
		text2=new JTextField("to be completed");
		text2.setColumns(20);
		second.add(text2);
		
		JPanel third=new JPanel();
		third.setBackground(new Color(0x6666FF));
		third.setLayout(new FlowLayout(FlowLayout.LEADING,40,20));
		third.add(new JLabel("End Date:"));
		text3=new JTextField("to be completed");
		text3.setColumns(20);
		third.add(text3);
		
		JPanel fourth=new JPanel();
		fourth.setLayout(new FlowLayout(FlowLayout.LEADING,50,20));
		fourth.add(new JLabel("Cinema Name:"));
		text4=new JTextField("to be completed");
		text4.setColumns(20);
		fourth.add(text4);
		 
		JPanel fifth=new JPanel();
		fifth.setLayout(new FlowLayout(FlowLayout.LEADING,40,20));
		fifth.add(new JLabel("Film Title:"));
		text5=new JTextField("to be completed");
		text5.setColumns(20);
		fifth.add(text5);
		 
		JPanel sixth=new JPanel();
		sixth.setBackground(new Color(0x6666FF));
		sixth.setLayout(new FlowLayout(FlowLayout.LEADING,22,20));
		sixth.add(new JLabel("Insertion succeed or not:"));
		text6=new JTextField("");
	    text6.setColumns(20);
	    sixth.add(text6);
	    
	    //ajouter les sous-panneaux dans le panneau top
		top.add(first);   
		top.add(second);
		top.add(third);
		top.add(fourth);
		top.add(fifth);
		top.add(sixth);
	 }
	 
	 //complétion du panneau bottom 
	 public void completeBottom()
	 {
		 bottom.setLayout(new FlowLayout(FlowLayout.LEADING,80,40));//préciser la position des composants
		 bottom.add(new JLabel("Programming state:"));//préciser le label et l'ajouter dans le panneau
		 box = new JComboBox(state);//préciser une boîte combinée
		 bottom.add(box); //ajouter la boîte combinée dans le panneau
    	 insert=new JButton("insert");//préciser un bouton <<insert>>
    	 clear=new JButton("clear");//préciser un bouton <<clear>>
    	 
    	 //ajouter les boutons dans le panneau   
    	 bottom.add(insert);
    	 bottom.add(clear);
    	 
    	 //écouter les événements correspondants 
    	 insert.addActionListener(this);
    	 clear.addActionListener(this);
	 }
	 
	 //traitement des événements
	 public void actionPerformed(ActionEvent evt) 
	 {
		 Connection connection = null;  
		 Statement statement = null;
		 String event=evt.getActionCommand();//Le label de l'événement est récupéré
		 int index=box.getSelectedIndex();//obtenir l'index auquel le choix correspond.
		 
		 //en cas d'une insertion
		 if (event.equals("insert"))
		 {
			 //lecture du contenu de chaque zone de texte
			 int id_pro=Integer.parseInt(text1.getText());
			 String start_date=text2.getText();
			 String end_date=text3.getText();
			 String cinema_name=text4.getText();
			 String title_film=text5.getText();		
			 try 
			 {  
				 //lancer une requête SQL
				 Class.forName("org.sqlite.JDBC");  
		         connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Administrator\\Desktop\\recent\\semestre7\\Base de donnee\\projetBD\\BDcinema.db"); 
		         statement = connection.createStatement();
		         String query = "INSERT INTO programming VALUES ('"+id_pro+"', '"+state[index]+"' , '"+start_date+"','"+end_date+"','"+cinema_name+"','"+title_film+"')";
				 System.out.println(query);
				 int val = statement.executeUpdate(query);
				 
				 //montrer si l'insertion a réussi ou pas
				 String res;
				 if(val == 1)
				 {
					 res ="Insertion succeeded";
				 }
				 else res ="Insertion failed";
				 text6.setText(res);
			 }
			 catch (Exception e) 
		     {  
		          e.printStackTrace();  
		     }
		     finally 
		     {  
		         try 
		         {  
		        	 //libérer les ressources associées
		             statement.close();
		             connection.close();
		         } 
		         catch (Exception e) 
		         {  
		             e.printStackTrace();  
		         }  
		     }  
		 }
		 
		 //en cas d'un effacement
		 else if (event.equals("clear"))
		 {
			 //effacer tous les contenus 
			 text1.setText("");
			 text2.setText("");
			 text3.setText("");
			 text4.setText("");
			 text5.setText("");
			 text6.setText("");
		 }
	}
	 
	private static void createAndShowGUI() 
	{
	    //création de la fenêtre
		Insertion ins = new Insertion("Programming Insertion");
    	ins.completeTop();
    	ins.completeBottom();
    	ins.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //affichage de la fenêtre
    	ins.pack();
    	ins.setVisible(true);
	 }
	
	//utiliser une nouvelle tâche pour la création de GUI
	public static void main(String[] args) 
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() 
            {
                createAndShowGUI();
            }
		});

	}
}

