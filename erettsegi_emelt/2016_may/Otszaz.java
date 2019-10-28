import java.io.*;
import java.nio.file.*;
import java.util.*;
 
public class Otszaz {
    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Paths.get("penztar.txt"));
        var vasarlasok = new ArrayList<List<String>>();
       
        var toAdd = new ArrayList<String>();
        for(var sor : lines) {
            if(sor.equals("F")) {
                vasarlasok.add(new ArrayList<>(toAdd));
                toAdd.clear();
            }else{
                toAdd.add(sor);
            }
        }
        
        System.out.println("V�s�rl�sok sz�ma: " + vasarlasok.size());
        System.out.println("Els� v�s�rl�sn�l vett dolgok sz�ma: " + vasarlasok.get(0).size());
        
        var input = new Scanner(System.in);
        System.out.println("�rj be 1 sorsz�mot");
        int beSorszam = input.nextInt();
        System.out.println("�rj be 1 �rut");
        String beAru = input.next();
        System.out.println("�rj be 1 mennyis�get");
        int beDBszam = input.nextInt();
        input.close();
        
        int osszesVetel = 0, utolsoSorszam = 0;
        
        for(int k = 0; k < vasarlasok.size(); ++k) {
            for(var cuccok : vasarlasok.get(k)) {
                if(cuccok.equals(beAru)) {
                    ++osszesVetel;
                    utolsoSorszam = k;
                    
                    if(osszesVetel == 1) {
                        System.out.println("El�sz�r a " + (k + 1) + ". v�s�rl�sn�l vettek " + beAru + "-t");
                    }
                }
            }
        }
       
        System.out.println("Utolj�ra a " + (utolsoSorszam + 1) + ". v�s�rl�sn�l vettek " + beAru + "-t");
        System.out.println("�sszesen " + osszesVetel + "-szor vettek " + beAru + "-t");
        System.out.println(beDBszam + " db eset�n a fizetend�: " + ertek(beDBszam));
        System.out.println("A " + beSorszam + ". v�s�rl�skor v�s�rolt dolgok: ");
        
        for(var statok : stat(vasarlasok.get(beSorszam - 1)).entrySet()) {
            System.out.println(statok.getKey() + "-b�l: " + statok.getValue() + " db");
        }
       
        try(var output = new PrintWriter("osszeg.txt")){
            for(int k = 0; k < vasarlasok.size(); ++k) {
                int irasDarab = 0;
                var statisztika = stat(vasarlasok.get(k));
                
                for(var entries : statisztika.entrySet()) {
                    irasDarab += ertek(entries.getValue());
                }
                output.println((k + 1) + ": " + irasDarab);
            }
        }
    }
    
    static int ertek(int dbSzam) {
        if(dbSzam == 1) {
            return 500;
        }else if(dbSzam == 2) {
            return 950;
        }else if(dbSzam == 3) {
            return 1350;
        }
        return 1350 + (500 * (dbSzam - 1));
    }
    
    static Map<String, Integer> stat(List<String> vasarlas){
        var freqMap = new HashMap<String, Integer>();
        
        for(var cucc : vasarlas) {
            if(!freqMap.containsKey(cucc)) {
                freqMap.put(cucc, Collections.frequency(vasarlas, cucc));
            }
        }
        
        return freqMap;
    }
}