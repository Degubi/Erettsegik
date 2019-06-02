import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;
import java.util.stream.*;

public class Vasmegye_lambda {

	public static void main(String[] args) throws IOException {
		var szuletesek = Files.lines(Path.of("vas.txt"))
							  .map(Szuletes::new)
							  .filter(Szuletes::cdvEll)   //Hib�sakat sosem t�roljuk el
							  .toArray(Szuletes[]::new);
		
		System.out.println("5. Feladat");
		System.out.println("Csecsem�k sz�ma: " + szuletesek.length);
		System.out.println("6. Feladat");
		System.out.println("Fi� csecsem�k sz�ma: " + Arrays.stream(szuletesek).filter(k -> k.szamjegyek[0] % 2 == 1).count());
		System.out.println("7. Feladat");
		
		var stat = Arrays.stream(szuletesek).mapToInt(k -> k.datum.getYear()).summaryStatistics();
		System.out.println("Vizsg�lt id�szak: Kezdet: " + stat.getMin() + ", v�ge: " + stat.getMax());
		
		System.out.println("8. Feladat");
		Arrays.stream(szuletesek)
			  .filter(k -> k.datum.getYear() % 4 == 0)
			  .findFirst()
			  .ifPresent(k -> System.out.println("Volt baba sz�k��vben"));
		
		System.out.println("9. Feladat");
		Arrays.stream(szuletesek)
			  .collect(Collectors.groupingBy(k -> k.datum.getYear(), LinkedHashMap::new, Collectors.counting())) //LinkedHashMap Supplier, k�l�nben az �vek random sorrendben lesznek
			  .forEach((ev, babak) -> System.out.println(ev + "-ben " + babak + " baba szuletett"));
	}
	
	static class Szuletes{
		LocalDate datum;
		int[] szamjegyek;
		
		public Szuletes(String line) {
			var split = line.split("-");
			
			szamjegyek = line.chars()
							 .filter(kar -> kar != '-')
							 .map(Character::getNumericValue)  //Kell, mert a 'chars' stream az karakterk�d stream
							 .toArray();
			
			datum = LocalDate.of(Integer.parseInt((szamjegyek[0] < 3 ? "19" : "20") + split[1].substring(0, 2)), //�v az alapj�n h 3-n�l kisebb v nagyobb e az els� sz�m
					 Integer.parseInt(split[1].substring(2, 4)), //H�nap
					 Integer.parseInt(split[1].substring(4, 6)));  //Nap
		}
		
		public boolean cdvEll() {
			return szamjegyek[10] == IntStream.rangeClosed(0, 9).map(index -> szamjegyek[index] * (10 - index)).sum() % 11;
		}
	}
}