package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.Face;
import cs3500.pyramidsolitaire.model.hw02.Posn;
import cs3500.pyramidsolitaire.model.hw02.Suite;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Model implementation of PyramidSolitaire that uses 3 Pyramids that overlap at some points
 * depending on how big it is.
 */
public final class MultiPyramidSolitaire extends BasicPyramidSolitaire {

  /**
   * Specific constructor to use for testing purposes.
   *
   * @param deck     Given deck that the game is to be played with.
   * @param drawPile Given draw pile to take cards from.
   * @param numRows  Number of rows this pyramid should have.
   * @param numDraw  Number of draw cards to be visible at a time.
   * @param started  True if the game has started, otherwise false.
   */
  public MultiPyramidSolitaire(List<Card> deck, List<Card> drawPile,
      int numRows, int numDraw, boolean started) {

    super(deck, drawPile, numRows, numDraw, started);
  }

  /**
   * Empty constructor to start a quick game.
   */
  public MultiPyramidSolitaire() {

    this.deck = new ArrayList<>();
    this.drawPile = new ArrayList<>();
    this.numRows = 1;
    this.numDraw = 10;
    this.started = false;
  }

  // Creates two iterations of the standard deck
  @Override
  public List<Card> getDeck() {

    List<Card> returnDeck = new ArrayList<>();

    for (Suite suite : Suite.values()) {

      for (Face face : Face.values()) {

        returnDeck.add(new Card(suite, face));
      }
    }

    for (Suite suite : Suite.values()) {

      for (Face face : Face.values()) {

        returnDeck.add(new Card(suite, face));
      }
    }

    return returnDeck;
  }

  @Override
  public void startGame(List<Card> deck, boolean shuffle, int numRows, int numDraw)
      throws IllegalArgumentException {

    if (deck == null) {

      throw new IllegalArgumentException("Deck is null");
    } else if (numRows < 1) {

      throw new IllegalArgumentException("numRows must be positive");
    } else if (numDraw < 0) {

      throw new IllegalArgumentException("numDraw must be positive");
    } else if (deck.size() != 104) {

      throw new IllegalArgumentException("Deck must have 104 cards");
    } else if (this.checkDupes(deck)) { // check for validity of deck

      throw new IllegalArgumentException("Deck is invalid");
    } else if (numRows > 8) {

      throw new IllegalArgumentException("Too many rows");
    } else if (numDraw > this.getMaxDrawCards(numRows)) {

      throw new IllegalArgumentException("Too many draw cards to show.");
    } else if (shuffle) {

      this.deck.addAll(deck);
      Collections.shuffle(this.deck);
      this.drawPile.addAll(this.deck);
      this.numRows = numRows;
      this.numDraw = numDraw;
      this.started = true;
    } else {

      this.deck.addAll(deck);
      this.drawPile.addAll(this.deck);
      this.numDraw = numDraw;
      this.numRows = numRows;
      this.started = true;
    }

    // Render the triple pyramid model and create draw pile
    this.createMultiPyramid();

  }

  /**
   * Used to find the maximum number of draw cards by finding the total amount of cards in pyramid
   * and subtracting that from 104.
   *
   * @param rows the number of rows in the pyramid.
   * @return The maximum number of draw cards possible in this game instance.
   */
  private int getMaxDrawCards(int rows) {

    int acc = 1;

    switch (rows) {

      case 1:
        acc += 103;
        break;
      case 2:
        acc += 97;
        break;
      case 3:
        acc += 92;
        break;
      case 4:
        acc += 80;
        break;
      case 5:
        acc += 71;
        break;
      case 6:
        acc += 53;
        break;
      case 7:
        acc += 40;
        break;
      case 8:
        acc += 16;
        break;
      default: // no default needed because all possible cases are covered.
        break;
    }

    return acc;
  }

  @Override
  protected boolean checkDupes(List<Card> deck) {

    // get a valid deck
    List<Card> fiftyTwo = super.getDeck();
    List<Card> checkDeck = new ArrayList<>(deck);
    // remove valid deck from given deck
    for (Card c : fiftyTwo) {

      checkDeck.remove(c);
    }

    // remaining deck should be valid still
    return super.checkDupes(checkDeck);
  }

  /**
   * Renders a mutlipyramid with overlapping elements depending on how many rows are given for the
   * pyramid.
   */
  private void createMultiPyramid() {

    if (this.numRows == 1) {

      this.deck.get(0).changePosn(0, 0);
      this.drawPile.subList(0, 1).clear();
    } else if (this.numRows == 2 || this.numRows == 3) {

      int counter = 0;
      for (int i = 0; i < this.numRows; i++) {

        for (int j = 0; j < i + 3; j++) {

          this.deck.get(counter).changePosn(i, j);
          if (i + 1 == this.numRows) {

            this.deck.get(counter).exposeCard();
          }
          counter++;
        }
      }
      this.drawPile.subList(0, counter).clear();
    } else if (this.numRows == 4 || this.numRows == 5) {

      int counter = 0;
      for (int i = 0; i < this.numRows; i++) {

        for (int j = 0; j < i + 5; j++) {

          if (i == 0 && j % 2 == 1) {

            continue;
          }

          this.deck.get(counter).changePosn(i, j);
          if (i + 1 == this.numRows) {

            this.deck.get(counter).exposeCard();
          }
          counter++;
        }
      }
      this.drawPile.subList(0, counter).clear();
    } else if (this.numRows == 6 || this.numRows == 7) {

      int counter = 0;
      for (int i = 0; i < this.numRows; i++) {

        for (int j = 0; j < i + 7; j++) {

          if (i == 0 && (j == 1 || j == 2 || j == 4 || j == 5)) {

            continue;
          } else if (i == 1 && (j == 2 || j == 5)) {

            continue;
          }

          this.deck.get(counter).changePosn(i, j);
          if (i + 1 == this.numRows) {

            this.deck.get(counter).exposeCard();
          }
          counter++;
        }
      }
      this.drawPile.subList(0, counter).clear();
    } else {

      int counter = 0;
      for (int i = 0; i < this.numRows; i++) {

        for (int j = 0; j < i + 9; j++) {

          if (i == 0 && (j == 1 || j == 2 || j == 3 || j == 5 || j == 6 || j == 7)) {

            continue;
          } else if (i == 1 && (j == 2 || j == 3 || j == 6 || j == 7)) {

            continue;
          } else if (i == 2 && (j == 3 || j == 7)) {

            continue;
          }

          this.deck.get(counter).changePosn(i, j);
          if (i + 1 == this.numRows) {

            this.deck.get(counter).exposeCard();
          }
          counter++;
        }
      }
      this.drawPile.subList(0, counter).clear();
    }
  }

  @Override
  protected void exposePyramid(Posn coord) {

    if ((coord.getCard() > 0) && (coord.getCard() < (this.getRowWidth(coord.getRow()) - 1))
        && (coord.getRow() != 0)) {
      if (this.getCardAt(coord.getRow(), coord.getCard() + 1) == null) {

        try {

          this.getCardAt(coord.getRow() - 1, coord.getCard()).exposeCard();
        } catch (NullPointerException e) {

          // For when there is a placeholder due to the multipyramid layout
        }
      }
      if (this.getCardAt(coord.getRow(), coord.getCard() - 1) == null) {

        try {

          this.getCardAt(coord.getRow() - 1, coord.getCard() - 1).exposeCard();
        } catch (NullPointerException e) {
          // For when there is a placeholder due to the multipyramid layout
        }
      }
    } else if ((coord.getCard() == 0) && (coord.getRow() != 0)) {

      if (this.getCardAt(coord.getRow(), coord.getCard() + 1) == null) {

        try {

          this.getCardAt(coord.getRow() - 1, coord.getCard()).exposeCard();
        } catch (NullPointerException e) {
          // For when there is a placeholder due to the multipyramid layout
        }
      }
    } else if ((coord.getCard() == (this.getRowWidth(coord.getRow()) - 1)) &&
        (coord.getRow() != 0)) {

      if (this.getCardAt(coord.getRow(), coord.getCard() - 1) == null) {

        try {

          this.getCardAt(coord.getRow() - 1, coord.getCard() - 1).exposeCard();
        } catch (NullPointerException e) {
          // For when there is a placeholder due to the multipyramid layout
        }
      }
    }
  }

  @Override
  public int getScore() throws IllegalStateException {

    if (!started) {

      throw new IllegalStateException("Game has not started yet");
    }

    int acc = 0;
    ArrayList<Card> scoringDeck = new ArrayList<>();
    for (Card c : this.deck) {

      if (c != null && !c.getPosn().equals(new Posn(-1, -1))) {

        scoringDeck.add(c);
      }
    }

    for (Card c : scoringDeck) {

      acc += c.getValue();
    }

    return acc;
  }

  @Override
  public int getRowWidth(int row) throws IllegalArgumentException, IllegalStateException {

    if (!this.started) {

      throw new IllegalStateException("Game hasn't started yet");
    } else if (row >= this.getNumRows() || row < 0) {

      throw new IllegalArgumentException("Invalid row");
    } else if (this.getNumRows() == 1) {

      return 1;

    } else if (row < this.getNumRows() / 2) {

      if (this.getNumRows() % 2 == 0) {

        return this.getNumRows() + (row + 1);
      }
      return this.getNumRows() + row;
    } else {

      return 3 * (this.getNumRows() / 2) + (row - (this.getNumRows() / 2) + 1);
    }
  }
}