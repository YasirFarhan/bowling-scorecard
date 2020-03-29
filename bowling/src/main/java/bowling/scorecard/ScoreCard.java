package bowling.scorecard;

import bowling.scorecard.model.Frame;
import bowling.scorecard.model.Player;
import org.springframework.stereotype.Component;

@Component
public class ScoreCard {

    public Player recordScore(Player player) {
        int frameNumber = getFrameNumber(player);
        recordASpareOrAStrike(player);
        frameRunningTotal(player);
        updatePlayerTotalScore(player);
        return player;
    }

    private void frameRunningTotal(Player player) {

        int frameNumber = getFrameNumber(player);
        int previousFrameTotal = 0;
        if (frameNumber > 1) {
            previousFrameTotal = player.getFrame().get(frameNumber - 2).getFrameRunningTotal();
        }
        player.getFrame().get(frameNumber - 1).calculateFrameRunningTotal(previousFrameTotal);
    }

    private void recordASpareOrAStrike(Player player) {
        int currentFrameNumber = getFrameNumber(player);

        if (currentFrameNumber == 1) return;

        Frame previousFrame = player.getFrame().get(currentFrameNumber - 2);

        if (isAStrike(previousFrame)) {
            int currentFrameThrow1 = player.getFrame().get(currentFrameNumber - 1).getThrow1();
            int currentFrameThrow2 = player.getFrame().get(currentFrameNumber - 1).getThrow2();
            previousFrame.setFrameRunningTotal(previousFrame.getFrameRunningTotal() + currentFrameThrow1 + currentFrameThrow2);
        } else if (isASpare(previousFrame)) {
            int currentFrameThrow1 = player.getFrame().get(currentFrameNumber - 1).getThrow1();
            previousFrame.setFrameRunningTotal(previousFrame.getFrameRunningTotal() + currentFrameThrow1);
        }
    }

    private boolean isAStrike(Frame frame) {
        return frame.getThrow1() == 10;
    }

    private boolean isASpare(Frame frame) {
        return frame.getThrow1() + frame.getThrow2() == 10;
    }

    private void updatePlayerTotalScore(Player player) {
        int latestFrame = getFrameNumber(player);
        int latestFrameTotal = player.getFrame().get(latestFrame - 1).getFrameRunningTotal();
        player.setTotalScore(latestFrameTotal);
    }

    private int getFrameNumber(Player player) {
        return player.getFrame().size();
    }


}
