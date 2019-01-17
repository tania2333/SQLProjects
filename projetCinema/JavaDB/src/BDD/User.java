package BDD;
import java.sql.*;
import java.util.Scanner;

public class User 
{

	public static void main(String[] args) 
	  {  
	     //initialisation
		 Connection connection = null;  
	     ResultSet resultSet = null;
	     ResultSet resultSetAgain = null;
	     Statement statement = null;
	     String []s = new String [50]; // un array s utilis¨¦ pour pr¨¦server le nom du film trouv¨¦.
	     int n = 0; //un index qui indique la position du nom du film dans un array s.
	     try 
	     {  
	    	 // connexion ¨¤ une base de donn¨¦es
	    	 Class.forName("org.sqlite.JDBC");  
	         connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Administrator\\Desktop\\recent\\semestre7\\Base de donnee\\projetBD\\BDcinema.db"); 
	         statement = connection.createStatement();
	         
	         //demande de taper une date et lecture de la date
	         System.out.println("*Please specify a date with a format of year-month-day:");
	         Scanner in = new Scanner(System.in);
	         String date = in.nextLine();
	         
	         //demande de taper un type et lecture de ce type
	         System.out.println("*Please specify a type of film:");
	         Scanner in2 = new Scanner(System.in);
	         String type = in2.nextLine();
	         
	         //lancer une requ¨ºte SQL pour trouver les noms des films
	         resultSet = statement.executeQuery("SELECT DISTINCT film.film_title FROM film JOIN genre ON film.id_genre = genre.id_genre JOIN programming ON film.film_title = programming.film_title  WHERE genre.name_genre ='"+type+"' AND '"+date+"'>date_start AND '"+date+"' < date_end");
        	 System.out.println("	film_title:");
        	 
        	 //utiliser le principe du parcours s¨¦quentiel d'une liste et montrer et pr¨¦server les noms des films dans un array. 
	         while(resultSet.next())
	         {
	        	 System.out.println("	"+(n++)+". "+resultSet.getString("film_title"));
	        	 s[n-1]=resultSet.getString("film_title");
	         }
	         
	         //demande de taper un num¨¦ro et lecture de ce num¨¦ro
	         System.out.println("*Please input the number you choose:");
	         Scanner in0 = new Scanner(System.in);
	         String num = in0.nextLine();
	         
	         //conversion du type String au type Integer
	         int i = Integer.parseInt(num);
	         
	         //lancer une autre requ¨ºte SQL pour trouver les cin¨¦mas correspondants
	         System.out.println("	The list of corresponding cinemas:");
	         resultSetAgain = statement.executeQuery("SELECT cinema.name_cinema,city FROM cinema JOIN programming ON programming.name_cinema = cinema.name_cinema WHERE '"+date+"'>date_start AND '"+date+"' < date_end AND programming.film_title ='"+s[i]+"'");
	         
	         //utiliser le principe du parcours s¨¦quentiel d'une liste et montrer l'information du cin¨¦ma.
	         while(resultSetAgain.next())
	         {
	        	 System.out.println("	"+"name_cinema : " +resultSetAgain.getString("name_cinema"));
	        	 System.out.println("	"+"city : " +resultSetAgain.getString("city")+"\n");
	         }
	     } 
	     catch (Exception e) 
	     {  
	         e.printStackTrace();  
	     }
	     finally 
	     {  
	         try 
	         {  
	        	 //lib¨¦rer les ressources associ¨¦es
	        	 resultSet.close();
	             resultSetAgain.close();
	             statement.close();
	             connection.close();
	         } 
	         catch (Exception e) 
	         {  
	             e.printStackTrace();  
	         }  
	     }  
	 }  
}

