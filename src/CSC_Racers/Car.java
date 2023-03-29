package CSC_Racers;

import java.util.Objects;

public class Car {

    private int maxSpeed;
    private int rowVelocity;
    private int colVelocity;
    private int row;
    private int col;
    private String idNumber;
    private boolean isWinner;

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public int getRowVelocity() {
        return rowVelocity;
    }

    public void setRowVelocity(int rowVelocity) {
        this.rowVelocity = rowVelocity;
    }

    public int getColVelocity() {
        return colVelocity;
    }

    public void setColVelocity(int colVelocity) {
        this.colVelocity = colVelocity;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public boolean GetWinner() {
        return isWinner;
    }

    public void SetWinner(boolean winner) {
        isWinner = winner;
    }

    public boolean isPathClear(Racetrack track, int y2, int x2)
    {
        double numberOfPoints = 10;
        double y1 = this.getRow();
        double x1 = this.getCol();
        double xSlope = (x1 - x2) / numberOfPoints;
        double ySlope = (y1 - y2) / numberOfPoints;

        //System.out.println("Car Coordinates: " + this.getRow() + "\t" + this.getCol());
        //System.out.println("X Slope: " + xSlope + "\tY Slope: " + ySlope );

        int ptsChecked = 0;
        int prevRow = -1; //Track current row position
        int prevCol = -1; //Track current column position

        while(ptsChecked < numberOfPoints)
        {
            x1 = x1 - xSlope;
            y1 = y1 - ySlope;
            int row = (int)Math.round(y1);
            int column = (int)Math.round(x1);

            //System.out.println("Track Coordinates: " + row + "\t" + column);
            //System.out.println("Track Position: " + track.GetTrack(row, column));
            if(track.GetTrack(row,column).equals("X") || row >= track.Height()-1 || column >= track.Width()-1)
            {
                if(!Objects.equals(this.getIdNumber(), "3"))
                {
                    this.carHitWall();

                    if(prevCol != -1 && prevRow != -1)
                    {
                        track.SetTrack(this.getRow(), this.getCol(),"T");
                        this.UpdateCoordinates(prevRow,prevCol);
                        this.setCarMove(track);
                        return false;

                    }
                }
                return false;

            }

            else if (track.GetTrack(row,column).equals("F"))
            {
                if(!this.GetWinner())
                {
                    System.out.println("CAR " + this.getIdNumber() + " WINS!");
                    this.SetWinner(true);
                }
                return true;
            }

            else if(!Objects.equals(track.GetTrack(row, column),"T") && !Objects.equals(track.GetTrack(row, column), this.getIdNumber()))
            {
                if(!Objects.equals(this.getIdNumber(), "3"))
                {
                    this.carHitCar();
                    if(prevCol != -1 && prevRow != -1)
                    {
                        track.SetTrack(this.getRow(), this.getCol(),"T");
                        this.UpdateCoordinates(prevRow,prevCol);
                        this.setCarMove(track);
                        return false;
                    }
                }
                return false;
            }
            ptsChecked++;
            if(track.GetTrack(row, column).equals("T"))
            {
                prevRow = row;
                prevCol = column;
            }

        }
        return true;
    }

    public void carHitWall()
    {
        if (maxSpeed > 1)
        {
            maxSpeed = maxSpeed - 1;
        }
        rowVelocity = 0;
        colVelocity = 0;
        System.out.println("Car " + idNumber + " has struck a wall");
    }

    public void carHitCar()
    {
        rowVelocity = 0;
        colVelocity = 0;
        System.out.println("Car " + idNumber + " has bumped another car.");
    }

    public void DisplayCarInfo()
    {
        System.out.print( "Car ID: " + idNumber + " Coordinates: " + row + "," + col);
        System.out.println( " Max Speed: " + maxSpeed + " Velocity: " + rowVelocity + "," + colVelocity);
    }

    /**
     *Display cars on track
     **/
    public void extractCar(Racetrack track)
    {
        int row = 0;
        int col = 0;
        int highWeight = 0;

        for(int i = 0; i < track.Height(); i++ )
        {
            for(int j = 0; j < track.Width(); j++)
            {

                if (highWeight < track.GetWeight(i,j) && track.GetWeight(i,j) != 1000000 && track.GetTrack(i,j).equals("T"))
                {
                    row = i;
                    col = j;
                    highWeight = track.GetWeight(i,j);
                }
            }
        }
        track.SetTrack(row,col,this.getIdNumber());
        this.UpdateCoordinates(row,col);
    }

    public void setCarMove(Racetrack track)
    {
        track.SetTrack(this.getRow(),this.getCol(), this.getIdNumber());
    }

    public void UpdateCoordinates(int row, int col)
    {
        //Add the current coordinate to the round move and velocity
        //ALWAYS update the coordinate before velocity as the move
        //accounts for the change in velocity
        this.setRow(row);
        this.setCol(col);
    }

    public void UpdateVelocity(int row, int col)
    {
        this.setRowVelocity(row);
        this.setColVelocity(col);

        //Check if velocity exceeds max-speed first
        if (rowVelocity > maxSpeed)
        {
            this.setRowVelocity(maxSpeed);
        }
        if (colVelocity > maxSpeed)
        {
            this.setColVelocity(maxSpeed);
        }
    }

    public void UpdateCarInfo(Racetrack track, int row, int col)
    {
        int rowTotal = this.getRow() + row;
        int colTotal = this.getCol() + col;

        //Check that car hasn't left the track again
        if (colTotal > track.Width() - 1)
        {
            colTotal = track.Width() - 1;
        }
        if (rowTotal > track.Height() - 1)
        {
            rowTotal = track.Height() - 1;
        }
        if (colTotal < 0)
        {
            colTotal = 0;
        }
        if (rowTotal < 0)
        {
            rowTotal = 0;
        }
        track.SetTrack(this.getRow(), this.getCol(),"T");
        this.UpdateCoordinates(rowTotal,colTotal);
        this.UpdateVelocity(this.getRowVelocity() + Math.abs(row),this.getColVelocity() + Math.abs(col));
        this.setCarMove(track);
    }
}
