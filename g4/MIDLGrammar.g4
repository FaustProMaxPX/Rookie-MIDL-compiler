grammar MIDLGrammar;
//@header {package gen;}
import MIDLLexer;
/*
| 表示或（备选分支）
* 表示出现0次或以上
? 表示出现0次或1次
+ 表示出现1次或以上
~ 表示取反
范围运算符：.. 或者 -，比如小写字母的表示：'a'..'z' 或者 [a-z]
*/

specification: definition+;
definition: type_decl';'| module ';';
module: 'module' ID '{' definition+ '}';
type_decl: struct_type | 'struct' ID;
struct_type: 'struct' ID '{' member_list '}';
member_list: (type_spec declarators ';')*;
type_spec: scoped_name | base_type_spec | struct_type;
scoped_name: '::'? ID ('::' ID)*;
base_type_spec: floating_pt_type|integer_type|'char'|'string'|'boolean';
floating_pt_type: 'float' | 'double' | 'long double';
integer_type: signed_int | unsigned_int;
signed_int: ('short'|'int16') | ('long'|'int32') | ('long' 'long' | 'int64') | 'int8';
unsigned_int: ('unsigned' 'short'| 'uint16')
   | ('unsigned' 'long'| 'uint32')
   | ('unsigned' 'long' 'long' | 'uint64')
   | 'uint8';
declarators: declarator (',' declarator)*;
declarator: simple_declarator | array_declarator;
simple_declarator: ID ('=' or_expr)?;
array_declarator: ID '[' or_expr ']' ('=' exp_list)?;
// 这里实验材料上写的是[]，但实际上数组应该是{}
exp_list: '{' or_expr (',' or_expr)* '}';
or_expr: xor_expr ('|' xor_expr)*;
xor_expr: and_expr ('^' and_expr)*;
and_expr: shift_expr ('&' shift_expr)*;
shift_expr: add_expr (('>>' | '<<') add_expr)*;
add_expr: mult_expr ( ('+' | '-') mult_expr )*;
mult_expr: unary_expr ( ('*' | '/' | '%' ) unary_expr )*;
unary_expr: ('-'| '+' | '~')? literal;
literal: INTEGER | FLOATING_PT | CHAR | STRING | BOOLEAN;
