import java.util.Scanner;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.util.Random;
import java.util.ArrayList;

/**
 * Importer deals with importing most information from a save file.
 *
 * @author Shinymewtwo99
 * @version
 */
public class Importer
{

    public static String[] importProv (String name, int provIDnum) throws IOException
    {

        String provID = Integer.toString(provIDnum);

        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;

        String keyWord = "	"+provID+"={";

        int aqq = 0;

        boolean endOrNot = true;
        String vmm = scnr.nextLine();
        String qaaa = vmm;
        String[] output;   // Owner Culture Religeon PopTotal Buildings
        output = new String[5];

        output[0] = "9999"; //default for no owner, uncolonized province
        output[1] = "noCulture"; //default for no culture, uncolonized province with 0 pops
        output[2] = "noReligion"; //default for no religion, uncolonized province with 0 pops

        try {
            while (endOrNot = true){

                qaaa = scnr.nextLine();

                if (qaaa.equals(keyWord)){
                    endOrNot = false;

                    while (flag == 0) {
                        qaaa = scnr.nextLine();
                        if (qaaa.split("=")[0].equals( "		owner" ) ) {
                            output[0] = qaaa.split("=")[1];
                        }
                        if (qaaa.split("=")[0].equals( "		culture" ) ) {
                            output[1] = qaaa.split("=")[1];
                            output[1] = output[1].substring(1,output[1].length()-1);
                        }
                        if (qaaa.split("=")[0].equals( "		religion" ) ) {
                            output[2] = qaaa.split("=")[1];
                            output[2] = output[2].substring(1,output[2].length()-1);
                        }

                        //popTotal
                        if (qaaa.split("=")[0].equals( "		pop" ) ) {
                            aqq = aqq + 1;
                            //double aq = 1;
                            output[3] = Integer.toString(aqq);
                        }

                        //might be used or ignored
                        if (qaaa.split("=")[0].equals( "		buildings" ) ) {
                            output[4] = qaaa.split("=")[1];
                            flag = 1; //end loop

                        }

                    }

                }
            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        return output;

    }

    public static String[] importCountry (String name, int natIDnum) throws IOException
    {

        String tab = "	";

        String VQ2 = "{}q";

        String bracket1 = VQ2.substring(0,1);
        String bracket2 = VQ2.substring(1,2);
        String tagID = Integer.toString(natIDnum);
        //System.out.println ("Load 1 done");
        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;
        //double q = 0.0;

        //String idStr = provID.toString();

        String startWord = "	country_database={";
        String endWord = "	state_database={";

        String keyWord = "		"+tagID+"={";

        int aqq = 0;
        boolean endOrNot = true;
        String vmm = scnr.nextLine();
        String qaaa = vmm;
        String[] output;   // Owner Culture Religeon PopTotal Buildings
        output = new String[21];

        output[0] = "9999"; //default for no owner, uncolonized province
        output[1] = "noCulture"; //default for no culture, uncolonized province with 0 pops
        output[2] = "noReligion"; //default for no religion, uncolonized province with 0 pops
        output[12] = "0"; //default for no researcher, will either be a random character in CKII or no character 
        output[13] = "0"; //default for no researcher, will either be a random character in CKII or no character 
        output[14] = "0"; //default for no researcher, will either be a random character in CKII or no character 
        output[15] = "0"; //default for no researcher, will either be a random character in CKII or no character
        output[16] = "0"; //default for no leader, will either be a random character in CKII or no character
        output[20] = "none"; //default for no governors/governorships, land will be directly held by the ruler

        try {
            while (endOrNot = true){

                qaaa = scnr.nextLine();
                if (vmm.equals(startWord)){

                    endOrNot = false;  
                    while (flag == 0) {
                        qaaa = scnr.nextLine(); 

                        if (qaaa.equals(keyWord)){
                            flag = 1;
                            while (flag == 1) {
                                qaaa = scnr.nextLine();
                                if (qaaa.split("=")[0].equals( "			tag" ) ) {
                                    output[0] = qaaa.split("=")[1];
                                    output[0] = output[0].substring(1,output[0].length()-1);
                                }
                                else if (qaaa.split("=")[0].equals( "			country_name" ) ) {
                                    qaaa = scnr.nextLine(); 
                                    output[19] = qaaa.split("=")[1];
                                    output[19] = output[19].substring(1,output[19].length()-1);
                                    if (output[19].equals("CIVILWAR_FACTION_NAME")) {
                                        qaaa = scnr.nextLine();
                                        if (qaaa.split("=")[0].equals(tab+tab+tab+tab+"adjective")) {
                                            qaaa = scnr.nextLine();    
                                        }
                                        if (qaaa.split("=")[0].equals(tab+tab+tab+tab+"base")) {

                                            qaaa = scnr.nextLine();
                                            qaaa = qaaa.split("=")[1];
                                            qaaa = qaaa.substring(1,qaaa.length()-1);
                                            output[19] = "CIVILWAR-"+qaaa;
                                        }
                                    }

                                    //System.out.println ("Load 3 done");
                                }

                                else if (qaaa.split("=")[0].equals( "				seed" ) ) {
                                    output[1] = qaaa.split("=")[1];
                                }
                                else if (qaaa.split("=")[0].equals( "			gender_equality" ) ) {
                                    output[2] = qaaa.split("=")[1]; //used for determining inheiratance laws
                                }
                                else if (qaaa.split("=")[0].equals( "			color" ) ) {

                                    output[3] = qaaa.split("			color2")[0];
                                    output[3] = qaaa.substring(15,output[3].length()-2);
                                }
                                else if (qaaa.split("=")[0].equals( "				gold" ) ) {
                                    output[4] = qaaa.split("=")[1];
                                }
                                else if (qaaa.split("=")[0].equals( "			capital" ) ) {
                                    output[5] = qaaa.split("=")[1];
                                }
                                else if (qaaa.split("=")[0].equals( "			primary_culture" ) ) {
                                    output[6] = qaaa.split("=")[1];
                                    output[6] = output[6].substring(1,output[6].length()-1);
                                }
                                else if (qaaa.split("=")[0].equals( "			religion" ) ) {
                                    output[7] = qaaa.split("=")[1];
                                    output[7] = output[7].substring(1,output[7].length()-1);
                                }
                                //Technology
                                else if (qaaa.split("=")[0].equals( "				military_tech" ) ) {
                                    if (qaaa.substring(qaaa.length()-14, qaaa.length()-13).equals( "c" )) {

                                        output[12] = qaaa.substring(33,qaaa.length()); //researcher
                                    }
                                    qaaa = scnr.nextLine();
                                    output[8] = qaaa.split("=")[1]; //technology
                                }
                                else if (qaaa.split("=")[0].equals( "				civic_tech" ) ) {
                                    if (qaaa.substring(qaaa.length()-14, qaaa.length()-13).equals( "c" )) {

                                        output[13] = qaaa.substring(30,qaaa.length()); //researcher
                                    }

                                    qaaa = scnr.nextLine();
                                    output[9] = qaaa.split("=")[1]; //technology
                                }
                                else if (qaaa.split("=")[0].equals( "				oratory_tech" ) ) {
                                    if (qaaa.substring(qaaa.length()-14, qaaa.length()-13).equals( "c" )) {

                                        output[14] = qaaa.substring(32,qaaa.length()); //researcher
                                    }

                                    qaaa = scnr.nextLine();
                                    output[10] = qaaa.split("=")[1]; //technology
                                }
                                else if (qaaa.split("=")[0].equals( "				religious_tech" ) ) {
                                    if (qaaa.substring(qaaa.length()-14, qaaa.length()-13).equals( "c" )) {

                                        output[15] = qaaa.substring(34,qaaa.length()); //researcher
                                    }

                                    qaaa = scnr.nextLine();
                                    output[11] = qaaa.split("=")[1]; //technology
                                }

                                else if (qaaa.split("=")[0].equals( "			monarch" ) ) {
                                    output[16] = qaaa.split("=")[1];

                                }
                                else if (qaaa.split("=")[0].equals( "			government_key" ) ) {
                                    output[17] = qaaa.split("=")[1];
                                    output[17] = output[17].substring(1,output[17].length()-1);
                                }
                                else if (qaaa.split("=")[0].equals( "			succession_law" ) ) {
                                    output[18] = qaaa.split("=")[1];

                                }

                                else if (qaaa.split("=")[0].equals( tab+tab+tab+"governorship" ) ) {
                                    qaaa = scnr.nextLine();
                                    String tempReg = qaaa.split("=")[1].substring(1,qaaa.split("=")[1].length()-1);
                                    qaaa = scnr.nextLine();
                                    if (qaaa.split("=")[0].equals (tab+tab+tab+tab+"governor")) {
                                        if (output[20].equals ("none") ) {
                                            output[20] = tempReg + "~" + qaaa.split("=")[1];
                                        }
                                        else {
                                            output[20] =  output[20] + "," + tempReg + "~" + qaaa.split("=")[1];   
                                        }
                                    }

                                }

                                else if (qaaa.split("=")[0].equals( "				budget_dates" ) ) {
                                    flag = 2; //end loop

                                }

                            }

                        }
                    }   

                }
            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        return output;

    }

    public static String[] importConvList (String name, int provIDnum) throws IOException //Checks old format first, then new format
    {

        String provID = Integer.toString(provIDnum);
        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;

        boolean endOrNot = true;

        String qaaa;
        String[] output;   // Owner Culture Religeon PopTotal Buildings
        output = new String[2];

        output[0] = "peq"; //default for no owner, uncolonized province
        output[1] = "99999"; //default for no culture, uncolonized province with 0 pops

        try {
            while (endOrNot = true){

                qaaa = scnr.nextLine();

                if (qaaa.split(",")[0].equals(provID)){
                    endOrNot = false;
                    output[0] = qaaa.split(",")[0];
                    output[1] = qaaa.split(",")[1];

                }

            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        return output;

    }

    public static String[] importCultList (String name, String provIDnum) throws IOException
    {

        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;

        boolean endOrNot = true;

        String qaaa;
        String[] output;   // Owner Culture Religeon PopTotal Buildings
        output = new String[2];

        output[0] = "peq"; //default for no owner, uncolonized province
        output[1] = "99999"; //default for no culture, uncolonized province with 0 pops

        try {
            while (endOrNot = true){

                qaaa = scnr.nextLine();

                if (qaaa.split(",")[0].equals(provIDnum)){
                    endOrNot = false;
                    output[0] = qaaa.split(",")[0];
                    output[1] = qaaa.split(",")[1];

                }
            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        return output;

    }

    public static String[] importLocalisation (String directory, String tag, String dynasty) throws IOException
    {

        String VM = "\\";
        VM = VM.substring(0);
        char quote = '"';
        String[] output;
        output = new String[2];
        String provOrName = "debug";
        String revoltName = "no";
        int usesDynasty = 0;

        try { 
            if (tag.split("-")[0].equals("CIVILWAR")) { //For the opposing side of a country undergoing civil war
                revoltName = tag.split("-")[1];
            }
        }catch (java.lang.NullPointerException exception){
            revoltName = "no";
        }

        if (dynasty.equals("00Region00")) {

            output = importRegionLocalisation(directory,tag);    

        }

        else if (revoltName.equals("no")) {

            try { 
                provOrName = tag.split("OV")[0];

            }catch (java.lang.NullPointerException exception){
                provOrName = "debug2";

            }

            if (provOrName.equals ("PR")) {
                output = importProvLocalisation(directory,tag);
            }
            else {  

                String name = directory + VM + "game" + VM + "localization" + VM + "english" + VM + "countries_l_english.yml";

                FileInputStream fileIn= new FileInputStream(name);
                Scanner scnr= new Scanner(fileIn);

                int flag = 0;

                boolean endOrNot = true;

                String qaaa;
                qaaa = scnr.nextLine();

                output[0] = tag; //default for no name, will just use tag ID
                output[1] = tag; //default for no adjective, will just use tag ID

                try {
                    while (endOrNot = true){

                        qaaa = scnr.nextLine();

                        if (qaaa.split(":")[0].equals(" "+tag)){
                            output[0] = qaaa.split(":")[1];
                            output[0] = output[0].substring(3,output[0].length()-1);

                        }

                        if (qaaa.split(":")[0].equals(" "+tag+"_ADJ") || qaaa.split(":")[0].equals(" "+tag+"_ADJECTIVE")){
                            endOrNot = false;
                            output[1] = qaaa.split(":")[1];
                            output[1] = output[1].substring(3,output[1].length()-1);

                        }
                    }

                }catch (java.util.NoSuchElementException exception){
                    endOrNot = false;

                }   

            }

            if (output[0].equals (tag) && usesDynasty != 1) {
                output = importFormableLocalisation(directory,tag);   
            }

        }
        else {

            String[] revoltNames = importLocalisation (directory, revoltName, dynasty);
            output[0] = revoltNames[1] + " Revolt";
            output[1] = revoltNames[1] + " Revolter";
        }

        if (output[0].charAt(0) == '[') { //For countries which use a dynasty name for their country, like the Seleukid Empire
            output[1] = dynasty;
            if (output[1].charAt(output[1].length()-1) == 'a') {
                output[1] = output[1] + "n";    
            }
            output[0] = output[1] + " Empire"; //may change out Empire for country rank
        } else if (output[0].equals(tag)) {
            output = importAreaLocalisation(directory,tag);    
        } 
        
        if (output[0].equals(tag)) { //Mod support
            output = importCustCountryLocalisation (tag);    
        }

        if (output[0].equals(tag)) {
            output = importRegionLocalisation(directory,tag);    
        }

        return output;

    }

    public static String[] importProvLocalisation (String directory, String tag) throws IOException
    {

        String VM = "\\";
        VM = VM.substring(0);
        char quote = '"';
        String name = directory + VM + "game" + VM + "localization" + VM + "english" + VM + "provincenames_l_english.yml";
        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;

        boolean endOrNot = true;
        String qaaa;
        qaaa = scnr.nextLine();
        String[] output;   // Owner Culture Religeon PopTotal Buildings
        output = new String[2];

        output[0] = tag; //default for no prov name, will just use prov ID
        output[1] = tag; //default for no prov adjective, will just use prov ID
        String idNum;

        try {
            while (endOrNot = true){

                qaaa = scnr.nextLine();

                if (qaaa.split(":")[0].equals(" "+tag)){
                    endOrNot = false;
                    output[0] = qaaa.split(":")[1];
                    output[0] = output[0].substring(3,output[0].length()-1);
                    output[1] = output[0];
                    if (output[1].charAt(output[1].length()-1) == 'a') {
                        output[1] = output[1] + "n";    
                    }

                }

            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        return output;

    }

    public static String[] importAreaLocalisation (String directory, String tag) throws IOException
    {

        String VM = "\\";
        VM = VM.substring(0);
        char quote = '"';
        String name = directory + VM + "game" + VM + "localization" + VM + "english" + VM + "regionnames_l_english.yml";
        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;

        boolean endOrNot = true;
        String qaaa;
        qaaa = scnr.nextLine();
        String[] output;   // Owner Culture Religeon PopTotal Buildings
        output = new String[2];

        output[0] = tag; //default for no prov name, will just use prov ID
        output[1] = tag; //default for no prov adjective, will just use prov ID
        String idNum;

        try {
            while (endOrNot = true){

                qaaa = scnr.nextLine();

                if (qaaa.split(":")[0].equals(" "+tag)){
                    endOrNot = false;
                    output[0] = qaaa.split(":")[1];
                    output[0] = output[0].substring(3,output[0].length()-1);
                    output[1] = output[0];
                    if (output[1].charAt(output[1].length()-1) == 'a') {
                        output[1] = output[1] + "n";    
                    }

                }

            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        return output;

    }
    
    public static String[] importCustCountryLocalisation (String tag) throws IOException //Supported Loc for popular IR mods
    {

        String VM = "\\";
        VM = VM.substring(0);
        char quote = '"';
        String name = "supportedModLoc.txt";
        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;

        boolean endOrNot = true;
        String qaaa;
        qaaa = scnr.nextLine();
        String[] output;
        output = new String[2];

        output[0] = tag; //default for no prov name, will just use prov ID
        output[1] = tag; //default for no prov adjective, will just use prov ID
        String idNum;

        try {
            while (endOrNot = true){

                qaaa = scnr.nextLine();

                if (qaaa.split(":")[0].equals(" "+tag)){
                    endOrNot = false;
                    output[0] = qaaa.split(":")[1];
                    output[0] = output[0].substring(3,output[0].length()-1);
                    output[1] = output[0];
                    if (output[1].charAt(output[1].length()-1) == 'a') {
                        output[1] = output[1] + "n";    
                    }

                }

            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        return output;

    }

    public static String[] importRegionLocalisation (String directory, String tag) throws IOException
    {

        String VM = "\\";
        VM = VM.substring(0);
        char quote = '"';
        String name = directory + VM + "game" + VM + "localization" + VM + "english" + VM + "macroregions_l_english.yml";
        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;

        boolean endOrNot = true;
        String qaaa;
        qaaa = scnr.nextLine();
        String[] output;   // Owner Culture Religeon PopTotal Buildings
        output = new String[2];

        output[0] = tag; //default for no prov name, will just use prov ID
        output[1] = tag; //default for no prov adjective, will just use prov ID
        String idNum;

        try {
            while (endOrNot = true){

                qaaa = scnr.nextLine();

                if (qaaa.split(":")[0].equals(" "+tag)){
                    endOrNot = false;
                    output[0] = qaaa.split(":")[1];
                    output[0] = output[0].substring(3,output[0].length()-1);
                    output[1] = output[0];
                    if (output[1].charAt(output[1].length()-1) == 'a') {
                        output[1] = output[1] + "n";    
                    }

                }

            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        return output;

    }

    public static String[] importFormableLocalisation (String directory, String tag) throws IOException
    {

        String VM = "\\";
        VM = VM.substring(0);
        char quote = '"';
        String[] output;   // Owner Culture Religeon PopTotal Buildings
        output = new String[2];
        String provOrName = "debug";

        String name = directory + VM + "game" + VM + "localization" + VM + "english" + VM + "nation_formation_l_english.yml";

        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;

        boolean endOrNot = true;
        String qaaa;
        qaaa = scnr.nextLine();
        String tag2 = tag;

        try {
            tag2 = tag.split("_NAM")[0];  
        }

        catch (java.lang.NullPointerException exception){

        }

        output[0] = tag; //default for no name, will just use tag ID
        output[1] = tag; //default for no adjective, will just use tag ID

        try {
            while (endOrNot = true){

                qaaa = scnr.nextLine();

                if (qaaa.split(":")[0].equals(" "+tag)){
                    output[0] = qaaa.split(":")[1];
                    output[0] = output[0].substring(3,output[0].length()-1);

                }

                if (qaaa.split(":")[0].equals(" "+tag+"_ADJ") || qaaa.split(":")[0].equals(" "+tag+"_ADJECTIVE") || 
                qaaa.split(":")[0].equals(" "+tag2+"_ADJ") || qaaa.split(":")[0].equals(" "+tag2+"_ADJECTIVE")){
                    endOrNot = false;
                    output[1] = qaaa.split(":")[1];
                    output[1] = output[1].substring(3,output[1].length()-1);
                }
            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        return output;

    }

    public static ArrayList<String> importModLocalisation (String directory) throws IOException
    {

        String VM = "\\";
        VM = VM.substring(0);
        ArrayList<String> oldFile = new ArrayList<String>();
        char quote = '"';

        String name = directory + VM + "localisation" + VM + "converted_title_localisation.csv";

        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;
        boolean endOrNot = true;
        String qaaa;

        try {
            while (endOrNot = true){

                qaaa = scnr.nextLine();

                oldFile.add(qaaa);

            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        return oldFile;

    }
    //developed originally by Shinymewtwo99
}