import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class HegyekMo_stream {
	public static void main(String[] args) throws IOException{
		var hegyek = Files.lines(Path.of("hegyekMo.txt"))
						  .skip(1)
						  .map(Hegy::new)
						  .toArray(Hegy[]::new);
		
		System.out.println("3. Feladat: Hegyek sz�ma: " + hegyek.length + " db");
		
		Arrays.stream(hegyek)
			  .mapToInt(k -> k.magassag)
			  .average()
			  .ifPresent(k -> System.out.println("4. Feladat: �tlagmagass�g: " + k + " m"));
		
		Arrays.stream(hegyek)
			  .max(Comparator.comparingInt(k -> k.magassag))
			  .ifPresent(k -> System.out.println("5. Feladat: Legmagasabb hegy: " + k.hegyseg + "-ben a " + k.nev + ", magass�ga: " + k.magassag + " m"));
		
		try(var input = new Scanner(System.in)){
			System.out.println("6. Feladat: �rj be egy magass�got!");
			var beMagassag = input.nextInt();
			
			Arrays.stream(hegyek)
				  .filter(k -> k.hegyseg.equals("B�rzs�ny"))
				  .filter(k -> k.magassag > beMagassag)
				  .findFirst()
				  .ifPresentOrElse(k -> System.out.println("Van magasabb hegys�g enn�l a B�rzs�nyben"),
						  		  () -> System.out.println("Nincs magasabb hegys�g enn�l a B�rzs�nyben"));
		}
		
		var konvertaltLab3000 = 3000D / 3.280839895D;
		var magasakSzama = Arrays.stream(hegyek).filter(k -> k.magassag > konvertaltLab3000).count();
		System.out.println("7. Feladat: 3000 l�bn�l magasabbak sz�ma: " + magasakSzama);
		System.out.println("8. Feladat: Hegys�g stat");
		
		Arrays.stream(hegyek)
			  .collect(Collectors.groupingBy(k -> k.hegyseg, Collectors.counting()))
			  .forEach((hegyseg, db) -> System.out.println(hegyseg + ": " + db));
		
		var fileAdat = Arrays.stream(hegyek)
							 .filter(k -> k.hegyseg.equals("B�kk-vid�k"))
							 .map(k -> String.format("%s;%.2f", k.nev, k.magassag * 3.280839895D))
							 .map(k -> k.replace(',', '.'))
							 .collect(Collectors.joining("\n"));
		
		Files.writeString(Path.of("bukk-videk.txt"), "Hegycs�cs neve;Magass�g l�b\n" + fileAdat);
	}
	
	public static class Hegy{
		public final String nev;
		public final String hegyseg;
		public final int magassag;
		
		public Hegy(String line) {
			var split = line.split(";");
			
			nev = split[0];
			hegyseg = split[1];
			magassag = Integer.parseInt(split[2]);
		}
	}
}