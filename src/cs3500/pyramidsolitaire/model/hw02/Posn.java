package cs3500.pyramidsolitaire.model.hw02;

import java.util.Objects;

/**
 * Represents a position of a card.
 */
public class Posn {

  private int row;
  private int card;

  /**
   * Constructs a position vector.
   *
   * @param row the row the card is in
   * @param card the card in the row the card is indexed at
   */

  public Posn(int row, int card) {

    this.row = row;
    this.card = card;
  }

  /**
   * Accessor for the x coord.
   *
   * @return this.x
   */
  public int getRow() {

    return this.row;
  }

  /**
   * Accessor for the y coord.
   *
   * @return this.y
   */
  public int getCard() {

    return this.card;
  }

  /**
   * Modifier for the position coords.
   *
   * @param row new row coord
   * @param card new card coord
   */
  public void changePositions(int row, int card) {

    this.row = row;
    this.card = card;
  }

  @Override
  public boolean equals(Object o) {
    // Fast path for pointer equality:
    if (this == o) {
      return true;
    }

    // If o isn't the right class then it can't be equal:
    if (!(o instanceof Posn)) {
      return false;
    }

    // The successful instanceof check means our cast will succeed:
    Posn that = (Posn) o;

    return this.row == that.row
        && this.card == that.card;
  }

  @Override
  public int hashCode() {
    return Objects.hash(row, card);
  }
}
