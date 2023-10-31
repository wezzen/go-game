package com.github.wezzen.bots.random;

import com.github.wezzen.base.Action;
import com.github.wezzen.base.Answer;
import com.github.wezzen.base.Color;
import com.github.wezzen.bots.base.Bot;
import com.github.wezzen.go.Game;
import com.github.wezzen.go.GameField;

public class RandomBot extends Bot {

    public static class RandomBotFactory extends BotFactory {

        @Override
        public Bot createBot(final Game game, final Color color) {
            return new RandomBot(game, color);
        }
    }

    private static final String NAME = RandomBot.class.getSimpleName();

    public RandomBot(final Game game, final Color color) {
        super(game, color);
    }

    private Answer.Type chooseAction() {
        final int emptyNum = game.getGameField().getNumEmptyPlaces();
        final int filledNum = game.getGameField().getNumPlacedStones();
        final double p = (double) filledNum / emptyNum;
        return p > Math.random() ? Answer.Type.PASS : Answer.Type.ACTION;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Answer getAnswer() {
        final Answer.Type type = chooseAction();
        switch (type) {
            case ACTION -> {
                final GameField field = game.getGameField();
                final int gameSize = game.getGameSize();
                int x, y;
                do {
                    x = getRandom(0, gameSize - 1);
                    y = getRandom(0, gameSize - 1);
                } while (!field.isActionAvailable(x, y));
                return new Answer(Answer.Type.ACTION, new Action(x, y));
            }
            case PASS -> {
                return new Answer(Answer.Type.PASS, null);
            }
            default -> throw new IllegalStateException("Invalid answerType " + type.name());
        }

    }
}
