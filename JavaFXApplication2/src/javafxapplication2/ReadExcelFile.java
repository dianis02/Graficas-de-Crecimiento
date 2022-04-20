/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package javafxapplication2;

import java.io.IOException;
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
public class ReadExcelFile{
    /**
    * Metodo para leer el csv e ir guardando cada linea por casilla del arreglo
    * @param ruta_del_archivo. ruta del archivo donde estan guardado los objetos
    * @return Arreglo tal que cada casilla es una fila del archivo
    */
    public ArrayList<String> leer(String nombreArchivo){
        
        ArrayList<String> arreglo = new ArrayList<String>(0);

        String linea = null;
        Scanner in = null;
        FileReader archivo = null;
        File f = new File(nombreArchivo);
        String absolute = f.getAbsolutePath();
        
        try{
            archivo=new FileReader(absolute);
            //Creamos el objeto de lectura
            in = new Scanner(archivo);
            //ciclo para leer todo el archivo
            while (in.hasNextLine()) {
                //recuperamos el objeto
                linea = in.nextLine();
                arreglo.add(linea);
            }

        }catch(IOException e){
            System.out.println("Lectura fallida: "+e);
        }finally{
            if(in!=null){
                try {
                    // Cerramos el flujo del in
                    in.close();
                    // Cerramos el flujo del archivo
                    archivo.close();
                    System.out.println("Se recuperó con éxito");
                } catch (IOException e) {
                    System.out.println(e);
                }//cerramos catch dentro de finally
            }else {
                System.out.println("No hay ningun archivo abierto.");
            }//cerramos la condicional
        }//cerramos finally
        return arreglo;
    }

}
