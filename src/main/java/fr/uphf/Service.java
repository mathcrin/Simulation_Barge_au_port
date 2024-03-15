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
    Integer occupation = 0;
    boolean isUsed = false;

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
        //ici nous n'utilisons pas la matrice de routage car elle ne permet pas d'avoir plusieur service qui passe par les même ports mais à des date différentes
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
            return Math.min(demande.getDateArrivee(), service.chemin.get(demande.getDestination()));
        }
        else {
            return -1;
        }
    }

    public static String servicesUtilises(List<Service> services) {
        StringBuilder servicesUtilises = new StringBuilder();
        for(Service service : services) {
            if(service.isUsed) servicesUtilises.append(service.getId()).append(" ");
        }
        return servicesUtilises.toString();
    }

    public static String pourcentageOccupationParService(List<Service> services) {
        StringBuilder pourcentageOccupation = new StringBuilder();
        for (Service service : services) {
            pourcentageOccupation.append(service.getId()).append(" : ").append(service.occupation * 100 / service.capacite).append("% | ");
        }
        return pourcentageOccupation.toString();
    }

    public int getId() {
        return id;
    }
}
