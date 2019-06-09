import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Szamok_lambda {
	
	public static void main(String[] args) throws IOException {
		var rand = new Random();
		var feladatok = new ArrayList<Feladat>();
		
		var file = Files.readAllLines(Paths.get("felszam.txt"), StandardCharsets.ISO_8859_1);
		
		IntStream.iterate(0, k -> k < file.size(), k -> k + 2).forEach(k -> {
			var split = file.get(k + 1).split(" ");
			feladatok.add(new Feladat(file.get(k), Integer.parseInt(split[0]), Integer.parseInt(split[1]), split[2]));
		});
		
		System.out.println(feladatok.size() + " Feladat van a f�jlban!");
		
		System.out.println("Matekfeladatok sz�ma: " + feladatok.stream()
				  .filter(k -> k.temakor.equals("matematika"))
				  .count());
		
		System.out.println("1 pontot �r� feladatok sz�ma: " + feladatok.stream()
				  .filter(k -> k.temakor.equals("matematika"))
				  .filter(k -> k.pont == 1)
				  .count());
		
		System.out.println("2 pontot �r� feladatok sz�ma: " + feladatok.stream()
		  	.filter(k -> k.temakor.equals("matematika"))
		  	.filter(k -> k.pont == 2)
		  	.count());
		
		System.out.println("3 pontot �r� feladatok sz�ma: " + feladatok.stream()
		  	.filter(k -> k.temakor.equals("matematika"))
		  	.filter(k -> k.pont == 3)
		  	.count());
		
		System.out.println("A legkisebb v�lasz� feladat: " + 
		        feladatok.stream().min(Comparator.comparingInt(k -> k.valasz)).get().valasz + ", a legnagyobb: " + 
		        feladatok.stream().max(Comparator.comparingInt(k -> k.valasz)).get().valasz);
		
		System.out.println("El�fordul� t�mak�r�k: " + feladatok.stream()
										.map(k -> k.temakor)
										.distinct()
										.collect(Collectors.joining(", ")));
		
		try(var input = new Scanner(System.in)){
			System.out.println("�rj be 1 t�mak�rt!");
			String readKor = input.nextLine();
			
			var categorizalt = feladatok.stream()
						 				.filter(k -> k.temakor.equals(readKor))
						 				.toArray(Feladat[]::new);
	
			var chosen = categorizalt[rand.nextInt(categorizalt.length)];
			System.out.println(chosen.kerdes);
			
			if(input.nextInt() == chosen.valasz) {
				System.out.println("Kapott pontsz�m: " + chosen.pont);
			}else{
				System.out.println("Rossz v�lasz, 0 pont...\nA helyes v�lasz: " + chosen.valasz);
			}
		}
		
		var generalt = rand.ints(0, feladatok.size())
						   .mapToObj(feladatok::get)
						   .distinct()
						   .limit(10)
						   .toArray(Feladat[]::new);
		
		try(var output = new PrintWriter("tesztfel.txt")){
			Stream.of(generalt).forEach(toPrint -> output.println(toPrint.pont + " " + toPrint.kerdes));
			output.print(Stream.of(generalt).mapToInt(k -> k.pont).sum());
		}
	}
	
	static class Feladat{
		String kerdes, temakor;
		int pont, valasz;
		
		public Feladat(String quest, int answ, int points, String cat) {
			kerdes = quest;
			valasz = answ;
			pont = points;
			temakor = cat;
		}
	}
}