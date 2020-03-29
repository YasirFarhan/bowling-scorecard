package bowling.scroecard

import bowling.scorecard.ScoreCard
import bowling.scorecard.model.Frame
import bowling.scorecard.model.Player
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class ScoreCardSpec extends Specification {


    def scoreCard = new ScoreCard()

    def "score can be recorded when both balls in a frame scores"() {
        given:
        Player p = new Player()
        p.setName("p1")
        List<Frame> frame = new ArrayList<>();
        frame.add(newFrame)
        p.setFrame(frame)

        when:
        def response = scoreCard.recordScore(p)
        then:
        response.totalScore == totalScore
        response.getFrame().get(0).getFrameRunningTotal() == frameTotal

        where:
        newFrame              | totalScore | frameTotal
        addNewFrame(1, 3, 3)  | 6          | 6
        addNewFrame(1, 10, 0) | 10         | 10
        addNewFrame(1, 0, 10) | 10         | 10
        addNewFrame(1, 5, 5)  | 10         | 10
        addNewFrame(1, 6, 2)  | 8          | 8
        addNewFrame(1, 4, 4)  | 8          | 8
        addNewFrame(1, 0, 0)  | 0          | 0
    }

    def "any frame can be recorded can be independently recorded added"() {
        given:
        Player p = new Player()
        p.setName("p1")
        List<Frame> frame = new ArrayList<>();
        frame.add(newFrame)
        p.setFrame(frame)

        when:
        def response = scoreCard.recordScore(p)

        then:
        response.totalScore == totalScore

        where:
        newFrame              | totalScore
        addNewFrame(1, 10, 0) | 10
        addNewFrame(2, 3, 3)  | 6
        addNewFrame(2, 0, 10) | 10
        addNewFrame(7, 0, 5)  | 5
        addNewFrame(2, 6, 2)  | 8
        addNewFrame(3, 4, 4)  | 8
        addNewFrame(5, 0, 0)  | 0
    }

    def "frame total can be calculated"() {
        given:
        Player p = new Player()
        p.setName("p1")
        List<Frame> frame = new ArrayList<>();
        p.setFrame(frame)
        frame.add(newFrame)

        when:
        def response = scoreCard.recordScore(p)

        then:
        response.getFrame().get(0).getFrameRunningTotal() == frameTotal

        where:
        newFrame               | frameTotal
        addNewFrame(2, 3, 3)   | 6
        addNewFrame(6, 10, 0)  | 10
        addNewFrame(2, 0, 10)  | 10
        addNewFrame(7, 0, 5)   | 5
        addNewFrame(2, 6, 2)   | 8
        addNewFrame(3, 4, 4)   | 8
        addNewFrame(5, 0, 0)   | 0
        addNewFrame(5, 10, 10) | 20
    }

    def "total can keep track of all 10 frames | no spares or strikes"() {
        when:
        Player player = new Player()
        player.setName("p1")
        List<Frame> frame = new ArrayList<>();
        player.setFrame(frame)

        frame.add(addNewFrame(1, 3, 3))
        scoreCard.recordScore(player)

        frame.add(addNewFrame(2, 4, 4))
        scoreCard.recordScore(player)

        frame.add(addNewFrame(3, 6, 0))
        scoreCard.recordScore(player)

        frame.add(addNewFrame(4, 7, 1))
        scoreCard.recordScore(player)

        frame.add(addNewFrame(5, 5, 3))
        scoreCard.recordScore(player)

        frame.add(addNewFrame(6, 9, 0))
        scoreCard.recordScore(player)

        frame.add(addNewFrame(7, 0, 0))
        scoreCard.recordScore(player)

        frame.add(addNewFrame(8, 6, 0))
        scoreCard.recordScore(player)

        frame.add(addNewFrame(9, 8, 0))
        scoreCard.recordScore(player)

        frame.add(addNewFrame(10, 5, 1))
        scoreCard.recordScore(player)

        then: 'total after all frames'
        player.getFrame().get(0).getFrameRunningTotal() == 6
        player.getFrame().get(1).getFrameRunningTotal() == 14
        player.getFrame().get(2).getFrameRunningTotal() == 20
        player.getFrame().get(3).getFrameRunningTotal() == 28
        player.getFrame().get(4).getFrameRunningTotal() == 36
        player.getFrame().get(5).getFrameRunningTotal() == 45
        player.getFrame().get(6).getFrameRunningTotal() == 45
        player.getFrame().get(7).getFrameRunningTotal() == 51
        player.getFrame().get(8).getFrameRunningTotal() == 59
        player.getFrame().get(9).getFrameRunningTotal() == 65

        and: 'player total is correctly calculated'
        player.getTotalScore() == 65

        and: 'all 10 frames are recorded'
        player.getFrame().size() == 10
    }

    def "test spare frames"() {
        when:
        Player player = new Player()
        player.setName("p1")
        List<Frame> frame = new ArrayList<>();
        player.setFrame(frame)

        frame.add(addNewFrame(1, 9, 1))//14
        scoreCard.recordScore(player)

        frame.add(addNewFrame(2, 4, 4))//22
        scoreCard.recordScore(player)

        frame.add(addNewFrame(3, 8, 2))//34
        scoreCard.recordScore(player)

        frame.add(addNewFrame(4, 2, 5))//41
        scoreCard.recordScore(player)

        frame.add(addNewFrame(5, 2, 5))//48
        scoreCard.recordScore(player)


        frame.add(addNewFrame(6, 5, 5))//62
        scoreCard.recordScore(player)

        frame.add(addNewFrame(7, 4, 2))//68
        scoreCard.recordScore(player)

        frame.add(addNewFrame(8, 1, 7))//76
        scoreCard.recordScore(player)

        frame.add(addNewFrame(9, 3, 3))//82
        scoreCard.recordScore(player)

        def lastFrame = addNewFrame(10, 5, 5)//97
        addThirdThrow(lastFrame, 5)
        frame.add(lastFrame)
        scoreCard.recordScore(player)

        then: 'all 10 frames are recorded'
        player.getFrame().size() == 10

        and: 'total after all frames'
        player.getFrame().get(0).getFrameRunningTotal() == 14
        player.getFrame().get(1).getFrameRunningTotal() == 22
        player.getFrame().get(2).getFrameRunningTotal() == 34
        player.getFrame().get(3).getFrameRunningTotal() == 41
        player.getFrame().get(4).getFrameRunningTotal() == 48
        player.getFrame().get(5).getFrameRunningTotal() == 62
        player.getFrame().get(6).getFrameRunningTotal() == 68

        player.getFrame().get(7).getFrameRunningTotal() == 76
        player.getFrame().get(8).getFrameRunningTotal() == 82
        player.getFrame().get(9).getFrameRunningTotal() == 97

        and: 'player total is correctly calculated'
        player.getTotalScore() == 97


    }

    def addNewFrame(int frameNumber, int score1, int score2) {
        def frame = new Frame()
        frame.setFrameNumber(frameNumber)
        frame.setThrow1(score1)
        frame.setThrow2(score2)
        return frame
    }

    def addThirdThrow(Frame frame, int thirdThrow) {
        frame.setThrow3(thirdThrow)
    }
}
