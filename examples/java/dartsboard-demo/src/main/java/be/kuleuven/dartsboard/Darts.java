package be.kuleuven.dartsboard;

import be.kuleuven.scorebord.Scorebord;

public class Darts {

    public void gooi() {
        Scorebord bord = new Scorebord();
        bord.voegToe("joske", 10);
    }

    public static void main(String[] args) {
        new Darts().gooi();
        System.out.println("Joske heeft gegooid, en scoorde 10");
    }

}
