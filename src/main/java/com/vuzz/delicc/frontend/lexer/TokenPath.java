package com.vuzz.delicc.frontend.lexer;

public class TokenPath {

    public String path = "token/nil";

    public TokenPath(String path) {
        this.path = path;
    }
    public static TokenPath from(String parent, String child) {
        return new TokenPath(parent+"/"+child);
    }

    public boolean equals(TokenPath obj) {
        return path.equals(obj.path);
    }
}
