package CSC_Racers;

import java.util.Scanner;

public class UserCar extends Car {

    public UserCar() {
        this.UpdateCoordinates(0,0);
        this.UpdateVelocity(0,0);
        this.setMaxSpeed(5);
        this.setIdNumber("1");
        this.SetWinner(false);
    }

    public void Move(Racetrack track) {
        int row;
        int col;
        boolean pathClear = true;
        boolean validMove = false;
        Scanner input = new Scanner(System.in);

        do {
            System.out.println("Enter row coordinate: ");
            row = input.nextInt();
            System.out.println("Enter column coordinate: ");
            col = input.nextInt();
            //System.out.println("Inputs: " + row + "\t" + col);
            if (row <= this.getRowVelocity() + 1 && col <= this.getColVelocity() + 1 && Math.abs(row) <= 5 && Math.abs(col) <= 5) {
                validMove = true;
            } else {
                System.out.println("Invalid move.  Try again");
            }
        }while (!validMove);

        pathClear = this.isPathClear(track, this.getRow() + row, this.getCol() + col);
        //System.out.println("Path: " + pathClear);

        if(pathClear)
        {
            this.UpdateCarInfo(track, row, col);
        }
    }
}
