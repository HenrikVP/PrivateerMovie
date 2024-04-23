package dk.tec.privateermovie.Models;

import java.util.ArrayList;

public class Movie {
    public boolean adult;
    public String backdrop_path;
    public Object belongs_to_collection;
    public int budget;
    public ArrayList<Genre> genres;
    public String homepage;
    public int id;
    public String imdb_id;
    public ArrayList<String> origin_country;
    public String original_language;
    public String original_title;
    public String overview;
    public double popularity;
    public String poster_path;
    public ArrayList<ProductionCompany> production_companies;
    public ArrayList<ProductionCountry> production_countries;
    public String release_date;
    public int revenue;
    public int runtime;
    public ArrayList<SpokenLanguage> spoken_languages;
    public String status;
    public String tagline;
    public String title;
    public boolean video;
    public double vote_average;
    public int vote_count;

    public class Genre {
        public int id;
        public String name;
    }

    public class ProductionCompany {
        public int id;
        public String logo_path;
        public String name;
        public String origin_country;
    }

    public class ProductionCountry {
        public String iso_3166_1;
        public String name;
    }

    public class SpokenLanguage {
        public String english_name;
        public String iso_639_1;
        public String name;
    }
}