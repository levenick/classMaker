import java.util.*;

class ClassMaker {
    
    StringTokenizer st;
    String returnMe;
    String className;
    Vector variables = new Vector();
    
    String makeIt(String input) {
        returnMe = "";
        
        st = new StringTokenizer(input, " {};\n", true);
        
        handleClassName();
        
        //emitComment();
        emitDeclaration();
        
        handleVariables();
        
        emitVariables();
        emitConstructors();
        emitAccessors();
        
        emitToString();
        emitClose();
        
        return returnMe;
    }
    
    void handleClassName(){
        String classS = getNextToken();
        if (!classS.equals("class")) {
            emit("\n\noops, must start with class\n\n");
        }
        className = getNextToken();
    }
    
    void handleVariables(){
        String variableName = "";
        String type = "";
        for (type = getNextToken(); !type.equals("}");type = getNextToken()) {
            variableName = getNextToken();
            getNextToken();  // eat the semicolon
            Variable nuV = new Variable(type, variableName);
            variables.addElement(nuV);
            debuggingEmit("added " + nuV.toString());
        }
    }
    
    void emitComment(){
        emit("/*  " + className + ".java       \n\n");
        emit("This class is a prototype for...                     \n\n");
        emit("Author: Asoka\n");
        emit("Date: 1/1/-357\n");
        emit("Major modifications: none; first writing\n\t\t\t\t\t\t*/\n\n");
    }
    
    void emitDeclaration(){
        emit("public class " + className + " {\n\n");
        String whatever = getNextToken();
        /*while (whatever.charAt(0) != '{') {  // read and echo anything until the {
            emit("... " + whatever);
            whatever = getNextToken();
        }*/
    }
    
    void emitVariables(){
        for (Enumeration e=variables.elements(); e.hasMoreElements();) {
            emit(e.nextElement().toString());
        }
    }
    
    void emitConstructors(){
        emitDefaultConstructor();
        emitInitializingConstructor();
    }
    
    void emitDefaultConstructor() {
        emit("\n    ");
        emit("public " + className+"(){}   //empty default constructor\n");
    }
    
    void emitInitializingConstructor() {
        emit("\n    ");
        emit("public " + className+"(");
        
        for (Enumeration e=variables.elements(); e.hasMoreElements();) {
            Variable nextVariable = (Variable) e.nextElement();
            emit(nextVariable.declaration());
            if (e.hasMoreElements())
                emit(", ");
        }
        emit(") {   //initializing constructor\n");
         
        emit("        this();   // invoke the default constructor\n");
        for (Enumeration e=variables.elements(); e.hasMoreElements();) {
            Variable nextVariable = (Variable) e.nextElement();
            emit("        this." + nextVariable.getName() + " = " + nextVariable.getName() + ";\n");
        }
        emit("    }\n\n");
   }
    
    void emitAccessors(){
        for (Enumeration e=variables.elements(); e.hasMoreElements();) {
            emit(((Variable)e.nextElement()).makeGetter());
        }
        
        emit("\n");
        
        for (Enumeration e=variables.elements(); e.hasMoreElements();) {
            emit(((Variable)e.nextElement()).makeSetter());
        }
        
    }
    
    void emitToString() {
        emit("\n    public String toString() {\n");
        emit("        String returnMe = \"I am a " + className +": \";\n");
        for (Enumeration e=variables.elements(); e.hasMoreElements();) {
            Variable nextVariable = (Variable) e.nextElement();
            String vName = nextVariable.getName();
            emit("        returnMe += \"\\t" + vName + "=\" + " + nextVariable.getterName() + ";\n");
        }
        emit("        return returnMe;\n    } // toString()\n");
        
    }
    
    void emitClose() {
        emit("}  // " + className + "\n");   
    }
    
    void emit(String s) {
        if (DEBUGGING)
            System.out.println("emitting " + s);
        returnMe += s;
    }
    
    void errorEmit(String s) {
        System.out.println("Error! " + s);
    }
    
    boolean DEBUGGING = false;
    void debuggingEmit(String s) {
        if (DEBUGGING)
            emit(s);
    }
    
    String getNextToken() {
        if (!st.hasMoreTokens()) {
            errorEmit("oops!  all out of tokens!");
            errorEmit("output so far=" + returnMe);
            System.exit(1);
            return "string tokenizer is empty!";
        }
        
        String next = st.nextToken();
        while (Character.isWhitespace(next.charAt(0)) && st.hasMoreTokens()) {
            next = st.nextToken();
        } // eat white space, spit out next token
        
        if (next.equals("[]"))
            errorEmit("\n\n\nOops!!  No space allowed before []!!!!!\n\n\n");
        
        debuggingEmit("getNextToken returning " + next); 
        return next;
    }   
}