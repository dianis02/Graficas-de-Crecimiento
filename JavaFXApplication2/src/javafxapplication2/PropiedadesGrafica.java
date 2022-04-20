/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication2;

import java.util.ArrayList;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;
import static javafxapplication2.HoveredThresholdNode.borrarPuntos;

/**
 *
 * @author dianis
 */
public class PropiedadesGrafica {
    int contador = 0;
    
    public void estiloIMC(LineChart chart, String sexo, String nombre){
        setChart(chart,nombre);
        setColorLineasIMC(chart,sexo);
       // setLegendLineasGuia(chart,sexo);
        setLegendIMC(chart, sexo);
        lineasGuia(chart,sexo);
    }
    public void estiloMayores(LineChart chart, String sexo, String nombre){
        setChart(chart,nombre);
        setColorLineasMayores(chart,sexo);
        setLegendMayores(chart,sexo);
        lineasGuia(chart,sexo);
    }
    
    public void estiloMenores(LineChart chart, String sexo, String nombre){
        setChart(chart,nombre);
        setColorLineasMenores(chart,sexo);
        setLegendMenores(chart,sexo);
        lineasGuia(chart,sexo);
    }
    private void setChart(LineChart chart, String nombre){
        chart.setAnimated(false);
        chart.setLegendSide(Side.RIGHT);
        chart.setCursor(Cursor.CROSSHAIR);
        chart.setPrefSize(1000, 700);
        chart.setTitle(nombre);
    }
    
    private void setLegendMayores(LineChart chart, String sexo) {
        Thread t = new Thread(() -> {           //color linea crecimiento          
            try {
                Thread.sleep(40);
            } catch (InterruptedException ex) {
                System.out.print("excepcion ocurrida");
            }
            Platform.runLater(() -> {
                for (int i = 0; i < 10; i++) {
                    String color =  "#0000FF"; //azul  
                    if (i == 0 || i == 6) {
                        color = "#FF0000"; //rojo
                    } else if (i == 1 || i == 5) {
                        color = "#FFFF00"; //amarillo
                    } else if (i == 7 && sexo.equals("mujer")) {
                        color = "#FF00FF"; //rosa
                    } else if (i == 7 && sexo.equals("hombre")) {
                        color = "#0000FF"; //azul  
                    }else if (i==8 || i==9){  
                       color = "TRANSPARENT" ;
                    } else {
                        color = "#00FF00"; //verde
                    }
                     Node ns = chart.lookup(".series" + i + ".chart-legend-item-symbol");
                    if(ns!= null){
                    ns.setStyle("-fx-background-color: " + color + ";");
                    }
                }

            });
        });
        t.start();

    }
    
     private void setLegendIMC(LineChart chart, String sexo) {
        Thread t = new Thread(() -> {           //color linea crecimiento          
            try {
                Thread.sleep(40);
            } catch (InterruptedException ex) {
                System.out.print("excepcion ocurrida");
            }
            Platform.runLater(() -> {
                for (int i = 0; i < 12; i++) {
            String color = null; //azul  
            if (i == 0 || i == 8) {
                color = "#FF0000"; //rojo
            } else if (i == 1 || i == 2 || i == 6 || i == 7) {
                color = "#FFFF00"; //amarillo
            } else if (i == 9 && sexo.equals("mujer")) {
                color = "#FF00FF"; //rosa
            } else if (i == 9 && sexo.equals("hombre")) {
                color =  "#0000FF"; //azul  
             }else if (i==10 || i==11){  
                color = "TRANSPARENT" ;
            } else {
                color = "#00FF00"; //verde
            }   
                    Node ns = chart.lookup(".series" + i + ".chart-legend-item-symbol");
                    if(ns!= null){
                    ns.setStyle("-fx-background-color: " + color + ";");
                    }
                   
                }

            });
        });
        t.start();

    }
    
     
     private void setLegendLineasGuia(LineChart chart, String sexo){
         
         Thread t = new Thread(() -> {           //color linea crecimiento          
            try {
                Thread.sleep(40);
            } catch (InterruptedException ex) {
                System.out.print("excepcion ocurrida");
            }
            Platform.runLater(() -> {
                for (int i = 0; i < 12; i++) {
            String color = null; //azul  
            if (i == 0 || i == 8) {
                color = "#FF0000"; //rojo
            } else if (i == 1 || i == 2 || i == 6 || i == 7) {
                color = "#FFFF00"; //amarillo
            } else if (i == 9 && sexo.equals("mujer")) {
                color = "#FF00FF"; //rosa
            } else if (i == 9 && sexo.equals("hombre")) {
                color = "#FF00FF"; //azul
            }else if (i==10 || i==11){  
                color = "TRANSPARENT" ;
            } else {
                color = "#00FF00"; //verde
            }   
                  
                    Node ns = chart.lookup(".series" + i + ".chart-legend-item-symbol");
                    if(ns!= null){
                    ns.setStyle("-fx-background-color: " + color + ";");
                    }
                  
                }

            });
        });
        t.start();
         
     }
      private void setLegendMenores(LineChart chart, String sexo) {
        Thread t = new Thread(() -> {           //color linea crecimiento          
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                System.out.print("excepcion ocurrida");
            }
            Platform.runLater(() -> {
                for (int i = 0; i < 8; i++) {
            String color = null; //azul  
            if (i == 0 || i == 4) {
                color = "#FF0000"; //rojo
            } else if (i == 1 || i == 3) {
                color = "#FFFF00"; //amarillo
            } else if (i == 5 && sexo.equals("mujer")) {
                color = "#FF00FF"; //rosa
            } else if (i == 5 && sexo.equals("hombre")) {
                color = "#0000FF"; //azul
            }else if (i==6 || i==7){  
                color = "TRANSPARENT" ;
            } else {
                color = "#00FF00"; //verde
            }
                    Node ns = chart.lookup(".series" + i + ".chart-legend-item-symbol");
                    if(ns!= null){
                    ns.setStyle("-fx-background-color: " + color + ";");
                    }
                }

            });
        });
        t.start();
    }

      
       private void setColorLineasMenores(LineChart chart, String sexo) {
        
                for (int i = 0; i < 6; i++) {
            String color = null; //azul  
            if (i == 0 || i == 4) {
                color = "#FF0000"; //rojo
            } else if (i == 1 || i == 3) {
                color = "#FFFF00"; //amarillo
            } else if (i == 5 && sexo.equals("mujer")) {
                color = "#FF00FF"; //rosa
            } else if (i == 5 && sexo.equals("hombre")) {
                color = "#0000FF"; //azul
            } else {
                color = "#00FF00"; //verde
            }
                    Node ns = chart.lookup(".series" + i + ".chart-series-line");
                    ns.setStyle("-fx-stroke: " + color + ";");
                }

          
    }
      
     private void setColorLineasIMC(LineChart chart, String sexo) {

       for (int i = 0; i < 10; i++) {
            String color = null; //azul  
            if (i == 0 || i == 8) {
                color = "#FF0000"; //rojo
            } else if (i == 1 || i == 2 || i == 6 || i == 7) {
                color = "#FFFF00"; //amarillo
            } else if (i == 9 && sexo.equals("mujer")) {
                color = "#FF00FF"; //rosa
            } else if (i == 9 && sexo.equals("hombre")) {
                color = "#0000FF"; //azul 
            } else {
                color = "#00FF00"; //verde
            }
            Node ns = chart.lookup(".series" + i + ".chart-series-line");
            ns.setStyle("-fx-stroke: " + color + ";");

    }
     }
     
     private void setColorLineasMayores(LineChart chart, String sexo) {
         for (int i = 0; i < 8; i++) {
                    String color = null; //azul  
                    if (i == 0 || i == 6) {
                        color = "#FF0000"; //rojo
                    } else if (i == 1 || i == 5) {
                        color = "#FFFF00"; //amarillo
                    } else if (i == 7 && sexo.equals("mujer")) {
                        color = "#FF00FF"; //rosa
                    } else if (i == 7 && sexo.equals("hombre")) {
                        color = "#0000FF"; //azul 
                    } else {
                        color = "#00FF00"; //verde
                    }
                    Node ns = chart.lookup(".series" + i + ".chart-series-line");
                     ns.setStyle("-fx-stroke: " + color + ";");
                }

         
    }
     
     private void lineasGuia(LineChart chart,String sexo) {
         contador = 0;
        for (XYChart.Series<Double, Double> ser : (ObservableList<XYChart.Series<Double, Double>>) chart.getData()) {
            contador++;
             //System.out.println(contador);
            if (ser.getName().equals("crecimiento")) {
                //if Name is "blue" then continue

                //for all series, take date, each data has Node (symbol) for representing point
                for (XYChart.Data<Double, Double> data : ser.getData()) {
                    final XYChart.Series series8 = HoveredThresholdNode.lineaGuiaX(data.getXValue(), data.getYValue()," "); //guia 1
                    final XYChart.Series series9 = HoveredThresholdNode.lineaGuiaY(data.getXValue(), data.getYValue(), " "); //guia 2

                    data.getNode().setOnMouseExited(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            //System.out.println("borrar");
                            HoveredThresholdNode.eliminarLineasGuia(chart, series8, series9);
                            System.out.println("antes if"+contador);
                            if(contador == 8){
                               setLegendMayores(chart,sexo);
                                System.out.println("mayores");
                            } else if(contador == 10){
                               setLegendIMC(chart,sexo);
                               System.out.println("imc");
                             }else if(contador==6){
                            setLegendMenores(chart,sexo); 
                        }
                            //setLegendLineasGuia(chart,sexo);
                        }
                    });

                    data.getNode().setOnMouseEntered(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            //System.out.println("dibujar");

                            chart.getData().addAll(series8, series9);
                            if(contador == 8){
                               setLegendMayores(chart,sexo); 
                            } else if(contador == 10){
                               setLegendIMC(chart,sexo);
                            }else if(contador==6){
                            setLegendMenores(chart,sexo); 
                        }

                            //setLegendLineasGuia(chart,sexo);

                            //solo lineas (lineas guia)
                            borrarPuntos(series8);
                            borrarPuntos(series9);

                            Node line8 = series8.getNode().lookup(".chart-series-line");
                            Node line9 = series9.getNode().lookup(".chart-series-line");
                    
                 
                            line8.setStyle("-fx-stroke: #000000;-fx-stroke-dash-array: 2 12;");
                            line9.setStyle("-fx-stroke: #000000;-fx-stroke-dash-array: 2 12;");
                            System.out.println(data.getXValue() + "," + data.getYValue());
                            

                        }

                    }
                    );
                }
            }
        }
         System.out.println("contador"+contador);
         //contador = 0;

    }


}
