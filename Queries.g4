grammar SQLParser;
program: {query | command};
query: relationname ' <- ' expr;
relationname: IDENTIFIER;
expr:
    relationname
    | 'select ' condition expr //selection
    | 'project ' attributelist expr //projection
    | 'rename ' attributelist expr//renaming
    | expr ' + ' expr //union
    | expr ' - ' expr //difference
    | expr ' * ' expr //product
    | expr ' & ' expr; //naturaljoin
//selection: 'select ' condition expr;
condition: conjunction (' || ' conjunction)*;
conjunction: comparison (' && ' comparison)*;
comparison: operand (op operand)?;
op: ('==' | '!=' | '<' | '>' | '<=' | '>=');
operand: attributename;// | literal;
attributename: IDENTIFIER;
//literal: who knows finish this
//projection: 'project' (attributelist) expr;
attributelist: attributename (', ' attributelist)*;
/*renaming: 'rename ' (attributelist) expr;
union: expr ' + ' expr;
difference: expr ' - ' expr;
product: expr ' * ' expr;
naturaljoin: expr ' & ' expr;
*/

IDENTIFIER: ALPHA (ALPHA | DIGIT)*;
ALPHA: [a-zA-Z];
DIGIT: [0-9];
