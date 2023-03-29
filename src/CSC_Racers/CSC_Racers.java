package CSC_Racers;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class CSC_Racers {

    public void startGame() throws FileNotFoundException {
        System.out.println( "   ___________ ______   ____             _            " );
        System.out.println( "  / ____/ ___// ____/  / __ \\____ ______(_)___  ____ _" );
        System.out.println( " / /    \\__ \\/ /      / /_/ / __ `/ ___/ / __ \\/ __ `/" );
        System.out.println( "/ /___ ___/ / /___   / _, _/ /_/ / /__/ / / / / /_/ / " );
        System.out.println( "\\____//____/\\____/  /_/ |_|\\__,_/\\___/_/_/ /_/\\__, /  " );
        System.out.println( "                                             /____/   " );

        playGame("track.txt");

        System.out.println("Thanks for Playing!" );

    }

    public static void playGame(String selection) throws FileNotFoundException {

        Racetrack track = new Racetrack();
        track.Read(selection);

        UserCar car1 = new UserCar();
        car1.extractCar(track);

        SpeedCar car2 = new SpeedCar();
        car2.extractCar(track);

        HandleCar car3 = new HandleCar();
        car3.extractCar(track);

        boolean winner;
        track.DisplayWeights();

        do
        {
            track.Display();
            car1.DisplayCarInfo();
            car2.DisplayCarInfo();
            car3.DisplayCarInfo();

            car1.Move(track);

            winner = car1.GetWinner();

            if(!winner)
            {
                car2.Move(track);
                winner = car2.GetWinner();
            }
            if(!winner)
            {
                car3.Move(track);
                winner = car3.GetWinner();
            }

        }while (!winner);
        track.Display();
    }
}
