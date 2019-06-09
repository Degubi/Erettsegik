import static java.nio.charset.StandardCharsets.*;

import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Helsinki1952 {
	
    public static void main(String[] args) throws Exception{
    	var helyezesek = Files.lines(Paths.get("helsinki.txt"), ISO_8859_1)
    						  .map(Helyezes::new)
    						  .toArray(Helyezes[]::new);
    	
    	System.out.println("3.Feladat: Pontszerz� helyez�sek sz�ma: " + helyezesek.length);
    	
    	var aranyak = Arrays.stream(helyezesek).filter(k -> k.helyezes == 1).count();
    	var ezustok = Arrays.stream(helyezesek).filter(k -> k.helyezes == 2).count();
    	var bronzok = Arrays.stream(helyezesek).filter(k -> k.helyezes == 3).count();
    	
    	System.out.println("4.Feladat: Aranyak: " + aranyak + ", ezustok: " + ezustok + ", bronzok: " + bronzok + ", �sszesen: " + (aranyak + ezustok + bronzok));
    	System.out.println("5.Feladat: Pontok sz�ma: " + Arrays.stream(helyezesek).mapToInt(Helyezes::pontCalc).sum());
    	
    	var uszas = Arrays.stream(helyezesek)
	    				  .filter(k -> k.helyezes <= 3)
	    				  .filter(k -> k.sportag.equals("uszas"))
	    				  .count();
    	
    	var torna = Arrays.stream(helyezesek)
						  .filter(k -> k.helyezes <= 3)
						  .filter(k -> k.sportag.equals("torna"))
						  .count();
    	
    	System.out.println("6.Feladat");
    	System.out.println(uszas == torna ? "Egyenl�ek" : (torna > uszas) ? "Torna t�bb" : "�sz�s t�bb");
    	
    	Files.write(Paths.get("helsinki2.txt"), Arrays.stream(helyezesek)
    												  .map(k -> k.helyezes + " " + k.pontCalc() + " " + k.sportag.replace("kajakkenu", "kajak-kenu"))
    												  .collect(Collectors.toList()));
    
    	Arrays.stream(helyezesek).max(Comparator.comparingInt(k -> k.sportolokSzama))
    						 	 .ifPresent(k -> System.out.println("Helyez�s: " + k.helyezes + ", sport�g: " + k.sportag + ", sz�m: " 
    								 			+ k.versenyszam + ", sportol�k: " + k.sportolokSzama));
    }
    
    static class Helyezes{
    	int helyezes, sportolokSzama;
    	String sportag, versenyszam;
    	
    	public Helyezes(String line) {
    		String[] split = line.split(" ");
    		helyezes = Integer.parseInt(split[0]);
    		sportolokSzama = Integer.parseInt(split[1]);
    		sportag = split[2];
    		versenyszam = split[3];
		}
    	
    	public int pontCalc() { return helyezes == 1 ? 7 : 7 - helyezes; }
    }
}