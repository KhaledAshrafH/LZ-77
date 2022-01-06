package lz77;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import static java.lang.Integer.max;
import java.util.ArrayList;
import java.util.Scanner;
public class LZ77 {
    
    public static String FRead(File file) throws Exception {
        FileInputStream read = new FileInputStream(file);
        int i=0;String s="";
        while ((i=read.read())!=-1){
            s=s+(char)i;
        }
        read.close();
        return s;
    }

    public static void FWrite(File file,String r) throws Exception {
        FileOutputStream write = new FileOutputStream(file);
        String name = r;
        byte[] names = name.getBytes();
        write.write(names);
        write.close();
    }
    
    public static void LZ77Decompress(ArrayList<String> tags) throws IOException, Exception{
        File file = new File("DeCompression\\file.txt");
        file.createNewFile();
        int current=-1,Temp=0;
        String result="";
        for(int i=0; i<tags.size(); i++) {
            current++;
            Temp=current;
            String posStr="",lengthStr="";
            int k=1;
            String subStr=tags.get(i);
            while(subStr.charAt(k) != ',') {
                posStr=posStr+subStr.charAt(k);
                k++;
            }
            int l=k+1;
            while(subStr.charAt(l) != ',') {
                lengthStr=lengthStr+subStr.charAt(l);
                l++;
            }
            int e=l+1;
            if(subStr.charAt(1)=='0' && subStr.charAt(3)=='0' ) result=result+subStr.charAt(e);
            else {
                String part="";
                int pos=Integer.parseInt(posStr);
                int length=Integer.parseInt(lengthStr);
                Temp-=pos;

                for(int j=Temp; j<Temp+length; j++){
                    part=part+result.charAt(j);
                }

                if(subStr.charAt(e)!='N') part=part+subStr.charAt(e);
                current=current+part.length()-1;
                result=result+part;
            }
        }
        System.out.println("        Tags\n       ------");
        for (int i = 0; i < tags.size(); i++) {
            System.out.print("       ");
            String posStr="",lengthStr="";
            int k=1;
            String PrintSubStr=tags.get(i);
            while(PrintSubStr.charAt(k) != ',') {
                posStr=posStr+PrintSubStr.charAt(k);
                k++;
            }

            int l=k+1;
            while(PrintSubStr.charAt(l) != ',') {
                lengthStr=lengthStr+PrintSubStr.charAt(l);
                l++;
            }

            int e=l+1;

            for (int j = 0; j < tags.get(i).length(); j++) {
                if(j==e) System.out.print(PrintSubStr.charAt(j));
                else System.out.print(PrintSubStr.charAt(j));
            }

            System.out.println();
        }
        System.out.println();
        System.out.println("Decompressed Text : "+result);
        FWrite(file,result);
    }

    public static void LZ77Compress(String str) throws IOException, Exception{
        File compressed = new File("Compression\\fileCompressed.txt");
        compressed.createNewFile();
        
        ArrayList<ArrayList<String>> tags=  new ArrayList<>();
        ArrayList<String> parts= new ArrayList<>();

        String check="";
        String Buffer="";
        String finalResult="";
        int COUNT=0;
        for(int i=0;i<str.length();i++){
            check=check+str.charAt(i);
            int index=Buffer.indexOf(check);

            if(i==str.length()-1){
                ArrayList<String> tag= new ArrayList<>();
                String existPart="",notExistPart="";
                int ptr2=i,ptr1=i-COUNT;
                for(int j=ptr1; j<ptr2; j++) existPart=existPart+str.charAt(j);
                for(int j=ptr1; j<=ptr2; j++) notExistPart=notExistPart+str.charAt(j);
                int lastIndex=Buffer.lastIndexOf(existPart);

                String firstPart=Integer.toString(ptr1-lastIndex);
                String secondPart=Integer.toString(COUNT);
                tag.add(firstPart);
                tag.add(secondPart);
                String nextChar="";
                nextChar=nextChar+str.charAt(i-1);
                tag.add(nextChar);
                tags.add(tag);
                Buffer+=check;
                parts.add(check);
                finalResult=finalResult+nextChar;
                break;
            }
            if(index==-1 && COUNT==0){
                ArrayList<String> tag= new ArrayList<>();
                tag.add("0");
                tag.add("0");
                String nextChar="";
                nextChar=nextChar+str.charAt(i);
                tag.add(nextChar);
                tags.add(tag);
                Buffer=Buffer+check;
                parts.add(check);
                check="";
                COUNT=0;
                finalResult=finalResult+nextChar;
            }
            else if(index==-1 && COUNT>0){
                ArrayList<String> tag= new ArrayList<>();
                String existPart="",notExistPart="";
                int ptr2=i,ptr1=i-COUNT;
                for(int j=ptr1; j<ptr2; j++) existPart=existPart+str.charAt(j);//ABAA
                for(int j=ptr1; j<=ptr2; j++) notExistPart=notExistPart+str.charAt(j);//AA
                int poss=Buffer.indexOf(existPart);
                String firstPart=Integer.toString(ptr1-poss);
                String secondPart=Integer.toString(COUNT);
                tag.add(firstPart);
                tag.add(secondPart);
                String nextChar="";
                nextChar=nextChar+str.charAt(i);
                tag.add(nextChar);
                tags.add(tag);
                Buffer=Buffer+check;
                parts.add(check);
                check="";
                COUNT=0;
                finalResult=finalResult+nextChar;
            }
            else {
                COUNT++;
            }
        }
        FWrite(compressed,finalResult);
        int max1=(int) -1e8,max2=(int) -1e8;

        for (int i = 0; i < tags.size(); i++) {
            for (int j = 0; j < tags.get(i).size(); j++) {
                int num=Integer.parseInt(tags.get(i).get(0));
                max1=max(max1,num);
            }
        }

        for (int i = 0; i < tags.size(); i++) {
            for (int j = 0; j < tags.get(i).size(); j++) {
                int num=Integer.parseInt(tags.get(i).get(1));
                max2=max(max2,num);
            }
        }
        
        int cnt1=0,cnt2=0;
        for (int i = 0; max1 > 0; max1 >>= 1) cnt1++;
        for (int i = 0; max2 > 0; max2 >>= 1) cnt2++;
        int first=cnt1,second=cnt2,third=8;

        System.out.println("        Tags\n       ------");
        for (int i = 0; i < tags.size(); i++) {
            for (int j = 0; j < tags.get(i).size(); j++) {
                if(j==0) System.out.print("      <");
                if(j!=2)System.out.print(tags.get(i).get(j)+",");
                else System.out.print('"'+tags.get(i).get(j)+'"');
                if(j==2) System.out.print("> ");
            }
            System.out.println();
        }
        System.out.println("\n        Parts\n       -------");

        for(int i=0; i<parts.size(); i++) System.out.print(parts.get(i)+" ");
        System.out.println("\n\n     Compare Size\n    --------------");
        System.out.println("Original Size = "+str.length() * 8+" Bits");
        System.out.println("Compressed Size = "+tags.size() * (first+second+third)+" Bits\n");
    }

    public static void main(String[] args) throws Exception {
        Scanner input=new Scanner(System.in); 
        System.out.println("1-Compression System\n2-Decompression System\n3-Exit");
        int choice=input.nextInt();
        if(choice==1){
            File file = new File("Compression\\file.txt");
            String str =FRead(file);
            System.out.println("----------------------\n| Compression System |\n----------------------");
            //System.out.print("Enter Content you want to Compress : ");
            //String str;//EX:ABAABABAABBBBBBBBBBBBABBBBBBBBABABABABABABAABABABABBABBABABABBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB
            //str=input.next();
            //Compression Function
            LZ77Compress(str);
            //--------------------
        }
        else if(choice==2){
            System.out.println("------------------------\n| Decompression System |\n------------------------");
            ArrayList<String> tags = new ArrayList<>();
            int n;
            String tag;
            System.out.print("Enter Number of tags : ");
            n=input.nextInt();
            System.out.println("Enter the tags in this form : <1,1,A>..");
            //EX:<0,0,A> <0,0,B> <2,1,A> <3,2,B> <5,3,B> <2,2,B> <5,5,B> <1,1,A>
            for(int i=0;i<n;i++){
                tag=input.next();
                tags.add(tag);
            }
            //Decompression Function
            LZ77Decompress(tags);
            //----------------------
        }
        else {
            System.exit(0);
        }
              
        
    }
}