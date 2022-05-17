package hexGame;

import java.util.Scanner;

public class Tournament {
    Player[] listOfPlayers;
    Player[] Winners;
    int winnerIndex=0;
    int playerCount;
    int col;
    int row;

    public Tournament(int row,int col,Player[] listOfPlayers){
        this.listOfPlayers=listOfPlayers;
        this.playerCount=this.listOfPlayers.length;
        this.row=row;
        this.col=col;
    }
    
    public void start() throws InterruptedException{
        Scanner scanner = new Scanner( System.in );
        if(playerCount%2==1){
            Winners=new Player[(playerCount+1)/2];
            System.out.println("The number of players is odd, so "+listOfPlayers[playerCount-1].name+" should choose a player to play with by entering his id. here is the list of players:");
            System.out.println("ID       Player");
            for (int i=0;i<playerCount-1;i++){
                System.out.println(listOfPlayers[i].id+"        "+listOfPlayers[i].name);
            }
            System.out.print("You can also play with the pc by typing 0: ");
            int opp=scanner.nextInt();

            while(opp>playerCount || opp<0){
                System.out.print("Invalid ID ! enter again: ");
                opp=scanner.nextInt();
            }
        
            if(opp>=0 && opp<=playerCount){
                for(int i=0;i<playerCount-1;i+=2){
                    Thread.sleep(1000);
                    System.out.println();
                    System.out.println("                       GAME "+(i+2)/2+"                       ");
                    System.out.println();
                    System.out.println("The players of this match will be: "+listOfPlayers[i].name+" VS "+listOfPlayers[i+1].name);
                    Game game= new Game();
                    game.launchPVP(row,col,listOfPlayers[i],listOfPlayers[i+1]);
                    if(game.winner.id!=0){
                        Winners[winnerIndex]=game.winner;
                        winnerIndex++;
                    }
                    lookForNewGame();
                }

                Thread.sleep(1000);
                System.out.println();
                System.out.println("                       GAME "+(playerCount+1)/2+"                       ");
                System.out.println();
                
                if(opp>0){
                    System.out.println("The players of this match will be: "+listOfPlayers[playerCount-1].name+" VS "+listOfPlayers[opp-1].name);
                    Game game=new Game();
                    game.launchPVP(row,col,listOfPlayers[playerCount-1],listOfPlayers[opp-1]);
                    if(game.winner.id!=0){
                        Winners[winnerIndex]=game.winner;
                        winnerIndex++;
                    }
                    lookForNewGame();
                    matchEnd();
                }
                if(opp==0){
                    System.out.println("The players of this match will be: "+listOfPlayers[playerCount-1].name+" VS CPU.");
                    Game game=new Game();
                    game.launch1P(row,col,listOfPlayers[playerCount-1]);
                    if(game.winner.id!=0){
                        Winners[winnerIndex]=game.winner;
                        winnerIndex++;
                    }
                    lookForNewGame();
                    matchEnd();
                }
            }
        }
        else{
            Winners=new Player[playerCount/2];
            Game game= new Game();
            for(int i=0;i<playerCount;i+=2){
                Thread.sleep(1000);
                System.out.println();
                System.out.println("                       GAME "+(i+2)/2+"                       ");
                System.out.println();
                System.out.println("The players of this match will be: "+listOfPlayers[i].name+" VS "+listOfPlayers[i+1].name);
                game= new Game();
                game.launchPVP(row,col,listOfPlayers[i],listOfPlayers[i+1]);
                if(game.winner.id!=0){
                    Winners[winnerIndex]=game.winner;
                    winnerIndex++;
                }
                lookForNewGame();
            }
            matchEnd();
        }
    }
    
    public void end(){
        System.out.println("The Winners are: ");
        for(int i=0;i<winnerIndex;i++){
            System.out.println("- "+Winners[i].name);
        }
    }

    public void lookForNewGame(){
        /*
        Just some prints to look for a game
        */

        System.out.println();
        System.out.println();
        System.out.print(".");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print(".");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print(".");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print("LOOKING FOR THE NEXT GAME!");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    public void matchEnd(){
        /*
        Just some prints to announce the end of all matchs
        */
        System.out.println();
        System.out.println();
        System.out.println("No more games found.");
        System.out.println();
        System.out.println("THANK YOU FOR YOUR PARTICIPATION!");
        System.out.println();    
    }
}