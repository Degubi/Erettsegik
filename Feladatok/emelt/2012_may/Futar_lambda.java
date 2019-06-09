import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Futar_lambda{
	
	public static void main(String[] args) throws IOException{
		var fuvarLista = Files.lines(Paths.get("tavok.txt")).map(k -> new Fuvar(k.split(" "))).toArray(Fuvar[]::new);
		
		System.out.println("2. Feladat: A h�t legels� �tja km-ben: " + fuvarLista[0].tavolsag + " km");
		System.out.println("3. Feladat: A h�t utols� �tja km-ben: " + fuvarLista[fuvarLista.length - 1].tavolsag + " km");
	
		IntStream.rangeClosed(1, 7).forEach(day -> {
			if(Stream.of(fuvarLista)
					 .mapToInt(k -> k.nap)
					 .distinct()
					 .noneMatch(k -> k == day)) System.out.println("A " + day + ". nap szabadnap volt");});
		
		System.out.println("5. Feladat: Legt�bb fuvar� nap: " + Stream.of(fuvarLista)
									  .max(Comparator.comparingInt(k -> k.tavolsag))
									  .get().nap);
		
		System.out.println("6. Feladat");
		IntStream.rangeClosed(1, 7).forEach(day -> System.out.println("A " + day + ". nap t�vja: " + 
							 Stream.of(fuvarLista)
				 				   .filter(k -> k.nap == day)
				 				   .mapToInt(k -> k.tavolsag)
				 				   .sum()));
		
		try(var input = new Scanner(System.in)){
			System.out.println("7.Feladat: �rj be 1 t�vols�got!");
			int readKm = input.nextInt();
			System.out.println(readKm + " km eset�n fizetend�: " + calcPrice(readKm));
		}
		
		try(var output = new PrintWriter("dijazas.txt")){
			Stream.of(fuvarLista).forEach(fuvar -> output.println(fuvar.nap + ". nap " + fuvar.sorszam + ". fuvar: " + calcPrice(fuvar.tavolsag) + "FT"));
		}
		System.out.println("9. Feladat: Az eg�sz heti fizet�s: " + Stream.of(fuvarLista)
										.mapToInt(k -> calcPrice(k.tavolsag))
										.sum());
	}
	
	private static int calcPrice(int distance) {
		if(distance <= 2) {
			return 500;
		}else if(distance <= 5) {
			return 700;
		}else if(distance <= 10) {
			return 900;
		}else if(distance <= 20) {
			return 1400;
		}
		return 2000;
	}
	
	static class Fuvar{
		int nap, sorszam, tavolsag;
		
		public Fuvar(String[] data) {
			nap = Integer.parseInt(data[0]);
			sorszam = Integer.parseInt(data[1]);
			tavolsag = Integer.parseInt(data[2]);
		}
	}
}