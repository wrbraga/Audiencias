package DB;
// Generated 26/11/2013 11:14:23 by Hibernate Tools 3.2.1.GA



/**
 * Classe generated by hbm2java
 */
public class Classe  implements java.io.Serializable {


     private int idclasse;
     private int classe;

    public Classe() {
    }

    public Classe(int idclasse, int classe) {
       this.idclasse = idclasse;
       this.classe = classe;
    }
   
    public int getIdclasse() {
        return this.idclasse;
    }
    
    public void setIdclasse(int idclasse) {
        this.idclasse = idclasse;
    }
    public int getClasse() {
        return this.classe;
    }
    
    public void setClasse(int classe) {
        this.classe = classe;
    }
    
}


