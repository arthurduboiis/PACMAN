package BACK;

import AFFICHAGE.VIEW.FinView;
import AFFICHAGE.VIEW.VueJeu;
import BACK.CONTROLEUR.ControleBouton;
import BACK.PREDEF.Directions;
import BACK.PREDEF.Liens;
import BACK.PREDEF.typeObjet;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Game<boosted> {
    final int HEIGHT_FEN = 720;
    final int WIDTH_FEN = 660;

    private Grille grilleDuJeu;

    private int score;

    private Record record;

    private String[] textCommentJouer;

    private VueJeu vueJeu;

    //private FinView finView;

    private int nombreVies;

    private boolean enCours, boosted;

    public boolean premierDemarrage = true;

    private String name;

    private Directions directions;

    private int niveauActuel;

    private Directions derniereDirection;

    public Audio audioBoost;

    public Timeline timeline = new Timeline(new KeyFrame(Duration.millis(250), actionEvent -> run()));

    public boolean direc;
    private int tempsRestantBooste;
    private Directions directionsF[];



    public Game(){

        setTextCommentJouer();



        record = new Record();

        timeline.setCycleCount(Animation.INDEFINITE);

        this.grilleDuJeu = new Grille(this);

        this.score = 0;

        this.nombreVies = 1;

        this.niveauActuel = 1;


        this.directions = Directions.DROITE;
        this.derniereDirection = Directions.DROITE;
        this.enCours = true;
        this.directionsF = new Directions[]{Directions.NULL,Directions.NULL,Directions.NULL,Directions.NULL};
        try {
            audioBoost = new Audio(Liens.getCheminSon_intermission());
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {


        System.out.println("Nombre de bonbons mangés: " + grilleDuJeu.getNbBonbonsManges() + "/" + grilleDuJeu.getNbBonbons());



        this.vueJeu.refresh();
        boostedActions();
        if (this.score % 10000 == 0 && this.score > 0){this.nombreVies+=1;}

        grilleDuJeu.genFruit();

        jeuPacman();


        jeuFantomes();

        if(tempsRestantBooste == 0){
            tempsRestantBooste += 15;
            grilleDuJeu.setSerie(1);
            setBoosted(false);
        }else if(boosted){
            tempsRestantBooste--;
        }

        grilleDuJeu.printObjetMob();
        System.out.println("//////////////////////////////////");


        if (checkFin()){
            System.out.println("FIN DU JEU");
            try {
                this.record.setRecord(Liens.getCheminFichierRecord(),this.name, String.valueOf(score));
            } catch (IOException e) {
                e.printStackTrace();
            }
            timeline.stop();
            vueJeu.stop();
        }


    }

    public void jeuPacman(){


     /*


        if(!this.grilleDuJeu.deplaceObjet(this.grilleDuJeu.getPacMan(),directions)){
            this.grilleDuJeu.deplaceObjet(this.grilleDuJeu.getPacMan(),derniereDirection);
            direc = false;

        }else{
            direc = true;

            this.derniereDirection = directions;

        }

         */


        if(!this.grilleDuJeu.deplacerPacman(directions, this.grilleDuJeu.getPacMan())){
            this.grilleDuJeu.deplacerPacman(derniereDirection, this.grilleDuJeu.getPacMan());
            direc = false;
        }else{
            direc = true;
            this.derniereDirection = directions;
        }

    }



    public void jeuFantomes(){

       /*

        Objet[] fantomes = grilleDuJeu.getFantomes();
        int n = 0;
        boolean verif;
        for(int i = 0; i < 4; i++)
        {
            n++;
            if(this.directionsF[i] == Directions.NULL)
            {
                this.directionsF[i] = this.grilleDuJeu.genDir();
            }
            if(n >= 15){
                n = 0;
                this.directionsF[i] = this.grilleDuJeu.genDir();
            }
            if(this.directionsF[i].equals(Directions.GAUCHE) || this.directionsF[i].equals(Directions.DROITE))
            {
                Random random = new Random();
                int nombreAleatoire = random.nextInt(6);
                if(nombreAleatoire == 1 || nombreAleatoire == 5){
                    this.directionsF[i] = Directions.BAS;
                }
                if(nombreAleatoire == 3){
                    this.directionsF[i] = Directions.HAUT;
                }
            }
            while(!(this.grilleDuJeu.contains(fantomes[i].getObjetMobile().getCoordonees()[0]+this.grilleDuJeu.convertDir(directionsF[i])[0], fantomes[i].getObjetMobile().getCoordonees()[1]+this.grilleDuJeu.convertDir(directionsF[i])[1]).isTraversable())){
                this.directionsF[i] = this.grilleDuJeu.genDir();
            }
            verif = grilleDuJeu.deplacerFantome(this.directionsF[i], fantomes[i]);
            if(!verif){
                this.directionsF[i] = Directions.NULL;
            }
        }


         */

        grilleDuJeu.deplacerFantome(grilleDuJeu.genDir(), grilleDuJeu.getFantomes()[0]);
        grilleDuJeu.deplacerFantome(grilleDuJeu.genDir(), grilleDuJeu.getFantomes()[1]);
        grilleDuJeu.deplacerFantome(grilleDuJeu.genDir(), grilleDuJeu.getFantomes()[2]);
        grilleDuJeu.deplacerFantome(grilleDuJeu.genDir(), grilleDuJeu.getFantomes()[3]);



    }
    public void addBoost(){
        if(!boosted){

            this.boosted = true;
        }
        this.tempsRestantBooste += 20;
        audioBoost.loop();
    }

    public void stop(){
        //vueJeu.stop();
    }




    public boolean checkFin(){

        if(this.nombreVies == 0){
            return true;
        }else if(this.grilleDuJeu.getNbBonbonsManges() == this.grilleDuJeu.getNbBonbons()){
            try {
                grilleDuJeu.initGrille();
                boosted = false;
                this.niveauActuel += 1;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public String[][] getCinqMaxRecords() throws IOException {
        return record.getMaxRecord(Liens.getCheminFichierRecord());
    }

    public Grille getGrilleDuJeu(){
        return grilleDuJeu;
    }

    public void setGrilleDuJeu(Grille grilleDuJeu) {
        this.grilleDuJeu = grilleDuJeu;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getNombreVies() {
        return nombreVies;
    }

    public void setNombreVies(int nombreVies) {
        this.nombreVies = nombreVies;
    }

    public boolean isBoosted() {
        return boosted;
    }

    public void setBoosted(boolean boosted) {
        this.boosted = boosted;
        if(boosted){
            this.tempsRestantBooste = 20;
            vueJeu.setBoostImages();
        }else{
            vueJeu.undoSetBoostImages();
        }
    }

    public void boostedActions(){
        if(this.boosted){
            vueJeu.setBoostImages();
        }else{
            vueJeu.undoSetBoostImages();
            audioBoost.pause();
        }
    }

    public boolean isEnCours() {
        return enCours;
    }

    public void setEnCours(boolean enCours) {
        this.enCours = enCours;
    }

    public boolean isPremierDemarrage() {
        return premierDemarrage;
    }

    public void setPremierDemarrage(boolean premierDemarrage) {
        this.premierDemarrage = premierDemarrage;
    }

    public Directions getDirections() {
        return directions;
    }

    public void setDirections(Directions directions) {
        this.directions = directions;
    }

    public Directions getDerniereDirection() {
        return derniereDirection;
    }

    public void setDerniereDirection(Directions derniereDirection) {
        this.derniereDirection = derniereDirection;
    }

    public Timeline getTimeline() {

        return timeline;
    }

    public VueJeu getVueJeu() {
        return vueJeu;
    }

    public void setVueJeu(VueJeu vueJeu) {
        this.vueJeu = vueJeu;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    public int getTempsRestantBooste() {
        return tempsRestantBooste;
    }

    public void setTempsRestantBooste(int tempsRestantBooste) {
        this.tempsRestantBooste = tempsRestantBooste;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHEIGHT_FEN() {
        return HEIGHT_FEN;
    }

    public int getWIDTH_FEN() {
        return WIDTH_FEN;
    }

    public Record getRecord() {
        return record;
    }

    public String[] getTextCommentJouer() {
        return textCommentJouer;
    }


    public void setTextCommentJouer() {
        this.textCommentJouer = new String[5];
        textCommentJouer[0] = "Utiliser les flèches directionnelles pour se déplacer";
        textCommentJouer[1] = "Touche ECHAPE pour afficher le menu Pause";
        textCommentJouer[2] = "Touche ESPACE pour quitter le menu Pause";
        textCommentJouer[3] = "";
        textCommentJouer[4] = """
                Pacman a 3 vies et doit manger les Gums qui lui font augmenter son score tout en esquivant les fantomes.
                Manger une grosse gum lui permet de manger les fantômes et de devenir invincible temporairement.
                Manger toutes les Gums lui font passer un niveau.
                Le but est d'obtenir le meilleur score.""";
    }

    public Directions[] getDirectionsF() {
        return directionsF;
    }
    public int getNiveauActuel() {
        return niveauActuel;
    }

    public void setNiveauActuel(int niveauActuel) {
        this.niveauActuel = niveauActuel;
    }





}
