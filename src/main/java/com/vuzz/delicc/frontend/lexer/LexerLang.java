package com.vuzz.delicc.frontend.lexer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexerLang {

    public ArrayList<Token> tokenCharsRegistries = new ArrayList<>();
    public ArrayList<Token> tokenReserved = new ArrayList<>();
    public ArrayList<DoubleToken> tokenDoubleChar = new ArrayList<>();

    public TokenPath identifierPath = TokenPath.from("control","identifier");
    public TokenPath stringPath = TokenPath.from("value","string");
    public TokenPath skippablePath = TokenPath.from("control","skip");

    public String registerChar(TokenPath path, String ...characters) {
        for (String character : characters)
            tokenCharsRegistries.add(new Token(path,character));
        return path.path;
    }

    public String registerDoubleChar(TokenPath from, String s, String s1) {
        tokenDoubleChar.add(new DoubleToken(from,s,s1));
        return from.path;
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

    public static String replaceEscapes(String input) {
        String regex = "\\\\([btnfr\"'\\\\]|[0-7]{1,3}|u[0-9a-fA-F]{4})";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            String match = matcher.group(1);
            String replacement = unescape(match);
            matcher.appendReplacement(buffer, replacement);
        }
        matcher.appendTail(buffer);

        return buffer.toString();
    }

    private static String unescape(String escape) {
        switch (escape) {
            case "n": return "\n";
            case "t": return "\t";
            case "b": return "\b";
            case "f": return "\f";
            case "r": return "\r";
            case "\"": return "\"";
            case "'": return "'";
            case "\\": return "\\";
            default:
                if (escape.startsWith("u")) {
                    int codePoint = Integer.parseInt(escape.substring(1), 16);
                    return new String(Character.toChars(codePoint));
                } else {
                    int codePoint = Integer.parseInt(escape, 8);
                    return new String(Character.toChars(codePoint));
                }
        }
    }

    public Token[] parseString(String str) {
        ArrayList<Token> arr = new ArrayList<Token>();
        String[] chars = str.split("");
        List<String> charsArr = Arrays.asList(chars);
        int i = 0;
        while(i < charsArr.size()) {
            Token charToken = parseChar(charsArr.get(i++));
            Token charToken2 = i < charsArr.size() ? parseChar(charsArr.get(i)) : null;

            if(charToken.id.path.startsWith("quote")) {
                String quote = charToken.symbol;
                StringBuilder identStr = new StringBuilder();
                while(i != charsArr.size() && !charsArr.get(i).equals(quote)) {
                    if(charsArr.get(i).equals("\\")) {
                        i++; String charStr = charsArr.get(i++);
                        identStr.append("\\").append(charStr);
                        continue;
                    }
                    identStr.append(charsArr.get(i++));
                }
                i++;
                charToken.id = stringPath;
                charToken.symbol = replaceEscapes(identStr.toString());
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

            Token finalCharToken = charToken;
            final boolean[] hasChanged = {false};
            tokenDoubleChar.forEach((t) -> {
                if(charToken2 == null || charToken2.id.equals(skippablePath)) return;
                if(t.sym1.equals(finalCharToken.symbol) && t.sym2.equals(charToken2.symbol)) {
                    finalCharToken.id = t.toToken().id;
                    finalCharToken.symbol = t.toToken().symbol;
                    hasChanged[0] = true;
                }
            });
            if(hasChanged[0]) {
                charToken.id = finalCharToken.id;
                charToken.symbol = finalCharToken.symbol;
                i++;
            }

            arr.add(charToken);
        }
        return arr.toArray(new Token[0]);
    }

    public boolean isAlphabetic(String str) { return str.toLowerCase() != str.toUpperCase(); }
    public boolean isInt(String str) { return str.matches("-?\\d+"); }
}
