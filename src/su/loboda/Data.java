package su.loboda;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

// todo: ������� ����� Data<T> ��� �� ��� LinkedHashMap<Integer, ArrayList<T>>
public class Data {

    LinkedHashMap<Integer, ArrayList<Float>> dataMap = new LinkedHashMap<>();

    public LinkedHashMap<Integer, ArrayList<Float>> getDataMap() {
        return dataMap;
    }

    public Data(String fileFoolPath){
        try(FileInputStream inputStream = new FileInputStream(fileFoolPath)) {
            int data = inputStream.read();

            int i = 0;
            // ������� ������� �� ������� ���������� ������ �����
            while (data != -1){
                ArrayList<Float> vector = new ArrayList<>();
                // ��������� ����� ������
                while (true){
                    if(data == -1){
                        break;
                    }
                    // ������� ���������� ������
                    if(data == 13){
                        data = inputStream.read();
                        continue;
                    }
                    // ��������� ����� ������
                    if(data == 10){
                        data = inputStream.read();
                        break;
                    }
                    // ������ ��������� �������
                    vector.add((float)Character.getNumericValue((char)data));
                    data = inputStream.read();

                }
                // ������ ������ � ���������
                dataMap.put(i, vector);
                i++;
            }

        } catch (FileNotFoundException e) {
            System.out.println("File " + fileFoolPath + "does not exists");
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * ���������� ������ - ������������ ������� � �������� `index` �� ������ ������ �� ����� X
     * @param index - ������ ������� �� ������ ������ ����� ���������������� ���� �������
     * @param X - ����� �� ������� ���������� �������� ������
     * @return ������ ��������� ������������
     */
    public ArrayList<Float> getVectorMultiplicationOnX(int index, float X){
        ArrayList<Float> dataVector = this.dataMap.get(index);
        return getVectorMultiplicationOnX(dataVector, X);
    }

    /**
     * ���������� ������ - ������������ ������� `dataVector` �� ����� X
     * @param dataVector - ������ ������� ���������� ��������
     * @param X - ����� �� ������� ���������� �������� ������
     * @return ������ ��������� ������������
     */
    public static ArrayList<Float> getVectorMultiplicationOnX(ArrayList<Float> dataVector, float X){
        ArrayList<Float> newVector = new ArrayList<>();
        for (int i = 0; i < dataVector.size(); i++){
            newVector.add(i, ((float)dataVector.get(i) * X));
        }
        return newVector;
    }

    /**
     * ���������� ������ - ����� �������� ��������� � �������� ���������
     * @param vectors - ������ �������� ��� ��������
     * @return - ������ ��������� ��������
     */
    public static ArrayList<Float> getVectorSum(ArrayList<Float>[] vectors){
        ArrayList<Float> result = (ArrayList<Float>)vectors[0].clone();
        for (int i = 1; i < vectors.length; i++){
            for (int j = 0; j < vectors[i].size(); j++){
                result.set(j, (result.get(j) + vectors[i].get(j)));
            }
        }
        return result;
    }
}
