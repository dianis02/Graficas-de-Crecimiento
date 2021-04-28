/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package javafxapplication2;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;

/**
*
* @author dianis
*/
public class IMC {
    NumberAxis xAxis = new NumberAxis(1,21,1); //ejes
    NumberAxis yAxis = new NumberAxis(13,35,2);
    String nombre = "Índice de Masa Corporal";

    public LineChart Grafica(Pane root,LineChart chart,double[] crecimiento,int sexo){
        //datos del paciente provisionales, se debe jalar de la base
        double[] arr ={24,14.3,30,14.7,43,15.8,60,15.9,77,16.4,90,17,217,30};
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

        archivo = manager.elegirTablaIMC(sexo);
        System.out.println(archivo);



        ArrayList<String> arrli = reader.leer(archivo);


        //Creamos las series de todos los percentiles estandar
        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();
        XYChart.Series series3 = new XYChart.Series();
        XYChart.Series series4 = new XYChart.Series();
        XYChart.Series series5 = new XYChart.Series();
        XYChart.Series series6 = new XYChart.Series();
        XYChart.Series series7 = new XYChart.Series();
        XYChart.Series series10 = new XYChart.Series();
        XYChart.Series series11 = new XYChart.Series();

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
                    case 11:
                    series10.getData().add(new XYChart.Data(manager.convertirMeses(Double.parseDouble(values.get(0))),Double.parseDouble(values.get(j))));
                    break;
                    case 12:
                    series11.getData().add(new XYChart.Data(manager.convertirMeses(Double.parseDouble(values.get(0))),Double.parseDouble(values.get(j))));
                    break;
                }
            }
        }

        series1.setName("percentil3");
        series2.setName("percentil10");
        series3.setName("percentil25");
        series4.setName("percentil50");
        series5.setName("percentil75");
        series6.setName("percentil85");
        series7.setName("percentil90");
        series10.setName("percentil95");
        series11.setName("percentil97");

        //Estilo ejes
        xAxis.setLabel("Edad");
        yAxis.setLabel("IMC");

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
        chart.getData().addAll(series1, series2, series3,series4,series5,series6,series7,series10,series11,series);
        //estilo grafica
        chart.setAnimated(false);
        chart.setLegendSide(Side.RIGHT);
        chart.setCursor(Cursor.CROSSHAIR);
        chart.setPrefSize(1000, 700);
        chart.setTitle(nombre);

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


        //Estilo linea
        Node line = series.getNode().lookup(".chart-series-line");
        Node line1 = series1.getNode().lookup(".chart-series-line");
        Node line2 = series2.getNode().lookup(".chart-series-line");
        Node line3 = series3.getNode().lookup(".chart-series-line");
        Node line4 = series4.getNode().lookup(".chart-series-line");
        Node line5 = series5.getNode().lookup(".chart-series-line");
        Node line6 = series6.getNode().lookup(".chart-series-line");
        Node line7 = series7.getNode().lookup(".chart-series-line");

        Node line10 = series10.getNode().lookup(".chart-series-line");
        Node line11 = series11.getNode().lookup(".chart-series-line");



        if(manager.sexo(sexo)){
            line.setStyle("-fx-stroke: #FF00FF;");
        }else{
            line.setStyle("-fx-stroke: #0000FF;");
        }

        //rojo=#FF0000;
        //amarillo= #FFFF00"
        //verde= #00FF00;
        line1.setStyle("-fx-stroke: #FF0000;");//rojo
        line2.setStyle("-fx-stroke: #FF0000;");
        line3.setStyle("-fx-stroke: #FFFF00;");
        line4.setStyle("-fx-stroke: #00FF00;");//verde
        line5.setStyle("-fx-stroke: #00FF00;");
        line6.setStyle("-fx-stroke: #FFFF00;");
        line7.setStyle("-fx-stroke: #FFFF00");//amarillo
        line10.setStyle("-fx-stroke: #FF0000;");
        line11.setStyle("-fx-stroke: #FF0000;");


        //calculo zscore y percentil
        //System.out.println(manager.Zscore(16.80719583, 30.5,arrli)+","+ manager.percentil(16.80719583, 30.5,arrli));
        //System.out.println(manager.Zscore(84.51558277, 30.5,arrli)+","+ manager.percentil(84.51558277, 30.5,arrli));
        //System.out.println(manager.Zscore(2.4, 45,arrli)+","+ manager.percentil(2.4, 45,arrli));

        //new ZoomManager(root, chart, series);

        return chart;
    }

}
