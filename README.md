# Film Query

## Description
A command-line application that retrieves and displays film data. Menu-based, allowing the user to choose actions and submit query data, based on film and actor information in database.

## Structure
All JDBC code is encapsulated in methods of the class. Declared methods in the DatabaseAccessor interface, then implement them in DatabaseAccessorObject. Methods return objects like Film, Actor, and List<Actor>, not String or List<String>.

All user input and display output will be in methods of FilmQueryApp.

## Topics and Technologies Used

  * Java 1.8

  * Interface

  * MySQL

  * Java Data Base Connectivity (JDBC)

  * Object-Relational Mapping (ORM)

## How to Run
From FilmQueryApp run command-line will present a menu of options. Enter a integer of 1,2, or 3. (3) will exit the program. (1) will prompt you to enter a film id (integer) and will display film information about the film id entered. (2) will prompt you to enter a search term and will provide a list of movies that title and Description match the search term entered. Will then loop to the main menu until prompt to exit.

## Lessons Learned
Patience was important, I took a long time to think through this program and how everything was needed to be set up. Which paid off as I had only minor debugging to do. I learn that asking for help sooner than I normally do is fine. Sometimes it's just something simple that another set of eyes can see easily and shouldn't spend hours on the same problem. 

### Issue:
In java the MySQL search term was not readable, giving out of bounds errors, due to the fact that it couldn't read %?% bind for the search term.

### Solution:
I placed the % inside the stmt.setString(1, "%"+keyword+"%") leaving by bind alone which solved my issue.
