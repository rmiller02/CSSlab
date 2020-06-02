import java.util.Scanner;

//implementation of blackjack player
public class Player {
    // the player's name
    private String name;

   // The player's Inital betting amount
   private double initalAmount = 200.00; 

   // The player's amount they bet
   private double playersBet;
   
    // the cards in the players hand
    private Card[] hand = new Card[10];// we assume that you will never get more than 10 cards

    // the number of cards in the player's current hand
    private int numCards;

    // Player constructor
    // takes param of aName, the name of the player
    public Player(String aName) {
        this.name = aName;

        // set a player's hand to empty
        this.emptyHand();// any cards from the previous game, we want to zero out
    }

    // Reset the player's hand to have no cards
    public void emptyHand() {
        for (int c = 0; c < 10; c++) {
            this.hand[c] = null;
        }
        this.numCards = 0; // number of cards is now set to zero

    }

    //Get players bet amount
    public double getInitalBet() {
        return this.initalAmount;
    }

    public double askPlayersBet() {
        System.out.println("Your current amount is " + this.initalAmount);
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a bet amount in increments of 5. (Ex. 5, 10, 15, etc.)");
        double amount;
        do {
            amount = sc.nextDouble();
            if(amount <= this.initalAmount) {
                if(amount % 5 != 0) {
                    System.out.println("Error, please enter an increment of 5");
                } else {
                    this.initalAmount -= amount;
                    System.out.println("Your bet amount is " + amount);
                    break;
                }
            } else {
                System.out.println("Sorry, you do not have enough funds, try a smaller amount.");
            }
        } while (1 != 0);
        
        this.playersBet = amount;

        return amount; 
    }


    public double getPlayersBet() {
        return this.playersBet;
    }

    // Add a card to the player's hand.
    // takes the param of aCard, the card to add to the hand
    // then returns whether the sum of the new hand is below or equal to 21
    public boolean addCard(Card aCard) {
        // print error if we already have max number of cards
        if (this.numCards == 10) {
            System.err.printf("%s's hand already has 10 cards; " + "cannot add another\n", this.name);
            System.exit(1);
        }
        // add a new card in next slot and increment number of cards counter
        this.hand[this.numCards] = aCard;
        this.numCards++;

        return (this.getHandSum() <= 21);// check if current hand is <=21 and return that value
    }

    // Get sume of the cards in the player's hand
    // return the sum
    public int getHandSum() {
        int handSum = 0;
        int cardNum;
        int numAces = 0;
        // calc each card's contribution to the hand sum
        for (int c = 0; c < this.numCards; c++) {
            // get the number of cards
            cardNum = this.hand[c].getMyNumber();

            if (cardNum == 1) {// 1=Ace
                numAces++;
                handSum += 11;
            } else if (cardNum > 10) {// face card
                handSum += 10;
            } else {
                handSum += cardNum;
            }
        }
        // If we have aces and our sum is > 21, set some/all of them to value 1 insteady
        while (handSum > 21 && numAces > 0) {// if hand sum is greater than 21 and number of aces is greater than 0,
                                             // we will subtract 10 from handsum or decrement the Aces
            handSum -= 10;
            numAces--;
        }
        return handSum;
    }

    // print out cards in player's hand
    // take param of showFirstCard which says if we want to print the card of the
    // first hand; useful when we may not want to see the cards of the dealer
    public void printHand(boolean showFirstCard) {
        System.out.printf("%s's cards: \n", this.name);
        for (int c = 0; c < this.numCards; c++) {
            if (c == 0 && !showFirstCard) {// if we are on the first card and don't want to print it
                System.out.println(" [hidden]");
            } else {
                System.out.printf(" %s\n", this.hand[c].toString());
            }
        }
    }

    public void playerLoses(double bet) {
        bet = this.playersBet;
        this.initalAmount -= bet;
    }

    public void playerWins(double bet) {
        bet = this.playersBet;
        this.initalAmount += bet;
    }
}