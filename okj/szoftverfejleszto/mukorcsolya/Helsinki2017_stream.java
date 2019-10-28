import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.Map.*;
import java.util.stream.*;

public class Helsinki2017_stream {

    public static void main(String[] args) throws IOException {
        var versenyzok = Files.lines(Path.of("rovidprogram.csv")).skip(1).map(Versenyzo::new).toArray(Versenyzo[]::new);
        var dontosok = Files.lines(Path.of("donto.csv")).skip(1).map(Versenyzo::new).toArray(Versenyzo[]::new);
        
        System.out.println("2. Feladat: Versenyz�k sz�ma: " + versenyzok.length);
        System.out.print("3. Feladat: ");
        
        Arrays.stream(dontosok)
              .filter(k -> k.orszag.equals("HUN"))
              .findFirst()
              .ifPresentOrElse(k -> System.out.println("A magyar versenyz� bejutott"), 
                                 () -> System.out.println("A magyar versenyz� nem jutott be"));
        
        try(var input = new Scanner(System.in)){
            System.out.println("5. Feladat: K�rem 1 versenyz� nev�t!");
            
            var bekertNev = input.nextLine();
            
            System.out.println("6. Feladat: " + bekertNev + " pontsz�ma: " + osszPontszam(bekertNev, versenyzok, dontosok));
        }
        
        System.out.println("7. Feladat");
        Arrays.stream(dontosok)
              .collect(Collectors.groupingBy(k -> k.orszag, Collectors.counting()))
              .entrySet().stream()
              .filter(k -> k.getValue() > 1)
              .forEach(k -> System.out.println(k.getKey() + ": " + k.getValue() + " db versenyz�"));
        
        
        //TODO: Ebbe bele kell m�g sz�molni a versenyzok t�mb pontjait
        var raw = Arrays.stream(dontosok)
                           .collect(Collectors.toMap(k -> k, Versenyzo::sumPont))
                           .entrySet().stream()
                           .sorted(Entry.comparingByValue(Comparator.reverseOrder()))
                           .map(k -> ";" + k.getKey().nev + ";" + k.getKey().orszag + ";" + k.getValue() + "\n")
                           .toArray(String[]::new);
            
        var fileba = IntStream.range(0, raw.length)
                              .mapToObj(k -> (k + 1) + raw[k])
                              .collect(Collectors.joining());
            
        Files.writeString(Path.of("vegeredmeny.csv"), fileba);
    }
    
    public static double osszPontszam(String nev, Versenyzo[] versenyzok, Versenyzo[] dontosok) {
        var versenyPont = Arrays.stream(versenyzok).filter(k -> k.nev.equals(nev)).mapToDouble(Versenyzo::sumPont).sum();
        var dontosPont = Arrays.stream(dontosok).filter(k -> k.nev.equals(nev)).mapToDouble(Versenyzo::sumPont).sum();
        
        return versenyPont + dontosPont;
    }
    
    public static class Versenyzo{
        public final String nev;
        public final String orszag;
        public final double techPont;
        public final double kompPont;
        public final int hibaPont;
        
        public Versenyzo(String line) {
            var split = line.split(";");
            
            nev = split[0];
            orszag = split[1];
            techPont = Double.parseDouble(split[2]);
            kompPont = Double.parseDouble(split[3]);
            hibaPont = Integer.parseInt(split[4]);
        }
        
        public double sumPont() {
            return techPont + kompPont - hibaPont;
        }
    }
}