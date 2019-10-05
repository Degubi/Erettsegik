import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Helyjegy_stream {
	
	public static void main(String[] args) throws IOException {
		var file = Files.readAllLines(Path.of("eladott.txt"));
		var firstSplit = file.get(0).split(" ");
		
		var eladottJegyek = Integer.parseInt(firstSplit[0]);
		var utHossz = Integer.parseInt(firstSplit[1]);
		var ar = Integer.parseInt(firstSplit[2]);  //10 km-k�nt
		var utasok = IntStream.range(0, file.size())
							  .mapToObj(i -> new Utas(file.get(i), i))
							  .toArray(Utas[]::new);
		
		var utolso = utasok[utasok.length - 1];
		System.out.println("2.Feladat: Utols� utas �l�se: " + utolso.ules + " utazott t�vols�g: " + utolso.getTavolsag());
		System.out.println("3.Feladat");
		Arrays.stream(utasok).filter(k -> k.getTavolsag() == utHossz).forEach(k -> System.out.print(k.sorszam + " "));
		System.out.println("\n4.Feladat");
		System.out.println("�sszes bev�tel: " + Arrays.stream(utasok).mapToInt(k -> k.getTavolsag()).sum());
		
		var uccso = Arrays.stream(utasok)
					   	  .mapToInt(k -> k.end)
					   	  .filter(k -> k != utHossz)
					   	  .max()
					   	  .orElseThrow();
		
		var felszallok = Arrays.stream(utasok).filter(k -> k.start == uccso).count();
		var leszallok = Arrays.stream(utasok).filter(k -> k.end == uccso).count();

		System.out.println("5.Feladat: Utols� meg�ll�n�l felsz�ll�k: " + felszallok + ", lesz�ll�k: " + leszallok);
		
		var allomasok = IntStream.concat(Arrays.stream(utasok).mapToInt(k -> k.end).distinct(), 
										 Arrays.stream(utasok).mapToInt(k -> k.start).distinct())
					  			 .distinct()
					  			 .toArray();
		
		System.out.println("6.Feladat: Meg�ll�k sz�ma: " + (allomasok.length - 2));
		
		try(var output = new PrintWriter("kihol.txt"); 
			var input = new Scanner(System.in)){
			
			System.out.println("�rj be 1 km sz�mot!");
			int readTav = input.nextInt();
			
			IntStream.rangeClosed(1, 48).forEach(index -> {
						 System.out.println(index + ". �l�s");
						 Arrays.stream(utasok)
						 	   .filter(k -> k.ules == index)
						 	   .filter(k -> k.start == readTav || k.end == readTav)
						 	   .findFirst()
						 	   .ifPresentOrElse(utas -> System.out.println(utas.sorszam + ". utas"), 
						 			   		   () -> System.out.println("�res"));
						 });
		}
	}
	
	public static class Utas{
		public final int ules, start, end, sorszam;
		
		public Utas(String line, int sorsz) {
			var data = line.split(" ");
			
			sorszam = sorsz;
			ules = Integer.parseInt(data[0]);
			start = Integer.parseInt(data[1]);
			end = Integer.parseInt(data[2]);
		}
		
		public int getTavolsag() {
			return end - start;
		}
		
		public int getAr(int kmAr) {
			var tav = getTavolsag();
			var utolso = tav % 10;
			var tizesek = tav / 10;
			
			if(utolso == 3 || utolso == 4 || utolso == 8 || utolso == 9) {
				++tizesek;
			}
			return kmAr * tizesek;
		}
	}
}