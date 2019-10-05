import java.io.*;
import java.nio.file.*;
import java.util.*;

public class HegyekMo {
	public static void main(String[] args) throws IOException{
		var sorok = Files.readAllLines(Path.of("hegyekMo.txt"));
		var hegyek = new ArrayList<Hegy>();
		
		for(var i = 1; i < sorok.size(); ++i) {
			hegyek.add(new Hegy(sorok.get(i)));
		}
		
		System.out.println("3. Feladat: Hegyek sz�ma: " + hegyek.size() + " db");
		
		var osszMagassag = 0D;
		for(var hegy : hegyek) {
			osszMagassag += hegy.magassag;
		}
		
		System.out.println("4. Feladat: �tlagmagass�g: " + (osszMagassag / hegyek.size()) + " m");
		
		Hegy legmagasabbHegy = null;
		var legnagyobbMagassag = 0;
		for(int i = 0; i < hegyek.size(); i++) {
			var hegy = hegyek.get(i);
			
			if(hegy.magassag > legnagyobbMagassag) {
				legnagyobbMagassag = hegy.magassag;
				legmagasabbHegy = hegy;
			}
		}
		
		System.out.println("5. Feladat: Legmagasabb hegy: " + legmagasabbHegy.hegyseg + "-ben a " + legmagasabbHegy.nev + ", magass�ga: " + legmagasabbHegy.magassag + " m");
		
		try(var input = new Scanner(System.in)){
			System.out.println("6. Feladat: �rj be egy magass�got!");
			var beMagassag = input.nextInt();
			
			var vanMagasabb = false;
			for(var hegy : hegyek) {
				if(hegy.hegyseg.equals("B�rzs�ny") && hegy.magassag > beMagassag) {
					vanMagasabb = true;
					break;
				}
			}
			
			if(vanMagasabb) {
				System.out.println("Van magasabb hegys�g enn�l a B�rzs�nyben");
			}else {
				System.out.println("Nincs magasabb hegys�g enn�l a B�rzs�nyben");
			}
		}
		
		var konvertaltLab3000 = 3000D / 3.280839895D;
		var magasakSzama = 0;
		
		for(var hegy : hegyek) {
			if(hegy.magassag > konvertaltLab3000) {
				++magasakSzama;
			}
		}
		
		System.out.println("7. Feladat: 3000 l�bn�l magasabbak sz�ma: " + magasakSzama);
		System.out.println("8. Feladat: Hegys�g stat");
		
		var hegysegStat = new HashMap<String, Integer>();
		for(var hegy : hegyek) {
			hegysegStat.put(hegy.hegyseg, hegysegStat.getOrDefault(hegy.hegyseg, 0) + 1);
		}
		
		for(var entry : hegysegStat.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
		
		try(var output = new PrintWriter("bukk-videk.txt")){
			output.println("Hegycs�cs neve;Magass�g l�b");
			
			for(var hegy : hegyek) {
				if(hegy.hegyseg.equals("B�kk-vid�k")) {
					var formazott = String.format("%s;%.2f", hegy.nev, hegy.magassag * 3.280839895D)
										  .replace(',', '.');
					
					output.println(formazott);
				}
			}
		}
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