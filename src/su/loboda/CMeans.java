package su.loboda;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class CMeans {

    private final float m = 1.5f;
    private final float e = 0.001f;
    private final int maxIterations = 150;
    private final int clustersAmount = 2;

    private LinkedHashMap<Integer, ArrayList<Float>> membershipMatrix = new LinkedHashMap<>();
    private LinkedHashMap<Integer, ArrayList<Float>> centroids = new LinkedHashMap<>();
    private Data data;

    public CMeans(Data data){
        this.data = data;
    }

    /**
     * ���������� ��������� ������
     * @param dimensionsAmount - ���������� ��������� ������������
     * @return - ������ � ���������������� ������������
     */
    private ArrayList<Float> getRandomPoint(int dimensionsAmount){
        ArrayList<Float> vector = new ArrayList<>();
        for(int i = 0; i < dimensionsAmount; i++){
             vector.add(i, (float) Math.random());
         }
        return vector;
    }

    /**
     * ������� ��������� � ����������� �� ������� �������� ������� ��������������
     */
    private void recalculateCentroids(){
        for(int j = 0; j < centroids.size(); j++){
            float denominator = 0;
            ArrayList<Float>[] vectors = new ArrayList[membershipMatrix.size()];
            for(int i = 0; i < membershipMatrix.size(); i++){
                // ��������� �����������
                float u = (float)Math.pow(membershipMatrix.get(i).get(j), this.m);
                denominator += u;
                vectors[i] = this.data.getVectorMultiplicationOnX(i, u);
            }
            ArrayList<Float> numerator = Data.getVectorSum(vectors);
            centroids.put(j, (Data.getVectorMultiplicationOnX(numerator, (1 / denominator))));
        }
    }

    /**
     * ������������� �������� ������� ��������������
     */
    private void recalculateMembershipMatrix(){
        for (int i = 0; i < data.getDataMap().size(); i++){
            ArrayList<Float> membershipRow = new ArrayList<>();
            float uSum = 0;
            for(int j = 0; j < this.clustersAmount; j++){
                try {
                    float distance = (float)Metrics.getEuclideanDistance(this.data.getDataMap().get(i), this.centroids.get(j));
                    if(distance == 0f){
                        throw new Error("�������� ������ � ������. ��");
                    }
                    float u = (float)Math.pow(1/distance, (2/(m-1))); // ����� ������� �� 0 ���� �������� �������� � ������
                    uSum += u;
                    membershipRow.add(j, u);
                }catch (Exception e){
                    e.printStackTrace();
                    System.exit(0);
                }
            }

            // ����������� �������� u
            for (int k = 0; k < membershipRow.size(); k++){
                membershipRow.set(k, (membershipRow.get(k) / uSum));
            }
            membershipMatrix.put(i, membershipRow);
        }
    }

    /**
     * ����������� �������� ������� �������
     * @return - �������� ������� �������
     */
    private float calculateDecisionFunctionValue(){
        float sum = 0;
        for (int i = 0; i < membershipMatrix.size(); i++){
            for (int j = 0; j < membershipMatrix.get(i).size(); j++){
                try {
                    sum =+ membershipMatrix.get(i).get(j) * (float)Metrics.getEuclideanDistance(this.data.getDataMap().get(i), this.centroids.get(j));
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
        return sum;
    }

    public void go(){
        // �������� ��������� ��������� ������ ���������(���������)
        for(int i = 0; i < this.clustersAmount; i++){
            this.centroids.put(i, getRandomPoint(this.data.getDataMap().get(0).size()));

        }

        // ������������� ������� ��������������
        this.recalculateMembershipMatrix();
        int k = 0;
        float funcValue = 1;
        while (k < this.maxIterations){


            this.recalculateCentroids();
            this.recalculateMembershipMatrix();

            float newFuncValue = calculateDecisionFunctionValue();
            if(Math.abs(funcValue - newFuncValue) < this.e){
//                System.out.println("old " + funcValue + " | new " + newFuncValue + " | " + Math.abs(funcValue - newFuncValue));
                funcValue = newFuncValue;
                break;
            }
            funcValue = newFuncValue;
            k++;
        }

        System.out.println("Best func value: " + funcValue);
        System.out.println("Iterations amount : " + k);
        System.out.println("Centroids points : ");
        this.printCentroids();
        System.out.println("Membership matrix : ");
        this.printMembershipMatrix();

    }

    private void printMembershipMatrix(){
        System.out.print("| ");
        for(int j = 0; j < this.clustersAmount; j++){
            System.out.print("Cluster #" + j + " | ");
        }
        System.out.println("");

        for (int i = 0; i < this.membershipMatrix.size(); i++){
            System.out.print("| ");
            for (int j = 0; j < this.membershipMatrix.get(i).size(); j++){
                System.out.print(this.membershipMatrix.get(i).get(j) + " | ");
            }
            System.out.println("");
        }
    }

    private void printCentroids(){

        for (int j = 0; j < this.centroids.size(); j++){
            System.out.print("Centroid #" + j + " | ");
            for (int i = 0; i < this.centroids.get(j).size(); i++){
                System.out.print(this.centroids.get(j).get(i) + "|");
            }
            System.out.println("");
        }
    }
}
