// Define a grammar called Hello
grammar Test;

program: (command)+;

attributeName: IDENTIFIER;
relationName: IDENTIFIER;
attributeList: attributeName (',' attributeName)*;
atomicExpr: relationName; //This is incorrect, need's to be updated with expr
expr: ;
condition: ;

command  : ( openCmd | closeCmd | writeCmd | exitCmd | showCmd | createCmd | updateCmd | insertCmd | deleteCmd ) ;
openCmd  : 'OPEN ' relationName;
closeCmd : 'CLOSE ' relationName;
writeCmd : 'WRITE ' relationName;
exitCmd : 'EXIT';
showCmd : 'SHOW ' atomicExpr;
createCmd: 'CREATE TABLE ' relationName ' (' typedAttributeList ') PRIMARY KEY (' attributeList ')';
updateCmd: 'UPDATE ' relationName ' SET ' attributeName '=' LITERAL (',' attributeName '=' LITERAL)* ' WHERE' condition;
insertCmd: 'INSERT INTO ' relationName ' VALUES FROM ' ('(' LITERAL (',' LITERAL)*')' | 'RELATION ' expr);
deleteCmd: 'DELETE FROM ' relationName ' WHERE ' condition;
typedAttributeList:  attributeName type (',' attributeName type)*;
type: 'VARCHAR(' INTEGER ')' | 'INTEGER';

IDENTIFIER: ALPHA (ALPHA | DIGIT)*;
INTEGER: (DIGIT)+;
ALPHA: [a-zA-Z_];
DIGIT: [0-9];
STRING_LITERAL : '"' (ALPHA | DIGIT)+ '"';
LITERAL: STRING_LITERAL | INTEGER;


WS: [ \n\t\r]+ -> skip;