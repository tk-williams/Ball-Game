package edu.uco.twilliams84.termprojecttimothyw.Model;

import java.util.ArrayList;
import java.util.Comparator;

public class Player {
    public static Player player = null;

    private String name;
    private String city;
    private String state;
    private String email;
    private String password;
    private ArrayList<Integer> scores;

    public Player(String name, String city, String state) {
        this.name = name;
        this.city = city;
        this.state = state;
        this.scores = new ArrayList<>();
    }

    public Player(String name, String city, String state, String email, String password) {
        this.name = name;
        this.city = city;
        this.state = state;
        this.email = email;
        this.password = password;
        this.scores = new ArrayList<>();
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public ArrayList<Integer> getScores() {
        return scores;
    }

    public void setScores(ArrayList<Integer> scores) {
        this.scores = scores;
    }

    public void addScore(Integer score) {
        scores.add(score);
    }

    public static Comparator<Player> compareScores() {
        Comparator comparator = new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                return p2.getScores().get(0).compareTo(p1.getScores().get(0));
            }
        };
        return comparator;
    }
}
