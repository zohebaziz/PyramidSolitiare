import cs3500.pyramidsolitaire.controller.PyramidSolitaireController;
import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;
import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw04.MultiPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw04.RelaxedPyramidSolitaire;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Class to hold main method in.
 */
public class SolitaireMain {

  /**
   * Main method to run PyramidSolitaireText and make sure everything works well.
   */
  public static void main(String[] args) {

    PyramidSolitaireModel<Card> model = new BasicPyramidSolitaire();
    List<Card> validDeck = model.getDeck();
    PyramidSolitaireModel<Card> rModel = new RelaxedPyramidSolitaire();
    PyramidSolitaireModel<Card> mModel = new MultiPyramidSolitaire();
    PyramidSolitaireController controller =
        new PyramidSolitaireTextualController(new InputStreamReader(System.in), System.out);

    controller.playGame(mModel, mModel.getDeck(),
        false, 4, 3);
  }
}
