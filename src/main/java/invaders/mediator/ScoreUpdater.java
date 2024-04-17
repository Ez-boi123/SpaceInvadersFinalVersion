package invaders.mediator;

import invaders.engine.GameEngine;

public class ScoreUpdater implements ScoreParticipant {
    private GameEngine gameEngine;
    private int scoreIncrement;

    public ScoreUpdater(GameEngine gameEngine, int scoreIncrement) {
        this.gameEngine = gameEngine;
        this.scoreIncrement = scoreIncrement;
    }

    @Override
    public void update() {
        gameEngine.addScore(scoreIncrement);
    }
}
