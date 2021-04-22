/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package javafxapplication2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.chart.LineChart;

/**
*
* @author dianis
*/
public class HoveredThresholdNode extends Pane {

    /**
    *
    * @param priorValue
    * @param value
    */
    HoveredThresholdNode(double priorValue, double value,LineChart chart,String nombre) {
        setPrefSize(15, 15);
        final Label label = createDataThresholdLabel(priorValue, value,nombre);
        setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getClickCount()==1){

                    getChildren().setAll(label);
                    setCursor(Cursor.NONE);
                    toFront();

                }else if(mouseEvent.getClickCount()==2){
                    getChildren().clear();
                    setCursor(Cursor.CROSSHAIR);
                }

            }
        });

    }

    /**
    *
    * @param priorValue
    * @param value
    * @return
    */
    private Label createDataThresholdLabel(double priorValue, double value, String nombre) {
        final Label label = elegirEtiqueta(priorValue,value,nombre);
        label.getStyleClass().addAll("default-color0", "chart-line-symbol", "chart-series-line");
        label.setStyle("-fx-font-size: 12; -fx-font-weight: bold;");

        if (priorValue == 0) {
            label.setTextFill(Color.DARKGRAY);
        } else if (value > priorValue) {
            label.setTextFill(Color.FORESTGREEN);
        } else {
            label.setTextFill(Color.FIREBRICK);
        }

        label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        return label;
    }

    public static XYChart.Series lineaGuiaX(double priorValue, double value, String name){
        XYChart.Series series = new XYChart.Series();
        series.getData().add(new XYChart.Data(priorValue,0));
        series.getData().add(new XYChart.Data(priorValue,value));
        series.setName(name);
        return series;
    }

    public static XYChart.Series lineaGuiaY(double priorValue, double value, String name){
        XYChart.Series series = new XYChart.Series();
        series.getData().add(new XYChart.Data(0,value));
        series.getData().add(new XYChart.Data(priorValue,value));
        series.setName(name);
        return series;
    }

    public static void borrarPuntos(XYChart.Series<Double, Double> ser){
        for (XYChart.Data<Double, Double> data : ser.getData()) {
            Node stackPane = data.getNode();
            stackPane.setVisible(false);
        }
    }


    public static void eliminarLineasGuia(LineChart chart, XYChart.Series<Double, Double> series8, XYChart.Series<Double, Double> series9){
        chart.getData().remove(series8);
        chart.getData().remove(series9);


    }

    public  static ObservableList<XYChart.Data<Double, Double>> plot(double[] y, LineChart chart, String nombre) {
        ObservableList<XYChart.Data<Double, Double>> dataset = FXCollections.observableArrayList();
        int i = 0;

        while (i < y.length) {
            final XYChart.Data<Double, Double> data = new XYChart.Data<>(y[i], y[i+1]);
            data.setNode(
            new HoveredThresholdNode(y[i],
            y[i+1],
            chart, nombre)
            );

            dataset.add(data);
            i+=2;
        }

        return dataset;
    }


    public String[] valoresEdad(double edad){
        String etiquetaEdad =Double.toString(edad);
        String [] valores = etiquetaEdad.split("\\.");
        int meses = 0;
        if(valores[1].equals("5")){
              meses = (int) Math.round((Double.parseDouble(valores[1])/10)*12); //solucion bug edad con 6 meses
        }else{
          meses = (int) Math.round((Double.parseDouble(valores[1])/100)*12);  
        }
        valores[1] = Integer.toString(meses);
        return valores;
    }
    
    private Label elegirEtiqueta(double priorValue, double value, String nombre){
        if(nombre.equals("Longitud para la Edad Menores 2 Años")||nombre.equals("Peso para la Edad Menores 2 Años")||
                nombre.equals("Perímetro Cefálico (centímetros)")){
            priorValue=Math.round((priorValue/12)*100.0)/100.0;
        }
        String [] values = valoresEdad(priorValue);
        Label label = null;
        if(nombre.equals("Longitud para la Edad Menores 2 Años")||nombre.equals("Talla para la edad")){
            label = new Label("Edad:" +values[0]+" años " +"\n"+values[1].substring(0,1)+" meses" +"\n"+"Talla:"+value+ "cm");
            System.out.println(values[1]);
            
        }else if(nombre.equals("Peso para la Edad Menores 2 Años")||nombre.equals("Peso para la edad")){
            
            label = new Label("Edad:" +values[0]+" años " +"\n"+values[1]+" meses" +"\n"+"Peso:"+value+ "kg");
            
        }else if(nombre.equals("Perímetro Cefálico (centímetros)")){
            label = new Label("Edad:" +values[0]+" años " +"\n"+values[1].substring(0,1)+" meses" +"\n"+"Perímetro:"+value+ "cm");
            
        }else if(nombre.equals("Peso para la Longitud Menores 2 Años")){
            label = new Label("Longitud:" +priorValue+"cm"+"\n"+"Peso:"+value+ "kg");
            
            
        }else if(nombre.equals("Índice de Masa Corporal")){
            label = new Label("Edad:" +values[0]+" años " +"\n"+values[1].substring(0,1)+" meses" +"\n"+"IMC:"+value);
            
        }
        return label;
    }
}
