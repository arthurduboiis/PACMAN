package BACK.OBJETS;

import BACK.PREDEF.typeObjet;

/*

Comprend pacman et les fantomes


 */

public class ObjetMobile extends Objet{

    private int[] coordonees;

    private Objet objetSauvegarde;

    private boolean boosted;

    /**
     * initialise objet mobile
     * @param t typeObjet
     * @param coordonneeX int
     * @param coordoneeY int
     */
    public ObjetMobile(typeObjet t, int coordonneeX, int coordoneeY) {
        super(t);
        super.setObjetMobile(this);
        this.coordonees = new int[]{coordonneeX, coordoneeY};
        this.objetSauvegarde = new ObjetDecors(typeObjet.VIDE);
    }

    /**
     * initialise objet mobile
     * @param t typeObjet
     * @param coordonneeX int
     * @param coordoneeY int
     * @param Image String
     */
    public ObjetMobile(typeObjet t, int coordonneeX, int coordoneeY, String Image) {
        super(t);
        super.setObjetMobile(this);
        this.coordonees = new int[]{coordonneeX, coordoneeY};
        super.setImage(Image);
    }




    /////////////////////////

    /**
     * renvoie l'objet sauvegarde
     * @return Objet
     */
    public Objet getObjetSauvegarde() {
        return objetSauvegarde;
    }

    /**
     * initialise l'objet sauvegarde
     * @param objetSauvegarde Objet
     */
    public void setObjetSauvegarde(Objet objetSauvegarde) {
        this.objetSauvegarde = objetSauvegarde;
    }

    /**
     * verifie si boosted
     * @return boolean
     */
    public boolean isBoosted() {
        return boosted;
    }

    /**
     * initialise boosted
     * @param boosted boolean
     */
    public void setBoosted(boolean boosted) {
        this.boosted = boosted;
    }

    /**
     * initialise coordonees
     * @param coordonees int
     */
    public void setCoordonees(int[] coordonees) {
        this.coordonees = coordonees;
    }

    /**
     * renvoie coordonees
     * @return int[]
     */
    public int[] getCoordonees() {
        return coordonees;
    }
}
