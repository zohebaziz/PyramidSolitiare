import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.Face;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw02.Suite;
import cs3500.pyramidsolitaire.model.hw04.RelaxedPyramidSolitaire;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;
import cs3500.pyramidsolitaire.view.PyramidSolitaireView;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tester class for RelaxedPyramidSolitaire.
 */
public class RelaxedPyramidSolitaireTest {

  PyramidSolitaireModel<Card> rModel = new RelaxedPyramidSolitaire();

  PyramidSolitaireView view = new PyramidSolitaireTextualView(rModel);

  List<Card> specialDeck = new ArrayList<>(rModel.getDeck());

  // Going through motion of playing game so I can debug and test lots of interactions.
  @Test
  public void removingWhenCovering() {

    this.rModel.startGame(rModel.getDeck(), false, 7, 3);

    this.view.toString();

    this.rModel.removeUsingDraw(1, 6, 0);

    this.rModel.remove(6, 1, 5, 0); // successful remove

    assertEquals(this.rModel.getCardAt(5, 0), null); // make sure card there is gone
  }

  // Make sure relaxed conditions still enforce that two cards must add up to 13
  @Test (expected = IllegalArgumentException.class)
  public void cantRemoveIfNot13() {

    this.rModel.startGame(rModel.getDeck(), false, 7, 3);

    this.view.toString();

    assertEquals(this.rModel.getRowWidth(6), 7);

    this.rModel.removeUsingDraw(1, 6, 0);

    this.rModel.remove(6, 1, 5, 0);

    // Try a jack and 4 remove should fail
    this.rModel.remove(6, 2, 5, 1);
  }

  // Test is removed with relaxed conditions it doesn't expose the wrong cards
  @Test
  public void testExposingRelaxed() {

    this.rModel.startGame(rModel.getDeck(), false, 7, 3);

    this.view.toString();

    this.rModel.removeUsingDraw(1, 6, 0);

    this.rModel.remove(6, 1, 5, 0);

    // should not expose this card with this remove.
    assertEquals(false, this.rModel.getCardAt(5, 1).isExposed());

  }

  // Testing game over when a card can be removed by relaxed ruleset but not the original
  @Test
  public void testGameOver() {

    this.specialDeck.remove(new Card(Suite.Heart, Face.Queen));
    this.specialDeck.add(0, new Card(Suite.Heart, Face.Queen));

    this.rModel.startGame(specialDeck, false, 2, 3);

    this.view.toString();

    // removing almost all the draw card to get near an end game state to test

    this.rModel.discardDraw(1);
    this.rModel.discardDraw(1);
    this.rModel.discardDraw(1);
    this.rModel.discardDraw(1);
    this.rModel.discardDraw(1);
    this.rModel.discardDraw(1);
    this.rModel.discardDraw(1);
    this.rModel.discardDraw(1);
    this.rModel.discardDraw(1);
    this.rModel.discardDraw(1);
    this.rModel.discardDraw(1);
    this.rModel.discardDraw(1);
    this.rModel.discardDraw(1);
    this.rModel.discardDraw(1);
    this.rModel.discardDraw(1);
    this.rModel.discardDraw(1);
    this.rModel.discardDraw(1);
    this.rModel.discardDraw(1);
    this.rModel.discardDraw(1);
    this.rModel.discardDraw(1);
    this.rModel.discardDraw(1);
    this.rModel.discardDraw(1);
    this.rModel.discardDraw(1);
    this.rModel.discardDraw(1);
    this.rModel.discardDraw(1);
    this.rModel.discardDraw(1);
    this.rModel.discardDraw(1);
    this.rModel.discardDraw(1);
    this.rModel.discardDraw(1);
    this.rModel.discardDraw(1);
    this.rModel.discardDraw(1);
    this.rModel.discardDraw(1);
    this.rModel.discardDraw(1);
    this.rModel.discardDraw(1);
    this.rModel.discardDraw(1);
    this.rModel.discardDraw(1);
    this.rModel.discardDraw(1);
    this.rModel.discardDraw(1);
    this.rModel.discardDraw(1);
    this.rModel.discardDraw(1);
    this.rModel.discardDraw(1);
    this.rModel.discardDraw(1);
    this.rModel.discardDraw(1);
    this.rModel.discardDraw(1);

    // remove the 2 so the end game condition is a relaxed condition
    this.rModel.removeUsingDraw(1, 1, 1);

    this.rModel.discardDraw(1);
    this.rModel.discardDraw(1);
    this.rModel.discardDraw(1);

    assertEquals(false, this.rModel.isGameOver());

    // Now remove using relaxed condition and make sure that works

    this.rModel.remove(0, 0, 1, 0);

    assertEquals(true, rModel.isGameOver());
  }
}
