package com.example.solitaire;

import java.io.Serializable;
import java.util.Vector;


public class game implements Serializable {

    public static class Stack extends java.util.Stack<Card> {}
    public static class Deck extends java.util.Stack<Card> {}


    public static final int STACK_COUNT = 4;
    public static final int DECK_COUNT = 7;

    public Stack [] stacks = new Stack[STACK_COUNT];
    public Deck [] decks = new Deck[DECK_COUNT];
    public Vector<Card> pioche = new Vector<>();
    public Vector<Card> returnedPioche = new Vector();

    public game() {

        // Step 1 - Toutes les cartes sont instanciées
        for( int i=1; i<=13; i++ ) {
            pioche.add( new Card( Card.CardType.CARREAU, i ) );
            pioche.add( new Card( Card.CardType.COEUR, i ) );
            pioche.add( new Card( Card.CardType.PIQUE, i ) );
            pioche.add( new Card( Card.CardType.TREFLE, i ) );
        }

        // Step 2 - On mélange les cartes
        for( int round=0; round<200; round++ ) {
            int position = (int) ( Math.random() * pioche.size() );
            Card removedCard = pioche.elementAt( position );
            pioche.removeElementAt( position );
            pioche.add( removedCard );
        }

        // Step 3 - On crée les sept decks avec des cartes tirées aléatoirement dans la pioche
        for( int deckIndex=0; deckIndex<DECK_COUNT; deckIndex++ ) {
            decks[deckIndex] = new Deck();
            for( int cardIndex = 0; cardIndex < deckIndex+1; cardIndex++ ) {
                int position = (int) ( Math.random() * pioche.size() );
                Card removedCard = pioche.elementAt( position );
                pioche.removeElementAt( position );
                decks[deckIndex].push( removedCard );
                if ( cardIndex == deckIndex ) removedCard.setReturned( true );
            }
        }

        // Step 4 - On initialise les quatre stacks.
        for( int stackIndex=0; stackIndex<STACK_COUNT; stackIndex++ ) {
            stacks[stackIndex] = new Stack();
        }

    }

    /**
     * Vérifie la carte peut être déposé sur l'une des quatre stacks.
     * @param card La carte à déposer.
     * @return L'indice de la stack sur laquelle la carte peut être déposée,
     *         -1 si ce n'est pas possible.
     */
    public int canMoveCardToStack( Card card ) {
        // Si une stack est vide et que la carte est un as
        if ( card.getValue() == 1 ) {
            int stackIndex = 0;
            while( ! this.stacks[stackIndex].isEmpty() ) {
                stackIndex++;
            }
            return stackIndex;
        }

        // Si ce n'est pas un as, peut-on empiler la carte sur une carte de
        // valeur inférieure dans l'une des piles.
        for( int stackIndex=0; stackIndex<STACK_COUNT; stackIndex++ ) {
            Stack stack = this.stacks[stackIndex];
            if ( ! stack.isEmpty() ) {
                if ( stack.lastElement().getType() != card.getType() ) continue;
                if ( stack.lastElement().getValue() == card.getValue()-1 ) return stackIndex;
            }
        }

        return -1;
    }

    /**
     * Vérifie si une carte peut être déposée sur un des sept decks.
     * @param card La carte à déposer.
     * @return L'indice du deck sur lequel la carte peut être déposée,
     *         -1 si ce n'est pas possible.
     */
    public int canMoveCardToDeck( Card card ) {
        // Si la carte est un roi et qu'un deck est vide, alors OK
            for( int deckIndex=0; deckIndex<DECK_COUNT; deckIndex++ ) {
                if ( this.decks[deckIndex].isEmpty() ) return deckIndex;
            }

        // Est-ce que la carte peut être placée sur un deck ?
        for( int deckIndex=0; deckIndex<DECK_COUNT; deckIndex++ ) {
            Deck deck = this.decks[deckIndex];
            if ( deck.size() > 0 ) {
                if ( deck.lastElement().getColor() == card.getColor() ) continue;
                if ( deck.lastElement().getValue() == card.getValue()+1 ) return deckIndex;
            }
        }

        return -1;
    }

    /**
     * Détermine si le jeu est terminé, c'est à dire que chaque stack possède bien 13 cartes.
     * @return true si le jeu est terminé, false dans le cas contraire.
     */
    public boolean isFinish() {
        return stacks[0].isEmpty() == false && stacks[0].lastElement().getValue() == 13 &&
                stacks[1].isEmpty() == false && stacks[1].lastElement().getValue() == 13 &&
                stacks[2].isEmpty() == false && stacks[2].lastElement().getValue() == 13 &&
                stacks[3].isEmpty() == false && stacks[3].lastElement().getValue() == 13;
    }

    //s'il y a pas un moove le jeu se termine
    //finaliser automatiquement le jeu
    public boolean allIsReturned() {
        for( int i=0; i<DECK_COUNT; i++ ) {
            Deck deck = decks[i];
            if ( deck.size() > 0 && deck.firstElement().isReturned() == false ) return false;
        }
        return true;
    }

}