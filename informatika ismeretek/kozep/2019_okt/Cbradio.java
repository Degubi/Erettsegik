import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Cbradio {

    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Path.of("cb.txt"));
        var bejegyzesek = new ArrayList<Bejegyzes>();
        
        for(var i = 1; i < lines.size(); i++) {
            bejegyzesek.add(new Bejegyzes(lines.get(i)));
        }
        
        System.out.println("3. Feladat: Bejegyz�sek sz�ma: " + bejegyzesek.size());
        
        var voltE4Adasos = false;
        for(var bejegyzes : bejegyzesek) {
            if(bejegyzes.adasok == 4) {
                voltE4Adasos = true;
                break;
            }
        }
        
        if(voltE4Adasos) {
            System.out.println("4. Feladat: Volt 4 ad�st ind�t� sof�r");
        }else {
            System.out.println("4. Feladat: Nem volt 4 ad�st ind�t� sof�r");
        }
        
        System.out.println("5. Feladat: �rj be egy nevet");
        try(var console = new Scanner(System.in)){
            var bekertNev = console.nextLine();
            var bekertHasznalatok = 0;
            
            for(var bejegyzes : bejegyzesek) {
                if(bejegyzes.nev.equals(bekertNev)) {
                    bekertHasznalatok += bejegyzes.adasok;
                }
            }
            
            if(bekertHasznalatok > 0) {
                System.out.println(bekertNev + " " + bekertHasznalatok + "x haszn�lta a r�di�t");
            }else{
                System.out.println("Nincs ilyen nev� sof�r!");
            }
        }
        
        try(var file = new PrintWriter("cb2.txt")){
            file.println("Kezdes;Nev;AdasDb");
            
            for(var bejegyzes : bejegyzesek) {
                file.println(atszamolPercre(bejegyzes.ora, bejegyzes.perc) + ";" + bejegyzes.nev + ";" + bejegyzes.adasok);
            }
        }
        
        var egyediSoforok = new HashSet<String>();
        for(var bejegyzes : bejegyzesek) {
            egyediSoforok.add(bejegyzes.nev);
        }
        
        System.out.println("8. Feladat: Sof�r�k sz�ma: " + egyediSoforok.size());
        
        var soforokAdasszamokkal = new HashMap<String, Integer>();
        for(var bejegyzes : bejegyzesek) {
            var soforNeve = bejegyzes.nev;
            
            soforokAdasszamokkal.put(soforNeve, soforokAdasszamokkal.getOrDefault(soforNeve, 0) + bejegyzes.adasok);
        }
        
        //El�g ronda m�dszer az els� elem lek�r�s�re, de kell valami amihez k�pest �sszehasonl�tani tudunk. :/
        var legtobbAdasBejegyzes = soforokAdasszamokkal.entrySet().iterator().next();
        for(var bejegyzes : soforokAdasszamokkal.entrySet()) {
            if(bejegyzes.getValue() > legtobbAdasBejegyzes.getValue()) {
                legtobbAdasBejegyzes = bejegyzes;
            }
        }
        
        System.out.println("9. Feladat: Legt�bb ad�st ind�t� sof�r: " + legtobbAdasBejegyzes.getKey() + ", ad�sok: " + legtobbAdasBejegyzes.getValue());
    }
    
    public static int atszamolPercre(int ora, int perc) {
        return ora * 60 + perc;
    }
    
    public static class Bejegyzes{
        public final int ora;
        public final int perc;
        public final int adasok;
        public final String nev;
        
        public Bejegyzes(String line) {
            var split = line.split(";");
            
            ora = Integer.parseInt(split[0]);
            perc = Integer.parseInt(split[1]);
            adasok = Integer.parseInt(split[2]);
            nev = split[3];
        }
    }
}