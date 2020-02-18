package memory.models;

public class Player {
    private String name;
    private Integer score;
    private Integer rank;

    public Player(String name, Integer score, Integer rank) {
        this.name = name;
        this.score = score;
        this.rank = rank;
    }

    public Player(String name) {
        this(name, 0, 0);
    }

    @Override
    public String toString(){
        return getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer score() {
        return score;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer rank() {
        return rank;
    }
}
