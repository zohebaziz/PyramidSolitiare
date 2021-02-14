import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw04.MultiPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator.GameType;
import cs3500.pyramidsolitaire.model.hw04.RelaxedPyramidSolitaire;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Tester class for the factory class, PyramidSolitaireCreator.
 */
public class PyramidSolitaireCreatorTests {

  PyramidSolitaireCreator creator = new PyramidSolitaireCreator();

  @Test
  public void testBasic() {

    assertEquals(true, creator.create(GameType.BASIC) instanceof BasicPyramidSolitaire);
    assertEquals(true,
        creator.create(GameType.MULTIPYRAMID) instanceof MultiPyramidSolitaire);
    assertEquals(true,
        creator.create(GameType.RELAXED) instanceof RelaxedPyramidSolitaire);
  }
}
