
/**
 * Write a description of class GAME here.
 *
 * @author (Anmol Bagati)
 * @version ()
 */
 

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random; 
import java.util.Scanner;

public class Game {

	private String playerName; 
	private int gameTotal;
	
	
	 public Game() 
	{
		gameTotal = 0; 
	}
	/* This block contains the main function in which 
	 * instantiation of classes and calling two main functions 
	 */
	public static void main(String args[])
	{
		Game game = new Game(); 
		Buffer buffer = new Buffer();
		registerPlayer(game);
		startGame(game);
	}
	
	// Reading values from the given multiple.txt file at random for each round //
	public static ArrayList<Integer> readMultiples()
	{
		String path =  "multiples.txt"; //path to the file //
		String content = null;
		try {
			 content = readFile(path, StandardCharsets.US_ASCII); //reading the file data into string //
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		List<String> multiplesString = Arrays.asList(content.split(",")) ; /* splitting the comma separated values 
		                                                                      to retrieve interger values */
		return getMultipleList(multiplesString);

	}
	//Converting list of string into list of integers//
	public static ArrayList<Integer> getMultipleList(List<String> multiplesString)
	{
		ArrayList<Integer> multiples = new ArrayList<>();
		for(int i =0; i< multiplesString.size(); i++)
		{
			int a = Integer.parseInt(multiplesString.get(i));
			multiples.add(a);
		}
		return multiples;
	}
	//Reading a file
	static String readFile(String path, Charset encoding) throws IOException 
	{
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return new String(encoded, encoding);
	}
	/* this function successfully registers a player handling 
	 *
	 * all errors of invalid inputs according to game specification */
	public static void registerPlayer(Game game)
	{
		Scanner sc1 = new Scanner(System.in); /* Scanner class for taking input from user */
		while(true) /* infinite loop for registering player with correct credentials */
		{
			
			System.out.print("\nPlease Enter Players Name (Between 3-10 characters): ");
			String player_name = ""; /*Initiate variable to store player name*/
			player_name = sc1.next(); /* put input in variable player_name */
			if(player_name.length() < 3 )
			{
				System.out.println("Input name has less then 3 characters. Please provide a valid input."); /*Invalid player_name input*/
				
			}
			else if(player_name.length() > 10)
			{
				System.out.println("Input name has more then 10 characters. Please provide a valid input."); /*Invalid player_name input*/
			}
			else
			{
				game.playerName = player_name; 
				System.out.println("Welcome "+ player_name+". You have been registerd successfully"); /* Condition needed 
				                                                                                            satisfied for
				                                                                                       starting the game*/
				pressContinue();
				break; /* Break statement to exit loop at only this else condition */

			}
			pressContinue(); 
			
		}
				
	}
	/* This block of code makes the player play the actual game */
	public static void startGame(Game game)
	{
		Scanner s = new Scanner(System.in); /* Scanner class for taking input */ 
		ArrayList<Integer> multiplesPool = readMultiples(); //Importing or reading all multiples from txt file to start game
		
		Buffer buffer = new Buffer(); 
		RNG rng = new RNG();
		rng.setMinimumValue(0);
		rng.setMaximumValue(multiplesPool.size() - 1); //Defining range = number of multiples read//
		String input = "0";
		int randomIndex = 0;
		while(true)
		{
			if(input.equalsIgnoreCase("0")) // checking if it is the first turn of the game for rng to put to play
			{
				randomIndex = getRandom(rng.getMaximumValue(), rng.getMinimumValue());//Genetrating random index//
				game.gameTotal = multiplesPool.get(randomIndex);//Choosing multiple at the random index in the array//
				
			}
			
			
			System.out.println("Game Total: " + game.gameTotal + ""
					+ "\nBuffer: " + toComma(buffer.getList())); //displaying score after round ends//
			
			if(isGameOver(buffer, game)) // checking condition for game end 
			{
				System.out.print("Game Over");
				writeResultToFile(buffer, game); // function to write result of game to the output txt file //
				break;
			}
			//game not over. program lets plyer proceed further//
			System.out.println("What would you like to do?"
					+ "\n1. Split"
					+ "\n2. Merge");
			input = s.next();
			if(input.equalsIgnoreCase("1")) //split
			{
				if(buffer.getList().size() < buffer.getMaxElements())  /*checking if the buffer is in a valid 
				                                                        condition of less than 5 */
				{
					Multiple multiple = new Multiple();
					multiple.setValue(game.gameTotal);// Puting multiple integer value into multiple object //
					buffer.getList().add(multiple);// Adding multiple objects to the buffer list of multiples //
					randomIndex = getRandom(rng.getMaximumValue(), rng.getMinimumValue());//generating new random index//
					game.gameTotal = multiplesPool.get(randomIndex);//getting multiple value at new random index//
				}
				else //Condition for full buffer//
				{
					System.out.println("Split not possible. Please choose a different option");
				}
				
			}
			else if(input.equalsIgnoreCase("2")) //merge//
			{
				int hasMultiple = 0;
				for(int i=0; i < buffer.getList().size(); i++)
				{
					if(buffer.getList().get(i).getValue() == game.gameTotal)//checking if there is a matching value in the multiple list as the game total
					{
						hasMultiple = 1;
						buffer.getList().remove(i);// removing the multiple from from the buffer list//
						game.gameTotal = game.gameTotal * 2;// putting the new added value to the game total//
						
					}
				}
				if(hasMultiple == 0)// if no matching multiple found in the list as the game total//
				{
					System.out.println("Merge not possible. Please choose a different option");
				}
			}
			else// if user provides any invalid randon input other than "1" or "2"//
			{
				System.out.println("Please provide a valid input");
			}

		}
		
		
	}
	// this fucntion generates random value between a given minimum and a given maximum value //
	public static int getRandom(int max, int min)
	{

		Random random = new Random();
		return random.nextInt((max - min) + 1) + min;

	}
	
	
	public static void pressContinue()
	{
		System.out.println("Press Enter to continue.....");
		try {
            int read = System.in.read(new byte[2]);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	//To display list as comma seperated in format -> { v1, v2 ,v3, .....}//
	public static String toComma(ArrayList<Multiple> array) {
        String result = "";

        if (array.size() > 0) {
            StringBuilder sb = new StringBuilder();
            
            sb.append("{ ");
            

            for (Multiple t : array) 
            {
            	
            		sb.append(t.getValue()).append(",");
            	
                
            }
            sb.append(" }");
            result = sb.deleteCharAt(sb.length() - 3).toString();
            

        }
        else
        {
        	result = "{ }";
        }
        
        return result;
}
	public static Boolean isGameOver(Buffer buffer, Game game)
	{
		Boolean isGameOver = false;
		if(game.gameTotal >= 256) //setting condition for ending the game//
		{
			return true;
		}
		//checking if any, split or merge is possible //
		if(buffer.getList().size() >= buffer.getMaxElements()) //split not possible//
		{
			Boolean isMergePossible = false;
			for(int i=0; i< buffer.getList().size(); i++)
			{
				if(buffer.getList().get(i).getValue() == game.gameTotal) //checking each buffer variable for equality tto game total//
				{
					isMergePossible = true; // make merge possible//
					break;
				}
			}
			if(!isMergePossible) //Merge not possible. end game.//
			{
				isGameOver = true;
			}
		}
		return isGameOver;
	}
	//Writing a string to the output file "outcome.txt" with result of the game in the given format//
	public static void writeResultToFile(Buffer buffer, Game game)
	{
		String text = game.playerName + " has Won the game, with the highest achieved score of " + getFinalScore(buffer, game) ;	
		try {
			File file = new File("outcome.txt");
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(text);
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		

	}
	//finding final score by Choosing highest value between game total and buffer list values.//
	public static int getFinalScore(Buffer buffer, Game game)
	{
		int finalScore = game.gameTotal;
		for(int i=0; i< buffer.getList().size(); i++)
		{
			if(buffer.getList().get(i).getValue() > finalScore)
			{
				finalScore = buffer.getList().get(i).getValue();
			}
		}
		return finalScore;
	}
}

