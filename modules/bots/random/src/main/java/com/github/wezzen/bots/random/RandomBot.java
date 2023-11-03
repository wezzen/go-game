package com.github.wezzen.bots.random;

import com.github.wezzen.base.Action;
import com.github.wezzen.base.Answer;
import com.github.wezzen.base.Color;
import com.github.wezzen.bots.base.Bot;
import com.github.wezzen.go.Game;
import com.github.wezzen.go.GameField;

import java.util.List;

public class RandomBot extends Bot {

    public static class RandomBotFactory extends BotFactory {

        @Override
        public Bot createBot(final Game game, final Color color) {
            return new RandomBot(game, color);
        }
    }

    private static final String NAME = RandomBot.class.getSimpleName();

    private int totalMoves;

    public RandomBot(final Game game, final Color color) {
        super(game, color);
    }

    private Answer.Type chooseAction() {
        final double p = (double) totalMoves / (game.getGameSize() * game.getGameSize());
        return p > Math.random() ? Answer.Type.PASS : Answer.Type.ACTION;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void startGame() {
        super.startGame();
        totalMoves = 0;
    }

    @Override
    public void playerActed(final String name, final Action action) {
        super.playerActed(name, action);
        totalMoves++;
    }

    @Override
    public Answer getAnswer() {
        final Answer.Type type = chooseAction();
        switch (type) {
            case ACTION -> {
                final GameField field = game.getGameField();
                final List<Action> availableActions = field.getAvailableActions(color);
                if (availableActions.size() == 0) {
                    return new Answer(Answer.Type.PASS, null);
                }
                final int random = getRandom(0, availableActions.size() - 1);
                final int x = availableActions.get(random).x;
                final int y = availableActions.get(random).y;
                return new Answer(Answer.Type.ACTION, new Action(x, y));
            }
            case PASS -> {
                return new Answer(Answer.Type.PASS, null);
            }
            default -> throw new IllegalStateException("Invalid answerType " + type.name());
        }

    }
}
