package Compiler;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Parser {
    
    static ArrayList<Token> tokens;
    static Token token;
    

    public static void main(String[] args){
        
        Lex lex = new Lex("C:\\Users\\kavou\\Desktop\\lex.txt");
        System.out.println("lexical correct program");
        tokens = lex.getTokens();
        
        token=next();
        program();
        System.out.println("syntactically correct program");
    } 

    
    private static Token next(){
        //Παίρνουμε το πρώτο Token απο τη λίστα Tokens και βάζουμε στη μεταβλητή token
        token = tokens.get(0);
        //Στη συνέχεια το αφαιρούμε απο τη λίστα
        tokens.remove(0);
        return token;
    }
    
    public static void error(String s){ 
        //Εμφανίζει μήνυμα λάθους
        System.out.println("ERROR: " +s);
        //Και τερματίζει το πρόγραμμα
        System.exit(0);
    }
    
    public static void program(){
        //Ελέγχουμε αν ξεκινάει με τη λέξη program
        if (token.type.name()=="programTK"){
            token=next();
            //Και αν στη συνέχεια ακολουθάει το όνομα του 
            //προγράμματος (το οποίο είναι τύπου variable)
            if(token.type.name()=="variableTK"){
                token=next();
                //Αφού έγιναν οι έλεγχοι, προχωράμε στη μέθοδο block
                block();
            }  
            //Αλλιώς εμφανίζεται μήνυμα λάθους
            else {
                error("variable expected after keyword program, error at line " +token.line);
            }
        }
        //Αλλιώς εμφανίζεται μήνυμα λάθους
        else {
            error("keyword program expected, error at line " +token.line);
        }   
    }
    
    
    public static void block(){
        //Ελέγχουμε αν ξεκινάει με τη λέξη begin
        if (token.type.name()=="beginTK"){
            token=next();
            //Αφού έγινε ο έλεγχος προχωράμε στη μέθοδο declarations
            declarations();
            //Και στη συνέχεια στη sequence
            sequence();
        }
        //Αλλιώς εμφανίζεται μήνυμα λάθους
        else {
            error("keyword begin expected, error at line " +token.line);
        }
    }
    

    public static void declarations(){
        //Ελέγχουμε αν ξεκινάει με το declare
        if (token.type.name()=="declareTK"){
            token=next();
            //Αφού έγινε ο έλεγχος προχωράμε στη μέθοδο varlist
            varlist();
                //Μετά την εκτέλεση της varlist, ελέγχουμε αν υπάρχει η λέξη enddeclare
                if (token.type.name()=="enddeclareTK"){
                    //Αν υπάρχει επιστρέφουμε στην block (για να συνεχίσει στη sequence)
                    token=next();
                } 
            //Αλλιώς εμφανίζεται μήνυμα λάθους    
            else {
                error("keyword enddeclare expected after keyword declare, error at line " +token.line);
            }            
        }
    }
 
    public static void varlist(){
        //Ελέγχουμε αν ξεκινάει με μεταβλητή. Αν όχι δεν εμφανίζεται 
        //μήνυμα λάθους, γιατί η varlist έχει το δικαίωμα να μην περιέχει τίποτα.
        if(token.type.name()=="variableTK"){
            token=next();
        }
        //Αν συναντήσουμε κόμμα
        while (token.type.name()=="commaTK"){
            token=next();
                //Πρέπει υποχρεωτικά να ακολουθεί μεταβλητή
                if(token.type.name()=="variableTK"){
                    token=next();
                }
                //Αλλιώς εμφανίζεται μήνυμα λάθους
                else{
                    error("variable expected after comma, error at line " +token.line);
            }
        }
    }   
    
    public static void sequence(){
        //Καλείται η statement
        statement();
        //Αν συναντήσουμε ελληνικό ερωτηματικό
        while (token.type.name()=="semicolTK"){
            
            //Προχωράμε στο επόμενο token
            token=next();
            //Και ξανακαλολυμε τη statement
            statement();
        }
    }
    
    public static void statement(){
        //Ελέγχουμε ποιο είναι το πρώτο token που συναντάμε 
        //και καλούμε την αντίστοιχη μέθοδο.
        //Αν δεν συναντήσουμε τίποτα, δεν εμφανίζεται μήνυμα λάθους
        //γιατί η statement έχει το δικαίωμα να μην περιέχει τίποτα.
        
        if (token.type.name()=="inputTK"){    
            token=next();
            input_stat();
        }
        
        else if (token.type.name()=="variableTK")
        {
            token=next();
            assignment_stat();
        }
        
        else if (token.type.name()=="printTK")
        {
            token=next();
            print_stat();
        }
        
        else if(token.type.name()=="endTK"){
            return;
        }
        
        //Εμφανίζεται μήνυμα λάθους μόνο όταν βρούμε κάποιο token, 
        //διαφορετικό απο τα παραπάνω τέσσερα
        else{
            error("not valid character, error at line " +token.line);
        }
    }
    
    public static void input_stat(){
            //Μετά το input ελέγχουμε αν υπάρχει αριστερή παρένθεση
            if(token.type.name()=="leftpTK"){
                //Και προχωράμε στο επόμενο token
                token=next();
            }
            //Αλλιώς εμφανίζεται μήνυμα λάθους
            else{
                error("expected left parenthesis after keyword input, error at line " +token.line);
            }
            
            //Στη συνέχεια ελέγχουμε αν μετά την αριστερή παρένθεση εμφανίζεται μεταβλητή
            if(token.type.name()=="variableTK"){
                //Και προχωράμε στο επόμενο token
                token=next();
            }
            //Αλλιώς εμφανίζεται μήνυμα λάθους
            else{
                error("expected variable inside input(), error at line " +token.line);
            }
            
            //Τέλος ελέγχουμε αν μετά τη μεταβλητή εμφανίζεται δεξιά παρένθεση
            if(token.type.name()=="rightpTK"){
                //Και προχωράμε στο επόμενο token
                token=next();
            }
            //Αλλιώς εμφανίζεται μήνυμα λάθους
            else{
                error("expected right parenthesis after variable, error at line " +token.line);
            }
    }
    
    public static void assignment_stat(){
        //Μετά τη μεταβλητή ελέγχουμε αν υπάρχει το token "assignmentTK" (:=)
        if(token.type.name()=="assignTK"){
            //Και προχωράμε στο επόμενο token
            token=next();
            //Και στη συνέχεια καλούμε τη μεθοδο expression
            expression();
        }
        //Αλλιώς εμφανίζεται μήνυμα λάθους
        else{
            error(":= expected after variable, error at line " +token.line);
        }
        
    }
    
    public static void print_stat(){
        //Μετά το print ελέγχουμε αν υπάρχει αριστερή παρένθεση
        if(token.type.name()=="leftpTK"){
            //Και παίρνουμε το επόμενο token
            token=next();       
                //Όσο δεν βρίσκουμε δεξιά παρένθεση, καλούμε την expression
                while(token.type.name()!="rightpTK"){
                    expression();
                }
                //Όταν βρούμε δεξιά παρένθεση, παίρνουμε το επόμενο token
                token=next();
        }
        //Εμφανίζεται μήνυμα λάθους αν δεν ξεκινάει με αριστερή παρένθεση μετά το print
        else{
            error("expected left parenthesis after keyword print, error at line " +token.line);
        }
    }
    
    public static void expression(){
        //Καλείται η optional_sign
        optional_sign(); 
        //Και στη συνέχεια η term
        term();

        //Όσες φορές βρούμε σύν ή πλήν
        while(token.type.name()=="plusTK" || token.type.name()=="minusTK"){
            //Καλούμε την add_oper 
            add_oper();
        }
    }
    
    public static void term(){
        //Καλείται η factor
        factor();
        
        //Όσες φορές βρούμε επί ή δια
        while(token.type.name()=="multTK" || token.type.name()=="divTK"){
            //Καλούμε την Mul_oper
            mul_oper();
         }
    }
    
    public static void factor(){      
        //Αν βρούμε constant ή variable
        if(token.type.name()=="constantTK" || token.type.name()=="variableTK")
        {   
            //Παίρνουμε το επόμενο token. Ο έλεγχος επιστρέφει στην term
            token=next();
        }
        
        //Αν βρούμε αριστερή παρένθεση
        else if(token.type.name()=="leftpTK"){
            //Παίρνουμε το επόμενο token
            token=next();        
                //Όσο δεν βρίσκουμε δεξιά παρένθεση καλούμε την expression.
                while(token.type.name()!="rightpTK"){
                    expression();
                }
                //Αν βρούμε δεξιά παρένθεση, παίρνουμε το επόμενο token.
                //Ο έλεγχος επιστρέφει στην term
                token=next();
        }
        //Αν δεν βρούμε ένα απ' τα προηγούμενα τρία token, τότε επιστρέφεται μήνυμα λάθους
        else{
            error("constant or variable or (EXPRESSION) expected, error at line " +token.line);
        }
    }
    
    public static void add_oper(){
        //Παίρνουμε το επόμενο token
        token=next();
        //Και καλούμε την term
        term();
    }
    
    public static void mul_oper(){
        //Παίρνουμε το επόμενο token
        token=next();
        //Και καλούμε την factor
        factor();
    }
    
    public static void optional_sign(){
        //Αν βρούμε σύν ή πλήν
        if(token.type.name()=="plusTK" || token.type.name()=="minusTK"){
            //Καλούμε την add_oper
            add_oper();
        }
    }
}