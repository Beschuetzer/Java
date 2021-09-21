package major.adam;

import java.util.ArrayList;
import java.util.List;

public class League <T extends Team> {
    private String name;
    private List<T> teams;

    public League(String name) {
        this.name = name;
        this.teams = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<T> getTeams() {
        return teams;
    }

    public boolean addTeam(T teamToAdd) {
        if (teamToAdd == null) return false;

        teams.add(teamToAdd);
        return true;
    }
}
