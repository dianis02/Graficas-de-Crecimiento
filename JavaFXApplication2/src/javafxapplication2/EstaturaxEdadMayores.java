/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package javafxapplication2;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;
import java.util.*;
import javafx.scene.Node;
import javafx.collections.*;
import javafx.scene.*;
import javafx.geometry.Side;
/**
*
* @author dianis
*/
public class EstaturaxEdadMayores {
    NumberAxis xAxis = new NumberAxis(1,20,1); //ejes
    NumberAxis yAxis = new NumberAxis(70,200,5);
    String nombre = "Talla para la edad";
    PropiedadesGrafica editor = new PropiedadesGrafica();

    public LineChart Grafica(Pane root,LineChart chart,double[] crecimiento,String sexo){
        //datos del paciente provisionales, se debe jalar de la base
        double[] arr ={24,81,30,88,43,95,60,105,77,114,90,120};
        //Manejador datos csv
        GrowthChartManager manager = new GrowthChartManager();

        //convertimos los meses en años
        for(int i = 0; i<arr.length; i+=2){
            arr[i]=manager.convertirMeses(arr[i]);
        }
        //lector del csv
        ReadExcelFile reader = new ReadExcelFile();
        //arreglo con toda la información de csv
        String archivo = null;

        archivo = manager.elegirTablaEstatura(sexo);



        ArrayList<String> arrli = reader.leer(archivo);


        //Creamos las series de todos los percentiles estandar
        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();
        XYChart.Series series3 = new XYChart.Series();
        XYChart.Series series4 = new XYChart.Series();
        XYChart.Series series5 = new XYChart.Series();
        XYChart.Series series6 = new XYChart.Series();
        XYChart.Series series7 = new XYChart.Series();

        //Llenamos cada serie
        for(int i =1; i<arrli.size();i++){
            ArrayList<String> values = manager.getRecordFromLine(arrli.get(i));
            for(int j=0; j<values.size(); j++){
                switch(j){
                    case 4:
                    series1.getData().add(new XYChart.Data(manager.convertirMeses(Double.parseDouble(values.get(0))),Double.parseDouble(values.get(j))));
                    break;
                    case 5:
                    series2.getData().add(new XYChart.Data(manager.convertirMeses(Double.parseDouble(values.get(0))),Double.parseDouble(values.get(j))));
                    break;
                    case 6:
                    series3.getData().add(new XYChart.Data(manager.convertirMeses(Double.parseDouble(values.get(0))),Double.parseDouble(values.get(j))));
                    break;
                    case 7:
                    series4.getData().add(new XYChart.Data(manager.convertirMeses(Double.parseDouble(values.get(0))),Double.parseDouble(values.get(j))));
                    break;
                    case 8:
                    series5.getData().add(new XYChart.Data(manager.convertirMeses(Double.parseDouble(values.get(0))),Double.parseDouble(values.get(j))));
                    break;
                    case 9:
                    series6.getData().add(new XYChart.Data(manager.convertirMeses(Double.parseDouble(values.get(0))),Double.parseDouble(values.get(j))));
                    break;
                    case 10:
                    series7.getData().add(new XYChart.Data(manager.convertirMeses(Double.parseDouble(values.get(0))),Double.parseDouble(values.get(j))));
                    break;
                }
            }
        }

        series1.setName("percentil3");
        series2.setName("percentil10");
        series3.setName("percentil25");
        series4.setName("percentil50");
        series5.setName("percentil75");
        series6.setName("percentil90");
        series7.setName("percentil97");

        //Estilo ejes
        xAxis.setLabel("Edad");
        yAxis.setLabel("Estatura");

        //Creamos la grafica con el crecimiento del paciente

        chart = new LineChart(
        xAxis, yAxis,
        FXCollections.observableArrayList(
        new XYChart.Series(
        "crecimiento",
        FXCollections.observableArrayList(
        HoveredThresholdNode.plot(arr,chart,nombre)
        )
        )
        )
        );



        //lo obtenemos y lo eliminamos para añadirlo al final
        //porque lo dibuja atras de lo contrario
        XYChart.Series series = (XYChart.Series)chart.getData().get(0);

        chart.getData().remove(0,1);
        chart.getData().addAll(series1, series2, series3,series4,series5,series6,series7,series);
        
        //lineas con puntos o solo la línea
        for (XYChart.Series<Double, Double> ser :(ObservableList<XYChart.Series<Double, Double>> )chart.getData()) {
            if (ser.getName().equals("crecimiento")) //if Name is "blue" then continue
            continue;

            //for all series, take date, each data has Node (symbol) for representing point
            for (XYChart.Data<Double, Double> data : ser.getData()) {
                Node stackPane = data.getNode();
                stackPane.setVisible(false);
            }
        }


        editor.estiloMayores(chart, sexo,"Talla para la edad");
        return chart;
    }

}
