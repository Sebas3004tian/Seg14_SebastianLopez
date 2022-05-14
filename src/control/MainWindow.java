package control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MainWindow implements Initializable {

    @FXML
    private Canvas canvas;
    
    
    private ArrayList<Double>ejeXFILE=new ArrayList<>();
    private ArrayList<Double>ejeYFILE=new ArrayList<>();
    
    private ArrayList<Double>ejeX=new ArrayList<>();
    private ArrayList<Double>ejeY=new ArrayList<>();
    private GraphicsContext gc;
    
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		 gc = canvas.getGraphicsContext2D();
		 try {
			deserializar();
		} catch (IOException e) {
			e.printStackTrace();
		}
		paint();
	}
	public void paint() {
       gc.setFill(Color.rgb(240,240,240));
       gc.fillRect(0, 0, canvas.getWidth(),canvas.getHeight());
       
       double[]resX = getMinMax(ejeXFILE);
       double minX = resX[0];
       double maxX = resX[1];
       
       double[]resY = getMinMax(ejeYFILE);
       double minY = resY[0];
       double maxY = resY[1];
       
       boolean out=false;
       int y=0;
       int x=0;
       
       while(!out) {
    	   if(y>maxY) {
    		   out=true;
    	   }else {
        	   y+=50; 
    	   }
       }
       out=false;
       while(!out) {
    	   if(x>maxX) {
    		   out=true;
    	   }else {
        	   x+=50; 
    	   }
       }
       double converX=canvas.getWidth()/x;
       double converY=canvas.getHeight()/y;
       

       gc.setFill(Color.rgb(187, 187, 187));
       gc.fillRect(0, canvas.getHeight()*1/4, canvas.getWidth(),1);
       gc.fillRect(0, canvas.getHeight()*2/4, canvas.getWidth(),1);
       gc.fillRect(0, canvas.getHeight()*3/4, canvas.getWidth(),1);
       
       gc.fillText(y*3/4+"", 0, canvas.getHeight()*1/4-5);
       gc.fillText(y*2/4+"", 0, canvas.getHeight()*2/4-5);
       gc.fillText(y*1/4+"", 0, canvas.getHeight()*3/4-5);
       
       gc.fillRect(canvas.getWidth()*1/4, 0, 1,canvas.getHeight());
       gc.fillRect(canvas.getWidth()*2/4, 0, 1,canvas.getHeight());
       gc.fillRect(canvas.getWidth()*3/4, 0, 1,canvas.getHeight());
       
       gc.fillText(x*1/4+"", canvas.getWidth()*1/4+5, canvas.getHeight()-5);
       gc.fillText(x*2/4+"", canvas.getWidth()*2/4+5, canvas.getHeight()-5);
       gc.fillText(x*3/4+"", canvas.getWidth()*3/4+5, canvas.getHeight()-5);
       
       
       
       gc.setFill(Color.BLUE);
       for(int i=0;i<ejeXFILE.size();i++) {
    	   
    	   double coordenadaX=ejeXFILE.get(i)*(converX);
    	   double coordenadaY=(y-ejeYFILE.get(i))*(converY);
    	   
    	   ejeX.add(coordenadaX);
    	   ejeY.add(coordenadaY);
    	   
    	   gc.fillOval(coordenadaX-3,coordenadaY-3, 6, 6);
       }
       
       gc.setStroke(Color.BLUE);
       gc.setLineWidth(2);
       for(int i=0;i<ejeXFILE.size()-1;i++) {
    	      // gc.setLineWidth(2);
    	       gc.moveTo(ejeX.get(i), ejeY.get(i));
    	       gc.lineTo(ejeX.get(i+1), ejeY.get(i+1));
       }
       gc.stroke();
	}

	public double[] getMinMax(ArrayList<Double>eje) {
		ArrayList<Double>aux=new ArrayList<>();
		aux.addAll(eje);
		Collections.sort(aux);
		double min = aux.get(0);
		double max = aux.get(aux.size()-1);
		return new double [] {min,max};
	}
	public void deserializar() throws IOException {
		String path = "../Seg14_SebastianLopez/src/means/data.csv";
		
		String[] dataList = new String[2];
		
		File dataFile = new File(path);
		
		
		FileReader fr = new FileReader(path);
		BufferedReader br = new BufferedReader(fr);
		
		String line;
		line=br.readLine();
		while((line=br.readLine())!=null) {
			String[] datosSeparados=line.split("\\,");
			datosSeparados[1] = datosSeparados[1].replace(";", "");
			

			ejeXFILE.add(Double.parseDouble(datosSeparados[0]));
			ejeYFILE.add(Double.parseDouble(datosSeparados[1]));
			
			
	    }
			
		if (br != null)br.close();
        if (fr != null)fr.close();
			
	}
	
}
