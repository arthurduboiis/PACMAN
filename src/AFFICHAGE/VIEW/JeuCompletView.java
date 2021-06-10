package AFFICHAGE.VIEW;

import BACK.CONTROLEUR.ControleBouton;
import BACK.CONTROLEUR.ControleJeu;
import BACK.Game;
import javafx.scene.layout.StackPane;

public class JeuCompletView extends StackPane {
    private Game game;
    private PauseView pauseView;
    private VueJeu vueJeu;

    public JeuCompletView(ControleBouton controleBouton, Game game){
        this.game = game;
        this.pauseView = new PauseView(controleBouton,this.game);


    }

    /**
     * initialise le controle jeu
     * @param controleJeu ControlJeu
     */
    public void setControleJeu(ControleJeu controleJeu){
        vueJeu = new VueJeu(controleJeu,this.game);

    }

    /**
     * initialise la vue du jeu
     * @param vueJeu VuueJeu
     */
    public void setVueJeu(VueJeu vueJeu){
        this.vueJeu = vueJeu;
    }

    /**
     * initialise Stack
     */
    public void initStack(){
        this.getChildren().clear();
        this.getChildren().add(vueJeu);
        this.getChildren().add(pauseView);

    }

    /**
     * renvoie le pause view
     * @return PauseView
     */
    public PauseView getPauseView() {
        return pauseView;
    }

    /**
     * renvoie la VueJeu
     * @return VueJeu
     */
    public VueJeu getVueJeu() {
        return vueJeu;
    }


}
