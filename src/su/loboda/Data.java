package su.loboda;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

// todo: сделать класс Data<T> что бы был LinkedHashMap<Integer, ArrayList<T>>
public class Data {

    LinkedHashMap<Integer, ArrayList<Float>> dataMap = new LinkedHashMap<>();

    public LinkedHashMap<Integer, ArrayList<Float>> getDataMap() {
        return dataMap;
    }

    public Data(String fileFoolPath){
        try(FileInputStream inputStream = new FileInputStream(fileFoolPath)) {
            int data = inputStream.read();

            int i = 0;
            // Создаем векторы из каждоый прочитаной строки файла
            while (data != -1){
                ArrayList<Float> vector = new ArrayList<>();
                // Обработка одной строки
                while (true){
                    if(data == -1){
                        break;
                    }
                    // Получен невалидный символ
                    if(data == 13){
                        data = inputStream.read();
                        continue;
                    }
                    // Достигнут конец строки
                    if(data == 10){
                        data = inputStream.read();
                        break;
                    }
                    // Кладем очередной элемент
                    vector.add((float)Character.getNumericValue((char)data));
                    data = inputStream.read();

                }
                // Кладем вектор в хранилище
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
     * Возвращает вектор - произведение вектора с индексом `index` из набора данных на число X
     * @param index - индекс вектора из набора данных этого инкапсулируемого этим классом
     * @param X - число на которое необходимо умножить вектор
     * @return вектор результат произведения
     */
    public ArrayList<Float> getVectorMultiplicationOnX(int index, float X){
        ArrayList<Float> dataVector = this.dataMap.get(index);
        return getVectorMultiplicationOnX(dataVector, X);
    }

    /**
     * Возвращает вектор - произведение вектора `dataVector` на число X
     * @param dataVector - вектор который необходимо умножить
     * @param X - число на которое необходимо умножить вектор
     * @return вектор результат произведения
     */
    public static ArrayList<Float> getVectorMultiplicationOnX(ArrayList<Float> dataVector, float X){
        ArrayList<Float> newVector = new ArrayList<>();
        for (int i = 0; i < dataVector.size(); i++){
            newVector.add(i, ((float)dataVector.get(i) * X));
        }
        return newVector;
    }

    /**
     * Возвращает вектор - сумму векторов переданых в качестве аргумента
     * @param vectors - массив векторов для сложения
     * @return - вектор результат сложения
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
