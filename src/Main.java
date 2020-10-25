
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try{
            while(true) {
                ArrayList<String> words = new ArrayList<>();
                Scanner in = new Scanner(System.in);
                Automaton automaton = new Automaton(new File("inputAutomate.txt"));
                System.out.print("������ �������: ");
                String lexema = in.nextLine();
                for(String a:lexema.split(" ")){
                    words.add(a.concat(" "));
                }
                if(words.contains("/* ") && words.contains("*/ ")){
                    int i1=words.indexOf("/* ");
                    int i2=words.indexOf("*/ ");
                    ArrayList<String> com= new ArrayList<>();
                    for(int k=i1; k<=i2;k++){
                        com.add(words.get(k));
                    }
                    words.removeAll(com);
                    continue;
                }
                int i = 0;
                for(String wrd:words){
                    if(wrd.matches("(\\s+)")){continue;}
                    automaton.w = wrd.toCharArray();

                    automaton.find();
                    StringBuilder text = new StringBuilder();
                       switch (automaton.kind.get(i)) {
                           case "comments" -> {
                               System.out.println(new String(text));
                               int i3=words.indexOf("// ");
                               for(int k=i3+1; k<=words.size();k++){
                                   words.remove(k);
                               }
                           }
                           case "strings" -> text.append("< ").append(wrd).append("> - <����: ������ ���������>\n");
                           case "chars" -> text.append("< ").append(wrd).append("> - <����: �������� ���������>\n");
                           case "numbers" -> text.append("< ").append(wrd).append("> - <����: �����>\n");
                           case "punctuation" -> text.append("< ").append(wrd).append("> - <����: ������� �����>\n");
                           case "operators" -> text.append("< ").append(wrd).append("> - <����: ���������>\n");
                           case "identifier" -> text.append("< ").append(wrd).append("> - <����: ��������������>\n");
                           case "reserved" -> text.append("< ").append(wrd).append("> - <����: ������������ �����>\n");
                           case "unknown" -> text.append("< ").append(wrd).append("> - <������������ �������>\n");
                           default -> throw new IllegalStateException("Unexpected value: " + automaton.kind.get(i));
                       }
                        i += 1;
                       System.out.println(new String(text));
                    }
                }
            } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }

}
