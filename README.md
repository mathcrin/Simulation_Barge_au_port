# Projet maven

## Comment lancer le programme :
- Avoir Java 17 d'installé sur votre machine
- Avoir maven d'installé et configuré sur votre machine
- Aller à la racine du projet
- Exécuter la commande suivante :
  `mvn clean install exec:java -D exec.mainClass="fr.uphf.Main"`


## Modifications possibles de la simulation :
### Modifier les services :  
- Les services sont représentés par des objets Json dans le fichier src/main/resources/services.json.
- "id" : Représente l'identifiant du service
- "capacite": Représente la capacité du service
- "chemin": Représente le chemin du service par une map avec les ports(point) et la date d'arrivée (date)


### Modifier les demandes :
- Les demandes sont représentés par des objets Json dans le fichier src/main/resources/demandes.json.
- "id" : Représente l'identifiant de la demande
- "origine": Représente le port d'origine de la demande
- "destination": Représente le port de destination de la demande
- "dateDepart": Représente la date de départ maximum de la demande
- "dateArrivee": Représente la date d'arrivée maximum de la demande
- "nbContainers": Représente le nombre de conteneurs de la demande

### Modifier les ports :
-Les ports sont représenté par une lettre majuscule dans la classe Main.java (sev.ports)

### Modifier le temps maximum de la simulation :
-Le temps maximum de la simulation est représenté par sev.UniteDeTempsMax dans la classe Main.java

# Notre équipe : 
__Galfano Nathan__ et __Crinchon Mathis__
