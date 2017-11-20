package Compiler;

//Κλάση Token
public class Token {
    
    //Μεταβλητές 
    public TokenType type;
    public String data;
    public Integer line;
    
    //Constuctor
    public Token(TokenType type, String data, Integer line){
        this.type = type;
        this.data = data;
        this.line = line;
    
    }
    
}

