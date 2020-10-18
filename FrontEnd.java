import java.util.Scanner;

// --== CS400 File Header Information ==--
// Name: Hunter Strohbusch
// Email: hstrohbusch@wisc.edu
// Team: AD
// TA: Sophie Stephenson
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

public class FrontEnd {
	public static void main(String[]args) {

		Scanner intScan = new Scanner(System.in);
		Scanner stringScan = new Scanner(System.in);

		RedBlackTree<Movie> movies = new RedBlackTree<>();
		DataWrangler.load(movies);
		int cur = 0;
		boolean stay = false;
		String temp = new String();
		Movie tempMovie = new Movie("", "", "", -1, new String[]{"",""}, 1, 1.0);

		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~  Welcome to the Movie Database  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

		while(cur!=9) {

			if(stay != true) {

				System.out.println("Please make a selection by entering the number of one of the options below and pressing enter");
				System.out.println("---------------------------------------------------------------------------------------------");

				System.out.println("1.) Search for a Movie");
				System.out.println("2.) Add a new Movie to the Movie Database");
				System.out.println("3.) Exit");
				System.out.println("---------------------------------------------------------------------------------------------");

				cur = intScan.nextInt();
				
				if(cur<1||cur>3)  { System.out.println("Invalid input"); }

				if(cur == 3) cur = 9;

				if(cur ==1) { //finding a movie
					System.out.println("---------------------------------------------------------------------------------------------");
					System.out.println("Please enter the exact name of the movie that you "
							+ "would like to learn about and press <Enter>:");
					System.out.println("---------------------------------------------------------------------------------------------");
					temp = stringScan.nextLine();
					Movie dummy = new Movie(temp, "", "", -1, new String[]{"",""}, 1, 1.0);
					tempMovie = findMovie(dummy, movies);
				}

			}

			if(cur ==1) { //looking at the found movie
				if(tempMovie == null) {	
					System.out.println("---------------------------------------------------------------------------------------------");
					System.out.println("A movie with the title "+temp+" was not found:");
					System.out.println("---------------------------------------------------------------------------------------------");}
				else {
					choices(temp);
					cur = intScan.nextInt();

					System.out.println("---------------------------------------------------------------------------------------------");
					if(cur == 1) {System.out.println("~~~ Title: " +tempMovie.getTitle()+" ~~~"); }
					else if(cur == 2) {System.out.println("~~~ Director: " +tempMovie.getDirector()+" ~~~"); }
					else if(cur == 3) {System.out.println("~~~ Genre: " +tempMovie.getGenre()+" ~~~"); }
					else if(cur == 4) {System.out.println("~~~ Run Time: "+tempMovie.getRunTime()+" ~~~"); }
					else if(cur == 5) {System.out.println("~~~ Actors: "+tempMovie.getActors()+" ~~~"); }
					else if(cur == 6) {System.out.println("~~~ Release Date: "+tempMovie.getReleaseDate()+" ~~~"); }
					else if(cur == 7) {System.out.println("~~~ Rating: " +tempMovie.getRatings()+" ~~~"); }	
					else if(cur == 8) {System.out.println(tempMovie.toString()); }	
					else if(cur != 9) {System.out.println("Incorrect input");}
					
					cur = 1;

					System.out.println("---------------------------------------------------------------------------------------------");

				}

				if(tempMovie != null) {
					System.out.println("Would you like to learn more about this movie?\nEnter Y if yes and N if no");
					System.out.println("---------------------------------------------------------------------------------------------");
					temp = stringScan.nextLine();
					if(temp.equalsIgnoreCase("y")) {stay = true;}
					else if(temp.equalsIgnoreCase("n")){
						System.out.println("---------------------------------------------------------------------------------------------");
						System.out.println("Would you like to continue seaching the Movie Database?\nEnter Y if yes and N if no");
						System.out.println("---------------------------------------------------------------------------------------------");
						temp = stringScan.nextLine();
						if(temp.equalsIgnoreCase("Y")) {stay = false;}
						else if(temp.equalsIgnoreCase("N")) { cur = 9;}
						else {System.out.println("Incorrect input: continuing search");}
					}
					else {System.out.println("Incorrect input: continuing search"); }
				}
				else {
					System.out.println("Would you like to continue seaching the Movie Database?\nEnter Y if yes and N if no");
					System.out.println("---------------------------------------------------------------------------------------------");
					temp = stringScan.nextLine();
					if(temp.equalsIgnoreCase("Y")) {stay = false;}
					else if(temp.equalsIgnoreCase("N")) { cur = 9;}
					else {System.out.println("Incorrect input: continuing search");}
				}
			}
			
			if(cur ==2) {//adding a new movie to the database
				System.out.println("---------------------------------------------------------------------------------------------");
				System.out.print("Please enter the Title of the movie that you want to add: ");
				String title = stringScan.nextLine();
				System.out.print("Please enter the Director of the movie that you want to add: ");
				String director = stringScan.nextLine();
				System.out.print("Please enter the Genre of the movie that you want to add: ");
				String genre = stringScan.nextLine();
				System.out.print("Please enter the Run Time of the movie that you want to add as a whole number: ");
				int runTime = Integer.valueOf(stringScan.nextLine());
				System.out.print("Please enter the Actors of the movie that you want to add seperated by commas: ");
				String actorsAsString = stringScan.nextLine(); //needs to be an array
				String[] actors = actorsAsString.split(", ");
				System.out.print("Please enter the Release Date of the movie that you want to add: ");
				int release = Integer.valueOf(stringScan.nextLine());
				System.out.print("Please enter the Rating of the movie that you want to add as a number with a decimal point value: ");
				Double rating = Double.valueOf(stringScan.nextLine());
								
				Movie added = new Movie(title, director, genre, runTime, actors, release, rating);
				
				try {
					movies.insert(added);
				} catch(IllegalArgumentException e1) {
					System.out.println("That movie is already in the database");
				} catch(Exception e) {
					System.out.println("An unexpected error has occured when attempting to add the new movie, please try again.");
				}
				System.out.println("---------------------------------------------------------------------------------------------");
				System.out.println("The movie that you just added:");
				System.out.println(added);
				
				System.out.println("---------------------------------------------------------------------------------------------");
				System.out.println("Would you like to continue seaching the Movie Database?\nEnter Y if yes and N if no");
				System.out.println("---------------------------------------------------------------------------------------------");
				temp = stringScan.nextLine();
				if(temp.equalsIgnoreCase("Y")) {stay = false;}
				else if(temp.equalsIgnoreCase("N")) { cur = 9;}
				else {System.out.println("Incorrect input: continuing search");}
				
			}
			

		}

		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Thank you and goodbye! ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		intScan.close();
		stringScan.close();

	}
	public static Movie findMovie(Movie m, RedBlackTree<Movie> RBT) {
		boolean looking = true;
		RedBlackTree.Node<Movie> check = RBT.root;

		while(looking) {
			try {
				int state = m.compareTo(check.data);

				if(state==0) { return check.data; }
				else if(state == 1) { check = check.rightChild; }
				else if(state == -1) { check = check.leftChild; }

				else { looking = false; }
			} catch( NullPointerException e ){
				looking = false;
			}
		}

		return null;
	}

	public static void choices(String s) {
		System.out.println("---------------------------------------------------------------------------------------------");
		System.out.println("What information about "+s+ " would you like to know?");
		System.out.println("1.) Title");
		System.out.println("2.) Director");
		System.out.println("3.) Genre");
		System.out.println("4.) Run Time");
		System.out.println("5.) Actors");
		System.out.println("6.) Release Date");
		System.out.println("7.) Rating");
		System.out.println("8.) All of the above");
		System.out.println("9.) I am done with the program");
		System.out.println("Please enter your choice as a number and press <Enter>");
		System.out.println("---------------------------------------------------------------------------------------------");
	}

}
