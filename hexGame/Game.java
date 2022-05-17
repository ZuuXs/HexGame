package hexGame;

import java.util.concurrent.ThreadLocalRandom;

import TournoiPadiflac.Channel;

import java.util.Scanner;

public class Game {
    
    Player winner;
    int x;
    char y;

    public Game() {
    }

    public void launchPVP(int row,int col,Player p1,Player p2) throws InterruptedException{
        /*
        PLAYER VS PLAYER
        */

        int randomNum = ThreadLocalRandom.current().nextInt(1, 3);

        // Choosing the player who is going to play first randomly 
        if(randomNum==2){
            Player tempPlayer;
            tempPlayer=p1;
            p1=p2;
            p2=tempPlayer;
        }
        p1.giveturn(1);
        p2.giveturn(2);

        Hex game= new Hex(row, col, p1, p2);

        System.out.println();

      
        Thread.sleep(1000);


        System.out.println(game.p1.name+" is chosen randomly to be the first player.");
        System.out.println();
  
        Thread.sleep(1000);

        System.out.println(game);
        System.out.println();

  

        // First plays with the swap possibility
        playTurn(game, game.p1, game.p2);

        System.out.println( game.p2.name+", Do you want to SWAP? () [IF YOU ENTER ANYTHING ELSE YOU LOSE THE CHANCE TO SWAP]: " );
        Scanner scan=new Scanner(System.in);

        if(scan.nextLine().equals("SWAP")){
            swap(game);
            nextTurn(game);
            playTurn(game, game.p1, game.p2);
        }

        // if the game has no winner, it keeps playing
        while(true){

            // Now is the second player's turn and we loop till we have a winner (if the game is still on)
            if(playTurn(game, game.p2, game.p1)){
                return;
            }
    
            // switch to the next player
            if(playTurn(game, game.p1, game.p2)){
                return;
            }
    
        }            
    }
    public void launch1P(int row,int col,Player p) throws InterruptedException{
        /*
        PLAYER VS CPU
        */
        Player CPU=new Player("CPU", 0);
        int trys=3;
        Hex game;

        System.out.println();
        Thread.sleep(1000);
        System.out.println("You choose to play first or second? (1/2)");
    
        Scanner scan=new Scanner(System.in);
        int turn=scan.nextInt();

        while((turn<1) || (turn>2)){
            System.out.println("1 or 2 only: ");
            turn=scan.nextInt();
        }

        System.out.println();

        if(turn==2){

            game= new Hex(row, col, CPU, p);
            System.out.println("\n"+game);
            CPUPlay(game, game.p1);


            System.out.print( game.p2.name+", Do you want to SWAP? (SWAP) [IF YOU ENTER ANYTHING ELSE YOU LOSE THE CHANCE TO SWAP]: " );
            scan.nextLine();
            if(scan.nextLine().equals("SWAP")){
                swap(game);
                nextTurn(game);
                CPUPlay(game, game.p1);
            }

            while(true){

                // Now is the second player's turn and we loop till we have a winner (if the game is still on)
                if(playTurn(game, game.p2, game.p1)){
                    return;
                }
                if(CPUPlay(game, game.p1)[0]=="won"){
                    return;
                }
            }

        }else{
            // if we choose to play first

            game= new Hex(row, col, p, CPU);
            System.out.println("\n"+game);
            playTurn(game, game.p1, game.p2);

  
            int swap = ThreadLocalRandom.current().nextInt(0, 2);
            // random swap of the cpu
            if(swap==1){
                Thread.sleep(1000);
                System.out.println("SWAP");
                swap(game); 
                nextTurn(game);
                playTurn(game, game.p1, game.p2);
            }

            while(true){
                if(CPUPlay(game, game.p2)[0]=="won"){
                    return;
                }
                if(playTurn(game, game.p1, game.p2)){
                    return;
                }
            }
        }
    }

    public void launchOnline(int row,int col,Channel left_channel,Channel right_channel) throws InterruptedException{
        /*
        CPU VS ONLINE PLAYER
        */
        Player localCPU=new Player("Local CPU", 0);
        Player onlinePlayer=new Player("Online Player",1);


        // ask for the turn
        System.out.println("You choose to play first or second? (1/2)");
        left_channel.send("You choose to play first or second? (1/2)");
        String turn=right_channel.getNext();

        

        if(turn.equals("1")){
            System.out.println("Online player chose to play first. \n");
            Hex game= new Hex(row, col, onlinePlayer, localCPU);
            System.out.println("\n"+game);

            onlinePlay(game, game.p1, right_channel);
            
            int swap = ThreadLocalRandom.current().nextInt(0, 2);

            if(swap==1){
                Thread.sleep(1000);
                System.out.println("SWAP");
                swap(game); 
                nextTurn(game);
                onlinePlay(game,game.p1,right_channel);

            }

            while(true){

                if(CPUPlayOnline(game, game.p2,left_channel)){
                    return;
                }
                if(onlinePlay(game,game.p1,right_channel)){
                    return;
                }
            }
        }
        
        else{
            System.out.println("Online Player chose to play 2nd");
            Hex game= new Hex(row, col, localCPU, onlinePlayer);
            System.out.println("\n"+game);

            CPUPlayOnline(game, game.p1, left_channel);

            if(right_channel.getNext().equals("SWAP")){
                swap(game);
                nextTurn(game);
                CPUPlayOnline(game, game.p1, left_channel);
            }

            while(true){

                // Now is the second player's turn and we loop till we have a winner (if the game is still on)
                if(onlinePlay(game,game.p2,right_channel)){
                    return;
                }

                if(CPUPlayOnline(game, game.p1, left_channel)){
                    return;
                }
            }

        }
    }




    public Boolean CPUPlayOnline(Hex game, Player p, Channel left_channel) throws InterruptedException{
        /*
        This function is just a quick upgrade for CPUPlay which will verify and return the case played at the same time
        */
        String[] details;
        details=CPUPlay(game, p);
        left_channel.send(details[1]);
        if(details[0]=="won"){
            return true;
        }
        return false;
    }


    public Boolean onlinePlay(Hex game,Player p,Channel right_channel){
        /*
        After reading the case played by the online player, we play it on our table and verify
        */
        int[] valeurs;
        int ligne;
        char colonne;

        // lecture du coup
        valeurs=readOnline(right_channel);
        ligne=valeurs[0];
        colonne=(char)(valeurs[1]+64);

        // affichage sur notre console le coup joué par l'adversaire
        System.out.println("the other player has played :"+((int)colonne-64)+" "+ligne);

        // jouer le coup lu
        game.play(ligne, colonne, p);

        nextTurn(game);

        if(game.verify(p.turn)){
            System.out.println();
            System.out.println("!!!!!!!!!!!!!!      "+p.name+" has won.      !!!!!!!!!!!!!!");
            return true;
        }
        return false;
    }


    public int[] readOnline(Channel right_channel){
        /*
        read the values played by the online player
        */
        int exponent=0;
        int[] values=new int[2];
        String[] message=right_channel.getNext().split(" ");

        // read the first number (the column)
        for(int i=message[0].length()-1;i>=0;i--){
            values[1]+=((int)message[0].charAt(i)-48)*(Math.pow(10, exponent));
            exponent++;
        }

        exponent=0;
        // read the second number (the row)
        for(int i=message[1].length()-1;i>=0;i--){
            values[0]+=((int)message[1].charAt(i)-48)*(Math.pow(10, exponent));
            exponent++;
        }
    

        return values;
    }



    public void read(){
        /*
         Read the player values which will be in this form '1A'
        */
        String s;
        int exponent=0;
        Scanner scan = new Scanner(System.in);
        s = scan.nextLine();
        y=s.charAt(s.length()-1);
        for(int i=s.length()-2; i>=0;i--){
            x+=((int)s.charAt(i)-48)*(Math.pow(10, exponent));
            exponent++;
        }
    }
    public void nextTurn(Hex game){
        /*
        just a print of the game 
        */
        System.out.println();
        System.out.println();
        System.out.println(game);
        System.out.println();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Boolean playTurn(Hex game,Player player,Player otherPlayer){
        /*
        Ask the player what case he wants to play and try it on the table if he fails 3 times he loses
        */
        x=0;
        int trys=3;
        if(player.turn==game.p1.turn){
            player.turn=game.p1.turn;
            otherPlayer.turn=game.p2.turn;
        }else{
            player.turn=game.p2.turn;
            otherPlayer.turn=game.p1.turn;
        }

        System.out.print( player.name+", which row(Integer) and column(Character) do you want to play?\nThe entry must be in this form '1A': " );
        read();

        // if the player entered an invalid play we ask again
        while(!game.play(x,y,player) && trys>0){
            x=0;
            trys--;
            System.out.println();
            System.out.print(trys+" trys remaining! \nTry again!: ");
            read();
        }
        // if the player failed 3 times, he loses the game
        if(trys==0){
            System.out.println("You played a wrong cell 3 times, YOU LOST.");
            System.out.println();
            System.out.println("!!!!!!!!!!!!!!      "+otherPlayer.name+" has won.      !!!!!!!!!!!!!!");
            winner=player;
            player.won();
            return true;
        }  

        nextTurn(game);
        
        // We verify if this player have a correct combination
        if(game.verify(player.turn)){
            System.out.println();
            System.out.println("!!!!!!!!!!!!!!      "+player.name+" has won.      !!!!!!!!!!!!!!");
            winner=player;
            player.won();
            return true;
        }
        return false;
    }

    public void swap(Hex game){
        /*
        we swap the first play
        */
        for(int i=0;i<game.row;i++){
            for(int j=0;j<game.col;j++){
                if(game.tab[i][j]==1){
                    game.tab[i][j]=2;
                    return;
                }
            }
        }
    }

   public String[] CPUPlay (Hex game,Player cpu) throws InterruptedException{
        int randomCol,randomRow;
        String coord;
        String[] details=new String[2]; 
        /* 
        details[0]: soit "won" si il a gagné sinon 0
        details[1]: coordonée de la piece posé
        */

        
        System.out.println("CPU IS THINKING...");
        int[][] empty= new int[game.freeCases][2];
        int pointerOfEmpty=0;

        //fill the table 'empty' with the plays possible
        for(int i=0;i<game.row;i++){
            for (int j=0;j<game.col;j++){
                if(game.tab[i][j]==0){
                    empty[pointerOfEmpty][0]=i+1;
                    empty[pointerOfEmpty][1]=j+1;
                    pointerOfEmpty++;
                }
            }
        }

        // pick one random play from the table 'empty' and play it
        int randomPick = ThreadLocalRandom.current().nextInt(0, game.freeCases);
        randomRow= empty[randomPick][0];
        randomCol= empty[randomPick][1];

        Thread.sleep(700);
        if(game.play(randomRow,(char)(randomCol+64), cpu)){
            coord=randomCol+" "+randomRow;
            details[1]=coord;
            System.out.println(coord);
        }

        nextTurn(game);

        if(game.verify(cpu.turn)){
            System.out.println();
            System.out.println("!!!!!!!!!!!!!!      "+cpu.name+" has won.      !!!!!!!!!!!!!!");
            details[0]="won";
        }
        return details;
   }

}
