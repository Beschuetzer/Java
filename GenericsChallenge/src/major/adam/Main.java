package major.adam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        BaseballPlayer baseballPlayer = new BaseballPlayer("Berry");
        SoccerPlayer soccerPlayer = new SoccerPlayer("Ronaldo");
        FootballPlayer footballPlayer = new FootballPlayer("Emmit");

        Team<SoccerPlayer> soccer1 = new Team<>("SomeSoccerTeam");

        Team<FootballPlayer> cowboys = new Team<>("Cowboys");
        Team<FootballPlayer> ravens = new Team<>("Ravens");
        Team<FootballPlayer> patriots = new Team<>("Patriots");
        cowboys.play(ravens, 15, 20);
        cowboys.play(patriots, 21, 20);

        List<Team<FootballPlayer>> teamList = new ArrayList<>(
                Arrays.asList(
                        cowboys,
                        ravens,
                        patriots
                )
        );

        Collections.sort(teamList);
        System.out.println(Arrays.toString(teamList.toArray()));

        League<Team<Player>> nfl = new League<>("National Football League");
        nfl.addTeam(cowboys);
    }
}
