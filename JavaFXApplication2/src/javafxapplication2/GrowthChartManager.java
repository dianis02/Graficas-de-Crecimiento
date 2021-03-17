package javafxapplication2;
import org.apache.commons.math3.distribution.NormalDistribution;
import java.util.ArrayList;
import java.util.Scanner;

public class GrowthChartManager {
    
    /**
     * Metodo para definir el sexo del paciente
     * @param sexo - entero que define el sexo del paciente 1-hombre , 2-mujer 
     * @return verdadero si sexo es distinto de 1
     */
    public boolean sexo(int sexo){
        return sexo!=1;
    }
    
    public boolean edad(int meses){
        return meses<24;
    }
    
    public String elegirTablaPeso(int sex){
        String archivo = null;
        if(sexo(sex)){
            archivo = "/home/dianis/NetBeansProjects/Graficas-de-Crecimiento/JavaFXApplication2/wtageMujer.csv";
        }else{
           archivo = "/home/dianis/NetBeansProjects/Graficas-de-Crecimiento/JavaFXApplication2/wtageHombre.csv";
        }//checamos sexo
        return archivo;
    }
    
    
    public String elegirTablaEstatura(int sex){
        String archivo = null;
        if(sexo(sex)){
            archivo = "/home/dianis/NetBeansProjects/Graficas-de-Crecimiento/JavaFXApplication2/statageMujer.csv";
        }else{
           archivo = "/home/dianis/NetBeansProjects/Graficas-de-Crecimiento/JavaFXApplication2/statageHombre.csv";
        }//checamos sexo
        return archivo;
    }
    
    public String elegirTablaBebes(int sex){
        String archivo = null;
        if(sexo(sex)){
            archivo = "/home/dianis/NetBeansProjects/JavaFXApplication2/longitudMujer.csv";
        }else{
           archivo = "/home/dianis/NetBeansProjects/JavaFXApplication2/longitudHombre.csv";
        }//checamos sexo
        return archivo;
    }

    /**
    * Metodo que calcula el ZSCORE a base del elementos L,M,S mediante la
    * siguiente formula z= (((X/M)**L) – 1)/LS , L!=0
    * @param file - nombre del archivo a leer
    * @param X - peso del paciente
    * @param age - edad del paciente
    * @return
    */
    public double Zscore(double X, double age, ArrayList<String> arrli){
        double L =0;
        double M =0;
        double S =0;
       // ReadExcelFile reader = new ReadExcelFile();
        //ArrayList<String> arrli = reader.leer(file);
        for(int i =1; i<arrli.size();i++){
            ArrayList<String> values = getRecordFromLine(arrli.get(i));
            //Hay meses como 30, 30.5 y en algunos casos solo uno de ellos en el cvs
            //Por ello se checan ambos casos
            if(Double.parseDouble(values.get(0))==age ||Double.parseDouble(values.get(0))==(age+0.5) ){
                L = Double.parseDouble(values.get(1));
                M = Double.parseDouble(values.get(2));
                S = Double.parseDouble(values.get(3));
              
                break;
    
            }
            
        }
        System.out.println(L+","+M+","+S);
        return (Math.pow((X/M),L)-1)/(L*S);

    }
    /**
    * Metodo para calcular el percentil de un Zscore en especifico
    * Se calcula mediante la distribución normal estándar acumulativa
    * Redondeamos el valor a 2 decimales que nos indican el percentil
    * @param file
    * @param X
    * @param age
    * @return
    */
    public double percentil (double X, double age,ArrayList<String> arrli){
        NormalDistribution dist = new NormalDistribution();
        double zscore  = Zscore(X,age,arrli);
        double val = Math.floor(dist.cumulativeProbability(zscore)* 100) / 100;
        return val;
    }

      /**
     * Metodo auxiliar para leer los valores por fila del csv
     * @param line - String que es toda una fila del csv
     * @return arreglo de Strings con todos los elementos de la linea recibida
     */
    public ArrayList<String> getRecordFromLine(String line) {
        ArrayList<String> values = new ArrayList<String>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(",");
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }



}
