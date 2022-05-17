package hexGame;

public class Hex{
Player p1,p2;
int[][] tab;
int row,col,winner,freeCases;
    
    // Constructor
    public Hex(int row,int col,Player p1, Player p2){
        this.p1=p1;
        this.p2=p2;
        this.p1.giveturn(1);;
        this.p2.giveturn(2);;
        this.row=row;
        this.col=col;
        this.tab= new int[row][col];
        this.winner=0;
        this.freeCases=row*col;
    }

    public String toString() {
        String topBottom="";
        System.out.print("    ");
        for (int j=0;j<col-1;j++){
            topBottom+=(char) (65+j)+" | ";
        }
        topBottom+=(char) (65+col-1);
        System.out.println(topBottom);

        for (int i = 0;i < tab.length; i++) {
            for (int repeat=0;repeat<i;repeat++){
                System.out.print("    ");
            }
            System.out.print((i+1)+" ");
            if(i+1<10){
                System.out.print(" ");
            }
            for (int j = 0;j < tab[i].length-1;j++) {
               System.out.print("["+tab[i][j] +"]"+",");  
            }
            System.out.print("["+tab[i][tab[i].length-1] +"]"); 
            System.out.print(" "+(i+1));
            System.out.println();
        }
        for(int i=0;i<row;i++){
            System.out.print("    ");
        }
        System.out.println(topBottom);
        System.out.println("(UP/DOWN) is red, (LEFT/RIGHT) is blue");
        return("Player 1 is "+p1.name+" with "+p1.colour+" colour."+'\n'+"Player 2 is "+p2.name+" with "+p2.colour+" colour.");
    }


    public Boolean play(int n,char x,Player p){
        int y = charToInt(x);
        if(canPlay(n,y)){
            tab[n-1][y-1]=p.turn;
            freeCases--;
            return true;
        }
        System.out.println("This cell is full or doesn t exist, try another one !");
        return false;
    }
    
///////////////////////////////////////////////////
    public Boolean canPlay(int n,int y){        //
        if(y<=col && y>0 && n>0 && n<=row){    //
            if(tab[n-1][y-1]==0){             //
                return true;                 //
            }                               //
        }                                  //
        return false;                     //
    }                                    //
//////////////////////////////////////////
    public int charToInt(char x){      //
        x=Character.toUpperCase(x);   //
        return (int) x-64;           //
    }                               //
/////////////////////////////////////



    public boolean voisins(int zone[][],int p) {
        // renvoie true si au moins une modification faite
        // cette modification reste visible dans le tableau "zone"
        boolean modif = false;
        for (int i = 0; i < zone.length; i++) {
            for (int j = 0; j < zone[i].length; j++) {
                // si position contient jeton rouge
                // et voisin de (i,j) marqué par la vague et portant jeton rouge
                // alors (i,j) devient marqué
                // convention : zone[i][j] == 1 -> jeton bleu
                // zone[i][j] == 2 -> jeton rouge et position pas encore atteinte par la vague
                // zone[i][j] == 0 -> case vide
                // zone[i][j] == 4 -> jeton rouge et position atteinte par la vague
                if (zone[i][j] == p) {
                    if ((i > 0) && (zone[i - 1][j] == 4)) {
                        zone[i][j] = 4;
                        modif = true;
                    }
                    if ((i < zone.length - 1) && (zone[i + 1][j] == 4)) {
                        zone[i][j] = 4;
                        modif = true;
                    }
                    if ((j < zone[i].length - 1) && (zone[i][j + 1] == 4)) {
                        zone[i][j] = 4;
                        modif = true;
                    }
                    if ((j > 0) && (zone[i][j - 1] == 4)) {
                        zone[i][j] = 4;
                        modif = true;
                    }
                    if ((i > 0) && (j < zone[i].length - 1) && (zone[i - 1][j + 1] == 4)) {
                        zone[i][j] = 4;
                        modif = true;
                    }
                    if ((i < zone.length - 1) && (j > 0) && (zone[i + 1][j - 1] == 4)) {
                        zone[i][j] = 4;
                        modif = true;
                    }
                }
            }
        }
        return modif;
    }

    public boolean balayage_succes(int zone[][],int turn) {
        if(turn==1){
            for (int i = 0; i < row; i++) {
                if (zone[i][col-1] == 4) {
                    return true;
                }
            }
        }else{
            for (int j = 0; j < col; j++) {
                if (zone[row-1][j] == 4) {
                    return true;
                }
            }
        }
        return false;
    }

    public void init_balayage(int zone[][],int turn) {
        // copie arene ds zone
        // et initialise la vague
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                zone[i][j] = this.tab[i][j];
            }
        }

        if(turn==1){
            for (int i = 0; i < row; i++) {
                if (zone[i][0] == turn) {
                    zone[i][0] = 4;
                }
            }
        }
        else{
            for (int j = 0; j < col; j++) {
                if (zone[0][j] == turn) {
                    zone[0][j] = 4;
                }
            }
        }
    }
    
    
    public boolean verify(int turn) {
        // à partir d'une arène donnée, voir jusqu'où la vague se stabilise
        // initialisation de la vague d'un des cotés
        int[][] rech = new int[row][col];
        init_balayage(rech,turn);
        while (voisins(rech,turn)) {}
        return balayage_succes(rech,turn);
    }



}