package edu.montana.gsoc.msusel.patterns.plantuml;

import de.sciss.syntaxpane.Token;
import de.sciss.syntaxpane.TokenType;
import de.sciss.syntaxpane.lexers.DefaultJFlexLexer;

%%

%public
%class PumlLexer
%extends DefaultJFlexLexer
%final
%unicode
%char
%type Token

%{
    /**
     * Create an empty lexer, yyrset will be called later to reset and assign
     * the reader
     */
    public PumlLexer() {
        super();
    }

    @Override
    public int yychar() {
        return yychar;
    }

    private static final byte PARAN     = 1;
    private static final byte BRACKET   = 2;
    private static final byte CURLY     = 3;

%}

LineTerminator = \r|\n|\r\n
LineWhiteSpace = [\ \t\f]
WhiteSpace = ({LineWhiteSpace}|{LineTerminator})+

ALPHA=[:letter:]
DIGIT=[:digit:]

ID_BODY={ALPHA} | {DIGIT} | "_"
ID=({ID_BODY}) *
EXT_ID=({ID_BODY}|{LineWhiteSpace}|\\) *

QUOTE=\"
CHAR={ALPHA} | {DIGIT} | "_" | {LineWhiteSpace}
StringCharacter=[^\n\r\f\\\"]
STRING={QUOTE}({StringCharacter}|{LineWhiteSpace}|{LineTerminator})*{QUOTE}

%state INSIDE_COLONS
%state INSIDE_PARENTHESIS
%state STEREOTYPE
%state STRING
%state CHARLITERAL
%%

<YYINITIAL> {
    /* keywords */
    "@startuml"   |
    "@enduml"     |
    "actor"       |
    "boundary"    |
    "control"     |
    "database"    |
    "entity"      |
    "autonumber"  |
    "stop"        |
    "resume"      |
    "newpage"     |
    "alt"         |
    "else"        |
    "opt"         |
    "loop"        |
    "par"         |
    "break"       |
    "critical"    |
    "group"       |
    "note"        |
    "participant" |
    "activate"    |
    "deactivate"  |
    "title"       |
    "end"         |
    "box"         |
    "hide"        |
    "footbox"     |
    "skinparam"   |
    "destroy"     |
    "usecase"     |
    "class"       |
    "interface"   |
    "enum"        |
    "package"     |
    "scale"       |
    "namespace"   |
    "set"         |
    "if"          |
    "then"        |
    "endif"       |
    "partition"   |
    "start"       |
    "elseif"      |
    "repeat"      |
    "while"       |
    "endwhile"    |
    "fork"        |
    "detach"      |
    "component"   |
    "node"        |
    "cloud"       |
    "frame"       |
    "rectanagle"  |
    "state"       |
    "object"      { return token(TokenType.KEYWORD); }

    /* Operators */
    "[->"         |
    "->]"         |
    "<--]"        |
    "[o->"        |
    "[o->o"       |
    "[x->"        |
    ":"           |
    "x<-]"        |
    "[x<-"        |
    "->o]"        |
    "o->o]"       |
    "->"          |
    "-->"         |
    "--->"        |
    "<|--"        |
    ".."          |
    "<.."         |
    "<--"         |
    "..>"         |
    "-left->"     |
    "-right->"    |
    "-up->"       |
    "-down->"     |
    "*--"         |
    "*.."         |
    "o--"         |
    "o.."         |
    "--|>"        |
    "..|>"        |
    "*..>"        |
    "*-->"        |
    "<..*"        |
    "<--*"        |
    "#--"         |
    "--#"         |
    "x--"         |
    "--x"         |
    "}--"         |
    "--{"         |
    "+--"         |
    "--+"         |
    "^--"         |
    "--^"         |
    "as"          |
    "-"           |
    "~"           |
    "#"           |
    "+"           |
    "::"          |
    "()-"         |
    "(*)"         |
    "[*]"         { return token(TokenType.OPERATOR); }

    /* string literal */
    \"            {
                     yybegin(STRING);
                     tokenStart = yychar;
                     tokenLength = 1;
                  }

    /* character literal */
    \'            {
                     yybegin(CHARLITERAL);
                     tokenStart = yychar;
                     tokenLength = 1;
                  }



    /* whitespace */
    {WhiteSpace}  { }

    /* identifiers */
    {ID}  { return token(TokenType.IDENTIFIER); }

}

<STRING> {
  \"                             {
                                     yybegin(YYINITIAL);
                                     // length also includes the trailing quote
                                     return token(TokenType.STRING, tokenStart, tokenLength + 1);
                                 }

  {StringCharacter}+             { tokenLength += yylength(); }

  /* escape sequences */

  \\.                            { tokenLength += 2; }
  {LineTerminator}               { yybegin(YYINITIAL);  }
}

<CHARLITERAL> {
  \'                             {
                                     yybegin(YYINITIAL);
                                     // length also includes the trailing quote
                                     return token(TokenType.STRING, tokenStart, tokenLength + 1);
                                 }

  {CHAR}+             { tokenLength += yylength(); }

  /* escape sequences */

  \\.                            { tokenLength += 2; }
  {LineTerminator}               { yybegin(YYINITIAL);  }
}


<INSIDE_COLONS> {
    {EXT_ID} {yybegin(INSIDE_COLONS); return token(TokenType.IDENTIFIER); }
    ":" {yybegin(YYINITIAL); return token(TokenType.OPERATOR); }
    [^] {yybegin(YYINITIAL); return token(TokenType.ERROR); }
}

<INSIDE_PARENTHESIS> {
    {EXT_ID} {yybegin(INSIDE_PARENTHESIS); return token(TokenType.IDENTIFIER); }
    ")" {yybegin(YYINITIAL); return token(TokenType.OPERATOR); }
    [^] {yybegin(YYINITIAL); return token(TokenType.ERROR); }
}

<STEREOTYPE> {
    {CHAR}* {yybegin(STEREOTYPE); return token(TokenType.STRING); }
    ">>" {yybegin(YYINITIAL); return token(TokenType.OPERATOR); }
    [^] {yybegin(YYINITIAL); return token(TokenType.ERROR); }
}