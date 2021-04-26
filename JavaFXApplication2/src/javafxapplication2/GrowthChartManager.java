package javafxapplication2;
import java.util.ArrayList;
import java.util.Scanner;
import org.apache.commons.math3.distribution.NormalDistribution;

/**
 *
 * @author dianis
 */
public class GrowthChartManager {
   /**
    * Metodo para definir el sexo del paciente
    * @param sexo - entero que define el sexo del paciente 1-hombre , 2-mujer
    * @return verdadero si sexo es distinto de 1
    */
    public boolean sexo(int sexo){
        return sexo!=1;
    }
    /**
     * Metodo para determinar a que rango pertenece el paciente:
     * de 0-2 años o de 2 a 20 años
     * @param meses - edad del paciente en meses
     * @return 
     */
    public static boolean edad(int meses){
        return meses<24;
    }

    /**
     * Metodo para definir que tabla leer dependiendo al sexo del paciente
     * @param sex - entero que define el sexo del paciente
     * @return archivo - nombre del archivo a leer
     */
    public String elegirTablaPeso(int sex){
        String archivo = null;
        if(sexo(sex)){
            archivo = "/home/dianis/NetBeansProjects/Percentil/tablas_crecimiento/niños/wtageMujer.csv";
        }else{
            archivo = "/home/dianis/NetBeansProjects/Percentil/tablas_crecimiento/niños/wtageHombre.csv";
        }//checamos sexo
        return archivo;
    }
    
    /**
     * Metodo para definir que tabla leer dependiendo al sexo del paciente
     * @param sex - entero que define el sexo del paciente
     * @return archivo - nombre del archivo a leer
     */
    public String elegirTablaIMC(int sex){
        String archivo = null;
        if(sexo(sex)){
            archivo = "/home/dianis/NetBeansProjects/Percentil/tablas_crecimiento/niños/IMCMujeres.csv";
        }else{
            archivo = "/home/dianis/NetBeansProjects/Percentil/tablas_crecimiento/niños/IMCHombres.csv";
        }//checamos sexo
        return archivo;
    }

    
    /**
     * Metodo para definir que tabla leer dependiendo al sexo del paciente
     * @param sex - entero que define el sexo del paciente
     * @return archivo - nombre del archivo a leer
     */
    public String elegirTablaEstatura(int sex){
        String archivo = null;
        if(sexo(sex)){
            archivo = "/home/dianis/NetBeansProjects/Percentil/tablas_crecimiento/niños/statageMujer.csv";
        }else{
            archivo = "/home/dianis/NetBeansProjects/Percentil/tablas_crecimiento/niños/statageHombre.csv";
        }//checamos sexo
        return archivo;
    }

    /**
     * Metodo para definir que tabla leer dependiendo al sexo del paciente
     * @param sex - entero que define el sexo del paciente
     * @return archivo - nombre del archivo a leer
     */
    public String elegirlongitudxEdad(int sex){

        String archivo = null;
        if(sexo(sex)){
            archivo = "/home/dianis/NetBeansProjects/Percentil/tablas_crecimiento/bebés/longitudxEdadMujeres.csv";
        }else{
            archivo = "/home/dianis/NetBeansProjects/Percentil/tablas_crecimiento/bebés/longitudxEdadHombres.csv";
        }//checamos sexo
        return archivo;
    }

    
    /**
     * Metodo para definir que tabla leer dependiendo al sexo del paciente
     * @param sex - entero que define el sexo del paciente
     * @return archivo - nombre del archivo a leer
     */
    public String elegirlongitudxPeso(int sex){

        String archivo = null;
        if(sexo(sex)){
            archivo = "/home/dianis/NetBeansProjects/Percentil/tablas_crecimiento/bebés/longitudxPesoMujeres.csv";
        }else{
            archivo = "/home/dianis/NetBeansProjects/Percentil/tablas_crecimiento/bebés/longitudxPesoHombres.csv";
        }//checamos sexo
        return archivo;
    }
    
    /**
     * Metodo para definir que tabla leer dependiendo al sexo del paciente
     * @param sex - entero que define el sexo del paciente
     * @return archivo - nombre del archivo a leer
     */
    public String elegirPesoxEdad(int sex){

        String archivo = null;
        if(sexo(sex)){
            archivo = "/home/dianis/NetBeansProjects/Percentil/tablas_crecimiento/bebés/pesoxEdadMujeres.csv";
        }else{
            archivo = "/home/dianis/NetBeansProjects/Percentil/tablas_crecimiento/bebés/pesoxEdadHombres.csv";
        }//checamos sexo
        return archivo;
    }
    
    /**
     * Metodo para definir que tabla leer dependiendo al sexo del paciente
     * @param sex - entero que define el sexo del paciente
     * @return archivo - nombre del archivo a leer
     */
    public String elegirPerimetroCefalico(int sex){

        String archivo = null;
        if(sexo(sex)){
            archivo = "/home/dianis/NetBeansProjects/Percentil/tablas_crecimiento/bebés/perimetroCefalicoMujeres.csv";
        }else{
            archivo = "/home/dianis/NetBeansProjects/Percentil/tablas_crecimiento/bebés/perimetroCefalicoHombres.csv";
        }//checamos sexo
        return archivo;
    }

    /**
    * Metodo que calcula el ZSCORE a base del elementos L,M,S mediante la
    * siguiente formula z= (((X/M)**L) – 1)/LS , L!=0
    * @param arrli - arreglo con todos los valores del csv
    * @param X - parametro que puede ser el peso, estatura, perímetro cefalico
    * @param fila - parametro para elegirla fila correspondiente en el csv (normalmente la edad)
    * @return
    */
    public double Zscore(double X, double fila, ArrayList<String> arrli){
        double L =0;
        double M =0;
        double S =0;
        for(int i =1; i<arrli.size();i++){
            //fila actual csv
            ArrayList<String> values = getRecordFromLine(arrli.get(i));
            //Si la el param fila se encuentra en el csv, usamos los L,M,S ya calculados
            if(Double.parseDouble(values.get(0))==fila){
                L = Double.parseDouble(values.get(1));
                M = Double.parseDouble(values.get(2));
                S = Double.parseDouble(values.get(3));

                break;
               //Si el param no está , calculamos L,M,S por medio de interpolacion
            }else if(Double.parseDouble(values.get(0))==(menor5(fila)+0.5)||Double.parseDouble(values.get(0))== menor5(fila+1)){
                //fila anterior del csv
                ArrayList<String> anterior = getRecordFromLine(arrli.get(i-1));
                double x0 =0;
                double y0 =0;
                double x1 = 0;
                double y1 = 0;
                double x = fila;
                //asignamos los valores L,M,S
                for(int j =1; j<4;j++){
                    switch(j){
                        case 1:
                            x0 = Double.parseDouble(values.get(0));
                            y0 = Double.parseDouble(values.get(1));;
                            x1 = Double.parseDouble(anterior.get(0));
                            y1 = Double.parseDouble(anterior.get(1));
                            L = interpolacion(x0,y0,x1,y1,x);
                            break;
                        
                        case 2:
                            x0 = Double.parseDouble(values.get(0));
                            y0 = Double.parseDouble(values.get(2));;
                            x1 = Double.parseDouble(anterior.get(0));
                            y1 = Double.parseDouble(anterior.get(2));
                            M = interpolacion(x0,y0,x1,y1,x);
                            break;
                            
                        case 3:
                            x0 = Double.parseDouble(values.get(0));
                            y0 = Double.parseDouble(values.get(3));;
                            x1 = Double.parseDouble(anterior.get(0));
                            y1 = Double.parseDouble(anterior.get(3));
                            S = interpolacion(x0,y0,x1,y1,x);
                            break;
                    }
                }
                
                 break;
            }
            

        }

        return (Math.pow((X/M),L)-1)/(L*S);

    }
    /**
    * Metodo para calcular el percentil de un Zscore en especifico
    * Se calcula mediante la distribución normal estándar acumulativa
    * Redondeamos el valor a 2 decimales que nos indican el percentil
    * @param arrli - arreglo con todos los valores del csv
    * @param X - parametro que puede ser el peso, estatura, perímetro cefalico
    * @param fila - parametro para elegirla fila correspondiente en el csv (normalmente la edad)
    * @return
    */
    public double percentil (double X, double fila,ArrayList<String> arrli){
        NormalDistribution dist = new NormalDistribution();
        double zscore  = Zscore(X,fila,arrli);
        double val = Math.round(dist.cumulativeProbability(zscore)* 100);
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
    
    /**
     * Metodo para obtener la edad en años a partir de los meses
     * @param meses
     * @return double edad
     */
    public double convertirMeses(double meses){
        double edad= Math.round((meses/12)*100.0)/100.0;
        return edad;
        
    }

/**
 * Metodo para obtener una mayor precision en el zscore y percentil
 * utilizando la interpolacion lineal para calcular los L,M,S 
 * @param x0 - valor mayor al parametro x en el csv
 * @param y0 - valor L,M o S de la fila actual 
 * @param x1 - valor menor al parametro x en el csv
 * @param y1 - valor L,M o S de la fila anterior 
 * @param x - parametro actual que no se halla en el csv
 * @return double - que puede ser el L,M o S con una mayor precision
 */
public double interpolacion(double x0, double y0, double x1, double y1, double x){
    return y0+((y1-y0)/(x1-x0))*(x-x0);
}
public double menor5(double valor){
    String[] num = String.valueOf(valor).split("\\.");
    double decimal = Double.parseDouble(num[1]);
    if(decimal<5){
        return Double.parseDouble(num[0]);
    }
    return Double.parseDouble(num[0])+0.5;
}

}
