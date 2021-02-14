import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.Face;
import cs3500.pyramidsolitaire.model.hw02.Posn;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw02.Suite;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests for methods in BasicPyramidSolitaire.
 */

public class BasicPyramidSolitaireTest {

  PyramidSolitaireModel<Card> cardDeck1 = new BasicPyramidSolitaire();

  BasicPyramidSolitaire startDeck1 = new BasicPyramidSolitaire();
  BasicPyramidSolitaire shuffleDeck1 = new BasicPyramidSolitaire();

  PyramidSolitaireTextualView text1 = new PyramidSolitaireTextualView(this.startDeck1);

  Card firstCard = new Card(Suite.Heart, Face.Ace);
  Card kingOfHearts = new Card(Suite.Heart, Face.King);
  Card aceSpade = new Card(Suite.Spade, Face.Ace);
  Card queenOfHearts = new Card(Suite.Heart, Face.Queen, false);

  Posn top = new Posn(0, 0);
  Posn def = new Posn(-1, -1);

  // Just going through motions of the game to try and find bugs.
  @Test
  public void playingTheGame() {

    this.startDeck1.startGame(this.startDeck1.getDeck(), false, 7, 3);

    this.text1.toString();

    this.startDeck1.remove(6, 4);
    this.startDeck1.remove(6, 3, 6, 5);
    this.startDeck1.remove(6, 2, 6, 6);
    this.startDeck1.removeUsingDraw(0, 6, 1);

    assertEquals(this.startDeck1.getDrawCards().get(0),
        new Card(Suite.Club, Face.Six));

    assertEquals(this.startDeck1.getDrawCards().get(2),
        new Card(Suite.Club, Face.Five));

    this.startDeck1.removeUsingDraw(2, 5, 5);

    this.startDeck1.removeUsingDraw(2, 5, 3);

    this.startDeck1.removeUsingDraw(2, 5, 2);

    this.startDeck1.removeUsingDraw(2, 5, 1);

    this.text1.toString();

    this.startDeck1.remove(4, 2);

    this.startDeck1.removeUsingDraw(0, 5, 4);

    this.startDeck1.discardDraw(0);
    this.startDeck1.discardDraw(0);
    this.startDeck1.discardDraw(0);
    this.startDeck1.discardDraw(0);
    this.startDeck1.discardDraw(0);
    this.startDeck1.discardDraw(0);
    this.startDeck1.discardDraw(0);
    this.startDeck1.discardDraw(0);
    this.startDeck1.discardDraw(0);
    this.startDeck1.discardDraw(0);
    this.startDeck1.discardDraw(0);
    this.startDeck1.discardDraw(0);
    this.startDeck1.discardDraw(0);
    this.startDeck1.discardDraw(0);
    this.startDeck1.discardDraw(0);

    this.startDeck1.discardDraw(0);
    this.startDeck1.discardDraw(1);
    this.startDeck1.discardDraw(2);

    this.text1.toString();

    this.startDeck1.remove(4, 1, 4, 3);

    this.startDeck1.getDrawCards().size();

    assertTrue(this.startDeck1.isGameOver());

    this.text1.toString();
  }

  // tests to make sure that the getDeck() method works as intended
  @Test
  public void testDeck() {

    assertEquals(this.firstCard,
        this.cardDeck1.getDeck().get(0));

    assertEquals(new Card(Suite.Spade, Face.Five),
        this.cardDeck1.getDeck().get(17));
  }

  // Series of tests to make sure the game is starting up correctly
  @Test
  public void startingTheGame() {

    assertEquals(-1, this.startDeck1.getNumDraw());

    this.startDeck1.startGame(this.startDeck1.getDeck(), false, 7, 24);
    this.shuffleDeck1.startGame(this.shuffleDeck1.getDeck(), true, 7, 24);

    assertEquals(24, this.startDeck1.getNumDraw());
    assertEquals(7, this.startDeck1.getNumRows());
    assertEquals(24, this.startDeck1.getDrawCards().size());
  }

  // Series of tests to make sure all the exceptions are thrown correctly in startGame()
  @Test(expected = IllegalArgumentException.class)
  public void nullStart() {

    this.startDeck1.startGame(null, false, 7, 24);
  }

  @Test(expected = IllegalArgumentException.class)
  public void duplicateDeck() {

    List<Card> badDeck = new ArrayList<>(this.startDeck1.getDeck());
    badDeck.remove(0);
    badDeck.add(0, new Card(Suite.Heart, Face.Two));
    this.startDeck1.startGame(badDeck, false, 7, 24);
  }

  @Test(expected = IllegalArgumentException.class)
  public void tooManyDraw() {

    this.startDeck1.startGame(this.startDeck1.getDeck(), false, 7, 26);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negRows() {

    this.startDeck1.startGame(this.startDeck1.getDeck(), false, 0, 24);
  }

  @Test(expected = IllegalArgumentException.class)
  public void zeroDraw() {

    this.startDeck1.startGame(this.startDeck1.getDeck(), false, 7, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void badDeck() {

    List<Card> testingDecking = this.startDeck1.getDeck();
    testingDecking.remove(0);
    this.startDeck1.startGame(testingDecking, false, 7, 24);
  }

  // Tests for methods in the Card class
  @Test
  public void testCards() {

    assertEquals(1, this.aceSpade.getValue());
    assertFalse(this.aceSpade.isExposed());
    assertEquals(12, this.queenOfHearts.getValue());
    assertEquals(new Posn(-1, -1), this.aceSpade.getPosn());

    this.aceSpade.changePosn(6, 4);
    this.aceSpade.exposeCard();

    assertTrue(this.aceSpade.isExposed());
    assertEquals(new Posn(6, 4), this.aceSpade.getPosn());
  }

  // Test getCardAt() first because it makes testing other methods easier
  @Test
  public void testGettingCard() {

    this.startDeck1.startGame(this.startDeck1.getDeck(), false, 7, 24);

    assertEquals(this.firstCard, this.startDeck1.getCardAt(0, 0));
    assertEquals(this.kingOfHearts, this.startDeck1.getCardAt(4, 2));
  }

  // getCard before game
  @Test(expected = IllegalStateException.class)
  public void cardBeforeGame() {

    this.startDeck1.getCardAt(0, 0);
  }

  // Invalid getCard() calls
  @Test(expected = IllegalArgumentException.class)
  public void badIndexCard() {

    this.startDeck1.startGame(this.startDeck1.getDeck(), false, 7, 24);
    this.startDeck1.getCardAt(8, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void badCardToGet() {

    this.startDeck1.startGame(this.startDeck1.getDeck(), false, 7, 24);
    this.startDeck1.getCardAt(9, 2);
  }

  // Test getDrawCards() also because testing other methods becomes easier if this works
  @Test
  public void testGettingDraws() {

    this.startDeck1.startGame(this.startDeck1.getDeck(), false, 7, 24);
    assertEquals(24, this.startDeck1.getDrawCards().size());
    assertEquals(new Card(Suite.Club, Face.Three),
        this.startDeck1.getDrawCards().get(0));
  }

  // getDrawCards() before game start
  @Test(expected = IllegalStateException.class)
  public void drawBeforeGame() {

    this.startDeck1.getDrawCards();
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeUnexposed() {

    this.startDeck1.startGame(this.startDeck1.getDeck(), false, 7, 24);

    this.startDeck1.remove(4, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeUnexposed2() {

    this.startDeck1.startGame(this.startDeck1.getDeck(), false, 7, 24);

    this.startDeck1.remove(0, 0, 4, 1); // ace and queen
  }

  // Try removing two cards that dont add up to 13
  @Test(expected = IllegalArgumentException.class)
  public void removeTwoNotThirteen() {

    this.startDeck1.startGame(this.startDeck1.getDeck(), false, 7, 24);

    this.startDeck1.remove(0, 0, 4, 0);
  }

  // Try removing a card that is null
  @Test(expected = IllegalArgumentException.class)
  public void removeOneNull() {

    this.startDeck1.startGame(this.startDeck1.getDeck(), false, 7, 24);

    this.startDeck1.remove(0, 0);

    this.startDeck1.remove(0, 0);

  }

  // Try removing a single card not worth 13
  @Test(expected = IllegalArgumentException.class)
  public void removeNotThirteen() {

    this.startDeck1.startGame(this.startDeck1.getDeck(), false, 7, 24);

    this.startDeck1.remove(4, 1);
  }

  // Tests for removing using draw and discard draw
  @Test
  public void removeTheDraws() {

    this.startDeck1.startGame(this.startDeck1.getDeck(), false, 7, 1);

    this.startDeck1.discardDraw(0);

    assertEquals(new Card(Suite.Club, Face.Four),
        this.startDeck1.getDrawCards().get(0));

    this.startDeck1.removeUsingDraw(0, 6, 0);

    assertEquals(new Card(Suite.Club, Face.Five), this.startDeck1.getDrawCards().get(0));


  }

  // Lots of invalid draw pile tests with exception coming ahead

  @Test(expected = IllegalArgumentException.class)
  public void drawNeedToBeInBounds() {

    this.startDeck1.startGame(this.startDeck1.getDeck(), false, 7, 24);

    this.startDeck1.discardDraw(26);
  }

  @Test(expected = IllegalArgumentException.class)
  public void drawNeedToBeWithCardInBounds() {

    this.startDeck1.startGame(this.startDeck1.getDeck(), false, 7, 24);

    this.startDeck1.removeUsingDraw(25, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void drawNeedToBeExistingCard() {

    this.startDeck1.startGame(this.startDeck1.getDeck(), false, 7, 24);

    this.startDeck1.removeUsingDraw(9, 8, 4);
  }

  // Removing a card before the game starts
  @Test(expected = IllegalStateException.class)
  public void removeBeforeGame1() {

    this.startDeck1.remove(0, 0, 0, 0);
  }

  @Test(expected = IllegalStateException.class)
  public void removeBeforeGame2() {

    this.startDeck1.remove(0, 0);
  }

  @Test(expected = IllegalStateException.class)
  public void removeBeforeGameDraw() {

    this.startDeck1.removeUsingDraw(1, 0, 0);
  }

  @Test(expected = IllegalStateException.class)
  public void removeBeforeGameDraw2() {

    this.startDeck1.discardDraw(0);
  }

  // Quick tests for getNumRows and getNumDraw
  @Test
  public void testEasyNumbers() {

    this.startDeck1.startGame(this.startDeck1.getDeck(), false, 7, 24);

    assertEquals(7, this.startDeck1.getNumRows());

    assertEquals(24, this.startDeck1.getNumDraw());

  }

  // Try and access these values before game start
  @Test
  public void testWrongNumbers() {

    assertEquals(-1, this.startDeck1.getNumRows());

    assertEquals(-1, this.startDeck1.getNumDraw());
  }

  // Test getRowWidth
  @Test
  public void testTheWidth() {

    this.startDeck1.startGame(this.startDeck1.getDeck(), false, 7, 24);

    assertEquals(1, this.startDeck1.getRowWidth(0));
    assertEquals(7, this.startDeck1.getRowWidth(6));
  }

  @Test(expected = IllegalArgumentException.class)
  public void widthTooBig() {

    this.startDeck1.startGame(this.startDeck1.getDeck(), false, 7, 24);

    this.startDeck1.getRowWidth(7);
  }

  // Getting width before game starts
  @Test(expected = IllegalStateException.class)
  public void widthBeforeGame() {

    this.startDeck1.getRowWidth(4);
  }


  // Test game over from score becoming zero
  @Test
  public void testGameState() {

    this.startDeck1.startGame(this.startDeck1.getDeck(), false, 7, 24);

    assertFalse(this.startDeck1.isGameOver());

    assertEquals(185, this.startDeck1.getScore());

    assertFalse(this.startDeck1.isGameOver());
  }

  // Test game over from empty draw pile
  @Test
  public void testGameState2() {

    this.startDeck1.startGame(this.startDeck1.getDeck(), false, 7, 24);

    assertFalse(this.startDeck1.isGameOver());

    assertEquals(185, this.startDeck1.getScore());

    this.startDeck1.getDrawCards().clear();

    assertEquals(185, this.startDeck1.getScore());

    assertFalse(this.startDeck1.isGameOver());
  }

  // Getting score before game start
  @Test(expected = IllegalStateException.class)
  public void scoreBeforeGame() {

    this.startDeck1.getScore();
  }

  // Checking if game is over before it starts
  @Test(expected = IllegalStateException.class)
  public void gameOverNoGameBegin() {

    this.startDeck1.isGameOver();
  }

  // Tests the card to string and textual view
  @Test
  public void testToString() {

    assertEquals("A♠", this.aceSpade.toString());
  }

  // Tests for the Posn class
  @Test
  public void testPosn() {

    assertEquals(this.firstCard.getPosn(), this.def);

    this.def.changePositions(0, 0);

    assertEquals(this.def, this.top);
  }

  @Test
  public void testPosn2() {

    assertEquals(this.firstCard.getPosn(), this.def);

    this.firstCard.changePosn(0, 0);

    assertEquals(this.firstCard.getPosn(), this.top);
  }
}
