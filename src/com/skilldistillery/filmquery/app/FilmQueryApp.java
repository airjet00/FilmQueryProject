package com.skilldistillery.filmquery.app;

import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {
  
  DatabaseAccessor db = new DatabaseAccessorObject();

  public static void main(String[] args) {
    FilmQueryApp app = new FilmQueryApp();
//    app.test();
    app.launch();
  }

  private void test() {
//    Film film = db.findFilmById(1);
//    Actor actor = db.findActorById(1);
//    List<Actor> actors = db.findActorsByFilmId(1);
//    System.out.println(film);
////    System.out.println(actors);
  }

  private void launch() {
    Scanner input = new Scanner(System.in);
    
    startUserInterface(input);
    
    input.close();
  }

  private void startUserInterface(Scanner input) {
    boolean isTrue =true;
    
    while (isTrue) {
    	displayMenu();
    	int userSelect = input.nextInt();
    	switch(userSelect) {
    	case 1: 
    		System.out.println("Enter the film ID to display information:");
    		
    		int filmId = input.nextInt();
    		
    		Film film = db.findFilmById(filmId);
    		
    		if(film != null) {
    			System.out.println(film.getTitle());
    			System.out.println(film.getReleaseYear());
    			System.out.println(film.getRating());
    			System.out.println(film.getLanguage());
    			System.out.println(film.getDescription());
    			System.out.println();
    		}else {
    			System.out.println("No film found by that ID.");
    		}
    		break;
    		
    	case 2: 
    		System.out.println("Enter keyword to search for:");
    		 
    		String keyword = input.next();
    		
    		List<Film> films = db.searchFilmByKeyword(keyword);
    		
    		if (films.isEmpty()) {
    			System.out.println("No matching films / description found.");
    			continue;
    		}
    		
    		for (Film film2 : films) {
    			
    			System.out.println();
				System.out.println(film2.getTitle());
				System.out.println(film2.getReleaseYear());
				System.out.println(film2.getRating());
				System.out.println(film2.getDescription());
				System.out.println();
			}
    		
    		break;
    	case 3: System.out.println("No popcorn for you, goodbye!");
    			isTrue = false;
    		break;
    	}
    }
    
    
    
  }
  private void displayMenu() {
	  System.out.println("Please enter an integer option from the menu.");
	  System.out.println("--------------------------------------------");
	  System.out.println("| 1. Look up a film by its id.             |\n");
	  System.out.println("| 2. Look up a film by a search keyword.   |\n");
	  System.out.println("| 3. Exit the application.                 |");
	  System.out.println("--------------------------------------------");
  }

}
