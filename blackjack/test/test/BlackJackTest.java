package test;

import org.junit.Test;

import static test.BlackJackTest.Card.*;
import static org.junit.Assert.assertEquals;

public class BlackJackTest{
         
    @Test
    public void test_hand_value_with_one_card() {
        assertEquals(3, createHand(_3).value());
        assertEquals(10, createHand(_10).value());
        assertEquals(10, createHand(Jack).value());
        assertEquals(10, createHand(Queen).value());
        assertEquals(10, createHand(King).value());
        assertEquals(11, createHand(Ace).value());
    }
    
    @Test
    public void test_hand_value_with_two_cards() {
        assertEquals(8, createHand(_3, _5).value());        
    }
    
    @Test
    public void test_hand_is_black_jack() {
        assertEquals(false, createHand(_3, _5).isBlackJack());        
        assertEquals(true, createHand(Ace, Jack).isBlackJack());
        assertEquals(true, createHand(Ace, King).isBlackJack());
        assertEquals(true, createHand(Ace, Queen).isBlackJack());
        assertEquals(true, createHand(_10, Ace).isBlackJack());        
    }
    
    

    private Hand createHand(Card... cards) {
        return new Hand() {

            @Override
            public int value() {
                int sum = 0;
                for (Card card : cards)
                    sum += card.value();
                return sum;
            }

            @Override
            public boolean isBlackJack() {
                return containsCardWithValue(11) && containsCardWithValue(10);
            }

            private boolean containsCardWithValue(int expectedValue) {
                for (Card card : cards)
                    if (card.value() == expectedValue) return true;
                return false;
                
            }

        };
    }

    public interface Hand {
        public int value();
        public boolean isBlackJack();

    }
    
    public enum Card {
        _2, _3, _4, _5, _6, _7, _8, _9, _10, Jack, Queen, King, Ace;

        private boolean isFace() {
            return this == King || this == Queen || this == Jack;
        }

        private boolean isAce() {
            return this == Ace;
        }

        private int value() {
            if (isAce()) return 11;
            if (isFace()) return 10;
            return ordinal() + 2;
        }
    }
    
    
    
}
