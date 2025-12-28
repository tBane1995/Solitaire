package com.tbane.solitaire.Game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.tbane.solitaire.GUI.Button;
import com.tbane.solitaire.GUI.ButtonWithText;
import com.tbane.solitaire.GUI.Font;
import com.tbane.solitaire.MyInput.MyInput;
import com.tbane.solitaire.Renderer;
import com.tbane.solitaire.Textures.TexturesManager;
import com.tbane.solitaire.Time;
import com.tbane.solitaire.Views.Layout;
import com.tbane.solitaire.Views.LayoutsManager;

import java.util.ArrayList;
import java.util.Random;

public class Game extends Layout {

    enum GameStates { Start, Game, AutoComplete, EndScreen, End };
    GameStates _state;
    private Button _backBtn;
    private ButtonWithText _autoCompleteBtn;

    public static int _moves = 0;
    public static float _startTime = 0;



    public Game() {
        super();

        _backBtn = new Button(
            TexturesManager.getTexture("tex/backButtonNormal.png"),
            TexturesManager.getTexture("tex/backButtonHover.png"),
            TexturesManager.getTexture("tex/backButtonPressed.png"),
            32, Renderer.VIRTUAL_HEIGHT - 96 - 32, 96, 96
        );

        _backBtn.onclick_func = () -> { LayoutsManager.pop_back(); };

        _autoCompleteBtn = new ButtonWithText(
            "auto-complete",
            TexturesManager.getTexture("tex/menuButtonNormal.png"),
            TexturesManager.getTexture("tex/menuButtonHover.png"),
            TexturesManager.getTexture("tex/menuButtonPressed.png"),
            Renderer.VIRTUAL_WIDTH/2 - 256,
            Renderer.VIRTUAL_HEIGHT/2 - 512 + 32,
            512,
            96
        );

        _autoCompleteBtn.onclick_func = () -> {
            _state = GameStates.AutoComplete;
        };

        // calculate the card size, dy and card padding
        float cardWdt = (Renderer.VIRTUAL_WIDTH-24)/(7);

        Foundation._dx = (Renderer.VIRTUAL_WIDTH - (cardWdt * 7)) / 2f;
        Foundation._dy = 160 + 16;

        Tableau._dx = Foundation._dx;
        Tableau._dy = 320 + 16;

        Card._padding = 4;
        cardWdt = cardWdt - 2 * Card._padding;
        Card._size = new Vector2(cardWdt, 356.0f*(cardWdt/256.0f));
        Card._dy = 36;

        _state = GameStates.Start;



    }

    private ArrayList<Card> generateCards() {
        ArrayList<Card> cards = new ArrayList<Card>();

        for (int i = 1; i <= 13; i++) {
            cards.add(new Card(Card.cardColor.Clover, i));
            cards.add(new Card(Card.cardColor.Pike, i));
            cards.add(new Card(Card.cardColor.Heart, i));
            cards.add(new Card(Card.cardColor.Tile, i));
        }

        return cards;
    }

    private void shuffleTheCards(ArrayList<Card> cards){
        // shuffle the cards
        Random random = new Random();
        for (int i = cards.size() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1); // 0..i

            Card temp = cards.get(i);
            cards.set(i, cards.get(j));
            cards.set(j, temp);
        }
    }

    private void startGame() {
        ArrayList<Card> cards = generateCards();
        shuffleTheCards(cards);

        Foundations.create();
        Tableaus.create();

        Stock.create(new Vector2(
            Foundation._dx + (Tableaus.tableaus.size()-1) * (Card._size.x + 2*Card._padding) + Card._padding,
            -Foundation._dy + Renderer.VIRTUAL_HEIGHT - Card._size.y - Card._padding
        ));

        Stock.addCards(cards);
        Tableaus.setTheCardsFromTheStock();

        // Create the Waste on the Top Right (left)
        Waste.create(new Vector2(
            Foundation._dx + (Tableaus.tableaus.size()-2) * (Card._size.x + 2*Card._padding) + Card._padding,
            -Foundation._dy + Renderer.VIRTUAL_HEIGHT - Card._size.y - Card._padding
        ));

        Animations.create();

        _startTime = Time.currentTime;
        _moves = 0;
    }

    private boolean readyToAutoComplete() {
        for(Tableau tableau : Tableaus.tableaus){
            for(Card card : tableau._cards){
                if(!card._isOpen)
                    return false;
            }
        }

        return true;
    }

    private void autoComplete() {



        // Open the hidden Cards
        for(Tableau tableau : Tableaus.tableaus){
            Card lastCard = tableau.getLastCard();
            if(lastCard != null){
                if(!lastCard._isOpen){
                    lastCard._isOpen = true;
                    Rectangle startRect = lastCard._rect;
                    Rectangle endRect = lastCard._rect;
                    Animations.addAnimation(lastCard, startRect, endRect);
                    _moves += 1;
                    return;
                }
            }
        }

        // Add the card from Tableaus to Foundation
        ArrayList<Card> acceptableCards = new ArrayList<Card>();
        ArrayList<Tableau> tabs = new ArrayList<Tableau>();

        for(Tableau tableau : Tableaus.tableaus){
            Card lastCard = tableau.getLastCard();
            if(lastCard != null){
                for(Foundation foundation : Foundations.foundations){
                    if(foundation.cardIsAcceptable(lastCard)){
                        acceptableCards.add(lastCard);
                        tabs.add(tableau);
                    }
                }
            }
        }

        if(!acceptableCards.isEmpty()){
            int id = (int)(Math.random() * acceptableCards.size());
            Card card = acceptableCards.get(id);
            for(Foundation foundation : Foundations.foundations){
                if(foundation.cardIsAcceptable(card)){
                    Rectangle startRect = new Rectangle(card._rect);
                    tabs.get(id).removeCard(tabs.get(id)._cards.size()-1);
                    foundation.addCard(card);
                    Rectangle endRect = new Rectangle(card._rect);
                    Animations.addAnimation(card, startRect, endRect);
                    _moves += 1;
                    return;
                }
            }
        }


        Card lastCard = Waste.getLastCard();

        if(lastCard != null){
            for(Foundation foundation : Foundations.foundations) {
                if(foundation.cardIsAcceptable(lastCard)){
                    Rectangle startRect = new Rectangle(Waste._rect);
                    Waste._cards.remove(Waste._cards.size()-1);
                    foundation.addCard(lastCard);
                    Rectangle endRect = new Rectangle(lastCard._rect);
                    Animations.addAnimation(lastCard, startRect, endRect);
                    _moves += 1;
                    return;
                }
            }
        }

        lastCard = Stock.getLastCard();

        if(lastCard != null){
            Rectangle startRect = Stock._rect;
            Waste.addCard(lastCard);
            Rectangle endRect = Waste._rect;
            Stock._cards.remove(Stock._cards.size() - 1);
            Animations.addAnimation(lastCard, startRect, endRect);
            lastCard._isOpen = true;
        }
        else{
            while (!Waste._cards.isEmpty()) {
                Rectangle startRect = Waste._rect;
                Card card = Waste._cards.get(Waste._cards.size() - 1);
                Waste._cards.remove(Waste._cards.size() - 1);
                Stock.addCard(card);
                Rectangle endRect = Stock._rect;
                Animations.addAnimation(card, startRect, endRect);
                _moves += 1;
                card._isOpen = false;
            }
        }

    }

    private boolean endGame() {
        for(Foundation foundation : Foundations.foundations){
            if(foundation._cards.size() != 13){
                return false;
            }
        }

        return true;
    }

    private void drawTimerAndMoves() {
        int minutes = (int)(Time.currentTime - _startTime)/60;
        int seconds = (int)(Time.currentTime - _startTime)%60;

        if(minutes>99){
            minutes = 99;
            seconds = 59;
        }

        String text = "Time: ";

        if(minutes < 10)
            text = text + "0";
        text = text + Integer.toString(minutes) + ":";

        if(seconds < 10)
            text = text + "0";
        text = text + Integer.toString(seconds);

        GlyphLayout layout = new GlyphLayout();
        layout.setText(Font.gameTopTextsFont,text );
        float textWidth = layout.width;
        float textHeight = Font.gameTopTextsFont.getCapHeight();
        float x = 96+64;
        float y = Renderer.VIRTUAL_HEIGHT - textHeight - 40;
        Font.gameTopTextsFont.draw(Renderer.spriteBatch, text, x, y);

        /// //////

        text = "Moves: " + Integer.toString(_moves);
        layout = new GlyphLayout();
        layout.setText(Font.gameTopTextsFont,text );
        textWidth = layout.width;
        textHeight = Font.gameTopTextsFont.getCapHeight();
        x = Renderer.VIRTUAL_WIDTH - textWidth - 64;
        y = Renderer.VIRTUAL_HEIGHT - textHeight - 40;
        Font.gameTopTextsFont.draw(Renderer.spriteBatch, text, x, y);
    }

    @Override
    public void handleEvents() {

        _backBtn.handleEvents();

        if(_state == GameStates.Game){
            if (MyInput.processor.isBackPressed()) {
                LayoutsManager.pop_back();
                MyInput.processor.reset();
            }

            CardsInHand.handleEvents();

            for(Tableau tableau : Tableaus.tableaus)
                tableau.handleEvents();

            for(Foundation foundation : Foundations.foundations)
                foundation.handleEvents();

            Stock.handleEvents();
            Waste.handleEvents();


            if(readyToAutoComplete())
                _autoCompleteBtn.handleEvents();
        }

    }

    @Override
    public void update() {

        _backBtn.update();

        switch (_state){
            case Start:
                startGame();
                _state = GameStates.Game;
                break;

            case Game:
                for(Tableau tableau : Tableaus.tableaus)
                    tableau.update();

                for(Foundation foundation : Foundations.foundations)
                    foundation.update();

                Stock.update();
                Waste.update();
                CardsInHand.update();


                _autoCompleteBtn.update();

                if(Animations.animations.isEmpty() && endGame())
                    _state = GameStates.End;
                break;

            case AutoComplete:

                for(Tableau tableau : Tableaus.tableaus)
                    tableau.update();

                for(Foundation foundation : Foundations.foundations)
                    foundation.update();

                Stock.update();
                Waste.update();
                CardsInHand.update();

                if(Animations.animations.isEmpty()){
                    autoComplete();
                }
                if(Animations.animations.isEmpty() && endGame())
                    _state = GameStates.EndScreen;
                break;

            case EndScreen:
                _state = GameStates.End;
                break;

            case End:
                LayoutsManager.pop_back();
                break;

        }


        Animations.update();
    }

    @Override
    public void draw() {

        Sprite background = new Sprite(TexturesManager.getTexture("tex/mainBoard.png").texture);
        background.setPosition(0,0 );
        background.draw(Renderer.spriteBatch);

        Sprite topPanel = new Sprite(TexturesManager.getTexture("tex/topPanel.png").texture);
        topPanel.setPosition(0,Renderer.VIRTUAL_HEIGHT - topPanel.getHeight() );
        topPanel.draw(Renderer.spriteBatch);

        _backBtn.draw();

        drawTimerAndMoves();

        switch (_state){
            case Game:
                for(Tableau tableau : Tableaus.tableaus)
                    tableau.draw();

                for(Foundation foundation : Foundations.foundations)
                    foundation.draw();

                Stock.draw();
                Waste.draw();
                Animations.draw();
                CardsInHand.draw();

                if(readyToAutoComplete())
                    _autoCompleteBtn.draw();

                break;
            case AutoComplete:
                for(Tableau tableau : Tableaus.tableaus)
                    tableau.draw();

                for(Foundation foundation : Foundations.foundations)
                    foundation.draw();

                Stock.draw();
                Waste.draw();
                Animations.draw();

                break;
        }

        // ads panel
        Sprite adsPanel = new Sprite(TexturesManager.getTexture("tex/bottomPanel.png").texture);
        adsPanel.setPosition(0, 0);
        adsPanel.draw(Renderer.spriteBatch);


    }
}
