//Patrycja Marucińska, nr indeksu: 42746, Informatyka II rok III semestr, studia dzienne

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
class Dice {
    private final int sides;
    public Dice(int sides) {
        this.sides = sides;
    }
    public int roll() {
        Random random = new Random();
        return random.nextInt(sides) + 1;
    }
}
class Player {
    private final String name;
    private int healthPoints = 50;
    //"gracz powinien mieć definiowane raz imię oraz zdefiniowaną wewnętrznie liczbę punkktów życia równą 50
    public Player(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public int getHealthPoints() {
        return healthPoints;
    }
    public void reduceHealthPoints(int amount) {
        healthPoints -= amount;
    }
}
class Game {
    private final HashMap<Integer, Integer> fields = new HashMap<>();
    private final ArrayList<Player> players = new ArrayList<>();
    private final Dice dice;
    private int maxFields = 100;
    // "Main powinna mieć możliwość jednokrotnego, ale opcjonalnego ustawienia maksymalnej liczby pól;
    // jeżeli nie zostałaby ona ustawiona, wówczas gra powinna przyjąć domyślnie 100 pól"

    private int maxTurns = -1;
    // "Main powinna mieć możliwość jednokrotnego, ale opcjonalnego ustawienia maksymalnej liczby tur;
    // jeżeli nie zostałaby ona ustawiona, gra toczyłaby się do osiągnięcia przez pierwszego gracza
    // ostatniego pola; w przeciwnym wypadku wygrałby gracz, który jako pierwszy dotrał najdalej
    // po zakończeniu tury"
    public Game(Dice dice) {
        this.dice = dice;
    }
    public void addPlayer(Player player) {
        if (players.size() < 2) {
            // "Dopisywani są do gry dwaj gracze
            players.add(player);
        }
    }
    public void setMaxFields(int maxFields) {
        this.maxFields = maxFields;
    }
    public void setMaxTurns(int maxTurns) {
        this.maxTurns = maxTurns;
    }
    public void prepareGame() {
        for (int i = 0; i < players.size(); i++) {
            fields.put(i, 0);
        }
    }
    public void run() {
        int turns = 0;
        while (true) {
            turns++;
            if (maxTurns > 0 && turns > maxTurns) {
                System.out.println("Max turns reached. Game ends in a draw.");
                break;
            }
            for (int i = 0; i < players.size(); i++) {
                int result = dice.roll();
                int position = fields.get(i) + result;
                if (position >= maxFields) {
                    position = maxFields;
                }
                System.out.println(players.get(i).getName() + " rolled " + result + ". Now is on position "
                        + position + ".");
                if (position >= maxFields) {
                    System.out.println(players.get(i).getName() + " won!!!");
                    return;
                }
                if (position % 7 == 0) {
                    //"jezeli gracz stanie na polu podzielnym przez 7, powinien rzucic
                    // kostką i stracić tyle punktów życia, ile wypadło oczek"

                    int damage = dice.roll();
                    System.out.println(players.get(i).getName() + " landed on a multiple of 7 and lost "
                            + damage + " health points.");
                    players.get(i).reduceHealthPoints(damage);
                    if (players.get(i).getHealthPoints() <= 0) {
                        System.out.println(players.get(i).getName() + " ran out of health points and lost!!!");
                        //" gracz, którego liczba punktów życia spadnie do zera przegrywa"
                        return;
                    }
                }
                fields.put(i, position);
            }
        }
    }
}
public class Main {
    public static void main(String[] args) {
        Dice dice = new Dice(20);
        // Kostka posiada maksymalnie do 20 ścian
        Game game = new Game(dice);
        game.addPlayer(new Player("Anakin Skywalker"));
        game.addPlayer(new Player("Obi-Wan Kenobi"));
        game.setMaxFields(100);
        // Przyjęcie maksymalnie 100 pól
        game.setMaxTurns(100);
        // Przyjęcie maksymalnie 100 tur
        game.prepareGame();
        game.run();
    }
}