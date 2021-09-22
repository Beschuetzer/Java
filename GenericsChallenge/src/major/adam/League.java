package major.adam;

import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class League <T extends Team> {
    private String name;
    private List<T> teams;

    public League(String name) {
        this.name = name;
        this.teams = new ArrayList<T>();
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

    public boolean addTeam(String name, Class<T> teamType) {
        T foundTeam = findTeam(name);
        if (foundTeam != null) return false;

        T newTeam;
        try {
            newTeam = teamType.newInstance();
            newTeam.setName(name);
            teams.add(newTeam);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        return true;
    }

    public boolean addTeam(T teamToAdd) {
        if (teamToAdd == null) return false;
        T foundTeam = findTeam(name);
        if (foundTeam != null) return false;

        teams.add(teamToAdd);
        return true;
    }

    private T findTeam(String name) {
        if (name == "") return null;

        for (T team : teams) {
            if (team.getName() == name) return team;
        }

        return null;
    }

    public void printTeams() {
        Collections.sort(teams);

        for (Team team : teams) {
            System.out.println(team);
        }
    }
}
