package cs3500.pyramidsolitaire.model.hw02;

import java.util.Objects;

/**
 * Represents a standard playing card.
 */
public class Card {

  private final Suite suite;
  private final Face face;
  private final Posn posn;
  private boolean exposed;

  /**
   * Constructs a card object.
   *
   * @param suite   the suite of the card.
   * @param face    the face value of the card.
   * @param exposed is the card exposed
   * @param posn    position of the card
   */

  Card(Suite suite, Face face, Posn posn, boolean exposed) {

    this.suite = suite;
    this.face = face;
    this.posn = posn;
    this.exposed = exposed;
  }

  public Card(Suite suite, Face face, boolean exposed) {

    this(suite, face, new Posn(-1, -1), exposed);
  }

  public Card(Suite suite, Face face) {

    this(suite, face, new Posn(-1, -1), false);
  }

  /**
   * Using the face value of the card return the integer value of the card from (1-13).
   *
   * @return the integer value of the card.
   * @throws RuntimeException when the face isn't part of the enumeration set.
   */
  public int getValue() {

    switch (this.face) {
      case Ace:
        return 1;
      case Two:
        return 2;
      case Three:
        return 3;
      case Four:
        return 4;
      case Five:
        return 5;
      case Six:
        return 6;
      case Seven:
        return 7;
      case Eight:
        return 8;
      case Nine:
        return 9;
      case Ten:
        return 10;
      case Jack:
        return 11;
      case Queen:
        return 12;
      case King:
        return 13;
      default:
        return 0;
    }
  }

  /**
   * Changes the position of the card.
   *
   * @param x the x coordinate of the new position of the card.
   * @param y the y coordinate of the new poisiton of the card.
   */
  public void changePosn(int x, int y) {

    this.posn.changePositions(x, y);
  }

  /**
   * Accessor for the posn field.
   *
   * @return the position of the card.
   */
  public Posn getPosn() {

    return this.posn;
  }

  /**
   * Accessor for the exposed field.
   *
   * @return true if the card is exposed.
   */

  public boolean isExposed() {

    return this.exposed;
  }

  /**
   * Expose this card.
   */
  public void exposeCard() {

    this.exposed = true;
  }

  @Override
  public String toString() {

    String acc = "";

    switch (this.face) {

      case Ace:
        acc += "A";
        break;
      case Two:
        acc += "2";
        break;
      case Three:
        acc += "3";
        break;
      case Four:
        acc += "4";
        break;
      case Five:
        acc += "5";
        break;
      case Six:
        acc += "6";
        break;
      case Seven:
        acc += "7";
        break;
      case Eight:
        acc += "8";
        break;
      case Nine:
        acc += "9";
        break;
      case Ten:
        acc += "10";
        break;
      case Jack:
        acc += "J";
        break;
      case Queen:
        acc += "Q";
        break;
      case King:
        acc += "K";
        break;
      default:
        acc += "";
        break;
    }

    switch (this.suite) {

      case Club:
        acc += '♣';
        break;
      case Heart:
        acc += '♥';
        break;
      case Diamond:
        acc += '♦';
        break;
      case Spade:
        acc += '♠';
        break;
      default:
        acc += "";
        break;
    }

    return acc;
  }


  @Override
  public boolean equals(Object o) {
    // Fast path for pointer equality:
    if (this == o) {
      return true;
    }

    // If o isn't the right class then it can't be equal:
    if (!(o instanceof Card)) {
      return false;
    }

    // The successful instanceof check means our cast will succeed:
    Card that = (Card) o;

    return this.suite == that.suite
        && this.face == that.face;
  }

  @Override
  public int hashCode() {
    return Objects.hash(suite, face);
  }
}
