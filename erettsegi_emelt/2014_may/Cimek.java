import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Cimek {
    
    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Paths.get("ip.txt"));
        lines.sort(Comparator.naturalOrder());
        
        System.out.println("Adatsorok sz�ma: " + lines.size());
        System.out.println("Legkisebb ip c�m: " + lines.get(0));
        
        var counters = new int[3];
        for(String ip : lines) {
            if(ip.startsWith("2001:0db8")) {
                ++counters[0];
            }else if(ip.startsWith("2001:0e")) {
                ++counters[1];
            }else {
                ++counters[2];
            }
        }
        
        System.out.println("Dokument�ci�s c�mek: " + counters[0]);
        System.out.println("Glob�lis c�mek: " + counters[1]);
        System.out.println("Helyi egyedi c�mek: " + counters[2]);
        
        try(var output = new PrintWriter("sok.txt")){
            for(String ip : lines) {
                int counter = 0;
                for(char karak : ip.toCharArray()) {
                    if(karak == '0') {
                        ++counter;
                    }
                }
                if(counter > 17) {
                    output.println((lines.indexOf(ip) + 1) + " " + ip);
                }
            }
        }
        
        System.out.println("�rj be 1 sorsz�mot!");
        try(var input = new Scanner(System.in)){
            int index = input.nextInt() - 1;
            System.out.println(lines.get(index) + " (Eredeti)");
            String roviditett = rov1(lines.get(index));
            System.out.println(roviditett + " (1. R�vid�t�s)");
            System.out.println(rov2(roviditett));
        }
    }
    
    public static String rov1(String toRov) {
        return toRov.replace(":0", ":").replace(":0", ":").replace(":0", ":");
    }
    
    public static String rov2(String toRov) {
        String formatted = toRov.replace(":0:0:0:", "::").replace(":0:0:", "::");
        return toRov.equals(formatted) ? "Nem lehet egyszer�s�teni" : formatted + " (2. R�vid�t�s)";
    }
}