package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.Posn;
import java.util.ArrayList;
import java.util.List;

/**
 * Model implementation of PyramidSolitaire that has a more relaxed ruleset. A card can still be
 * removed if it's not exposed as long as the card it is being removed with is the card that is
 * covering it.
 */
public final class RelaxedPyramidSolitaire extends BasicPyramidSolitaire {

  /**
   * Specific constructor to be used for testing purposes.
   *
   * @param deck     the deck the game is to be played with.
   * @param drawPile the draw stack that is used in the game.
   * @param numRows  the number of rows the pyramid is to have.
   * @param numDraw  the number of visible draw cards at a time.
   * @param started  whether the game has been started yet.
   */
  public RelaxedPyramidSolitaire(List<Card> deck, List<Card> drawPile,
      int numRows, int numDraw, boolean started) {

    super(deck, drawPile, numRows, numDraw, started);
  }

  /**
   * Empty default constructor to create a quick game.
   */

  public RelaxedPyramidSolitaire() {

    this.deck = new ArrayList<>();
    this.drawPile = new ArrayList<>();
    this.numRows = 1;
    this.numDraw = 10;
    this.started = false;
  }

  // Override to make sure relaxed conditions are met
  @Override
  public void remove(int row1, int card1, int row2, int card2)
      throws IllegalArgumentException, IllegalStateException {

    if (!this.started) {

      throw new IllegalStateException("Game not started yet");
    }

    Card gameCard1 = this.getCardAt(row1, card1);
    Card gameCard2 = this.getCardAt(row2, card2);

    if (gameCard1 == null || gameCard2 == null) {

      throw new IllegalArgumentException("Chosen card is not in the game.");
    }

    if (!gameCard1.isExposed() && !gameCard2.isExposed()) {

      throw new IllegalArgumentException("Neither of the given cards are not exposed.");
    } else if ((gameCard1.isExposed() && !gameCard2.isExposed()) ||
        (!gameCard1.isExposed() && gameCard2.isExposed())) {

      if (this.isCovering(gameCard1, gameCard2)) {

        if (gameCard1.getValue() + gameCard2.getValue() == 13) {

          int i1 = this.deck.indexOf(gameCard1);
          int i2 = this.deck.indexOf(gameCard2);
          Posn coord1 = new Posn(row1, card1);

          // Exposing cards in the normal part of the pyramid
          this.exposePyramid(coord1);

          this.deck.set(i1, null);
          this.deck.set(i2, null);
        } else {

          throw new IllegalArgumentException("Invalid move: Given cards do not add up to 13");
        }
      } else if (this.isCovering(gameCard2, gameCard1)) {

        if (gameCard1.getValue() + gameCard2.getValue() == 13) {

          int i1 = this.deck.indexOf(gameCard1);
          int i2 = this.deck.indexOf(gameCard2);
          Posn coord2 = new Posn(row2, card2);

          // Exposing cards in the normal part of the pyramid
          this.exposePyramid(coord2);

          this.deck.set(i1, null);
          this.deck.set(i2, null);
        } else {

          throw new IllegalArgumentException("Invalid move: Given cards do not add up to 13");
        }
      } else {

        throw new IllegalArgumentException("Given remove does not meet relaxed game rules.");
      }
    } else if (gameCard1.isExposed() && gameCard2.isExposed()) {

      if (gameCard1.getValue() + gameCard2.getValue() == 13) {

        int i1 = this.deck.indexOf(gameCard1);
        int i2 = this.deck.indexOf(gameCard2);
        Posn coord1 = new Posn(row1, card1);
        Posn coord2 = new Posn(row2, card2);

        // Exposing cards in the normal part of the pyramid
        this.exposePyramid(coord1);
        this.deck.set(i1, null);
        this.exposePyramid(coord2);
        this.deck.set(i2, null);
      }
    } else {

      throw new IllegalArgumentException("Invalid move: Given cards do not add up to 13");
    }
  }

  /**
   * Helper method to check if a card is "Covering" another card and is the only reason the behind
   * card sis not exposed.
   *
   * @param c1 First given card, the "in front" card."
   * @param c2 Second given card, the "behind card."
   * @return true if first card is "Covering" the second card in a pyramid.
   */
  private boolean isCovering(Card c1, Card c2) {

    Posn p1 = c1.getPosn();
    Posn p2 = c2.getPosn();

    // Check case for when c2 isn't adjacent to c1
    if (p2.getRow() != p1.getRow() - 1) {

      return false;
    } else if (p2.getCard() == p1.getCard() - 1 || p2.getCard() == p1.getCard()) {

      if (p1.getCard() > 0 && p1.getCard() < this.getRowWidth(p1.getRow()) - 1
          && p1.getRow() != 0) {

        if (this.getCardAt(p1.getRow(), p1.getCard() + 1) == null &&
            p2.getCard() == p1.getCard()) {

          return true;
        } else if (this.getCardAt(p1.getRow(), p1.getCard() - 1) == null
            && p2.getCard() == p1.getCard() - 1) {

          return true;
        }


      } else if (p1.getCard() == 0 && p1.getRow() != 0) {

        if (this.getCardAt(p1.getRow(), p1.getCard() + 1) == null &&
            p2.getCard() == p1.getCard()) {

          return true;
        }

      } else if (p1.getCard() == this.getRowWidth(p1.getRow()) - 1 && p1.getRow() != 0) {

        if (this.getCardAt(p1.getRow(), p1.getCard() - 1) == null &&
            p2.getCard() == p1.getCard() - 1) {

          return true;
        }
      }
    }
    return false;
  }

  @Override
  public boolean isGameOver() throws IllegalStateException {

    if (!this.started) {

      throw new IllegalStateException("Game has not started yet");
    } else if (this.getScore() == 0) {

      return true;
    } else if (this.drawPile.size() > this.getNumDraw()) {

      return false;
    } else if (this.drawPile.size() <= this.getNumDraw()) {

      // Create list of just the exposed cards
      List<Card> exposedCards = new ArrayList<>();
      List<Card> unExposedCards = new ArrayList<>();
      List<Card> deckNoDraws = new ArrayList<>(this.deck);
      deckNoDraws.removeAll(this.drawPile);

      for (Card c : this.deck) {

        if (c != null && c.isExposed()) {

          exposedCards.add(c);
        }
      }

      for (Card c : deckNoDraws) {

        if (c != null && !c.isExposed()) {

          unExposedCards.add(c);
        }
      }

      // Check if theres any possible relaxed combinations
      for (Card ce : exposedCards) {

        for (Card cu : unExposedCards) {

          if (this.isCovering(ce, cu)) {

            if (ce.getValue() + cu.getValue() == 13) {

              return false;
            }
          }
        }
      }
      // Check for any possible combinations
      for (int i = 0; i < exposedCards.size(); i++) {

        for (int j = i + 1; j < exposedCards.size(); j++) {

          if (exposedCards.get(i).getValue() == 13 || exposedCards.get(j).getValue() == 13) {

            return false;
          } else if (exposedCards.get(i).getValue() + exposedCards.get(j).getValue() == 13) {

            return false;
          }
        }
      }

      // Check for possible combinations with drawCards
      for (Card c : this.getDrawCards()) {

        for (Card e : exposedCards) {

          if (c != null && c.getValue() + e.getValue() == 13) {

            return false;
          }
        }
      }
    }

    return true;
  }
}
