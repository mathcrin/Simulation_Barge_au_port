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
    List<String> ports;
    int UniteDeTempsMax;
    List<Integer> TimeLine = new ArrayList<Integer>();
    int[][] routingMatrix ;


    public void startSimulation() {
        // On suppose la liste des demandes triée par date de départ
        // On place tous les évènements de départ et d'arrivé dans la liste des évènements(TimeLine)
        TimeLine.add(UniteDeTempsMax);
        for(Demande demande : demandes) {
            if (!TimeLine.contains(demande.getDateDepart())) TimeLine.add(demande.getDateDepart());
        }
        // On trie la liste des évènements
        Collections.sort(TimeLine);

        System.out.println("### Début de la simulation ###");
        int UniteDeTemps = TimeLine.get(0);
        int i = 1;
        while(UniteDeTemps < UniteDeTempsMax && UniteDeTemps<=TimeLine.get(TimeLine.size()-1)){
            System.out.println("\n### Unite de temps : " + UniteDeTemps);
            // On traite les évènements de départ
            for(Demande demande : demandes) {
                if(demande.getDateDepart() == UniteDeTemps) {
                    Service service = Service.unServiceRepondALaDemande(services, demande);
                    if(service != null) {
                        System.out.println("Départ de la demande " + demande.getId() + ", acceptée par le service " + service.getId() + ", chargement de " + demande.getNbConteneurs() + " conteneurs.");
                        demande.setService(service);
                        demande.setDateReponse(Service.dateDeRepondALaDemande(service, demande));
                        service.isUsed = true;
                        service.occupation += demande.getNbConteneurs();
                        //On ajoute la date de résolution de la demande dans la liste des évènements
                        if (!TimeLine.contains(demande.getDateResponse())) TimeLine.add(demande.getDateResponse());
                        Collections.sort(TimeLine);
                    } else {
                        System.out.println("La demande " + demande.getId() + " est refusée, car aucun service ne peut la satisfaire (soit l'unité de temps max ne le permet pas, soit aucun chemin ne relie les ports origine et destination).");
                    }
                }
                if(demande.getDateResponse() == UniteDeTemps && !demande.isResolu()) {
                    System.out.println("Arrivé de la demande " + demande.getId());
                    demande.setResolu(true);
                }
            }
            // Passez à l'unité de temps suivante
            UniteDeTemps = TimeLine.get(i++);
        }
        //Affichage des demandes non résolues
        System.out.println("\n### Fin de la simulation ###");
        System.out.println("### Indicateur clé : ###");
        System.out.println("#Liste des demandes non résolues :  ");
        for(Demande demande : demandes) {
            if(!demande.isResolu()) {
                System.out.println("Demande " + demande.getId() + " non résolue");
            }
        }
        System.out.println("#Pourcentage de demandes résolues : " + Demande.pourcentageDemandesResolues(demandes));
        System.out.println("#Liste des services utilisés : " + Service.servicesUtilises(services));
        System.out.println("#Pourcentae d'occupation des services : " + Service.pourcentageOccupationParService(services));
        System.out.println("#Nombre de container par port à la fin : " + this.nbContainerParPortFin(services));
    }

    private String nbContainerParPortFin(List<Service> services) {
        //Affiche le nombre de container par port à la fin de la simulation
        Map<String, Integer> nbContainerParPort = new HashMap<>();
        for(Demande demande : demandes){
            if(demande.isResolu()){
//                String port = demande.getOrigine();
//                if(nbContainerParPort.containsKey(port)){
//                    nbContainerParPort.put(port, nbContainerParPort.get(port) - demande.getNbConteneurs());
//                }else{
//                    nbContainerParPort.put(port, -demande.getNbConteneurs());
//                }
                String port = demande.getDestination();
                if(nbContainerParPort.containsKey(port)){
                    nbContainerParPort.put(port, nbContainerParPort.get(port) + demande.getNbConteneurs());
                }else{
                    nbContainerParPort.put(port, demande.getNbConteneurs());
                }
            }
        }
        for(String port : ports){
            if(!nbContainerParPort.containsKey(port)){
                nbContainerParPort.put(port, 0);
            }
        }
        return nbContainerParPort.toString();
    }

    //Réseau espace temps
    public void retAffichage() {
        System.out.println("### Affichage du réseau espace temps sous form de grille ###");
        for(String port : ports) {
            System.out.print("  " + port + " | "); // Ajout de deux espaces avant le nom du port
            for(int i = 0; i < UniteDeTempsMax; i++) {
                if(isServiceAtPortAndTime(port, i)) {
                    System.out.print(" - ");
                } else {
                    System.out.print("   ");
                }
            }
            System.out.println();
        }
        System.out.print("   ");
        for(int i = 0; i < UniteDeTempsMax; i++) {
            System.out.printf("%3d", i);
        }
        System.out.println();
    }

    private boolean isServiceAtPortAndTime(String port, int time) {
        for(Service service : services) {
            if(service.chemin.containsKey(port) && service.chemin.get(port) == time) {
                return true;
            }
        }
        return false;
    }

    public void generateRoutingMatrix() {
        routingMatrix = new int[ports.size()][ports.size()];
        //Remplire la matrice de routage de 0
        for(int i = 0; i < ports.size(); i++) {
            for(int j = 0; j < ports.size(); j++) {
                routingMatrix[i][j] = 0;
            }
        }
        for(Service service : services) {
            for(int i = 0; i < ports.size(); i++) {
                for(int j = 0; j < ports.size(); j++) {
                    if(service.chemin.containsKey(ports.get(i)) && service.chemin.containsKey(ports.get(j))) {
                        routingMatrix[i][j] = service.getId();
                    }
                }
            }
        }
    }

    public void afficherMatriceDeRoutage() {
        System.out.println("### Affichage de la matrice de routage ###");

        // Print column headers
        System.out.print("  ");
        for (String port : ports) {
            System.out.printf("%4s", port);
        }
        System.out.println();

        // Print rows
        for (int i = 0; i < ports.size(); i++) {
            System.out.print(ports.get(i) + " ");
            for (int j = 0; j < ports.size(); j++) {
                System.out.printf("%4d", routingMatrix[i][j]);
            }
            System.out.println();
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
