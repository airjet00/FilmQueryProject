package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
				+ "film.rating, film.special_features FROM film WHERE film.id = ?;";
		PreparedStatement stmt = conn.prepareStatement(sqltxt);
		
		
		stmt.setInt(1, filmId);
		
		ResultSet filmById = stmt.executeQuery();
		
		if(filmById.next()) {
			film = new Film();
			film.setId(filmById.getInt(filmId));
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
		return null;
	}
	
	@Override
	public List<Actor> findActorsByFilmId(int filmId){
		return null;
	}

	


}
