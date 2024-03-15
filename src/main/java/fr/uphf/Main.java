package fr.uphf;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Sev sev = new Sev();
        sev.ports = List.of("A", "B", "C", "D", "E");
        sev.UniteDeTempsMax = 25;
        sev.loadFromJson();
        sev.generateRoutingMatrix();
        sev.afficherMatriceDeRoutage();
        sev.startSimulation();
    }
}