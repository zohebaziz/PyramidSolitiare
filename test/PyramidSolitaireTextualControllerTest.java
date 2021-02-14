import static org.junit.Assert.assertEquals;

import cs3500.pyramidsolitaire.controller.PyramidSolitaireController;
import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;
import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import java.io.Reader;
import java.io.StringReader;

import org.junit.Test;


/**
 * Test class for Textual implementation of the Pyramid Solitaire Controller.
 */
public class PyramidSolitaireTextualControllerTest {

  PyramidSolitaireModel<Card> model = new BasicPyramidSolitaire();
  PyramidSolitaireController controller;

  // tests win game
  @Test
  public void testWinGame() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("rmwd 1 3 3 rmwd 3 3 1 rmwd 2 3 2 rmwd 1 2 2 "
        + "rmwd 3 2 1 rmwd 2 1 1");
    this.controller = new PyramidSolitaireTextualController(in, out);
    this.controller.playGame(this.model, this.model.getDeck(), false,
        3, 3);
    assertEquals("    A♥\n"
        + "  2♥  3♥\n"
        + "4♥  5♥  6♥\n"
        + "Draw: 7♥, 8♥, 9♥\n"
        + "Score: 21\n"
        + "    A♥\n"
        + "  2♥  3♥\n"
        + "4♥  5♥  .\n"
        + "Draw: 10♥, 8♥, 9♥\n"
        + "Score: 15\n"
        + "    A♥\n"
        + "  2♥  3♥\n"
        + ".   5♥  .\n"
        + "Draw: 10♥, 8♥, J♥\n"
        + "Score: 11\n"
        + "    A♥\n"
        + "  2♥  3♥\n"
        + ".   .   .\n"
        + "Draw: 10♥, Q♥, J♥\n"
        + "Score: 6\n"
        + "    A♥\n"
        + "  2♥  .\n"
        + ".   .   .\n"
        + "Draw: K♥, Q♥, J♥\n"
        + "Score: 3\n"
        + "    A♥\n"
        + "  .   .\n"
        + ".   .   .\n"
        + "Draw: K♥, Q♥, A♠\n"
        + "Score: 1\n"
        + "You win!", out.toString());

  }

  // tests to see how an invalid output would react
  @Test
  public void testInvalid() {

    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("rm1 1 1 dd 4 4 12 quit game q");
    this.controller = new PyramidSolitaireTextualController(in, out);
    this.controller.playGame(this.model, this.model.getDeck(), false,
        7, 1);
    assertEquals("            A♥\n"
        + "          2♥  3♥\n"
        + "        4♥  5♥  6♥\n"
        + "      7♥  8♥  9♥  10♥\n"
        + "    J♥  Q♥  K♥  A♠  2♠\n"
        + "  3♠  4♠  5♠  6♠  7♠  8♠\n"
        + "9♠  10♠ J♠  Q♠  K♠  A♣  2♣\n"
        + "Draw: 3♣\n"
        + "Score: 185\n"
        + "Invalid move. Play again. Given card is invalid.\n"
        + "            A♥\n"
        + "          2♥  3♥\n"
        + "        4♥  5♥  6♥\n"
        + "      7♥  8♥  9♥  10♥\n"
        + "    J♥  Q♥  K♥  A♠  2♠\n"
        + "  3♠  4♠  5♠  6♠  7♠  8♠\n"
        + "9♠  10♠ J♠  Q♠  K♠  A♣  2♣\n"
        + "Draw: 3♣\n"
        + "Score: 185\n"
        + "Invalid move. Play again. Draw index was invalid.\n"
        + "            A♥\n"
        + "          2♥  3♥\n"
        + "        4♥  5♥  6♥\n"
        + "      7♥  8♥  9♥  10♥\n"
        + "    J♥  Q♥  K♥  A♠  2♠\n"
        + "  3♠  4♠  5♠  6♠  7♠  8♠\n"
        + "9♠  10♠ J♠  Q♠  K♠  A♣  2♣\n"
        + "Draw: 3♣\n"
        + "Score: 185\n"
        + "Invalid input. Retry\n"
        + "Invalid input. Retry\n"
        + "Invalid input. Retry\n"
        + "Invalid input. Retry\n"
        + "Game quit!\n"
        + "State of game when quit:\n"
        + "            A♥\n"
        + "          2♥  3♥\n"
        + "        4♥  5♥  6♥\n"
        + "      7♥  8♥  9♥  10♥\n"
        + "    J♥  Q♥  K♥  A♠  2♠\n"
        + "  3♠  4♠  5♠  6♠  7♠  8♠\n"
        + "9♠  10♠ J♠  Q♠  K♠  A♣  2♣\n"
        + "Draw: 3♣\n"
        + "Score: 185", out.toString());
  }

  // test to see all actions work
  @Test
  public void testActions() {

    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("rm1 7 5 rm2 7 4 7 6 f q");
    this.controller = new PyramidSolitaireTextualController(in, out);
    this.controller.playGame(this.model, this.model.getDeck(), false,
        7, 1);
    assertEquals("            A♥\n"
        + "          2♥  3♥\n"
        + "        4♥  5♥  6♥\n"
        + "      7♥  8♥  9♥  10♥\n"
        + "    J♥  Q♥  K♥  A♠  2♠\n"
        + "  3♠  4♠  5♠  6♠  7♠  8♠\n"
        + "9♠  10♠ J♠  Q♠  K♠  A♣  2♣\n"
        + "Draw: 3♣\n"
        + "Score: 185\n"
        + "            A♥\n"
        + "          2♥  3♥\n"
        + "        4♥  5♥  6♥\n"
        + "      7♥  8♥  9♥  10♥\n"
        + "    J♥  Q♥  K♥  A♠  2♠\n"
        + "  3♠  4♠  5♠  6♠  7♠  8♠\n"
        + "9♠  10♠ J♠  Q♠  .   A♣  2♣\n"
        + "Draw: 3♣\n"
        + "Score: 172\n"
        + "            A♥\n"
        + "          2♥  3♥\n"
        + "        4♥  5♥  6♥\n"
        + "      7♥  8♥  9♥  10♥\n"
        + "    J♥  Q♥  K♥  A♠  2♠\n"
        + "  3♠  4♠  5♠  6♠  7♠  8♠\n"
        + "9♠  10♠ J♠  .   .   .   2♣\n"
        + "Draw: 3♣\n"
        + "Score: 159\n"
        + "Invalid input. Retry\n"
        + "Game quit!\n"
        + "State of game when quit:\n"
        + "            A♥\n"
        + "          2♥  3♥\n"
        + "        4♥  5♥  6♥\n"
        + "      7♥  8♥  9♥  10♥\n"
        + "    J♥  Q♥  K♥  A♠  2♠\n"
        + "  3♠  4♠  5♠  6♠  7♠  8♠\n"
        + "9♠  10♠ J♠  .   .   .   2♣\n"
        + "Draw: 3♣\n"
        + "Score: 159", out.toString());
  }

  // test to see that that an illegal state exception will be thrown when there are not
  // enough inputs
  @Test(expected = IllegalStateException.class)
  public void readBad() {

    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("2");
    this.controller = new PyramidSolitaireTextualController(in, out);
    this.controller.playGame(this.model, this.model.getDeck(), false,
        3, 3);
  }

  // test to check when game is quit
  @Test
  public void testQuittingLowerCase() {

    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("q");
    this.controller = new PyramidSolitaireTextualController(in, out);
    this.controller.playGame(this.model, this.model.getDeck(), false, 7, 3);
    assertEquals("            A♥\n"
        + "          2♥  3♥\n"
        + "        4♥  5♥  6♥\n"
        + "      7♥  8♥  9♥  10♥\n"
        + "    J♥  Q♥  K♥  A♠  2♠\n"
        + "  3♠  4♠  5♠  6♠  7♠  8♠\n"
        + "9♠  10♠ J♠  Q♠  K♠  A♣  2♣\n"
        + "Draw: 3♣, 4♣, 5♣\n"
        + "Score: 185\n"
        + "Game quit!\n"
        + "State of game when quit:\n"
        + "            A♥\n"
        + "          2♥  3♥\n"
        + "        4♥  5♥  6♥\n"
        + "      7♥  8♥  9♥  10♥\n"
        + "    J♥  Q♥  K♥  A♠  2♠\n"
        + "  3♠  4♠  5♠  6♠  7♠  8♠\n"
        + "9♠  10♠ J♠  Q♠  K♠  A♣  2♣\n"
        + "Draw: 3♣, 4♣, 5♣\n"
        + "Score: 185", out.toString());
  }

  @Test
  public void testQuittingUpperCase() {

    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("Q");
    this.controller = new PyramidSolitaireTextualController(in, out);
    this.controller.playGame(this.model, this.model.getDeck(), false, 7, 3);
    assertEquals("            A♥\n"
        + "          2♥  3♥\n"
        + "        4♥  5♥  6♥\n"
        + "      7♥  8♥  9♥  10♥\n"
        + "    J♥  Q♥  K♥  A♠  2♠\n"
        + "  3♠  4♠  5♠  6♠  7♠  8♠\n"
        + "9♠  10♠ J♠  Q♠  K♠  A♣  2♣\n"
        + "Draw: 3♣, 4♣, 5♣\n"
        + "Score: 185\n"
        + "Game quit!\n"
        + "State of game when quit:\n"
        + "            A♥\n"
        + "          2♥  3♥\n"
        + "        4♥  5♥  6♥\n"
        + "      7♥  8♥  9♥  10♥\n"
        + "    J♥  Q♥  K♥  A♠  2♠\n"
        + "  3♠  4♠  5♠  6♠  7♠  8♠\n"
        + "9♠  10♠ J♠  Q♠  K♠  A♣  2♣\n"
        + "Draw: 3♣, 4♣, 5♣\n"
        + "Score: 185", out.toString());
  }

  // Test illegal argument case when constructor has null elements
  @Test(expected = IllegalArgumentException.class)
  public void testNullInput() {

    StringBuilder out = new StringBuilder();
    PyramidSolitaireController controller = new
        PyramidSolitaireTextualController(null, out);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullOutput() {

    Reader in = new StringReader("");
    PyramidSolitaireController controller = new
        PyramidSolitaireTextualController(in, null);
  }

  // test illegal argument exception for null model
  @Test(expected = IllegalArgumentException.class)
  public void testNullModel() {

    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("");
    PyramidSolitaireController controller = new
        PyramidSolitaireTextualController(in, out);
    controller.playGame(null, this.model.getDeck(), false, 7, 3);
  }
}
