package fr.uphf;

import org.json.JSONObject;

import java.time.LocalDate;

public class Demande {
    private Integer id;
    private String Origin;
    private String Destination;
    private Integer dateDepart;
    private Integer dateArrivee;
    public void loadFromJson(JSONObject json) {
        id = json.getInt("id");
        Origin = json.getString("Origin");
        Destination = json.getString("Destination");
        dateDepart = json.getInt("dateDepart");
        dateArrivee = json.getInt("dateArrivee");
    }
}
