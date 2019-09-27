// Define a grammar called Hello
grammar Rules;

program: (query ';'| command ';')+;

query: relationName '<-' expr;
relationName: IDENTIFIER;
attributeName: IDENTIFIER;

expr: (atomicExpr | selection | projection | renaming | union | difference | product | naturalJoin);
command  : ( openCmd | closeCmd | writeCmd | exitCmd | showCmd | createCmd | updateCmd | insertCmd | deleteCmd ) ;

//expressions
atomicExpr: relationName | '(' expr ')' ;
selection: 'select' '(' condition ')' atomicExpr;
projection: 'project' '(' attributeList ')' atomicExpr;
renaming: 'rename' '(' attributeList ')' atomicExpr;
union: atomicExpr '+' atomicExpr;
difference: atomicExpr '-' atomicExpr;
product: atomicExpr '*' atomicExpr;
naturalJoin: atomicExpr '&' atomicExpr;

condition: conjunction ('||' conjunction)*;
conjunction: comparison ('&&' comparison)*;
comparison: operand OP operand | '(' condition ')';
operand: attributeName | literal;
attributeList: attributeName (',' attributeName)*;

//commands
openCmd  : 'OPEN' relationName;
closeCmd : 'CLOSE' relationName;
writeCmd : 'WRITE' relationName;
exitCmd : 'EXIT';
showCmd : 'SHOW' atomicExpr;
createCmd: 'CREATE' 'TABLE' relationName '(' typedAttributeList ')' 'PRIMARY' 'KEY' '(' attributeList ')';
updateCmd: 'UPDATE' relationName 'SET' attributeName '=' literal (',' attributeName '=' literal)* 'WHERE' condition;
insertCmd: 'INSERT' 'INTO' relationName 'VALUES' 'FROM' ('(' literal (',' literal)*')' | 'RELATION' expr);
deleteCmd: 'DELETE' 'FROM' relationName 'WHERE' condition;
typedAttributeList:  attributeName type (',' attributeName type)*;
type: 'VARCHAR' '(' INTEGER ')' | 'INTEGER';
literal: STRING_LITERAL | INTEGER;

//tokenizers
IDENTIFIER: ALPHA (ALPHA | DIGIT)*;
OP: ('==' | '!=' | '<' | '>' | '<=' | '>=');
INTEGER: (DIGIT)+;
ALPHA: [a-zA-Z_];
DIGIT: [0-9];
STRING_LITERAL : '"' (ALPHA | DIGIT)+ '"';

WS: [ \n\t\r]+ -> skip;