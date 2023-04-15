package com.vuzz.delicc.frontend.lexer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class LexerLang {

    public ArrayList<Token> tokenCharsRegistries = new ArrayList<Token>();
    public ArrayList<Token> tokenReserved = new ArrayList<Token>();

    public TokenPath identifierPath = TokenPath.from("control","identifier");
    public TokenPath stringPath = TokenPath.from("value","string");
    public TokenPath skippablePath = TokenPath.from("control","skip");

    public String registerChar(TokenPath path, String ...characters) {
        for (String character : characters)
            tokenCharsRegistries.add(new Token(path,character));
        return path.path;
    }

    public String registerKeyword(TokenPath path, String character) {
        tokenReserved.add(new Token(path,character));
        return path.path;
    }


    public Token parseChar(String character) {
        Token token = parseKeyword(character);
        tokenCharsRegistries.forEach((t) -> {
            if(t.symbol.equals(character)) {
                token.symbol = t.symbol;
                token.id = t.id;
            }
        });
        return token;
    }

    public Token parseKeyword(String keyword) {
        Token token = new Token(identifierPath,keyword);
        tokenReserved.forEach((t) -> {
            if(t.symbol.equals(keyword)) {
                token.symbol = t.symbol;
                token.id = t.id;
            }
        });
        return token;
    }

    public String registerSkippable(String ...chars) {
        registerChar(skippablePath,chars);
        return skippablePath.path;
    }

    public Token[] parseString(String str) {
        ArrayList<Token> arr = new ArrayList<Token>();
        String[] chars = str.split("");
        List<String> charsArr = Arrays.asList(chars);
        int i = 0;
        while(i < charsArr.size()) {
            Token charToken = parseChar(charsArr.get(i++));

            if(charToken.id.path.startsWith("quote")) {
                String quote = charToken.symbol;
                StringBuilder identStr = new StringBuilder("");
                while(i != charsArr.size() && !charsArr.get(i).equals(quote)) {
                    identStr.append(charsArr.get(i++));
                }
                i++;
                charToken.id = stringPath;
                charToken.symbol = identStr.toString();
            }
            else if(charToken.id.equals(identifierPath)) {
                StringBuilder identStr = new StringBuilder(charToken.symbol);
                while(i != charsArr.size() && (isAlphabetic(charsArr.get(i)) || isInt(charsArr.get(i)))) {
                    identStr.append(charsArr.get(i++));
                }
                charToken = parseKeyword(identStr.toString());
            }
            else if(isInt(charToken.symbol)) {
                StringBuilder identStr = new StringBuilder(charToken.symbol);
                while(i != charsArr.size() && (isInt(charsArr.get(i)) || charsArr.get(i).equals("."))) {
                    identStr.append(charsArr.get(i++));
                }
                charToken.symbol = identStr.toString();
            }
            else if(charToken.id.equals(skippablePath)) {
                continue;
            }

            arr.add(charToken);
        }
        return arr.toArray(new Token[0]);
    }

    public boolean isAlphabetic(String str) { return str.toLowerCase() != str.toUpperCase(); }
    public boolean isInt(String str) { return str.matches("-?\\d+"); }

}
