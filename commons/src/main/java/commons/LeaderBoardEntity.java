/**
 * module with classes for both client and server
 */

package commons;

import java.util.Objects;

public class LeaderBoardEntity {

    public int rank = 0;
    public String name = null;
    public int score = 0;
    public int position = 0;

    /**
     * constructor.
     * @param rank
     * @param name
     * @param score
     */
    public LeaderBoardEntity(int rank, String name, int score) {
        this.rank = rank;
        this.name = name;
        this.score = score;
    }

    /**
     * diggeremt fonstructor.
     * @param rank
     * @param score
     * @param position
     */
    public LeaderBoardEntity(int rank, int score, int position) {
        this.rank = rank;
        this.score = score;
        this.position = position;
    }

    /**
     * getter ore setter.
     *
     * @return
     */
    public int getRank() {
        return rank;
    }

    /**
     * getter ore setter.
     *
     * @return
     */
    public void setRank(int rank) {
        this.rank = rank;
    }
    /**
     * getter ore setter.
     *
     * @return
     */

    public String getName() {
        return name;
    }

    /**
     * getter ore setter.
     *
     * @return
     */
    public int getPosition() {
        return position;
    }
    /**
     * getter ore setter.
     *
     * @return
     */


    public void setName(String name) {
        this.name = name;
    }
    /**
     * getter ore setter.
     *
     * @return
     */

    public int getScore() {
        return score;
    }

    /**
     * getter ore setter.
     *
     * @return
     */
    public void setScore(int score) {
        this.score = score;
    }
    /**
     * getter ore setter.
     *
     * @return
     */

    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * just an euqlas fpr testing.
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LeaderBoardEntity)) return false;
        LeaderBoardEntity that = (LeaderBoardEntity) o;
        return getRank() == that.getRank() && getScore() == that.getScore()
                && getName().equals(that.getName()) && getPosition() == that.getPosition();
    }

    /**
     * generates hashes.
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(getRank(), getName(), getScore(), getPosition());
    }
}
