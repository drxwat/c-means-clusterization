package su.loboda;

import java.util.ArrayList;

public class Metrics {

    private ArrayList<Float> first;
    private ArrayList<Float> second;

    public Metrics(ArrayList<Float> first, ArrayList<Float> second) throws Exception{
        if(first.size() != second.size()){
            throw new Exception("Not equal size");
        }
        this.first = first;
        this.second = second;
    }

    public double getEuclideanDistance(){
        double sum = 0;
        for (int i = 0; i < first.size(); i++){
            sum += Math.pow(((float)first.get(i)) - ((float)second.get(i)), 2);
        }
        return Math.sqrt(sum);
    }

    public static double getEuclideanDistance(ArrayList<Float> first, ArrayList<Float> second) throws Exception{
        return new Metrics(first, second).getEuclideanDistance();
    }
}
