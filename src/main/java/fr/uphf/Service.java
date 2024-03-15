package fr.uphf;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Service {
    Integer id;
    Integer capacite;
    Map<String,Integer> chemin;

    public void loadFromJson(JSONObject json) {
        id = json.getInt("id");
        capacite = json.getInt("capacite");
        chemin = new HashMap<>();
        JSONArray jsonChemin = json.getJSONArray("chemin");
        for(int i = 0; i < jsonChemin.length(); i++) {
            JSONObject jsonEtape = jsonChemin.getJSONObject(i);
            chemin.put(jsonEtape.getString("point"), jsonEtape.getInt("date"));
        }
    }

    public static Service unServiceRepondALaDemande(List<Service> services, Demande demande) {
        //TODO : Changer la mméthode pour trouver le service grâce à la matrice de routage
        for(Service service : services) {
            if(service.chemin.containsKey(demande.getOrigine()) && service.chemin.containsKey(demande.getDestination())) {
                if(service.capacite >= demande.getNbConteneurs()) {
                    if(service.chemin.get(demande.getDestination()) >= demande.getDateArrivee() && service.chemin.get(demande.getOrigine()) >= demande.getDateDepart()) {
                        return service;
                    }
                }
            }
        }
        return null;
    }

    public static Integer dateDeRepondALaDemande(Service service, Demande demande) {
        if(service == null) {return -1;}
        else if(service.chemin.containsKey(demande.getDestination()) && demande.getDateArrivee() >= service.chemin.get(demande.getDestination()) && demande.getDateDepart() <= service.chemin.get(demande.getOrigine())) {
            Integer dateReponse = Math.min(demande.getDateArrivee(), service.chemin.get(demande.getDestination()));
            return dateReponse;
        }
        else {
            return -1;
        }
    }

    public int getId() {
        return id;
    }
}
