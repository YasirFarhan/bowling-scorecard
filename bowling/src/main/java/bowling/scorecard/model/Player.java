package bowling.scorecard.model;

import java.util.List;

public class Player {
    private String name;
    private List<Frame> frame;
    private int totalScore;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Frame> getFrame() {
        return frame;
    }

    public void setFrame(List<Frame> frame) {
        this.frame = frame;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int latestFrameTotal) {
        this.totalScore = latestFrameTotal;
    }
}
