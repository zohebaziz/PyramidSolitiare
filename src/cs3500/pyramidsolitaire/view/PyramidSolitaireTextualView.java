package cs3500.pyramidsolitaire.view;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import java.io.IOException;

/**
 * Will portray the pyramid solitaire game as a test based view. Formatted in pyramid form as a
 * string.
 */
public class PyramidSolitaireTextualView implements PyramidSolitaireView {

  private final PyramidSolitaireModel<?> model;
  private Appendable ap;

  /**
   * Constructor for PyramidSolitaireTextualView.
   *
   * @param model current game state.
   * @param ap    Appendable object for use in render() method.
   */

  public PyramidSolitaireTextualView(PyramidSolitaireModel<?> model, Appendable ap) {

    this.model = model;
    this.ap = ap;
  }

  /**
   * Constructor that takes in the current game state.
   *
   * @param model current game state.
   */

  public PyramidSolitaireTextualView(PyramidSolitaireModel<?> model) {

    this.model = model;
  }

  @Override
  public String toString() {

    // Check if game start
    if (this.model.getNumRows() == -1) {

      return "";
    } else if (this.model.getScore() == 0) {

      return "You win!";
    } else if (this.model.isGameOver()) {

      return "Game over. Score: " + this.model.getScore();
    }

    // Render pyramid
    String acc = "";

    for (int i = 0; i < this.model.getNumRows(); i++) {

      for (int s = 0; s < (this.model.getNumRows() - (i + 1)) * 2; s++) {

        acc += " ";
      }

      for (int j = 0; j < this.model.getRowWidth(i); j++) {

        if (j != this.model.getRowWidth(i) - 1) {

          if (this.model.getCardAt(i, j) != null) {

            this.model.getCardAt(i, j);

            if (this.model.getCardAt(i, j).toString().length() == 3) {

              acc += this.model.getCardAt(i, j).toString() + " ";
            } else {
              acc += this.model.getCardAt(i, j).toString() + "  ";
            }
          } else {

            acc += ".   ";
          }
        } else {

          if (this.model.getCardAt(i, j) != null) {

            acc += this.model.getCardAt(i, j).toString();
          } else {

            acc += ".";
          }
        }
      }

      acc += "\n";
    }

    acc += "Draw:";

    for (int i = 0; i < this.model.getDrawCards().size(); i++) {

      if (this.model.getDrawCards().get(i) != null) {

        acc += " " + this.model.getDrawCards().get(i).toString();

        if (i + 1 != this.model.getNumDraw()) {

          acc += ",";
        }
      }
    }

    return acc;
  }

  @Override
  public void render() throws IOException {

    this.ap.append(this.toString());
  }
}