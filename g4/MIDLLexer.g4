lexer grammar MIDLLexer;

// boolean 需要排在ID前面，因为它的取值也满足ID的表达式
BOOLEAN: 'TRUE' | 'true' | 'FALSE' | 'false';

UNDERLINE: '_';

INTEGER_TYPE_SUFFIX: 'l' | 'L';

// 所有 能表示多种运算符的节点都必须写入词法文件，
// 否则antlr不会将其识别为token，也就不能在生成树中直接追踪
SHIFT_OP: '>>' | '<<';
ADD_OP: '+' | '-';
MULT_OP: '*' | '/' | '%';

INTEGER: ('0' | [1-9] [0-9]*) INTEGER_TYPE_SUFFIX?;

EXPONENT: ('e' | 'E') ('+' | '-')? [0-9]+;

FLOAT_TYPE_SUFFIX:  'f' | 'F' | 'd' | 'D';

FLOATING_PT:  [0-9]+ '.' [0-9]*  EXPONENT?  FLOAT_TYPE_SUFFIX?
   				|  '.' [0-9]+  EXPONENT?  FLOAT_TYPE_SUFFIX?
   				|  [0-9]+  EXPONENT  FLOAT_TYPE_SUFFIX?
   				|  [0-9]+  EXPONENT?  FLOAT_TYPE_SUFFIX;


ESCAPE_SEQUENCE: '\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '"' | '\'' | '\\' );

CHAR: '\''(ESCAPE_SEQUENCE | (~'\\'|~'\''))'\'';

STRING: '"' (ESCAPE_SEQUENCE | (~'\\'|~'"'))* '"';

// 字母打头，后接下划线与字符数字的组合
ID: LETTER (UNDERLINE?(LETTER | DIGIT))*;
LETTER: [a-z] | [A-Z];
DIGIT: [0-9];

WS : [ \t\r\n]+ -> skip;