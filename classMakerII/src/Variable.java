class Variable {
    String type;
    String name;
    
    Variable(String t, String n) {
        type = t;
        name = n;
    }
    
    String getType() {return type;}
    String getName() {return name;}
    
    String modifiedName() {
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }
    
    String getterName() {
        return "get" + modifiedName() + "()"; 
    }
    
    String makeGetter() {
        String returnMe = "    public " + type + " " + getterName() + " {return " + name + ";}\n";
        return returnMe;
    }
    
    String makeSetter() {
        String returnMe = "    public " + "void" + " set" + modifiedName() + "(" + type + " " + name + ") { this." + name + " = " + name + ";}\n";
        return returnMe;
    }
    
    public String declaration() {
        return type + " " + name;
    }
    
    public String toString() {
        return "    protected " + declaration() + ";\n";   
    }
}