package hexGame;

public class Player {
    String name,colour;
    int id,turn,wins;
    
    public Player(String name,int id) {
        this.name=name;
        this.id=id;
        this.wins=0;
        this.colour="White";
        this.turn=0;
    }
    
    public String toString() {
        return "Player [id=" + id + ", name=" + name + ", turn="+ turn +", colour="+ colour +"]";
    }

    public void won(){
        this.wins++;
    }

    public void giveTurn(int n){
        if(n==1){
            this.colour="Blue";
            this.turn=1;
        }else{
            this.colour="Red";
            this.turn=2;
        }
    } 
}
