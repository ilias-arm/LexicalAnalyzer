package Compiler;

public enum TokenType {
    
        whitespaceTK("[ \t\f\r]+"),		//λευκός χαρακτήρας
        newlineTK("\n"),                        // αλλαγή γραμμής             
        constantTK("-?\\d+\\.\\d+|-?\\d+"),     // αριθμητική σταθερά
        programTK("\\bprogram\\b"),             // δεσμευμένη λέξη program
        declareTK("\\bdeclare\\b"),             // δεσμευμένη λέξη declare
        enddeclareTK("\\benddeclare\\b"),       // δεσμευμένη λέξη enddeclare
        beginTK("\\bbegin\\b"),                 // δεσμευμένη λέξη begin
        endTK("\\bend\\b"),			// δεσμευμένη λέξη end
        printTK("\\bprint\\b"),                 // δεσμευμένη λέξη print
        inputTK("\\binput\\b"),                 // δεσμευμένη λέξη input
        variableTK("\\w+|\\w+\\d+"),            // μεταβλητή
        plusTK("\\+"),                          // σύμβολο πρόσθεσης
        minusTK("\\-"),                         // σύμβολο αφαίρεσης
        multTK("\\*"),                          // σύμβολο πολλαπλασιασμού
        divTK("\\/"),                           // σύμβολο διαίρεσης
        leftpTK("\\("),                 	// σύμβολο αριστερής παρένθεσης
        rightpTK("\\)"),                	// σύμβολο δεξιάς παρένθεσης
        assignTK("\\:="),               	// σύμβολο εκχώρησης
        semicolTK("\\;"),                       // ελληνικό ερωτηματικό
        commaTK("\\,"),                         // κόμμα
        unknownTK("\\W|\\D|\\S"),               // άγνωστος χαρακτήρας, 
                                                // θα οδηγεί σε μήνυμα σφάλματος 
        eofTK("\\Z"),                           // τέλος προγράμματος
        ;
        
        public final String pattern;
        
        private TokenType(String pattern) {
            this.pattern = pattern;
        }    

}
