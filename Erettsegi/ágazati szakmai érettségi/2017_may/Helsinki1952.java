import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Helsinki1952 {
	
	private static int pontCalc(int helyezes) { return helyezes == 1 ? 7 : 7 - helyezes; }
	
    public static void main(String[] args) throws Exception{
    	var helyezesek = Files.lines(Paths.get("helsinki.txt"), StandardCharsets.ISO_8859_1)
    						  .map(Helyezes::new)
    						  .toArray(Helyezes[]::new);
    	
    	System.out.println("3.Feladat\nPontszerz� helyez�sek sz�ma: " + helyezesek.length);
    	
    	var aranyak = Stream.of(helyezesek).filter(k -> k.helyezes == 1).count();
    	var ezustok = Stream.of(helyezesek).filter(k -> k.helyezes == 2).count();
    	var bronzok = Stream.of(helyezesek).filter(k -> k.helyezes == 2).count();
    	
    	System.out.println("4.Feladat\nAranyak: " + aranyak + ", ezustok: " + ezustok + ", bronzok: " + bronzok + ", �sszesen: " + (aranyak + ezustok + bronzok));
    	System.out.println("5.Feladat\nPontok sz�ma: " + Stream.of(helyezesek).mapToInt(k -> pontCalc(k.helyezes)).sum());
    	
    	var uszas = Stream.of(helyezesek)
	    				  .filter(k -> k.helyezes <= 3)
	    				  .filter(k -> k.sportag.equals("uszas"))
	    				  .count();
    	
    	var torna = Stream.of(helyezesek)
						  .filter(k -> k.helyezes <= 3)
						  .filter(k -> k.sportag.equals("torna"))
						  .count();
    	
    	System.out.println("6.Feladat");
    	System.out.println(uszas == torna ? "Egyenl�ek" : (torna > uszas) ? "Torna t�bb" : "�sz�s t�bb");
    	
    	Files.write(Paths.get("helsinki2.txt"), Stream.of(helyezesek)
    												  .map(k -> k.helyezes + " " + pontCalc(k.helyezes) + " " + k.sportag.replace("kajakkenu", "kajak-kenu"))
    												  .collect(Collectors.toList()));
    
    	Stream.of(helyezesek).max(Comparator.comparingInt(k -> k.sportolokSzama))
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
    }
}