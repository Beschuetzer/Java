package major.adam;

public class Team<T extends Player> implements Comparable<Team<T>> {
    private String name;
    private int wins;
    private int losses;
    private int ties;

    public Team(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getTies() {
        return ties;
    }

    public int getRanking() {
        return getWins() * 2 - getLosses() + getTies();
    }

    @Override
    public int compareTo(Team<T> otherTeam) {
        int ourRanking = getRanking();
        int otherRanking = otherTeam.getRanking();
        if (ourRanking > otherRanking) return -1;
        else if (otherRanking > ourRanking) return 1;
        else return 0;
    }

    public void play(Team<T> opponent, int ourScore, int opponentScore) {
        if (ourScore > opponentScore) wins++;
        else if (opponentScore > ourScore) losses++;
        else ties++;

        if (opponent != null) {
            opponent.play(null, opponentScore, ourScore);
            System.out.println(String.format("%s score: %s", getName(), getWins()));
        }
    }

    @Override
    public String toString() {
        return "Team{" +
                "name='" + name + '\'' +
                ", wins=" + wins +
                ", losses=" + losses +
                ", ties=" + ties +
                '}';
    }

    public void setName(String name) {
        if (name == "") return;
        this.name = name;
    }
}
