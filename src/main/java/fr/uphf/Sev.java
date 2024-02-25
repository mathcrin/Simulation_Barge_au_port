package fr.uphf;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Sev {
    //TODO : faire un readme qui explique comment lancer le programme et les modification possible dans le fichier json
    //Simulateur à évenement discret
    //Liste d'évènement discret
    List<Demande> demandes;
    List<Service> services;

    public void startSimulation() {
        //On suppose la liste des demandes triée par date de départ
        Demande demandeActuel = demandes.get(0);
        Service service;
        Integer UniteDeTemps = demandeActuel.getDateDepart();
        System.out.println("### Début de la simulation ###");
        while(true){
            System.out.println("Unite de temps : " + UniteDeTemps);
            service = Service.unServiceRepondALaDemande(services, demandeActuel);
            if(service != null) {

            }
            else {
                System.out.println("Aucun service ne peut prendre en charge la demande " + demandeActuel.getId());

            }
        }
    }


    public void loadFromJson() {
        InputStream inputStream = this.getClass().getResourceAsStream("/services.json");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String content = reader.lines().collect(Collectors.joining());
        JSONArray jsonServices = new JSONArray(content);
        services = new ArrayList<>();
        for (int i = 0; i < jsonServices.length(); i++) {
            JSONObject jsonService = jsonServices.getJSONObject(i);
            Service service = new Service();
            service.loadFromJson(jsonService);
            services.add(service);
        }
        inputStream = this.getClass().getResourceAsStream("/demandes.json");
        reader = new BufferedReader(new InputStreamReader(inputStream));
        content = reader.lines().collect(Collectors.joining());
        JSONArray jsonDemandes = new JSONArray(content);
        demandes = new ArrayList<>();
        for (int i = 0; i < jsonDemandes.length(); i++) {
            JSONObject jsonDemande = jsonDemandes.getJSONObject(i);
            Demande demande = new Demande();
            demande.loadFromJson(jsonDemande);
            demandes.add(demande);
        }
        System.out.println("Chargement des services et des demandes terminé");
    }
}
