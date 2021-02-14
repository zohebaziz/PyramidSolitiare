package cs3500.pyramidsolitaire;

import cs3500.pyramidsolitaire.controller.PyramidSolitaireController;
import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator.GameType;
import java.io.InputStreamReader;

/**
 * Main class for PyramidSolitaire. Will initialize a game of PyramidSolitaire.
 */
public final class PyramidSolitaire {

  /**
   * Main method for PyramidSolitaire. Allows the user to play a full text based game of
   * PyramidSolitaire in the console.
   * @param args  user settings for what implementation of PyramidSolitaire they would like to
   *              play.
   */
  public static void main(String[] args) {

    PyramidSolitaireModel<Card> model = null;
    PyramidSolitaireCreator creator = new PyramidSolitaireCreator();
    PyramidSolitaireController controller =
        new PyramidSolitaireTextualController(new InputStreamReader(System.in), System.out);

    switch (args[0]) {

      case "basic":
        model = creator.create(GameType.BASIC);
        break;
      case "relaxed":
        model = creator.create(GameType.RELAXED);
        break;
      case "multipyramid":
        model = creator.create(GameType.MULTIPYRAMID);
        break;
      default: // no default case needed since all possible valid inputs are covered
        break;
    }

    if (args.length == 1) {

      controller.playGame(model, model.getDeck(), true, 7, 3);
    } else if (args.length == 3) {

      controller.playGame(model, model.getDeck(), true,
          Integer.parseInt(args[1]), Integer.parseInt(args[2]));
    }
  }
}
