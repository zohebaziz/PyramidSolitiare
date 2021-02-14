package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

/**
 * Factory class that is used to create one of the models of PyramidSolitaire.
 */
public class PyramidSolitaireCreator {

  /**
   * Each represents one of the implementations of PyramidSolitaire that is to be played.
   */
  public enum GameType {

    BASIC,
    RELAXED,
    MULTIPYRAMID
  }

  /**
   * Constructs a model of PyramidSolitaire.
   * @param type The implementation the user wishes to create.
   * @return A model of PyramidSolitaire.
   */
  public static PyramidSolitaireModel<Card> create(GameType type) {

    switch (type) {

      case RELAXED:
        return new RelaxedPyramidSolitaire();

      case MULTIPYRAMID:
        return new MultiPyramidSolitaire();

      default:
        return new BasicPyramidSolitaire();
    }
  }
}
