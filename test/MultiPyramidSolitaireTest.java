import cs3500.pyramidsolitaire.controller.PyramidSolitaireController;
import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;
import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.Face;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw02.Suite;
import cs3500.pyramidsolitaire.model.hw04.MultiPyramidSolitaire;

import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;
import cs3500.pyramidsolitaire.view.PyramidSolitaireView;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Test class for the MultiPyramidSolitaire model implementation.
 */
public class MultiPyramidSolitaireTest {

  PyramidSolitaireModel<Card> mModel = new MultiPyramidSolitaire();
  PyramidSolitaireModel<Card> bModel = new BasicPyramidSolitaire();

  PyramidSolitaireView view = new PyramidSolitaireTextualView(this.mModel);

  PyramidSolitaireController controller;

  List<Card> multiDeck = new ArrayList<>(mModel.getDeck());

  // Check to see if getDeck is returning a valid deck
  // also tests my check dupes method as well
  @Test
  public void testGettingDeck() {

    List<Card> valid52 = new ArrayList<>(this.bModel.getDeck());

    // should not be the same as basic deck
    assertNotEquals(valid52, this.multiDeck);

    // should be 104 cards in deck
    assertEquals(104, this.multiDeck.size());

    // after removing a valid deck, a valid deck should remain
    for (Card c : valid52) {

      this.multiDeck.remove(c);
    }

    // A valid deck should remain
    assertEquals(52, this.multiDeck.size());
    assertEquals(this.multiDeck, valid52);
  }

  // Make sure start game throws all the exceptions it needs to

  @Test(expected = IllegalArgumentException.class)
  public void nullDeck() {

    this.mModel.startGame(null, false,  7, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negRows() {

    this.mModel.startGame(this.multiDeck, false, -1, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negDraw() {

    this.mModel.startGame(this.multiDeck, false, 7, -1);
  }

  // Test that the view is rendering correctly
  @Test
  public void testToString1() {

    // For a single row
    this.mModel.startGame(this.multiDeck, false, 1, 3);
    assertEquals("A♥\n"
        + "Draw: 2♥, 3♥, 4♥", this.view.toString());
  }

  @Test
  public void testToString3() {

    // For multiple rows but not yet adding the placeholder values
    this.mModel.startGame(this.multiDeck, false, 3, 3);
    assertEquals("    A♥  2♥  3♥\n"
        + "  4♥  5♥  6♥  7♥\n"
        + "8♥  9♥  10♥ J♥  Q♥\n"
        + "Draw: K♥, A♠, 2♠", this.view.toString());
  }

  @Test
  public void testToString6() {

    // For multiple rows but not yet adding the placeholder values
    this.mModel.startGame(this.multiDeck, false, 6, 3);
    assertEquals("          A♥  .   .   2♥  .   .   3♥\n"
        + "        4♥  5♥  .   6♥  7♥  .   8♥  9♥\n"
        + "      10♥ J♥  Q♥  K♥  A♠  2♠  3♠  4♠  5♠\n"
        + "    6♠  7♠  8♠  9♠  10♠ J♠  Q♠  K♠  A♣  2♣\n"
        + "  3♣  4♣  5♣  6♣  7♣  8♣  9♣  10♣ J♣  Q♣  K♣\n"
        + "A♦  2♦  3♦  4♦  5♦  6♦  7♦  8♦  9♦  10♦ J♦  Q♦\n"
        + "Draw: K♦, A♥, 2♥", this.view.toString());
  }

  // Test that nulls are in the correct place
  @Test
  public void emptySpacesCorrect() {

    this.mModel.startGame(this.multiDeck, false, 4, 2);
    // first card is an ace
    assertEquals(this.mModel.getCardAt(0, 0), new Card(Suite.Heart, Face.Ace));
    // second position is a placeholder
    assertEquals(this.mModel.getCardAt(0, 1), null);
    // make sure next card is a 2 and didnt get skipped over
    assertEquals(this.mModel.getCardAt(0, 2), new Card(Suite.Heart, Face.Two));
    // make sure card doesnt get skipped when going to a new row
    assertEquals(this.mModel.getCardAt(1, 0), new Card(Suite.Heart, Face.Four));
  }

  @Test
  public void testScore() {

    this.mModel.startGame(this.multiDeck, false, 4, 2);

    assertEquals(157, this.mModel.getScore());

    // make sure score is still good after making some remove actions

    this.mModel.remove(3, 2, 3, 3);

    assertEquals(144, this.mModel.getScore());
  }

  // Tests for getRowWidth for multi
  @Test
  public void getRowWidth() {

    this.mModel.startGame(this.multiDeck, false, 7, 3);

    // normal row with width no empty spaces
    assertEquals(13, this.mModel.getRowWidth(6));

    // make sure is counting the empty spaces correctly
    assertEquals(7, this.mModel.getRowWidth(0));
  }

  @Test
  public void testExposing() {

    this.mModel.startGame(this.multiDeck, false, 4, 3);

    this.mModel.remove(3, 2, 3, 3);
    this.mModel.remove(3, 1, 3, 4);

    assertEquals(true, this.mModel.getCardAt(2, 1).isExposed());
    assertEquals(false, this.mModel.getCardAt(2, 0).isExposed());

    this.mModel.removeUsingDraw(2, 2, 2);
    this.mModel.removeUsingDraw(2, 2, 1);

    this.mModel.remove(2, 3);

    this.mModel.discardDraw(0);
    this.mModel.discardDraw(0);
    this.mModel.discardDraw(0);
    this.mModel.discardDraw(0);

    this.mModel.removeUsingDraw(0, 1, 2);
    this.mModel.removeUsingDraw(0, 1, 1);

    // make sure cards next to the placeholder didnt get exposed
    assertEquals(false, this.mModel.getCardAt(0, 0).isExposed());
    assertEquals(false, this.mModel.getCardAt(0, 2).isExposed());

  }

  // try to remove a card with one the empty spaces
  @Test(expected = IllegalArgumentException.class)
  public void removeEmpty() {

    this.mModel.startGame(this.multiDeck, false, 4, 3);

    this.mModel.remove(3, 2, 3, 3);
    this.mModel.remove(3, 1, 3, 4);

    this.mModel.removeUsingDraw(2, 2, 2);
    this.mModel.removeUsingDraw(2, 2, 1);

    this.mModel.remove(2, 3);

    this.mModel.discardDraw(0);
    this.mModel.discardDraw(0);
    this.mModel.discardDraw(0);
    this.mModel.discardDraw(0);

    this.mModel.removeUsingDraw(0, 1, 2);
    this.mModel.removeUsingDraw(0, 1, 1);

    // try to remove a placeholder
    this.mModel.remove(0, 1, 3, 0);

  }

  // Test interactions with the controller

  // when given invalid deck
  @Test(expected = IllegalArgumentException.class)
  public void invalidDeck() {

    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("");
    this.controller = new PyramidSolitaireTextualController(in, out);
    this.multiDeck.remove(new Card(Suite.Heart, Face.Ace));
    this.multiDeck.remove(new Card(Suite.Spade, Face.Ace));
    this.controller.playGame(this.mModel, this.multiDeck, false, 7, 3);
  }

  // invalid draws
  @Test(expected = IllegalArgumentException.class)
  public void tooManyDraws() {

    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("");
    this.controller = new PyramidSolitaireTextualController(in, out);
    this.controller.playGame(this.mModel, this.multiDeck, false, 8, 43);
  }
}
