package TournoiPadiflac;

import hexGame.Game;

public class Online {
    Channel channel_left,channel_right;


    public Online(String channel){
        channel_left=new Channel(channel+"_left");
        channel_left.connect();
        channel_right=new Channel(channel+"_right");
        channel_right.connect();
    }
    
    public void start() throws InterruptedException{
        Game game=new Game();
        game.launchOnline(11,11,channel_left,channel_right);
    }


    
}
