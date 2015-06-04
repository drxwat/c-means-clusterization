package su.loboda;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

//        System.out.println("Type data file fool path (default /tmp/c-means-data): ");
        String filePath = "C:\\Users\\drxwat\\IdeaProjects\\c-means\\src\\su\\loboda\\data.txt";
        Data data = new Data(filePath);

        CMeans algorithm = new CMeans(data);
        algorithm.go();

//        for( int i = 1; i < data.getDataMap().size(); i++){
//            try {
//                System.out.println(Metrics.getEuclideanDistance(data.getDataMap().get(0), data.getDataMap().get(i)));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }



//	    System.out.println("Data processed and validated successfully!");
//
//	    System.out.println("Choose metric (default Euclidean distance): 1 - Euclidean distance, 2 - Another metric");
//
//	    System.out.println("Choose algorithm (default c-means): 1 - c-means, 2 - k-means");
//
//	    System.out.println("Please set constants (depends on algorithm) ...");
    }
}
