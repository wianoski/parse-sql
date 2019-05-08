/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sbdtubes2;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
/**
 *
 * @author Thirafi Wian
 */
public class SbdTubes2 {
    public static int menu() {
        int selection;
        Scanner input = new Scanner(System.in);

        System.out.println("\n-------------------------");
        System.out.println("1. BFR dan Fanout Ratio Setiap Tabel");
        System.out.println("2. Total Blok Data + Blok Index Setiap Tabel");
        System.out.println("3. Jumlah Blok yang Diakses Untuk Pencarian Rekord");
        System.out.println("4. QEP dan Cost");
        System.out.println("5. Isi File Shared Pool");
        System.out.println("6. EXIT");
        System.out.println("-------------------------\n");
        System.out.print(" Input: ");

        selection = input.nextInt();
        return selection;    
    }
    
    public static String[] cekQuery(String query){
        String delims = "[ ]+";
        String[] tokens = query.split(delims);
        boolean flag = false;
        System.out.println("");
        int a = 0;
        for(int i=0;i<tokens.length;i++){
            switch (i) {
               case 0:
                    if("SELECT".equals(tokens[i].toUpperCase())){
                        flag=true;
                        a = a+1;
                    }   break;
                case 1:
                    if("*".equals(tokens[i].toUpperCase())||"ID_KARYAWAN".equals(tokens[i].toUpperCase())||"NAMA_KARYAWAN".equals(tokens[i].toUpperCase())||"ROLE".equals(tokens[i].toUpperCase())||"ID_TRANSAKSI".equals(tokens[i].toUpperCase())||"TGL_TRANSAKSI".equals(tokens[i].toUpperCase())||"ITEM".equals(tokens[i].toUpperCase())||"ID_OBAT".equals(tokens[i].toUpperCase())||"NAMA_OBAT".equals(tokens[i].toUpperCase())||"KOMPOSISI".equals(tokens[i].toUpperCase())||"DOSIS_STD".equals(tokens[i].toUpperCase())||"JUMLAH".equals(tokens[i].toUpperCase())){
                        flag=true;
                        a = a+1;
                    }   break;
                case 2:
                    if("FROM".equals(tokens[i].toUpperCase())){
                        flag=true;
                        a = a+1;
                    }   break;
                case 3:
                    if("KARYAWAN".equals(tokens[i].toUpperCase())||"TRANSAKSI".equals(tokens[i].toUpperCase())||"OBAT".equals(tokens[i].toUpperCase())||"STOK_OBAT".equals(tokens[i].toUpperCase())){
                        flag=true;
                        a = a+1;
                    }   break;
                case 4:
                    if("JOIN".equals(tokens[i].toUpperCase())||";".equals(tokens[i].toUpperCase())||("WHERE".equals(tokens[i].toUpperCase()))){
                        flag=true;
                        a = a+1;
                    }   break;
                case 5:
                    if("KARYAWAN".equals(tokens[i].toUpperCase())||"TRANSAKSI".equals(tokens[i].toUpperCase())||"OBAT".equals(tokens[i].toUpperCase())||"STOK_OBAT".equals(tokens[i].toUpperCase())){
                        flag=true;
                        a = a+1;
                    }   break;
                case 6:
                    if("USING".equals(tokens[i].toUpperCase())||"=".equals(tokens[i])){
                        flag=true;
                        a = a+1;
                    }   break;
                case 7:
                    if("(".equals(tokens[i].toUpperCase())||tokens[i]!=null){
                        flag=true;
                        a = a+1;
                    }   break;
                case 8:
                    if("ID_OBAT".equals(tokens[i].toUpperCase())||"OBAT".equals(tokens[i].toUpperCase())){
                        flag=true;
                        a = a+1;
                    }   break;
                case 9:
                    if(")".equals(tokens[i].toUpperCase())||");".equals(tokens[i].toUpperCase())){
                        flag=true;
                        a = a+1;
                    }   break;
                case 10:
                    if("JOIN".equals(tokens[i].toUpperCase())||";".equals(tokens[i].toUpperCase())){
                        flag=true;
                        a = a+1;
                    }   break;
                case 11:
                    if("OBAT".equals(tokens[i].toUpperCase())||"NAMA_OBAT".equals(tokens[i].toUpperCase())||"KOMPOSISI".equals(tokens[i].toUpperCase())||"DOSIS_STD".equals(tokens[i].toUpperCase())){
                        flag=true;
                        a = a+1;
                    }   break;
                case 12:
                    if("USING".equals(tokens[i].toUpperCase())){
                        flag=true;
                        a = a+1;
                    }   break;
                case 13:
                    if("(".equals(tokens[i])){
                        flag=true;
                        a = a+1;
                    }   break;
                case 14:
                    if("ID_OBAT".equals(tokens[i].toUpperCase())||"STOK".equals(tokens[i].toUpperCase())){
                        flag=true;
                        a = a+1;
                    }   break;
                case 15:
                    if (");".equals(tokens[i])){
                        flag=true;
                        a = a+1;
                    }   break;
                case 16:
                    if ("WHERE".equals(tokens[i].toUpperCase())){
                        flag=true;
                        a = a+1;
                    }   break;
                case 17:
                    if ("=".equals(tokens[i])){
                        flag=true;
                        a = a+1;
                        
                    }   break;
                default:
                    break;
            }
        }
        if (a<tokens.length){
            flag = false;
            return null;
        }
        if(flag==true){
            System.out.println("Sukses");
            return tokens;
        }else{
            System.out.println("Error");
            return null;
        }
    }
    
    public static void pressEnter(){
        System.out.println("");
        System.out.print("Press ENTER to continue...");
        try{       
            System.in.read();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) throws IOException {
        
        
        String csvFile = "src/sbdtubes2/kamusData.csv";
        String line = "#";
        
        String cvsSplitBy = ";";
        ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {
                
                ArrayList<String> dataX = new ArrayList<String>();
                String[] type = line.split(cvsSplitBy);
                for (int i = 0; i < type.length; i++){
                    String baru = type[i].toString().replace("#", "");
                    String baru2 = baru.replaceAll("\\s", "");
                    type[i] = baru2;
                    if (i > 1){
                        String baru3 = baru2.replaceAll("[^\\d]", "");
                        type[i] = baru3;
                    }
                    dataX.add(type[i]);
                }
                data.add(dataX);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < data.get(0).size(); i++){
            String baru3 = data.get(0).get(i).replaceAll("[^\\d]", "");
            data.get(0).set(i, baru3);
        }
        
        
        boolean again= true;
        while (again){
            
            String sharedFile = "OUTPUT.txt";
            String linee = "";
            ArrayList<String> dataShared = new ArrayList<>();
            try (BufferedReader buffer = new BufferedReader(new FileReader(sharedFile))) {
                while ((linee = buffer.readLine()) != null) {
                    dataShared.add(linee);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            String bestCase = null;
            String worstCase = null;
            if(!dataShared.isEmpty()){
                bestCase = dataShared.get(4).replaceAll("[^\\d]", "");
                worstCase = dataShared.get(10).replaceAll("[^\\d]", "");
            }
            int userChoice;
            Scanner input = new Scanner(System.in);
            userChoice = menu();
            int p = Integer.parseInt(data.get(0).get(0));
            int b = Integer.parseInt(data.get(0).get(1));
            System.out.println("");
            switch (userChoice) {
                case 1:
                    for (int i = 1; i < data.size(); i++){
                        int r = Integer.parseInt(data.get(i).get(2));
                        int v = Integer.parseInt(data.get(i).get(4));
                        int bfr = b / r;
                        int fanout = b / (v + p);
                        System.out.println("BFR untuk " + data.get(i).get(0) + " : " + bfr);
                        System.out.println("Fan Out Ratio untuk " + data.get(i).get(0) + " : " + fanout + "\n");
                    }
                    pressEnter();
                    break;
                case 2:
                    for (int i = 1; i < data.size(); i++){
                        int r = Integer.parseInt(data.get(i).get(2));
                        int n = Integer.parseInt(data.get(i).get(3));
                        int v = Integer.parseInt(data.get(i).get(4));
                        int bfr = b / r;
                        int entrysize = v + p;
                        int fanout = (int) Math.floor(b / (v + p));
                        int entryblock = (int) Math.ceil((double)n/(double)fanout)+1;
                        int jumlahblok = (int) Math.ceil((double)n/(double)bfr);
                        int h1 = (int) Math.ceil(Math.log(fanout) * jumlahblok);
                        System.out.println("Tabel data " + data.get(i).get(0) + " : " + jumlahblok + " Blok");
                        System.out.println("Index " + data.get(i).get(0) + " : " + entryblock + " Blok");
                    }
                    pressEnter();
                    break;
                case 3:
                    
                    System.out.println("Input Query : ");
                    
                    break;
                case 4:
                    // Perform "quit" case.
                    System.out.print("Input Query: ");
                    String query = input.nextLine();
                    String[] dataInput = cekQuery(query);
                    System.out.println(query);
                    System.out.println(dataInput.length);
                    if(dataInput != null){
                        int k = 0;
                        for (int i = 1; i < data.size(); i++) {
                            if (dataInput[3].equalsIgnoreCase(data.get(i).get(0))){
                                k = i;
                            }
                        }
//                      # RUMUS
                        int r = Integer.parseInt(data.get(k).get(2));
                        int n = Integer.parseInt(data.get(k).get(3));
                        int v = Integer.parseInt(data.get(k).get(4));
                        int bfr = b / r;
                        int entrysize = v + p;
                        int fanout = (int) Math.floor(b / (v + p));
                        int jumlahblok = (int) Math.ceil((double)n/(double)bfr);
                        int costA1 = (int) Math.ceil((double)jumlahblok / 2);
                        int y = (int) Math.floor(b / (v + p));
                        int h1 = (int) Math.ceil(Math.log(y) * jumlahblok);
                        int costA2 = h1 + 1;
                        int bestCost;
                        int worstCost;
                        
                        System.out.println("Tabel " + data.get(k).get(0));
                        System.out.println("List Kolom: " + data.get(k).get(1));
                        System.out.println("\nQEP #1");
                        System.out.println("PROJECTION " + dataInput[1] + " -- on the fly");
                        System.out.print("SELECTION ");
                        if(dataInput.length > 5){
                            System.out.print(dataInput[5]);
                        }
                        System.out.println(" A1 Key");
                        System.out.println("Cost : " + costA1 + " Block");
                        System.out.println("\nQEP #2");
                        System.out.println("PROJECTION " + dataInput[1] + " -- on the fly");
                        System.out.print("SELECTION ");
                        String thirdLine = "";
                        String thirdLine2 = "";
                        if(dataInput.length > 5){
                            System.out.print(dataInput[5]);
                            thirdLine = "Selection: " + dataInput[5];
                            thirdLine2 = "Selection: " + dataInput[5];
                        }
                        System.out.println(" A2");
                        System.out.println("Cost : " + costA2 * 2 + " Block");
                        System.out.print("\nQEP Optimal: ");
                        if (costA1 < costA2){
                            System.out.print("QEP#1");
                            bestCost = costA1;
                            worstCost = costA2 * 2;
                            thirdLine.concat(" -- A1 non Key");
                            thirdLine2.concat(" -- A2");
                        }
                        else {
                            System.out.print("QEP#2");
                            bestCost = costA2 * 2;
                            worstCost = costA1;
                            thirdLine.concat(" -- A2");
                            thirdLine2.concat(" -- A1 Key");
                        }
                        System.out.println("");
                        
                        pressEnter();
                    }
                    else {
                        System.out.println("Error query salah");
                        pressEnter();
                    }
                    
                    break;
                
                case 5:
                    BufferedReader bra = new BufferedReader(new FileReader("OUTPUT.txt"));
                    ArrayList<String> kosong = new ArrayList<String>();
                    try {
                        StringBuilder sb = new StringBuilder();
                        String lines = bra.readLine();                        
                        while (lines != null) {
                            sb.append(lines);
                            sb.append(System.lineSeparator());
                            lines = bra.readLine();
                            sb.append(sb);
                            kosong.add("PAAN SIH");
                        }
                        String everything = sb.toString();
                        System.out.println(everything);
                    } finally {
                        bra.close();
                    }
                    if (kosong.isEmpty()){
                        System.out.println("Shared Pool Kosong");
                    }
                    pressEnter();
                    break;
                    
                case 6:
                    System.exit(0);
                    break;
                    
                default:
                    break;
                    // The user input an unexpected choice.
            }
        }
    }
}
