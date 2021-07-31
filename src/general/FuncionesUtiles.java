package general;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.swing.JTextArea;

public class FuncionesUtiles {
	public static boolean isDouble(String strNum) {
	    if (strNum == null) {
	        return false;
	    }
	    try {
	        double d = Double.parseDouble(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	
	public static boolean isInteger(String strNum) {
	    if (strNum == null) {
	        return false;
	    }
	    try {
	        double d = Integer.parseInt(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	
	public static List<Double> linspaceCustom(double start, double stop, int n)
	{
	   List<Double> result = new ArrayList<Double>();

	   double step = (stop-start)/(n-1);

	   for(int i = 0; i <= n-2; i++)
	   {
	       result.add(start + (i * step));
	   }
	   result.add(stop);

	   return result;
	}
	
	public static void printRng(ArrayList<Double> pRng, JTextArea ptxtResultado)
	{
		int cont = 0;
        for (Double i: pRng) {
        	ptxtResultado.append(""+ String.format(Locale.ROOT, "%.4f",pRng.get(cont)));
        	cont = cont + 1;
        	if (cont < pRng.size()) {
        		ptxtResultado.append(" , ");
        	}
		}
        ptxtResultado.append(" ]");
	}
	
	public static void printQDI(ArrayList<Double> pQDI, JTextArea ptxtResultado)
	{
		int cont = 0;
        for (Double i: pQDI) {
        	ptxtResultado.append(""+ String.format(Locale.ROOT, "%.2f",pQDI.get(cont)));
        	cont = cont + 1;
        	if (cont < pQDI.size()) {
        		ptxtResultado.append(" , ");
        	}
		}
        ptxtResultado.append(" ]");
	}
	
	public static void printTabla2xN(double[][] pTabla, JTextArea ptxtResultado)
	{
		ptxtResultado.append("\nx = [");
        for (int i = 0; i < pTabla[0].length; i++) {
        	ptxtResultado.append(""+ String.format(Locale.ROOT, "%.4f",pTabla[0][i]));
       		ptxtResultado.append(" , ");
        	
		}
        ptxtResultado.append(" ]");
        ptxtResultado.append("\n");
        ptxtResultado.append("fx = [");
        for (int i = 0; i < pTabla[0].length; i++) {
        	ptxtResultado.append(""+ String.format(Locale.ROOT, "%.4f",pTabla[1][i]));
       		ptxtResultado.append(" , ");
        	
		}
        ptxtResultado.append(" ]\n");
	}
	
	public static void printVector (double[] pVector, JTextArea ptxtResultado)
	{
		ptxtResultado.append("Fx = [");
        for (int i = 0; i < pVector.length; i++) {
        	ptxtResultado.append(""+ String.format(Locale.ROOT, "%.4f",pVector[i]));
       		ptxtResultado.append(" , ");
        	
		}
        ptxtResultado.append(" ]");
        ptxtResultado.append("\n");
	}
	
}
