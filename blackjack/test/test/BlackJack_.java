package test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.junit.Assert.*;
import static test.BlackJack_.Card.*;

import org.junit.Test;

public class BlackJack_ {
    
    @Test
    public void given_one_card_should_calculate_value() {
       assertEquals(5, createHand(_5).value());
       assertEquals(6, createHand(_6).value());
       assertEquals(10, createHand(Jack).value());
       assertEquals(10, createHand(Queen).value());
       assertEquals(10, createHand(King).value());
       assertEquals(11, createHand(Ace).value());
    }
    
    @Test
    public void given_two_cards_should_calculate_value() {
       assertEquals(11, createHand(_5, _6).value());        
       assertEquals(12, createHand(Ace, Ace).value());        
    }
    
    @Test 
    public void given_two_cards_should_determine_if_is_black_jack() {
       assertEquals(false, createHand(_5, _6).isBlackJack());        
       assertEquals(true, createHand(Ace, Queen).isBlackJack());               
    }

    @Test 
    public void given_three_cards_should_determine_that_is_not_black_jack() {
       assertEquals(false, createHand(_5, _6, Queen).isBlackJack());               
    }
    
    @Test 
    public void given_two_cards_should_determine_that_is_not_bust() {
       assertEquals(false, createHand(_4,_3).isBust());               
    }
    
    @Test 
    public void given_three_cards_should_determine_that_is_bust_or_not() {
       assertEquals(true, createHand(_4, Jack, King).isBust());               
       assertEquals(false, createHand(_4, _2, _3).isBust());               
    }

    private Hand createHand(Card... cards) {
        return new Hand() {
            @Override
            public int value() {
                return canUseAceExtendedValue() ? sum() + 10 : sum();
            }

            private int sum() {
                return Stream.of(cards).mapToInt(c->c.value()).sum();
            }

            private boolean canUseAceExtendedValue() {
                return sum() <= 11 && containsAce();
            }

            private boolean containsAce() {
                return Stream.of(cards).anyMatch(c->c==Ace);
            }

            @Override
            public boolean isBlackJack() {
                return value() == 21 && cards.length == 2;
            }

            @Override
            public boolean isBust() {                
                return value() > 21;
            }

        };
    }

    public interface Hand {
        int value();
        boolean isBlackJack();
        boolean isBust();
    }

    public enum Card {
        Ace, _2, _3, _4, _5, _6, _7, _8, _9, _10, Jack, Queen, King;

       
        public boolean isFace() {
            return this == Jack || this == Queen || this == King;            
        }

        public int value() {
            return isFace() ? 10 : ordinal() + 1;
        }
        
        
    }    
    
}
