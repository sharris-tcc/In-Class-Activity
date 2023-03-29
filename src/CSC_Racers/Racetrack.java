package CSC_Racers;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Racetrack {

    private ArrayList<ArrayList<String>> track;
    private ArrayList<ArrayList<Integer>> weights;

    public String GetTrack(int x, int y){
        return track.get(x).get(y);
    }

    public void SetTrack(int x, int y, String value){
        track.get(x).set(y,value);
    }

    public Integer GetWeight(int x, int y){
        return weights.get(x).get(y);
    }

    public int Height() {
        return track.size();
    }

    public int Width() {
        return  track.get(0).size();
    }

    public void Read(String trackName) throws FileNotFoundException {
        track = new ArrayList<>();

        Path path = Paths.get(trackName);

        File file = new File(
                path.toAbsolutePath().toString().replace("\\", "\\\\"));
        //System.out.println(file);
        Scanner input = new Scanner(file);
        String str;

        while (input.hasNextLine()) {
            ArrayList<String> row = new ArrayList<>();
            str = input.nextLine();


            // Copying character by character into array
            // using for each loop
            for (int i = 0; i < str.length(); i++) {
                row.add(String.valueOf(str.charAt(i)));
            }
            track.add(row);
        }
        this.initWeights();
    }

    public void Display() {
        for (ArrayList<String> row : track) {
            for (String col : row) {
                if(col.equals("T"))
                {
                    System.out.print("\t");
                }
                else
                {
                    System.out.print(col + "\t");
                }
            }
            System.out.print("\n");
        }
        System.out.println("Track Dimensions: " + this.Height() + "x" + this.Width());

    }

    public void initWeights() {
        weights = new ArrayList<>();

        for(int i = 0; i < this.Height(); i++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < this.Width(); j++) {
                if (track.get(i).get(j).equals("F")) {
                    row.add(0);
                }
                if (track.get(i).get(j).equals("X")) {
                    row.add(9999);
                }
                if (track.get(i).get(j).equals("T")) {
                    row.add(-1);
                }
            }
            weights.add(row);
        }
        int currentWeight = 0;
        while (true) {
            boolean weightsComplete = true;
            for (int x = 0; x < this.Height(); x++) {
                for (int y = 0; y < this.Width(); y++) {
                    if (weights.get(x).get(y).equals(currentWeight)) {
                        weightsComplete = false;
                        for (int k = -1; k < 2; k++) {
                            for (int l = -1; l < 2; l++) {
                                if (weights.get(x + k).get(y + l) < 0) {
                                    weights.get(x + k).set(y + l, currentWeight + 1);
                                }

                            }
                        }
                    }
                }
            }
            currentWeight++;
            if (weightsComplete) {
                break;
            }
        }
    }
    public void DisplayWeights() {
        System.out.println("=====================TRACK WEIGHTS======================");
        for (ArrayList<Integer> row : weights) {
            for (Integer col : row) {
                if (col == 9999) {
                    System.out.print("X\t");
                } else {
                    System.out.print(col + "\t");
                }
            }
            System.out.print("\n");
        }
        System.out.println("=====================TRACK WEIGHTS======================");
    }
}


