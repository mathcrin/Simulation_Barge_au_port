package fr.uphf;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Sev sev = new Sev();
        sev.loadFromJson();
        sev.startSimulation();
    }
}