package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {

	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Film findFilmById(int filmId) {
		Film film = null;
		String user = "student";
		String pass = "student";

		Connection conn;
		try {
			conn = DriverManager.getConnection(URL, user, pass);

			String sqltxt = "SELECT film.id, film.title, film.description,"
					+ "film.release_year, film.language_id, film.rental_duration,"
					+ "film.rental_rate, film.length, film.replacement_cost," 
					+ "film.rating, film.special_features "
					+ "FROM film " + "WHERE film.id = ?;";
			PreparedStatement stmt = conn.prepareStatement(sqltxt);

			stmt.setInt(1, filmId);

			ResultSet filmById = stmt.executeQuery();

			if (filmById.next()) {
				film = new Film();
				film.setId(filmById.getInt("film.id"));
				film.setTitle(filmById.getString("film.title"));
				film.setDescription(filmById.getString("film.description"));
				film.setReleaseYear(filmById.getString("film.release_year"));
				film.setLanguageID(filmById.getInt("film.release_year"));
				film.setRentalDuration(filmById.getInt("film.rental_duration"));
				film.setRentalRate(filmById.getDouble("film.rental_rate"));
				film.setLength(filmById.getInt("film.length"));
				film.setReplacementCost(filmById.getDouble("film.replacement_cost"));
				film.setRating(filmById.getString("film.rating"));
				film.setSpecialFeatures(filmById.getString("film.special_features"));
				film.setActors(findActorsByFilmId(filmId));
				film.setLanguage(findFilmLanguage(filmId, film));
				
			}

			filmById.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return film;
	}

	@Override
	public Actor findActorById(int actorID) {
		Actor actor = null;
		String user = "student";
		String pass = "student";

		Connection conn;
		try {
			conn = DriverManager.getConnection(URL, user, pass);

			String sqltxt = "SELECT actor.id, actor.first_name, actor.last_name" + " FROM actor WHERE actor.id = ?;";
			PreparedStatement stmt = conn.prepareStatement(sqltxt);

			stmt.setInt(1, actorID);

			ResultSet filmById = stmt.executeQuery();

			if (filmById.next()) {
				actor = new Actor();
				actor.setId(filmById.getInt(actorID));
				actor.setFirstName(filmById.getString("actor.first_name"));
				actor.setLastName(filmById.getString("actor.last_name"));

			}

			filmById.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return actor;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		List<Actor> actors = new ArrayList<>();
		Actor actor = null;
		String user = "student";
		String pass = "student";

		Connection conn;
		try {
			conn = DriverManager.getConnection(URL, user, pass);

			String sqltxt = "SELECT actor.id, actor.first_name, actor.last_name" + " FROM actor JOIN film_actor"
					+ " ON actor.id = film_actor.actor_id" + " JOIN film" + " ON film_actor.film_id = film.id"
					+ " WHERE film.id = ?;";

			PreparedStatement stmt = conn.prepareStatement(sqltxt);

			stmt.setInt(1, filmId);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				actor = new Actor();
//				actor.setId(rs.getInt(filmId));
				actor.setFirstName(rs.getString("actor.first_name"));
				actor.setLastName(rs.getString("actor.last_name"));
				actors.add(actor);
			}

			rs.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return actors;
	}
	
	public List<Film> searchFilmByKeyword(String keyword){
		List<Film> films = new ArrayList<>();;
		Film film = null;
		String user = "student";
		String pass = "student";

		Connection conn;
		try {
			conn = DriverManager.getConnection(URL, user, pass);

			String sqltxt = "SELECT film.title, film.description, film.release_year, film.rating" 
					+ " FROM film"
					+ " WHERE film.title LIKE ?" 
					+ " OR film.description  LIKE ?;"; // can't see bind var with %?%

			PreparedStatement stmt = conn.prepareStatement(sqltxt);

			stmt.setString(1, "%"+keyword+"%");
			stmt.setString(2, "%"+keyword+"%");

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				film = new Film();
				film.setTitle(rs.getString("film.title"));
				film.setDescription(rs.getString("film.description"));
				film.setReleaseYear(rs.getString("film.release_year"));
				film.setRating(rs.getString("film.rating"));

				films.add(film);
			}

			rs.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return films;
	}
	
	public String findFilmLanguage(int filmId, Film film) {
		String lang = null;
		
		String user = "student";
		String pass = "student";

		Connection conn;
		try {
			conn = DriverManager.getConnection(URL, user, pass);

			String sqltxt = "SELECT film.language_id,language.name" 
					+ " FROM film JOIN language"
					+ " ON film.language_id = language.id" 
					+ " WHERE film.id = ?;"; // can't see bind var with %?%

			PreparedStatement stmt = conn.prepareStatement(sqltxt);

			stmt.setInt(1, filmId);
			
			ResultSet rs = stmt.executeQuery();

			if(rs.next()) {
				lang = rs.getString("language.name");
			}

			rs.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return lang;
	}

}
