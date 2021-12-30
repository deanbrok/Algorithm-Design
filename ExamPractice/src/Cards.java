public class Cards {
    /**
     * You should implement this method.
     * @param n the number of cards
     * @param cards the cards at indices 1 through n, note that you should ignore index 0!
     * @return a card that is equivalent to the majority, or null if no such card exists.
     */
    public static Card bankFraud(int n, Card[] cards) {
        // TODO

        Triple r = bankFraud(cards, 1, n);

        if (r == null) return null;
        return r.card;
    }

    private static Triple bankFraud(Card[] cards, int lo, int hi) {


        if (lo >= hi) return new Triple(1, lo, cards[lo]);

        int majorityReq = (hi - lo + 1) / 2;
        int mid = lo + (hi -lo) / 2;

        Triple majorityLeft = bankFraud(cards, lo, mid);

        if (majorityLeft != null) {
            for (int i = mid + 1; i <= hi; i++) {
                if (cards[i].isEquivalent(majorityLeft.card)) {
                    majorityLeft.cnt++;
                }
            }
            if (majorityLeft.cnt > majorityReq) return majorityLeft;
        }

        Triple majorityRight = bankFraud(cards, mid + 1, hi);

        if (majorityRight != null) {
            for (int i = lo; i <= mid; i++) {
                if (cards[i].isEquivalent(majorityRight.card)) {
                    majorityRight.cnt++;
                }
            }
            if (majorityRight.cnt > majorityReq) return majorityRight;
        }

        return null;
    }

    /*
       A class you may find useful to keep track of three pieces of data together.
       Although our reference solution uses it, there is no obligation to use it!
       */
    static class Triple {

        int cnt;

        int index;

        Card card;

        public Triple(int cnt, int index, Card card) {
            this.cnt = cnt;
            this.index = index;
            this.card = card;
        }
    }


    /**
     * NOTE: Although these public tests are simply cards that use an id, our spec tests have a more involved method of checking for equivalence!
     * You cannot assume anything about these Card objects, other than that the method isEquivalent exists!
     */
    static class PublicTestCard implements Card {

        int id;

        @Override
        public String toString() {
            return "PublicTestCard{" +
                    "id=" + id +
                    '}';
        }

        public PublicTestCard(int id) {
            this.id = id;
        }

        @Override
        public boolean isEquivalent(Card other) {
            if (other instanceof PublicTestCard) {
                return this.id == ((PublicTestCard) other).id;
            }
            return false;
        }
    }
    interface Card {

        boolean isEquivalent(Card other);
    }

    public static void main(String[] args) {
        int n = 20;
        Card[] cards = new Card[n + 1];
        for (int i = 1; i <= n; i++) {
            cards[i] = new PublicTestCard(i);
        }
        // They are all different
//        Assert.assertNull(Gringotts.bankFraud(n, cards));
        for (int i = 1; i <= n / 2; i++) {
            cards[i] = new PublicTestCard(1);
        }
        // Exactly half are equivalent now.
//        Assert.assertNull(Gringotts.bankFraud(n, cards));
        cards[n] = new PublicTestCard(1);
        // Exactly half + 1 are equivalent now.
        System.out.println(bankFraud(n, cards));
    }

}
