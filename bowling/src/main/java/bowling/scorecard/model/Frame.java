package bowling.scorecard.model;

public class Frame {
    private int frameNumber;
    //    private String type;//strike or spare
    private int throw1;
    private int throw2;
    private int throw3;
    private int frameRunningTotal;

    public int getThrow1() {
        return throw1;
    }

    public void setThrow1(int throw1) {
        this.throw1 = throw1;
    }

    public int getThrow2() {
        return throw2;
    }

    public void setThrow2(int throw2) {
        this.throw2 = throw2;
    }

    public int getFrameRunningTotal() {
        return frameRunningTotal;
    }

    public void calculateFrameRunningTotal(int scoreToAdd) {
        this.frameRunningTotal = this.throw1 + this.throw2 + this.throw3 + scoreToAdd;
    }

    public void setFrameRunningTotal(int frameRunningTotal) {
        this.frameRunningTotal = frameRunningTotal;
    }

    public int getFrameNumber() {
        return frameNumber;
    }

    public void setFrameNumber(int frameNumber) {
        this.frameNumber = frameNumber;
    }

    public int getThrow3() {
        return throw3;
    }

    public void setThrow3(int throw3) {
        this.throw3 = throw3;
    }
}
