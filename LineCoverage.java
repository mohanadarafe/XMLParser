import java.util.*;
import java.io.*; 
class LineCoverage{

    public static List<String> fileNames = new ArrayList<String>();

    public static void fetchFile(File file, HashMap map){
        String st;
        String fileName = "";
        int count = 0;
        int index = 0;
        ArrayList<String> lineCovered = new ArrayList<String>();
        ArrayList<Integer> newFile = differentFile(file);
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((st = br.readLine()) != null){
                fileName = st.substring(0, st.indexOf(" ")); 
                lineCovered.add(st.substring(st.indexOf(" ") + 1));
                ++count;
                if (count + 1 == newFile.get(index)){
                    map.put(fileName, new ArrayList<String>(lineCovered));
                    fileNames.add(fileName);
                    lineCovered.clear();
                    index++;
                }
            } 
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<Integer> differentFile(File file){
        ArrayList<Integer> newFileList = new ArrayList<Integer>();
        String st;
        String temp = "";
        int lineNum = 1;

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((st = br.readLine()) != null){
                if (lineNum == 1){ 
                    temp = st;
                }
                String fileName = st.substring(0, st.indexOf(" "));
                String tempFileName = temp.substring(0, temp.indexOf(" "));
                if (!fileName.equals(tempFileName)){
                    newFileList.add(lineNum);
                    temp = st;
                }
                lineNum++;   
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return newFileList;
    }

    public static void compareXML(HashMap jacoco, HashMap clover){
        System.out.println();
        for (String file : fileNames) {
            System.out.println("===============================");
            System.out.println("FILE: " + file);
            System.out.println("===============================");

            if (jacoco.get(file) == null || clover.get(file) == null) {
                continue;
            }

            List<String> jacocoNums = (List)jacoco.get(file);
            List<String> cloverNums = (List)clover.get(file);
            Set<String> set1 = new HashSet<String>(jacocoNums);
            Set<String> set2 = new HashSet<String>(cloverNums);

            set1.removeAll(cloverNums);
            set2.removeAll(jacocoNums);
            System.out.println("jacoco-ut covers lines " + set1 + " which are NOT in clover!");
            System.out.println("clover covers lines " + set2 + " which are NOT in jacoco-ut!");
            System.out.println("===============================");
        }
    }

    public static void main(String[] args) {
        File jacocoFile = new File("/Users/mohanadarafe/Desktop/Concordia/SOEN345/A1/q5/jacoco.txt");
        File cloverFile = new File("/Users/mohanadarafe/Desktop/Concordia/SOEN345/A1/q5/clover.txt");
        HashMap<String, ArrayList<String>> jacoco = new HashMap<String, ArrayList<String>>();
        HashMap<String, ArrayList<String>> clover = new HashMap<String, ArrayList<String>>();

        fetchFile(jacocoFile, jacoco);
        fetchFile(cloverFile, clover);
        compareXML(jacoco, clover);
    }

}