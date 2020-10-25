

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Automaton {
    private final List<String> states; // стани
    private final String startState; // початковий стан
    private final List<String> finalStates; // кінцеві
    private final String[][] transitions; // переходи

    private String curr_state;
    public char[] w;
    public ArrayList<String> kind = new ArrayList<>();

    public int str_constanta_urr;

    Automaton(File input_file) throws FileNotFoundException {
        Scanner in = new Scanner(input_file);
        states = new ArrayList<>();
        String[] ss = in.nextLine().split("-");
        for (int i = Integer.parseInt(ss[0]); i <= Integer.parseInt(ss[1]); i++)
            states.add(String.valueOf(i));
        this.startState = in.nextLine().replace(" ", "");
        this.finalStates = new ArrayList<>();
        this.finalStates.addAll(Arrays.asList(in.nextLine().split(" ")));
        transitions = new String[states.size()][states.size()];
        while (in.hasNext()){
            String v = in.nextLine();
            String s_ = v.split(" ")[0];
            String a_ = v.split(" ")[1];
            String s2_ = v.split(" ")[2];
            transitions[states.indexOf(s_)][states.indexOf(s2_)] = a_;
        }
    }

    String move(String c){
        for (int i = 0; i < this . states.size(); i++){
            if (this.transitions[Integer.parseInt(this.curr_state)][i] == null) continue;
            Pattern p = Pattern.compile(this.transitions[Integer.parseInt(this.curr_state)][i]);
            Matcher m = p.matcher(c);
            if (m.matches() || (c.equals(" ") && this.transitions[Integer.parseInt(this.curr_state)][i].equals("\\\\s"))){
                this.curr_state = String.valueOf(i);
                return "ok";
            }
        }
        return "error";
    }

   public void find( ){
        this.curr_state = startState;
        int i = -1;
            while (i != w.length - 1) {
                i += 1;
                String c = String.valueOf(w[i]);
                String responce = this.move(c);
                if (responce.equals("ok")) {
                    if (this.finalStates.contains(this.curr_state)){
                        if (this.curr_state.equals("162")) {
                            this.kind.add("comments");
                        }
                        if (this.curr_state.equals("160")) {
                            this.kind.add("strings");
                        }
                        if (this.curr_state.equals("158")) {
                            this.kind.add("chars");
                        }
                        if (this.curr_state.equals("138")) {
                            this.kind.add("punctuation");
                        }
                        if (this.curr_state.equals("154")) {
                            this.kind.add("numbers");
                        }
                        if (this.curr_state.equals("8")) {
                            this.kind.add("reserved");
                        }
                        if (this.curr_state.equals("20")) {
                            this.kind.add("identifier");
                        }
                        if (this.curr_state.equals("149")) {
                            this.kind.add("operators");
                        }
                        break;
                    }

                }
               else {
                    this.kind.add("unknown");
                    break;
                }
            }
       }
  }
