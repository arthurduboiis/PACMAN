package BACK.CONTROLEUR;

import AFFICHAGE.VIEW.MenuView;
import BACK.Game;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ControleMenu{


    private MenuView menuView;

    private Game game;

    private boolean EE1;

    private ArrayList<KeyCode> KC;


    public ControleMenu(Game game){

        this.game = game;

        this.EE1 = false;
        this.KC = new ArrayList<KeyCode>();

    }


    public void addControleMenu(MenuView menuView){

        System.out.println("Controle menu active");


        menuView.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent event)
            {
                if(event.getCode() == KeyCode.M)
                {
                    System.out.println("M appuyé");

                    if (EE1){
                        EE1 = false;
                        game.getVueJeu().initImages();
                    }else{
                        EE1 = true;
                        game.getVueJeu().initEE1Images();
                    }


                }else if (event.getCode() == KeyCode.A) {
                    System.out.println("A appuyé");
                    KC.add(event.getCode());
                    tryKC();
                }
                else if (event.getCode() == KeyCode.B) {
                    System.out.println("B appuyé");
                    KC.add(event.getCode());
                    tryKC();
                }
                else if(event.getCode() == KeyCode.UP)
                {
                    System.out.println("Flèche haut appuyée");
                    KC.add(event.getCode());
                    tryKC();
                }
                //goes down
                else if(event.getCode() == KeyCode.DOWN)
                {
                    System.out.println("Flèche bas appuyée");
                    KC.add(event.getCode());
                    tryKC();


                }
                // goes right
                else if(event.getCode() == KeyCode.RIGHT)
                {
                    System.out.println("Flèche droite appuyée");
                    KC.add(event.getCode());
                    tryKC();


                }
                // goes left
                else if(event.getCode() == KeyCode.LEFT)
                {
                    System.out.println("Flèche gauche appuyée");
                    KC.add(event.getCode());
                    tryKC();

                }
            }
        });

    }



    public void tryKC(){

        KeyCode[] sampleKC = new KeyCode[]{KeyCode.UP,KeyCode.UP,KeyCode.DOWN,KeyCode.DOWN,KeyCode.LEFT,KeyCode.RIGHT,KeyCode.LEFT,KeyCode.RIGHT,KeyCode.B,KeyCode.A};

        for (int i = 0; i < 10; i++) {

            if (!(sampleKC[i].equals(KC.get(i)))){

                KC = new ArrayList<KeyCode>();
                return;
            }

        }

        //TODO: Ajouter fonction konami COde


    }




}