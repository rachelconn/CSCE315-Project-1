grammar SQLParser;
program: {query | command};
query: relationname ' <- ' expr;
relationname: identifier;
expr:
    atomicexpr | selection | projection | renaming | union | difference | product | naturaljoin;
selection: 'SELECT ' '(' condition ') ' atomicexpr;
projection: 'PROJECT (' attributelist ') ' atomicexpr;
renaming: 'RENAME (' attributelist ') ' atomicexpr;
union: atomicexpr ' + ' atomicexpr;
difference: atomicexpr ' - ' atomicexpr;
product: atomicexpr ' * ' atomicexpr;
naturaljoin: atomicexpr ' & ' atomicexpr;
atomicexpr: (relationname | ('(' expr ')'));
condition: conjunction (' || ' conjunction)*;
conjunction: comparison (' && ' comparison)*;
comparison: operand op operand | '(' condition ')';
op: (' == ' | ' != ' | ' < ' | ' > ' | ' <= ' | ' >= ');
operand: attributename | literal;
identifier: ALPHA (ALPHA | DIGIT)*;
literal: '"'(ALPHA | DIGIT)*'"';
attributename: identifier;
attributelist: attributename (', ' attributelist)*;

ALPHA: [a-zA-Z];
DIGIT: [0-9];
