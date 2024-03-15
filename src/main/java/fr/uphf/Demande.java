package fr.uphf;

import org.json.JSONObject;

import java.time.LocalDate;

public class Demande {
    private Integer id;
    private String origine;
    private String destination;
    private Integer dateDepart;
    private Integer dateArrivee;
    private Integer nbConteneurs;
    private Service service;
    private  Integer dateReponse = -1;
    private boolean isResolu = false;

    public void loadFromJson(JSONObject json) {
        id = json.getInt("id");
        origine = json.getString("origine");
        destination = json.getString("destination");
        dateDepart = json.getInt("dateDepart");
        dateArrivee = json.getInt("dateArrivee");
        nbConteneurs = json.getInt("nbConteneurs");
    }

    public String getOrigine() {
        return origine;
    }

    public String getDestination() {
        return destination;
    }

    public Integer getDateDepart() {
        return dateDepart;
    }

    public Integer getDateArrivee() {
        return dateArrivee;
    }

    public Integer getNbConteneurs() {
        return nbConteneurs;
    }

    public Integer getId() {
        return id;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public void setDateReponse(Integer integer) {
        dateReponse = integer;
    }
    public Integer getDateResponse() {
        return dateReponse;
    }
    public Object getService() {
        return service;
    }
    public boolean isResolu() {
        return isResolu;
    }
    public void setResolu(boolean resolu) {
        isResolu = resolu;
    }
}
