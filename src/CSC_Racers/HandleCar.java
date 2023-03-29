package CSC_Racers;

import java.util.Objects;

public class HandleCar extends Car {

    public HandleCar() {

        this.UpdateCoordinates(0,0);
        this.UpdateVelocity(0,0);
        this.setMaxSpeed(5);
        this.setIdNumber("3");
        this.SetWinner(false);
    }

    public void Move(Racetrack track)
    {
        int row = 0;
        int col = 0;
        int lowWeight = 9999;
        boolean pathClear = true;

        int rowIncrease = 0;
        int colIncrease = 0;

        if (this.getRowVelocity() < this.getMaxSpeed())
        {
            rowIncrease = 1;
        }
        if (this.getColVelocity() < this.getMaxSpeed())
        {
            colIncrease = 1;
        }

        for(int i = -(this.getRowVelocity() + rowIncrease); i <= this.getRowVelocity() + rowIncrease; i++)
        {
            for(int j = -(this.getColVelocity() + colIncrease); j <= this.getColVelocity() + colIncrease;  j++)
            {
                //Ensure only moves within the track are checked
                if(this.getRow() + i > 0 && this.getCol() + j > 0 && this.getRow() + i < track.Height()-1 && this.getCol() + j < track.Width()-1 )
                {
                    pathClear = this.isPathClear(track, this.getRow() + i, this.getCol() + j);
                }
                if(this.getRow() + i > 0 && this.getCol() + j > 0 && this.getRow() + i < track.Height()-1 && this.getCol() + j < track.Width()-1 )
                {
                    if(lowWeight > track.GetWeight(this.getRow() + i,this.getCol() + j) && pathClear && !Objects.equals(track.GetTrack(this.getRow() + i, this.getCol() + j), "1") && !Objects.equals(track.GetTrack(this.getRow() + i, this.getCol() + j), "3") && !Objects.equals(track.GetTrack(this.getRow() + i, this.getCol() + j), "2"))
                    {
                        lowWeight = track.GetWeight(this.getRow() + i,this.getCol() + j);
                        row = i;
                        col = j;
                    }
                }
            }
        }
        pathClear = this.isPathClear(track, this.getRow() + row,this.getCol() + col);
        if(pathClear)
        {
            this.UpdateCarInfo(track, row, col);
        }
    }

}
