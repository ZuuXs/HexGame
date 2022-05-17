package hexGame;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("                                                WELCOME TO THE HEX GAME");
        Scanner scanner = new Scanner( System.in );
        int playerCount=0;
        System.out.print( "Enter the Size of the table (greater than 4): " );
        int size = scanner.nextInt();
        while(size<5){
            System.out.print( "Greater than 4 or the game becomes unplayable : " );
            size = scanner.nextInt();                    
        }
        System.out.print( "How many people want to play? " );
        playerCount = scanner.nextInt();
        while(playerCount<1){
            System.out.print( "There must be at least 1 player! \nenter again: " );
            playerCount = scanner.nextInt();
        }
        scanner.nextLine();
        if(playerCount==1){
            System.out.print("Enter Your Name: ");
            Player player = new Player (scanner.nextLine(),1);
            Game game=new Game();
            game.launch1P(size,size,player);
        }else{
            Player[] listOfPlayers= new Player[playerCount];
            for(int i=0;i<playerCount;i++){
                System.out.print("Enter the name of player "+(i+1)+": ");
                listOfPlayers[i] = new Player (scanner.nextLine(),(i+1));
            }
            Tournament tournament=new Tournament(size,size,listOfPlayers);
            tournament.start();
            tournament.end();
        }
        scanner.close();
    }
}
