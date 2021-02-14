package cs3500.pyramidsolitaire.controller;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;
import cs3500.pyramidsolitaire.view.PyramidSolitaireView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Textual representation of a PyramidSolitaireController. Offers prompts and interacts with both
 * the pyramid model and the pyramid view.
 */

public class PyramidSolitaireTextualController implements PyramidSolitaireController {

  private final Appendable ap;
  private final Scanner sc;
  private boolean wasGameQuit;

  /**
   * Constructs the controller.
   *
   * @param rd to read inputs.
   * @param ap to print outputs.
   * @throws IllegalArgumentException if either parameters are null values.
   */

  public PyramidSolitaireTextualController(Readable rd, Appendable ap)
      throws IllegalArgumentException {

    if (rd == null || ap == null) {

      throw new IllegalArgumentException("Inputs cannot be null");
    }

    this.ap = ap;
    this.sc = new Scanner(rd);
    wasGameQuit = false;
  }

  @Override
  public <K> void playGame(PyramidSolitaireModel<K> model, List<K> deck, boolean shuffle,
      int numRows, int numDraw) {

    if (model == null) {

      throw new IllegalArgumentException("Model cannot be null");
    }

    else if (deck == null) {

      throw new IllegalArgumentException("Deck cannot be null");
    }

    PyramidSolitaireView view = new PyramidSolitaireTextualView(model, this.ap);

    // instantiate game phase
    try {

      model.startGame(deck, shuffle, numRows, numDraw);
    } catch (IllegalArgumentException e) {

      throw new IllegalStateException("Game cannot be started.");
    }


    while (!model.isGameOver()) {

      // play game in here
      try {

        view.render();
        this.ap.append("\n"); // new line between transmissions
        this.ap.append(String.format("Score: %d", model.getScore()));
        this.ap.append("\n");

        ArrayList<Integer> inputs = this.findInputs();

        if (this.wasGameQuit) {

          // quit game
          break;
        } else if (inputs.size() == 1) {

          try {

            model.discardDraw(inputs.get(0) - 1);
          } catch (IllegalArgumentException e) {

            this.ap.append("Invalid move. Play again. Draw index was invalid.\n");
          }
        } else if (inputs.size() == 2) {

          try {

            model.remove(inputs.get(0) - 1, inputs.get(1) - 1);
          } catch (IllegalArgumentException e) {

            this.ap.append("Invalid move. Play again. Given card is invalid.\n");
          }
        } else if (inputs.size() == 4) {

          try {

            model.remove(inputs.get(0) - 1, inputs.get(1) - 1,
                inputs.get(2) - 1, inputs.get(3) - 1);
          } catch (IllegalArgumentException e) {

            this.ap.append("Invalid move. Play again. One or more of given cards is invalid.\n");
          }
        } else if (inputs.size() == 3) {

          try {

            model.removeUsingDraw(inputs.get(0) - 1, inputs.get(1) - 1,
                inputs.get(2) - 1);
          } catch (IllegalArgumentException e) {

            this.ap.append("Invalid move. Play again. Draw index or given card was invalid.\n");
          }
        }
      } catch (IOException e) { // if appendable was unable to append

        throw new IllegalStateException("Appendable was unable to transmit game state.");
      }
    }

    // Different endings depending on if game was quit or played out.

    if (wasGameQuit) {

      try {

        this.ap.append("Game quit!");
        this.ap.append("\n");
        this.ap.append("State of game when quit:");
        this.ap.append("\n");
        view.render();
        this.ap.append("\n");
        this.ap.append(String.format("Score: %d", model.getScore()));
      } catch (IOException e) {

        throw new IllegalStateException("Appendable was unable to transmit game state.");
      }
    } else {

      try {

        // Transmit final game state
        view.render();
      } catch (IOException e) {

        throw new IllegalStateException("Appendable was unable to transmit game state.");
      }
    }
  }

  // possibly create a helper that given a string returns an array of indexes that are valid

  /**
   * Gets inputs from user and checks if valid.
   *
   * @return an arraylist of inputs given by user.
   */
  private ArrayList<Integer> findInputs() {

    boolean checkingInput = true;
    ArrayList<Integer> inputs = new ArrayList<>();

    while (checkingInput) {

      String action;

      try {

        action = this.sc.next();

        if (action.equalsIgnoreCase("q")) {

          this.wasGameQuit = true;
          checkingInput = false;
        } else if (action.equals("dd")) {

          try {

            inputs.add(this.sc.nextInt());
          } catch (NoSuchElementException e) {

            try {

              this.ap.append("Bad input. Try again\n");
            } catch (IOException ex) {

              throw new IllegalStateException("Appendable was unable to transmit game state.");
            }
          }
          checkingInput = false;
        } else if (action.equals("rm1")) {

          try {

            inputs.add(this.sc.nextInt());
            inputs.add(this.sc.nextInt());
          } catch (NoSuchElementException e) {

            try {

              this.ap.append("Bad input. Try again\n");
            } catch (IOException ex) {

              throw new IllegalStateException("Appendable was unable to transmit game state.");
            }
          }
          checkingInput = false;
        } else if (action.equals("rm2")) {

          try {

            inputs.add(this.sc.nextInt());
            inputs.add(this.sc.nextInt());
            inputs.add(this.sc.nextInt());
            inputs.add(this.sc.nextInt());
          } catch (NoSuchElementException e) {

            try {

              this.ap.append("Bad input. Try again\n");
            } catch (IOException ex) {

              throw new IllegalStateException("Appendable was unable to transmit game state.");
            }
          }
          checkingInput = false;
        } else if (action.equals("rmwd")) {

          try {

            inputs.add(this.sc.nextInt());
            inputs.add(this.sc.nextInt());
            inputs.add(this.sc.nextInt());
          } catch (NoSuchElementException e) {

            try {

              this.ap.append("Bad input. Try again\n");
            } catch (IOException ex) {

              throw new IllegalStateException("Appendable was unable to transmit game state.");
            }
          }
          checkingInput = false;
        } else {

          try {

            this.ap.append("Invalid input. Retry");
            this.ap.append("\n");
          } catch (IOException e) {

            throw new IllegalStateException("Appendable was unable to transmit game state.");
          }
        }
      } catch (NoSuchElementException e) {

        throw new IllegalStateException("Readable was unable to communicate with user input");
      }
    }

    return inputs;
  }
}
