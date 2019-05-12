import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Helyjegy_lambda {
	
	public static void main(String[] args) throws IOException {
		var file = Files.readAllLines(Paths.get("eladott.txt"));
		var firstSplit = file.get(0).split(" ");
		
		var eladottJegyek = Integer.parseInt(firstSplit[0]);
		var utHossz = Integer.parseInt(firstSplit[1]);
		var ar = Integer.parseInt(firstSplit[2]);  //10 km-k�nt
		var utasok = file.stream().skip(1).map(Utas::new).toArray(Utas[]::new);
		
		var utolso = utasok[utasok.length - 1];
		System.out.println("2.Feladat\nUtols� utas �l�se: " + utolso.ules + " utazott t�vols�g: " + utolso.getTavolsag());
		System.out.println("3.Feladat");
		Stream.of(utasok).filter(k -> k.getTavolsag() == utHossz).forEach(k -> System.out.print(k.sorszam + " "));
		System.out.println("\n4.Feladat");
		System.out.println("�sszes bev�tel: " + Stream.of(utasok).mapToInt(k -> k.getTavolsag()).sum());
		
		var uccso = Stream.of(utasok)
					   	  .mapToInt(k -> k.end)
					   	  .filter(k -> k != utHossz)
					   	  .max()
					   	  .orElseThrow();
		
		var felszallok = Stream.of(utasok).filter(k -> k.start == uccso).count();
		var leszallok = Stream.of(utasok).filter(k -> k.end == uccso).count();

		System.out.println("5.Feladat\nUtols� meg�ll�n�l felsz�ll�k: " + felszallok + ", lesz�ll�k: " + leszallok);
		
		var allomasok = IntStream.concat(Stream.of(utasok).mapToInt(k -> k.end).distinct(), 
										 Stream.of(utasok).mapToInt(k -> k.start).distinct())
					  			 .distinct()
					  			 .toArray();
		
		System.out.println("6.Feladat\nMeg�ll�k sz�ma: " + (allomasok.length - 2));
		
		try(var output = new PrintWriter("kihol.txt"); 
			var input = new Scanner(System.in)){
			
			System.out.println("�rj be 1 km sz�mot!");
			int readTav = input.nextInt();
			
			IntStream.rangeClosed(1, 48).forEach(index -> {
						 System.out.println(index + ". �l�s");
						 Stream.of(utasok)
						 	   .filter(k -> k.ules == index)
						 	   .filter(k -> k.start == readTav || k.end == readTav)
						 	   .findFirst()
						 	   .ifPresentOrElse(utas -> System.out.println(utas.sorszam + ". utas"), 
						 			   		   () -> System.out.println("�res"));
						 });
		}
	}
	
	static class Utas{
		int ules, start, end, sorszam;
		static int counter = 0;
		
		public Utas(String line) {
			sorszam = ++counter;
			var data = line.split(" ");
			
			ules = Integer.parseInt(data[0]);
			start = Integer.parseInt(data[1]);
			end = Integer.parseInt(data[2]);
		}
		
		public int getTavolsag() {
			return end - start;
		}
		
		public int getAr(int kmAr) {
			int tav = getTavolsag();
			int utolso = tav % 10;
			int tizesek = tav / 10;
			if(utolso == 3 || utolso == 4 || utolso == 8 || utolso == 9) {
				++tizesek;
			}
			return kmAr * tizesek;
		}
	}
}