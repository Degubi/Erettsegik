import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;
import java.util.stream.*;

public class EU_stream {

	public static void main(String[] args) throws IOException {
		var csatlakozasok = Files.lines(Path.of("EUcsatlakozas.txt"), StandardCharsets.ISO_8859_1)
								 .map(Csatlakozas::new)
								 .toArray(Csatlakozas[]::new);
		
		var ketezertizennyolc = LocalDate.of(2018, 1, 1);
		var tagallamok2018 = Arrays.stream(csatlakozasok).filter(k -> k.csatlakozas.isBefore(ketezertizennyolc)).count();
		System.out.println("3. Feladat: EU tag�llamainak sz�ma: " + tagallamok2018);
		
		var csatlakozott2007 = Arrays.stream(csatlakozasok).filter(k -> k.csatlakozas.getYear() == 2007).count();
		System.out.println("4. Feladat: 2007-ben " + csatlakozott2007 + " orsz�g csatlakozott");
		
		Arrays.stream(csatlakozasok)
			  .filter(k -> k.orszag.equals("Magyarorsz�g"))
			  .findFirst()
			  .ifPresent(k -> System.out.println("5. Feladat: M.o. csatlakoz�sa: " + k.csatlakozas));
		
		var voltEMajusban = Arrays.stream(csatlakozasok).anyMatch(k -> k.csatlakozas.getMonth() == Month.MAY);
		if(voltEMajusban) {
			System.out.println("6. Feladat: Volt m�jusban csatlakoz�s");
		}else{
			System.out.println("6. Feladat: Nem volt m�jusban csatlakoz�s");
		}
		
		Arrays.stream(csatlakozasok)
			  .max(Comparator.comparing(k -> k.csatlakozas))
			  .ifPresent(k -> System.out.println("7. Feladat: Utolj�ra csatlakozott: " + k.orszag));
		
		System.out.println("8. Feladat:");
		Arrays.stream(csatlakozasok)
			  .collect(Collectors.groupingBy(k -> k.csatlakozas.getYear(), Collectors.counting()))
			  .forEach((ev, db) -> System.out.println(ev + " - " + db + " orsz�g"));
	}
	
	public static class Csatlakozas{
		public final String orszag;
		public final LocalDate csatlakozas;
		
		public Csatlakozas(String line) {
			var split = line.split(";");
			
			orszag = split[0];
			csatlakozas = LocalDate.parse(split[1].replace('.', '-'));
		}
	}
}