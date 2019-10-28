import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;

public class EU {
	public static void main(String[] args) throws IOException {
		var csatlakozasok = new ArrayList<Csatlakozas>();
		var lines = Files.readAllLines(Path.of("EUcsatlakozas.txt"), StandardCharsets.ISO_8859_1);
		
		for(var line : lines) {
			csatlakozasok.add(new Csatlakozas(line));
		}
		
		System.out.println("3. Feladat: 2018-ig csatlakozott orsz�gok sz�ma: " + csatlakozasok.size());
		
		var tagallamok2007ben = 0;
		for(var csati : csatlakozasok) {
			if(csati.csatlakozas.getYear() == 2007) {
				++tagallamok2007ben;
			}
		}
		
		System.out.println("4. Feladat: 2007-ben csatlakozott orsz�gok sz�ma: " + tagallamok2007ben);
		
		LocalDate magyarorszag = null;
		for(var csati : csatlakozasok) {
			if(csati.orszag.equals("Magyarorsz�g")) {
				magyarorszag = csati.csatlakozas;
				break;
			}
		}
		System.out.println("5. Feladat: Magyarorsz�g csatlakoz�sa: " + magyarorszag);
		
		var voltEMajusban = false;
		for(var csati : csatlakozasok) {
			if(csati.csatlakozas.getMonth() == Month.MAY) {
				voltEMajusban = true;
				break;
			}
		}
		
		if(voltEMajusban) {
			System.out.println("6. Feladat: Volt m�jusban csatlakoz�s");
		}else{
			System.out.println("6. Feladat: Nem volt m�jusban csatlakoz�s");
		}
		
		var utolso = csatlakozasok.get(0);
		for(var csati : csatlakozasok) {
			if(csati.csatlakozas.isAfter(utolso.csatlakozas)) {
				utolso = csati;
			}
		}
		System.out.println("7. Feladat: Utolj�ra csatlakozott: " + utolso.orszag);
		System.out.println("8. Feladat:");
		
		var stat = new HashMap<Integer, Integer>();
		for(var csati : csatlakozasok) {
			var ev = csati.csatlakozas.getYear();
			
			stat.put(ev, stat.getOrDefault(ev, 0) + 1);
		}
		
		for(var entry : stat.entrySet()) {
			System.out.println(entry.getKey() + " - " + entry.getValue() + " db orsz�g");
		}
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