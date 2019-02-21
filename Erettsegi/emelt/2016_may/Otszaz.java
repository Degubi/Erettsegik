import java.io.*;
import java.nio.file.*;
import java.util.*;
 
public class Otszaz {
	
    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Paths.get("penztar.txt"));
        var vasarlasok = new ArrayList<Vasarlas>();
       
        ArrayList<String> toAdd = new ArrayList<>();
        for(String sor : lines) {
            if(sor.equals("F")) {
                vasarlasok.add(new Vasarlas(toAdd));
                toAdd.clear();
            }else{
                toAdd.add(sor);
            }
        }
		
        System.out.println("V�s�rl�sok sz�ma: " + vasarlasok.size());
        System.out.println("Els� v�s�rl�sn�l vett dolgok sz�ma: " + vasarlasok.get(0).dolgok.size());
		
        Scanner input = new Scanner(System.in);
        System.out.println("�rj be 1 sorsz�mot");
        int sorszam = input.nextInt();
        System.out.println("�rj be 1 �rut");
        String aru = input.next();
        System.out.println("�rj be 1 mennyis�get");
        int dbszam = input.nextInt();
        input.close();
        
        int amount = 0, utolso = 0;
        for(int k = 0; k < vasarlasok.size(); ++k) {
            for(var entries : vasarlasok.get(k).dolgok.entrySet()) {
                if(entries.getKey().equals(aru)) {
                    ++amount;
                    utolso = k;
                    if(amount == 1) {
                        System.out.println("El�sz�r a " + ++k + ". v�s�rl�sn�l vettek " + aru + "-t");
                    }
                }
            }
        }
       
        System.out.println("Utolj�ra a " + ++utolso + ". v�s�rl�sn�l vettek " + aru + "-t");
        System.out.println("�sszesen " + amount + "-szor vettek " + aru + "-t");
        System.out.println(dbszam + " db eset�n a fizetend�: " + ertek(dbszam));
        System.out.println("A " + sorszam + ". v�s�rl�skor v�s�rolt dolgok: " + vasarlasok.get(sorszam - 1).dolgok.toString());
       
        try(PrintWriter output = new PrintWriter("osszeg.txt")){
	        for(int k = 0; k < vasarlasok.size(); ++k) {
	            int printMount = 0;
	            for(var entries : vasarlasok.get(k).dolgok.entrySet()) {
	                printMount += ertek(entries.getValue());
	            }
	            output.println(Integer.toString(k + 1) + ":" + printMount);
	        }
        }
    }
    
    public static int ertek(int dbSzam) {
        if(dbSzam == 1) {
            return 500;
        }else if(dbSzam == 2) {
            return 950;
        }else if(dbSzam == 3) {
            return 1350;
        }
        return 1350 + (500 * (dbSzam - 1));
    }
    
    static class Vasarlas{
        HashMap<String, Integer> dolgok = new HashMap<>();
        
        public Vasarlas(ArrayList<String> things) {
            for(String th : things) {
                if(!dolgok.containsKey(th)) {
                    dolgok.put(th, Collections.frequency(things, th));
                }
            }
        }
    }
}