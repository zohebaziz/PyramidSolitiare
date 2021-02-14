package cs3500.pyramidsolitaire.model.hw02;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A game of pyramid solitaire using a standard deck of cards.
 */
public class BasicPyramidSolitaire implements PyramidSolitaireModel<Card> {

  protected List<Card> deck;
  protected List<Card> drawPile;
  protected int numRows;
  protected int numDraw;
  protected boolean started;

  /**
   * Creates a Pyramid solitaire with my custom deck of cards.
   *
   * @param deck    Deck of cards used in the game
   * @param numRows Number of rows of cards to use in game
   * @param numDraw Number of cards in the draw pile
   * @param started Whether or not the game has started yet
   */

  // Specific constructor to use for testing purposes
  public BasicPyramidSolitaire(List<Card> deck, List<Card> drawPile,
      int numRows, int numDraw, boolean started) {

    this.deck = deck;
    this.drawPile = drawPile;
    this.numDraw = numDraw;
    this.numRows = numRows;
    this.started = started;
  }

  /**
   * Empty constructor to start a quick game.
   */
  public BasicPyramidSolitaire() {

    this.deck = new ArrayList<>();
    this.drawPile = new ArrayList<>();
    this.numRows = 1;
    this.numDraw = 10;
    this.started = false;
  }

  @Override
  public List<Card> getDeck() {

    List<Card> returnDeck = new ArrayList<>();

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
    } else if (deck.size() != 52) {

      throw new IllegalArgumentException("Deck must have 52 cards");
    } else if (this.checkDupes(deck)) { // check for validity of deck

      throw new IllegalArgumentException("Deck is invalid");
    } else if (numRows > 9) {

      throw new IllegalArgumentException("Too many rows");
    } else if (numDraw > 52 - ((numRows * (numRows + 1)) / 2)) {

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

    // Initialize positions of cards and set bottom row to exposed

    int counter = 0;
    for (int i = 0; i < this.numRows; i++) {

      for (int j = 0; j <= i; j++) {

        this.deck.get(counter).changePosn(i, j);
        if (i + 1 == numRows) {

          this.deck.get(counter).exposeCard();
        }
        counter += 1;
      }
    }

    int deckSize = (this.numRows * (this.numRows + 1)) / 2;

    // Create draw pile
    this.drawPile.subList(0, deckSize).clear();
  }

  /**
   * A method to find duplicate cards in a deck.
   *
   * @param deck the given deck to check for duplicates in.
   * @return true if there are duplicates, otherwise false.
   */
  protected boolean checkDupes(List<Card> deck) {

    int end = deck.size();
    Card card1;
    Card card2;

    for (int i = 0; i < end; i++) {

      card1 = deck.get(i);

      for (int j = i + 1; j < end; j++) {

        card2 = deck.get(j);

        if (card2.equals(card1)) {

          return true;
        }
      }
    }

    return false;
  }

  /**
   * Iterates through card deck and exposes cards correctly after a remove move.
   *
   * @param coord the coordinate of card just to be removed
   */

  protected void exposePyramid(Posn coord) {

    if ((coord.getCard() > 0) && (coord.getCard() < (this.getRowWidth(coord.getRow()) - 1))
        && (coord.getRow() != 0)) {
      if (this.getCardAt(coord.getRow(), coord.getCard() + 1) == null) {

        this.getCardAt(coord.getRow() - 1, coord.getCard()).exposeCard();
      }
      if (this.getCardAt(coord.getRow(), coord.getCard() - 1) == null) {

        this.getCardAt(coord.getRow() - 1, coord.getCard() - 1).exposeCard();
      }

    } else if ((coord.getCard() == 0) && (coord.getRow() != 0)) {

      if (this.getCardAt(coord.getRow(), coord.getCard() + 1) == null) {

        this.getCardAt(coord.getRow() - 1, coord.getCard()).exposeCard();
      }
    } else if ((coord.getCard() == (this.getRowWidth(coord.getRow()) - 1))
        && (coord.getRow() != 0)) {

      if (this.getCardAt(coord.getRow(), coord.getCard() - 1) == null) {

        this.getCardAt(coord.getRow() - 1, coord.getCard() - 1).exposeCard();
      }
    }
  }

  @Override
  public void remove(int row1, int card1, int row2, int card2)
      throws IllegalArgumentException, IllegalStateException {

    if (!this.started) {

      throw new IllegalStateException("Game not started yet");
    }

    Card gameCard1 = this.getCardAt(row1, card1);
    Card gameCard2 = this.getCardAt(row2, card2);

    if (gameCard1 == null || gameCard2 == null) {

      throw new IllegalArgumentException("Chosen card is not in the game");
    }

    if (!gameCard1.isExposed()) {

      throw new IllegalArgumentException("One of the given cards is not exposed");
    } else if (!gameCard2.isExposed()) {

      throw new IllegalArgumentException("One of the given cards is not exposed");
    }

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
    } else {

      throw new IllegalArgumentException("Invalid move: Given cards do not add up to 13");
    }
  }

  @Override
  public void remove(int row, int card) throws IllegalArgumentException, IllegalStateException {

    if (!this.started) {

      throw new IllegalStateException("Game not started yet");
    }

    Card gameCard = this.getCardAt(row, card);
    Posn coord = new Posn(row, card);

    if (gameCard == null) {

      throw new IllegalArgumentException("Chosen card is not in the game");
    }

    if (!gameCard.isExposed()) {

      throw new IllegalArgumentException("Given card is not exposed");
    } else if (gameCard.getValue() == 13) {

      int i = this.deck.indexOf(gameCard);
      this.exposePyramid(coord);
      this.deck.set(i, null);
    } else {

      throw new IllegalArgumentException("Invalid move: given card does not have a value of 13");
    }
  }

  @Override
  public void removeUsingDraw(int drawIndex, int row, int card)
      throws IllegalArgumentException, IllegalStateException {

    if (!this.started) {

      throw new IllegalStateException("Game has not started yet");
    }

    Card card1;
    Card card2 = this.getCardAt(row, card);

    if (card2 == null) {

      throw new IllegalArgumentException("Chosen card is not in the game");
    }

    Posn coord = new Posn(row, card);

    if (!card2.isExposed()) {

      throw new IllegalArgumentException("Given card is not exposed");
    } else if (drawIndex < 0 || drawIndex >= this.getNumDraw()) {

      throw new IllegalArgumentException("Draw index is out of bounds");
    }

    card1 = this.drawPile.get(drawIndex);

    if (card1.getValue() + card2.getValue() == 13) {

      int i = this.deck.indexOf(card2);

      this.exposePyramid(coord);

      this.discardDraw(drawIndex);
      this.deck.set(i, null);
    } else {

      throw new IllegalArgumentException("Invalid Move: 2 Cards do not add up to 13");
    }
  }

  @Override
  public void discardDraw(int drawIndex) throws IllegalArgumentException, IllegalStateException {

    if (!this.started) {

      throw new IllegalStateException("Game has not started yet");
    }

    if (drawIndex < 0 || drawIndex >= this.getNumDraw()) {

      throw new IllegalArgumentException("Given index is out of bounds");
    }

    if (this.drawPile.size() <= this.getNumDraw()) {

      this.drawPile.set(drawIndex, null);
    } else {

      this.drawPile.set(drawIndex, this.drawPile.get(this.getNumDraw()));
      this.drawPile.remove(this.getNumDraw());
    }
  }

  @Override
  public int getNumRows() {
    if (this.started) {

      return this.numRows;
    }

    return -1;
  }

  @Override
  public int getNumDraw() {
    if (this.started) {

      return this.numDraw;
    }

    return -1;
  }

  @Override
  public int getRowWidth(int row) throws IllegalArgumentException, IllegalStateException {

    if (!this.started) {

      throw new IllegalStateException("Game hasn't started yet");
    } else if (row >= getNumRows() || row < 0) {

      throw new IllegalArgumentException("Invalid row");
    }
    return row + 1;
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
      for (Card c : this.deck) {

        if (c != null && c.isExposed()) {

          exposedCards.add(c);
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

  @Override
  public int getScore() throws IllegalStateException {

    if (!started) {

      throw new IllegalStateException("Game has not started yet");
    }

    int acc = 0;
    int deckSize = (this.numRows * (this.numRows + 1)) / 2;

    for (int i = 0; i < deckSize; i++) {

      try {

        acc += this.deck.get(i).getValue();
      } catch (NullPointerException e) {

        acc += 0;
      }
    }

    return acc;
  }

  @Override
  public Card getCardAt(int row, int card) throws IllegalArgumentException, IllegalStateException {

    if (!this.started) {

      throw new IllegalStateException("Game has not started yet");
    }

    Posn position = new Posn(row, card);

    if (row < 0 || row > numRows - 1 || card < 0 || card > getRowWidth(position.getRow()) - 1) {

      throw new IllegalArgumentException("Given card is not in the game");
    }

    for (Card c : this.deck) {

      if (c != null && c.getPosn().equals(position)) {

        return c;
      }
    }

    return null;
  }

  @Override
  public List<Card> getDrawCards() throws IllegalStateException {

    if (!this.started) {

      throw new IllegalStateException("Game has not started yet");
    }

    List<Card> toReturn = new ArrayList<>(this.drawPile);

    return toReturn.subList(0, getNumDraw());
  }
}